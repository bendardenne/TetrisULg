package view;

import game.Block;
import java.awt.Color;

public class ColorFactory {

	public static Color getColor(Block type)
	{
		switch(type)
		{
			case TetrominoI:
				return new Color(245, 135, 58);

			case TetrominoJ:
				return new Color(178, 219, 237);

			case TetrominoL:
				return new Color(17,77,103);

			case TetrominoO:
				return new Color(251,101,88);

			case TetrominoS:
				return new Color(137, 216, 106);

			case TetrominoT:
				return new Color(255, 237, 189);

			case TetrominoZ:
				return new Color(247, 220, 89);

			case Penalty:
				return new Color(106,28,67);

			default:
				return new Color(255, 255, 255);

		}
	}

}
