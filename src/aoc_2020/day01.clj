(ns aoc-2020.day01
  (:require [clojure.string :as str]))

(def target-sum 2020)

(defn- parse-input
  [input]
  (map #(Integer/parseInt %) (str/split-lines (str/trim input))))

(defn- two-sum-product
  [elements index seen-elements]
  (when (>= index (count elements))
    -1)
  (let [current-element (nth elements index)
        target-val (- target-sum current-element)]
    (if (contains? seen-elements target-val)
      (* current-element target-val)
      (two-sum-product elements (+ index 1) (conj seen-elements current-element)))))

(defn- part1-impl
  [input]
  (two-sum-product input 0 (set nil)))

(defn part1
  "Determines the product of the two numbers that sum to 2020."
  [input]
  (part1-impl (parse-input input)))

(defn- check-index-impl
  [elements mid-index last-index]
  (if (>= mid-index last-index)
    nil
    (let [x (first elements)
          y (nth elements mid-index)
          z (nth elements last-index)
          s (+ x y z)]
      (cond
        ; Slight optimization: in either of these cases it's impossible to
        ; achieve the target sum given x, so we should just give up on x.
        (> (+ x y y) target-sum) nil
        (< (+ x z z) target-sum) nil
        ; Because the vector is sorted, if the current sum is too small then
        ; we'll increase the lower of the two bounds; if it's too large then
        ; we'll decrease the larger of the two.
        (> s target-sum) (check-index-impl elements mid-index (dec last-index))
        (< s target-sum) (check-index-impl elements (inc mid-index) last-index)
        :else (* x y z)))))

(defn- check-index
  "Checks whether the given elements (a subvector containing a suffix of the
   original input) contain a trio of numbers that sum to 2020, where the first
   element must be in the trio."
  [elements]
  (check-index-impl elements 1 (dec (count elements))))

(defn- three-sum-product
  "Checks each index of the sorted elements to see if the index is the smallest
   in some trio of numbers that sum to 2020. Returns the product of the three
   numbers if it finds such a trio, else nil."
  [elements]
  (let [res (check-index elements)]
    (if (nil? res)
      (three-sum-product (subvec elements 1))
      res)))

(defn- part2-impl
  [input]
  ; Sort the elements to enable faster three-sum searching.
  (three-sum-product (vec (sort input))))

(defn part2
  "Determines the product of the three numbers that sum to 2020."
  [input]
  (part2-impl (parse-input input)))