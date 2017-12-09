(ns day8.core
  (:require [clojure.string :as str]
            [clojure.spec.alpha :as s]))

(def test-file "/Users/edd/work/github/aoc-2017/08/day8/test.txt")
(def puzzle-file "/Users/edd/work/github/aoc-2017/08/day8/puzzle.txt")

(def inp (str/split-lines (slurp test-file)))

(s/def ::condition (s/cat :reg symbol? :op #{"==" ">" "<" ">=" "<="}
                          :n integer?))
(s/def ::instr (s/cat :reg symbol? :op #{"inc" "dec"}
                      :n integer? ::if #{"if"} :cond ::condition ))
