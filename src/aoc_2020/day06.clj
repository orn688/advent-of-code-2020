(ns aoc-2020.day06
  (:require [clojure.set])
  (:require [clojure.string :as str]))

(defn parse-group
  [raw-group]
  (->> raw-group
       str/split-lines
       (map set)))

(defn parse-input
  "Returns a sequence of groups, where each group is a sequence of sets
  corresponding to the questions each group member answered 'yes' to."
  [orig-input]
  (->> orig-input
       str/trim
       (#(str/split % #"\n\n"))
       (map parse-group)))

(defn count-all-yeses
  [group]
  (->> group
       (apply clojure.set/union)
       count))

(defn part1
  "Returns the sum of the number of unique questions that any member of each
  group answered 'yes' to."
  [orig-input]
  (->> orig-input
       parse-input
       (map count-all-yeses)
       (reduce +)))

(defn count-shared-yeses
  [group]
  (->> group
       (apply clojure.set/intersection)
       count))

(defn part2
  "Returns the sum of the number of unique questions that *all* members of
  each group answered 'yes' to."
  [orig-input]
  (->> orig-input
       parse-input
       (map count-shared-yeses)
       (reduce +)))