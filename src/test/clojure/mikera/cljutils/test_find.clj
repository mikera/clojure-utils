(ns mikera.cljutils.test-find
  (:use clojure.test)
  (:use [mikera.cljutils find]))

(deftest test-find-first
  (testing "find a numner"
    (is (= 3 (find-first number? [:foo :bar 3 :baz 4 5 :bif])))))

