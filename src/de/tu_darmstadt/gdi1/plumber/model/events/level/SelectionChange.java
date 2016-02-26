package de.tu_darmstadt.gdi1.plumber.model.events.level;

import java.awt.Point;

import de.tu_darmstadt.gdi1.plumber.model.events.EventListener;
import de.tu_darmstadt.gdi1.plumber.model.level.Level;


/**
 * event which is triggered at a selection change 
 *
 */
public class SelectionChange extends LevelEvent_abstract {

	public Point levelsize;
	
	/**
	 * constructor
	 * save current level
	 * @param currentLevel transfered level
	 */
	public SelectionChange(Level currentLevel) {
		super(currentLevel);
	}

	/**
	 * trigger selection change event
	 */
	@Override
	public void trigger(EventListener eventhandler) {
		eventhandler._level(this);
	}
}
