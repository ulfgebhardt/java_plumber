package de.tu_darmstadt.gdi1.plumber.controller.logic.level;

import de.tu_darmstadt.gdi1.plumber.controller.logic.LogicController;
import de.tu_darmstadt.gdi1.plumber.model.events.level.NewLevel;
import de.tu_darmstadt.gdi1.plumber.model.events.level.NewLevelElement;
import de.tu_darmstadt.gdi1.plumber.model.level.Level;
import de.tu_darmstadt.gdi1.plumber.model.levelelements.Sink;
import de.tu_darmstadt.gdi1.plumber.model.levelelements.Well;

/**
 * finds out if level is correct
 *
 */
public class ModuleIsCorrect {

	/**
	 * checks correctness of level, 
     * meaning: do all of the lines have the same length, 
     *          is the exactly one sink and one well 
     *          
     * Löst das Event NewLevel aus -> Diese funktion muss aufgerufen werden,
     * wenn das Interface das Level anzeigen soll
	 * 
	 * @param level
	 * @return true if level is correct
	 */
	public static boolean isCorrect(Level level)
	{	
		boolean well = false;
		boolean sink = false;
		
		int linelength = 0;
		
		for(int i = 0; i < level.getElements().size(); i++)
		{
			if(i == 0)
			{
				linelength = level.getElements().get(i).size();
			}
			
			//same line length?
			if(level.getElements().get(i).size() != linelength)
			{
				return false;
			}
			
			for(int j = 0; j < level.getElements().get(i).size(); j++)
			{
				//one sink check
				if(level.getElements().get(i).get(j) instanceof Sink)
				{
					if(sink) //2 sinks
					{
						return false;
					}
					
					sink = true;
				}
				
				//one well check
				if(level.getElements().get(i).get(j) instanceof Well)
				{
					if(well) //2 wells
					{
						return false;
					}
					
					well = true;
				}
			}
		}
		
		if(!sink || !well) //no sink or well
		{
			return false;
		}
		
		for(int i = 0; i < level.getElements().size(); i++)
		{
			
			for(int j = 0; j < level.getElements().get(i).size(); j++)
			{
				LogicController.triggerModelEvent(new NewLevelElement(level));
			}
		
		}
		
		LogicController.triggerModelEvent(new NewLevel(level));
		
		return true;
	}
}
