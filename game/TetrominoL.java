package game;

public class TetrominoL extends Tetromino {

	public TetrominoL()
	{
		blocks = new Block[3][3];
		blocks[0][1] = Block.TetrominoL;
		blocks[1][1] = Block.TetrominoL;
		blocks[2][1] = Block.TetrominoL;
		blocks[0][2] = Block.TetrominoL;
	}

	@Override
	public int rotations() { return 4; }
}
