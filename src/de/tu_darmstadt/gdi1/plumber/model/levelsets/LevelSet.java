package de.tu_darmstadt.gdi1.plumber.model.levelsets;

import java.util.Vector;

import de.tu_darmstadt.gdi1.plumber.model.highscores.Highscore;
import de.tu_darmstadt.gdi1.plumber.model.level.Level;
//UNUSED
//import plumber.model.events.levelset.LevelSetEvent_abstract;

/**
 * Datenrepresentation eines LevelSets
 */
public class LevelSet{
	
	/**
	 * Pfade der Levels als StringVector (dateien)
	 */
	private Vector<String> levelpaths = new Vector<String>();
	
	/**
	 * Zeigt auf ein Element im levelpaths-Vector
	 * 
	 * Zeigt das aktuelle Level an
	 */
	private int levelnumber = 0;
	
	/**
	 * Geladenes Level
	 */
	private Level currentLevel;
	
	/**
	 * Pfad des Levelsets (ordner)
	 */
	private String levelsetpath;
	
	/**
	 * Name des Levelsets(ordnername)
	 */
	private String name = "";
	
	/**
	 * Highscore-Instanz
	 */
	private Highscore highscore;
	
	////////////////////////////////////////////////
	/**
	 * GETTER AND SETTER BELOW
	 */
	////////////////////////////////////////////////
	
	public Highscore getHighscore()
	{
		return highscore;
	}
	
	public Level getCurrentLevel() {
		return currentLevel;
	}

	public String getLevelsetpath() {
		return levelsetpath;
	}

	public int getLevelNumber() {
		return levelnumber;
	}

	public Vector<String> getLevelsetpaths() {

		return levelpaths;
	}

	public void setCurrentLevel(Level level) {
		currentLevel = level;
		
	}

	public void setLevelNumber(int i) {
		this.levelnumber = i;
		
	}

	public void setLevelSetPath(String levelsetpath) {
		this.levelsetpath = levelsetpath;
	}

	public void setHighscore(Highscore highscore) {
		this.highscore = highscore;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
}
