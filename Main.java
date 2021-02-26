
/*Nhut Trinh-Assignment1*/
public class Main extends ABACMonitor {

	public static void main(String[] args) throws Exception {

		String commandLine = "";

		for(String arg: args){
			commandLine += " " + arg;
		}
		
		String[] commands = commandLine.split(";");

		for(String command: commands){

			command = command.trim();

			executeCommand(command);

	}

}
}
