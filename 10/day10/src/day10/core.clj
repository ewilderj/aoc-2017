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

(defn interpret [prog l max-range cur skip]
  (loop [prog prog l l cur cur skip skip]
    (if (empty? prog) (list l cur skip)
        (recur (rest prog)
               (rev-and-move l (first prog) max-range cur)
               (mod (+ cur skip (first prog)) max-range)
               (inc skip)))))

(defn sixty-four [prog max-range]
  (loop [n 64 l (range max-range) cur 0 skip 0]
    (if (= n 0) l
        (let [[l2 ncur nskip]
              (interpret prog l max-range (mod cur max-range) skip)]
          (recur (dec n) l2 ncur nskip)))))

(defn make-hash [sparse-hash]
    (->> sparse-hash
         (partition 16)
         (map (partial apply bit-xor))
         (map #(format "%02x" %))
         (apply str)))

(println "part1" (apply * (take 2 (first (interpret inp1 (range 256) 256 0 0)))))
(println "part2" (make-hash (sixty-four inp2 256)))

