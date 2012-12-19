(ns mikera.cljutils.dummy
  (:require [mikera.cljutils.namespace :as n])
  (:require [mikera.cljutils.import :as imp]))

(def dummy-bar 
  "dummy-bar in mikera.cljutils.dummy namespace" 9)

(defn test-stuff []
  
  (meta #'dummy-bar)  ;; gets metadata inluding docstring
  )

;; (n/pull mikera.cljutils.import ['import-foo])