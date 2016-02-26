package de.tu_darmstadt.gdi1.plumber.model;

import java.util.Vector;

import de.tu_darmstadt.gdi1.plumber.model.levelsets.LevelSet;

/**
 * PlumberLogic,
 * 
 * Ist für alle Berechnungen zuständig, imformiert Controller/UI über
 * Änderungen.
 * 
 * @author rylon
 * @version 0.2
 */

public class Model{
	
	//Member-Vars---------------------------------------------------------------------
	/**
	 * Pfade zu den nicht(mehr) geladenen Levelsets
	 */
	private Vector<String> levelsetpaths = new Vector<String>();
	
	/**
	 * Aktuelles Levelset in dem levelsetpaths-vector
	 */
	private int	levelsetnumber = -1;
	
	/**
	 * Ordnerpfad mit Levelsetordnern
	 */
	private String levelsetspath = ""; //TODO: get from options
	
	/**
	 * Aktuelles LevelSet.
	 * 
	 * Es wird immer nur ein Levelset im Speicher gehalten.
	 */
	private LevelSet currentLevelSet; 
	
	private Vector<String> themepaths = new Vector<String>(); //Pfade zu den nicht geladenen Themes
	private int themenumber = 0; //aktueller Theme im Themevektor
	private String currentTheme; //aktueller Theme
	private String themespath = ""; //TODO get from options
	
	
	//Daten bekommen - auf Daten zugreifen
	/**
	 * Gibt aktuelles Levelset zurück oder null
	 */
	public LevelSet getCurrentLevelSet()
	{	
		return currentLevelSet;
	}
	
	/**
	 * Gibt aktuellen Theme zurück oder null
	 * @return Gibt aktuellen Theme zurück oder null
	 */
	public String getCurrentTheme()
	{
		return currentTheme;
	}
	
	/**
	 *  Gibt den Pfad zu dem Ordner mit den Levelset-Ordnern zurück
	 * @return Gibt den Pfad zu dem Ordner mit den Levelset-Ordnern zurück
	 */
	public String getLevelSetsPath() {
		return levelsetspath;
	}
	
	/**
	 * Gibt den Pfad zu dem Ordner mit den Theme-Ordnern zurück
	 * @return Gibt den Pfad zu dem Ordner mit den Theme-Ordnern zurück
	 */
	public String getThemesPath()
	{
		return themespath;
	}

	public int getLevelSetNumber() {
		return this.levelsetnumber;
	}
	
	public int getThemeNumber()
	{
		return this.themenumber;
	}

	/**
	 * Gibt Vector mit den Pfaden der Unterordnern zurück
	 * @return
	 */
	public Vector<String> getLevelSetsPaths() {
		return this.levelsetpaths;
	}
	
	/**
	 * Gibt Vector mit den Pfaden der Unterordner zurück
	 * @return
	 */
	public Vector<String> getThemesPaths()
	{
		return this.themepaths;
	}

	
	// Daten setzen
	
	/**
	 * Setze zu nutzendes Levelset
	 */
	public void setCurrentLevelSet(LevelSet levelSet) {
		currentLevelSet = levelSet;
	}
	/**
	 * Setze zu nutzendes Theme
	 * @param theme Ordnername des zu nutzenden Themes
	 */
	public void setCurrentTheme(String theme)
	{
		currentTheme = theme;
	}

	/**
	 * Setze die Nummer des Levelsets im Vector, welches genutzt werden soll
	 * @param i Zahl des Levelsets im Vektor
	 */
	public void setLevelSetNumber(int i) {
		this.levelsetnumber = i;
	}
	
	/**
	 * Setze die Nummer des Themes im Vector, welches genutzt werden soll
	 * @param i Zahl des Themes im Vektor
	 */
	public void setThemeNumber(int i)
	{
		this.themenumber = i;
	}

	/**
	 * Setze Pfad zum Ordner in dem Levelset-Ordner sind
	 * @param levelsetspath OrdnerPfad in dem Levelset-Ordner sind
	 */
	public void setLevelSetsPath(String levelsetspath) {
		this.levelsetspath = levelsetspath;
	}
	
	/**
	 * Setze Pfad zum Ordner in dem Theme-Ordner sind
	 * @param themespath Ordnerpfad in dem Theme-Ordner enthalten sind
	 */
	public void setThemesPath(String themespath)
	{
		this.themespath = themespath;
	}
}
