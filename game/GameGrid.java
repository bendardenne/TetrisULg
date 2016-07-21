package game;

import java.awt.Point;
import java.util.Observable;
import util.Direction;

public class GameGrid extends Observable {

	private int height, width;
	private Block[][] blocks;
	private Tetromino current;
	private Tetromino next;
	private Point currentPosition;
	private boolean gameOver;
	private TetrominoFactory tetrominoFactory;
	private final int penaltyHolePosition;

	public GameGrid(int width, int height)
	{
		this(width, height, (int) System.nanoTime());
	}

	public GameGrid(int width, int height, int seed)
	{
		this.width = width;
		this.height = height;
		this.gameOver = false;
		this.currentPosition = new Point(0, width / 2);
		this.blocks = new Block[width][height];
		this.tetrominoFactory = new TetrominoFactory(seed);
		this.penaltyHolePosition = new util.Random().nextInt(width);
	}

	public GameGrid(GameGrid source)
	{
		this.width = source.width;
		this.height = source.height;
		this.gameOver = source.gameOver;
		this.currentPosition = (Point) source.currentPosition.clone();
		this.blocks = new Block[width][height];
		this.current = source.current;

		for(int i = 0; i < width; i++)
			System.arraycopy(source.blocks[i], 0, this.blocks[i], 0, height);

		this.tetrominoFactory = source.tetrominoFactory; //FIXME !!!!
		this.penaltyHolePosition = source.penaltyHolePosition;
	}

	// Moves current Tetromino. Returns true if the move was successful.
	public boolean moveTetromino(Direction dir)
	{
		if(current == null)
		{
			newTetromino();

			this.setChanged();
			this.notifyObservers();
			return true;
		}

		Point oldPosition = new Point(currentPosition);
		switch(dir)
		{
			case DOWN:
				currentPosition.y++;
				break;

			case LEFT:
				currentPosition.x--;
				break;

			case RIGHT:
				currentPosition.x++;
				break;

			default:
				break;
		}

		if(!currentHasValidPosition())
		{
			currentPosition = oldPosition;  // Cancel the move
			if(currentPosition.y < 0)	// new Tetromino that cannot be inserted -> game over
			{
				this.setChanged();
				this.notifyObservers();
				gameOver = true;
			}
			return false;
		}

		this.setChanged();
		this.notifyObservers();
		return true;
	}

	private void newTetromino()
	{
		if (next == null)
			next = tetrominoFactory.getNew();

		current = next;
		next = tetrominoFactory.getNew();

		currentPosition.y = 0;
		currentPosition.x = (width - current.getBlocks().length) / 2;
	}

	public boolean rotateTetromino(boolean clockwise)
	{
		if(current == null)
			return false;

		current.rotate(clockwise);

		if(!currentHasValidPosition())
		{
			current.rotate(!clockwise);
			return false;
		}

		this.setChanged();
		this.notifyObservers();
		return true;
	}

	public void harddrop()
	{
		while(!currentHasReachedBottom())
			moveTetromino(Direction.DOWN);
	}

	public Block[][] getBlocks()
	{
		Block[][] blocksTemp = new Block[width][height];

		for(int i = 0; i < width; i++)
			System.arraycopy(blocks[i], 0, blocksTemp[i], 0, height);

		if(current == null)
			return blocksTemp;

		// Add references to the blocks within the current Tetromino at
		// the approriate position of the array
		Block[][] currentBlocks = current.getBlocks();

		for(int i = 0; i < currentBlocks.length; i++)
		{
			for(int j = 0; j < currentBlocks[0].length; j++)
			{
				if(currentBlocks[i][j] != null)
				{
					// Block within Tetromino is outside grid, ignore it.
					if(currentPosition.y - currentBlocks[0].length + 1 + j < 0)
						continue;
					blocksTemp[currentPosition.x + i][currentPosition.y - currentBlocks[0].length + 1 + j] = currentBlocks[i][j];
				}
			}
		}

		return blocksTemp;
	}

	public Block[][] getNextBlocks()
	{
		if(next == null)
			return new Block[0][0];

		return next.getBlocks();
	}

	public void addCurrentToGrid()
	{
		Block[][] currentBlocks = current.getBlocks();

		for(int i = 0; i < currentBlocks.length; i++)
		{
			for(int j = 0; j < currentBlocks[0].length; j++)
			{
				if(currentBlocks[i][j] != null)
				{
					if(currentPosition.y - currentBlocks[0].length + 1 + j < 0)
					{
						gameOver = true;
						continue;
					}
					blocks[currentPosition.x + i][currentPosition.y - currentBlocks[0].length + 1 + j] = currentBlocks[i][j];
				}
			}
		}

		current = null;

		this.setChanged();
		this.notifyObservers();
	}

