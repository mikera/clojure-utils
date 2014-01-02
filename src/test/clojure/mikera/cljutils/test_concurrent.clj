(ns mikera.cljutils.test-concurrent
  (:use clojure.test)
  (:use mikera.cljutils.concurrent))

(deftest test-effects
  (let [a (atom 0)]
    (plet [t1 (swap! a inc)
           t2 (swap! a inc)
           t3 (swap! a inc)]
      (is (== 3 @a)))))

