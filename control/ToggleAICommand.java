package control;

import java.awt.event.ActionEvent;

public class ToggleAICommand extends Command {

	private final PlayerController target;

	public ToggleAICommand(PlayerController target)
	{
		super(target.getPlayer().getName());
		this.target = target;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		target.toggleAI();
	}
}
