(ns aoc-2020.day11-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc-2020.day11 :refer [part1 part2]]))

(def test-input "
L.LL.LL.LL
LLLLLLL.LL
L.L.L..L..
LLLL.LL.LL
L.LL.LL.LL
L.LLLLL.LL
..L.L.....
LLLLLLLLLL
L.LLLLLL.L
L.LLLLL.LL
")

;; (deftest part1-test
;;   (testing "part1"
;;     (is (= (part1 test-input) 37))))

(deftest part2-test
  (testing "part2"
    (is (= (part2 test-input) 26))))