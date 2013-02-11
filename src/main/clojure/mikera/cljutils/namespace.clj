(ns mikera.cljutils.namespace
  (:refer-clojure :exclude [import]))

(set! *warn-on-reflection* true)
(set! *unchecked-math* true)

;; The use and distribution terms for this software are covered by the
;; Eclipse Public License 1.0
;; (http://opensource.org/licenses/eclipse-1.0.php) which can be found
;; in the file epl-v10.html at the root of this distribution. By using
;; this software in any fashion, you are agreeing to be bound by the
;; terms of this license. You must not remove this notice, or any
;; other, from this software.

;; import macros originally from ztellman/potemkin

(defmacro import-fn 
  "Given a function in another namespace, defines a function with the same name in the
   current namespace.  Argument lists, doc-strings, and original line-numbers are preserved."
  [sym]
  (let [vr (resolve sym)
        m (meta vr)
        nspace (:name m)
        n (:name m)
        arglists (:arglists m)
        doc (:doc m)
        protocol (:protocol m)]
    (when-not vr
      (throw (IllegalArgumentException. (str "Don't recognize " sym))))
    (when (:macro m)
      (throw (IllegalArgumentException. (str "Calling import-fn on a macro: " sym))))
    `(do
       (def ~(with-meta n {:protocol protocol}) (deref ~vr))
       (alter-meta! (var ~n) assoc
         :doc ~doc
         :arglists ~(list 'quote arglists)
         :file ~(:file m)
         :line ~(:line m))
       ~vr)))

(defmacro import-macro
  "Given a macro in another namespace, defines a macro with the same name in the
   current namespace.  Argument lists, doc-strings, and original line-numbers are preserved."
  [sym]
  (let [vr (resolve sym)
        m (meta vr)
        n (:name m)
        nspace (:ns m)
        arglists (:arglists m)
        doc (:doc m)]
    (when-not vr
      (throw (IllegalArgumentException. (str "Don't recognize " sym))))
    (when-not (:macro m)
      (throw (IllegalArgumentException. (str "Calling import-macro on a non-macro: " sym))))
    `(do
       (def ~n ~(resolve sym))
       (alter-meta! (var ~n) assoc
         :doc ~doc
         :arglists ~(list 'quote arglists)
         :file ~(:file m)
         :line ~(:line m))
       (.setMacro (var ~n))
       ~vr)))

(defmacro import-def 
  "Given a regular def'd var from another namespace, defined a new var with the
   same name in the current namespace."
  [sym]
  (let [vr (resolve sym)
        m (meta vr)
        n (:name m)
        n (if (:dynamic m) (with-meta n {:dynamic true}) n) 
        nspace (:ns m)
        doc (:doc m)]
    (when-not vr
      (throw (IllegalArgumentException. (str "Don't recognize " sym))))
    `(do
       (def ~n @~vr)
       (alter-meta! (var ~n) assoc
         :doc ~doc
         :file ~(:file m)
         :line ~(:line m))
       ~vr)))

(defmacro import [sym]
  (let [vr (resolve sym)
        m (meta vr)]
    (cond
      (:macro m) `(import-macro ~sym)
      (:arglists m) `(import-fn ~sym)
      :default `(import-def ~sym))))

;; pull macros modified from:
;;   http://stackoverflow.com/questions/4732134

(defmacro pull 
  "Pulls one or more symbols from another namespace"
  ([ns-qualified-sym]
    `(pull ~(symbol (namespace ns-qualified-sym)) ~(symbol (name ns-qualified-sym))))
  ([ns vlist]
    (let [vlist (if (coll? vlist) vlist [vlist])]
      `(do ~@(for [sym vlist]
            (let [full-sym (symbol (str ns) (str sym))]
              `(import ~full-sym)))))))

(defmacro pull-all 
  "Pulls in all public symbols from another namespace"
  ([ns]  
    (require ns) 
    `(pull ~ns ~(for [[sym var] (ns-publics ns)] sym))))

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
  `(binding [*ns* (the-ns '~ns)]
     ~@(map (fn [form] `(eval '~form)) body)))

(defmacro with-temp-ns
  "Evaluates body in an anonymous namespace, which is then immediately
  removed.  The temporary namespace will 'refer' clojure.core."
  [& body]
  `(try
    (create-ns 'sym#)
    (let [result# (with-ns sym#
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

(defmacro with-merged-environment 
  "Evaluates body in a temporary namespace that merges several other namespaces"
  ([namespaces & body]
    `(with-environment ~namespaces 
      (pull-all ~(ns-name *ns*))
      ~@body)))