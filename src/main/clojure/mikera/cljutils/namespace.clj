(ns mikera.cljutils.namespace)

(defmacro pull 
  "Pulls one ore more symbols from another namespace"
  ([ns & vlist]
    `(do ~@(for [sym vlist]
            (let [full-sym (symbol (str ns) (str sym))
                  var (find-var full-sym)
                  metadata (meta var)]
              ;;(println metadata)
              `(def ~sym ~var))))))

(defmacro pull-all 
  "Pulls in all symbols from another namespace"
  ([ns]
    `(do ~@(for [[sym var] (ns-publics ns)]
             `(pull ~ns ~sym)))))

;; with-ns macros
;; by Stuart Sierra, http://stuartsierra.com/
;; March 28, 2009
;; licensed under EPL

(defmacro with-ns
  "Evaluates body in another namespace.  ns is either a namespace
  object or a symbol.  This makes it possible to define functions in
  namespaces other than the current one."
  [ns & body]
  `(binding [*ns* (the-ns ~ns)]
     ~@(map (fn [form] `(eval '~form)) body)))

(defmacro with-temp-ns
  "Evaluates body in an anonymous namespace, which is then immediately
  removed.  The temporary namespace will 'refer' clojure.core."
  [& body]
  `(try
    (create-ns 'sym#)
    (let [result# (with-ns 'sym#
                    (clojure.core/refer-clojure)
                    ~@body)]
      result#)
    (finally (remove-ns 'sym#))))