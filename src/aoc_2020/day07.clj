(ns aoc-2020.day07
  (:require [clojure.set])
  (:require [clojure.string :as str]))

(def start-color "shiny gold")

(defn- parse-contained-bag
  "'5 faded blue bags' -> ['faded blue' '5']"
  [bag]
  (let [matcher (re-matcher #"^(?<count>\d+) (?<color>[\w ]+) bags?$" bag)]
    (when (.matches matcher)
      (let
       [count (Integer/parseInt (.group matcher "count"))
        color (.group matcher "color")]
        [color count]))))

(defn- parse-contents
  [contents]
  (if (= contents "no other bags")
    {}
    (->> contents
         (#(str/split % #", "))
         (map parse-contained-bag))))

(defn- parse-line
  [line]
  (let [matcher (re-matcher #"^(?<color>[\w ]+) bags contain (?<contents>.+)\.$" line)]
    (when (.matches matcher)
      (let [color (.group matcher "color")
            contents (.group matcher "contents")]
        [color (into {} (parse-contents contents))]))))

(def reachable
  "Determines whether a bag of color `current` contains (directly or indirectly) any bags of color `target`."
  (memoize
   (fn
     [graph current target]
     (let [edges (get graph current)]
       (if (contains? edges target)
         true
         (some true? (map #(reachable graph % target) (keys edges))))))))

(defn- parse-input
  [input]
  (->> input
       str/trim
       str/split-lines
       (map parse-line)
       (into {})))

(defn- filter-reachable
  [target graph]
  (filter #(reachable graph % target) (keys graph)))

(defn part1
  "Returns the number of bag colors that can contain shiny gold bags."
  [input]
  (->> input
       parse-input
       (filter-reachable start-color)
       count))

(def total-bags
  "Returns the total number of bags contained in a bag of color
  `containing-color`."
  (memoize
   (fn
     [containing-color graph]
     (let [edges (get graph containing-color)]
       (reduce + 0
               (map
                (fn
                  [[color count]]
                  ; For each bag, count the bag itself plus the number of bags
                  ; it contains.
                  (* count (inc (total-bags color graph))))
                edges))))))

(defn part2
  "Returns the number of bag colors that can contain shiny gold bags."
  [input]
  (->> input
       parse-input
       (total-bags start-color)))