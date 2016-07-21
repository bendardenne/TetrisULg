package control;

import game.GameGrid;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import network.Message;
import network.MessageType;
import util.Direction;

public class MoveCommand extends Command {

	private final PlayerController target;
	private final Direction direction;

	public MoveCommand(PlayerController target, Direction direction)
	{
		this.target = target;
		this.direction = direction;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(!target.playing())
			return;
		
		if(target.getController().remote() && target.getController().getRemote() != e.getSource())
		{
			Serializable[] args = new Serializable[1];
			args[0] = direction;
			target.getController().getRemote().send(new Message(
				target.getController().getRemote().getID(), 
				MessageType.MOVE, 
				args));	
			return;
		}
		
		GameGrid playerGrid = target.getPlayer().getGrid();
		if(direction == Direction.DOWN && playerGrid.currentHasReachedBottom())
			return;

		playerGrid.moveTetromino(direction);
	}

	@Override
	public String toString()
	{
		return "Move";
	}

}
