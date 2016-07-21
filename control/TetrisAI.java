package control;

import game.GameGrid;

public class TetrisAI extends Thread {

	private final PlayerController player;
	private int speed;
	private volatile boolean run = true;

	public TetrisAI(PlayerController player, int speed)
	{
		this.player = player;
		this.speed = speed;
	}

	public void setSpeed(int speed){ this.speed = speed; }
	
	public void halt()
	{
		run = false;
	}
	
	@Override
	public void run()
	{
		while(run)
		{
			if(player.playing())
			{
				Searcher searcher = new Searcher(new GameGrid(player.getPlayer().getGrid()));
				control.State bestState = searcher.getBestState();

				player.getPlayer().getGrid().setPosition(bestState.x, bestState.y);

				for(int i = 0; i < bestState.rotation; i++)
					player.getPlayer().getGrid().getTetromino().rotate(true);

				player.addCurrentToGrid();
				new TickCommand(player).actionPerformed(null);
			}

			if(player.getGame().gameOver())
				break;

			try {
				Thread.sleep(speed);
			} catch(InterruptedException e) {}
		}
	}
}
