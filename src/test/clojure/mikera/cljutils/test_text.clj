(ns mikera.cljutils.test-text
  (:use clojure.test)
  (:use [mikera.cljutils text]))

(deftest test-dt
  (testing "dotted truncation"
    (is (= "a..." (dotted-truncate 4 "aardvark")))
    (is (= "aa.." (dotted-truncate 4 "aardvark" :num-dots 2)))
    (is (= "aardvark" (dotted-truncate 40 "aardvark" :num-dots 2)))))
