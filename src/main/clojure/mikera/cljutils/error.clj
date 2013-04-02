(ns mikera.cljutils.error)


(set! *warn-on-reflection* true)
(set! *unchecked-math* true)

(defmacro error
  "Throws an error with the provided message(s). This is a macro in order to try and ensure the 
   stack trace reports the error at the correct source line number."
  ([& vals]
    `(throw (mikera.cljutils.Error. (str ~@vals)))))

(defmacro error?
  "Returns true if executing body throws an error, false otherwise."
  ([& body]
    `(try 
       ~@body
       false
       (catch Throwable t# 
         true)))) 

(defmacro TODO
  "Throws a TODO error. This ia a useful macro as it is easy to search for in source code, while
   also throwing an error at runtime if encountered."
  ([]
    `(error "TODO: Not yet implemented")))

(defmacro valid 
  "Asserts that an expression is true, throws an error otherwise."
  ([body & msgs]
    `(or ~body
       (error ~@msgs))))

(defmacro try-or 
  "An exception-handling version of the 'or' macro.
   Trys expressions in sequence until one produces a result that is neither false nor an exception.
   Useful for providing a default value in the case of errors."
  ([exp & alternatives]
     (if-let [as (seq alternatives)] 
       `(or (try ~exp (catch Throwable t# (try-or ~@as))))
       exp)))