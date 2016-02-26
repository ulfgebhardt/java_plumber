package de.tu_darmstadt.gdi1.plumber.model.level;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import de.tu_darmstadt.gdi1.plumber.controller.logic.LogicController;
import de.tu_darmstadt.gdi1.plumber.controller.logic.level.ModuleStartEndTime;
import de.tu_darmstadt.gdi1.plumber.model.events.level.WaterOut;
import de.tu_darmstadt.gdi1.plumber.model.events.level.WaterStart;
import de.tu_darmstadt.gdi1.plumber.model.events.level.WaterWin;
import de.tu_darmstadt.gdi1.plumber.model.exceptions.PlumberError;
import de.tu_darmstadt.gdi1.plumber.model.highscores.Highscore;
import de.tu_darmstadt.gdi1.plumber.model.history.History;
import de.tu_darmstadt.gdi1.plumber.model.levelelements.Element_abstract;

import java.util.Vector;


/**
 * Level-Daten-Repräsentation
 */
public class Level{
	
	/**
	 * Zweidimensionales Array von LevelElementen.
	 * 
	 * Repräsentiert das Spielfeld
	 */
	private Vector<Vector<Element_abstract>> elements = new Vector<Vector<Element_abstract>>();
	
	/**
	 * History der Rotationen
	 * @see History
	 */
	private History history = new History();
	
	/**
	 * Ungeparster String, der das Level representiert
	 * 
	 * Wird benutzt um beim restart() zu ermöglichen (string wird neu geparst)
	 */
	private String initialLevel;
	
	/**
	 * Ist das Wasser bereits angestellt?
	 */
	private boolean water_started = false;

	/**
	 * Hat das Wasser die Senke erreicht?
	 */
	private boolean water_win = false;
	
	/**
	 * Hat das Wasser eine Wand/fehlende Verbindung erreicht und ist "ausgelaufen"
	 */
	private boolean water_out = false;

	/**
	 * Wie viel Zeit bis zum Wasser Start
	 * 
	 * std 30sec
	 * 
	 * Wird zur Highscoreberechnung benutzt
	 */
	private int timeleft_start = 30000;
	
	/**
	 * Wie viel Zeit war übrig, wenn das Wasser zu laufen anfängt
	 * (wenn der Spieler das Wasser manuel startet)
	 *  
	 * Wird zur Highscoreberechnung benutzt
	 */
	private int timeleft_end = 0;
	/**
	 * Wie oft hat der Spieler das Spiel neugestartet
	 * 
	 * Jeder Restart erhöht die benötigte Zeit in der Highscore um timeleft_start
	 * 
	 * Wird zur Highscoreberechnung benutzt
	 */
	private int restarts = -1;
	
	/**
	 * Nummer des Levels
	 */
	private int levelnumber = 0;
	
	/**
	 * Wie oft hat der Spieler Elemente in Level rotiert
	 * (schließt undo redo Rotationen ein)
	 */
	private int rotatesteps = 0;
	
	/**
	 * Wie viel Zeit ist übrig bis das Wasser von aleine
	 * anfängt zu laufen? in ms
	 */
	private int timeleft = 30000; // std = 30sec pro level
	
	/**
	 * In Welchen Abständen tickt der Timer? in ms
	 */
	private int timerperiod = 750; // repeat every 750ms
	
	/**
	 * Zeitgeber, wird beim Erstellen des Levels erstellt. 
	 */
	private Timer timer;
	
	/**
	 * Aktuelle Wasserposition.
	 * 
	 * Wenn Wasser nicht gestartet ist ist Water_pos = null;
	 */
	private Point water_pos = null;

	/**
	 * Reffrenz zur LevelsetHighscore
	 */
	private Highscore highscore;
	
	/**
	 * Konstruktor,
	 * 
	 * Benötigt eine Refferenz zu einer Highscore(vom Levelset), da 
	 * das Level die highscore schreibt, falls der Spieler das Level
	 * gewinnt.
	 * 
	 * @param h Highscorerefferenz
	 */
	public Level(Highscore h)
	{
		this.highscore = h;
		
		timer = new Timer(timerperiod, new ActionListener() {
				public void actionPerformed(ActionEvent actionEvent)
				{
					//Highscore wird �bergeben um beim gewinnen ein eintrag zu machen
					ModuleStartEndTime.OnTimer(getThis(),highscore);
				}
		}); 
	}
	
