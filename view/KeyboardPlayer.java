package view;

import control.HarddropCommand;
import control.MoveCommand;
import control.PauseCommand;
import control.Command;
import control.PlayerController;
import control.RotateCommand;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import util.Direction;

public class KeyboardPlayer implements KeyListener {

	private final Map<Integer, Command> commands;

	public KeyboardPlayer(int i, PlayerController player)
	{
		commands = new HashMap<>();
		if(i == 1)
		{
			registerKey(KeyEvent.VK_UP, new RotateCommand(player));
			registerKey(KeyEvent.VK_LEFT, new MoveCommand(player, Direction.LEFT));
			registerKey(KeyEvent.VK_RIGHT, new MoveCommand(player, Direction.RIGHT));
			registerKey(KeyEvent.VK_DOWN, new MoveCommand(player, Direction.DOWN));
			registerKey(KeyEvent.VK_ENTER, new HarddropCommand(player));
			registerKey(KeyEvent.VK_P, new PauseCommand(player));
		}
		else
		{
			registerKey(KeyEvent.VK_Z, new RotateCommand(player));
			registerKey(KeyEvent.VK_Q, new MoveCommand(player, Direction.LEFT));
			registerKey(KeyEvent.VK_D, new MoveCommand(player, Direction.RIGHT));
			registerKey(KeyEvent.VK_S, new MoveCommand(player, Direction.DOWN));
			registerKey(KeyEvent.VK_CONTROL, new HarddropCommand(player));
			registerKey(KeyEvent.VK_T, new PauseCommand(player));
		}
	}

	public void registerKey(int key, Command command)
	{
		commands.put(key, command);
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		try{
			commands.get(key).actionPerformed(new ActionEvent(e, key, "keyboard")); // null à la base
		} catch(NullPointerException ex) {
			// Unused key
		}
	}

	// Unused
	@Override
	public void keyTyped(KeyEvent e){}
	@Override
	public void keyReleased(KeyEvent e){}

}
