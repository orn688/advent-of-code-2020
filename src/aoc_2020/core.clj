(ns aoc-2020.core
  (:require [clj-http.client :as client])
  (:require [clojure.java.io :as io])
  (:require [aoc-2020.day01 :as day01])
  (:require [aoc-2020.day02 :as day02])
  (:require [aoc-2020.day03 :as day03])
  (:require [aoc-2020.day04 :as day04])
  (:require [aoc-2020.day05 :as day05])
  (:require [aoc-2020.day06 :as day06]))

(def year 2020)
(def cache-dir ".cache")
(def cookie-env-var "AOC_SESSION_ID")

(defn input-url
  [day]
  (str "https://adventofcode.com/" year "/day/" day "/input"))

(defn fetch-input
  "Download a day's AOC input"
  [day]
  (println (format "fetching input for day %d" day))
  (get
   (client/get (input-url day)
               {:cookies {"session" {:value (System/getenv cookie-env-var)}}})
   :body))

(defn get-input
  [day]
  (let [path (io/file cache-dir (format "%02d" day))]
    (when-not (.exists (io/as-file path))
      (spit path (fetch-input day)))
    (slurp path)))

(def funcs {1 [day01/part1 day01/part2]
            2 [day02/part1 day02/part2]
            3 [day03/part1 day03/part2]
            4 [day04/part1 day04/part2]
            5 [day05/part1 day05/part2]
            6 [day06/part1 day06/part2]})

(defn run-solution
  [day part]
  (let [input (get-input day)]
    (println ((nth (get funcs day) part) input))))

(defn -main
  "I don't do a whole lot."
  [day part]
  (run-solution
   (Integer/parseInt day)
   ; Use 1-indexed part numbers.
   (- (Integer/parseInt part) 1)))