	public boolean currentHasReachedBottom()
	{
		if(current == null)
			return false;

		Block[][] currentBlocks = current.getBlocks();
		int tetrominoWidth = currentBlocks.length;
		int tetrominoHeight = currentBlocks[0].length;

		for(int i = 0; i < tetrominoWidth; i++)
		{
			for(int j = 0; j < tetrominoHeight; j++)
			{
				if(currentBlocks[i][j] != null)
				{
					// Block has not yet appeard on the grid and is not on the first line above the grid
					// (since the first line may reach the "bottom" if the blocks stack up to the top of the grid)
					if(currentPosition.y - tetrominoHeight + 1 + j < -1)
						continue;

					// Block has reached bottom of grid  || Block has reached an existing block
					if(currentPosition.y - tetrominoHeight + 2 + j >= this.height ||
						blocks[currentPosition.x + i][currentPosition.y - tetrominoHeight + 2 + j] != null)
						return true;
				}
			}
		}

		return false;
	}

	//Checks if the line is full
	public boolean checkLine(int line)
	{
		for (int i = 0; i < width; i++)
		{
			if (blocks[i][line] == null)
				return false;
		}

		return true;
	}


	// Checks for full lines in the grid, removes them and returns
	// the number of lines removed (0 - 4)
	public int checkFullLines()
	{
		int fullLines = 0;

		// Only need to check 4 lines above currentPosition since a Tetromino is at most 4 lines high.
		// Start from the top and go down so that no problem arises from lines moving down
		for(int j = currentPosition.y - 3; j <= currentPosition.y && j > 0 && j < height; j++)
		{
			boolean full = true;

			for(int i = 0; i < width; i++)
			{
				if(blocks[i][j] == null)
				{
					full = false;
					break;
				}
			}

			if(full)
			{
				removeLine(j);
				fullLines++;
			}
		}

		return fullLines;
	}

	public void removeLine(int line)
	{
		for(int i = 0; i < width; i++)
		{
			for(int j = line; j > 0; j--)
				blocks[i][j] = blocks[i][j - 1];
		}

		for(int i = 0; i < width; i++)
			blocks[i][0] = null;

		this.setChanged();
		this.notifyObservers();
	}

	// Add lines at the bottom of the grid
	public void addlines(int number)
	{
		for (int j = 0; j < height; j++)
		{
			for (int i = 0; i < width; i++)
			{
				if ((j < number) && (blocks[i][j] != null))
				{
					gameOver = true;
					return;
				}

				if (j >= number)
				{
					blocks[i][j - number] = blocks[i][j];
				}
			}
		}

		for (int j = height - number; j < height; j++)
		{
			for (int i = 0; i < width; i ++)
			{
				if (i == penaltyHolePosition)
					blocks[i][j] = null;
				else
					blocks[i][j] = Block.Penalty;
			}
		}

		// Move the current Tetromino up if necessary
		while(current != null && !currentHasValidPosition())
			currentPosition.y--;

		this.setChanged();
		this.notifyObservers();
	}

	// Check if the Tetromino is in a valid position
	public boolean currentHasValidPosition()
	{
		Block[][] currentBlocks = current.getBlocks();
		int tetrominoWidth = currentBlocks.length;
		int tetrominoHeight = currentBlocks[0].length;

		for(int i = 0; i < tetrominoWidth; i++)
		{
			for(int j = 0; j < tetrominoHeight; j++)
			{
				if(currentBlocks[i][j] != null)
				{
					// Block is outside the grid on the right or on the left
					if(currentPosition.x + i >= this.width || currentPosition.x + i < 0 ||
							currentPosition.y - tetrominoHeight + j >= this.height)
						return false;

					// Block has not yet appeard on the grid
					else if(currentPosition.y - tetrominoHeight + 1 + j < 0)
						continue;

					// Block is beneath floor
					else if(currentPosition.y - tetrominoHeight + 1 + j >= this.height)
						return false;

					// Block is colliding over an existing block
					else if (currentPosition.y - tetrominoHeight + 1 + j < this.height
							&&	blocks[currentPosition.x + i][currentPosition.y - tetrominoHeight + 1 + j] != null)
						return false;
				}
			}
		}

		return true;
	}

	public void setPosition(int x, int y){ currentPosition.x = x; currentPosition.y = y; }

	public Tetromino getTetromino() { return current; }
	public Point currentPosition() { return currentPosition; } //***//
	public boolean gameOver() { return gameOver; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
}
