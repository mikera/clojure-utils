(ns mikera.cljutils.logic)

(defn xor 
  "Returns the logical xor of a set of values, considered as booleans"
  ([] false)
  ([x] (boolean x))
  ([x y] (if x (not y) (boolean y)))
  ([x y & more]
    (loop [p (xor x y) ss (seq more)]
      (if ss
        (recur (if (first ss) (not p) p) (next ss))
        p))))