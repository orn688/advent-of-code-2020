(ns aoc-2020.day01
  (:require [clojure.string :as str]))

(def target-sum 2020)

(defn parse-input
  [orig-input]
  (map #(Integer/parseInt %) (str/split-lines (str/trim orig-input))))

(defn two-sum-product
  [elements index seen-elements]
  (when (>= index (count elements))
    -1)
  (let [current-element (nth elements index)
        target-val (- target-sum current-element)]
    (if (contains? seen-elements target-val)
      (* current-element target-val)
      (two-sum-product elements (+ index 1) (conj seen-elements current-element)))))

(defn part1-impl
  [input]
  (two-sum-product input 0 (set nil)))

(defn part1
  "determines the product of the two numbers that sum to 2020"
  [orig-input]
  (part1-impl (parse-input orig-input)))

(defn part2
  "determines the product of the three numbers that sum to 2020"
  [_]
  nil)