package de.tu_darmstadt.gdi1.plumber.model.levelelements;

import java.awt.Point;

import de.tu_darmstadt.gdi1.plumber.controller.logic.LogicController;
import de.tu_darmstadt.gdi1.plumber.model.events.levelelement.Rotate;
//UNUSED
//import java.util.Vector;
//import plumber.model.events.levelelement.LevelElementEvent_abstract;
//import plumber.model.events.levelelement.ReDo;
//import plumber.model.events.levelelement.Water;


/**
 * Element Datenstrucktur
 * 
 * ACHTUNG: Rotate-Funktion ist eigentlich aufgabe des Controllers
 */
public abstract class Element_abstract{
	
	/**
	 * Definiert die 4 verschiedenen Verbindungsarten als Konstante für getCon()
	 */
	public final static int CON_TOP = 0;
	public final static int CON_RIGHT = 1;
	public final static int CON_BOTTOM = 2;
	public final static int CON_LEFT = 3;
	
	/**
	 * Verbindungen zu anderen Elementen: Oben,Rechts,Unten,Links
	 */
	protected boolean[] Connections = {false,false,false,false};//Connection to top[0],right[1],bottom[2],left[3]
	//UNUSED
	//protected Vector<LevelElementEvent_abstract> History = new Vector<LevelElementEvent_abstract>(); //Every Entry is a rotate-right/left true f�r right false f�r left
	//protected int HistoryOffset = 0; //negativ if some steps were undone
	
	/**
	 * Ist Wasser im Feld?
	 */
	protected boolean watered = false;
	
	/**
	 * DEPRECATED
	 * position of the field - to trigger with the events
	 */
	protected Point pos;

	/**
	 * Erstellt ein Element und rotiert es entsprechend
	 * 
	 * @param rotateright How often should this item be rotated in right-direction when created?
	 * @param pos Position im Level
	 * @param connections verbindungen (sollten von erbenden Klassen vergeschrieben werden)
	 */
	public Element_abstract(int rotateright, Point pos, boolean[] connections)
	{
		this.Connections = connections;
		
		for(int i=0; i<rotateright; i++)
		{
			rotate(true);
		}
		
		this.pos = pos;
	}
	
	/**
	 * Gibt zurück ob die Verbindung nach Oben/Unten/Rechts/Links besteht
	 * 
	 * @param con Siehe Konstanten oben
	 * @return true falls die Verbindung existiert
	 */
	public boolean getCon(int con)
	{
		if(con < 4 && con >= 0)
		{
			return Connections[con];
		}
		
		return false;
	}
	
	/**
	 * Rotiert das Element nach Rechts,
	 * falls es rotierbar ist
	 * 
	 * @return konnte rotiert werden?
	 */
	public boolean rotateRight(){
		
		if(isRotatable())
		{
			rotate(true);
			LogicController.triggerModelEvent(new Rotate(pos,this,true));
			return true;
		}
		
		return false;
	}
	
	/**
	 * Rotiert das Element nach Links,
	 * falls es rotierbar ist
	 * 
	 * @return konnte rotiert werden?
	 */
	public boolean rotateLeft(){
		if(isRotatable())
		{	
			rotate(false);
			LogicController.triggerModelEvent(new Rotate(pos,this,false));
			return true;
		}
		
		return false;
	}
	
	/**
	 * Rotiert Element nach rechts/links
	 * 
	 * Überprüft NICHT ob das Element rotierbar ist!
	 * 
	 * @param rotateright -> falls true rotiere rechts, sonst links
	 */
	private void rotate(boolean rotateright) //Adjust Connections in this function
	{
		if(rotateright)
		{
			//Speichere letztes Element
			boolean b = Connections[3];
			// Shift all elements right by one
			System.arraycopy(Connections, 0, Connections, 1, Connections.length-1);
			//Schreibe letztes Element als erstes
			Connections[0] = b;
		} else
		{	
			//Speichere erstes Element
			boolean b = Connections[0];
			// Shift all elements left by one
			System.arraycopy(Connections, 1, Connections, 0, Connections.length-1);
			//Schreibe erstes Element als letztes
			Connections[3] = b;
		}
	}
	
	/**
	 * Gibt true zurück, wenn man das Element drehen kann.
	 * 
	 * Muss implementiert werden - abstrakt
	 */
	public abstract boolean isRotatable();
	
	////////////////////////////////////////////////
	/**
	 * GETTER AND SETTER BELOW
	 */
	////////////////////////////////////////////////
	
	public boolean isWatered()
	{
		return watered;
	}
	
	public void setWatered(boolean w)
	{
		watered = w;
	}
	
	public Point getPos() {
		return pos;
	}
	
	//////////////////////
	//UNUSED METHODS BELOW
	//////////////////////
	
	//UNUSED
	//it is possible to undo a step if there are still steps to be undone
	/*public boolean isUnDoPossible()
	{
		if(History.size() + HistoryOffset >= 0)
		{
			return true;
		}
	
		return false;
	}*/
	
	//UNUSED
	//it is possible to redo a step if steps have been done and at least one step has been undone
	/*public boolean isReDoPossible()
	{
		if(	History.size() > 0 &&
			HistoryOffset < 0)
		{
			return true;
		}
		
		return false;
	}*/
	
	
	//UNUSED
	/*public void LogEvent(LevelElementEvent_abstract e){
		int i = History.size() -1 + HistoryOffset;
		if(i >= 0)
		{
			for(int k = History.size();k>i;k--)
			{ //R�ckw�rts weil remove den Index verschiebt
				//History.remove(k);
			}
		}
		
		History.add(e);
	}*/
	
	//UNUSED
	/*public void TriggerEvent(LevelElementEvent_abstract e)
	{
		TriggerEvent(e,true);
	}*/
	
	//UNUSED
	/*public void TriggerEvent(LevelElementEvent_abstract e, boolean dolog)
	{
		//Sende Event an alle Listeners
		LogicController.triggerModelEvent(e);
		
		//log
		if(dolog)
		{
			LogEvent(e);
		}
	}*/
	
	//UNUSED
	//undo the last step, meaning: if field was rotated, rotate back and set water back
	/*public boolean UnDo(){
		int i = History.size() -1 + HistoryOffset -1;
		if(i >= 0 && i < History.size())
		{
			//rotate(!History.get(i)); 
			HistoryOffset -= 1;
			
			if(History.get(i) instanceof Rotate)
			{
				rotate(!((Rotate)History.get(i)).rotatedirection); //rotiere in die entgegengesetzte richtung
				return true;
			}
			
			if(History.get(i) instanceof Water)
			{
				setWatered(!History.get(i).element.isWatered()); //w�ssere in die entgegengesetzte richtung
				return true;
			}
		}
		
		return false;
	}*/
	
	//UNUSED
	//redo the last step, meaning: if field was rotated, rotate back 
	//                             and set water in the opposite direction
	/*public boolean ReDo(){
		int i = History.size() -1 + HistoryOffset +1;
		if(i >= 0 && i < History.size())
		{
			HistoryOffset += 1;
			
			TriggerEvent(new ReDo(pos,this),false);
			
			if(History.get(i) instanceof Rotate)
			{
				rotate(((Rotate)History.get(i)).rotatedirection); //rotiere in die entgegengesetzte richtung
				return true;
			}
			
			if(History.get(i) instanceof Water)
			{
				setWatered(History.get(i).element.isWatered()); //w�ssere in die entgegengesetzte richtung
				return true;
			}
		}
		
		return false;
	}*/
}
