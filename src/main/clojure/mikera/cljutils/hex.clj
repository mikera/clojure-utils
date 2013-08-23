(ns mikera.cljutils.hex)

(defn hex-string 
  "Converts an integer to a hexadecimal string." ([n]
  (Long/toHexString n)))