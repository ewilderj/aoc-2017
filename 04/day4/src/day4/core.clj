(ns day4.core
  (:require [clojure.string :as str]))

(def inp (str/split-lines (slurp "/Users/edd/work/github/aoc-2017/04/day4/puzzle.txt")))

(println "part1"
         (reduce + (map (fn [l] (if (empty? (filter #(> (second %) 1) (frequencies (str/split l #" ")))) 1 0)) inp)))

(println "part2"
         (reduce + (map (fn [l] (if (empty? (filter #(> (second %) 1) (frequencies (map (comp set frequencies) (str/split l #" "))))) 1 0)) inp)))


         
