(ns aoc-2020.day09-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc-2020.day09 :refer [part1 part2]]))

(def test-input "
35
20
15
25
47
40
62
55
65
95
102
117
150
182
127
219
299
277
309
576
")

(deftest part1-test
  (testing "part1"
    (is (= (part1 test-input 5 5) 127))))