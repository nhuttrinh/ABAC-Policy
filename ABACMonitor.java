
/*Nhut Trinh-Assignment1*/
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ABACMonitor extends ABACPolicyLoader{

	
	public static void loadPolicy(String filename) throws IOException {
		loadABACPolicy(filename);
	}
	
	public static void showPolicy() {
		System.out.println("ATTRS: ");
		for(int i=0;i<ATTRSType.size();i++) {
			System.out.print("<"+ATTRSType.get(i)+", "+ATTRSName.get(i)+">; ");
		}
		System.out.println("\n\nPERMS: ");
		for(int i=0;i<PERMS.size();i++) {
			System.out.print("<"+PERMS.get(i)+">; ");
		}
		System.out.println("\n\nPA: ");
		for(int i=0;i<PA.size();i++) {
			System.out.println(PA.get(i));			
		}
		System.out.println("\nENTITIES: ");
		for(int i=0;i<ENTITIES.size();i++) {
			System.out.print("<"+ENTITIES.get(i)+">; ");			
		}
		System.out.println("\n\nAA: ");
		for(int i=0;i<AA.size();i++) {
			System.out.println(AA.get(i));			
		}
	}

	
	public static void checkPermission( String userId, String objectId, String environmentId, String permissionId){
		
		ArrayList<String> user = new ArrayList<>();
		String object="";
		String environment="";
		boolean found = false;
		int key =0;
		
		for(int i=0;i<AA.size();i++) {
			if(userId.equalsIgnoreCase(AA.get(i).get(0))) {
				for(int j=0; j<AA.get(i).size();j++) {
					user.add(AA.get(i).get(j));
				}
				
			}
			if(objectId.equalsIgnoreCase(AA.get(i).get(0))) {
				for(int j=1; j<AA.get(i).size();j++) {
					object=AA.get(i).get(j);
				}
				
			}
			if(environmentId.equalsIgnoreCase(AA.get(i).get(0))) {
				for(int j=1; j<AA.get(i).size();j++) {
					environment=AA.get(i).get(j);

				}
			}
		}
		
		for(int i=0;i<PA.size();i++) {
			if(permissionId.equalsIgnoreCase(PA.get(i).get(0))){
				key++;
				for(int j=1;j<PA.get(i).size();j++) {
					String part = PA.get(i).get(j);
					
						for(int  k=0;k<user.size();k++) {

							if(user.get(k).equalsIgnoreCase(part)) {
								key++;
							}
						}

						if(object.equalsIgnoreCase(part)) {
							key++;
						}

						if(environment.equalsIgnoreCase(part)) {
							key++;	
						}

				}
				if(key==PA.get(i).size()&&key!=1) {
					found=true;
					break;
				}
				else {
					key = 0;
				}
				
			}
		}
		
		if(found) {
			System.out.println("Permission GRANTED!");	
		}
		else {
			System.out.println("Permission DENIED!");	
		}
	}
	
	public static void addEntity(String entityName) {
		ENTITIES.add(entityName);
		
	}
	
	public static void removeEntity(String entityName) {
		ENTITIES.remove(entityName);
	}
	
	public static void addAttribute(String attributeName, String attributeType) {
		ATTRSName.add(attributeName);
		ATTRSType.add(attributeType);
	}
	
	public static void removeAttribute(String attributeName) {
		for(int i=0;i<ATTRSName.size();i++) {
		if(ATTRSName.get(i).equalsIgnoreCase(attributeName)) {
			ATTRSName.remove(i);
			ATTRSType.remove(i);
		}
		}
		for(int i=0;i<PA.size();i++) {
			for(int j=0;j<PA.get(i).size();j++) {
				String[] parts = PA.get(i).get(j).split("-");
				if(attributeName.equalsIgnoreCase(parts[0])) {
					PA.get(i).remove(j);
				}
			}
		}
		for(int i=0;i<AA.size();i++) {
			for(int j=0;j<AA.get(i).size();j++) {
				String[] parts = AA.get(i).get(j).split("-");
				if(attributeName.equalsIgnoreCase(parts[0])) {
					AA.get(i).remove(j);
				}
			}
		}
				
	}
	
	public static void addPermission(String permissionName) {
		PERMS.add(permissionName);
	}
	
	public static void removePermission(String permissionName) {
		
		for(int i=0;i<PERMS.size();i++) {
			if(PERMS.get(i).equalsIgnoreCase(permissionName)) {
				PERMS.remove(i);
			}
		}
		for(int i=0;i<PA.size();i++) {
			for(int j=0;j<PA.get(i).size();j++) {
				if(permissionName.equalsIgnoreCase(PA.get(i).get(j))) {
					PA.remove(i);
				}
			}
		}
	}
	
	
	public static void addAtoP(String permissionName, String attributes) {
		ArrayList<String> list = new ArrayList<>();
		PA.add(list);
		int size = PA.size();

		PA.get(size-1).add(permissionName);
		
		String[] attribute = attributes.split(" ");
		for(int i=2;i<attribute.length-1;i++) {
			PA.get(size-1).add(attribute[i]+"-"+attribute[i+1]);
			i++;
		}
		
		
	}
	public static void removeAfromP(String permissionName, String attributeName, String attributeValue) {
		for(int i=0;i<PA.size();i++) {
			if(PA.get(i).get(0).equalsIgnoreCase(permissionName)) {
				for(int j=0; j<PA.get(i).size();j++) {
					if(PA.get(i).get(j).equalsIgnoreCase(attributeName+"-"+attributeValue)) {
						PA.get(i).remove(j);
					}
				}
			}
		}
	}
	
	public static void addAtoE(String entityName, String attributeName, String attributeValue) {
		for(int i=0;i<AA.size();i++) {
			for(int j=0;j<AA.get(i).size();j++) {
				if(AA.get(i).get(j).equalsIgnoreCase(entityName)) {
					AA.get(i).add(attributeName+"-"+attributeValue);
				}
			}
		}
	}
	
	public static void removeAfromE(String entityName, String attributeName, String attributeValue) {
		for(int i=0;i<AA.size();i++) {
			for(int j=0;j<AA.get(i).size();j++) {
				if(AA.get(i).get(0).equalsIgnoreCase(entityName)) {
					if(AA.get(i).get(j).equalsIgnoreCase(attributeName+"-"+attributeValue)) {
						AA.get(i).remove(j);
					}
				}
			}
		}
	}
	

	public static void executeCommand(String command) throws Exception{
		
		String[] commandParts = command.split(" ");

		switch (commandParts[0]){

		case "load-policy": loadPolicy(commandParts[1]); break;

		case "show-policy": showPolicy(); break;
		
		case "check-permission": checkPermission(commandParts[1], commandParts[2], commandParts[3], commandParts[4]); break;
		
		case "add-entity": addEntity(commandParts[1]); break;
		
		case "remove-entity": removeEntity(commandParts[1]); break;
		
		case "add-attribute": addAttribute(commandParts[1], commandParts[2]); break;
		
		case "remove-attribute": removeAttribute(commandParts[1]); break;
		
		case "add-permission": addPermission(commandParts[1]); break;
		
		case "remove-permission": removePermission(commandParts[1]); break;

		case "add-attributes-to-permission": addAtoP(commandParts[1], command); break;
		
		case "remove-attribute-from-permission": removeAfromP(commandParts[1], commandParts[2], commandParts[3]); break;

		case "add-attribute-to-entity": addAtoE(commandParts[1], commandParts[2], commandParts[3]); break;
		
		case "remove-attribute-from-entity": removeAfromE(commandParts[1], commandParts[2], commandParts[3]); break;

		default: System.err.println("Unrecognized command!"); break;
		}
	}

}


