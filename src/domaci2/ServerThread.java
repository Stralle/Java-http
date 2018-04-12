package domaci2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread implements Runnable {

	private Socket client;
	private int clientID;
	private BufferedReader in;
	private PrintWriter out;
	//private ArrayList<Contact> contacts = new ArrayList<>();
	
	public ServerThread(Socket client, int clientNum) throws IOException {
		this.client = client;
		this.clientID = clientNum;
		
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())),true);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			//citanje zahteva
			String request = "";
			request = in.readLine();
			
			System.out.println("[REQUEST IS]: " + request);

			String response = "";
			response = createResponse(request);
//			System.out.println(request + "number: " + clientID + " is now connected.");
			//odgovor
//			out.println(" Your number is " + clientID + ". To add new contact enter: name surname phone."
//					+ "Commands are: EXIT, LIST");
			
//			while(!(request = in.readLine()).equalsIgnoreCase("")) {
//				
//				if(request.equalsIgnoreCase("LIST")) {
//					//TODO: list all contacts
//					Server.listAllContacts(out);
//				}
//				else {
//					Server.addContact(request);
//				}
//				out.println("Enter new contact, LIST or EXIT.");
//			}
//			
//			System.out.println("Client " + clientID + " has disconnected");
			
			//ovaj deo nam sluzi samo da bismo ispisali na konzoli servera ceo HTTP zahtev
			System.out.println("\nHTTP ZAHTEV KLIJENTA:");
			do{
				System.out.println(request);
				request = in.readLine();
			} while(!request.trim().equals(""));
			
			
			
			out.println(response);
			
			in.close();
			out.close();
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private String createResponse(String request){
		String retVal = "HTTP/1.1 200 OK\r\nConnection: Close\r\nContent-Type: text/html\r\n\r\n"; //Connection: close\r\n
//		if(request
//				.contains("favicon.ico")){
//			return "";
//		}
		System.out.println("[createResponse REQUEST IS]: " + request);
//		String name = request.substring(
//				request.indexOf("fullname=")+9, 
//				request.indexOf("cellphone=")-1);
		String contact = "";
		String button = "";
		String name = "";
		if(request == null) {
			request = "";
		}
		
		if(request.contains("add")) {
			name = request.substring(0, 
					request.indexOf("cellphone=")-1);
			name = name.substring(name.indexOf("fullname=") + 9);
			System.out.println("[FULL NAME]: " + name);
			
			if(name.contains("+")) {
				String[] strings = name.split("\\+");
				name = "";
				for(String s: strings) {
					name += s;
					name += " ";
				}
			}
			
			contact = request.substring(request.indexOf("cellphone=")+10, request.indexOf("add=")-1);
			if(contact.contains("+")) {
				String[] strings = contact.split("\\+");
				contact = "";
				for(String s: strings) {
					contact += s;
				}
			}
			
			button = request.substring(request.indexOf("add=")+4, request.indexOf("HTTP")-1);
			Server.addContact(name + " " + contact);
		}
		else if(request.contains("list")) {
//			contact = request.substring(request.indexOf("cellphone=")+10, request.indexOf("list=")-1);
			button = request.substring(request.indexOf("list=")+5, request.indexOf("HTTP")-1);
		}

		retVal += "<html><head><title>Server response</title></head>\n";
		retVal += "<body><h1>Add new contact or list them all</h1>\n";
		retVal += "<form action=\"http://localhost:2072\" method=\"GET\" name=\"forma\">\n" + 
				"		<table>\n" + 
				"			<tr>\n" + 
				"				<td>Name and Surname: </td>\n" + 
				"				<td><input type=\"text\" name = \"fullname\" placeholder = \"Name Surname\"></td>\n" + 
				"			</tr>\n" + 
				"			<tr>\n" + 
				"				<td>Cellphone: </td>\n" + 
				"				<td><input type=\"text\" name = \"cellphone\" placeholder = \"Cellphone\"></td>\n" + 
				"			</tr>\n" + 
				"			<tr>\n" + 
				"				<td>\n" + 
				"					<input type=\"submit\" style=\"background-color:blue; color:white; font-weight: bold;\" name=\"add\" value=\"ADD\">\n" + 
				"				</td>\n" + 
				"				<td>\n" + 
				"					<input type=\"submit\" style=\"background-color:green; color:white; font-weight: bold;\" name=\"list\" value=\"LIST\">\n" + 
				"				</td>\n" + 
				"			</tr>\n" + 
				"		</table>\n" + 
				"	</form>\n";
		if(button.equalsIgnoreCase("add")) {
			retVal += "<br>";
			retVal += "<p>You succesfully added a contact: " + name + " " + contact + ".</p>";
		}
//		else if(button.equalsIgnoreCase("list")) {
//			
//		}
		retVal += "<br>";
		if(Server.contacts.size()>0) {
			retVal += "<h3> All contacts are: <h3>";
			retVal += Server.listAllContactsHTML();
		}
		else {
			retVal += "<p style=\"color:red\">Add some contacts and they will show here!</p>";
		}
		retVal += "</body></html>";
		
		System.out.println("HTTP odgovor:");
		System.out.println(retVal);
		
		return retVal;
	}

}
