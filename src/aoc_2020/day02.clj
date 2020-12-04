(ns aoc-2020.day02
  (:require [clojure.string :as str]))

(deftype PasswordInfo [x y letter password])

(defn create-password-info
  "`1-3 a: abcde` ->
   password-info{x: 1, y: 3, letter: `a`, password: `abcde`}"
  [raw-info]
  (let [parts (str/split raw-info #" ")
        nums (map #(Integer/parseInt %) (str/split (first parts) #"-"))
        x (first nums)
        y (second nums)
        letter (first (second parts))
        password (peek parts)]
    (PasswordInfo. x y letter password)))

(defn parse-input
  [orig-input]
  (map create-password-info (str/split-lines (str/trim orig-input))))

(defn count-occurrences
  [password letter]
  (get (frequencies password) letter 0))

(defn is-valid-part1
  [pwi]
  (let [actual-count (count-occurrences (.password pwi) (.letter pwi))]
    (and
     (>= actual-count (.x pwi))
     (<= actual-count (.y pwi)))))

(defn part1-impl
  [pwis]
  (count (filter is-valid-part1 pwis)))

(defn part1
  "Given a list of passwords, each with a constraint (a required letter and
  the range of allowed counts for that letter), determine how many passwords
  satisfy their constraint."
  [orig-input]
  (part1-impl (parse-input orig-input)))

(defn is-valid-part2
  [pwi]
  (let [letter (.letter pwi)
        password (.password pwi)
        ; Indices are one-based, hence the `dec`s to convert to zero-based.
        letter-x (nth password (dec (.x pwi)))
        letter-y (nth password (dec (.y pwi)))]
    (not=
     (= letter letter-x)
     (= letter letter-y))))

(defn part2-impl
  [pwis]
  (count (filter is-valid-part2 pwis)))

(defn part2
  "Given a list of passwords, each with a constraint (a required letter and
  two indices such that the letter must be at exactly one of the two
  indices), determine how many passwords satisfy their constraint."
  [orig-input]
  (part2-impl (parse-input orig-input)))