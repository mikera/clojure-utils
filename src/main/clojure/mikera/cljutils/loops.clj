(ns mikera.cljutils.loops
  (:use mikera.cljutils.error)
  (:import [mikera.cljutils FastSeq]))

(set! *warn-on-reflection* true)
(set! *unchecked-math* true)

(defmacro loop-results 
  "Like loop, but returns a seq of all loop results rather than the final value."
  ;; is this a good idea? often will have a "nil" at end of list at loop termination....
  [[bindings] body]
  (or (vector? bindings) (error "Loop bindings must be a vector"))
  (or (even? (count bindings) (error "Loop bindings require an even number of forms")))
  (TODO))

(defmacro for-loop 
  "Runs an imperative for loop, binding sym to init, running code as long as check is true, 
  updating sym according to change"
  ([[sym init check change :as params] & code]
	  (cond
	    (not (vector? params)) 
	      (throw (Error. "Binding form must be a vector for for-loop"))
	    (not= 4 (count params)) 
	      (throw (Error. "Binding form must have exactly 4 arguments in for-loop"))
	    :default
	      `(loop [~sym ~init value# nil]
	         (if ~check
	           (recur ~change (do ~@code))
	           value#)))))

(defmacro doseq-indexed 
  "loops over a set of values, binding index-sym to the 0-based index of each value"
  ([[val-sym values index-sym] & code]
  `(loop [vals# (seq ~values) 
          ~index-sym (long 0)]
     (if vals#
       (let [~val-sym (first vals#)]
             ~@code
             (recur (next vals#) (inc ~index-sym)))
       nil))))

(defmacro dovec 
  "Performs an operation for each element of an indexed vector. Binds i to the index at each element."
  ([[sym v :as bindings] & body]
    (when-not (vector? bindings) (error "dovec requires a binding vector"))
    (when-not (symbol? sym) (error "dovec binding requires a symbol"))
    `(let [v# ~v
           c# (count v#)]
       (loop [~'i 0]
         (if (< ~'i c#)
           (let [~sym (v# ~'i)] ~@body)
           nil)))))

(defn eager-map
  "Like map, but eager runs the function over the whole sequence"
  ([f xs]
    (if-let [xs (seq xs)]
      (let [result (FastSeq. (f (first xs)))]
        (loop [xs xs
               r result]
          (if-let [nxs (next xs)]
            (do (set! (._next r) (FastSeq. (f (first nxs))))
              (recur nxs (._next r)))
            result)))))) 

(defmacro or-loop 
  "Evaluates body repeatedly up to a given number of times, until it returns a truthy value. 
   Returns nil if a truthy value is not found."
  ([[max-times :as bindings] & body]
    (when-not (vector? bindings) (error "or-loop requires a binding vector"))
    `(loop [tries# ~max-times]
       (if (<= tries# 0) nil
         (if-let [res# (do ~@body)]
           res#
           (recur (dec tries#)))))))

(defmacro dotimes-results 
  "Like dotimes, but retuns a seq of the results of each iteration."
  ([[sym n :as binding] & body]
    (or (vector? binding) (error "Must have a binding vector!"))
    (or (== 2 (count binding)) (error "Binding vector must have 2 elements!"))
    (or (symbol? sym) (error "First binding argument must be a symbol."))
    `(let [n# (long ~n)]
       (loop [~sym 0 ^FastSeq fs# nil ^FastSeq hfs# nil]
         (if (< ~sym n#)
           (let [^FastSeq nfs# (FastSeq. (do ~@body) nil)]
             (when hfs# (set! (. hfs# ~'_next) nfs#)) 
             (recur (unchecked-inc ~sym) (or fs# nfs#) nfs#))
         fs#)))))