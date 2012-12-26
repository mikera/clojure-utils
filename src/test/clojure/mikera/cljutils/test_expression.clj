(ns mikera.cljutils.test-expression
  (:use clojure.test)
  (:use [mikera.cljutils expression]))

(deftest test-ex-info
  (testing "primitive maths"
    (let [e (expression-info '(+ 1.0 2.0))]
      (is (= (Double/TYPE) (:class e)))
      (is (:primitive? e)))))
