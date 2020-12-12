(ns aoc-2020.day10
  (:require [clojure.string :as str]))

(defn- parse-input
  [input]
  (->> input
       str/trim
       str/split-lines
       (map #(Integer/parseInt %))))

(defn- steps
  [[curr & rest]]
  (if (empty? rest)
    []
    (conj (steps rest) (- (first rest) curr))))

(defn part1
  "Arranges all the adaptors in order by rating and calculates the number of
  3-jolt and 1-jolt increases along all the adaptors from the outlet to the
  device. Then returns the product of the 3-jolt and 1-jolt counts."
  [input]
  (->> input
       parse-input
       ; Add the rating of the outlet (zero) and the rating of the device
       ; (three greater than the max of the adaptor ratings).
       (#(conj % 0 (+ 3 (apply max %))))
       sort
       steps
       frequencies
       (#(* (get % 1) (get % 3)))))

(defn- is-valid-index
  "Given an adaptor with rating `curr`, determine whether the input rating at
  index `i` in `rest` can possibly be connected to the current adaptor."
  [i curr rest]
  (and (< i (count rest)) (<= (- (nth rest i) curr) 3)))

(def count-arrangements-impl
  "Given that we *include* curr, how many arrangements of the remaining
  adaptors are there that start with curr?"
  (memoize
   (fn
     [curr rest]
     (if (empty? rest)
       1
       (->> [0 1 2]
            (filter #(is-valid-index % curr rest))
            (map #(count-arrangements-impl (nth rest %) (subvec rest (inc %))))
            (reduce + 0))))))

(defn- count-arrangements
  [ratings]
  (count-arrangements-impl (first ratings) (subvec ratings 1)))

(defn part2
  "Counts how many possible arrangements of the adaptors can connect the
  outlet to your device, with no jump of greater than 3 jolts of capacity."
  [input]
  (->> input
       parse-input
       ; Add the rating of the outlet (zero) and the rating of the device
       ; (three greater than the max of the adaptor ratings).
       (#(conj % 0 (+ 3 (apply max %))))
       sort
       vec
       count-arrangements))
