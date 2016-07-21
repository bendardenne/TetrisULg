package control;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.CommandFactory;
import network.Message;


public class RemoteController extends Thread  {

	private final Controller controller;
	private final Socket socket;
	private final ObjectOutputStream os;
	private final ObjectInputStream is;
	private int ID;
	private Set<Integer> IDs;
	private Map<Integer, PlayerController> players;
	private boolean keepGoing = true;
	
	public RemoteController(Controller controller, String host, int port) throws IOException
	{
		this.controller = controller;
		this.socket = new Socket(host, port);
		os = new ObjectOutputStream(socket.getOutputStream());
		is = new ObjectInputStream(socket.getInputStream());
	} 

	// Receive messages
	@Override
	public void run()
	{
		while(keepGoing) 
		{
			try {
				Message m = (Message) is.readObject();
				Command command = CommandFactory.getCommand(m, controller,
					(players == null ? null : players.get(m.getSource())));
				command.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
				
				if(controller.getGame() != null && controller.getGame().gameOver())
					controller.disconnectRemote();
			} catch(IOException e) {
				// Remote has likely disconnected
				controller.disconnectRemote();
			} 
			catch (ClassNotFoundException e) {
				System.err.println("Serialization failed");
				return;
			}
		}
	}

	public void send(Message message)
	{
		try{
			os.reset();
			os.writeObject(message);
			os.flush();
		} catch(IOException e) {
			throw new RuntimeException("Could not send message.");
		}
	}

	public void disconnect()
	{
		try {
			os.close();
			is.close();
			socket.close();
		} catch (IOException ex) {
			Logger.getLogger(RemoteController.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			keepGoing = false;
		}
	}
	
	public void setID(int ID) { this.ID = ID; }
	public void setIDs(Set<Integer> IDs) { this.IDs = IDs; }
	public void setPlayers(List<PlayerController> players)
	{
		assert IDs.size() == players.size();
		Iterator<PlayerController> it = players.iterator();
		this.players = new HashMap<>();
		for(Integer i : IDs)
			this.players.put(i, it.next());
	}

	public PlayerController getLocalPlayer() { return players.get(ID); }
	public int getID() { return ID; }
	public Set<Integer> getAllIDs() { return IDs; }
}
