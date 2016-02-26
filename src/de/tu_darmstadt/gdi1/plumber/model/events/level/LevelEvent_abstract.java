package de.tu_darmstadt.gdi1.plumber.model.events.level;

import de.tu_darmstadt.gdi1.plumber.model.events.EventListener;
import de.tu_darmstadt.gdi1.plumber.model.events.Event_interface;
import de.tu_darmstadt.gdi1.plumber.model.level.Level;
/**
 *vorlage klasse für level events
 */
public abstract class LevelEvent_abstract implements Event_interface{

	public Level currentLevel;
	
	/**
	 * constructor
	 * save current level
	 * @param currentLevel
	 */
	public LevelEvent_abstract(Level currentLevel)
	{
		this.currentLevel = currentLevel;
	}
	
	/**
	 * trigger event
	 * implemented by the inheritor 
	 */
	public abstract void trigger(EventListener eventhandler);
	
}
