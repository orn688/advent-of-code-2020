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

(defn part2
  ([_]))
