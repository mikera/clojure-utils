(ns mikera.cljutils.test-namespace-pulling
  (:use clojure.test)
  (:use mikera.cljutils.dummy)
  (:require [mikera.cljutils.namespace :as n]))

(deftest test-imported-stuff
  (testing "function"
    (is (= 0 (import-func)))
    (is (= 0 (pulled-func)))
    (is (= 1 (import-func "foo"))))
  (testing "macro"
    (is (string? (import-macro)))
    (is (:macro (meta #'import-macro))))) 
