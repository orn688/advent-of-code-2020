(ns aoc-2020.day04
  (:require [clojure.string :as str]))

(defn parse-int
  [str-num]
  (try
    (Integer/parseInt str-num)
    (catch NumberFormatException _ nil)))

(defn num-in-range
  [str-num min-val max-val]
  (when-let [n (parse-int str-num)]
    (<= min-val n max-val)))

(defn year-in-range-func
  "Returns a unary function that validates that a given year (as a string) is
  within the inclusive range [min-val, max-val]."
  [min-val max-val]
  (fn year-in-range
    [year-string]
    (when (re-find #"^\d{4}$" year-string)
      (num-in-range year-string min-val max-val))))

(defn height-valid?
  [raw]
  (let [matcher (re-matcher #"^(?<value>\d+)(?<unit>cm|in)$" raw)]
    (when (.matches matcher)
      (let [value (.group matcher "value")]
        (case (.group matcher "unit")
          "cm" (num-in-range value 150 193)
          "in" (num-in-range value 59 76))))))

(def valid-eye-colors #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"})

; Mapping from field name to unary validator function.
(def required-fields {; Birth year
                      "byr" (year-in-range-func 1920 2002)
                      ; Passport issue year
                      "iyr" (year-in-range-func 2010 2020)
                      ; Passport expiration year
                      "eyr" (year-in-range-func 2020 2030)
                      ; Height
                      "hgt" height-valid?
                      ; Hair color
                      "hcl" #(re-find #"^#[\da-f]{6}$" %)
                      ; Eye color
                      "ecl" #(contains? valid-eye-colors %)
                      ; Passport ID
                      "pid" #(re-find #"^\d{9}$" %)})

(defn valid-part1?
  [passport]
  (every? false? (map #(nil? (get passport %)) (keys required-fields))))

(defn line-to-passport
  "Convert a space-separated string of `<key>:<val>` pairs to a hashmap."
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

(defn valid-count
  [valid-func passports]
  (count (filter valid-func passports)))

(defn part1
  "Returns the number of passports that have all of the required fields."
  [orig-input]
  (valid-count valid-part1? (parse-input orig-input)))

(defn field-valid?
  [passport field-name valid-func]
  (if-let [value (get passport field-name)]
    (boolean (valid-func value))
    false))

(defn valid-part2?
  "Determines whether all required fields in the passport are present and
  valid."
  [passport]
  (every? true? (map
                 #((comp boolean field-valid?) passport (key %) (val %))
                 required-fields)))

(defn part2
  "Returns the number of passports for which all required fields exist and
  conform to the requirements."
  [orig-input]
  (valid-count valid-part2? (parse-input orig-input)))