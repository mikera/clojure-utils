(ns mikera.cljutils.test-core
  (:use clojure.test)
  (:use [mikera.cljutils core]))

(defn kws [& {:keys []
              :as options}]
  options)

(deftest test-apply-kw
  (is (= {:a 2} (apply-kw kws {:a 2}))))


