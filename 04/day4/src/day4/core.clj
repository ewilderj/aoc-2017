(ns day4.core
  (:require [clojure.string :as str]
            [clojure.walk :as w]))

(def codes (map #(str/split % #" ") (str/split-lines (slurp "/Users/edd/work/github/aoc-2017/04/day4/puzzle.txt"))))

(println "part1"
         (reduce + (map (fn [l] (if (empty? (filter #(> (second %) 1) (frequencies l))) 1 0)) codes)))

(println "part2"
         (reduce + (map (fn [l] (if (empty? (filter #(> (second %) 1) (frequencies (map (comp set frequencies) l)))) 1 0)) codes)))


;; threaded part 1
(def t1 (->> codes
             (map frequencies)
             (map (partial filter #(> (second %) 1)))
             (filter empty?)
             (count)))

;; threaded part 2
(def t2 (->> codes
             (map (partial map (comp set frequencies)))
             (map frequencies)
             (map (partial filter #(> (second %) 1)))
             (filter empty?)
             (count)))

