package mikera.cljutils;

import clojure.lang.RT;
import clojure.lang.Symbol;
import clojure.lang.Var;

/**
 * Class containing static utility methods for Java->Clojure interop
 * 
 * @author Mike
 *
 */
public class Clojure {
	public static final Var REQUIRE=var("clojure.core", "require");
	public static final Var META=var("clojure.core", "meta");
	public static final Var EVAL=var("clojure.core", "eval");
	public static final Var READ_STRING=var("clojure.core", "read-string");
	static {
		require("clojure.core");
	}
	
	public static Object require(String nsName) {
		return REQUIRE.invoke(Symbol.intern(nsName));
	}
	
	public static Object readString(String s) {
		return READ_STRING.invoke(s);
	}
	
	public static Var var(String varName) {
		return var("clojure.core",varName);
	}
	
	public static Var var(String nsName, String varName) {
		return RT.var(nsName,varName);
	}

	public static Object exec(String string) {
		return EVAL.invoke(readString(string));
	}
	
}
