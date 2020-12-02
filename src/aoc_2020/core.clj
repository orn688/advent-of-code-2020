(ns aoc-2020.core
  (:require [clj-http.client :as client])
  (:require [clojure.java.io :as io])
  (:require [aoc-2020.day01 :as day01]))

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

(def funcs {1 [aoc-2020.day01/part1 aoc-2020.day01/part2]})

(defn run-solution
  [day part]
  (let [input (get-input day)]
    ((get (get funcs day) part) input)))

(defn -main
  "I don't do a whole lot."
  [day part]
  (run-solution
   (Integer/parseInt day)
   ; Use 1-indexed part numbers.
   (- (Integer/parseInt part) 1)))

