(ns day3.core)

;;; There are plenty of good solutions that embrace the sequence
;;; math of this. I went instead for the "fun with Clojure" part.
;;; for the clever stuff, visit:
;;;  https://www.reddit.com/r/adventofcode/comments/7h7ufl/2017_day_3_solutions/

(def inp 325489)

; 1 1 2 2 3 3 4 4 etc
(def steps (interleave (rest (range)) (rest (range))))
(def directions (cycle [[1 0] [0 -1] [-1 0] [0 1]]))
(def instructions (partition 2 (interleave directions steps)))
(def moves (concat [[0 0]] (mapcat (fn [[d l]] (repeat l d)) instructions)))
(defn add-vec [[x1 y1] [x2 y2]] [(+ x1 x2) (+ y1 y2)])
; nth coordinates just apply n moves, indexed at 1
(defn loc [n] (reduce add-vec [0 0] (take n moves)))

(println "part 1" (reduce + (map #(Math/abs %) (loc inp))))

(defn neighbor-coords [p]
  (map (partial add-vec p) [[0 1] [0 -1] [1 1] [1 -1] [1 0] [-1 0] [-1 1] [-1 -1]]))

(defn part2 []
  (loop [m {[0 0] 1} p 1]
    (let [pn (loc (inc p))
          vn (reduce + (map #(get m % 0) (neighbor-coords pn)))]
      (if (< vn inp) (recur (assoc m pn vn) (inc p)) vn))))

(println "part 2" (part2))
