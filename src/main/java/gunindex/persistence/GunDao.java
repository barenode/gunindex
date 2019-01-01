package gunindex.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import gunindex.model.Gun;
import gunindex.model.GunProperty;
import gunindex.model.Guns;

/**
 * Gun serialize format: * 
 * Name|Manufacturer|Property1:Property1Value|Property2:Property2Value|...
 * 
 * | and : characters are not allowed within any gun attribute including properties.
 */
public class GunDao {	
	private static final String SEPARATOR = "|";
	
	public GunDao() {
		super();
	}

	/**
	 * Loads list of {@see Gun} from given input stream.
	 */
	public Guns load(InputStream in) 
		throws IOException
	{
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in));
			List<Gun> result = new ArrayList<>();
			while(reader.ready()) {
				result.add(parse(reader.readLine()));
			}
			return new Guns(result);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}		
	}
	
	/**
	 * Saves list of {@see Gun} to given output stream.
	 */
	public void save(Guns guns, OutputStream out) 
		throws IOException
	{
		BufferedWriter outputWriter = null;
		try {
			outputWriter = new BufferedWriter(new OutputStreamWriter(out));
			if (guns!=null) {
				boolean first = true;
				for (Gun gun : guns.getGuns()) {
					if (!first) {
						outputWriter.newLine();
					}
					String line = serialize(gun);
					outputWriter.write(line);
					first = false;					
				}
			}
		} finally {
			try {	
				if (outputWriter!=null) {
					outputWriter.flush();
					outputWriter.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
	}
	
	private Gun parse(String line) {
		String[] parts = line.split(Pattern.quote(SEPARATOR));
		if (parts.length<2) {
			throw new IllegalArgumentException("ILLEGAL GUN FILE CONTENT: Expecting at least 2 values separated by | character. But got " + parts.length + ".");
		}
		Map<GunProperty, String> properties = new TreeMap<>(Gun.GUN_PROPERTY_COMPARATOR);
		if (parts.length>2) {
			for (int i=2; i<parts.length; i++) {
				String rawProperty = parts[i];
				String[] propertyParts = rawProperty.split(":");
				String propertyName = null;
				String propertyValue = null;
				if (propertyParts.length==1) {
					propertyName = propertyParts[0];
				} else if (propertyParts.length==2) {
					propertyName = propertyParts[0];
					propertyValue =  propertyParts[1];
				} else {
					throw new IllegalArgumentException("ILLEGAL GUN FILE CONTENT: Wrong gun property format, expecting name and value separated by : character. But got " + rawProperty + ".");
				}
				properties.put(GunProperty.valueOf(propertyName), parseValue(propertyValue));					
			}
		}
		return new Gun(
			parseValue(parts[0]),
			parseValue(parts[1]),
			properties
		);
	} 
	
	private String parseValue(String raw) {
		if (raw==null) {
			return null;
		}
		if ("".equals(raw.trim())) {
			return null;
		} else {
			return raw.trim();
		}
	}
	
	private String serialize(Gun gun) {
		StringBuilder buffer = new StringBuilder();
		buffer.append(serializeValue(gun.getName()));
		buffer.append(SEPARATOR);
		buffer.append(serializeValue(gun.getManufacturer()));
		buffer.append(SEPARATOR);
		if (gun.getProperties()!=null) {
			for (Map.Entry<GunProperty, String> entry : gun.getProperties().entrySet()) {
				buffer.append(entry.getKey().name());
				buffer.append(":");
				buffer.append(serializeValue(entry.getValue()));
				buffer.append(SEPARATOR);
			}
		}
		return buffer.toString();
	}
	
	private String serializeValue(String raw) {
		if (raw==null) {
			return " ";
		} else if ("".equals(raw.trim())) {
			return " ";
		} else {
			return raw.trim();
		}
	}
}
