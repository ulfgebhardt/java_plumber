package de.tu_darmstadt.gdi1.plumber.view.windows;
import javax.swing.*;

import de.tu_darmstadt.gdi1.plumber.controller.Controller;

import java.awt.*;
import java.awt.event.*;

/**
 * Fenster f�r die credits
 *
 */
public class AboutWindow extends Translatable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 647745719966414188L;
	
	/**
	 * Controller-Referenz
	 */
	@SuppressWarnings("unused")
	private Controller controller;
	
	/** generiert ein Fenster mit den credits
	 * @param controller
	 */
	public AboutWindow (final Controller controller){
		super("Plumber - About", controller);
		
		this.controller = controller;
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent arg0) { //wenn Fenster geschlossen wird
				controller.view.showMainMenuWindow(); //MainMenuWindow anzeigen
			}
					
		});

		setLayout(new BorderLayout());

		//translatable lable 
		JLabel creditText = controller.view.getTranslator().getGenerator().generateJLabel("credits");
		add(creditText, BorderLayout.PAGE_START); //fügt credits Text hinzu
		
		JLabel aboutText = controller.view.getTranslator().getGenerator().generateJLabel ("about");
		add(aboutText, BorderLayout.CENTER); //fügt about Text hinzu
		
		//translatable lable 
		JLabel nameText = controller.view.getTranslator().getGenerator().generateJLabel("names");
		add(nameText, BorderLayout.PAGE_END); //fügt Namen hinzu

		AbstractButton aboutToMenu = controller.view.getTranslator().getGenerator().generateJButton("backButton",null,false);
		aboutToMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				controller.view.showMainMenuWindow();
			}
		});
		add(aboutToMenu, BorderLayout.SOUTH);
		
		//Frame Size
		Dimension frameSize = new Dimension (250, 200);
		setSize(frameSize);
		
		//Frame Position - middle of screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int top = (screenSize.height - frameSize.height) / 2 ;
		int left = (screenSize.width - frameSize.width) / 2 ;
		setLocation(left , top);
	}

	/**
	 * Zeigt Fenster
	 */
	public void showWindow() {
		// Open the window
		setVisible(true);
		
	}

	/**
	 * Versteckt Fenster
	 */
	public void hideWindow() {
		setVisible(false);
		
	}

}