	/**
	 * Startet das Wasser beim nächsten Timertick
	 * 
	 * timeleft wird auf 0 gesetzt.
	 */
	public void startWater()
	{
		timeleft = 0;
	}
	
	/**
	 * Gibt Größe des Levels in einem Point(x,y)
	 * zurück.
	 * 
	 * Fehler wenn keine Elemente vorhanden sind
	 * 
	 * @return Point mit Levelgröße
	 * @throws PlumberError
	 */
	public Point getLevelSize() throws PlumberError
	{
		Point p = new Point();
		
		p.x = elements.size(); 
		if(p.x > 0)
		{
			p.y = elements.get(0).size();
			return p;
		}
		
		throw new PlumberError();
	}
	
	
	////////////////////////////////////////////////
	/**
	 * GETTER AND SETTER BELOW
	 */
	////////////////////////////////////////////////
	
	/**
	 * Um auf diese Instanz in Timerevent zuzugreifen
	 */
	private Level getThis()
	{
		return this;
	}
	
	public History getHistory() {
		return history;
	}
	
	public Vector<Vector<Element_abstract>> getElements()
	{
		return elements;
	}
	
	public boolean isWater_started() {
		return water_started;
	}
	
	public Timer getTimer()
	{
		return timer;
	}
	
	public boolean isSolved(){
		return water_win;
	}
	
	public boolean isLost(){
		return water_out;
	}

	public void setInitialLevel(String lvlstr) {
		this.initialLevel = lvlstr;
	}

	public String getInitialLevel() {
		return initialLevel;
	}

	public int getTimeLeft() {
		return timeleft;
	}

	public int getTimerPeriod() {
		return timerperiod;
	}

	public Point getWaterPos() {
		return water_pos;
	}
	
	public void setWaterPos(Point p) {
		water_pos = p;
	}

	public void setTimeLeft(int i) {
		timeleft = i;		
	}

	/**
	 * Triggert Event, wenn waterstarted = true
	 * @param b Gestartet?
	 */
	public void setWaterStarted(boolean b) {
		water_started = b;
		if(b)
		{
			LogicController.triggerModelEvent(new WaterStart(this));
		}
	}
	
	public boolean getWaterStarted(){
		return water_started;
	}
	
	/**
	 * Triggert Event, wenn waterout = true;
	 * @param b "Ausgelaufen?"
	 */
	public void setWaterOut(boolean b) {
		water_out = b;
		if(b)
		{
			LogicController.triggerModelEvent(new WaterOut(this));
		}
	}
	
	public boolean getWaterOut(){
		return water_out;
	}
	
	/**
	 * Triggert Event, wenn waterwin = true;
	 * @param b Gewonnen?
	 */
	public void setWaterWin(boolean b) {
		water_win = b;
		if(b)
		{
			LogicController.triggerModelEvent(new WaterWin(this));
		}
	}
	
	public boolean getWaterWin(){
		return water_win;
	}

	public void setTimerIntervall(int timerintervall) {
		this.timerperiod = timerintervall;
		this.timer.setDelay(timerperiod); 
		
	}

	public void setRestarts(int restarts) {
		this.restarts = restarts;
	}

	public int getRestarts() {
		return restarts;
	}
	
	public int getTimeleft_start() {
		return timeleft_start;
	}

	public void setTimeleft_start(int timeleftStart) {
		timeleft_start = timeleftStart;
	}

	public int getTimeleft_end() {
		return timeleft_end;
	}

	public void setTimeleft_end(int timeleftEnd) {
		timeleft_end = timeleftEnd;
	}

	public int getLevelnumber() {
		return levelnumber;
	}

	public void setLevelnumber(int levelnumber) {
		this.levelnumber = levelnumber;
	}

	public int getRotatesteps() {
		return rotatesteps;
	}

	public void setRotatesteps(int rotatesteps) {
		this.rotatesteps = rotatesteps;
	}
}
