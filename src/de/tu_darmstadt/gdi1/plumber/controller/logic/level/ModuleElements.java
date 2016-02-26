package de.tu_darmstadt.gdi1.plumber.controller.logic.level;

import java.awt.Point;

import de.tu_darmstadt.gdi1.plumber.model.exceptions.PlumberError;
import de.tu_darmstadt.gdi1.plumber.model.history.events.LevelRotateElement;
import de.tu_darmstadt.gdi1.plumber.model.level.Level;
import de.tu_darmstadt.gdi1.plumber.model.levelelements.Element_abstract;


/**
 * L�sst Elemente, wenn erlaubt rotieren, triggert Event
 *
 */
public class ModuleElements {

	/** lässt Elemente rotieren (nicht selbst)
	 * @param level
	 * @param p
	 * @param right
	 * @param triggerevent
	 * @return false wenn nichts zu rotieren
	 */
	public static boolean rotate(Level level, Point p, boolean right,boolean triggerevent)
	{
		if(	level.getElements().size() > p.x 			&&   //welches level
				p.x >= 0						&&           //rotieren erlaubt?!
				level.getElements().get(0).size() > p.y 	&&
				p.y >= 0						)
			{
				level.setRotatesteps(level.getRotatesteps()+1); //erh�ht Rotierschritte 
				if(right)
				{
					
					if(level.getElements().get(p.x).get(p.y).rotateRight())// recht rotieren? 
					{
						if(triggerevent)
						{
							level.getHistory().Add(new LevelRotateElement(level,p,true)); //rotiere Element (rechts)
						}
						return true;
					}
					return false;
				} else
				{
					if(level.getElements().get(p.x).get(p.y).rotateLeft())
					{
						if(triggerevent)
						{
							level.getHistory().Add(new LevelRotateElement(level,p,false)); //rotiere Element (links)
						}
						return true;
					}
					return false;
				}
					
			}
			
			return false; //nichts rotieren
	}
	
	/**
	 * Gibt Element an Position p zurück, falls vorhanden, sonst wird Fehler geworfen 
	 * 
	 * @param level Level auf das man sich bezieht
	 * @param p Punkt des Elements
	 * @return Element
	 * @throws PlumberError
	 */
	public static Element_abstract getElement(Level level, Point p) throws PlumberError {
		if(	level.getElements().size() > p.x &&
			p.x >=0 &&
			level.getElements().get(0).size() > p.y &&
			p.y >= 0)
		{
			return level.getElements().get(p.x).get(p.y);
		}
		
		throw new PlumberError();
	}
	
	/**
	 * Findet Element im übergebenen Level, und gibt es zurück, wenn es vorhanden ist.
	 * Wirft Fehler, wenn nciht gefunden
	 * 
	 * @param level
	 * @param le
	 * @return Gefundenes Element
	 * @throws PlumberError
	 */
	public static Element_abstract FindElement(Level level, Element_abstract le) throws PlumberError
	{
		for(int i = 0; i < level.getElements().size(); i++) //lines
		{
			for(int j = 0; j < level.getElements().get(i).size();j++) //columns
			{
				if(level.getElements().get(i).get(j).getClass() == le.getClass())
				{
					return level.getElements().get(i).get(j);
				}
			}	
		}
		
		throw new PlumberError();
	}
}
