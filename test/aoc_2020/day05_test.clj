(ns aoc-2020.day05-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc-2020.day05 :refer [part1 seat-id]]))

(deftest seat-id-test
  (testing "seat-id returns the correct seat ID"
    (is (= (seat-id "BFFFBBFRRR") 567))
    (is (= (seat-id "FFFBBBFRRR") 119))
    (is (= (seat-id "BBFFBBFRLL") 820))))

(def test-input "
BFFFBBFRRR
FFFBBBFRRR
BBFFBBFRLL
")

(deftest part1-test
  (testing "part1"
    (is (= (part1 test-input) 820))))
