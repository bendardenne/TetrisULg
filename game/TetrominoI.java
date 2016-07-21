package game;

public class TetrominoI extends Tetromino {

	public TetrominoI()
	{
		blocks = new Block[4][4];
		blocks[1][0] = Block.TetrominoI;
		blocks[1][1] = Block.TetrominoI;
		blocks[1][2] = Block.TetrominoI;
		blocks[1][3] = Block.TetrominoI;
	}

	@Override
	public int rotations() { return 2; }
}
