(ns mikera.cljutils.error)

(defmacro error
  "Throws an error with the provided message(s)"
  ([& vals]
    `(throw (mikera.cljutils.Error. (str ~@vals)))))
