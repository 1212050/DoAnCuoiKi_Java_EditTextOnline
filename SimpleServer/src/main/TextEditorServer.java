package main;
import java.net.ServerSocket;
import java.net.Socket;

import ResourceManager.ResoucesServerManager;
import Thread.HandleClientRunnable;


public class TextEditorServer {

	
	private final int port = 5555;
	
		
	public TextEditorServer()
	{
		ResoucesServerManager.setTextEditorServer(this);
	}
	
	public void startServer()
	{
		try
		{
			ServerSocket serverSocket = new ServerSocket(port);
			while(true)
			{
				
				Socket client = serverSocket.accept();
				
				Thread threadHandleClient = new Thread(new HandleClientRunnable(client));
				
				threadHandleClient.start();
				System.out.println("Have a connect");
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TextEditorServer server = new TextEditorServer();
		server.startServer();
	}
	
}
