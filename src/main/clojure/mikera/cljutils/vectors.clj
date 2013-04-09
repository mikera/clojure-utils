(ns mikera.cljutils.vectors
  (:use mikera.cljutils.error))

(set! *warn-on-reflection* true)
(set! *unchecked-math* true)

(defn find-identical-position
  "Searches a vector for an identical item and returns the index, or -1 if not found.
   Mainly used to pick out the position of a thing within a specific location"
  ^long [item ^clojure.lang.APersistentVector vector]
  (let [c (count vector)]
    (loop [i (int 0)]
      (if (>= i c)
        -1
        (if (identical? item (.nth vector i)) i (recur (inc i)))))))

(defn vector-without
  "Cuts a specific position of a vector"
  [^clojure.lang.PersistentVector vector ^long i]
  (let [c (count vector)
        ni (inc i)]
    (cond 
      (== c 1) (if (== i 0) [] (error "Idex of out range: " i))
      (== ni c) (subvec vector 0 i)
      :else (vec (concat (subvec vector 0 i) (subvec vector ni c))))))

(defn remove-from-vector
  "Removes a specific object from a vector. Throws an error if the object is not found."
  [item ^clojure.lang.APersistentVector vector]
  (let [i (find-identical-position item vector)]
    (when (< i 0) (error "item not found!"))
    (vector-without vector i)))