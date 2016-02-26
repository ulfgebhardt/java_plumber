package de.tu_darmstadt.gdi1.plumber;

import java.io.File;
import java.io.IOException;

import de.tu_darmstadt.gdi1.plumber.controller.Controller;
import de.tu_darmstadt.gdi1.plumber.controller.options.Option;
import de.tu_darmstadt.gdi1.plumber.controller.options.Options;


/**
 * Startet das Spiel, beinhaltet main-funktion
 * 
 * parsed cmd-line
 * 
 * @author rylon
 * @version 0.1
 */
public class Plumber {

	/**
	 * Instanz des PlumberControllers
	 */
	private Controller controller;
	
	/**
	 * Options-Konstanten
	 */
	public final static String OPTION_APP_PATH = "ApplicationPath";
	public final static String OPTION_THEMES_PATH = "ThemesPath";
	public final static String OPTION_LEVELSETS_PATH = "LevelSetsPath";
	
	/**
	 * Parse Optionen aus den Cmd-Line Parametern
	 * 
	 * @param args
	 */
	public Plumber(String[] args)
	{		
		try
		{
			Options.setOption(new Option(OPTION_APP_PATH,new File(".").getCanonicalPath()));
			Options.setOption(new Option(OPTION_THEMES_PATH,new File(".").getCanonicalPath() + File.separator + "images" + File.separator ));
			Options.setOption(new Option(OPTION_LEVELSETS_PATH,new File(".").getCanonicalPath() + File.separator + "levelsets" + File.separator));
		} catch (IOException e)
		{
			//TODO ERROR
		}
	}
	
	/**
	 * Startet das Spiel
	 */
	public void start()
	{
		//UNUSED
		//try // Alle Exceptions an den Exceptioncatcher geben.
		//{
			controller = new Controller();
			controller.view.showMainMenuWindow();
		//} catch(Exception e)
		//{
			//UNUSED
			//Send Exceptions to ExceptionCatcher - Always do that!
			//plumber.controller.exceptioncatcher.ExceptionCatcher.add(e);
		//}
	}
	
	/**
	 * Main Methode
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		new Plumber(args).start(); //Übergebe args and Plumber
	}

}
