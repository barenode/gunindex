package gunindex.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class Gun implements Serializable {
	private static final long serialVersionUID = 1L;	
	
	private String name;
	private String manufacturer;
	private Map<GunProperty, String> properties = new TreeMap<>(GUN_PROPERTY_COMPARATOR);
	
	public Gun() {
		super();
	}
	
	public Gun(
		String name,
		String manufacturer,
		Map<GunProperty, String> properties) 
	{
		super();
		this.name = name;
		this.manufacturer = manufacturer;
		this.properties = properties;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Map<GunProperty, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<GunProperty, String> properties) {
		this.properties = properties;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((manufacturer == null) ? 0 : manufacturer.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((properties == null) ? 0 : properties.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Gun other = (Gun) obj;
		if (manufacturer == null) {
			if (other.manufacturer != null)
				return false;
		} else if (!manufacturer.equals(other.manufacturer))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (properties == null) {
			if (other.properties != null)
				return false;
		} else if (!properties.equals(other.properties))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Gun [name=" + name + ", manufacturer=" + manufacturer + ", properties=" + properties + "]";
	}	
	
	public static final Comparator<GunProperty> GUN_PROPERTY_COMPARATOR = new Comparator<GunProperty>() {
		@Override
		public int compare(GunProperty o1, GunProperty o2) {
			return Integer.valueOf(o1.ordinal()).compareTo(Integer.valueOf(o2.ordinal()));
		}		
	};
}
