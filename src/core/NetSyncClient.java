package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
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
					String[] words = message.split("\\|");
//					System.err.println("[client #" + words[0] + "]: " + message.substring(message.indexOf("|") + 1));
					int x = Integer.parseInt(words[1]);
					int y = Integer.parseInt(words[2]);
					RobotHandler.getRobot().mouseMove(x, y);
				}
			})).start();
			
			Scanner kb = new Scanner(System.in);
			while (kb.hasNextLine()) {
				String message = kb.nextLine();
				out.println(clientNum + "|" + message);
			}
			kb.close();
		} catch (SocketException e) {
			System.err.println("Server disconnected. Exiting...");
			System.exit(1);
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
