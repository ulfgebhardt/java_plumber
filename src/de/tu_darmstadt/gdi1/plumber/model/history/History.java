package de.tu_darmstadt.gdi1.plumber.model.history;

import java.util.Vector;

import de.tu_darmstadt.gdi1.plumber.model.history.events.HistoryEvent_interface;


/**
 * History, speichert in einem Vektor sämmtliche Schritte, die der Spieler im
 * Spiel gemacht hat. Somit kann man Schritte zurück gehen und auch wiederholen
 * Sofern keine neuen Schritte nach dem zurück gehen gemacht wurden.
 * 
 */
public class History {

	/**
	 * Vector von History-Events
	 */
	private Vector<HistoryEvent_interface> History = new Vector<HistoryEvent_interface>();

	/**
	 * Zeiger auf die Aktuelle Position in der History 0, wenn kein redo möglich
	 * -1, 1x redo möglich
	 */
	private int HistoryOffset = 0;

	/**
	 * Schritte Rückgängig machen
	 */
	public void UnDo() {
		int i = History.size() - 1 + HistoryOffset;
		if (i >= 0 && i < History.size()) {
			HistoryOffset -= 1;

			History.get(i).UnDo();
		}
	}

	/**
	 * Schritte wiederholen, nachdem sie Rückgängig gemacht wurden. Nur möglich,
	 * wenn kein neuer Schritt von selbst gemacht wurde nach einem UnDo.
	 */
	public void ReDo() {
		int i = History.size() - 1 + HistoryOffset + 1;
		if (i >= 0 && i < History.size()) {
			HistoryOffset += 1;

			History.get(i).Do();

		}
	}

	/**
	 * Füge einen Rotierschritt(Pos und was für ein Element wie stand) dem
	 * Vektor hinzu.
	 * 
	 * @param e
	 *            RotierEvent
	 */
	public void Add(HistoryEvent_interface e) {
		if (HistoryOffset < 0 && History.size() + HistoryOffset >= 0) {
			for (int i = HistoryOffset; i < 0; i++) {
				History.remove(History.size() - 1); // remove last

				HistoryOffset += 1;
			}
		}

		History.add(e);
	}

	/**
	 * History leeren.
	 */
	public void clear() {
		History.clear();

		HistoryOffset = 0;
	}
}
