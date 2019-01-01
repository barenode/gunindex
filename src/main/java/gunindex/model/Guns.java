package gunindex.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Guns implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final List<Gun> guns;
	private final List<Gun> selectedGuns = new ArrayList<>();
	
	public Guns() {
		this(new ArrayList<>());
	}
	
	public Guns(List<Gun> guns) {
		super();
		this.guns = guns;
	}
	
	public List<Gun> getGuns() {
		return guns;
	}
	
	public Gun get(int index) {
		return guns.get(index);
	}
	
	public int size() {
		return guns.size();
	}

	public void newGun() {
		guns.add(new Gun());
	}
	
	/**
	 * Selection is not based on objects equality, as it is possible for multiple equal guns to exist within single model.
	 */
	public void setSelected(Gun gun, boolean selected) {
    	if (selected) {
    		if (!isSelected(gun)) {
    			selectedGuns.add(gun);
    		}
    	} else {
    		selectedGuns.removeIf(item -> item == gun);
    	}
    }
    
	public boolean isSelected(Gun gun) {
    	//instance check
    	return selectedGuns.stream().anyMatch(item -> item == gun);
    }
    
    public void removeSelected() {
    	for (Gun gun : selectedGuns) {
    		guns.removeIf(item -> item == gun);
    	}    
    	selectedGuns.clear();
    }
}
