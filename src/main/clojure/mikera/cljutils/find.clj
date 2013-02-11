(ns mikera.cljutils.find)

(set! *warn-on-reflection* true)
(set! *unchecked-math* true)

(defn find-first [pred coll]
  "Searches a collection and returns the first item for which pred is true, nil if not found.
   Like 'some', except it returns the value from the collection (rather than the result of 
   applying the predicate to the value). This is often more useful."
  (loop [s (seq coll)] 
    (when s  
      (let [v (first s)]
        (if (pred v)
          v
          (recur (next s)))))))

(defn find-position 
  "Searches a collection and returns the (long) index of the item's position."
  (^long [coll item] 
    (find-position coll item 0))
  (^long [coll item ^long i] 
    (if (empty? coll) 
      nil
	    (let [v (first coll)]
	      (if (= item v)
	        i
	        (recur (rest coll) item (inc i)))))))