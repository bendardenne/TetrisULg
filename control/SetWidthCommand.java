package control;

import java.awt.event.ActionEvent;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SetWidthCommand extends Command implements ChangeListener {

	private final Controller target;

	public SetWidthCommand(Controller target)
	{
		this.target	= target;
	}

	@Override
	public void actionPerformed(ActionEvent e){}

	@Override
	public void stateChanged(ChangeEvent e)
	{
		if(target.remote() && e.getSource() != target.getRemote())
			return;

		JSlider slider = (JSlider) e.getSource();
		target.setWidth(slider.getValue());
	}
}
