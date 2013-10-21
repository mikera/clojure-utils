(ns mikera.cljutils.bytes
  (:refer-clojure :exclude [reverse]))

(set! *warn-on-reflection* true)
(set! *unchecked-math* true)

(def BYTE-ARRAY-CLASS (Class/forName "[B"))

(defn reverse 
  (^bytes [^bytes bs]
    (let [n (alength bs)
          res (byte-array n)]
      (dotimes [i n]
        (aset res i (aget bs (- n (inc i)))))
      res)))

(defn join 
  "Concatenates two byte arrays"
  (^bytes [^bytes a ^bytes b]
    (let [al (int (alength a))
          bl (int (alength b))
          n (int (+ al bl))
          ^bytes res (byte-array n)]
      (System/arraycopy a (int 0) res (int 0) al)
      (System/arraycopy b (int 0) res (int al) bl)
      res)))



(defn unchecked-byte-array 
  "Like clojure.core/byte-array but performs unchecked casts on sequence values."
  (^bytes [size-or-seq] 
    (. clojure.lang.Numbers byte_array 
      (if (number? size-or-seq) 
        size-or-seq
        (map unchecked-byte size-or-seq ))))
  (^bytes [size init-val-or-seq] 
    (. clojure.lang.Numbers byte_array size 
      (if (sequential? init-val-or-seq) 
        (map unchecked-byte init-val-or-seq )
        init-val-or-seq))))