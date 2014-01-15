(ns mikera.cljutils.reader)

(set! *warn-on-reflection* true)
(set! *unchecked-math* true)

(defn string-reader 
  "Returns a reader fopr the given String"
  ([^String s]
    (clojure.lang.LineNumberingPushbackReader. (java.io.StringReader. s))))