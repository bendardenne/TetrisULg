package game;

import java.util.Set;

public class GameVS extends Game {

	public GameVS(int players_no, int width, int height, Set<Integer> seeds)
	{
		super(players_no, width, height, seeds);
	}

	@Override
	protected void addPlayers(int no)
	{
		int seed = seeds != null ? seeds.iterator().next() : (int) System.nanoTime();
		for(int i = 0; i < no; i++)
			players.add(new Player(width, height, seed, "Player " + (i+1)));
	}

	@Override
	public void lineAdded(Player target)
	{
		GameGrid playerGrid = target.getGrid();

		int lines = playerGrid.checkFullLines();

		if (lines >= 2)
			for (Player p : players)
				if (p != target)
					p.getGrid().addlines(lines - 1);

		target.scoreLines(lines);
	}
}
