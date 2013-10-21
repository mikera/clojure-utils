(ns mikera.cljutils.bytes
  (:refer-clojure :exclude [reverse])
  (:require [mikera.cljutils.hex :as hex])
  (:require [clojure.string :as str]))

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

(defn slice
  "Slices a byte array with a given start and length"
  (^bytes [a start]
    (slice a start (- (alength ^bytes a) start)))
  (^bytes [a start length]
    (let [al (int (alength ^bytes a))
          ^bytes res (byte-array length)]
      (System/arraycopy a (int start) res (int 0) length)
      res)))

(defn to-hex-string 
  ([^bytes bs]
    (str/join " " (map #(hex/hex-string-from-byte %) bs))))

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