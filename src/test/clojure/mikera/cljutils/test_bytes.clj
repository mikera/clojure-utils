(ns mikera.cljutils.test-bytes
  (:use clojure.test)
  (:use mikera.cljutils.bytes))

(deftest test-join
  (is (= (seq (unchecked-byte-array [0 1 2 3 0 1]))
         (seq (join-bytes (unchecked-byte-array (range 4)) (unchecked-byte-array (range 2)))))))