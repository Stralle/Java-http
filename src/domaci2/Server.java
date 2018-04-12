package domaci2;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	
	private ServerSocket serverSocket;
	private static final int PORT= 2072;
	private int clientNum = 0;
	public static ArrayList<Contact> contacts = new ArrayList<>();
	
	public Server() throws IOException {
		serverSocket = new ServerSocket(PORT);
		System.out.println("Server opened at port " + PORT);
		
		while(true) {
			Socket client = serverSocket.accept();
			
			System.out.println("Accepted client " + ++clientNum);
		
			ServerThread serverThread = new ServerThread(client, clientNum);
			Thread thread = new Thread(serverThread);
			thread.start();
		}
		
	}
	
	public synchronized static void addContact(String request) {
		String n[] = request.split(" ");
		Contact c;
		if(n.length < 3) {
			c = new Contact(n[0], "", n[1]);
		}
		else if (n.length == 3) {
			c = new Contact(n[0], n[1], n[2]);
		}
		else {
			String name = n[0];
			String last = "";
			for(int i = 1; i < n.length-1; i++) {
				last += n[i];
				last += " ";
			}
			String con = n[n.length-1];
			c = new Contact(name, last, con);
		}
		contacts.add(c);
	}
	
	public synchronized static void listAllContacts(PrintWriter o) {
		PrintWriter out = o;
		for(Contact c: Server.contacts) {
			out.println(c.getName() + " " + c.getSurname() + " " + c.getContact());
		}
		out.println("STOP");
	
	}
	
	public synchronized static String listAllContactsHTML() {
		String s = "";
		s += "<table>";
		for(Contact c: Server.contacts) {
			s += "<tr>\n";
			s += "<td>" + c + "</td>\n";
			s += "</tr>\n";
			
		}
		s += "</table>";
		
		return s;
	}
	
	public static void main(String[] args) throws IOException {
		new Server();
	}

}
