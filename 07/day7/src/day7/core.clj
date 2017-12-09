(ns day7.core
  (:require [clojure.string :as str]
            [clojure.walk :as walk]
            [clojure.set :as s]
            [clojure.set :as set]))

(def test-file "/Users/edd/work/github/aoc-2017/07/day7/test.txt")
(def puzzle-file "/Users/edd/work/github/aoc-2017/07/day7/puzzle.txt")

(defn read-data
  "Generate two maps: weights, and discs->parents"
  [f]
  (loop [d (str/split-lines (slurp f)) w {} g {}]
    (if (empty? d) (list w g)
        (let [[x c] (str/split (first d) #" -> ")
              [_ xn xw] (re-find #"(\w+)\s+\(([0-9]+)" x)
              p (if (nil? c) {}
                     (into {} (map vec (partition 2 (interleave (str/split c #", ") (repeat xn))))))]
          (recur (rest d) (assoc w xn (Integer/parseInt xw)) (conj g p))
          ))))

(defn part1 []
  "The parent-less disc is the answer"
  (let [[w m] (read-data puzzle-file)]
    (s/difference (set (vals m)) (set (keys m)))))

(def st-weight
  (memoize
   (fn [w t n] (reduce + (w n) (map (partial st-weight w t) (kids n t))))))

(defn part2 []
  (let [[w m] (read-data puzzle-file)]

    ;; start at root
    (loop [n (first (s/difference (set (vals m)) (set (keys m))))
           target-weight 0]

      (let [;; which disks are stacked on this node?
            k (map first (filter #(= n (second %)) m))

            ;; compute stacked tower weights
            ws (map (partial st-weight w m) k)

            ;; reverse map of disc names to weights: ok lossy because we
            ;; only care about identifying the odd-disc-out
            stw (into {} (map vec (partition 2 (interleave ws k))))

            ;; look up disc name for odd man out (weight with frequency 1)
            ono (stw (get (set/map-invert (frequencies ws)) 1))

            ;; target weight for our children is one with non-1 freq
            tgt (ffirst (filter #(not (= 1 (second %))) (frequencies ws)))]

        ;; if no odd-man-out, return the weight we ought to be
        (if (nil? ono) (- target-weight (reduce + ws))

            ;; otherwise, search the odd man out stack
            (recur ono tgt))))))
