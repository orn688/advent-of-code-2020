(ns aoc-2020.day01-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc-2020.day01 :refer [part1 part2]]))

(def test-input "
1721
979
366
299
675
1456
")

(deftest part1-test
  (testing "part1 finds the product of the two nums that sum to 2020"
    (is (= (part1 test-input) 514579))))

(deftest part2-test
  (testing "part2 finds the product of the three nums that sum to 2020"
    (is (= (part2 test-input) 241861950))))
