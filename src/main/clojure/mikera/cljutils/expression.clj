(ns mikera.cljutils.expression
  (:import clojure.lang.Compiler))

(defmacro constant [body]
  "Evaluates a constant expression at compile time" 
  (eval body))

(defn expression-info
 "Uses the Clojure compiler to analyze the given s-expr. Returns
 a map with keys :class and :primitive? indicating what the 
 compiler concluded about the return value of the expression. 
 Returns nil if no type info can be determined at compile-time.
  
 Example: (expression-info '(+ (int 5) (float 10)))
 Returns: {:class float, :primitive? true}"
 [expr]
 (let [fn-ast (Compiler/analyze clojure.lang.Compiler$C/EXPRESSION 
                `(fn [] ~expr))
       expr-ast (.body (first (.methods fn-ast)))]
   (when (.hasJavaClass expr-ast)
     {:class (.getJavaClass expr-ast)
      :primitive? (.isPrimitive (.getJavaClass expr-ast))})))