package de.tu_darmstadt.gdi1.plumber.model.events.levelelement;

import java.awt.Point;

import de.tu_darmstadt.gdi1.plumber.model.events.EventListener;
import de.tu_darmstadt.gdi1.plumber.model.levelelements.Element_abstract;


public class UnDo extends LevelElementEvent_abstract {
	
	public UnDo(Point elementpos, Element_abstract element)
	{
		super(elementpos,element);
	}
	
	public void trigger(EventListener eventhandler)
	{
		eventhandler._levelelement(this);
	}
}
