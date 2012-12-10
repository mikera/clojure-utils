(ns mikera.cljutils.loops)

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
	           (let [new-value# (do ~@code)]
	             (recur ~change new-value#))
	           value#)))))