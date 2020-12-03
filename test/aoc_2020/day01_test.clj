(ns aoc-2020.day01-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc-2020.day01 :refer [part1-impl part2-impl]]))

(deftest part1-test
  (testing "part1 finds the product of the two nums that sum to 2020"
    (is (=
         (part1-impl [1721 979 366 299 675 1456])
         514579))))

(deftest part2-test
  (testing "part2 finds the product of the three nums that sum to 2020"
    (is (=
         (part2-impl [1721 979 366 299 675 1456])
         241861950))))
