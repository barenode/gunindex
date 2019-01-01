package gunindex.model;

public enum GunProperty {
	
	Calibre("Calibre"),
	MagazineCapacity("Magazine capacity"),
	Frame("Frame"),
	Grips("Grips"),
	Trigger("Trigger"),
	Sights("Sights"),
	Length("Overall length (mm)"),
	BarrelLength("Barrel length (mm)"),
	Height("Height (mm)"),
	Width("Width (mm)"),
	Weight("Weight (g)");

	private final String title;
	
	GunProperty(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}	
}
