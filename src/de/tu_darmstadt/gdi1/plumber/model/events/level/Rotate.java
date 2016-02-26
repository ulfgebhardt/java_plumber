package de.tu_darmstadt.gdi1.plumber.model.events.level;

import de.tu_darmstadt.gdi1.plumber.model.events.EventListener;
import de.tu_darmstadt.gdi1.plumber.model.level.Level;
/**
 * event which is triggered when element is rotated
 *
 */
public class Rotate extends LevelEvent_abstract {

	/**
	 * constructor
	 * save current level
	 * @param currentLevel transfered level
	 */
	public Rotate(Level currentLevel) {
		super(currentLevel);
	}

	/**
	 * trigger rotate event
	 */
	@Override
	public void trigger(EventListener eventhandler) {
		eventhandler._level(this);
	}
}
