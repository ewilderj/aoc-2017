(ns day2.core
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :as combo]))

(def inp
  (map (fn [c] (map #(Integer/parseInt %) c))
       (map #(str/split % #"\t")
            (str/split-lines (slurp "/Users/edd/work/github/aoc-2017/02/day2/puzzle.txt")))))

(println "part1 " (reduce + (map #(- (apply max %) (apply min %)) inp)))

(defn cx [c]
  (apply / (flatten (filter #(= 0 (apply mod %)) (map (partial sort >) (combo/combinations c 2))))))

(println "part2 " (reduce + (map cx inp)))

;; EXTENSION EXERCISE: do it without non-core libraries
;; if you think using combo/combinations is cheating, here's
;; a definition of it just for sorted pairwise combinations

(defn pairwise-combinations [c]
  (partition 2 (flatten (map (fn [[x y]] (map #(sort > (list x %)) y))
                             (drop-last (map-indexed (fn [n x] (list x (drop (inc n) c))) c))))))

(defn px [c]
  (apply / (flatten (filter #(= 0 (apply mod %)) (pairwise-combinations c)))))

(println "part2-ex " (reduce + (map px inp)))
