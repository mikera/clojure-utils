(ns mikera.cljutils.reader)

(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)

(defn string-reader 
  "Returns a reader for the given String"
  ([^String s]
    (clojure.lang.LineNumberingPushbackReader. (java.io.StringReader. s))))
