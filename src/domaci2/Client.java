package domaci2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	
	private static final int PORT = 2018+54;
	private String host = "127.0.0.1";
	private Socket socket;
	
	public Client() throws UnknownHostException, IOException {
		socket = new Socket(host, PORT);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
		
		Scanner sc = new Scanner(System.in);
		
		String string;
		out.println("Client ");
		
		string = in.readLine();
		System.out.println("[Server]:" + string);
		
		//To enter new contact just type: name surname phone
		//							e.g.: John Dohn +1 555 555
		while(true) {
			System.out.print("[Client]: ");
			string = sc.nextLine();
			
			if (string.equalsIgnoreCase("EXIT")) {
				out.println(string);
				System.out.println("You succesfully ended TCP connection.");
				break;
			}
			
			else if (string.equalsIgnoreCase("LIST")) {
				out.println(string);
				//It's not actually the server, but this is just for esthetics.
				System.out.println("[Server]: All contacts are:" );
				while(!(string = in.readLine()).equals("STOP")) {
					System.out.println("[Server]: " + string);
				}
			}
			
			else {
				out.println(string);
			}
			
			System.out.println("[Server]: " + in.readLine());
		}
		
		sc.close();
		in.close();
		out.close();
		socket.close();
		
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		new Client();
	}

}
