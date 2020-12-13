(ns aoc-2020.day12
  (:require [clojure.string :as str]))

(defn- parse-line
  [line]
  [(first line)
   (Integer/parseInt (subs line 1))])

(defn- parse-input
  [input]
  (->> input
       str/trim
       str/split-lines
       (map parse-line)))

(def get-multi (comp vals select-keys))

(defn- apply-instruction1
  [pos [action amount]]
  (let [[x y direction] (get-multi pos [:x :y :direction])]
    (case action
      \N (assoc pos :y (+ y amount))
      \S (assoc pos :y (- y amount))
      \E (assoc pos :x (+ x amount))
      \W (assoc pos :x (- x amount))
      \R (assoc pos :direction (mod (+ direction amount) 360))
      \L (assoc pos :direction (mod (- direction amount) 360))
      \F (case direction
           0 (apply-instruction1 pos [\N amount])
           90 (apply-instruction1 pos [\E amount])
           180 (apply-instruction1 pos [\S amount])
           270 (apply-instruction1 pos [\W amount])))))

(defn- execute-instructions
  [position instructions apply-func]
  (if (empty? instructions)
    position
    (let [[current & rest] instructions]
      (execute-instructions (apply-func position current) rest apply-func))))

(defn- abs
  [n]
  (if (pos? n) n (- n)))

(defn- manhattan-distance
  [p1 p2]
  (+
   (abs (- (get p1 :x) (get p2 :x)))
   (abs (- (get p1 :y) (get p2 :y)))))

(defn part1
  "Finds the Manhattan distance from the ship's starting position to it's
  ending position after following all the navigation instructions."
  [input]
  (let [orig-position {:x 0, :y 0, :direction 90}
        instructions (parse-input input)
        final-position (execute-instructions orig-position instructions apply-instruction1)]
    (manhattan-distance orig-position final-position)))

(defn- rotate-waypoint
  [pos action amount]
  (if (zero? amount)
    pos
    (let [[wx wy] (get-multi pos [:wx :wy])
          nextpos (case action
                    \R {:wx wy, :wy (- wx)}
                    \L {:wx (- wy), :wy wx})]
      (rotate-waypoint (merge pos nextpos) action (- amount 90)))))

(defn- apply-instruction2
  [pos [action amount]]
  (let [[x y wx wy] (get-multi pos [:x :y :wx :wy])]
    (case action
      \N (assoc pos :wy (+ wy amount))
      \S (assoc pos :wy (- wy amount))
      \E (assoc pos :wx (+ wx amount))
      \W (assoc pos :wx (- wx amount))
      \F (merge pos {:x (+ x (* amount wx)), :y (+ y (* amount wy))})
      (rotate-waypoint pos action amount))))

(defn part2
  "Finds the Manhattan distance from the ship's starting position to it's
  ending position after following all the navigation instructions,
  interpreted slightly differently (all actions move a waypoint relative to
  the ship, except F, which moves the ship to the waypoint)."
  [input]
  ; wx and wy represent the east/west and north/south distance from the ship to
  ; the waypoint, respectively.
  (let [orig-position {:x 0, :y 0, :wx 10, :wy 1}
        instructions (parse-input input)
        final-position (execute-instructions orig-position instructions apply-instruction2)]
    (manhattan-distance orig-position final-position)))
