(ns day5.core
  (:require [clojure.string :as str]))

(def prog
  ->> (slurp "/Users/edd/work/github/aoc-2017/05/day5/puzzle.txt")
  (str/split-lines)
  (map #(Integer/parseInt %)))

