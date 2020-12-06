(ns aoc-2020.day02-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc-2020.day02 :refer [part1 part2]]))

(def test-input "
1-3 a: abcde
1-3 b: cdefg
2-9 c: ccccccccc
")

(deftest part1-test
  (testing "part1 finds how many passwords contain a valid count of their
            given letter"
    (is (= (part1 test-input) 2))))

(deftest part2-test
  (testing "part2 finds how many passwords contain the letter at exactly one of the two indices"
    (is (= (part2 test-input) 1))))
