package core;

import java.io.BufferedReader;
import java.io.IOException;

public class InputReader implements Runnable {
	public interface MessageCallback {
		void messageReceived(String message);
	}

	private BufferedReader in;
	private MessageCallback messageCallback;

	public InputReader(BufferedReader in, MessageCallback messageCallback) {
		this.in = in;
		this.messageCallback = messageCallback;
	}

	@Override
	public void run(){
		String line;
		try{
			while((line = in.readLine()) != null){
				messageCallback.messageReceived(line);
			}
		} catch(IOException e){
			e.printStackTrace();
		}
	}
}