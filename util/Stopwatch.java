package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.concurrent.TimeUnit;
import javax.swing.Timer;

public class Stopwatch extends Observable {

	int elapsed;
	Timer timer;
	private final static int TIMER_PRECISION = 250;

	public Stopwatch()
	{
		elapsed = 0;

		timer = new Timer(TIMER_PRECISION, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { elapsed += TIMER_PRECISION; setChanged(); notifyObservers(); }
		});

		timer.start();
	}

	public void toggle()
	{
		if(timer.isRunning())
			timer.stop();
		else
			timer.start();

		setChanged();
		notifyObservers();
	}

	public void stop() { timer.stop(); setChanged(); notifyObservers(); }
	
	public int milliseconds() { return elapsed;}
	public int seconds() { return elapsed / 1000;}

	@Override
	public String toString()
	{
		return String.format("%02d:%02d",
			TimeUnit.MILLISECONDS.toMinutes(elapsed),
			TimeUnit.MILLISECONDS.toSeconds(elapsed) % 60);
	}
}
