package game;

public class TetrominoJ extends Tetromino {

	public TetrominoJ()
	{
		blocks = new Block[3][3];
		blocks[0][1] = Block.TetrominoJ;
		blocks[1][1] = Block.TetrominoJ;
		blocks[2][1] = Block.TetrominoJ;
		blocks[2][2] = Block.TetrominoJ;
	}

	@Override
	public int rotations() { return 4; }

}
