package network;


import control.Controller;
import control.GameFactory;
import control.PlayerController;
import game.Mode;
import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
// import view.ServerView;

public class Server {

	private ServerSocket socket;
	private final Map<Integer, ServerThread> clients;
	private final Controller controller;
	private final HashSet<Integer> keys;
	private static final Logger log = Logger.getGlobal(); 
 	// private final ServerView view;  // Uncomment for ServerView
	
	public Server(int port, int players_no, Mode mode) 
	{
		this.clients = new HashMap<>();
		this.keys = new HashSet<>();
		controller = new Controller(mode, players_no);
		//view = new ServerView(controller);  // Uncomment for ServierView
		
		try {
			FileHandler simpleHandler = new FileHandler("messages.log", true);
            simpleHandler.setFormatter(new SimpleFormatter());
            log.addHandler(simpleHandler);
			log.setLevel(Level.INFO);
		} catch(IOException e) {
			System.err.println("Could not reach log file");
		}
		
		try {
			socket = new ServerSocket(port);
		} catch(IOException e) {
			log.severe("Could not start server on port " + port);
			System.exit(-1);
		}

		log.info("Started server on port " + port + " with mode " + mode + " " + players_no + " players.");
		
		while(true)
			play();
	}

	private void play()
	{
		keys.clear();
		clients.clear();

		// wait until enough clients are connected
		while(clients.size() != controller.getNumberPlayers())
		{
			try {
				Socket client = socket.accept();
				log.info("Client connected.");

				// This is used both as an identifier for the client and as random seed for the game.
				int key = (int) System.nanoTime(); 
				ServerThread newClient = new ServerThread(this, client, key);
			
				// Inform client of its ID and parameters of the game 
				Serializable[] args = new Serializable[5];
				args[0] = key;
				args[1] = controller.getMode();
				args[2] = controller.getNumberPlayers();
				args[3] = controller.getWidth();
				args[4] = controller.getHeight();
				newClient.send(new Message(0, MessageType.HANDSHAKE, args));
				
				clients.put(key, newClient);
				keys.add(key);
				newClient.start();
			} catch (IOException e) { 
				log.severe(e.getMessage());
			}
		}
			
		controller.setGame(GameFactory.getGame(controller, keys));
		
		// Set the PlayerController locally associated with each client
		Iterator<PlayerController> pc = controller.getPlayers().iterator();
		for(Integer key : keys)
		{
			PlayerController current_pc = pc.next();
			clients.get(key).setPlayerController(current_pc);
		}

		// Inform all clients of the seeds to use for the game
		Serializable[] args = new Serializable[1];
		args[0] = keys;
		broadcast(new Message(0, MessageType.SETPLAYERS, args));

		// Start the game locally, then notify clients to do the same.
		controller.start();
		broadcast(new Message(0, MessageType.START, null));

		// Wait until game is over
		while(!controller.getGame().gameOver())
			try{
				Thread.sleep(2);
			} catch (InterruptedException e) {}
		
		log.info("Game Over.");
		// Clients should be disconnecting.	
	}
	
	// Broadcast a message to all threads
	public synchronized void broadcast(Message message)
	{
		log.info("Broadcasting message: " + message.getType() + " from " + message.getSource());
		
		for(ServerThread c : clients.values())
			c.send(message);
	}

	public synchronized void removeClient(int ID) { clients.remove(ID); }
	
	public Controller getController() { return controller; }

}
