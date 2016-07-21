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

public class GridPanel extends JPanel implements Observer {

	private final PlayerController player;
	private final GameGrid grid;
	private final int blockSize;

	public GridPanel(PlayerController player, int blockSize)
	{
		super();

		this.blockSize = blockSize;
		this.player = player;
		this.grid = player.getPlayer().getGrid();
		grid.addObserver(this);

		setBackground(Color.black);
		setMinimumSize(new Dimension(grid.getWidth() * blockSize, grid.getHeight() * blockSize));
		setPreferredSize(new Dimension(grid.getWidth() * blockSize, grid.getHeight() * blockSize));
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);

		if(player.getGame() != null && player.getGame().gameOver())
		{
			g.setColor(new Color(223, 53, 40));
			g.fillRect(0, 0, grid.getWidth() * blockSize, grid.getHeight() * blockSize);
			return;
		}

		if(!player.playing())
			return;

		Block[][] blocks = grid.getBlocks();

		for(int i = 0; i < grid.getWidth(); i++)
			for(int j = 0; j < grid.getHeight(); j++)
				if(blocks[i][j] != null)
					drawBlock(g, i, j, ColorFactory.getColor(blocks[i][j]));

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
