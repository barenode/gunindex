package gunindex.view.event;

import java.util.EventObject;

public class FilterChangedEvent extends EventObject {	
	private static final long serialVersionUID = 1L;

	private final String filter;
	
	public FilterChangedEvent(Object source, String filter) {
		super(source);
		this.filter = filter;
	}

	public String getFilter() {
		return filter;
	}	
}
