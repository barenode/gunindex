package gunindex.view.event;

import java.util.EventObject;

public class ErrorEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	
	private final Exception exception;

	public ErrorEvent(Object source, Exception exception) {
		super(source);
		this.exception = exception;
	}

	public Exception getException() {
		return exception;
	}
}