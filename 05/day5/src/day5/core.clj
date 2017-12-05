(ns day5.core
  (:require [clojure.string :as str]))

(def prog
  (->> (slurp "/Users/edd/work/github/aoc-2017/05/day5/puzzle.txt")
  (str/split-lines)
  (map #(Integer/parseInt %))
  (vec)))

(defn move [c]
  (loop [ins (transient c) pc 0 n 0]
    (if (>= pc (count ins)) n
        (let [i (nth ins pc)]
          (recur (assoc! ins pc (inc i)) (+ pc i) (inc n))))))

(defn part1 [] (println (move prog)))
;; -> 356945

(defn move2 [c]
  (loop [ins (transient c) pc 0 n 0]
    (if (>= pc (count ins)) n
        (let [i (nth ins pc)]
          (recur (assoc! ins pc (if (>= i 3) (dec i) (inc i))) (+ pc i) (inc n))))))

(defn part2 [] (println (move2 prog)))
;; -> 28372145

;; using transient took part1 from 447 to 140 msecs (3.1x better)
;; and part f2 from 35077 msecs to 10926 msecs (3.2x)
