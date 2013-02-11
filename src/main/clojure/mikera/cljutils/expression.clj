(ns mikera.cljutils.expression
  (:import clojure.lang.Compiler))

(defmacro constant [body]
  "Evaluates a constant expression at compile time" 
  (eval body))


(set! *warn-on-reflection* true)
(set! *unchecked-math* true)


;   Copyright (c) Chris Houser, Dec 2008. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Common Public License 1.0 (http://opensource.org/licenses/cpl.php)
;   which can be found in the file CPL.TXT at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(defn expression-info
 "Uses the Clojure compiler to analyze the given s-expr. Returns
 a map with keys :class and :primitive? indicating what the 
 compiler concluded about the return value of the expression. 
 Returns nil if no type info can be determined at compile-time.
  
 Example: (expression-info '(+ (int 5) (float 10)))
 Returns: {:class float, :primitive? true}"
 [expr]
 (let [^clojure.lang.Compiler$Expr fn-ast (Compiler/analyze clojure.lang.Compiler$C/EXPRESSION 
                `(fn [] ~expr))
       ^clojure.lang.Compiler$Expr expr-ast (.body (first (.methods fn-ast)))]
   (when (.hasJavaClass expr-ast)
     {:class (.getJavaClass expr-ast)
      :primitive? (.isPrimitive (.getJavaClass expr-ast))})))