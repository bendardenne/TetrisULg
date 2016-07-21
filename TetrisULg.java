import control.Controller;
import game.Mode;
import network.Server;
import view.GameWindow;
public class TetrisULg {

	public static void main(String[] args)
	{
		if(args.length >= 3 && args[0].equals("--s"))
		{
			Mode mode = Mode.MULTI;
			switch(args[1]) {
				case "simple": 
					mode = Mode.MULTI;
					break;
				case "vs":
					mode = Mode.MULTI_VS;
					break;
				case "coop":
					mode = Mode.MULTI_COOP;
					break;
				default:
					System.err.println("Invalid game mode: " + args[1] );
					System.exit(-1);
			}
		
			int port = Integer.parseInt(args[2]);
			int players = 2;
		    if(args.length >= 4) players = Integer.parseInt(args[3]);
			
			// Server is blocking
			Server server = new Server(port, players, mode);
		}
		Controller controller = new Controller();
		GameWindow window = new GameWindow(controller);
	}
}
