(ns aoc-2020.day13
  (:require [clojure.string :as str]))

(defn- parse-input1
  [input]
  (->> input
       str/trim
       str/split-lines
       ((fn [[timestamp bus-ids]]
          [(Integer/parseInt timestamp)
           (->> bus-ids
                (#(str/split % #","))
                (remove #(= % "x"))
                (map #(Integer/parseInt %)))]))))

(defn- next-departure-for-bus
  [timestamp bus-id]
  (->> bus-id
       (/ timestamp)
       (#(Math/ceil %))
       int
       (* bus-id)))

(defn- next-bus
  ([timestamp bus-ids]
   (next-bus timestamp bus-ids -1 -1))
  ([timestamp bus-ids next-bus-so-far min-so-far]
   (if (empty? bus-ids)
     [next-bus-so-far min-so-far]
     (let [[bus-id & rest] bus-ids
           next-departure (next-departure-for-bus timestamp bus-id)]
       (if (or (neg? min-so-far) (< next-departure min-so-far))
         (next-bus timestamp rest bus-id next-departure)
         (next-bus timestamp rest next-bus-so-far min-so-far))))))

(defn part1
  "Returns the ID of the earliest bus you can take to the airport, multiplied
  by the number of minutes you have to wait."
  [input]
  (let [[timestamp bus-ids] (parse-input1 input)
        [next-bus-id departure-time] (next-bus timestamp bus-ids)]
    (* next-bus-id (- departure-time timestamp))))

(defn id-and-rem
  [offset id-str]
  (if (= id-str "x")
    []
    (let [id (Integer/parseInt id-str)
          remainder (mod (- id offset) id)]
      [id remainder])))

(defn- parse-input2
  [input]
  (->> input
       str/trim
       str/split-lines
       last
       (#(str/split % #","))
       (map-indexed id-and-rem)
       (remove empty?)
       sort
       reverse))

(defn- generate-nums-mod
  ([quotient modulo] (generate-nums-mod quotient modulo 0))
  ([quotient modulo x]
   (lazy-seq
    (cons
     (+ modulo (* x quotient))
     (generate-nums-mod quotient modulo (inc x))))))

(defn- solve-crt
  "Given a sequence of integer two-tuples [x_i, y_i], calculates the lowest
  integer t such that `t = x_i mod y_i` for all [x_i, y_i]. This assumes all
  integers are coprime, so by the Chinese Remainder Theorem such a number t
  must exist."
  ([[[t remainder] & rest]]
   (solve-crt remainder t rest))
  ([x step [[t remainder] & rest]]
   (let [new-x (->> (generate-nums-mod step x)
                    (filter #(= remainder (mod % t)))
                    first)]
     (if (empty? rest)
       new-x
       (solve-crt new-x (* step t) rest)))))

(defn part2
  "Determines the first timestamp t at which the first bus in the input
  leaves at time t, the second leaves at time t+1, the third at t+2, etc.
  Uses sieving search to solve the Chinese Remainder Theorem:
  https://en.wikipedia.org/wiki/Chinese_remainder_theorem#Search_by_sieving"
  [input]
  (->> input
       parse-input2
       solve-crt))
