package view;

import control.Controller;
import control.PlayerController;
import game.Game;
import java.awt.BorderLayout;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ServerView extends JFrame implements Observer {

	private final Controller controller;
	private Game game;
	private List<PlayerController> players;

	public ServerView(Controller controller)
	{
		super("TetrisULg Server");

		this.controller = controller;
		controller.addObserver(this);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		this.pack();
	}

	@Override
	public void repaint()
	{
		super.repaint();
	}

	@Override
	public void update(Observable o, Object arg)
	{
		if(controller.getGame() != game)
		{
			/* The game has changed, get the new grids and players */
			game = controller.getGame();
			game.addObserver(this);
			players = controller.getPlayers();
			
			getContentPane().removeAll();

			JPanel playersPanel = new JPanel();
			playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.X_AXIS));
			for(PlayerController p : players)
				playersPanel.add(new PlayerPanel(p));

			this.add(playersPanel);
			this.add(new StatusBar(game), BorderLayout.PAGE_END);

			this.requestFocus();
		}

		this.pack();
		this.repaint();
	}
}
