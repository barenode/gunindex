package gunindex.view.event;

import java.util.EventObject;

import gunindex.model.Guns;

public class ModelChangeEvent extends EventObject {	
	private static final long serialVersionUID = 1L;
	
	private final Guns guns;
	
	public ModelChangeEvent(Object source, Guns guns) {
		super(source);
		this.guns = guns;
	}

	public Guns getGuns() {
		return guns;
	}	
}
