(ns aoc-2020.day08
  (:require [clojure.string :as str]))

(defn- parse-line
  [line]
  (let [parts (str/split line #" ")
        instruction (first parts)
        value (Integer/parseInt (second parts))]
    [instruction value]))

(defn- parse-input
  [input]
  (->> input
       str/trim
       str/split-lines
       (map parse-line)
       vec))

(defn- execute
  ([program] (execute program 0 0 #{}))
  ([program index acc visited]
   (cond
     (= index (count program)) [true acc] ; Program terminates.
     (contains? visited index) [false acc] ; Program does not terminate.
     :else (let [item (nth program index)
                 instruction (first item)
                 value (second item)
                 new-visited (conj visited index)]
             (case instruction
               "nop" (execute program (inc index) acc new-visited)
               "acc" (execute program (inc index) (+ acc value) new-visited)
               "jmp" (execute program (+ index value) acc new-visited))))))

(defn part1
  "Returns the value of the accumulator variable immediately before the first
  occurrence of the processor executing an already-executed instruction."
  [input]
  (->> input
       parse-input
       execute
       second))

(defn- acc-value
  [program]
  (let [[completes acc] (execute program)]
    (if completes
      acc
      nil)))

(defn- check-index
  [program [index [instruction value]]]
  (case instruction
    "nop" (acc-value (assoc program index ["jmp" value]))
    "jmp" (acc-value (assoc program index ["nop" value]))
    "acc" nil))

(defn- check-indices
  "For each index of the program, determine whether changing the instruction
  at that index from a 'jmp' to a 'nop' or vice versa will cause the program
  to terminate. If so, return the accumulator value at the end of the
  resulting program. Otherwise, return nil."
  [program]
  (->> program
       (map-indexed vector)
       (map #(check-index program %))))

(defn part2
  "Returns the value of the accumulator variable at the end of the program,
  for the only modified version of the program that causes it to complete
  rather than entering an infinite loop. The only allowed modifications are
  exchanging a 'nop' for a 'jmp' and vice versa."
  [input]
  (->> input
       parse-input
       check-indices
       (remove nil?)
       first))