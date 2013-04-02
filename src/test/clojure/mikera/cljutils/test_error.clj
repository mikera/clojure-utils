(ns mikera.cljutils.test-error
  (:use clojure.test)
  (:use [mikera.cljutils error]))

(deftest test-error
  (testing "Error"
    (is (thrown? Throwable (error "foo")))))

(deftest test-error?
  (testing "error?"
    (is (error? (error "foo")))
    (is (not (error? (+ 1 2))))))

