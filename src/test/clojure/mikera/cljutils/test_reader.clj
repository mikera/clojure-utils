(ns mikera.cljutils.test-reader
  (:use clojure.test)
  (:use mikera.cljutils.reader))

(deftest test-read
  (is (= [1 2] (read (string-reader "[1 2]")))))

