(ns aoc-2020.day05-test
  (:require [clojure.string :as str]
            [clojure.test :refer [deftest is testing]]
            [aoc-2020.day05 :refer [part1 part2]]))

(deftest seat-id-test
  (testing "seat-id returns the correct seat ID"
    (is (= (#'aoc-2020.day05/seat-id "BFFFBBFRRR") 567))
    (is (= (#'aoc-2020.day05/seat-id "FFFBBBFRRR") 119))
    (is (= (#'aoc-2020.day05/seat-id "BBFFBBFRLL") 820))))

(def test-input-part1 "
BFFFBBFRRR
FFFBBBFRRR
BBFFBBFRLL
")

(deftest part1-test
  (testing "part1"
    (is (= (part1 test-input-part1) 820))))

(def test-input-part2
  (str/join "\n" (shuffle (map
                           (partial str "FFFFFF")
                           ["FRRR" ; 7
                            "BLLL" ; 8
                            "BLLR" ; 9
                            "BLRL" ; 10
                            ; Missing 11
                            "BRLL" ; 12
                            "BRLR" ; 13
                            "BRRL" ; 14
                            "BRRR" ; 15
                            ]))))

(deftest part2-test
  (testing "part2"
    (is (= (part2 test-input-part2) 11))))
