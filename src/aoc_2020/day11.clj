(ns aoc-2020.day11
  (:require [clojure.string :as str]))

(def seat-empty \L)
(def seat-occupied \#)
(def no-seat \.)

(defn- padded-row
  [seats]
  (->> seats
       first
       count
       (#(repeat % no-seat))))

(defn- parse-input
  "Parse the input and add a seatless border around it so that the main
  algorithm doesn't have to do bounds checking."
  [input]
  (->> input
       str/trim
       str/split-lines
       (map char-array)
       (map #(concat [no-seat] % [no-seat]))
       (#(concat [(padded-row %)] % [(padded-row %)]))))

(defn- neighbor-coords
  [row col]
  [[(inc row) (dec col)]
   [(inc row) col]
   [(inc row) (inc col)]
   [row (dec col)]
   [row (inc col)]
   [(dec row) (dec col)]
   [(dec row) col]
   [(dec row) (inc col)]])

(defn- seat-status
  [seats row col]
  (nth (nth seats row) col))

(defn count-occurrences
  [target coll]
  (->> coll
       frequencies
       (#(get % target 0))))

(defn- neighbor-count
  "Counts the number of occupied seats adjacent (including diagonals) to the
  seat at the specified row and column. Assumes that the row and column are
  *not* at the edge of the seating grid, which is safe to assume because we
  pad the seating grid with no-seat spots, and avoid calling this function
  for no-seat spots."
  [seats row col]
  (->> (neighbor-coords row col)
       (map #(seat-status seats (first %) (second %)))
       (count-occurrences seat-occupied)))

(defn- advance-seat
  [seats row col]
  (let [status (seat-status seats row col)]
    (cond
      (and
       (= status seat-empty)
       (zero? (neighbor-count seats row col))) seat-occupied
      (and
       (= status seat-occupied)
       (>= (neighbor-count seats row col) 4)) seat-empty
      :else status)))

(defn- advance-row
  [seats row]
  (->> (range (count (first seats)))
       (map #(advance-seat seats row %))))

(defn- advance-one-round
  [seats]
  (->> (range (count seats))
       (map #(advance-row seats %))
       (map vec)
       vec))

(defn- run-until-no-change
  [seats]
  (let [new-seats (advance-one-round seats)]
    (if (= seats new-seats)
      seats
      (run-until-no-change new-seats))))

(defn part1
  [input]
  (->> input
       parse-input
       run-until-no-change
       (map #(count-occurrences seat-occupied %))
       (reduce +)))

(defn part2
  [_])
