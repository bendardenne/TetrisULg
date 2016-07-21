package control;

import javax.swing.AbstractAction;

public abstract class Command extends AbstractAction {
	
	public Command() {} 
	
	public Command(String string)
	{
		super(string);
	}
}