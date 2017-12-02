(ns day2.core
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :as combo]))

(def inp
  (->> (slurp "/Users/edd/work/github/aoc-2017/02/day2/puzzle.txt")
       (str/split-lines)
       (map #(str/split % #"\t"))
       (map (fn [c] (map #(Integer/parseInt %) c)))))

(println "part1 " (reduce + (map #(- (apply max %) (apply min %)) inp)))

(defn cx [c]
  (->> (combo/combinations (sort > c) 2)
       (filter #(= 0 (apply mod %)))
       (flatten)
       (apply /)))

(println "part2 " (reduce + (map cx inp)))

;; EXTENSION EXERCISE: do it without non-core libraries
;; if you think using combo/combinations is cheating, here's
;; a definition of it just for pairwise combinations

(defn pairwise-combinations [c]
  (->> (map-indexed (fn [n x] (list x (drop (inc n) c))) c)
       (drop-last)
       (map (fn [[x y]] (map #(list x %) y)))
       (flatten)
       (partition 2)))

(defn px [c]
  (->> (pairwise-combinations (sort > c))
       (filter #(= 0 (apply mod %)))
       (flatten)
       (apply /)))

(println "part2-ex " (reduce + (map px inp)))
