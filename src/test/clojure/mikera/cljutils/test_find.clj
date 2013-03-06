(ns mikera.cljutils.test-find
  (:use clojure.test)
  (:use [mikera.cljutils find]))

(deftest test-find-first
  (testing "find a number"
    (is (= 3 (find-first number? [:foo :bar 3 :baz 4 5 :bif])))
    (is (= 3 (find-first number? '(:foo :bar 3 :baz 4 5 :bif))))))

(deftest test-find-index
  (testing "find a number"
    (is (== 2 (find-index number? [:foo :bar 3 :baz 4 5 :bif])))
    (is (== 2 (find-index number? '(:foo :bar 3 :baz 4 5 :bif))))
    (is (== -1 (find-index number? [:foo :bar :bif])))))

(deftest test-find-position
  (is (== 2 (find-position 4 [0 2 4 6 8])))
  (is (== -1 (find-position 5 [0 2 4 6 8]))))

(deftest test-find-position-in-vector
  (is (== 2 (find-position-in-vector 4 [0 2 4 6 8])))
  (is (== -1 (find-position-in-vector 5 [0 2 4 6 8]))))

(deftest test-indexed
  (is (indexed? [1 2 3 4]))
  (is (not (indexed? '(1 2 3 4))))) 

