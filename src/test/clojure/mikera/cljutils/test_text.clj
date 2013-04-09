(ns mikera.cljutils.test-text
  (:use clojure.test)
  (:use [mikera.cljutils text]))

(deftest test-text-padding
  (testing "dotted truncation"
    (is (= "a..." (truncate-dotted "aardvark" 4)))
    (is (= "aa.." (truncate-dotted "aardvark" 4 :num-dots 2)))
    (is (= "aardvark" (truncate-dotted "aardvark" 40 :num-dots 2)))
    (is (= "" (truncate-dotted "aardvark" 0 :num-dots 2))))
  (testing "right padding"
    (is (= "a  " (pad-right "a" 3)))
    (is (= "a--" (pad-right "a" 3 \-)))
    (is (= "armadillo" (pad-right "armadillo" 3))))
  (testing "left padding"
    (is (= "  a" (pad-left "a" 3)))
    (is (= "--a" (pad-left "a" 3 \-)))
    (is (= "armadillo" (pad-left "armadillo" 3))))
  (testing "truncate"
    (is (= "" (truncate "hello" 0)))
    (is (= "he" (truncate "hello" 2)))
    (is (= "hello" (truncate "hello" 20)))))
