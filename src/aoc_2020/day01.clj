(ns aoc-2020.day01
  (:require [clojure.string :as str]))

(defn parse-input
  [orig-input]
  (map #(Integer/parseInt %) (str/split-lines (str/trim orig-input))))

(defn part1
  [input]
  (println (parse-input input)))

(defn part2
  [input]
  (println "part2")
  (println input))