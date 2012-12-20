(ns mikera.cljutils.test-namespace
  (:use clojure.test)
  (:require [mikera.cljutils.namespace :as n])
  (:require [mikera.cljutils.dummy]))

(n/clone 'mikera.cljutils.dummy)

(deftest test-with-ns
  (testing "use existing ns"
    (is (= 9 (n/with-ns 'mikera.cljutils.dummy dummy-bar))))
  (testing "cloned within ns"
    (is (= 9 dummy-bar))))

(deftest test-namespacing
  (testing "objects in their original namespace"
    (is (= "mikera.cljutils.dummy" (str (.ns (var mikera.cljutils.dummy/dummy-bar)))))
    (is (= "mikera.cljutils.dummy" (str (.ns (ns-resolve 'mikera.cljutils.dummy 'dummy-bar))))))
  (testing "pulled objects exist in new namespace"
    (is (= "mikera.cljutils.dummy" (str (.ns (var mikera.cljutils.dummy/import-foo)))))
    (is (= "mikera.cljutils.dummy" (str (.ns (var mikera.cljutils.dummy/import-func)))))
    (is (= "mikera.cljutils.dummy" (str (.ns (var mikera.cljutils.dummy/import-macro))))))
  (testing "refered objects stay in original namespace var"
    (is (= "mikera.cljutils.import" (str (.ns (ns-resolve 'mikera.cljutils.dummy 'clone-foo))))))) 

(deftest test-imported-stuff
  (testing "function"
    (is (= 0 (mikera.cljutils.dummy/import-func)))
    (is (= 1 (mikera.cljutils.dummy/import-func "foo"))))
  (testing "macro"
    (is (string? (mikera.cljutils.dummy/import-macro)))
    (is (:macro (meta #'mikera.cljutils.dummy/import-macro))))) 

(deftest test-clones-imports
  (is (ns-interns *ns*) 'dummy-bar)
  (is (ns-interns 'mikera.cljutils.dummy) 'dummy-bar)) 

(deftest test-with-ns
  (testing "dummy ns"
    (is (= 10 (n/with-ns mikera.cljutils.dummy import-foo))))
  (testing "dummy ns function"
    (is (= 0 (n/with-ns mikera.cljutils.dummy (import-func)))))) 

(deftest test-with-temp-ns
  (testing "temp env"
    (is (= 2 (n/with-temp-ns (+ 1 1)))))) 


(deftest test-with-environment
  (testing "dummy env"
    (is (= 0 (n/with-environment [mikera.cljutils.dummy] (import-func)))))) 
