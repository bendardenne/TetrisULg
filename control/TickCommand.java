package control;

import game.GameGrid;
import java.awt.event.ActionEvent;
import util.Direction;

public class TickCommand extends Command {

	private final PlayerController target;

	public TickCommand(PlayerController target)
	{
		super("Tick");
		this.target	= target;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(!target.playing() || 
				(target.getController().remote() && target.getController().getRemote() != e.getSource()))
			return;
		
		GameGrid playerGrid = target.getPlayer().getGrid();
		if(playerGrid.currentHasReachedBottom())
		{
			target.addCurrentToGrid();
			return;
		}

		playerGrid.moveTetromino(Direction.DOWN);
		target.getTimer().setDelay(900 / (target.getPlayer().getLevel() + 1));  
	}
}
