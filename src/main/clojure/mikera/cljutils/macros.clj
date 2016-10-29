(ns mikera.cljutils.macros)

(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)

; TODO: figure out why not working wiith Clojure 1.6.0?
;(when (and (>= (:major *clojure-version*) 1) (< (:minor *clojure-version*) 5))
;  (defmacro as->
;     "Binds name to expr, evaluates the first form in the lexical context
;      of that binding, then binds name to that result, repeating for each
;      successive form, returning the result of the last form."
;     [expr name & forms]
;    `(let [~name ~expr
;           ~@(interleave (repeat name) forms)]
;       ~name)))

(defmacro and-as-> 
  "Similar to as->, except wraps an implicit and around each step: returns nil/false
   as soon as any expression returns nil/false."
  ([expr sym & body]
    (if (seq body) 
      `(as-> ~expr ~sym
         ~(first body)
         ~@(map (fn [b] `(and ~sym ~b)) (next body)))
      expr)))