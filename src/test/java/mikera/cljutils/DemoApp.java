package mikera.cljutils;

import mikera.cljutils.Clojure;

public class DemoApp {
	public static void main(String [] args) {
		String s = "(+ 1 2)";
		System.out.println("Evaluating Clojure code: "+s);

		Object result=Clojure.eval(s);
		System.out.println("=> "+ result);
	}
}
