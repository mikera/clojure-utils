(ns mikera.cljutils.test-arrays
  (:use clojure.test)
  (:use [mikera.cljutils arrays]))

(deftest test-types-array
  (is (typed-array [1 2 3])))


