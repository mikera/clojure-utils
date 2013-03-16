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

(deftest test-and*
  (testing "AND"
    (is (and* 1 2))
    (is (and* 1 1 1 1))
    (is (and* 1))
    (is (and*))
    (is (not (and* nil)))
    (is (not (and* 1 1 1 1 1 1 nil 1 1 1 1)))))
