package de.tu_darmstadt.gdi1.plumber.view.windows;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.tu_darmstadt.gdi1.plumber.controller.Controller;
import de.tu_darmstadt.gdi1.plumber.model.highscores.Highscore;


/**
 * Fenster für den Highscore
 *
 */
public class HighscoreWindow extends Translatable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1065886056990842168L;
	
	/**
	 * Controller-Refferenz
	 */
	@SuppressWarnings("unused")
	private Controller controller;

	/**
	 * Konstruktor für das HighscoreWindow,
	 * 
	 * zeigt Highscore für das aktuelle Levelset an
	 * @param controller
	 */
	public HighscoreWindow(final Controller controller){
		super("Highscore",controller);
		this.controller = controller;
		
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent arg0) {
				controller.view.showMainMenuWindow();
			}
					
		});
		
		
		//Layout des kompletten Fensters
		setLayout(new BorderLayout());
		
		
		//Layout für Highscore
		JPanel highscore = new JPanel();
		highscore.setLayout(new GridLayout(11,4));
		
		//frameName translatable lable
		JLabel highscoreText = controller.view.getTranslator().getGenerator().generateJLabel ("highscore");
		add(highscoreText, BorderLayout.NORTH);
		
		highscore.add(new JLabel("Name")); //muss nicht übersetzt werden
		//translatable lable
		JLabel timeText = controller.view.getTranslator().getGenerator().generateJLabel ("time");
		highscore.add(timeText);
		highscore.add(new JLabel("Level")); //muss nicht übersetzt werden
		//translatable lable
		JLabel pointsText = controller.view.getTranslator().getGenerator().generateJLabel ("points");
		highscore.add(pointsText);
		
		for (int i = 0 ; i < 10 ; i++){
			if (controller.logic.levelset.getHighscore().getAt(i) == null){ //für leere Highscoreliste
				highscore.add(new JLabel("-"));
				highscore.add(new JLabel("-"));
				highscore.add(new JLabel("-"));
				highscore.add(new JLabel("-"));
				
			}else //Highscoreeintrag machen
			{
				highscore.add(new JLabel(controller.logic.levelset.getHighscore().getAt(i).getPlayername()));
				highscore.add(new JLabel(String.valueOf(controller.logic.levelset.getHighscore().getAt(i).getTime()/1000)));
				highscore.add(new JLabel(String.valueOf(controller.logic.levelset.getHighscore().getAt(i).getReachedlevel())));
				highscore.add(new JLabel(String.valueOf(Highscore.calculatePoints(controller.logic.levelset.getHighscore().getAt(i)))));
			}
		}
		
		add(highscore); 
		
		//Frame Size
		Dimension frameSize = new Dimension (300, 500);
		setSize(frameSize);
		
		//Frame Position - middle of screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int top = (screenSize.height - frameSize.height) / 2 ;
		int left = (screenSize.width - frameSize.width) / 2 ;
		setLocation(left , top);
		
	}
	
	/**
	 * Versteckt Fenster
	 */
	public void showWindow() {
		setVisible(true);
	}
}