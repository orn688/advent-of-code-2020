(ns aoc-2020.day04
  (:require [clojure.string :as str]))

(def required-fields ["byr" "iyr" "eyr" "hgt" "hcl" "ecl" "pid"])
(def optional-fields ["cid"])

(defn line-to-passport
  [line]
  (try
    (into {} (map #(str/split % #":" 2) (str/split line #" ")))
    (catch Exception e
      (throw (Exception. (format "can't parse passport '%s': %s" line (.getMessage e)))))))

(defn parse-input
  [orig-input]
  (map line-to-passport
       (map
        #(str/replace % #"\n" " ")
        (str/split (str/trim orig-input) #"\n\n"))))

(defn valid?
  [passport]
  (every? false? (map #(nil? (get passport %)) required-fields)))

(defn part1-impl
  [input]
  (count (filter valid? input)))

(defn part1
  "Returns how many passports are valid (have all required fields)."
  [orig-input]
  (part1-impl (parse-input orig-input)))

(defn part2
  [_])