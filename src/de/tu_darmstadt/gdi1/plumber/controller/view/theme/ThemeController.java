package de.tu_darmstadt.gdi1.plumber.controller.view.theme;

import java.io.File;

import de.tu_darmstadt.gdi1.plumber.controller.view.ViewController;
import de.tu_darmstadt.gdi1.plumber.model.Model;
import de.tu_darmstadt.gdi1.plumber.model.exceptions.PlumberError;
import de.tu_darmstadt.gdi1.plumber.view.tudframework.InternalFailureException;
import de.tu_darmstadt.gdi1.plumber.view.tudframework.InvalidOperationException;
import de.tu_darmstadt.gdi1.plumber.view.tudframework.ParameterOutOfRangeException;

public class ThemeController {

	/*=========================Constant Identifier=========================*/
	public final static String WELL_UP_UNFILLED_IDENT = "WELL_UP_UNFILLED" ;
	public final static String WELL_RIGHT_UNFILLED_IDENT = "WELL_RIGHT_UNFILLED" ;
	public final static String WELL_LEFT_UNFILLED_IDENT = "WELL_LEFT_UNFILLED" ;
	public final static String WELL_DOWN_UNFILLED_IDENT = "WELL_DOWN_UNFILLED" ;
	
	public final static String WELL_UP_FILLED_IDENT = "WELL_UP_FILLED" ;
	public final static String WELL_RIGHT_FILLED_IDENT = "WELL_RIGHT_FILLED" ;
	public final static String WELL_LEFT_FILLED_IDENT = "WELL_LEFT_FILLED" ;
	public final static String WELL_DOWN_FILLED_IDENT = "WELL_DOWN_FILLED" ;
	
	public final static String SINK_UP_UNFILLED_IDENT = "SINK_UP_UNFILLED" ;
	public final static String SINK_RIGHT_UNFILLED_IDENT = "SINK_RIGHT_UNFILLED" ;
	public final static String SINK_LEFT_UNFILLED_IDENT = "SINK_LEFT_UNFILLED" ;
	public final static String SINK_DOWN_UNFILLED_IDENT = "SINK_DOWN_UNFILLED" ;
		
	public final static String SINK_UP_FILLED_IDENT = "SINK_UP_FILLED" ;
	public final static String SINK_RIGHT_FILLED_IDENT = "SINK_RIGHT_FILLED" ;
	public final static String SINK_LEFT_FILLED_IDENT = "SINK_LEFT_FILLED" ;
	public final static String SINK_DOWN_FILLED_IDENT = "SINK_DOWN_FILLED" ;
	
	public final static String BENT_LEFT_UP_UNFILLED_IDENT = "BENT_LEFT_UP_UNFILLED" ;
	public final static String BENT_RIGHT_UP_UNFILLED_IDENT = "BENT_RIGHT_UP_UNFILLED" ;
	public final static String BENT_LEFT_DOWN_UNFILLED_IDENT = "BENT_LEFT_DOWN_UNFILLED" ;
	public final static String BENT_RIGHT_DOWN_UNFILLED_IDENT = "BENT_RIGHT_DOWN_UNFILLED" ;

	public final static String BENT_LEFT_UP_FILLED_IDENT = "BENT_LEFT_UP_FILLED" ;
	public final static String BENT_RIGHT_UP_FILLED_IDENT = "BENT_RIGHT_UP_FILLED" ;
	public final static String BENT_LEFT_DOWN_FILLED_IDENT = "BENT_LEFT_DOWN_FILLED" ;
	public final static String BENT_RIGHT_DOWN_FILLED_IDENT = "BENT_RIGHT_DOWN_FILLED" ;
	
	public final static String STRAIGHT_UP_DOWN_UNFILLED_IDENT = "STRAIGHT_UP_DOWN_UNFILLED";
	public final static String STRAIGHT_LEFT_RIGHT_UNFILLED_IDENT = "STRAIGHT_LEFT_RIGHT_UNFILLED";

	public final static String STRAIGHT_UP_DOWN_FILLED_IDENT = "STRAIGHT_UP_DOWN_FILLED";
	public final static String STRAIGHT_LEFT_RIGHT_FILLED_IDENT = "STRAIGHT_LEFT_RIGHT_FILLED";
	/*=========================Constant Identifier End=========================*/	
	
