package network;

import control.Command;
import control.Controller;
import control.HandshakeCommand;
import control.HarddropCommand;
import control.MoveCommand;
import control.PauseCommand;
import control.PlayerController;
import control.RotateCommand;
import control.SetPlayersCommand;
import control.StartCommand;
import control.TickCommand;
import game.Mode;
import java.util.HashSet;
import util.Direction;

public class CommandFactory {
	
	@SuppressWarnings("unchecked")  //HashSet<Integers>
	public static Command getCommand(Message message, Controller controller, PlayerController player)
	{
		switch(message.getType()){
			case HANDSHAKE:
				return new HandshakeCommand(controller, 
						(Integer) message.getArgs()[0], 
						(Mode) message.getArgs()[1], 
						(Integer) message.getArgs()[2],
						(Integer) message.getArgs()[3],
						(Integer) message.getArgs()[4]); 
			case SETPLAYERS:
				return new SetPlayersCommand(controller, (HashSet<Integer>) message.getArgs()[0]);
			case START:
				return new StartCommand(controller);
			case MOVE:
				return new MoveCommand(player, (Direction) message.getArgs()[0]);
			case ROTATE:
				return new RotateCommand(player);
			case HARDDROP :
				return new HarddropCommand(player);
			case PAUSE :
				return new PauseCommand(player);
			case TICK :
				return new TickCommand(player);
		}
		return null;
	}
	
}
