(ns mikera.cljutils.bytes)


(set! *warn-on-reflection* true)
(set! *unchecked-math* true)

(defn join-bytes 
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