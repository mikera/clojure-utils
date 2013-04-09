(ns mikera.cljutils.text)

(defn repeat-char
  "Repeats a character a given numbers of times and returns the concatenated String"
  (^String [num char]
    (apply str (repeat num char))))

(defn dotted-truncate 
  "Truncates a string to a specified length, putting dots at the end if length is exceeded"
  (^String [length ^String s & {:keys [num-dots]
                                :or {num-dots 3}}]
    (let [num-dots (int (min num-dots length))
          slen (.length s)
          ss (min slen (- length num-dots))]
      (if (< slen length)
        s
        (str (.substring s 0 ss) (repeat-char num-dots \.))))))