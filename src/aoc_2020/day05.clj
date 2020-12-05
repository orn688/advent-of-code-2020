(ns aoc-2020.day05
  (:require [clojure.string :as str]))

(defn seat-id
  "Interpets a seat name as a binary number."
  [seat-name]
  (Integer/parseInt
   (str/replace (str/trim seat-name) #"\w" {"F" "0", "B" "1", "L" "0", "R" "1"})
   2))

(defn parse-input
  [orig-input]
  (str/split-lines (str/trim orig-input)))

(defn part1
  "Determines the maximum seat ID among all the seat strings."
  [orig-input]
  (apply max (map seat-id (parse-input orig-input))))

(defn part2
  [_])