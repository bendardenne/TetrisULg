package network;

import control.Command;
import control.PlayerController;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

public class ServerThread extends Thread {
	
	private final Server server;
	private final Socket socket;
	private final int ID;
	private PlayerController player;
	private ObjectOutputStream os;
	private ObjectInputStream is;
	private boolean keepRunning = true;
	
	public ServerThread(Server server, Socket socket, int ID) 
	{
		this.server = server;
		this.socket = socket;
		this.ID = ID;
		try{ 
			os = new ObjectOutputStream(socket.getOutputStream());
			is = new ObjectInputStream(socket.getInputStream());
		} catch(IOException e) {}
	}

	public void send(Message message) 
	{
		try {
			os.reset();
			os.writeObject(message);
			os.flush();
		} catch (IOException e) {
			this.disconnect();
		}
	
	}
	
	@Override
	public void run() 
	{
		while(keepRunning)
		{
			try {
				Message m = (Message) is.readObject();
				Command command = CommandFactory.getCommand(m, server.getController(), player);
				// Perform action locally
				command.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
				// Broadcast
				server.broadcast(m);
			} catch(IOException | ClassNotFoundException e) {
					this.disconnect();
					return;
			}
		}
	}

	public void disconnect()
	{
		Logger.getGlobal().info("Client disconnected.");
		try {
			// Stop emitting ticks.
			player.getTimer().stop(); 
			os.close();
			is.close();
			socket.close();
		} catch (IOException e) {}
		finally {
			server.removeClient(this.ID);
			keepRunning = false;
		}
	}
	
	public void setPlayerController(PlayerController player)
	{
		this.player = player;
		// Add server ticks
		player.getTimer().addActionListener(new ServerTick(ID, server));
	}
}
