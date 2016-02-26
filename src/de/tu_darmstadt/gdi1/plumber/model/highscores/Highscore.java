package de.tu_darmstadt.gdi1.plumber.model.highscores;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Vector;

/**
 * Highscore Berechnung
 *
 * ACHTUNG: Enhält Controller-Teile
 */
public class Highscore{
	/**
	 * Deffinitionen für Dateiname und Syntax-Konstanten
	 */
	public final static String HIGHSCORE_FILENAME = "highscore.hsc";
	public final static String HIGHSCORE_LINEEND = "\n";
	public final static String HIGHSCORE_VALUESEPERATOR = ";";
	
	/**
	 * Einzelne Highscore-Einträge
	 * 
	 * @see HighscoreEntry
	 */
	private Vector<HighscoreEntry> HighscoreEntries = new Vector<HighscoreEntry>();
	
	/**
	 * Pfad des Levelsets -> von dort wird die Highscore-Datei geladen, falls vorhanden
	 * 
	 * und gespeichert
	 */
	private String levelsetpath = "";
	
	/** 
	 * Läd Highscore-Datei- falls vorhanden
	 * 
	 * @param levelsetpath
	 */
	public Highscore(String levelsetpath) {
		
		this.levelsetpath = levelsetpath;
		
		String highscorepath = levelsetpath + File.separator + HIGHSCORE_FILENAME;
		
		try
		{
			// Open the file that is the first 
            FileInputStream fstream = new FileInputStream(highscorepath);

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
		
			String[] scores = temps.split(HIGHSCORE_LINEEND);
			for(int i = 0; i< scores.length; i++)
			{
				String[] score = scores[i].split(HIGHSCORE_VALUESEPERATOR);
				
				if(score.length == 4)
				{
					addHighscore(new HighscoreEntry(Double.parseDouble(score[0]),
													Integer.parseInt(score[1]),
													score[2],
													Integer.parseInt(score[3])), false);
				}
			}
		} 
		catch (Exception e)
		{
			//NO HIGHSCOREFILE FOUND FOR THIS LEVELSET
		}
	}

	/** Punkteberechnung
	 * @param e
	 * @return int Punktestand
	 */
	public static int calculatePoints(HighscoreEntry e)
	{
		return (int)((e.getReachedlevel()+1)*10000 + //ReachedLevel +1 (da bei 0 beginnend) (*10000 zur Gewichtung)
					(120-(e.getTime()/1000)*100) + //120sec - Verbrauchte Zeit (*100 zur Gewichtung)
					(1000-e.getSteps()));  // 1000-Steps - benötigte Steps
	}
	
	/**
	 * Speichert alle vorhandenen Highscore-Einträge nach Datei
	 * (erstellt Datei falls nicht vorhanden)
	 * 
	 * Syntax: Pro Zeile ein Eintrag
	 * 
	 * Eintrag: zeit;steps;playername;reachedlevel
	 */
	public void saveToFile()
	{		
		FileOutputStream out; // declare a file output object
		PrintStream p; // declare a print stream object

        try
        {
        	// Create a new file output stream
        	out = new FileOutputStream(levelsetpath + File.separator + HIGHSCORE_FILENAME);

        	// Connect print stream to the output stream
        	p = new PrintStream( out );

        	for(int i = 0; i< HighscoreEntries.size(); i++)
        	{
        		p.print(HighscoreEntries.get(i).getTime() +
        				HIGHSCORE_VALUESEPERATOR +
        				HighscoreEntries.get(i).getSteps() +
        				HIGHSCORE_VALUESEPERATOR +
        				HighscoreEntries.get(i).getPlayername() +
        				HIGHSCORE_VALUESEPERATOR +
        				HighscoreEntries.get(i).getReachedlevel() +
        				HIGHSCORE_LINEEND);
        	}

            p.close();
        }
        catch (Exception e)
        {
        	return; //TODO FILEERROR
        }
	}
	
	/** adden zur Highscoreliste
	 * @param e
	 */
	public void addHighscore(HighscoreEntry e)
	{
		addHighscore(e,true);
	}
	
	/**
	 * Hinzufügen zur Highscore-Liste + speicher zu Datei, wenn savetofile
	 * 
	 * Sortiert automatisch Einträge,
	 * löscht Einträge, wenn > 10
	 * 
	 * @param e
	 * @param savetofile
	 */
	public void addHighscore(HighscoreEntry e, boolean savetofile)
	{
		double points = calculatePoints(e); 
		
		for(int i=0; i<HighscoreEntries.size(); i++)
		{
			if(calculatePoints(HighscoreEntries.get(i)) < points)
			{
				HighscoreEntries.insertElementAt(e, i);
				return;
			}
		}
		
		HighscoreEntries.add(e);
		
		while(HighscoreEntries.size() > 10)
		{
			HighscoreEntries.remove(HighscoreEntries.size() -1);
		}
		
		if(savetofile)
		{
			saveToFile();
		}
	}
	
	/**
	 * Hinzufügen-Hilfsfunktion, erstellt Highscore-Entry
	 * 
	 * @param time
	 * @param steps
	 * @param playername
	 * @param reachedlevel
	 */
	public void addHighscoreEntry(double time, int steps, String playername, int reachedlevel)
	{
		addHighscore(new HighscoreEntry(time, steps, playername, reachedlevel));
	}
	
	/**
	 * Hinzufügen-Hilfsfunktion, erstellt Highscore-Entry
	 * 
	 * @param time
	 * @param playername
	 * @param reachedlevel
	 */
	public void addHighscoreEntry(double time, String playername, int reachedlevel)
	{
		addHighscore(new HighscoreEntry(time, playername, reachedlevel));
	}
	
	/**
	 * Zählt Einträge in Highscoreliste
	 * 
	 * @return Anzahl
	 */
	public int count()
	{
		return HighscoreEntries.size();
	}
	
	/**
	 * Löscht Liste, speichert leere Liste in Datei 
	 */
	public void reset()
	{
		HighscoreEntries.clear();
		saveToFile();
	}
	
	/**
	 * Gibt Highscore-Entry an position x zurück, falls vorhanden, sonst null
	 * @param pos
	 * @return
	 */
	public HighscoreEntry getAt(int pos)
	{
		if(HighscoreEntries.size() > pos &&
			pos >= 0)
		{
			return HighscoreEntries.get(pos);
		}
		
		return null;
	}
}
