(ns day1.core
  (:require [clojure.string :as str]))

(def c (map #(- (int %) 48) (first (str/split-lines (slurp "/Users/edd/work/github/aoc-2017/01/day1/puzzle.txt")))))

(defn solution [i o]
  (reduce + (map first (filter #(apply = %) (partition 2 (interleave i (drop o (flatten (repeat i)))))))))

(println "part1 " (solution c 1))
(println "part2 " (solution c (/ (count c) 2)))
