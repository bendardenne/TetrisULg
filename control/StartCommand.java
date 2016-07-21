package control;

import java.awt.event.ActionEvent;

public class StartCommand extends Command {

	private final Controller target;

	public StartCommand(Controller target)
	{
		super("Start");
		this.target	= target;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{	
		if(target.remote() && e.getSource() != target.getRemote())
			return;
		
		target.start();
	}

	@Override
	public String toString() 
	{
		return "Start";
	}
}
