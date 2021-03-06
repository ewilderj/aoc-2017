(ns day10.core
  (:require [clojure.string :as str]))

(def inp "83,0,193,1,254,237,187,40,88,27,2,255,149,29,42,100")
(def inp1 (map #(Integer/parseInt %) (str/split inp #",")))
(def inp2 (concat (map int inp) (list 17 31 73 47 23)))

;; reverse n digits at point p, returns cycle from p on
(defn reverse-n [l n m p]
  (let [r (drop p (cycle l))]
    (take m (concat (reverse (take n r)) (drop n r)))))

;; reverse digits, correct for reverse-n's offset
(defn rev-and-move [l n m p]
  (take m (drop (mod (- p) m) (cycle (reverse-n l n m p)))))

(defn interpret [prog max-range]
  (loop [prog prog l (range max-range) cur 0 skip 0]
    (if (empty? prog) l
        (recur (rest prog)
               (rev-and-move l (first prog) max-range cur)
               (mod (+ cur skip (first prog)) max-range)
               (inc skip)))))

(defn make-hash [sparse-hash]
    (->> sparse-hash
         (partition 16)
         (map (partial apply bit-xor))
         (map #(format "%02x" %))
         (apply str)))

(println "part1" (apply * (take 2 (interpret inp1 256))))
(println "part2" (make-hash (interpret (flatten (repeat 64 inp2)) 256)))
