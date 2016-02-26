package de.tu_darmstadt.gdi1.plumber.controller.logic.levelset;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import de.tu_darmstadt.gdi1.plumber.controller.logic.LogicController;
import de.tu_darmstadt.gdi1.plumber.model.Model;
import de.tu_darmstadt.gdi1.plumber.model.exceptions.PlumberError;
import de.tu_darmstadt.gdi1.plumber.model.highscores.Highscore;
import de.tu_darmstadt.gdi1.plumber.model.level.Level;
import de.tu_darmstadt.gdi1.plumber.model.levelsets.LevelSet;


public class LevelSetController {

	public final static String LEVEL_FILE_EXTENSION = ".lvl";
	
	/**
	 * Refferenz zu den Laufzeitdaten
	 */
	private Model modeldata;
	
	/**
	 * LogikController Refferenz
	 */
	private LogicController logicController;
	
	/**
	 * Konstruktor
	 * 
	 * @param logicController
	 * @param modeldata
	 */
	public LevelSetController(LogicController logicController, Model modeldata) {
		this.modeldata = modeldata;
		this.logicController = logicController;
		this.modeldata.setCurrentLevelSet(new LevelSet());
		getLevelSet().setHighscore(new Highscore(getLevelSet().getLevelsetpath()));
	}
	
	/**
	 * Gibt Highscore des aktuellen Levelsets zurück
	 * @return
	 */
	public Highscore getHighscore()
	{
		return getLevelSet().getHighscore();
	}

	/**
	 * Gibt aktuelles LevelSet zurück
	 * @return
	 */
	public LevelSet getLevelSet()
	{
		return modeldata.getCurrentLevelSet();
	}
	
	/**
	 * Lade nächstes Level
	 * @return true wenn erfolgreich
	 */
	public boolean previousLevel() {
		return loadLevel(getLevelSet().getLevelNumber()-1);
	}
	
	/**
	 * Lade vorheriges Level
	 * @return true wenn erfolgreich
	 */
	public boolean nextLevel() {
		return loadLevel(getLevelSet().getLevelNumber()+1);
	}
	
	/**
	 * Setzt den LevelSetPfad,
	 * Läd enthaltene Levelsets
	 * 
	 * @param levelsetpath
	 * @throws PlumberError
	 */
	public void setLevelSetPath(String levelsetpath) throws PlumberError
	{
		getLevelSet().getLevelsetpaths().clear();
		getLevelSet().setLevelNumber(getLevelSet().getLevelNumber()-1);
		getLevelSet().setLevelSetPath(levelsetpath);
		
		String[] pathseperated = levelsetpath.split("\\" + (String)File.separator);
		if(pathseperated.length > 0)
		{
			getLevelSet().setName(pathseperated[pathseperated.length-1]);
		} else
		{
			getLevelSet().setName("");
		}
			
		//Filter: Nur LevelDateien
		FilenameFilter filter = new FilenameFilter() {
			   public boolean accept(File dir, String name) {
			        return name.endsWith(LEVEL_FILE_EXTENSION);
			    }
		};
			
		File dir = new File(levelsetpath);

		String[] children = dir.list(filter);
		if (children == null || children.length == 0)
		{
			//Either dir does not exist or is not a directory
			//or no levelfiles
			throw new PlumberError();
		} else
		{
		    for (int i=0; i<children.length; i++)
		    {
		        // Get name of directory
		    	try
			    {
		    		getLevelSet().getLevelsetpaths().add(dir.getCanonicalPath() + File.separator + children[i]);
			    }catch(IOException e)
			    {
			    	//skip element
			    }
			}
		}
			
		//Highscore
		getLevelSet().setHighscore(new Highscore(levelsetpath));
		
		loadLevel(0);
	}
	
	/**
	 * Lade Level an der Position position in der Levelliste des Levelsets
	 * @param position
	 * @return
	 */
	public boolean loadLevel(int position)
	{
		if(	getLevelSet().getLevelsetpaths().size() > position &&
				position >= 0)
			{
				getLevelSet().setCurrentLevel(new Level(getLevelSet().getHighscore()));
				if(!logicController.level.loadLevel(getLevelSet().getLevelsetpaths().get(position)))
				{
					getLevelSet().setLevelNumber(getLevelSet().getLevelNumber() -1);
					getLevelSet().getCurrentLevel().setLevelnumber(getLevelSet().getLevelNumber() -1);
					return false;
				}
				
				getLevelSet().setLevelNumber(position);
				getLevelSet().getCurrentLevel().setLevelnumber(position);
				return logicController.level.isCorrect();
			}
		
		return false;
	}
	
	/**
	 * Setzt das aktuelle Levelset
	 * @param levelset
	 */
	public void setLevelSet(LevelSet levelset)
	{
		modeldata.setCurrentLevelSet(levelset);
	}
}
