package de.tu_darmstadt.gdi1.plumber.model.events.level;

import de.tu_darmstadt.gdi1.plumber.model.events.EventListener;
import de.tu_darmstadt.gdi1.plumber.model.level.Level;

/**
 * event which is triggered when water starts 
 */
public class WaterStart extends LevelEvent_abstract {

	/**
	 * constructor
	 * save current level
	 * @param currentLevel transfered level
	 */
	public WaterStart(Level currentLevel) {
		super(currentLevel);
		// TODO Auto-generated constructor stub
	}

	/**
	 * trigger waterstart event 
	 */
	@Override
	public void trigger(EventListener eventhandler) {
		eventhandler._level(this);
	}
}
