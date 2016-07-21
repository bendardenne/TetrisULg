package control;

import java.awt.event.ActionEvent;
import network.Message;
import network.MessageType;

public class HarddropCommand extends Command {
	
	private PlayerController target;

	public HarddropCommand() {}

	public HarddropCommand(PlayerController target)
	{
		this.target = target;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(!target.playing())
			return;

		if(target.getController().remote() && e.getSource() != target.getController().getRemote())
		{
			target.getController().getRemote().send(new Message(
				target.getController().getRemote().getID(), 
				MessageType.HARDDROP, null));	
			return;
		}

		target.getPlayer().getGrid().harddrop();
		target.addCurrentToGrid();
	}
	
	@Override
	public String toString()
	{
		return "Harddrop";
	}

}
