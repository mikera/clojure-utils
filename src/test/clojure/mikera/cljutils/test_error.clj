(ns mikera.cljutils.test-error
  (:use clojure.test)
  (:use [mikera.cljutils error]))

(deftest test-error
  (testing "Error"
    (is (thrown? Throwable (error "foo")))))
