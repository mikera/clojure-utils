clojure-utils
=============

A library of various small but handy Clojure utility functions

Of particular interest:

 - **Clojure.java** - Java class of utility functions for calling Clojure from Java
 - **arrays.clj** - manipulating Java arrays
 - **core.clj** - handy functions that should have been in clojure.core :-)
 - **error.clj** - handling errors and exceptions
 - **expression.clj** - analysing Clojure expressions
 - **find.clj** - searching for items within different types of collection
 - **logic.clj** - extra logic functions / macros (e.g. xor, nand)
 - **loops.clj** - extra looping constructs and macros
 - **macros.clj** - some handy macros
 - **namespace.clj** - for merging / managing namespaces
 - **test.clj** - tools for handling text strings
 - **vectors.clj** - useful functions for working with Clojure persistent vectors

 
### Clojure calling example

Perhaps the most useful feature in `clojure-utils` is a set of utility functions 
designed to allow easy calling of Clojure code from Java. These are contained 
in the `mikera.cljutils.Clojure` class. Usage example:

```java
import mikera.cljutils.Clojure;

public class Demo {
	public static void main(String [] args) {
		String s = "(+ 1 2)";
		System.out.println("Evaluating Clojure code: "+s);

		Object result = Clojure.eval(s);
		System.out.println("=> "+ result);
	}
}
```


### License

All the code I have written in clojure-utils is licensed under the LGPL v3.

 - http://www.gnu.org/copyleft/lesser.html
 
Exceptions apply to some specific code which has been incorporated from elsewhere under permissive licenses
that allow this: see the source files for more information