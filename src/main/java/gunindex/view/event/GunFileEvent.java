package gunindex.view.event;

import java.io.File;
import java.util.EventObject;

public class GunFileEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	
	private final File file;

	public GunFileEvent(Object source, File file) {
		super(source);
		this.file = file;
	}

	public File getFile() {
		return file;
	}	
}
