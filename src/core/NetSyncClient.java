package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import core.InputReader.MessageCallback;

public class NetSyncClient {
	private static final int PORT_NUM = 31415;

	public static void main(String[] args){
		Socket server = null;
		try{
			String host = "localhost";
			if (args.length > 0) {
				host = args[0];
			}
			server = new Socket(host, PORT_NUM);
			System.out.println("===== CONNECTED TO SERVER AT " + host + ":" + PORT_NUM + " =====");
			BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
			PrintWriter out = new PrintWriter(server.getOutputStream(), true);
			
			int clientNum = -1;
			String s = in.readLine();
			String[] words = s.split("\\|");
			clientNum = Integer.parseInt(words[1]);
			
			new Thread(new InputReader(in, new MessageCallback(){
				@Override
				public void messageReceived(String message){
					System.err.println("MESSAGE RECEIVED: " + message);
				}
			})).start();
			
			Scanner kb = new Scanner(System.in);
			while (kb.hasNextLine()) {
				String message = kb.nextLine();
				out.println(clientNum + "|" + message);
			}
			kb.close();
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
