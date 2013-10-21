(ns mikera.cljutils.hex
  (:require [clojure.string :as str]))

(set! *warn-on-reflection* true)
(set! *unchecked-math* true)

(defn hex-string 
  "Converts an integer to a hexadecimal string." ([n]
  (Long/toHexString n)))

(defn bytes-from-hex-string [^String s]
  (let [s (str/replace s #"\s+" "")
        ^String s (str/replace s "0x" "")
        cc (.length s)
        n (quot cc 2)
        res (byte-array n)]
    (dotimes [i n]
      (aset res i (byte (+ (bit-and 0xF0 (bit-shift-left (Character/getNumericValue (char (.charAt s (int (* 2 i))))) 4)) 
                           (bit-and 0x0F (long (Character/getNumericValue (.charAt s (int (+ (* 2 i) 1))))))))))
    res))