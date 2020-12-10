(ns aoc-2020.day09
  (:require [clojure.string :as str]
            [clojure.data.finger-tree :refer [conjl double-list]]
            [clojure.edn :as edn]))

(defn- parse-input
  [input]
  (->> input
       str/trim
       str/split-lines
       (map edn/read-string)
       vec))

(defn- check-two-sum
  ([target candidates] (check-two-sum target candidates #{}))
  ([target [candidate & rest] seen]
   (if (contains? seen (- target candidate))
     true
     (if (empty? rest)
       false ; Didn't find a two-sum.
       (check-two-sum target rest (conj seen candidate))))))

(defn- find-invalid
  [[n & rest] buffer]
  (if (check-two-sum n buffer)
    (find-invalid rest (conjl (pop buffer) n))
    n))

(defn- init-buffer
  [nums preamble-size buffer-size]
  (->> nums
       (#(subvec % (- preamble-size buffer-size) preamble-size))
       reverse
       (apply double-list)))

(defn part1
  "Returns the first number after the preamble that is *not* the sum of any
  two of the preceding 25 numbers."
  ([input] (part1 input 25 25))
  ([input preamble-size buffer-size]
   (->> input
        parse-input
        (#(find-invalid
           (subvec % preamble-size)
           (init-buffer % preamble-size buffer-size))))))

(defn part2
  [_])