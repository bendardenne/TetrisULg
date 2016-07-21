package game;

public class TetrominoT extends Tetromino {

	public TetrominoT()
	{
		blocks = new Block[3][3];
		blocks[0][1] = Block.TetrominoT;
		blocks[1][1] = Block.TetrominoT;
		blocks[2][1] = Block.TetrominoT;
		blocks[1][2] = Block.TetrominoT;
	}

	@Override
	public int rotations() { return 4; }
}
