(ns mikera.cljutils.test-loops
  (:use clojure.test)
  (:use [mikera.cljutils loops]))

(deftest test-for-loop
  (testing "ten iterations"
    (let [c (atom 0)]
      (for-loop [i 0 (< i 10) (inc i)]
                (swap! c inc))
      (is (= @c 10)))))

(deftest test-doseq-indexed
  (testing "ten iterations"
    (let [c (atom 0)
          ic (atom 0)]
      (doseq-indexed [x [1 2 3 4] i]
                (swap! c + x)
                (swap! ic + i))
      (is (= @c 10))
      (is (= @ic 6)))))
