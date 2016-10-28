(ns mikera.cljutils.test-loops
  (:use clojure.test)
  (:use [mikera.cljutils loops])
  (:require [mikera.cljutils.error :refer [error]]))

(deftest test-for-loop
  (testing "Ten iterations"
    (let [c (atom 0)]
      (for-loop [i 0 (< i 10) (inc i)]
                (swap! c inc))
      (is (= @c 10)))))

(deftest test-doseq-indexed
  (testing "doseq-indexed with small vector"
    (let [c (atom 0)
          ic (atom 0)]
      (doseq-indexed [x [1 2 3 4] i]
                (swap! c + x)
                (swap! ic + i))
      (is (= @c 10))
      (is (= @ic 6)))))

(deftest test-dovec
  (testing "Side effects"
    (let [r (atom 0)
          vs (atom #{})]
      (dovec [v [:a :b :c]]
             (swap! r + i)
             (swap! vs conj v))
      (is (= #{:a :b :c} @vs))
      (is (== 3 @r))))
  (testing "Empty collection"
    (dovec [v []] (error "Shouldn't happen!"))
    (dovec [v nil] (error "Shouldn't happen!"))))

(deftest test-eager-map
  (testing "Eager map"
    (is (= [1 2 3] (eager-map inc [0 1 2])))
    (is (nil? (eager-map inc [])))
    (is (nil? (eager-map inc nil))))) 

(deftest test-dotimes-results
  (is (nil? (dotimes-results [i 0] 6)))
  (is (= [0 1 2 3] (dotimes-results [i 4] i)))
  (is (= [0] (dotimes-results [i 1] i))))

(deftest test-or-loop 
  (is (nil? (or-loop [10] nil)))
  (is (= 10 (or-loop [10] 10)))
  (is (nil? (or-loop [0] 10))))