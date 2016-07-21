package control;

import game.Mode;
import java.awt.event.ActionEvent;

public class HandshakeCommand extends Command {
	
	private final Controller controller;
	private final int ID, no, width, height;
	private final Mode mode;

	public HandshakeCommand(Controller controller, int ID, Mode mode, int no, int width, int height)
	{
		this.controller = controller;
		this.ID = ID;
		this.no = no;
		this.mode = mode;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		controller.getRemote().setID(ID);
		controller.setMode(mode);
		controller.setNumberPlayers(no);
		controller.setWidth(width);
		controller.setHeight(height);
	}
	
}
