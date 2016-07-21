package control;

import game.Game;
import game.Mode;
import game.Player;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Observer;

public class Controller {

	private Game game;
	private final List<PlayerController> players;
	private final List<Observer> observers;
	private int width = 10, height = 22;
	private RemoteController remote;
	private Mode mode = Mode.SINGLE;
	private int players_no;
	private String host = "localhost";
	private int port = 8005;

	public Controller()
	{
		this.players_no = 1;
		observers = new LinkedList<>();
		players = new LinkedList<>();
	}
	
	public Controller(Mode mode, int players_no)
	{
		this();
		this.mode = mode;
		this.players_no = players_no;
	}

	public void start()
	{
		if(game == null || game.gameOver())
			newGame();
	
		for(Observer o : observers)
			game.addObserver(o);
		
		for(PlayerController pc : players)
			pc.start();
	}

	public void newGame()
	{
		// We need players with the right seeds if remote game
		game = GameFactory.getGame(this, remote() ? remote.getAllIDs() : null);
		setGame(game);
	}
	
	public void setGame(Game game) 
	{ 
		this.game = game; 
		players.clear();

		if(game == null)
			return;
		
		for(Player p : game.getPlayers())
		{
			PlayerController pc = new PlayerController(this, p);
			players.add(pc);
		}

		if(remote())
			remote.setPlayers(players);
	}
	
	public void addObserver(Observer o) { observers.add(o); }

	public void connectToRemote()
	{
		if(remote())
			disconnectRemote();

		try {
			game = null;
			remote = new RemoteController(this, host, port);
			remote.start();
		} catch (IOException e) {
			System.err.println("Could not connect to server: " + host);
			remote = null;
		}
	}

	public void disconnectRemote()
	{
		if(!remote())
			return;

		remote.disconnect();
		remote = null;
	}
	
	public boolean playing()  { 
		return game != null && !game.paused() && !game.gameOver();
	}


	public boolean remote() {
		return remote != null;
	}
	
	public void setMode(Mode mode) { this.mode = mode; }
	public void setWidth(int width){ this.width = width; }
	public void setHeight(int height){ this.height = height; }
	public void setHost(String host, int port){ this.host = host; this.port = port;}
	public void setNumberPlayers(int players_no) { this.players_no = players_no; }

	public int getNumberPlayers() { return players_no; }
	public List<PlayerController> getPlayers(){ return players; }
	public Game getGame(){ return game; }
	public Mode getMode(){ return mode; }
	public int getWidth(){ return width; }
	public int getHeight(){ return height; }
	public String getHost(){ return host; }
	public int getPort(){ return port; }
	public RemoteController getRemote(){ return remote; }

}
