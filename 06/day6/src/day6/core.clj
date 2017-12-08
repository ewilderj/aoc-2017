(ns day6.core)

(def inp [10	3	15	10	5	15	5	15 9	2	5	8	5	2	3	6])

(defn distribute [a i]
  (loop [j (mod (inc i) (count a)) n (nth a i) b (assoc a i 0)]
    (if (<= n 0) b
        (recur (mod (inc j) (count b)) (dec n)
               (assoc b j (inc (nth b j)))))))

(defn search [a]
  (loop [a a seen #{} n 0]
    (if (contains? seen a) (list n a)
        (recur (distribute a (.indexOf a (apply max a)))
               (conj seen a) (inc n)))))

(println "part1 " (first (search inp)))
(println "part2 " (first (search (second (search inp)))))
