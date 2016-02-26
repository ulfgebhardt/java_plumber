package de.tu_darmstadt.gdi1.plumber.view.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;

import de.tu_darmstadt.gdi1.plumber.controller.Controller;


/**
 * Fenster f�r Spielanleitung
 *
 */
public class DirectionsWindow extends Translatable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8507153576958050668L;
	
	/**
	 * Controller-Refferenz
	 */
	@SuppressWarnings("unused")
	private Controller controller;

	/** generiert ein Fenster mit der Spielanleitung
	 * @param controller
	 */
	public DirectionsWindow(final Controller controller)
	{
		super("Plumber",controller);	
		this.controller = controller;
		addWindowListener(new WindowAdapter(){
									@Override
									public void windowClosing(WindowEvent arg0) { //wenn Fenster geschlossen wird
										controller.view.showMainMenuWindow(); //MainMenuWindow anzeigen
									}
											
								});
		
		//translatable lable 
		JLabel controlText = controller.view.getTranslator().getGenerator().generateJLabel("control");
		add(controlText, BorderLayout.PAGE_START); //fügt controll Text hinzu
		
		setLayout(new GridLayout(3,1)); // GridLayout mit 3 Texten 
		//translatable lable 
		JLabel shortcutsText = controller.view.getTranslator().getGenerator().generateJLabel("shortcuts");
		add(shortcutsText); //fügt shortcut Text hinzu
		
		//translatable lable 
		JLabel aimText = controller.view.getTranslator().getGenerator().generateJLabel("aim");
		add(aimText); //fügt goal Text hinzu
		
		//translatable lable 
		JLabel rulesText = controller.view.getTranslator().getGenerator().generateJLabel("rules");
		add(rulesText); //fügt rule Text hinzu
		
								
		//Frame Size
		Dimension frameSize = new Dimension (600, 500);
		setSize(frameSize);
		
		//Frame Position  -  middle of screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int top = (screenSize.height - frameSize.height) / 2 ;
		int left = (screenSize.width - frameSize.width) / 2 ;
		setLocation(left , top);
	}
		
	/**
	 * Zeigt Fenster
	 */
	public void showWindow() {
		setVisible(true);
	}

	/**
	 * Versteckt Fenster
	 */
	public void hideWindow() {
		setVisible(false);
	}
}
