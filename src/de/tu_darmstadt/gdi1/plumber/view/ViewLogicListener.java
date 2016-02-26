package de.tu_darmstadt.gdi1.plumber.view;

import java.awt.Color;

import de.tu_darmstadt.gdi1.plumber.model.events.EventListener;
import de.tu_darmstadt.gdi1.plumber.model.events.level.NewLevel;
import de.tu_darmstadt.gdi1.plumber.model.events.level.SelectionChange;
import de.tu_darmstadt.gdi1.plumber.model.events.level.Timer;
import de.tu_darmstadt.gdi1.plumber.model.events.level.WaterOut;
import de.tu_darmstadt.gdi1.plumber.model.events.level.WaterStart;
import de.tu_darmstadt.gdi1.plumber.model.events.level.WaterWin;
import de.tu_darmstadt.gdi1.plumber.model.events.levelelement.LevelElementEvent_abstract;
import de.tu_darmstadt.gdi1.plumber.view.game.Panel;
import de.tu_darmstadt.gdi1.plumber.view.game.Window;

//UNUSED
//import plumber.model.events.levelset.LevelSetEvent_abstract;

public class ViewLogicListener extends EventListener {
	
	private Window plumberWindow;
	
	private boolean water_started = false;
	
	public ViewLogicListener(Window plumberWindow) {
		this.plumberWindow = plumberWindow;
	}

	//plumber
	//UNUSED
	/*public void levelset(LevelSetEvent_abstract e)
	{
		//TODO LevelSet-Anzeige Updaten
		System.out.println("New LevelSet");
		
	}; //reports a Levelsetchange*/
	
	@Override
	public void _level(NewLevel e) //new level loaded
	{
		Panel p = (Panel) plumberWindow.getGamePanel(); //create GamePanel
		p.OnLevelChange(e.currentLevel);
		water_started = false; //water isn't started at beginning ;)
		plumberWindow.timeleft.setForeground(Color.BLACK);
		plumberWindow.levelnumber(plumberWindow.controller.logic.level.getLevel().getLevelnumber());//level nummer im levelset
		plumberWindow.levelsetname(plumberWindow.controller.logic.levelset.getLevelSet().getName()); //levelset ordner name
	}
	
	@Override
	/**
	 * Wenn Selektion über Pfeiltasten geändert wird
	 */
	public void _level(SelectionChange e)
	{
		Panel p = (Panel) plumberWindow.getGamePanel();
		p.OnLevelChange(e.currentLevel);
	}
	
	@Override
	/**
	 * Wenn Timer tickt (alle 750 ms)
	 */
	public void _level(Timer e)
	{
		plumberWindow.settime(plumberWindow.controller.logic.level.getLevel().getTimeLeft()); //set remaining time in gamewindow
		if(water_started)
		{
			Panel p = (Panel) plumberWindow.getGamePanel();
			p.OnLevelChange(plumberWindow.controller.logic.level.getLevel());
		}
	}
	
	@Override
	/**
	 * Wasser kommt nicht in Senke an -> GameOver
	 */
	public void _level(WaterOut e) //when water goes out
	{
		plumberWindow.gameoverdialog(); //you lose dialog
	}
	
	@Override
	/**
	 * Wasser erreicht Senke -> Level gewonnen
	 */
	public void _level(WaterWin e) //water comes to sink
	{ 
		plumberWindow.gamewindialog(); //you win dialog
		plumberWindow.controller.logic.stopGame(); //stop the game
		water_started = false;
		if(plumberWindow.controller.logic.levelset.nextLevel()) //next level if existent
		{
			plumberWindow.controller.logic.startGame(); //new level start
		}else
		{
			plumberWindow.levelsetenddialog(); //level set ending dialog
		}
	}
	
	@Override
	/**
	 * Wasserfluss startet
	 */
	public void _level(WaterStart e) //water start trigger
	{
		water_started = true; //water start true
	}
	
	/**
	 * restart Level
	 */
	public void restart(){
		plumberWindow.controller.logic.level.restart(); //call restart
	}
	
	/**
	 * UnDo aufruf
	 */
	public void UnDo(){
		plumberWindow.controller.logic.level.UnDo();
	}
	
	
	/**
	 * ReDo aufruf
	 */
	public void ReDo(){
		plumberWindow.controller.logic.level.ReDo();
	}
	
	
		/**
		 * Wenn Levelelement geändert wird 
		 * rotiert oder mit Wasser gefüllt
		 */
	public void levelelement(LevelElementEvent_abstract e){
		//Update gamewindow according to info in e
		Panel p = (Panel) plumberWindow.getGamePanel();
		p.OnLevelChange(plumberWindow.controller.logic.level.getLevel());
	};
}
