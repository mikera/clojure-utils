clojure-utils
=============

A library of various small but handy Clojure utility functions

Of particular Interest:

 - mikera.cljutils.Clojure - Java class of utility functions for calling Clojure from Java
 - mikera.cljutils.namepace - Clojure functions for merging / managing namespaces
 - mikera.cljutils.loops - Extra looping constructs and macros. Handy if you need them.
 
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