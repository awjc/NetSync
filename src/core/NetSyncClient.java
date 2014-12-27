package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NetSyncClient {
	private static final int PORT_NUM = 31415;

	public static void main(String[] args){
		Socket server = null;
		try{
			String host = "localhost";
			server = new Socket(host, PORT_NUM);
			System.out.println("===== CONNECTED TO SERVER AT " + host + ":" + PORT_NUM + " =====");
			BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
			PrintWriter out = new PrintWriter(server.getOutputStream(), true);
			
			String s = in.readLine();
			System.out.println("CLIENT RECEIVED: \"" + s + "\"");
			out.println("OHAI");
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
