package de.tu_darmstadt.gdi1.plumber.controller.logic.level;

import java.awt.Point;

import de.tu_darmstadt.gdi1.plumber.controller.logic.LogicController;
import de.tu_darmstadt.gdi1.plumber.model.Model;
import de.tu_darmstadt.gdi1.plumber.model.events.level.SelectionChange;
import de.tu_darmstadt.gdi1.plumber.model.exceptions.PlumberError;
import de.tu_darmstadt.gdi1.plumber.model.exceptions.PlumberWarning;
import de.tu_darmstadt.gdi1.plumber.model.level.Level;
import de.tu_darmstadt.gdi1.plumber.model.levelelements.Element_abstract;


public class LevelController {

	/**
	 * Refferenz zu den Laufzeitdaten
	 */
	private Model modeldata;
	
	/**
	 * Selektiertes Element als Point gespeichert
	 * 
	 * Startet bei (0/0)
	 */
	private Point selectedElement = new Point(0,0); //start on top
	
	@SuppressWarnings("unused")
	private LogicController logicController;
	
	/**
	 * Konstruktor
	 * 
	 * @param logicController
	 * @param modeldata
	 */
	public LevelController(LogicController logicController, Model modeldata)
	{
		this.modeldata = modeldata;
		this.logicController = logicController;
		if(this.modeldata.getCurrentLevelSet() != null)
		{
			this.modeldata.getCurrentLevelSet().setCurrentLevel(new Level(this.modeldata.getCurrentLevelSet().getHighscore()));
		}
	}
	
	/**
	 * Rotiert selektiertes Element
	 */
	public void rotateSelected()
	{
		rotateRight(selectedElement);
	}
	
	/**
	 * Bewegt den Selektionsmarker auf dem Spielfeld nach oben,
	 * wenn möglich
	 */
	public void moveSelectionUp()
	{
		if(selectedElement.x > 0)
		{
			selectedElement.x -= 1;
			LogicController.triggerModelEvent(new SelectionChange(getLevel()));
		}
	}
	
	/**
	 * Bewegt den Selektionsmarker auf dem Spielfeld nach unten,
	 * wenn möglich
	 */
	public void moveSelectionDown()
	{
		try {
			if(selectedElement.x < getLevel().getLevelSize().x-1)
			{
				selectedElement.x += 1;
				LogicController.triggerModelEvent(new SelectionChange(getLevel()));
			}
		} catch (PlumberError e) {
			// TODO Auto-generated catch block
			return;
		}
	}
	
	/**
	 * Bewegt den Selektionsmarker auf dem Spielfeld nach Links,
	 * wenn möglich
	 */
	public void moveSelectionLeft()
	{
		if(selectedElement.y > 0)
		{
			selectedElement.y -= 1;
			LogicController.triggerModelEvent(new SelectionChange(getLevel()));
		}
	}
	
	/**
	 * Bewegt den Selektionsmarker auf dem Spielfeld nach Rechts,
	 * wenn möglich
	 */
	public void moveSelectionRight()
	{
		try {
			if(selectedElement.y < getLevel().getLevelSize().y-1)
			{
				selectedElement.y += 1;
				LogicController.triggerModelEvent(new SelectionChange(getLevel()));
			}
		} catch (PlumberError e) {
			// TODO Auto-generated catch block
			return;
		}
	}
	
	/**
	 * ACHTUNG: DIE FUNKTIONEN SIND AUS �BERSICHTLICHKEITSGR�NDEN
	 * AUSGELAGERT KOMMENTIERUNG UND IMPLEMNTIERUNG SIEHE
	 * 
	 * ModuleElements,
	 * ModuleIsCorrect,
	 * ModuleLoadSave,
	 * ModuleStartEndTime
	 */
	
	public void setLevel(Level level)
	{
		modeldata.getCurrentLevelSet().setCurrentLevel(level);
		this.selectedElement = new Point(0,0);
	}
	
	public Level getLevel()
	{
		return modeldata.getCurrentLevelSet().getCurrentLevel();
	}
	
	public boolean loadLevel(String filepath)
	{
		try {
			modeldata.getCurrentLevelSet().setCurrentLevel(new Level(modeldata.getCurrentLevelSet().getHighscore()));
			ModuleLoadSave.loadLevel(getLevel(), filepath);
			this.selectedElement = new Point(0,0);
		} catch (PlumberError e) {
			return false;
		}
		
		return true;
	}
	
	public void saveLevel(String filepath)
	{
		try {
			ModuleLoadSave.saveLevel(getLevel(), filepath);
		} catch (PlumberError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void fromString(String lvlstr)
	{
		try {
			ModuleLoadSave.fromString(getLevel(), lvlstr);
			this.selectedElement = new Point(0,0);
		} catch (PlumberError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PlumberWarning e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String toString()
	{
		return ModuleLoadSave.toString(getLevel());
	}
	
	public boolean isCorrect()
	{
		return ModuleIsCorrect.isCorrect(getLevel());
	}
	
	public Element_abstract getElement(Point p) throws PlumberError
	{
		return ModuleElements.getElement(getLevel(), p);
	}
	
	public Element_abstract findElement(Element_abstract le) throws PlumberError
	{
		return ModuleElements.FindElement(getLevel(), le); 
	}
	
	public boolean rotateRight(Point p)
	{
		return ModuleElements.rotate(getLevel(),p,true,true);
	}
	
	public boolean rotateLeft(Point p)
	{
		return ModuleElements.rotate(getLevel(),p,false,true);
	}
	
	public void start()
	{
		getLevel().getTimer().start();
	}
	
	public void ReDo()
	{
		getLevel().getHistory().ReDo();
	}
	
	public void UnDo()
	{
		getLevel().getHistory().UnDo();
	}
	
	public void clearHistory()
	{
		getLevel().getHistory().clear();
	}

	public void stop() {
		getLevel().getTimer().stop();
		
	}
	
	public boolean restart()
	{
		fromString(getLevel().getInitialLevel());
		
		return isCorrect();
	}
	
	public void startWater()
	{
		ModuleStartEndTime.startWater(getLevel());
	}

	public Point getSelection() {
		return this.selectedElement;
	}
}
