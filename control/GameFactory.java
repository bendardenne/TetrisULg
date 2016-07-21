package control;

import game.Game;
import game.GameCoop;
import game.GameMulti;
import game.GameSingle;
import game.GameVS;
import static game.Mode.MULTI;
import static game.Mode.MULTI_COOP;
import static game.Mode.MULTI_VS;
import static game.Mode.SINGLE;
import java.util.Set;

public class GameFactory {
	
	public static Game getGame(Controller controller, Set<Integer> s)
	{
		int no = controller.getNumberPlayers();
		int width = controller.getWidth();
		int height = controller.getHeight();

		/* If the game is remote, use PlayerIDs as seeds, otherwise use whatever was given
		 	(s may be null).  */
		Set<Integer> seeds = controller.remote() ? controller.getRemote().getAllIDs() : s;
		Game game = null;
		
		switch(controller.getMode())
		{
			case SINGLE:
				game = new GameSingle(width, height);
				break;

			case MULTI:
				game = new GameMulti(no, width, height, seeds);
				break;

			case MULTI_VS:
				game = new GameVS(no, width, height, seeds);
				break;

			case MULTI_COOP:
				game = new GameCoop(no, width, height, seeds);
				break;
		}
	
		return game;
	}
	
}
