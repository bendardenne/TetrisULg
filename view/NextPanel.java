package view;

import control.PlayerController;
import game.Block;
import game.GameGrid;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

public class NextPanel extends JPanel implements Observer {

	private final PlayerController player;
	private final GameGrid grid;
	private final int blockSize;

	public NextPanel(PlayerController player, int blockSize)
	{
		super();

		this.player = player;
		this.grid = player.getPlayer().getGrid();
		this.blockSize = blockSize;
		grid.addObserver(this);

		setBackground(Color.black);
		setMaximumSize(new Dimension(5 * blockSize, 5 * blockSize));
		setMinimumSize(new Dimension(5 * blockSize, 5 * blockSize));
		setPreferredSize(new Dimension(5 * blockSize, 5 * blockSize));
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);

		if(!player.playing())
			return;

		Block[][] blocks = grid.getNextBlocks();

		for(int i = 0; i < blocks.length; i++)
			for(int j = 0; j < blocks[0].length; j++)
				if(blocks[i][j] != null)
					drawBlock(g, i + 1, j + 1, ColorFactory.getColor(blocks[i][j]));

	}

	@Override
	public void update(Observable o, Object arg)
	{
		this.repaint();
	}

	private void drawBlock(Graphics g, int x, int y, Color color)
	{
		g.setColor(color);
		g.fillRect(x * blockSize, y * blockSize, blockSize, blockSize);
	}
}
