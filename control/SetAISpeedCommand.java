package control;

import java.awt.event.ActionEvent;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SetAISpeedCommand extends Command implements ChangeListener {

	private final PlayerController target;

	public SetAISpeedCommand(PlayerController target)
	{
		this.target	= target;
	}

	@Override
	public void actionPerformed(ActionEvent e){}

	@Override
	public void stateChanged(ChangeEvent e)
	{
		JSlider slider = (JSlider) e.getSource();
		target.setAISpeed(slider.getValue());
	}
}