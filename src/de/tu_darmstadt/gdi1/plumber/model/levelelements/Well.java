package de.tu_darmstadt.gdi1.plumber.model.levelelements;

import java.awt.Point;

/**
 * Quelle
 */
public class Well extends Element_abstract
{
	//Ausgangsposition des Elements
	// *-|-*
	// | * |
	// *---*
	
	/**
	 * constructor
	 * 
	 * @param rotateright how often does it have to be rotated
	 * @param pos position of the field
	 */
	public Well(int rotateright, Point pos)
	{
		super(rotateright,pos, new boolean[] {true,false,false,false});
	}
	
	/**
	 * true wenn rotierbar
	 * 
	 * Quelle ist niemals rotierbar
	 */
	public boolean isRotatable()
	{
		return false;
	}
}
