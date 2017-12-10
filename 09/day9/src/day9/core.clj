(ns day9.core
  (:require [clojure.string :as str]
            [clojure.spec.alpha :as s]
            [clojure.java.io :as io]))

;; An exercise in spec
;; Doesn't perform so quickly!

(defn remove-ignored [s] (apply concat (str/split s #"!.")))

(def inp (str/trim (slurp "/Users/edd/work/github/aoc-2017/09/day9/puzzle.txt")))

(defn random? [c] (not= c \>))
(s/def ::garbage (s/cat :s #{\<} :u (s/* random?) :e #{\>}))
(s/def ::content (s/cat :data (s/alt :group ::group :garbage ::garbage)
                        :m (s/? #{\,})))
(s/def ::group (s/cat :x #{\{} :content (s/* ::content) :y #{\}}))

(def test-garbage ["<>", "<jshdkjdhsf>", "<<<<>", "<{!>}>", "<!!>",
                   "<!!!>>", "<{o\"i!a,<{i<a>"])

(assert (every? #{true} (map #(s/valid? ::garbage (remove-ignored %)) test-garbage)))

(def test-groups ["{}", "{{{}}}", "{{},{}}",
                  "{{{},{},{{}}}}", "{<a>,<a>,<a>,<a>}",
                  "{{<ab>},{<ab>},{<ab>},{<ab>}}",
                  "{{<!!>},{<!!>},{<!!>},{<!!>}}",
                  "{{<a!>},{<a!>},{<a!>},{<ab>}}"])

(assert (every? #{true} (map #(s/valid? ::group (remove-ignored %)) test-groups)))

(defn count-in [g n]
  (conj
   (if-let [c (:content g)]
     (->> c
          (map :data)
          (filter #(#{:group} (first %)))
          (map #(count-in (second %) (inc n)))
          )) n))

(defn part1 []
  (reduce + (flatten (count-in (s/conform ::group (remove-ignored inp)) 1))))

(defn count-garbage [g]
  (let [gf (fn [[t m]] (if (= :group t) (count-garbage m) (count (:u m))))
        c (:content g)]
  (if (nil? c) (list 0)
      (->> c
           (map :data)
           (filter #(#{:group :garbage} (first %)))
           (map gf)))))

(defn part2 []
  (reduce + (flatten (count-garbage (s/conform ::group (remove-ignored inp))))))
