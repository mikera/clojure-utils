(ns mikera.cljutils.namespace)

;; The use and distribution terms for this software are covered by the
;; Eclipse Public License 1.0
;; (http://opensource.org/licenses/eclipse-1.0.php) which can be found
;; in the file epl-v10.html at the root of this distribution. By using
;; this software in any fashion, you are agreeing to be bound by the
;; terms of this license. You must not remove this notice, or any
;; other, from this software.

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

;; nstools functions
;; by Conrad hinsen
;; licensed under EPL

(defn like
  "Take over all refers, aliases, and imports from the namespace named by
   ns-sym into the current namespace. Use :like in the ns+ macro in preference
   to calling this directly."
  [ns-sym]
  (require ns-sym)
  (doseq [[alias-sym other-ns] (ns-aliases ns-sym)]
    (alias alias-sym (ns-name other-ns)))
  (doseq [[sym ref] (ns-refers ns-sym)]
    (ns-unmap *ns* sym)
    (. *ns* (refer sym ref))
  (doseq [[sym ref] (ns-imports ns-sym)]
    (. *ns* (importClass ref)))))

(defn clone
  "Take over all refers, aliases, and imports from the namespace named by
   ns-sym into the current namespace, and add refers to all vars interned.
   Use :clone in the ns+ macro in preference to calling this directly."
  [ns-sym]
  (like ns-sym)
  (use ns-sym))

(defn from
  "Add refers to syms from ns-sym to the current namespace, replacing
   existing refers if necessary. If :all is given instead of syms,
   all symbols from ns-sym are referred to, making this equivalent
   to (use ns-sym). Use :from in the ns+ macro in preference to calling
   this directly."
  [ns-sym & syms]
  (if (= syms '(:all))
    (use ns-sym)
    (do
      (require ns-sym)
      (doseq [sym syms]
        (ns-unmap *ns* sym)
        (. *ns* (refer sym (ns-resolve ns-sym sym)))))))

(defn remove-from-ns
  "Remove symbols from the namespace. Use :remove in the ns+ macro in
   preference to calling this directly."
  [& syms]
  (doseq [sym syms]
    (ns-unmap *ns* sym)))

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

(defmacro with-environment 
  "Evaluates body in a temporary namespace that merges several other namespaces"
  ([namespaces & body]
    `(with-temp-ns
      ~@(for [ns namespaces]
         `(pull-all ~ns))
      ~@body)))