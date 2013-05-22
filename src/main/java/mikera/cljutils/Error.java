package mikera.cljutils;

/**
 * A custom runtime exception class
 * @author Mike
 *
 */
@SuppressWarnings("serial")
public class Error extends RuntimeException {
	public Error(String message) {
		super(message);
	}

	public Error(Throwable e) {
		super(e);
	}
}
