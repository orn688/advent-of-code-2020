(ns aoc-2020.day03
  (:require [clojure.string :as str]))

(def tree-space \#)
(def open-space \.)

(defn- parse-input
  [input]
  ((comp vec map) char-array (str/split-lines (str/trim input))))

(defn- tree-value
  [space]
  (condp = space
    tree-space 1
    open-space 0))

(defn- safe-subvec
  "Return the subvector of v starting at index `start`, or an empty vector if
  `start` is beyond the end of `v`."
  [v start]
  (if (< start (count v))
    (subvec v start)
    []))

(defn- tree-collisions
  [input run rise x-distance]
  (if (empty? input)
    0
    (let [row (first input)
          x (mod x-distance (count row))]
      (+ (tree-value (nth row x))
         (tree-collisions (safe-subvec input rise) run rise (+ x run))))))

(defn part1
  "Calculates how many trees we'll encounter going from the top left corner
  of the input grid until we reach the bottom, descending by `rise` rows and
  going right by `run` columns after each step."
  [input]
  (let [run 3
        rise 1]
    (tree-collisions (parse-input input) run rise 0)))

; [run, rise]
(def part2-slopes [[1 1]
                   [3 1]
                   [5 1]
                   [7 1]
                   [1 2]])

(defn- part2-impl
  [input]
  (apply * (map #(tree-collisions input (first %) (second %) 0) part2-slopes)))

(defn part2
  "Calculates the product across all of part2-slopes of the number of trees
  we'll encounter using that slope."
  [input]
  (->> input
       parse-input
       part2-impl))