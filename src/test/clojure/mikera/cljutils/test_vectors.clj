(ns mikera.cljutils.test-vectors
  (:use clojure.test)
  (:use [mikera.cljutils vectors]))

(deftest test-without
  (is (= [1 3] (vector-without [1 2 3] 1)))
  (is (= [1 2] (vector-without [1 2 3] 2)))
  (is (= [] (vector-without [1] 0))))


(deftest test-vector-without
  (let [a 1 b 2 c 3]
    (= [a b] (vector-without [a b c] 2))
    (= [a c] (vector-without [a b c] 1))
    (= [b c] (vector-without [a b c] 0))
    (= [] (vector-without [a] 0))))
