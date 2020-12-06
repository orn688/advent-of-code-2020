(ns aoc-2020.day05
  (:require [clojure.string :as str]))

(defn- seat-id
  "Interpets a seat name as a binary number."
  [seat-name]
  (Integer/parseInt
   (str/replace (str/trim seat-name) #"\w" {"F" "0", "B" "1", "L" "0", "R" "1"})
   2))

(defn- parse-input
  [input]
  (map seat-id (str/split-lines (str/trim input))))

(defn part1
  "Determines the maximum seat ID among all the seat strings."
  [input]
  (apply max (parse-input input)))

(defn- find-missing-id
  [seat-ids index]
  (if (= index (dec (count seat-ids)))
    (throw (Exception. "no missing seat ID found"))
    (let [curr (nth seat-ids index)
          next (nth seat-ids (inc index))
          diff (- next curr)]
      (case diff
        1 (find-missing-id seat-ids (inc index))
        2 (inc curr)
        (throw (Exception. (format "bad diff between seat ids %d and %d" curr next)))))))

(defn part2
  "Determines the narrator's seat ID (the only seat ID x such that x+1 and
  x-1 are present, but x is not)."
  [input]
  (->> input
       parse-input
       sort
       vec
       (#(find-missing-id % 0))))
