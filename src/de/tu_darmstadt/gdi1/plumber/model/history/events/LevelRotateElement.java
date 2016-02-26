package de.tu_darmstadt.gdi1.plumber.model.history.events;

import java.awt.Point;

import de.tu_darmstadt.gdi1.plumber.controller.logic.level.ModuleElements;
import de.tu_darmstadt.gdi1.plumber.model.level.Level;

/**
 * Dafür zuständig, dass mit hilfe der History
 * Elemente gedreht werden können. 
 * Vor allem für UnDo, also um Schritte rückgänig zu machen
 */
public class LevelRotateElement implements HistoryEvent_interface {

	private Level level;
	private Point pos;
	private boolean rotatedir;

	/**
	 * setzen von variablen um do und undo machen zu können
	 * 
	 * @param level das Level in dem man sich befindet
	 * @param position position des Elements
	 * @param rotatedir true = rechts drehen / false = links drehen
	 */
	public LevelRotateElement(Level level, Point position, boolean rotatedir)
	{
		this.level = level;
		this.pos = position;
		this.rotatedir = rotatedir;
	}
	
	/**
	 * einen Schritt nach vorne machen in der History
	 * Rotate aufrufen (rechtsrum)
	 */
	public void Do() {
		ModuleElements.rotate(level, pos, rotatedir,false);
	}

	/**
	 * einen Schritt in der History nach hinten gehen
	 * Rotate aufrufen (linksrum)
	 */
	public void UnDo() {
		ModuleElements.rotate(level, pos, !rotatedir,false);
	}

}
