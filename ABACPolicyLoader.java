
/*Nhut Trinh-Assignment1*/
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ABACPolicyLoader {
	
	public static List<String> PERMS = new ArrayList<>();
	public static List<String> ATTRSName = new ArrayList<>();
	public static List<String> ATTRSType = new ArrayList<>();
	public static List<List<String>> PA = new ArrayList<>();
	public static List<String> ENTITIES = new ArrayList<>();
	public static List<List<String>> AA = new ArrayList<>();
	
	

	public static void readPA(String paLine) {
		
		String[] entries = paLine.split("-");
		int i = entries.length;
		int k = 0;
		for(String entry: entries) {
			PA.add(new ArrayList<String>());

			String[] parts = entry.split(":");
			String attributesLine = parts[0].trim();
			String permissionName = parts[1].trim();

			String[] attributes = attributesLine.split(";");

			PA.get(k).add(permissionName);
			for(String attribute: attributes) {

				attribute = attribute.trim();
				attribute = attribute.substring(1, attribute.length()-1);
				String[] attributeParts = attribute.split(",");

				String name = attributeParts[0].trim();
				String value = attributeParts[1].trim();
				
				PA.get(k).add(name+ "-" +value);

			}
			
			k++;
			
		}

	}

	public static void readEntities(String entitiesLine) {
		String[] entries = entitiesLine.split(";");

		for(String entry: entries) {
			String[] parts = entry.split(";");
			String entityName = parts[0].trim();
			entityName = entityName.substring(1, entityName.length()-1);

			ENTITIES.add(entityName);	
		}

	}
	
	public static void readATTRS(String attLine) {
		String[] entries = attLine.split(";");
		for(String entry: entries) {
		
		String[] parts = entry.split(";");
		String attributesLine = parts[0].trim();
		String[] attributes = attributesLine.split(",");
		String type = attributes[0].trim();
		String name = attributes[1].trim();
		type = type.substring(1, type.length());
		name = name.substring(0, name.length()-1);


		ATTRSType.add(type);
		ATTRSName.add(name);	
		}
		
	}
	
	public static void readAA(String aaLine) {
	
		int k = 0;
		String[] entries = aaLine.split(";");
		for(String entry: entries) {
			
			AA.add(new ArrayList<String>());
			
			String[] parts = entry.split(":");
			String name = parts[0].trim();
			String attributes = parts[1].trim();
			String[] attribute = attributes.split(",");
			name = name.substring(1, name.length()-1);

			String attName = attribute[0];
			String attValue = attribute[1].trim();
			attName = attName.substring(1, attName.length());
			attValue = attValue.substring(0, attValue.length()-1);
			
			AA.get(k).add(name);
			AA.get(k).add(attName+"-"+attValue);
			k++;
			
		}
	}
	
	
	public static void readP(String pLine) {
		String[] entries = pLine.split(";");

		for(String entry: entries) {

			entry = entry.substring(2, entry.length()-1);

			PERMS.add(entry);	
		}
	}	

	public static void loadABACPolicy(String filename) throws IOException{

		Scanner scanner  = new Scanner(new File(filename));

		while(scanner.hasNextLine()){
			String line = scanner.nextLine();

			String[] parts = line.split("=");

			switch(parts[0].trim()){
			case "PA": readPA(parts[1]); break;
			case "ENTITIES" : readEntities(parts[1]); break;
			case "ATTRS" : readATTRS(parts[1]); break;
			case "AA" : readAA(parts[1]); break;
			case "PERMS" : readP(parts[1]); break;
			}
		}

		scanner.close();

	}

}
