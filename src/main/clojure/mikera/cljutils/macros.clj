(ns mikera.cljutils.macros)


(set! *warn-on-reflection* true)
(set! *unchecked-math* true)


(defmacro and-as-> 
  "Similar to as->, except wraps an implicit and around ech step: returns nil/false
   as soon as any expression returns nil/false."
  ([expr sym & body]
  `(as-> ~expr ~sym
     ~@(map (fn [b] `(and ~sym ~b)) (butlast body))
     ~(last body))))