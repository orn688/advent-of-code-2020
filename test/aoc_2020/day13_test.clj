(ns aoc-2020.day13-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc-2020.day13 :refer [part1 part2]]))

(def test-input "
939
7,13,x,x,59,x,31,19
")

(deftest part1-test
  (testing "part1"
    (is (= (part1 test-input) 295))))