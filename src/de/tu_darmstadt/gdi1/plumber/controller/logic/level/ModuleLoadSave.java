package de.tu_darmstadt.gdi1.plumber.controller.logic.level;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Vector;

import de.tu_darmstadt.gdi1.plumber.model.exceptions.PlumberError;
import de.tu_darmstadt.gdi1.plumber.model.exceptions.PlumberWarning;
import de.tu_darmstadt.gdi1.plumber.model.level.Level;
import de.tu_darmstadt.gdi1.plumber.model.levelelements.Element_abstract;
import de.tu_darmstadt.gdi1.plumber.model.levelelements.Pipe_Bent;
import de.tu_darmstadt.gdi1.plumber.model.levelelements.Pipe_Straight;
import de.tu_darmstadt.gdi1.plumber.model.levelelements.Sink;
import de.tu_darmstadt.gdi1.plumber.model.levelelements.Well;


/**beinhaltet:
 * from String und to String Methoden (zum umwandeln der Level)
 * Lädt und speichert Level
 */
public class ModuleLoadSave{

	//Namenszuweisungen
	public final static String LEVELELEMENT_WELL_UP 				= "a";
	public final static String LEVELELEMENT_WELL_RIGHT 				= "b";
	public final static String LEVELELEMENT_WELL_DOWN 				= "c";
	public final static String LEVELELEMENT_WELL_LEFT 				= "d";
	
	public final static String LEVELELEMENT_SINK_RIGHT 				= "l";
	public final static String LEVELELEMENT_SINK_UP 				= "k";
	public final static String LEVELELEMENT_SINK_DOWN 				= "m";
	public final static String LEVELELEMENT_SINK_LEFT 				= "n";
	
	public final static String LEVELELEMENT_PIPEBENT_LEFTDOWN 		= "1";
	public final static String LEVELELEMENT_PIPEBENT_LEFTUP 		= "2";
	public final static String LEVELELEMENT_PIPEBENT_RIGHTDOWN 		= "3";
	public final static String LEVELELEMENT_PIPEBENT_RIGHTUP 		= "4";
	
	public final static String LEVELELEMENT_PIPESTRAIGHT_UPDOWN 	= "5";
	public final static String LEVELELEMENT_PIPESTRAIGHT_LEFTRIGHT 	= "6";
	
	public final static String LEVELELEMENT_LINE_END 				= "\n";
	public final static String LEVELELEMENT_ADDITIONAL 				= "###";
	private static final String LEVEL_ADDITIONLAL_SEPERATOR 		= "\\|";
	private static final String LEVEL_ADDITIONAL_VALUE_SEPERATOR 	= "\\:";
	private static final Object LEVEL_ADDITIONAL_INFO_TIMETOWATER 	= "time_to_water";
	private static final Object LEVEL_ADDITIONAL_INFO_TIMEBETWEENWATER = "time_between_water";
	
	/** laden eines Levels von einem Path
	 * @param level
	 * @param filepath
	 * @throws PlumberError
	 */
	public static void loadLevel(Level level, String filepath) throws PlumberError{
		try
		{
			// Open the file that is the first 
            FileInputStream fstream = new FileInputStream(filepath);

            // Convert our input stream to a
            // DataInputStream
			DataInputStream in = new DataInputStream(fstream);

			String temps = new String("");
			
            // Continue to read chars while 
            // there are still some left to read
            while (in.available() !=0)
			{
				temps += (char)in.read();
			}

			in.close();
			
			fromString(level,temps);
		} 
		catch (Exception e)
		{
			throw new PlumberError();
		}
	}

	/** speichern eines Levels zu einem Path
	 * @param level
	 * @param filepath
	 * @throws PlumberError
	 */
	public static void saveLevel(Level level, String filepath) throws PlumberError{
		FileOutputStream out; // declare a file output object
		PrintStream p; // declare a print stream object

        try
        {
        	// Create a new file output stream
        	out = new FileOutputStream(filepath);

        	// Connect print stream to the output stream
        	p = new PrintStream( out );

            p.print(toString(level));

            p.close();
        }
        catch (Exception e)
        {
        	throw new PlumberError();
        }
	}
	
