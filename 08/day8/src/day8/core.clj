(ns day8.core
  (:require [clojure.string :as str]
            [clojure.spec.alpha :as s]
            [clojure.java.io :as io]))

(def test-file "/Users/edd/work/github/aoc-2017/08/day8/test.txt")
(def puzzle-file "/Users/edd/work/github/aoc-2017/08/day8/puzzle.txt")

(s/def ::register symbol?)
(s/def ::operand int?)
(s/def ::operator #{'inc 'dec})
(s/def ::comparator #{'== '> '< '>= '<= '!=})
(s/def ::iftoken #{'if})
(s/def ::condition (s/cat :reg ::register :op ::comparator :val ::operand))
(s/def ::action (s/cat :reg ::register :op ::operator :val ::operand))
(s/def ::instruction (s/cat :action ::action :if ::iftoken
                            :condition ::condition))

(def inp (->> (slurp puzzle-file)
              (str/split-lines)
              (map #(str/split % #" "))
              (map (partial map read-string))
              (map #(s/assert ::instruction %))
              (map #(s/conform ::instruction %))))

;; "b inc 5 if a > 1"
;; {:action {:reg b, :op inc, :val 5}, :if if,
;;  :condition {:reg a, :op >, :val 1}}

(defn read-reg [registers r] (get registers r 0))

(def != not=)
(defn test-cond [registers condition]
  (let [{r :reg op :op v :val} condition]
    (apply (eval op) (list (read-reg registers r) v))))

(defn act [registers action]
  (let [{r :reg op :op v :val} action]
    (assoc registers r
           (apply ({'inc + 'dec -} op) (list (read-reg registers r) v)))))

(defn interpret [prog]
  (loop [code prog registers {} m Integer/MIN_VALUE]
    (let [n (apply max (conj (vals registers) m))]
      (if (empty? code) (list registers m)
          (recur (rest code)
                 (if (test-cond registers (:condition (first code)))
                   (act registers (:action (first code)))
                   registers)
                 n)))))

(defn part1 [] (apply max (vals (first (interpret inp)))))
(defn part2 [] (second (interpret inp)))
