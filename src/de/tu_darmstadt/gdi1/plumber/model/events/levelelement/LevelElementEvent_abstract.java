package de.tu_darmstadt.gdi1.plumber.model.events.levelelement;

import java.awt.Point;

import de.tu_darmstadt.gdi1.plumber.model.events.EventListener;
import de.tu_darmstadt.gdi1.plumber.model.events.Event_interface;
import de.tu_darmstadt.gdi1.plumber.model.levelelements.Element_abstract;


public abstract class LevelElementEvent_abstract implements Event_interface{
	
	public Point elementpos;
	public Element_abstract element;
	
	public LevelElementEvent_abstract(Point elementpos, Element_abstract element)
	{
		this.elementpos = elementpos;
		this.element = element;
	}

	public abstract void trigger(EventListener eventhandler);
}
