package control;

import game.Mode;
import java.awt.event.ActionEvent;

public class SetModeCommand extends Command {

	private final Controller target;
	private final int players;
	private final Mode mode;

	public SetModeCommand(Controller target, Mode mode, int players, String name)
	{
		super(name);
		this.target = target;
		this.players = players;
		this.mode = mode;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		target.setNumberPlayers(players);
		target.setMode(mode);
	}
}
