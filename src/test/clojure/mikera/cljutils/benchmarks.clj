(ns mikera.cljutils.benchmarks
  (:use [mikera.cljutils loops]) 
  (:require [criterium.core :as c]))

(defn benchmarks []
  ;; direct vectorz add
  (let []
    (c/quick-bench (eager-map inc (range 1000))))
  ;; 77 us
  
  (let []
    (c/quick-bench (doall (map inc (range 1000)))))
  ;; 79 us


) 