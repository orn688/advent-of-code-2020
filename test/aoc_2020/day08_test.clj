(ns aoc-2020.day08-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc-2020.day08 :refer [part1 part2]]))

(def test-input "
nop +0
acc +1
jmp +4
acc +3
jmp -3
acc -99
acc +1
jmp -4
acc +6
")

(deftest part1-test
  (testing "part1"
    (is (= (part1 test-input) 5))))

;; (deftest part2-test
;;   (testing "part2"
;;     (is (= (part2 test-input) 8))))
