package de.tu_darmstadt.gdi1.plumber.controller.logic.level;

import java.awt.Point;

import de.tu_darmstadt.gdi1.plumber.controller.logic.LogicController;
import de.tu_darmstadt.gdi1.plumber.controller.options.Option;
import de.tu_darmstadt.gdi1.plumber.controller.options.Options;
import de.tu_darmstadt.gdi1.plumber.model.exceptions.PlumberError;
import de.tu_darmstadt.gdi1.plumber.model.exceptions.PlumberWarning;
import de.tu_darmstadt.gdi1.plumber.model.highscores.Highscore;
import de.tu_darmstadt.gdi1.plumber.model.highscores.HighscoreEntry;
import de.tu_darmstadt.gdi1.plumber.model.level.Level;
import de.tu_darmstadt.gdi1.plumber.model.levelelements.Element_abstract;
import de.tu_darmstadt.gdi1.plumber.model.levelelements.Sink;
import de.tu_darmstadt.gdi1.plumber.model.levelelements.Well;


/**
 * Kontrolle der Zeit und des Wassers
 *
 */
public class ModuleStartEndTime {
	
	/** Neustart des Levels
	 * @param level
	 * @return true wenn Level neu geladen werden konnte
	 */
	public static boolean restartLevel(Level level)
	{
		try
		{
			ModuleLoadSave.fromString(level,level.getInitialLevel());
		} catch(PlumberError e)
		{
			return false;
		} catch(PlumberWarning w)
		{
			return false;
		}
		
		return true;
	}
	
	/** Wasser start
	 * @param level
	 */
	public static void startWater(Level level)
	{
		level.setTimeleft_end(level.getTimeLeft());
		level.setTimeLeft(0);
	}
	
	/** zählt Zeit runter und lässt Wasser starten und fortfließen
	 * @param level
	 * @param highscore
	 */
	public static void OnTimer(Level level, Highscore highscore)
	{
		//trigger event LevelEvent_Timer
		LogicController.triggerModelEvent(new de.tu_darmstadt.gdi1.plumber.model.events.level.Timer(level));
		
		if(level.getTimeLeft() > 0)
		{
			level.setTimeLeft(level.getTimeLeft() - level.getTimerPeriod()); 
			return;
		}
		
		if(!level.isLost() && !level.isSolved())
		{
		if(level.getWaterPos() == null)
		{
			try
			{
				level.setWaterPos(ModuleElements.FindElement(level,new Well(0,new Point(0,0))).getPos());
				ModuleElements.getElement(level, level.getWaterPos()).setWatered(true);
			}catch(PlumberError e)
			{
				return;
			}
			level.setWaterStarted(true);
		} else
		{
			//CAREFULL: ONLY ONE OUTGOING CONNECTION PER ELEMENT IS SUPPORTED
			Element_abstract e;
			try
			{
				e = ModuleElements.getElement(level, level.getWaterPos());
				
				if(e instanceof Sink)
				{
					if(!level.getWaterOut())
					{	
						level.setWaterWin(true);
						Option playernameopt = Options.getOption("playername");
						String playername = "player";
						if(playernameopt != null)
						{
							playername = playernameopt.getValue();
						}
						
						highscore.addHighscore(new HighscoreEntry(	level.getTimeleft_start()-level.getTimeleft_end() + level.getTimeleft_start()*level.getRestarts(),
																	level.getRotatesteps(),
																	playername,
																  	level.getLevelnumber()));
					}
					
					return;
				}
				
			} catch(PlumberError ex)
			{
				return;
			}
			
			Point water_pos = level.getWaterPos();
			
			if(e.getCon(Element_abstract.CON_TOP))
			{
				water_pos.x -=1;			
				try
				{
					if(!ModuleElements.getElement(level,water_pos).getCon(Element_abstract.CON_BOTTOM))
					{
						level.setWaterOut(true);
						level.setWaterWin(false);
						return;
					}
					
					if(!ModuleElements.getElement(level,water_pos).isWatered())
					{
						ModuleElements.getElement(level,water_pos).setWatered(true);
						level.setWaterPos(water_pos);
						return;
					} else
					{
						water_pos.x += 1;
					}
					
				}catch(PlumberError ex)
				{
					//aus dem spielfeld rausgelaufen
					level.setWaterOut(true);
					level.setWaterWin(false);
					return;
				}
			}
			
			if(e.getCon(Element_abstract.CON_RIGHT))
			{
				water_pos.y +=1;			
				try
				{
					if(!ModuleElements.getElement(level,water_pos).getCon(Element_abstract.CON_LEFT))
					{
						level.setWaterOut(true);
						level.setWaterWin(false);
						return;
					}
					
					if(!ModuleElements.getElement(level,water_pos).isWatered())
					{
						ModuleElements.getElement(level,water_pos).setWatered(true);
						level.setWaterPos(water_pos);
						return;
					} else
					{
						water_pos.y -= 1;
					}
					
				}catch(PlumberError ex)
				{
					//aus dem spielfeld rausgelaufen
					level.setWaterOut(true);
					level.setWaterWin(false);
					return;
				}
			}
			
			if(e.getCon(Element_abstract.CON_BOTTOM))
			{
				water_pos.x +=1;			
				try
				{
					if(!ModuleElements.getElement(level,water_pos).getCon(Element_abstract.CON_TOP))
					{
						level.setWaterOut(true);
						level.setWaterWin(false);
						return;
					}
				
					if(!ModuleElements.getElement(level,water_pos).isWatered())
					{
						ModuleElements.getElement(level,water_pos).setWatered(true);
						level.setWaterPos(water_pos);
						return;
					} else
					{
						water_pos.x -= 1;
					}
				}catch(PlumberError ex)
				{
					//aus dem spielfeld rausgelaufen
					level.setWaterOut(true);
					level.setWaterWin(false);
					return;
				}
			}
			
			if(e.getCon(Element_abstract.CON_LEFT))
			{
				water_pos.y -=1;			
				try
				{
					if(!ModuleElements.getElement(level,water_pos).getCon(Element_abstract.CON_RIGHT))
					{
						level.setWaterOut(true);
						level.setWaterWin(false);
						return;
					}
					
					if(!ModuleElements.getElement(level,water_pos).isWatered())
					{
						ModuleElements.getElement(level,water_pos).setWatered(true);
						level.setWaterPos(water_pos);
						return;
					} else
					{
						water_pos.y += 1;
					}
					
				}catch(PlumberError ex)
				{
					//aus dem spielfeld rausgelaufen
					level.setWaterOut(true);
					level.setWaterWin(false);
					return;
				}
			}
			
		}
		}
	}
}