	/*=========================Constant ImageNames=========================*/	
	public final static String WELL_UP_UNFILLED_IMG = "source-up.png";
	public final static String WELL_RIGHT_UNFILLED_IMG = "source-right.png";
	public final static String WELL_LEFT_UNFILLED_IMG = "source-left.png";
	public final static String WELL_DOWN_UNFILLED_IMG = "source-down.png";
	
	public final static String WELL_UP_FILLED_IMG = "source-up-filled.png";
	public final static String WELL_RIGHT_FILLED_IMG = "source-right-filled.png";
	public final static String WELL_LEFT_FILLED_IMG = "source-left-filled.png";
	public final static String WELL_DOWN_FILLED_IMG = "source-down-filled.png";
	
	public final static String SINK_UP_UNFILLED_IMG = "sink-up.png";
	public final static String SINK_RIGHT_UNFILLED_IMG = "sink-right.png";
	public final static String SINK_LEFT_UNFILLED_IMG = "sink-left.png";
	public final static String SINK_DOWN_UNFILLED_IMG = "sink-down.png";
	
	public final static String SINK_UP_FILLED_IMG = "sink-up-filled.png";
	public final static String SINK_RIGHT_FILLED_IMG = "sink-right-filled.png";
	public final static String SINK_LEFT_FILLED_IMG = "sink-left-filled.png";
	public final static String SINK_DOWN_FILLED_IMG = "sink-down-filled.png";
	
	public final static String BENT_LEFT_UP_UNFILLED_IMG = "left-up.png";
	public final static String BENT_RIGHT_UP_UNFILLED_IMG = "right-up.png";
	public final static String BENT_LEFT_DOWN_UNFILLED_IMG = "left-down.png";
	public final static String BENT_RIGHT_DOWN_UNFILLED_IMG = "right-down.png";
	
	public final static String BENT_LEFT_UP_FILLED_IMG = "left-up-filled.png";
	public final static String BENT_RIGHT_UP_FILLED_IMG = "right-up-filled.png";
	public final static String BENT_LEFT_DOWN_FILLED_IMG = "left-down-filled.png";
	public final static String BENT_RIGHT_DOWN_FILLED_IMG = "right-down-filled.png";
	
	public final static String STRAIGHT_UP_DOWN_UNFILLED_IMG = "vertical.png";
	public final static String STRAIGHT_LEFT_RIGHT_UNFILLED_IMG = "horizontal.png";

	public final static String STRAIGHT_UP_DOWN_FILLED_IMG = "vertical-filled.png";
	public final static String STRAIGHT_LEFT_RIGHT_FILLED_IMG = "horizontal-filled.png";
	/*=========================Constant ImageNames End=========================*/
	/**
	 * Controller-Refferenz
	 */
	private ViewController view;
	
	/**
	 * Laufzeitdaten-Refferenz
	 */
	private Model modeldata;

	public ThemeController(ViewController view, Model modeldata)
	{
		this.view = view;
		this.modeldata = modeldata;
	}

