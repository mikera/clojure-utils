(ns mikera.cljutils.import) 

(def import-foo 
  "Docstring for import-foo in mikera.cljutils.import namespace" 10)

(defn import-func
  "Docstring for import-func in mikera.cljutils.import namespace" 
  ([]
    "import-func called with zero args")
  ([x]
    "import-func called with one arg"))

(defmacro import-macro
  "Docstring for import-macro in mikera.cljutils.import namespace" 
  ([]
    "import-macro called with zero args")
  ([x]
    "import-macro called with one arg"))