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

(defn- neighbor-count-part1
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
  [neighbor-count-func max-neighbors seats row col]
  (let [status (seat-status seats row col)]
    (cond
      (and
       (= status seat-empty)
       (zero? (neighbor-count-func seats row col))) seat-occupied
      (and
       (= status seat-occupied)
       (>= (neighbor-count-func seats row col) max-neighbors)) seat-empty
      :else status)))

(defn- advance-row
  [neighbor-count-func max-neighbors seats row]
  (->> (range (count (first seats)))
       (map #(advance-seat neighbor-count-func max-neighbors seats row %))))

(defn- advance-one-round
  [neighbor-count-func max-neighbors seats]
  (->> (range (count seats))
       (map #(advance-row neighbor-count-func max-neighbors seats %))
       (map vec)
       vec))

(defn- run-until-no-change
  [neighbor-count-func max-neighbors seats]
  (let [new-seats (advance-one-round neighbor-count-func max-neighbors seats)]
    (if (= seats new-seats)
      seats
      (run-until-no-change neighbor-count-func max-neighbors new-seats))))

(defn- run-simulation
  [input neighbor-count-func max-neighbors]
  (->> input
       parse-input
       (run-until-no-change neighbor-count-func max-neighbors)
       (map #(count-occurrences seat-occupied %))
       (reduce +)))

(defn part1
  "Runs the simulation until the seating stops changing, then returns the
  number of occupied seats."
  [input]
  (run-simulation input neighbor-count-part1 4))

(def neighbor-diffs
  [[-1 -1]
   [-1 0]
   [-1 1]
   [0 -1]
   [0 1]
   [1 -1]
   [1 0]
   [1 1]])

(def neighbor-in-view
  (memoize
   (fn
     [seats row col row-diff col-diff]
     (let [nrow (+ row row-diff)
           ncol (+ col col-diff)]
       (cond
         ; Reached the edge of the seating area without seeing an occupied seat.
         (or
          (neg? nrow) (neg? ncol)
          (>= nrow (count seats)) (>= ncol (count (first seats)))) false
         ; An empty seat blocks the view of the rest of the seats in line.
         (= seat-empty (seat-status seats nrow ncol)) false
         ; Found an occupied seat.
         (= seat-occupied (seat-status seats nrow ncol)) true
         ; Keep looking further.
         :else (neighbor-in-view seats nrow ncol row-diff col-diff))))))

(defn- neighbor-count-part2
  "Counts the number of occupied seats that are within view of the specified
  row or column along a vertical or horizontal line, or a diagonal."
  [seats row col]
  (->> neighbor-diffs
       (map #(neighbor-in-view seats row col (first %) (second %)))
       (count-occurrences true)))

; TODO: This is super slow (~2 minutes to produce a final answer). It can
; presumably be optimized further.
(defn part2
  "Runs the simulation until the seating stops changing, then returns the
  number of occupied seats. Unlike part 1, neighbors are counted by whether
  they're visible along a straight line or diagonal, and an occupied seat
  will only turn empty if there are 5 or more neighbors."
  [input]
  (run-simulation input neighbor-count-part2 5))
