package de.tu_darmstadt.gdi1.plumber.controller.logic;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Vector;

import de.tu_darmstadt.gdi1.plumber.Plumber;
import de.tu_darmstadt.gdi1.plumber.controller.Controller;
import de.tu_darmstadt.gdi1.plumber.controller.logic.level.LevelController;
import de.tu_darmstadt.gdi1.plumber.controller.logic.levelset.LevelSetController;
import de.tu_darmstadt.gdi1.plumber.controller.options.Option;
import de.tu_darmstadt.gdi1.plumber.controller.options.Options;
import de.tu_darmstadt.gdi1.plumber.model.Model;
import de.tu_darmstadt.gdi1.plumber.model.events.EventListener;
import de.tu_darmstadt.gdi1.plumber.model.events.Event_interface;
import de.tu_darmstadt.gdi1.plumber.model.exceptions.PlumberError;
import de.tu_darmstadt.gdi1.plumber.model.levelsets.LevelSet;


public class LogicController {

	/**
	 * Reffrenz zu den Laufzeitdaten
	 */
	private Model modeldata;

	/**
	 * Diese Variable wird nicht benutzt und sollte auch nicht benutzt werden.
	 * Existiert aber der Vollständigkeit halber.
	 */
	@SuppressWarnings("unused")
	private Controller controller;

	/**
	 * LevelController, verwaltet aktuelles Level
	 */
	public LevelController level;

	/**
	 * LevelSetController, verwaltet levelsets und enthaltene level
	 */
	public LevelSetController levelset;

	/**
	 * Konstruktor
	 * 
	 * @param controller
	 *            Hauptcontroller
	 * @param modeldata
	 *            Laufzeitdaten
	 */
	public LogicController(Controller controller, Model modeldata) {
		this.controller = controller;
		this.modeldata = modeldata;

		levelset = new LevelSetController(this, modeldata);
		level = new LevelController(this, modeldata);

		Option o = Options.getOption(Plumber.OPTION_LEVELSETS_PATH);
		if (o != null) {
			loadLevelSetsPath(o.getValue());
		}
	}

	/**
	 * "Zuhöhrer", werden über Änderungen im Spielfeld und Fehler informiert.
	 * Müssen derivate von PlumberLogicUpdateInterface sein.
	 */
	private static Vector<EventListener> listeners = new Vector<EventListener>();

	/**
	 * Registriert einen neuen "Zuh�rer"
	 * 
	 * @param listener
	 * @return
	 */
	public static boolean registerListener(EventListener listener) {
		return listeners.add(listener);
	}

	/**
	 * Deregistriert einen "Zuh�rer"
	 * 
	 * @param listener
	 * @return
	 */
	public static boolean deregisterListener(EventListener listener) {
		return listeners.remove(listener);
	}

	/**
	 * L�st LogicEvent aus. Es wird an alle registrierten Zuh�rer weitergegeben
	 * 
	 * @param event
	 */
	public static void triggerModelEvent(Event_interface event) {
		for (int i = 0; i < listeners.size(); i++) {
			event.trigger(listeners.get(i));
		}
	}

	/**
	 * Startet das aktuell geladene level
	 */
	public void startGame() {
		level.start();
	}

	/**
	 * L�d Levelset nr position.
	 * 
	 * @param position
	 * @return
	 */
	public boolean loadLevelSet(int position) {
		if (modeldata.getLevelSetsPaths().size() > position && position >= 0) {
			try {
				modeldata.setCurrentLevelSet(new LevelSet());
				this.levelset.setLevelSetPath(modeldata.getLevelSetsPaths()
						.get(position));
			} catch (PlumberError e) {
				// Ignore wrong levelsets
				modeldata.getLevelSetsPaths().remove(position);
				loadLevelSet(position);
			}

			modeldata.setLevelSetNumber(position);
			return true;
		}

		return false;
	}

	/**
	 * L�d n�chstes LevelSet
	 * 
	 * @return true falls n�chstes LevelSet geladen werden konnte
	 */
	public boolean nextLevelSet() {
		return loadLevelSet(modeldata.getLevelSetNumber() + 1);
	}

	/**
	 * L�d letztes LevelSet
	 * 
	 * @return true falls letztes LevelSet geladen werden konnte
	 */
	public boolean previousLevelSet() {
		return loadLevelSet(modeldata.getLevelSetNumber() - 1);
	}

	/**
	 * lade LevelSets-Pfad.
	 * 
	 * @param levelsetspath
	 * @return
	 */
	public boolean loadLevelSetsPath(String levelsetspath) {
		if (modeldata == null) {
			// TODO ERROR
		}
		modeldata.getLevelSetsPaths().clear();
		modeldata.setLevelSetNumber(-1);
		modeldata.setLevelSetsPath(levelsetspath);

		// Filter: Nur Ordner
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return dir.isDirectory();
			}
		};

		File dir = new File(levelsetspath);

		String[] children = dir.list(filter);
		if (children == null || children.length == 0) {
			// Either dir does not exist or is not a directory
			// or no directories
			return false;
		} else {
			for (int i = 0; i < children.length; i++) {
				// Get name of directory
				try {
					modeldata.getLevelSetsPaths().add(
							dir.getCanonicalPath() + File.separator + children[i]);
				} catch (IOException e) {
					// TODO
				}
			}
		}

		loadLevelSet(0);

		return true;
	}

	/**
	 * Stoppt das aktuell geladene Level
	 */
	public void stopGame() {
		level.stop();
	}

	public void reloadLevelSet() {
		loadLevelSet(modeldata.getLevelSetNumber());

	}

}
