(ns aoc-2020.day13
  (:require [clojure.string :as str]))

(defn- parse-input
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
  [input]
  (let [[timestamp bus-ids] (parse-input input)
        [next-bus-id departure-time] (next-bus timestamp bus-ids)]
    (* next-bus-id (- departure-time timestamp))))

(defn part2
  [_])
