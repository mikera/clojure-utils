(ns mikera.cljutils.benchmarks
  (:use [mikera.cljutils loops]) 
  (:require [criterium.core :as c]))

(defn benchmarks []
  ;; eager-map with long sequence
  (let []
    (c/quick-bench (eager-map inc (range 1000))))
  ;; 77 us
  
  (let []
    (c/quick-bench (doall (map inc (range 1000)))))
  ;; 79 us
  
  ;; eager-map with short sequence
  (let []
    (c/quick-bench (eager-map inc (range 10))))
  ;; 877 ns
  
  (let []
    (c/quick-bench (doall (map inc (range 10)))))
  ;; 1159 ns


) 