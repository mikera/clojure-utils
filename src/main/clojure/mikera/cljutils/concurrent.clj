(ns mikera.cljutils.concurrent)

;; Namespace for concurrency utils

(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)

(defmacro plet [[s1 v1 & bindings] & body]
  "Equivalent of let that evaluates bindings in parallel, using a separate thread for each. 

   The body is executed after all bindings are computed."
  (let [bindings (partition 2 bindings)
        syms (map first bindings)
        vals (map second bindings)]
    `(let [~@(mapcat (fn [var expr] `[~var (future ~expr)]) syms vals)
           ~s1 ~v1
           ~@(mapcat (fn [sym] `[~sym (deref ~sym)]) syms)]
       ~@body)))