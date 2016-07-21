package game;

import java.awt.Point;
import java.util.Set;

public class GameCoop extends Game {

	public GameCoop(int players_no, int width, int height, Set<Integer> seeds)
	{
		super(players_no, width, height, seeds);
	}

	@Override
	public void lineAdded(Player target)
	{
		GameGrid playerGrid = target.getGrid();

		Point position = playerGrid.currentPosition();
		boolean match;
		int lines = 0;

		for (int j = position.y - 3; j <= position.y && j > 0 && j < height; j++)
		{
			match = true;
			for(Player p : players)
			{
				if (p.getGrid().checkLine(j) == false)
				{
					match = false;
					break;
				}
			}

			if(match)
			{
				lines++;
				for (Player p : players)
					p.getGrid().removeLine(j);
			}
		}

		for(Player p : players)
			p.scoreLines(lines);
	}

}
