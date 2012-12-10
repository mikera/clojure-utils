package mikera.cljutils;

@SuppressWarnings("serial")
public class ClojureError extends RuntimeException {
	public ClojureError(String message) {
		super(message);
	}

	public ClojureError(Throwable e) {
		super(e);
	}
}
