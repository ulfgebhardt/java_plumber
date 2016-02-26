package de.tu_darmstadt.gdi1.plumber.controller.options;

import java.util.Vector;

/** static class
 * für Optionen
 *
 */
public class Options
{	
	private static Vector<Option> options = new Vector<Option>();
	
	public static Option getOption(String name)
	{
		for(int i=0; i < options.size();i++)
		{
			if(options.get(i).getName() == name)
			{
				return options.get(i);
			}
		}
		
		return null;
	}
	
	public static void setOption(Option o)
	{
		delOption(o.getName());
		options.add(o);
	}
	
	/** Löschen von Optionen
	 * @param name
	 * @return false wenn Option nicht vorhanden
	 */
	public static boolean delOption(String name)
	{
		for(int i=0; i< options.size(); i++)
		{
			if(options.get(i).getName() == name)
			{
				options.remove(i);
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean parseOptionsFile(String path)
	{
		//TODO
		return false;
	}
}
