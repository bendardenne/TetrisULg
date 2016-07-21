package game;

public class TetrominoO extends Tetromino {

	public TetrominoO()
	{
		blocks = new Block[2][2];
		blocks[0][0] = Block.TetrominoO;
		blocks[0][1] = Block.TetrominoO;
		blocks[1][1] = Block.TetrominoO;
		blocks[1][0] = Block.TetrominoO;
	}

	@Override
	public void rotate(boolean clockwise) { }

	@Override
	public int rotations() { return 1; }
}
