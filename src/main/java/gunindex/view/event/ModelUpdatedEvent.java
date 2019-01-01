package gunindex.view.event;

import java.util.EventObject;

public class ModelUpdatedEvent extends EventObject {	
	private static final long serialVersionUID = 1L;
	
	public ModelUpdatedEvent(Object source) {
		super(source);
	}
}
