package gunindex.view;

import javax.swing.table.AbstractTableModel;

import gunindex.model.Gun;
import gunindex.model.GunProperty;
import gunindex.model.Guns;

public class GunTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	
	private final Guns guns;
	
	public GunTableModel(Guns guns) {
		super();
		this.guns = guns;
	}
	
	@Override
    public Class<?> getColumnClass(int col) {
		if (col==0) {
			return Boolean.class;
		} else {
			return super.getColumnClass(col);
		}
	}

	@Override
	public String getColumnName(int col) {
		if (col==0) {
			return "";
		} else if (col==1) {
			return "Name";
		} else if (col==2) {
			return "Manufacturer";
		} else {
			int propertyIndex = col-3;
			GunProperty property = GunProperty.values()[propertyIndex];
			return property.getTitle();
		}
	}


	@Override
	public int getColumnCount() {
		//nanme, manufacturer and properties
		return GunProperty.values().length + 3;
	}

	@Override
	public int getRowCount() {
		return guns.size();
	}
	
	public boolean isCellEditable(int row, int col) {
		return true;
    }

	@Override
	public Object getValueAt(int row, int col) {
		Gun gun = guns.get(row);
		if (col==0) {
			return guns.isSelected(gun);
		} else if (col==1) {
			return gun.getName();
		} else if (col==2) {
			return gun.getManufacturer();
		} else {
			int propertyIndex = col-3;
			GunProperty property = GunProperty.values()[propertyIndex];
			if (gun.getProperties().containsKey(property)) {
				return gun.getProperties().get(property);
			} else {
				return null;
			}
		}
	}		

    public void setValueAt(Object value, int row, int col) {
    	Gun gun = guns.get(row);
    	if (col==0) {
    		guns.setSelected(gun, (Boolean)value);
    	} else if (col==1) {
			gun.setName((String)value);
		} else if (col==2) {
			gun.setManufacturer((String)value);
		} else {
			int propertyIndex = col-3;
			GunProperty property = GunProperty.values()[propertyIndex];
			gun.getProperties().put(property, (String)value);
		}
        fireTableCellUpdated(row, col);
    }
}
