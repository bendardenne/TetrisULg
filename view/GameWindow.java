package view;

import control.Controller;
import control.PlayerController;
import game.Game;
import java.awt.BorderLayout;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameWindow extends JFrame implements Observer {

	private final Controller controller;
	private Game game;
	private List<PlayerController> players;
	private final List<KeyboardPlayer> kp;

	public GameWindow(Controller controller)
	{
		super("TetrisULg");

		this.kp = new LinkedList<>();
		this.controller = controller;
		controller.addObserver(this);

		this.add(new ControlPanel(controller), BorderLayout.LINE_START);

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
			players = controller.getPlayers();
			kp.clear();
			
			getContentPane().removeAll();

			for(KeyListener l : getKeyListeners())
				removeKeyListener(l);
			
			this.add(new ControlPanel(controller), BorderLayout.LINE_START);

			if(game == null)
				return;
				
			game.addObserver(this);
			JPanel playersPanel = new JPanel();
			playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.X_AXIS));
			for(PlayerController p : players)
				playersPanel.add(new PlayerPanel(p));

			this.add(playersPanel);
			if(controller.remote())
				this.addKeyListener(new KeyboardPlayer(1, controller.getRemote().getLocalPlayer()));
			else
			{
				this.addKeyListener(new KeyboardPlayer(1, players.get(0)));
					
				if(players.size() > 1)
					this.addKeyListener(new KeyboardPlayer(2, players.get(1)));
			}

			this.add(new StatusBar(game), BorderLayout.PAGE_END);

			this.requestFocus();
		}

		this.pack();
		this.repaint();
	}
}
