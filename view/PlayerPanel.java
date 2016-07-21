package view;

import control.PlayerController;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerPanel extends JPanel implements Observer{

	private final PlayerController player;
	private final JLabel scoreText;
	private final static int BLOCK_SIZE = 25;
	private final static String SCORE_FORMAT = "<html>Score: %d <br/>Level: %d</html>";

	public PlayerPanel(PlayerController player)
	{
		this.player = player;
		player.getPlayer().addObserver(this);

		this.add(new GridPanel(player, BLOCK_SIZE));
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

		scoreText = new JLabel(String.format(SCORE_FORMAT, 0, 0));
		scoreText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		rightPanel.add(new NextPanel(player, BLOCK_SIZE));
		rightPanel.add(scoreText);
		this.add(rightPanel);
	}

	@Override
	public void update(Observable o, Object arg)
	{
		String score = String.format(SCORE_FORMAT, player.getPlayer().getScore(), player.getPlayer().getLevel());
		scoreText.setText(score);
		this.repaint();
	}

}
