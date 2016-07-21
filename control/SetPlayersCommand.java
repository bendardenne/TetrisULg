package control;

import java.awt.event.ActionEvent;
import java.util.HashSet;

public class SetPlayersCommand extends Command {
	
	private final Controller target;
	private final HashSet<Integer> IDs;

	public SetPlayersCommand(Controller target, HashSet<Integer> IDs)
	{
		this.target	= target;
		this.IDs = IDs;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		target.getRemote().setIDs(IDs);
	}
	
	@Override
	public String toString()
	{
		return "SetPlayers";
	}
}