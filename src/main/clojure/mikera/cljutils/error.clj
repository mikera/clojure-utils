(ns mikera.cljutils.error)

(defmacro error
  "Throws an error with the provided message(s)"
  ([& vals]
    `(throw (mikera.cljutils.Error. (str ~@vals)))))

(defmacro TODO
  "Throws a TODO error. This ia a useful macro as it is easy to search for in source code, while
   also throwing an error at runtime if encountered."
  ([]
    `(error "TODO: Not yet implemented")))