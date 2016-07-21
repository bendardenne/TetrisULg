package control;

import game.Block;
import game.GameGrid;
import java.awt.Point;
import java.util.LinkedList;

public class Searcher {

	private final GameGrid grid;
	private boolean[][][] visited;

	public Searcher(GameGrid grid)
	{
		this.grid = grid;
	}

	public State getBestState()
	{
		State best;
		double bestScore = Double.NEGATIVE_INFINITY;
		visited = new boolean[grid.getWidth() + 4][grid.getHeight() + 4][grid.getTetromino().rotations()];

		/* Add 4 rows and columns to account for the fact that
		  	tetrominos are contained in a 4x4 square. So x positions
			may be negative and y may be greater than grid height. */
		for(int i = 0; i < grid.getWidth() + 4; i++)
			for(int j = 0; j < grid.getHeight() + 4; j++)
				for(int k = 0; k < grid.getTetromino().rotations(); k++)
					visited[i][j][k] = false;

		LinkedList<State> queue = new LinkedList<>();
		// First state is spawn state
		queue.push(new State(grid.currentPosition().x, grid.currentPosition().y, 0));
		best = queue.get(0);

		queue.push(new State(grid.currentPosition().x, grid.currentPosition().y, 0));
		
		// While some states have not been tested, keep going
		while(!queue.isEmpty())
		{
			// Try a set, set the appropriate variables
			State current = queue.pop();
			visited[current.x][current.y][current.rotation] = true;

			Point oldPosition = new Point(grid.currentPosition());

			grid.setPosition(current.x - 4, current.y);
			for(int i = 0; i < current.rotation; i++)
				grid.getTetromino().rotate(true);

			// Valid state ?
			if(grid.currentHasValidPosition())
			{
				if(grid.currentHasReachedBottom())
				{
					// This state is locked, score it
					double score = evaluate();
					if(score > bestScore)
					{
						bestScore = score;
						best = current;
					}
				}

				else
				{
					// Enqueue first next state, i.e. the piece being moved down one line
					if(!visited[current.x][current.y + 1][current.rotation])
						queue.push(new State(current.x, current.y + 1, current.rotation));
				}
			}

			// Enqueue the second next state, i.e. piece move to the right
			if(!visited[(current.x + 1) % (grid.getWidth() + 4)][current.y][current.rotation])
				queue.push(new State((current.x + 1) % (grid.getWidth() + 4), current.y, current.rotation));

			// Third next state, i.e. piece rotated
			if(!visited[current.x][current.y][(current.rotation + 1) % grid.getTetromino().rotations()])
				queue.push(new State(current.x, current.y, (current.rotation + 1) % grid.getTetromino().rotations()));

			// Restore original state.
			grid.setPosition(oldPosition.x, oldPosition.y);
			for(int i = 0; i < current.rotation; i++)
				grid.getTetromino().rotate(false);
		}

		// Correct x position
		best.x -= 4;
		
		return best;
	}

	private double evaluate()
	{
		Block[][] blocks = grid.getBlocks();
		int width = blocks.length;
		int height = blocks[0].length;

		int blocksHeights = 0, blockades = 0, floor = 0, clears = 0, holes = 0, clear = 0;
		for(int j = 0; j < height; j++)
		{
			clear = 0;
			for(int i = 0; i < width; i++)
			{
				if(blocks[i][j] != null)
				{
					clear++;
					blocksHeights += (height - j);
					if(j + 1 < height && blocks[i][j + 1] == null)
					{
						for (int z = j; z >= 0; z--)
						{
							if (blocks[i][z] == null)
								break;
							blockades++;
						}
					}
					if(j + 1 == height)
						floor++;
				}

				if (j > 0 && blocks[i][j - 1] != null)
				{
					for (int z = j; z < height; z++)
					{
						if (blocks[i][z] != null)
							break;
						holes++;
					}
				}
			}
			if (clear == width)
				clears++;
		}

		return 10 * clears - 3 * blocksHeights - 8 * holes - 8 * blockades + 8 * floor;
	}
}
