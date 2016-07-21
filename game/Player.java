package game;

import java.util.Observable;

public class Player extends Observable {

	private String name;
	private int score, totalLines;
	private final GameGrid grid;

	public Player(int gridWidth, int gridHeight, String name)
	{
		grid = new GameGrid(gridWidth, gridHeight);
		this.name = name;
	}

	public Player(int gridWidth, int gridHeight, int seed, String name)
	{
		grid = new GameGrid(gridWidth, gridHeight, seed);
		this.name = name;
	}

	public void reset()
	{
		score = 0;
		totalLines = 0;
	}

	public void scoreLines(int added)
	{
		if(added == 0)
			return;

		totalLines += added;

		int scoreLines = 0;
		switch(added)
		{
			case 1:
				scoreLines = getLevel() * 40;
				break;
			case 2:
				scoreLines = getLevel() * 100;
				break;
			case 3:
				scoreLines = getLevel() * 300;
				break;
			default:
				scoreLines = getLevel() * added * 300;
				break;
		}

		score += scoreLines;

		this.setChanged();
		this.notifyObservers();
	}


	public int getLevel() { return (int) Math.ceil(totalLines / 10.0); };
	public int getScore() { return score; }
	public GameGrid getGrid() { return grid; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

}
