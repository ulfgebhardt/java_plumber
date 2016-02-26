package de.tu_darmstadt.gdi1.plumber.model.events;

import de.tu_darmstadt.gdi1.plumber.model.exceptions.PlumberError;
import de.tu_darmstadt.gdi1.plumber.model.exceptions.PlumberInfo;
import de.tu_darmstadt.gdi1.plumber.model.exceptions.PlumberWarning;

public abstract class EventListener {
	//errors
	public void error_info(PlumberInfo e){}
	public void error_warning(PlumberWarning e){};
	public void error_error(PlumberError e){};
	
	//plumber
	//UNUSED
	/*public void levelset(plumber.model.events.levelset.LevelSetEvent_abstract e){}; //reports a Levelsetchange
		public void _levelset(plumber.model.events.levelset.PreviousSet e){levelset(e);};
		public void _levelset(plumber.model.events.levelset.NextSet e){levelset(e);};*/
		//special events
		
	public void level(de.tu_darmstadt.gdi1.plumber.model.events.level.LevelEvent_abstract e){}; //reports a levelchange
		//special events
		public void _level(de.tu_darmstadt.gdi1.plumber.model.events.level.SelectionChange e){level(e);};
		public void _level(de.tu_darmstadt.gdi1.plumber.model.events.level.WaterOut e){level(e);};
		public void _level(de.tu_darmstadt.gdi1.plumber.model.events.level.WaterStart e){level(e);};
		public void _level(de.tu_darmstadt.gdi1.plumber.model.events.level.WaterWin e){level(e);};
		public void _level(de.tu_darmstadt.gdi1.plumber.model.events.level.Rotate e){level(e);};
		public void _level(de.tu_darmstadt.gdi1.plumber.model.events.level.NewLevel e){level(e);};
		public void _level(de.tu_darmstadt.gdi1.plumber.model.events.level.NewLevelElement e){level(e);};
		public void _level(de.tu_darmstadt.gdi1.plumber.model.events.level.Timer e){level(e);};
		
	public void levelelement(de.tu_darmstadt.gdi1.plumber.model.events.levelelement.LevelElementEvent_abstract e){}; //Reports a elementchange in current level
		//special events
		public void _levelelement(de.tu_darmstadt.gdi1.plumber.model.events.levelelement.ReDo e){levelelement(e);};
		public void _levelelement(de.tu_darmstadt.gdi1.plumber.model.events.levelelement.UnDo e){levelelement(e);};
		//UNUSED
		//public void _levelelement(plumber.model.events.levelelement.Water e){levelelement(e);};
		//public void _levelelement(plumber.model.events.levelelement.UnWater e){levelelement(e);};
		public void _levelelement(de.tu_darmstadt.gdi1.plumber.model.events.levelelement.Rotate e){levelelement(e);};
}
