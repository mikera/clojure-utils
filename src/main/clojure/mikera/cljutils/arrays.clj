(ns mikera.cljutils.arrays
  "Utility functions for dealing with Java arrays"
  (:use mikera.cljutils.loops)
  (:import java.lang.reflect.Array)
  (:refer-clojure :exclude [aget]))

(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)

(defn array?
  "Returns true if the argument is a Java array, false otherwise"
  ([a]
    (.isArray (.getClass ^Object a))))

(defn aget*
  "Returns an element from a Java array, avoiding the usual reflection warning."
  ([a index]
    (Array/get a (int index))))

(defn array-type
  "Returns the type of elements in a Java array. Returns nil if the argument is not an array."
  ([a]
    (.getComponentType (.getClass ^Object a))))

(defn typed-array
  "Creates a typed Java array of a collection of objects. 

   If class is not provided, uses the class of the first object to determine the type of the array."
  ([^Class class objects]
    (let [cnt (count objects)
          ^objects arr (make-array class cnt)]
      (doseq-indexed [o objects i]
        (aset arr (int i) o))
      arr))
  ([objects]
    (typed-array (.getClass ^Object (first objects)) objects)))

(defn copy-long-array 
  "Returns a copy of a long array"
  (^longs [^longs arr]
    (java.util.Arrays/copyOf arr (int (alength arr))))) 

(defn long-range 
  "Returns a range of longs in a long[] array"
  (^longs [end]
    (let [end (int end)
          ^longs arr (long-array end)]
      (dotimes [i end]
        (aset arr i (long i)))
      arr))) 

(defn long-array-of 
  "Creates a long array with the specified values."
  (^longs [] (long-array 0))
  (^longs [a] 
    (let [arr (long-array 1)]
      (aset arr 0 (long a))
      arr))
  ([a b] 
    (let [arr (long-array 2)]
      (aset arr 0 (long a))
      (aset arr 1 (long b))
      arr))
  ([a b & more] 
    (let [arr (long-array (+ 2 (count more)))]
      (aset arr 0 (long a))
      (aset arr 1 (long b))
      (doseq-indexed [x more i] (aset arr (+ 2 i) (long x))) 
      arr)))

(defn object-array-of 
  "Creates a long array with the specified values."
  ([] (object-array 0))
  ([a] 
    (let [arr (object-array 1)]
      (aset arr 0 a)
      arr))
  ([a b] 
    (let [arr (object-array 2)]
      (aset arr 0 a)
      (aset arr 1 b)
      arr))
  ([a b & more] 
    (let [arr (object-array (+ 2 (count more)))]
      (aset arr 0 a)
      (aset arr 1 b)
      (doseq-indexed [x more i] (aset arr (+ 2 i) x)) 
      arr)))
