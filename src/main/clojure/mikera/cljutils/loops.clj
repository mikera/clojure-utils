(ns mikera.cljutils.loops)

(set! *warn-on-reflection* true)
(set! *unchecked-math* true)

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