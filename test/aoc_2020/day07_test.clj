(ns aoc-2020.day07-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc-2020.day07 :refer [part1 part2]]))

(def test-input "
light red bags contain 1 bright white bag, 2 muted yellow bags.
dark orange bags contain 3 bright white bags, 4 muted yellow bags.
bright white bags contain 1 shiny gold bag.
muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
dark olive bags contain 3 faded blue bags, 4 dotted black bags.
vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
faded blue bags contain no other bags.
dotted black bags contain no other bags.
")

(def test-input2 "
shiny gold bags contain 2 dark red bags.
dark red bags contain 2 dark orange bags.
dark orange bags contain 2 dark yellow bags.
dark yellow bags contain 2 dark green bags.
dark green bags contain 2 dark blue bags.
dark blue bags contain 2 dark violet bags.
dark violet bags contain no other bags.
")

(deftest part1-test
  (testing "part1"
    (is (= (part1 test-input) 4))))

(deftest part2-test
  (testing "part2 input #1"
    (is (= (part2 test-input) 32)))
  (testing "part2 input #2"
    (is (= (part2 test-input2) 126))))
