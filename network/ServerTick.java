package network;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class ServerTick extends AbstractAction {

	private final int source;
	private final Server server;

	public ServerTick(int source, Server server)
	{
		super("ServerTick");
		this.source = source;
		this.server = server;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(!server.getController().playing())
			return;

		server.broadcast(new Message(source, MessageType.TICK, null));
	}
}
