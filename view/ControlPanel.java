package view;

import control.Controller;
import control.PlayerController;
import control.SetAISpeedCommand;
import control.SetHeightCommand;
import control.SetHostCommand;
import control.SetModeCommand;
import control.SetWidthCommand;
import control.StartCommand;
import control.ToggleAICommand;
import game.Mode;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;

public class ControlPanel extends JPanel { 

	private final Controller controller;

	public ControlPanel(final Controller controller)
	{
		this.controller = controller;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JRadioButton single = new JRadioButton(new SetModeCommand(controller, Mode.SINGLE, 1, "Single"));
		JRadioButton multi = new JRadioButton(new SetModeCommand(controller, Mode.MULTI, 2, "Multi"));
		JRadioButton vs = new JRadioButton(new SetModeCommand(controller, Mode.MULTI_VS, 2, "VS"));
		JRadioButton coop = new JRadioButton(new SetModeCommand(controller, Mode.MULTI_COOP, 2, "Coop"));
		
		single.setFocusable(false);
		multi.setFocusable(false); 
		vs.setFocusable(false);
		coop.setFocusable(false);

		switch(controller.getMode())
		{
			case SINGLE:
				single.setSelected(true);
				break;
			case MULTI:
				multi.setSelected(true);
				break;
			case MULTI_COOP:
				coop.setSelected(true);
				break;
			case MULTI_VS:
				vs.setSelected(true);
				break;
		}

		ButtonGroup modes = new ButtonGroup();
		modes.add(single);
		modes.add(multi);
		modes.add(vs);
		modes.add(coop);

		this.add(single);
		this.add(multi);
		this.add(vs);
		this.add(coop);

		if(!controller.remote())
		{
			this.add(new JLabel("Width:"));
			JSlider width = new JSlider(5, 30, controller.getWidth());
			Dimension size = width.getPreferredSize();
			size.width = 50;
			width.setPreferredSize(size);
			width.setFocusable(false);
			width.addChangeListener(new SetWidthCommand(controller));
			this.add(width);

			this.add(new JLabel("Height:"));
			JSlider height = new JSlider(5, 40, controller.getHeight());
			height.setPreferredSize(size);
			height.setFocusable(false);
			height.addChangeListener(new SetHeightCommand(controller));
			this.add(height);
			
			if(controller.getPlayers().size() > 0)
			{
				this.add(new JLabel("AI: "));

				for(PlayerController p : controller.getPlayers())
				{
					JCheckBox AI = new JCheckBox(new ToggleAICommand(p));
					AI.setFocusable(false);
					this.add(AI);
				
					this.add(new JLabel("Speed:"));
					JSlider speed = new JSlider(100, 4000, p.getAISpeed());
					speed.setFocusable(false);
					speed.setPreferredSize(size);
					speed.addChangeListener(new SetAISpeedCommand(p));
					this.add(speed);
				}
			}
		}

		JButton connect = new JButton(new AbstractAction("Connect") {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				String host = (String) JOptionPane.showInputDialog(null,
																   "Hostname :",
																   "Connect",
																   JOptionPane.PLAIN_MESSAGE,
																   null,
																   null,
																   controller.getHost());
				String port = (String) JOptionPane.showInputDialog(null,
																   "Port :",
																   "Connect",
																   JOptionPane.PLAIN_MESSAGE,
																   null,
																   null,
																   controller.getPort());
				new SetHostCommand(controller, host, Integer.parseInt(port)).actionPerformed(
						new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
			}	
		});
		connect.setFocusable(false);
		this.add(connect);

		JButton start = new JButton(new StartCommand(controller));
		start.setFocusable(false);
		this.add(start);
	}
}
