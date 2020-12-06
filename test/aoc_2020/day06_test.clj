(ns aoc-2020.day06-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc-2020.day06 :refer [part1 part2]]))

(def test-input "
abc

a
b
c

ab
ac

a
a
a
a

b
")

(deftest part1-test
  (testing "part1"
    (is (= (part1 test-input) 11))))

(deftest part2-test
  (testing "part2"
    (is (= (part2 test-input) 6))))