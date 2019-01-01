package gunindex.persistence;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import gunindex.model.Gun;
import gunindex.model.GunProperty;
import gunindex.model.Guns;
import junit.framework.TestCase;

public class GunDaoTestCase extends TestCase {
	private static final Gun GUN1;
	private static final Gun GUN2;
	private static final String SERIALIZED = 
		"CZ 75 TS|CZUB|Calibre:9x19, .40 S&W|MagazineCapacity:20, 17|Frame:STEEL|Grips:WOOD|Trigger:SA|Sights:FIXED|Length:225|BarrelLength:130|Height:150|Width:45|Weight:1270|" +
		System.getProperty("line.separator") +
		"CZ 97 B|CZUB|Calibre:.45 ACP|MagazineCapacity:10|Frame:STEEL|Grips:WOOD|Trigger:SA/DA|Sights:LUMINESCENT|Length:212|BarrelLength:114,8|Height:150|Width:35|Weight:1150|";	
		
	private GunDao dao;	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		dao = new GunDao();
	}

	public void testLoad() throws Exception {
		Guns guns = dao.load(new ByteArrayInputStream(SERIALIZED.getBytes(Charset.forName("utf-8"))));
		assertEquals(2, guns.size());
		assertEquals(GUN1, guns.get(0));
		assertEquals(GUN2, guns.get(1));
	}
	
	public void testPersist() throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		dao.save(new Guns(Arrays.asList(GUN1, GUN2)), out);
		assertEquals(SERIALIZED, out.toString("utf-8"));
	}	
	
	public void testLoadSparse() throws Exception {
		Guns guns = dao.load(new ByteArrayInputStream(" | |".getBytes(Charset.forName("utf-8"))));
		assertEquals(1, guns.size());
		assertEquals(new Gun(), guns.get(0));
	}	
	
	public void testPersistSparse() throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Guns guns = new Guns();
		guns.newGun();
		dao.save(guns, out);
		assertEquals(" | |", out.toString("utf-8"));
	}
	
	public void testLoadPropertyWithNoValue() throws Exception {
		Guns guns = dao.load(new ByteArrayInputStream(" | |Calibre:|".getBytes(Charset.forName("utf-8"))));		
		assertEquals(1, guns.size());
	}
	
	static {
		Map<GunProperty, String> gun1Properties = new TreeMap<>(Gun.GUN_PROPERTY_COMPARATOR);
		gun1Properties.put(GunProperty.Calibre, 			"9x19, .40 S&W");
		gun1Properties.put(GunProperty.MagazineCapacity, 	"20, 17");
		gun1Properties.put(GunProperty.Frame, 				"STEEL");
		gun1Properties.put(GunProperty.Grips, 				"WOOD");
		gun1Properties.put(GunProperty.Trigger, 			"SA");
		gun1Properties.put(GunProperty.Sights, 				"FIXED");
		gun1Properties.put(GunProperty.Length, 				"225");		
		gun1Properties.put(GunProperty.BarrelLength, 		"130");
		gun1Properties.put(GunProperty.Height, 				"150");
		gun1Properties.put(GunProperty.Width, 				"45");
		gun1Properties.put(GunProperty.Weight, 				"1270");
		GUN1 = new Gun(
			"CZ 75 TS",
			"CZUB",
			gun1Properties
		);
		
		Map<GunProperty, String> gun2Properties = new TreeMap<>(Gun.GUN_PROPERTY_COMPARATOR);
		gun2Properties.put(GunProperty.Calibre, 			".45 ACP");
		gun2Properties.put(GunProperty.MagazineCapacity, 	"10");
		gun2Properties.put(GunProperty.Frame, 				"STEEL");
		gun2Properties.put(GunProperty.Grips, 				"WOOD");
		gun2Properties.put(GunProperty.Trigger, 			"SA/DA");
		gun2Properties.put(GunProperty.Sights, 				"LUMINESCENT");
		gun2Properties.put(GunProperty.Length, 				"212");		
		gun2Properties.put(GunProperty.BarrelLength, 		"114,8");
		gun2Properties.put(GunProperty.Height, 				"150");
		gun2Properties.put(GunProperty.Width, 				"35");
		gun2Properties.put(GunProperty.Weight, 				"1150");
		GUN2 = new Gun(
			"CZ 97 B",
			"CZUB",
			gun2Properties
		);
	}
}
