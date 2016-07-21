package view;

import game.Game;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusBar extends JPanel implements Observer {

	private final Game game;
	private final JLabel stopwatch;

	public StatusBar(Game game)
	{
		this.game = game;
		game.getStopwatch().addObserver(this);
		stopwatch = new JLabel();
		this.add(stopwatch);
	}

	@Override
	public void update(Observable o, Object arg)
	{
		String status = "Time : " + game.getStopwatch();

		if(game.paused())
			status += " [PAUSED]";

		stopwatch.setText(status);
		this.repaint();
	}
}



