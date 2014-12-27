package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import core.InputReader.MessageCallback;

public class NetSyncServer {
	private static final int SERVER_PORT = 31415;
	private static List<BufferedReader> ins = new ArrayList<>();
	private static List<PrintWriter> outs = new ArrayList<>();

	public static void main(String[] args){
		ServerSocket server = null;
		int clientNum = 0;
		try{
			server = new ServerSocket(SERVER_PORT);
			System.out.println("===== SERVER LISTENING ON PORT " + SERVER_PORT + " =====");
			while (true) {
				Socket client = server.accept();		
				System.out.println("CONNECTED TO CLIENT #" + clientNum + " @" + client.getInetAddress());
				
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintWriter out = new PrintWriter(client.getOutputStream(), true);

				ins.add(in);
				outs.add(out);
				
				out.println("HELLO|" + clientNum);
				
				new Thread(new InputReader(in, new MessageCallback() {
					@Override
					public void messageReceived(String message){
						String[] words = message.split("\\|");
						int clientNum = Integer.parseInt(words[0]);
						for (int i=0; i < outs.size(); i++) {
							if (i != clientNum) {
								outs.get(i).println(message.substring(message.indexOf("|")+1));
							}
						}
					}
				})).start();
				
				clientNum++;
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
