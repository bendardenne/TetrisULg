package control;

import java.awt.event.ActionEvent;

public class SetHostCommand extends Command {

	private final Controller target;
	private final String host;
	private final int port;

	public SetHostCommand(Controller target, String host, int port)
	{
		this.target	= target;
		this.host = host;
		this.port = port;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		target.setHost(host, port);
		target.connectToRemote();
	}
	
}
