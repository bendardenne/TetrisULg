package control;

import game.Game;
import game.Player;
import javax.swing.Timer;

public class PlayerController {

	private final Controller controller;
	private Player player;
	private final Timer timer;
	private TetrisAI ai;
	private int AISpeed = 1000;
	private final static int START_DELAY = 900;

	public PlayerController(Controller controller, Player player)
	{
		this.controller = controller;
		this.player = player;
		timer = new Timer(START_DELAY, new TickCommand(this));
	}

	public void start()
	{
		timer.start();
	}

	// Fixes the line in the grid and notify the game
	// that a line has been added for this player
	public void addCurrentToGrid()
	{
		player.getGrid().addCurrentToGrid();
		controller.getGame().lineAdded(player);
	}

	public boolean playing() { return controller.playing(); }

	public void togglePause()
	{
		controller.getGame().togglePause(player);
	}

	public void toggleAI()
	{
		if(controller.remote())
			return;
		
		if(ai == null) 
		{
			ai = new TetrisAI(this, AISpeed);
			ai.start();
		}
		else
		{
			ai.halt();
			ai = null;
		}
	}

	public void setAISpeed(int speed)
	{ 
		this.AISpeed = speed;
		
		if(ai != null) 
			ai.setSpeed(speed);
	}

	public void setPlayer(Player player){ this.player = player; }

	public Controller getController(){ return controller; }
	public Game getGame(){ return controller.getGame(); }
	public Player getPlayer(){ return player; }
	public Timer getTimer(){ return timer; }
	public int getAISpeed() { return AISpeed; }

}
