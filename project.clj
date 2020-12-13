(defproject aoc-2020 "0.1.0-SNAPSHOT"
  :description "Advent of Code 2020 solutions in Clojure"
  :url "https://github.com/orn688/advent-of-code-2020"
  :plugins [[lein-cljfmt "0.7.0"]]
  :dependencies [[clj-http "3.10.3"]
                 [org.clojure/clojure "1.10.1"]
                 [org.clojure/data.finger-tree "0.0.3"]]
  :repl-options {:init-ns aoc-2020.core}
  :main aoc-2020.core)
