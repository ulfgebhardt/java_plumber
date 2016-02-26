package de.tu_darmstadt.gdi1.plumber.model.events.level;

import java.awt.Point;

import de.tu_darmstadt.gdi1.plumber.model.events.EventListener;
import de.tu_darmstadt.gdi1.plumber.model.exceptions.PlumberError;
import de.tu_darmstadt.gdi1.plumber.model.level.Level;


/**
 * event which is triggered after a new level is loaded
 *
 */
public class NewLevel extends LevelEvent_abstract {

	public Point levelsize;
	
	/**
	 * constructor
	 * save current level
	 * @param currentLevel transfered level
	 */
	public NewLevel(Level currentLevel) {
		super(currentLevel);
		// TODO Auto-generated constructor stub
		try
		{
			levelsize = currentLevel.getLevelSize();
		} catch(PlumberError e)
		{
			levelsize = new Point(0,0);
		}
	}

	/**
	 * trigger new level event
	 */
	@Override 
	public void trigger(EventListener eventhandler) {
		eventhandler._level(this);
	}
}
