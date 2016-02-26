package de.tu_darmstadt.gdi1.plumber.model.highscores;

/**
 * Datenrepräsentation von einem Highscore-Eintrag
 */
public class HighscoreEntry{
	
	/**
	 * Vergangene Zeit
	 */
	private double time;
	
	/**
	 * Anzahl der Rotierschritte
	 */
	private int steps;
	
	/**
	 * Spielername
	 */
	private String playername;
	
	/**
	 * Erreichtes Level
	 */
	private int reachedlevel;
	
	/**
	 * KONSTRUKTOREN
	 */
	public HighscoreEntry(double time, int steps, String playername, int reachedlevel)
	{
		this.time = time;
		this.steps = steps;
		this.playername = playername;
		this.reachedlevel = reachedlevel;
	}
	
	public HighscoreEntry(double time, String playername, int reachedlevel)
	{
		this.time = time;
		this.steps = 0;
		this.playername = playername;
		this.reachedlevel = reachedlevel;
	}
	
	/**
	 * GETTER & SETTER
	 */
	
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}
	public int getSteps() {
		return steps;
	}
	public void setSteps(int steps) {
		this.steps = steps;
	}
	public String getPlayername() {
		return playername;
	}
	public void setPlayername(String playername) {
		this.playername = playername;
	}
	public int getReachedlevel() {
		return reachedlevel;
	}
	public void setReachedlevel(int reachedlevel) {
		this.reachedlevel = reachedlevel;
	}
}
