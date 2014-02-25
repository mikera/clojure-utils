package mikera.cljutils;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestClojure {
	@Test public void testClojureRun() {
		assertEquals((long)2,Clojure.eval("(+ 1 1)"));
		assertEquals((long)3,Clojure.eval("(+ 1 2)"));
	}
}
