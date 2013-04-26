 (ns mikera.cljutils.core)
 
 (defn apply-kw
   "Applies a function to a set of argumens, where the last argument is a map of 
    additional keyword arguments."
   [f & args]
   {:pre [(map? (last args))]}
   (apply f (apply concat (butlast args) (last args))))
 
 