	/**
	 * Bilder registrieren.
	 * @param themepath Pfad in den Bilder Ordner
	 * @throws PlumberError
	 */
	public void load(String themepath) throws PlumberError {
		try {
			
			view.getGamePanel().clearImages(); //Bilder deregistrieren.
			
			//Registrire Bilder
			themepath += File.separator;
			view.getGamePanel().registerImage(WELL_UP_UNFILLED_IDENT, themepath + WELL_UP_UNFILLED_IMG);
			view.getGamePanel().registerImage(WELL_RIGHT_UNFILLED_IDENT, themepath + WELL_RIGHT_UNFILLED_IMG);
			view.getGamePanel().registerImage(WELL_LEFT_UNFILLED_IDENT, themepath + WELL_LEFT_UNFILLED_IMG);
			view.getGamePanel().registerImage(WELL_DOWN_UNFILLED_IDENT, themepath + WELL_DOWN_UNFILLED_IMG);
			view.getGamePanel().registerImage(WELL_UP_FILLED_IDENT, themepath + WELL_UP_FILLED_IMG);
			view.getGamePanel().registerImage(WELL_RIGHT_FILLED_IDENT, themepath + WELL_RIGHT_FILLED_IMG);
			view.getGamePanel().registerImage(WELL_LEFT_FILLED_IDENT, themepath + WELL_LEFT_FILLED_IMG);
			view.getGamePanel().registerImage(WELL_DOWN_FILLED_IDENT, themepath + WELL_DOWN_FILLED_IMG);
			
			view.getGamePanel().registerImage(SINK_UP_UNFILLED_IDENT, themepath + SINK_UP_UNFILLED_IMG);
			view.getGamePanel().registerImage(SINK_RIGHT_UNFILLED_IDENT, themepath + SINK_RIGHT_UNFILLED_IMG);
			view.getGamePanel().registerImage(SINK_LEFT_UNFILLED_IDENT, themepath + SINK_LEFT_UNFILLED_IMG);
			view.getGamePanel().registerImage(SINK_DOWN_UNFILLED_IDENT, themepath + SINK_DOWN_UNFILLED_IMG);
			view.getGamePanel().registerImage(SINK_UP_FILLED_IDENT, themepath + SINK_UP_FILLED_IMG);
			view.getGamePanel().registerImage(SINK_RIGHT_FILLED_IDENT, themepath + SINK_RIGHT_FILLED_IMG);
			view.getGamePanel().registerImage(SINK_LEFT_FILLED_IDENT, themepath + SINK_LEFT_FILLED_IMG);
			view.getGamePanel().registerImage(SINK_DOWN_FILLED_IDENT, themepath + SINK_DOWN_FILLED_IMG);
			
			view.getGamePanel().registerImage(BENT_LEFT_UP_UNFILLED_IDENT, themepath + BENT_LEFT_UP_UNFILLED_IMG);
			view.getGamePanel().registerImage(BENT_RIGHT_UP_UNFILLED_IDENT, themepath + BENT_RIGHT_UP_UNFILLED_IMG);
			view.getGamePanel().registerImage(BENT_LEFT_DOWN_UNFILLED_IDENT, themepath + BENT_LEFT_DOWN_UNFILLED_IMG);
			view.getGamePanel().registerImage(BENT_RIGHT_DOWN_UNFILLED_IDENT, themepath + BENT_RIGHT_DOWN_UNFILLED_IMG);
			view.getGamePanel().registerImage(BENT_LEFT_UP_FILLED_IDENT, themepath + BENT_LEFT_UP_FILLED_IMG);
			view.getGamePanel().registerImage(BENT_RIGHT_UP_FILLED_IDENT, themepath + BENT_RIGHT_UP_FILLED_IMG);
			view.getGamePanel().registerImage(BENT_LEFT_DOWN_FILLED_IDENT, themepath + BENT_LEFT_DOWN_FILLED_IMG);
			view.getGamePanel().registerImage(BENT_RIGHT_DOWN_FILLED_IDENT, themepath + BENT_RIGHT_DOWN_FILLED_IMG);
			
			view.getGamePanel().registerImage(STRAIGHT_UP_DOWN_UNFILLED_IDENT, themepath + STRAIGHT_UP_DOWN_UNFILLED_IMG);
			view.getGamePanel().registerImage(STRAIGHT_LEFT_RIGHT_UNFILLED_IDENT, themepath + STRAIGHT_LEFT_RIGHT_UNFILLED_IMG);
			view.getGamePanel().registerImage(STRAIGHT_UP_DOWN_FILLED_IDENT, themepath + STRAIGHT_UP_DOWN_FILLED_IMG);
			view.getGamePanel().registerImage(STRAIGHT_LEFT_RIGHT_FILLED_IDENT, themepath + STRAIGHT_LEFT_RIGHT_FILLED_IMG);
			
			
		} catch (ParameterOutOfRangeException e) {
			throw new PlumberError();
		} catch (InvalidOperationException e) {
			throw new PlumberError();
		} catch (InternalFailureException e) {
			throw new PlumberError();
		}
		
		modeldata.setCurrentTheme(themepath);
	}
}
