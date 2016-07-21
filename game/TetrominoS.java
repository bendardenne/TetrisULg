package game;

public class TetrominoS extends Tetromino {

	public TetrominoS()
	{
		blocks = new Block[3][3];
		blocks[1][1] = Block.TetrominoS;
		blocks[0][2] = Block.TetrominoS;
		blocks[1][2] = Block.TetrominoS;
		blocks[2][1] = Block.TetrominoS;
	}

	@Override
	public int rotations() { return 2; }
}
