(ns aoc-2020.day03-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc-2020.day03 :refer [part1 part2]]))

(def test-input "
..##.......
#...#...#..
.#....#..#.
..#.#...#.#
.#...##..#.
..#.##.....
.#.#.#....#
.#........#
#.##...#...
#...##....#
.#..#...#.#
")

(deftest part1-test
  (testing ""
    (is (= (part1 test-input) 7))))

(deftest part2-test
  (testing ""
    (is (= (part2 test-input) 336))))
