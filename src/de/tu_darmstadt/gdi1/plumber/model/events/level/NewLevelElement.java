package de.tu_darmstadt.gdi1.plumber.model.events.level;

import de.tu_darmstadt.gdi1.plumber.model.events.EventListener;
import de.tu_darmstadt.gdi1.plumber.model.level.Level;

/**
 * DEPRECATED
 * event which is triggered when a new level element is created
 *
 */
public class NewLevelElement extends LevelEvent_abstract {

	/**
	 * constructor
	 * save current level
	 * @param currentLevel transfered level
	 */
	public NewLevelElement(Level currentLevel) {
		super(currentLevel);
	}

	/**
	 * trigger new level element event
	 */
	@Override
	public void trigger(EventListener eventhandler) {
		eventhandler._level(this);
	}
}
