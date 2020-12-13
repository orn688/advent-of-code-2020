(ns aoc-2020.day12
  (:require [clojure.string :as str]))

(defn- parse-line
  [line]
  [(first line)
   (Integer/parseInt (subs line 1))])

(defn- parse-input
  [input]
  (->> input
       str/trim
       str/split-lines
       (map parse-line)))

(defn- apply-instruction
  [pos [action amount]]
  (let [x (get pos :x)
        y (get pos :y)
        direction (get pos :direction)]
    (case action
      \N (assoc pos :y (+ y amount))
      \S (assoc pos :y (- y amount))
      \E (assoc pos :x (+ x amount))
      \W (assoc pos :x (- x amount))
      \L (assoc pos :direction (mod (- direction amount) 360))
      \R (assoc pos :direction (mod (+ direction amount) 360))
      \F (case direction
           0 (apply-instruction pos [\N amount])
           90 (apply-instruction pos [\E amount])
           180 (apply-instruction pos [\S amount])
           270 (apply-instruction pos [\W amount])))))

(defn- execute-instructions
  [position instructions]
  (if (empty? instructions)
    position
    (let [[current & rest] instructions]
      (execute-instructions (apply-instruction position current) rest))))

(defn- abs
  [n]
  (if (pos? n) n (- n)))

(defn- manhattan-distance
  [p1 p2]
  (+
   (abs (- (get p1 :x) (get p2 :x)))
   (abs (- (get p1 :y) (get p2 :y)))))

(defn part1
  "Finds the Manhattan distance from the ship's starting position to it's
  ending position after following all the navigation instructions."
  [input]
  (let [orig-position {:x 0, :y 0, :direction 90}
        final-position (execute-instructions orig-position (parse-input input))]
    (manhattan-distance orig-position final-position)))

(defn part2 [_])
