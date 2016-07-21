package control;

import java.awt.event.ActionEvent;
import network.Message;
import network.MessageType;

public class RotateCommand extends Command {

	private final PlayerController target;

	public RotateCommand(PlayerController target)
	{
		this.target = target;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(!target.playing())
			return;
		
		if(target.getController().remote() && target.getController().getRemote() != e.getSource())
		{
			target.getController().getRemote().send(new Message(
				target.getController().getRemote().getID(), 
				MessageType.ROTATE, 
				null));	
			return;
		}
		
		target.getPlayer().getGrid().rotateTetromino(false);
	}
	
	@Override
	public String toString()
	{
		return "Rotate";
	}
}
