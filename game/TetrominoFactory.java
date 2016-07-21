package game;

import util.Random;

public class TetrominoFactory {

	private final Random rand;
	private final static int TETROMINO_TYPES = 7;

	public TetrominoFactory()
	{
		rand = new Random();
	}

	public TetrominoFactory(int seed)
	{
		rand = new Random(seed);
	}

	public Tetromino getNew()
	{
		Tetromino t;

		switch(rand.nextInt(TETROMINO_TYPES))
		{
			case 0:
				t = new TetrominoT();
				break;
			case 1:
				t = new TetrominoO();
				break;
			case 2:
				t = new TetrominoL();
				break;
			case 3:
				t = new TetrominoJ();
				break;
			case 4:
				t = new TetrominoS();
				break;
			case 5:
				t = new TetrominoZ();
				break;
			case 6:
			default:
				t = new TetrominoI();
		}

		return t;
	}

}
