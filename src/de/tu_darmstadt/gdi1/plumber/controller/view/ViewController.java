package de.tu_darmstadt.gdi1.plumber.controller.view;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Locale;

import de.tu_darmstadt.gdi1.plumber.Plumber;
import de.tu_darmstadt.gdi1.plumber.controller.Controller;
import de.tu_darmstadt.gdi1.plumber.controller.options.Options;
import de.tu_darmstadt.gdi1.plumber.controller.view.theme.ThemeController;
import de.tu_darmstadt.gdi1.plumber.model.Model;
import de.tu_darmstadt.gdi1.plumber.model.exceptions.PlumberError;
import de.tu_darmstadt.gdi1.plumber.view.game.Window;
import de.tu_darmstadt.gdi1.plumber.view.tudframework.GamePanel;
import de.tu_darmstadt.gdi1.plumber.view.windows.AboutWindow;
import de.tu_darmstadt.gdi1.plumber.view.windows.DirectionsWindow;
import de.tu_darmstadt.gdi1.plumber.view.windows.HighscoreWindow;
import de.tu_darmstadt.gdi1.plumber.view.windows.MainMenuWindow;

import translator.Translator;

/**
 * view controller controls everything that has to do with the view,like:
 * windows and themes
 *
 */
public class ViewController {

	/**
	 * Refferenz zum Kontroller
	 */
	private Controller controller;
	
	/**
	 * Refferenz zu den Laufzeitdaten
	 */
	private Model modeldata;
	
	//Windows
	private Window gamewindow; //TODO put windows into model 
	private AboutWindow aboutwindow;
	private MainMenuWindow mainmenuwindow;
	private HighscoreWindow highscorewindow;
	private DirectionsWindow directionswindow;

	private ThemeController themes;

	//set language to German
	public Locale targetLocale = Locale.GERMANY;
	
	private Translator translator;
	
	/**
	 * get translator
	 * @return translator
	 */
	public Translator getTranslator()
	{
		return translator;
	}
	
	/**
	 * constructor 
	 * assigns variables, creates translator and theme controller
	 * @param controller
	 * @param modeldata
	 */
	public ViewController(Controller controller, Model modeldata)
	{
		this.modeldata = modeldata;
		this.controller = controller;
		
		translator = new Translator("Plumber", targetLocale);
		
		themes = new ThemeController(this, modeldata);
	}
	
	/**
	 * destroy all of the displayed windows and stop the game in case a game was played
	 */
	public void hideAll()
	{
		if(gamewindow != null)
		{
			gamewindow.dispose();
			gamewindow.finalize();
			controller.logic.stopGame();
		}
		
		
		if(highscorewindow != null)
		{
			highscorewindow.dispose();
		}
		
		if(mainmenuwindow != null)
		{
			mainmenuwindow.dispose();
		}
		 
		if(aboutwindow != null)
		{
			aboutwindow.dispose();
		}
		
		if(directionswindow != null)
		{
			directionswindow.dispose();
		}
	}
	
	/**
	 * create new main window and display it
	 */
	public void showMainMenuWindow() {
		hideAll();  //destroy existing windows
		mainmenuwindow = new MainMenuWindow(controller);
		mainmenuwindow.showWindow();
	}
	
	/**
	 * create new about window and display it
	 */
	public void showAboutWindow()
	{	
		hideAll();  //destroy existing windows
		aboutwindow = new AboutWindow(controller);
		aboutwindow.showWindow();
	}
	
	/**
	 * create new directions window and display it
	 */
	public void showDirectionsWindow()
	{
		hideAll();  //destroy existing windows
		directionswindow = new DirectionsWindow(controller);
		directionswindow.showWindow();
	}
	
	/**
	 * create new highscore window and display it
	 */
	public void showHighscoreWindow()
	{
		hideAll();  //destroy existing windows
		highscorewindow = new HighscoreWindow(controller);
		highscorewindow.showWindow();
	}
	
	/**
	 * create new game window and display it
	 */
	public void showGameWindow()
	{
		hideAll();  //destroy existing windows
		gamewindow = new Window(controller);
		
		if(!loadThemesPath(Options.getOption(Plumber.OPTION_THEMES_PATH).getValue()))
		{
			return; //TODO
		}
		
		controller.logic.reloadLevelSet();
		
		controller.logic.level.isCorrect(); //to trigger new-levelevent
		controller.logic.startGame();
		
		gamewindow.showWindow();
	}
	
	/**
	 * load theme
	 * @param position of the theme
	 * @return true if loading theme was successful  
	 */
	public boolean loadTheme(int position)
	{
		if(modeldata.getThemesPaths().size() > position && // load theme is possible if there is a theme at this
			position >= 0)                                 // position and the position is greater than or equal 0
		{
			try
			{
				this.themes.load(modeldata.getThemesPaths().get(position)); //TODO LOAD HERE this.levelset.setLevelSetPath();
			} catch(PlumberError e)
			{
				//Ignore wrong levelsets
				modeldata.getThemesPaths().remove(position);
				return loadTheme(position);
			}
			
			modeldata.setThemeNumber(position);
			return true;
		}
		
		modeldata.setThemeNumber(0);
		return false;
	}
	
	/**
	 *loads next theme 
	 * 
	 * @return true falls n�chstes Theme geladen werden konnte
	 */
	public boolean nextTheme(){
		modeldata.setThemeNumber(modeldata.getThemeNumber() +1);
		return loadTheme(modeldata.getThemeNumber());
	}
	
	/**
	 * loads previous theme
	 * 
	 * @return true falls letztes Theme geladen werden konnte
	 */
	public boolean previousTheme(){
		modeldata.setThemeNumber(modeldata.getThemeNumber() -1);
		return loadTheme(modeldata.getThemeNumber());
	}
	
	/**
	 * lade Theme-Pfad.
	 * 
	 * @param levelsetspath
	 * @return
	 */
	public boolean loadThemesPath(String themespath)
	{
		modeldata.getThemesPaths().clear();
		modeldata.setThemeNumber(0);
		modeldata.setThemesPath(themespath);
		
		//Filter: Nur Ordner
		FilenameFilter filter = new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return dir.isDirectory();
		    }
		};
		
		File dir = new File(themespath);

		String[] children = dir.list(filter);
		if (children == null || children.length == 0)
		{
		    //Either dir does not exist or is not a directory
			//or no directories
			return false;
		} else
		{
		    for (int i=0; i<children.length; i++)
		    {
		        // Get name of directory
		    	try
		    	{
		    		modeldata.getThemesPaths().add(dir.getCanonicalPath() + File.separator + children[i]);
		    	} catch(IOException e)
		    	{
		    		//TODO
		    	}
		    }
		}
		
		loadTheme(0);
		
		return true;
	}

	public GamePanel getGamePanel() {
		return gamewindow.getGamePanel();
	}
}
