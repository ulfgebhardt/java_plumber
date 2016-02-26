package de.tu_darmstadt.gdi1.plumber.controller;

import de.tu_darmstadt.gdi1.plumber.controller.logic.LogicController;
import de.tu_darmstadt.gdi1.plumber.controller.view.ViewController;
import de.tu_darmstadt.gdi1.plumber.model.Model;

/**
 * Controller des Plumberprogramms,
 * besitzt sub-Controller view und logic
 * 
 * @author ulf
 */
public class Controller{
	
	/**
	 * Laufzeitdaten des Programms sind hier gespeichert
	 * 
	 * @see Model
	 */
	private Model modeldata = new Model();
	
	/**
	 * Controller für den View-Teil (Menüanzeige usw)
	 */
	public ViewController view = new ViewController(this, modeldata);
	
	/**
	 * Controller für den Logik-Teil (level-laden usw)
	 */
	public LogicController logic = new LogicController(this, modeldata);
	
	/**
	 * Beendet das Programm
	 */
	public void exit()
	{
		System.exit(0);
	}
	
}
