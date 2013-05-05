package mikera.cljutils;

import mikera.cljutils.Clojure;

public class DemoApp {
	public static void main(String [] args) {
		// create a string containing a valid Clojure expression
		String s = "(+ 1 2)";
		
		System.out.println("Evaluating Clojure code: " + s);

		// evaluate the expression
		Object result = Clojure.eval(s);
		
		System.out.println("=> "+ result);
	}
}
