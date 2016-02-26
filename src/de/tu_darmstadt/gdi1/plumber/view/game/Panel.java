package de.tu_darmstadt.gdi1.plumber.view.game;

import java.awt.*;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

import de.tu_darmstadt.gdi1.plumber.model.exceptions.PlumberError;
import de.tu_darmstadt.gdi1.plumber.model.level.Level;
import de.tu_darmstadt.gdi1.plumber.model.levelelements.Element_abstract;
import de.tu_darmstadt.gdi1.plumber.model.levelelements.Pipe_Bent;
import de.tu_darmstadt.gdi1.plumber.model.levelelements.Pipe_Straight;
import de.tu_darmstadt.gdi1.plumber.model.levelelements.Sink;
import de.tu_darmstadt.gdi1.plumber.model.levelelements.Well;
import de.tu_darmstadt.gdi1.plumber.view.tudframework.GamePanel;
import de.tu_darmstadt.gdi1.plumber.view.tudframework.GameWindow;
import de.tu_darmstadt.gdi1.plumber.view.tudframework.ParameterOutOfRangeException;


/**
 * Plumber GamePanel
 */
public class Panel extends GamePanel{

	private static final long serialVersionUID = -6952107268488601098L;

	private Window gamewindow;
	
	public Panel(GameWindow theParentWindow) {
		super(theParentWindow);
		
		this.gamewindow = (Window) theParentWindow;
		
		setVisible(true);
	}

	/**
	 * Bei Button benutzung rotiere Element rechtsrum.
	 */
	@Override 
	protected void entityClicked(int positionX, int positionY) {
		gamewindow.controller.logic.level.rotateRight(new Point(positionY,positionX));
	}

	@Override
	protected void panelResized() {
		//Auto-generated method stub -> not used
	}

	/**
	 * Bei Level�nderung (Rotieren, Roter Kasten wandert, Felder sind bew�ssert)
	 * wird der Frame geleert und das Level wird neu gemalt.
	 * @param level das aktuelle Level
	 */
	public void OnLevelChange(Level level)
	{
		clearFrame(); //macht Panel leer
		Vector<Vector<Element_abstract>> elements = level.getElements();
		
		Point selectedElement = gamewindow.controller.logic.level.getSelection(); //ausgew�hltes Element (Roter Kasten)
		
		Point levelsize; //Levelgr��e
		try {
			levelsize = level.getLevelSize();
		} catch (PlumberError exp) {
			//Auto-generated catch block
			return;
		}
		setLayout(new GridLayout(levelsize.x,levelsize.y)); //Feldgr��e als GridLayout setzen
		
		//geschachtelte For-Schleife um alle Levelelemente durch zu gehen und Bilder zu finden
		for(int i = 0; i < elements.size(); i++) 
		{
			for(int j= 0; j < elements.get(i).size(); j++)
			{
				Element_abstract e = elements.get(i).get(j);
				String ident = "";
				
				try {	
					if(e instanceof Well) //Quelle
					{
						ident += "WELL_"; //identifier f�r Bild hinzuf�gen/anbauen
					} else
					if(e instanceof Sink) //Senke
					{
						ident += "SINK_"; //identifier f�r Bild hinzuf�gen/anbauen
					} else
					if(e instanceof Pipe_Bent) //gebogen
					{
						ident += "BENT_"; //identifier f�r Bild hinzuf�gen/anbauen
					}else
					if(e instanceof Pipe_Straight) //gerade
					{
						ident += "STRAIGHT_"; //identifier f�r Bild hinzuf�gen/anbauen
					} else
					{
						return;
					}
					
					if(e.getCon(Element_abstract.CON_LEFT)) //Element hat Verbindung links
					{
						ident += "LEFT_"; //identifier f�r Bild hinzuf�gen/anbauen
					}
					if(e.getCon(Element_abstract.CON_RIGHT)) //Element hat Verbindung rechts
					{
						ident += "RIGHT_"; //identifier f�r Bild hinzuf�gen/anbauen
					}
					if(e.getCon(Element_abstract.CON_TOP)) //Element hat Verbindung oben
					{
						ident += "UP_"; //identifier f�r Bild hinzuf�gen/anbauen
					}
					if(e.getCon(Element_abstract.CON_BOTTOM)) //Element hat Verbindung unten
					{
						ident += "DOWN_"; //identifier f�r Bild hinzuf�gen/anbauen
					}
					
					if(e.isWatered()) //Felder mit Wassergef�llt
					{
						ident += "FILLED"; //identifier f�r Bild hinzuf�gen/anbauen
					} else
					{
						ident += "UNFILLED"; //identifier f�r Bild hinzuf�gen/anbauen
					}
						
					JButton b = placeEntity(ident); //Button an Stelle plazieren
					b.setBorder(new LineBorder(Color.LIGHT_GRAY,2)); //grauer Rand am Button
					//b.setMargin(new Insets(-50,-50,-50,-50));
					if(	selectedElement.x == i &&
						selectedElement.y == j)
					{
						b.setBorder(new LineBorder(Color.RED,2)); //wenn der Button selektiert ist Rand Rot
					}
				
				} catch (ParameterOutOfRangeException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		//Gr��e des Fensters anpassen
		gamewindow.pack();
		
		//Frame Position
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int top = (screenSize.height - gamewindow.getSize().height) / 2 ;
		int left = (screenSize.width - gamewindow.getSize().width) / 2 ;
		gamewindow.setLocation(left , top);
		
	}
	
	@Override
	protected void setGamePanelContents() {
		//Auto-generated method stub -> not used
	}

}