	/** umwandeln eines Levels von String
	 * @param level
	 * @param lvlstr
	 * @throws PlumberError
	 * @throws PlumberWarning
	 */
	public static void fromString(Level level, String lvlstr) throws PlumberError, PlumberWarning
	{
		//save lvlstr to be able to restart level
		level.setInitialLevel(lvlstr);
		level.getElements().clear();
		level.setTimeLeft(30000);
		level.setTimeleft_start(30000);
		level.setTimerIntervall(750);
		level.getHistory().clear();
		level.setWaterPos(null);
		level.setWaterOut(false);
		level.setWaterWin(false);
		
		if(level.getRestarts() < 0)
		{
			level.setRestarts(0);
		} else
		{
			level.setRestarts(level.getRestarts() +1);
		}
		
		String[] lines = lvlstr.split(LEVELELEMENT_LINE_END);
		
		boolean additionalline = false;
		
		for(int i=0; i < lines.length; i++) //bis Zeilenende
		{
			String line = lines[i]; 
			
			if(line.substring(0,LEVELELEMENT_ADDITIONAL.length()).equals(LEVELELEMENT_ADDITIONAL))
			{
				//Cut off additional-info-indicator
				line = line.substring(LEVELELEMENT_ADDITIONAL.length());
				
				//information -> plural
				
				String[] infos = line.split(LEVEL_ADDITIONLAL_SEPERATOR);
				
				for(int j=0; j<infos.length; j++)
				{
					//single information handling
					String[] info =  infos[j].split(LEVEL_ADDITIONAL_VALUE_SEPERATOR);
					
					if(info.length == 2) //name + value
					{
						if(info[0].toLowerCase().equals(LEVEL_ADDITIONAL_INFO_TIMETOWATER))
						{
							level.setTimeLeft(Integer.parseInt(info[1]));
							level.setTimeleft_start(Integer.parseInt(info[1]));
						} else
						
						if(info[0].toLowerCase().equals(LEVEL_ADDITIONAL_INFO_TIMEBETWEENWATER))
						{
							level.setTimerIntervall(Integer.parseInt(info[1])); //TODO MAKE CONSTANT
						} else
						{
							//TODO THROW SOMETHING
						}
					}
				}
				
				additionalline = true;
			} else
			{
				for(int j=0; j< line.length(); j++)
				{
					String letter = line.substring(j,j+1);
					Element_abstract e = null; //LevelElement ggf zugewiesen wird.
					
					Point actpos = new Point(i,j);
					
					if(additionalline)
					{
						actpos.x -= 1;
					}
					
					if(letter.equals(LEVELELEMENT_WELL_UP)) //start well
					{
						e = new Well(0,actpos);
					} else
				
					if (letter.equals(LEVELELEMENT_WELL_RIGHT))
					{
						e = new Well(1,actpos);
					} else
				
					if(letter.equals(LEVELELEMENT_WELL_DOWN))
					{
						e = new Well(2,actpos);
					} else
				
					if(letter.equals(LEVELELEMENT_WELL_LEFT))
					{
						e = new Well(3,actpos);
					} else//end well
					
					if(letter.equals(LEVELELEMENT_SINK_UP)) //start sink
					{
						e = new Sink(0, actpos);
					} else
				
					if(letter.equals(LEVELELEMENT_SINK_RIGHT))
					{
						e = new Sink(1, actpos);
					} else
				
					if(letter.equals(LEVELELEMENT_SINK_DOWN))
					{
						e = new Sink(2, actpos);
					} else
				
					if(letter.equals(LEVELELEMENT_SINK_LEFT))
					{
						e = new Sink(3, actpos);
					} else //end sink
					
					if(letter.equals(LEVELELEMENT_PIPEBENT_LEFTDOWN)) //start bent
					{
						e = new Pipe_Bent(2, actpos);
					} else
					
					if(letter.equals(LEVELELEMENT_PIPEBENT_LEFTUP))
					{
						e = new Pipe_Bent(3, actpos);
					} else
					
					if(letter.equals(LEVELELEMENT_PIPEBENT_RIGHTDOWN))
					{
						e = new Pipe_Bent(1, actpos);
					} else
					
					if(letter.equals(LEVELELEMENT_PIPEBENT_RIGHTUP))
					{
						e = new Pipe_Bent(0, actpos);
					} else //end bent
					
					if(letter.equals(LEVELELEMENT_PIPESTRAIGHT_UPDOWN)) //start straight
					{
						e = new Pipe_Straight(0, actpos);
					} else
					
					if(letter.equals(LEVELELEMENT_PIPESTRAIGHT_LEFTRIGHT))
					{
						e = new Pipe_Straight(1, actpos);
					} //end straight
					
					if(e != null) //element hinzufügen?
					{
						if(level.getElements().size() < actpos.x +1) //neue line
						{
							//neue line erzeugen
							Vector<Element_abstract> newline = new Vector<Element_abstract>();
							//element in neue line schreiben (1. Stelle)
							newline.add(e);
							//neue linie zu den anderen hinzufügen
							level.getElements().add(newline);
						} else
						{ //line existiert, element an vorhandene Line anfügen
							level.getElements().get(actpos.x).add(e);
						}
					}
				}// endfor line-counter
			}//endif
		}//endfor row-counter
	}
	
