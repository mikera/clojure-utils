(ns mikera.cljutils.test-logic
  (:use clojure.test)
  (:use [mikera.cljutils logic]))

(deftest test-xor
  (testing "XOR"
    (is (xor 1 nil))
    (is (xor 1 nil 1 1))
    (is (xor 1))
    (is (not (xor)))
    (is (not (xor true false false true false false true true)))))
