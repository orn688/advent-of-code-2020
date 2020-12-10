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

(defn- min-and-max
  "Returns the smallest and largest numbers from a list. Assumes the list is
  non-empty."
  ([nums] (min-and-max nums ##Inf ##-Inf))
  ([[num & rest] current-min current-max]
   (if (empty? rest)
     [current-min current-max]
     (min-and-max rest (min num current-min) (max num current-max)))))

(defn- subbarray-sum
  "Returns the indices x and y such that the [x, y) subbarray of `num` sums
  to `target`."
  ([target nums] (subbarray-sum target nums 0 0 0))
  ([target nums start end sum]
   (cond
     (< sum target) (subbarray-sum target nums start (inc end) (+ sum (nth nums end)))
     (> sum target) (subbarray-sum target nums (inc start) end (- sum (nth nums start)))
     :else (subvec nums start end))))

(defn part2
  "Finds the contiguous subarray of the input numbers that sums to the result
  of part1. Then returns the sum of the maximum and minimum numbers from that
  subarray."
  ([input] (part2 input 25 25))
  ([input preamble-size buffer-size]
   (let [target (part1 input preamble-size buffer-size)]
     (->> input
          parse-input
          (subbarray-sum target)
          min-and-max
          (reduce +)))))
