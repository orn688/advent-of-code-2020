(ns aoc-2020.day06
  (:require [clojure.string :as str]))

(defn part1
  "Returns the sum of the number of unique questions that any member of each
  group answered 'yes' to."
  [orig-input]
  (->> orig-input
       str/trim
       (#(str/split % #"\n\n"))
       (map #(str/replace % #"\n" ""))
       (map frequencies)
       (map keys)
       (map count)
       (reduce +)))

(defn part2
  [_])