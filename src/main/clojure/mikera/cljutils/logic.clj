(ns mikera.cljutils.logic)

(defmacro nand
  ([] false)
  ([x] `(not ~x))
  ([x y] `(not (and ~x ~y)))
  ([x y & more] `(not (and ~x ~y ~@more))))

(defn xor 
  "Returns the logical xor of a set of values, considered as booleans"
  (^Boolean [] false)
  (^Boolean [x] (boolean x))
  (^Boolean [x y] (if x (not y) (boolean y)))
  (^Boolean [x y & more]
    (loop [p (xor x y) ss (seq more)]
      (if ss
        (recur (if (first ss) (not p) p) (next ss))
        p))))

(defn and* 
  "Returns the logical and of a set of values, considered as booleans"
  (^Boolean [] true)
  (^Boolean [x] (boolean x))
  (^Boolean [x y] (if x (boolean y) false))
  (^Boolean [x y & more]
    (loop [p (and* x y) ss (seq more)]
      (if p
        (if ss (recur (first ss) (next ss)) true)
        false))))

(defn or* 
  "Returns the logical or of a set of values, considered as booleans"
  (^Boolean [] false)
  (^Boolean [x] (boolean x))
  (^Boolean [x y] (if x true (boolean y)))
  (^Boolean [x y & more]
    (loop [p (or* x y) ss (seq more)]
      (if p
        true
        (if ss (recur (first ss) (next ss)) false)))))