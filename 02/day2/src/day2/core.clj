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
