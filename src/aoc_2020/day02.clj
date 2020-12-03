(ns aoc-2020.day02
  (:require [clojure.string :as str]))

(deftype PasswordInfo [min-count max-count letter password])

(defn create-password-info
  "`1-3 a: abcde` ->
   password-info{min-count: 1, max-count: 3, letter: `a`, password: `abcde`}"
  [raw-info]
  (let [parts (str/split raw-info #" ")
        min-max (map #(Integer/parseInt %) (str/split (first parts) #"-"))
        min-count (first min-max)
        max-count (second min-max)
        letter (first (second parts))
        password (peek parts)]
    (PasswordInfo. min-count max-count letter password)))

(defn parse-input
  [orig-input]
  (map create-password-info (str/split-lines (str/trim orig-input))))

(defn count-occurrences
  [password letter]
  (get (frequencies password) letter 0))

(defn is-valid
  [password-info]
  (let [actual-count (count-occurrences (.password password-info) (.letter password-info))]
    (and
     (>= actual-count (.min-count password-info))
     (<= actual-count (.max-count password-info)))))

(defn part1-impl
  [password-infos]
  (count (filter is-valid password-infos)))

(defn part1
  "Given a list of passwords, each with a constraint (a required letter and the range of allowed counts for that
   letter), determine how many passwords satisfy their constraint."
  [orig-input]
  (part1-impl (parse-input orig-input)))

(defn part2
  [orig-input]
  ())