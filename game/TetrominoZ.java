package game;

public class TetrominoZ extends Tetromino {

	public TetrominoZ()
	{
		blocks = new Block[3][3];
		blocks[0][1] = Block.TetrominoZ;
		blocks[1][1] = Block.TetrominoZ;
		blocks[1][2] = Block.TetrominoZ;
		blocks[2][2] = Block.TetrominoZ;
	}

	@Override
	public int rotations() { return 2; }
}
