(ns aoc-2020.day12-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc-2020.day12 :refer [part1 part2]]))

(def test-input "
F10
N3
F7
R90
F11
")

(deftest part1-test
  (testing "part1"
    (is (= (part1 test-input) 25))))

(deftest part2-test
  (testing "part2"
    (is (= (part2 test-input) 286))))
