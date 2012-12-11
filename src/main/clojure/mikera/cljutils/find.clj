(ns mikera.cljutils.find)

(defn find-first [pred coll]
  "Searches a collection and returns the first item for which pred is true, nil if not found"
  (loop [s (seq coll)] 
    (when s  
      (let [v (first s)]
        (if (pred v)
          v
          (recur (next s)))))))

(defn find-position 
  "Searches a collection and returns the index of the item's position"
  ([coll item] 
    (find-position coll item 0))
  ([coll item i] 
    (if (empty? coll) 
      nil
	    (let [v (first coll)]
	      (if (= item v)
	        i
	        (recur (rest coll) item (inc i)))))))