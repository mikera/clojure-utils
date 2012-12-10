(ns mikera.cljutils.test-loops
  (:use clojure.test)
  (:use [mikera.cljutils loops]))

(deftest test-for-loop
  (testing "ten iterations"
    (let [c (atom 0)]
      (for-loop [i 0 (< i 10) (inc i)]
                (swap! c inc))
      (is (= @c 10)))))
