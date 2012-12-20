(ns mikera.cljutils.test-namespace-pulling
  (:use clojure.test)
  (:use mikera.cljutils.dummy))

;; the dummy namespace contains pulled / imported functions 
;; should able to :use this namespace and get at them!

(deftest test-imported-stuff
  (testing "function"
    (is (= 0 (import-func)))
    (is (= 0 (pulled-func)))
    (is (= 1 (import-func "foo"))))
  (testing "macro"
    (is (:macro (meta #'import-macro)))
    (is (:macro (meta #'pulled-macro)))
    (is (string? (import-macro)))
    (is (string? (pulled-macro)))
    )) 
