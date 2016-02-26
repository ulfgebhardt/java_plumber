package de.tu_darmstadt.gdi1.plumber.model.events.levelelement;

import java.awt.Point;

import de.tu_darmstadt.gdi1.plumber.model.events.EventListener;
import de.tu_darmstadt.gdi1.plumber.model.levelelements.Element_abstract;


public class Rotate extends LevelElementEvent_abstract {
	
	public boolean rotatedirection = true;  
	
	public Rotate(Point elementpos, Element_abstract element, boolean rotatedirection)
	{
		super(elementpos,element);
		this.rotatedirection = rotatedirection;
	}
	
	public void trigger(EventListener eventhandler)
	{
		eventhandler._levelelement(this);
	}
}
