(ns aoc-2020.day10-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc-2020.day10 :refer [part1 part2]]))

(def test-input "
16
10
15
5
1
11
7
19
6
12
4
")

(def test-input2 "
28
33
18
42
31
14
46
20
48
47
24
23
49
45
19
38
39
11
1
32
25
35
8
17
7
9
4
2
34
10
3
")

(deftest part1-test
  (testing "part1"
    (is (= (part1 test-input) 35)))
  (testing "part1-input2"
    (is (= (part1 test-input2) 220))))

(deftest part2-test
  (testing "part2"
    (is (= (part2 test-input) 8)))
  (testing "part2-input2"
    (is (= (part2 test-input2) 19208))))