(ns day5.core
  (:require [clojure.string :as str]))

(def prog
  (->> (slurp "/Users/edd/work/github/aoc-2017/05/day5/puzzle.txt")
  (str/split-lines)
  (map #(Integer/parseInt %))
  (vec)))

(defn move [c]
  (loop [ins c pc 0 n 0]
    (if (>= pc (count ins)) n
        (let [i (nth ins pc)]
          (recur (update ins pc inc) (+ pc i) (inc n))))))

(defn part1 [] (println (move prog)))
;; -> 356945

(defn move2 [c]
  (loop [ins c pc 0 n 0]
    ;; (if (= 0 (mod n 5000)) (println "pc" pc))
    (if (>= pc (count ins)) n
        (let [i (nth ins pc)]
          (recur (update ins pc (if (>= i 3) dec inc)) (+ pc i) (inc n))))))

