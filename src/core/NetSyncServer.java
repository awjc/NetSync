package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class NetSyncServer {
	private static final int SERVER_PORT = 31415;
	
	public static void main(String[] args){
		ServerSocket server = null;
		int clientNum = 1;
		try{
			server = new ServerSocket(SERVER_PORT);
			System.out.println("===== SERVER LISTENING ON PORT " + SERVER_PORT + " =====");
			while (true) {
				Socket client = server.accept();		
				System.out.println("CONNECTED TO CLIENT #" + clientNum + " @" + client.getInetAddress());
				clientNum++;
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintWriter out = new PrintWriter(client.getOutputStream(), true);
				
				out.println("Hola\r\n");
				String s = in.readLine();
				System.out.println("SERVER RECEIVED: \"" + s + "\"");
			}
		} catch(IOException e){
			e.printStackTrace();
		} finally {
			if (server != null) {
				try{
					server.close();
				} catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
}
