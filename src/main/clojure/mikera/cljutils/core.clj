 (ns mikera.cljutils.core)
 
 (defn apply-kw
   "Applies a function to a set of arguments, where the last argument is a map of 
    additional keyword arguments.

    Directly provided keywords will override those in the final argument map."
   [f & args]
   {:pre [(map? (last args))]}
   (if-let [kvs (seq (butlast args))]
     (apply f (apply concat (apply assoc (last args) kvs)))
     (apply f (apply concat (first args)))))
 
 