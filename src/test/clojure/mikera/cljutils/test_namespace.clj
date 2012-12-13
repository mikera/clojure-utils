(ns mikera.cljutils.test-namespace
  (:use clojure.test)
  (:use [mikera.cljutils namespace]))

(deftest test-for-loop
  (testing "create ns"
    (create-ns 'namespace-test.foo)
    (with-ns 'namespace-test.foo
      (def a :foo))))

