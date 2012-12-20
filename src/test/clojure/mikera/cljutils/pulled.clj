(ns mikera.cljutils.pulled) 

(def pulled-foo 
  "Docstring for pulled-foo in mikera.cljutils.pulled namespace" 10)

(defn pulled-func
  "Docstring for pulled-func in mikera.cljutils.pulled namespace" 
  ([]
    0)
  ([x]
    1))

(defmacro pulled-macro
  "Docstring for pulled-macro in mikera.cljutils.pulled namespace" 
  ([]
    "pulled-macro called with zero args")
  ([x]
    "pulled-macro called with one arg"))