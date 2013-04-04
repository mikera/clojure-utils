(ns mikera.cljutils.arrays
  (:use mikera.cljutils.loops))

(set! *warn-on-reflection* true)
(set! *unchecked-math* true)



(defn copy-long-array 
  "Returns a copy of a long array"
  (^longs [^longs arr]
    (java.util.Arrays/copyOf arr (int (alength arr))))) 

(defn long-range 
  "Returns a range of longs in a long[] array"
  ([end]
    (let [end (int end)
          ^longs arr (long-array end)]
      (dotimes [i end]
        (aset arr i (long i)))
      arr))) 

(defn long-array-of 
  "Creates a long array with the specified values."
  ([] (long-array 0))
  ([a] 
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
