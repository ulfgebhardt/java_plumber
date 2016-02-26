package de.tu_darmstadt.gdi1.plumber.model.levelelements;

import java.awt.Point;

/**
 * Senke
 */
public class Sink extends Element_abstract
{
	//Ausgangsposition des Elements
	// *-|-*
	// | * |
	// *---*
	
	/**
	 * Konstruktor
	 * 
	 * @param rotateright how often does it have to be rotated
	 * @param pos position of the field
	 */
	public Sink(int rotateright, Point pos)
	{
		super(rotateright,pos, new boolean[] {true,false,false,false});
	}
	
	/**
	 * true wenn rotierbar
	 * 
	 * Senke ist niemals rotierbar
	 */
	public boolean isRotatable()
	{
		return false;
	}
}
