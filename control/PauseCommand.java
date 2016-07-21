package control;

import java.awt.event.ActionEvent;
import network.Message;
import network.MessageType;

public class PauseCommand extends Command {

	private final PlayerController target;

	public PauseCommand(PlayerController target)
	{
		this.target = target;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(target.getController().remote() && target.getController().getRemote() != e.getSource())
		{
			target.getController().getRemote().send(new Message(
				target.getController().getRemote().getID(), 
				MessageType.PAUSE, 
				null));	
			return;
		}
		
		target.togglePause();
	}
	
	@Override
	public String toString()
	{
		return "Pause";
	}
}
