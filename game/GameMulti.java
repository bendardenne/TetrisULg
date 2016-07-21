package game;

import java.util.Set;

public class GameMulti extends Game {

	public GameMulti(int players_no, int width, int height, Set<Integer> seeds)
	{
		super(players_no, width, height, seeds);
	}

	@Override
	public void lineAdded(Player target)
	{
		GameGrid playerGrid = target.getGrid();
		target.scoreLines(playerGrid.checkFullLines());
	}

}
