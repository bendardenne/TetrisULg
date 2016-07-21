package game;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import util.Stopwatch;

public abstract class Game extends Observable  {

	protected int width, height;
	protected Stopwatch stopwatch;
	protected boolean pause, gameOver = false;
	protected Player pauseHolder;
	protected List<Player> players;
	protected Set<Integer> seeds;

	public Game(int players_no, int width, int height)
	{
		this(players_no, width, height, null);
	}
	
	public Game(int players_no, int width, int height, Set<Integer> seeds)
	{
		this.width = width;
		this.height = height;
		this.seeds = seeds;
		this.players = new LinkedList<>();
		this.stopwatch = new Stopwatch();
		this.addPlayers(players_no);

		this.setChanged();
	}

	public abstract void lineAdded(Player player);

	public boolean gameOver()
	{
		if(gameOver)
			return true;
		
		for(Player p : players)
			if(p.getGrid().gameOver())
			{
				stopwatch.stop();
				return gameOver = true;
			}

		return false;
	}

	protected void addPlayers(int no)
	{
		if(seeds == null)
			for(int i = 0; i < no; i++)
				players.add(new Player(width, height, "Player " + (i + 1)));
		else
		{
			assert seeds.size() == no;
			int i = 0;
			for(Integer s: seeds)
			{
				i++;
				players.add(new Player(width, height, s, "Player " + (i + 1)));
			}
		}
	}

	public void togglePause(Player player)
	{
		if(pause && player != pauseHolder)
			return;

		pause = !pause;
		stopwatch.toggle();
		pauseHolder = player;
	}

	public void setPlayers(List<Player> players) { this.players = players; }
	
	public boolean paused(){ return pause; }
	public int getWidth(){ return width; }
	public int getHeight(){ return height; }
	public List<Player> getPlayers() { return players; }
	public Stopwatch getStopwatch() { return stopwatch; }

	@Override
	public void addObserver(Observer o)
	{
		super.addObserver(o);
		stopwatch.addObserver(o);
		this.notifyObservers();		// Start displaying the Game ASAP
	}

}
