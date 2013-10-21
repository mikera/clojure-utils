(ns mikera.cljutils.text)

(set! *warn-on-reflection* true)
(set! *unchecked-math* true)

(defn repeat-char
  "Repeats a character a given numbers of times and returns the concatenated String"
  (^String [num char]
    (apply str (repeat num char))))

(defn truncate 
  "Truncates a string to a given maximum length."
  (^String [^String s length]
    (let [slen (.length s)]
      (if (<= slen length)
        s
        (.substring s 0 length)))))

(defn truncate-dotted 
  "Truncates a string to a specified length, putting dots at the end if length is exceeded"
  (^String [^String s length & {:keys [num-dots]
                                :or {num-dots 3}}]
    (let [num-dots (int (min num-dots length))
          slen (.length s)
          ss (min slen (- length num-dots))]
      (if (< slen length)
        s
        (str (.substring s 0 ss) (repeat-char num-dots \.))))))

(defn pad-right
  "Pads a string on the right with the given char. Char defaults to space if not specified."
  (^String [^String string min-length]
    (pad-right string min-length \space))
  (^String [^String string min-length char]
    (let [slen (.length string)]
      (if (< slen min-length )
        (str string (repeat-char (- min-length slen) char))
        string))))

(defn pad-left
  "Pads a string on the left with the given char. Char defaults to space if not specified."
  (^String [^String string min-length]
    (pad-left string min-length \space))
  (^String [^String string min-length char]
    (let [slen (.length string)]
      (if (< slen min-length )
        (str (repeat-char (- min-length slen) char) string)
        string))))

(defn pad-centre
  "Pads a string on the both sides with the given char. Char defaults to space if not specified."
  (^String [^String string min-length]
    (pad-left string min-length \space))
  (^String [^String string min-length char]
    (let [slen (.length string)]
      (if (< slen min-length )
        (str (repeat-char (- min-length slen) char) string)
        string))))

(defn capitalise ^String [^String s]
  (if (> (count s) 0)
    (str (Character/toUpperCase (.charAt s 0)) (.substring s 1))
    s))

(defn take-lines 
  "Takes a specified number of lines from a file, and returns a sequence of these lines as Strings"
  ([n file]
  (with-open [rdr (clojure.java.io/reader file)]
    (doall (take n (line-seq rdr))))))