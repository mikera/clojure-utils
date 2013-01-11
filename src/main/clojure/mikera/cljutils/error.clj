(ns mikera.cljutils.error)

(defmacro error
  "Throws an error with the provided message(s)"
  ([& vals]
    `(throw (mikera.cljutils.Error. (str ~@vals)))))

(defmacro TODO
  "Throws a TODO error"
  ([]
    `(error "TODO: Not yet implemented")))