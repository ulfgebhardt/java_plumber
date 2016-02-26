package de.tu_darmstadt.gdi1.plumber.view.windows;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import de.tu_darmstadt.gdi1.plumber.controller.Controller;

import translator.TranslatableGUIElement; // for GUI elements

public class Translatable extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1874366379191280678L;
	
	/**
	 * Controller-Referenz
	 */
	@SuppressWarnings("unused")
	private Controller controller;
	
	/**
	 * builds a menu with language options
	 * when language is chosen all of the translatable objects are displayed in the chosen language
	 * 
	 * @param windowName window "name"
	 * @param controller
	 */
	public Translatable(String windowName, final Controller controller){
		
		// windowName is given to JFrame to create a window with the given title 
		super(windowName);
		this.controller = controller;
	
	        
		     // retrieve the GUI element builder
				TranslatableGUIElement guiBuilder = controller.view.getTranslator().getGenerator();

				// create a JMenuBar
				JMenuBar menuBar = new JMenuBar();
				// generate the JMenu for this
				JMenu menu = guiBuilder.generateJMenu("languageMenu");
				
				// add the menu to the menu bar and add this to the JFrame
				menuBar.add(menu);
				super.setJMenuBar(menuBar);
				
				// generate a menu item with parameters (key, useIconIfExists)
				//deutsch button
				JMenuItem deutschItem = guiBuilder.generateJMenuItem("deutschItem");
				
				//set the language to German
				deutschItem.addActionListener(new ActionListener() {
					                             public void actionPerformed(ActionEvent e) 
					                             {
					                            	 controller.view.getTranslator().setTranslatorLocale(Locale.GERMANY);
					                             }
			    });
				// add the item to the JMenu
				menu.add(deutschItem);
				
				// generate a menu item with parameters (key, useIconIfExists)
				//english button
				JMenuItem englishItem = guiBuilder.generateJMenuItem("englishItem");
				
				// set the language to English
				englishItem.addActionListener(new ActionListener() {
					                             public void actionPerformed(ActionEvent e) 
					                             {
					                            	 controller.view.getTranslator().setTranslatorLocale(Locale.US);
					                             }
				});
				// add the item to the JMenu
				menu.add(englishItem);
		}

}
