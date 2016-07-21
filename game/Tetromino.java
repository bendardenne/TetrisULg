package game;

public abstract class Tetromino {

	protected Block[][] blocks;

	public abstract int rotations();

	// Returns a square array of blocks composing the Tetromino
	public Block[][] getBlocks(){ return blocks; }

	public void rotate(boolean clockwise)
	{
		transpose();

		if(clockwise)
			columnSwap();
		else
			lineSwap();

	}

	private void transpose()
	{
		int size = blocks.length;

		for(int i = 0;	i < size; i++)
		{
			for(int j = 0; j < i; j++)
			{
				Block tmp = blocks[j][i];
				blocks[j][i] = blocks[i][j];
				blocks[i][j] = tmp;
			}
		}
	}

	private void lineSwap()
	{
		int size = blocks.length;

		for(int i = 0;	i < size; i++)
		{
			for(int j = 0; j < size / 2; j++)
			{
				Block tmp = blocks[i][j];
				blocks[i][j] = blocks[i][size - 1 -j];
				blocks[i][size - 1 - j] = tmp;
			}
		}
	}

	private void columnSwap()
	{
		int size = blocks.length;

		for(int i = 0;	i < size / 2; i++)
		{
			for(int j = 0; j < size; j++)
			{
				Block tmp = blocks[i][j];
				blocks[i][j] = blocks[size - 1 - i][j];
				blocks[size - 1 - i][j] = tmp;
			}
		}
	}

}
