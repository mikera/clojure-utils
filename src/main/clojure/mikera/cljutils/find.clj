(ns mikera.cljutils.find
  "Namespace for utility functions that find values in collections."
  (:require [mikera.cljutils.arrays :as arrays]))

(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)

(ns-unmap *ns* 'indexed?)

(defn indexed?
  ([coll]
    (instance? clojure.lang.Indexed coll))) 

(defn find-first 
   "Searches a collection and returns the first item for which pred is true, or nil if not found.

   An optional start position may be provided, which defaults to zero (starts searching from begining 
   of the collection).
   
   Like 'clojure.core/some', except it returns the value from the collection (rather than the result of 
   applying the predicate to the value). This is often more useful.
   
   Note that it is possible to find and return a nil value if the collection contains nils."
   ([pred coll]
     (find-first pred coll 0))
   ([pred coll ^long start]
     (cond 
       (indexed? coll)
         (let [c (count coll)
               ^clojure.lang.Indexed icoll coll]
           (loop [i start]
             (if (< i c) 
               (let [v (.nth icoll i)]
                 (if (pred v) v (recur (inc i))))
               nil)))
       (arrays/array? coll)
         (let [c (count coll)]
           (loop [i start]
             (if (< i c) 
               (let [v (arrays/aget* coll (int i))]
                 (if (pred v) v (recur (inc i))))
               nil)))
       :else ;; default to treating as sequence
         (loop [s (drop start coll)] 
           (when s  
             (let [v (first s)]
               (if (pred v)
                 v
                 (recur (next s)))))))))

(defn find-index
  "Searches a collection and returns the index of the first item for which pred is true.
   
   Returns nil if not found"
  ([pred coll]
    (find-index pred coll 0))
  ([pred coll ^long start]
    (if (indexed? coll)
      (let [c (count coll)]
        (loop [i start]
          (if (< i c) 
            (if (pred (nth coll i)) i (recur (inc i)))
            nil)))
      (loop [i start s (drop start coll)]
        (when s
          (if (pred (first s)) i (recur (inc i) (next s)))))))) 

(defn find-position 
  "Searches a collection and returns the (long) index of the item's position.
   An optional start position may be provided (defaults to 0)

   Returns nil if not found"
  ([item coll] 
    (find-position item coll 0))
  ([item coll ^long start] 
    (cond 
       (indexed? coll)
         (let [c (count coll)
               ^clojure.lang.Indexed icoll coll]
           (loop [i start]
             (if (< i c) 
               (let [v (.nth icoll i)]
                 (if (= item v) i (recur (inc i))))
               nil)))
       (arrays/array? coll)
         (let [c (count coll)]
           (loop [i start]
             (if (< i c) 
               (let [v (arrays/aget* coll (int i))]
                 (if (= item v) i (recur (inc i))))
               nil)))
       :else ;; default to treating as sequence
         (loop [i start 
                s (seq (drop start coll))] 
           (when s  
             (let [v (first s)]
               (if (= item v)
                 i
                 (recur (inc i) (next s)))))))))

(defn eager-filter 
  "Filters a collection eagerly, returning a sequence.
   Like clojure.core/filterv except the result is an ISeq (or nil if empty)"
  ([pred coll]
    (seq (filterv pred coll))))
