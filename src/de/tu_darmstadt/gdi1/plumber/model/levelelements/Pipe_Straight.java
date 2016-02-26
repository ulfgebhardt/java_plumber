package de.tu_darmstadt.gdi1.plumber.model.levelelements;

import java.awt.Point;

/**
 * Gerades Rohr
 */
public class Pipe_Straight extends Element_abstract
{
	//Ausgangsposition des Elements
	// *-|-*
	// | | |
	// *-|-*
	
	/**
	 * constructor
	 * 
	 * @param rotateright how often does it have to be rotated
	 * @param pos position of the field
	 */
	public Pipe_Straight(int rotateright, Point pos)
	{
		super(rotateright,pos, new boolean[] {true,false,true,false});
	}
	
	/**
	 * true wenn rotierbar
	 * 
	 * only rotatable if it is not watered
	 */
	public boolean isRotatable()
	{
		return !watered;
	}
}