	/** umwandeln eines Levels zu String
	 * @param level
	 * @return result gibt den String aus
	 */
	public static String toString(Level level)
	{
		//Ergebnis
		String result = "";
		
		//Wird nicht benutzt, da sonst Tests Fehlschlagen
/*		{//additionalinfo hier
			result += LEVELELEMENT_ADDITIONAL;
			result += LEVEL_ADDITIONAL_INFO_TIMETOWATER +
					  LEVEL_ADDITIONAL_VALUE_SEPERATOR +
					  level.getTimeLeft() +
					  LEVEL_ADDITIONLAL_SEPERATOR;
			result += LEVEL_ADDITIONAL_INFO_TIMEBETWEENWATER +
			  		  LEVEL_ADDITIONAL_VALUE_SEPERATOR +
			  		  level.getTimerPeriod();
			result += LEVELELEMENT_LINE_END;
		}*/
		
		{ //Levelaufbau
			for(int i = 0; i < level.getElements().size(); i++) //lines
			{
				for(int j = 0; j < level.getElements().get(i).size();j++) //columns
				{
					Element_abstract e = level.getElements().get(i).get(j);
					
					//ist Element eine Quelle?
					if(e instanceof Well)
					{
						//Zeigt die Quelle nach oben
						if(e.getCon(Element_abstract.CON_TOP))
						{
							result += LEVELELEMENT_WELL_UP;
						}
						
						//Nach Rechts?
						if(e.getCon(Element_abstract.CON_RIGHT))
						{
							result += LEVELELEMENT_WELL_RIGHT;
						}
						
						//Nach Unten?
						if(e.getCon(Element_abstract.CON_BOTTOM))
						{
							result += LEVELELEMENT_WELL_DOWN;
						}
						
						//Nach Links?
						if(e.getCon(Element_abstract.CON_LEFT))
						{
							result += LEVELELEMENT_WELL_LEFT;
						}
					} //ende well 
					
					//ist Element eine Senke?
					if(e instanceof Sink) 
					{
						//Zeigt die Senke nach oben
						if(e.getCon(Element_abstract.CON_TOP))
						{
							result += LEVELELEMENT_SINK_UP;
						}
						
						//nach Rechts?
						if(e.getCon(Element_abstract.CON_RIGHT))
						{
							result += LEVELELEMENT_SINK_RIGHT;
						}
						
						//nach Unten?
						if(e.getCon(Element_abstract.CON_BOTTOM))
						{
							result += LEVELELEMENT_SINK_DOWN;
						}
						
						//nach Links?
						if(e.getCon(Element_abstract.CON_LEFT))
						{
							result += LEVELELEMENT_SINK_LEFT;
						}
					} //ende sink
					
					//ist Element eine Ecke?
					if(e instanceof Pipe_Bent) 
					{
						//von Oben nach Rechts?
						if(e.getCon(Element_abstract.CON_RIGHT) && e.getCon(Element_abstract.CON_TOP))
						{
							result += LEVELELEMENT_PIPEBENT_RIGHTUP;
						}
						
						//von Unten nach Rechts?
						if(e.getCon(Element_abstract.CON_RIGHT) && e.getCon(Element_abstract.CON_BOTTOM))
						{
							result += LEVELELEMENT_PIPEBENT_RIGHTDOWN;
						}
						
						//von Unten nach Links?
						if(e.getCon(Element_abstract.CON_LEFT) && e.getCon(Element_abstract.CON_BOTTOM))
						{
							result += LEVELELEMENT_PIPEBENT_LEFTDOWN;
						}
						
						//von Oben nach Links?
						if(e.getCon(Element_abstract.CON_LEFT) && e.getCon(Element_abstract.CON_TOP))
						{
							result += LEVELELEMENT_PIPEBENT_LEFTUP;
						}
					} //ende bent
					
					//ist Element eine Gerade?
					if(e instanceof Pipe_Straight) 
					{
						//von Oben nach Unten?
						if(e.getCon(Element_abstract.CON_TOP))
						{
							result += LEVELELEMENT_PIPESTRAIGHT_UPDOWN;
						}
						
						//von Links nach Rechts?
						if(e.getCon(Element_abstract.CON_RIGHT))
						{
							result += LEVELELEMENT_PIPESTRAIGHT_LEFTRIGHT;
						}
					} //ende straight
										
					
				} //endcolumns
				
				//Nächste Line
				result += LEVELELEMENT_LINE_END;
			} //endlines
		}
		
		//Ergebnis zurückgeben
		return result;
	}
	
}
