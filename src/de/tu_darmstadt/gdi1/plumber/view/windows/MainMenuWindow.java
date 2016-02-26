package de.tu_darmstadt.gdi1.plumber.view.windows;

import java.awt.*;
import java.awt.event.*;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import de.tu_darmstadt.gdi1.plumber.controller.Controller;
import de.tu_darmstadt.gdi1.plumber.controller.options.Option;
import de.tu_darmstadt.gdi1.plumber.controller.options.Options;

import translator.TranslatableGUIElement;

/**
 * Hauptmenü
 */
public class MainMenuWindow extends Translatable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3575290148137411381L;
	@SuppressWarnings("unused")
	/**
	 * Controller-Refferenz
	 */
	private Controller controller;
	
	/**
	 * Playername-Eingabe-Feld
	 */
	private JTextField playername;
	
	/**
	 * Levelset-Text-Feld
	 */
	private JLabel lvlset;

	
	/**
	 * Constructs a new window with a given title
	 * Includes buttons which will be displayed in the selected language 
	 */
	public MainMenuWindow(final Controller controller) {
		super("Plumber",controller);	
        
	     // retrieve the GUI element builder
			TranslatableGUIElement guiBuilder = controller.view.getTranslator().getGenerator();
			
			
			this.controller = controller;
			//Main Menu -> 8 Buttons 
			setLayout(new GridLayout (7,1));
			
			addWindowListener(new WindowAdapter(){
				@Override
				public void windowClosing(WindowEvent arg0) {
					controller.exit();
				}
						
			});

			//PlayerName
			JPanel player = new JPanel();
			player.setLayout(new GridLayout());
			
			//Textfield and Button for PlayerName
			playername = new JTextField("Player");
			
			Option o = Options.getOption("playername");
			
			if(o != null)
			{
				playername.setText(o.getValue());
			}
			
			playername.getDocument().addDocumentListener(new DocumentListener() {
									public void changedUpdate(DocumentEvent e)
									{
										Options.setOption(new Option("playername", playername.getText()));
									}
									public void removeUpdate(DocumentEvent e){
										changedUpdate(e);
									}
									public void insertUpdate(DocumentEvent e){
										changedUpdate(e);
									}
			}); 
			/*playername.addFocusListener(new FocusAdapter() {
	            public void focusLost(FocusEvent evt) {
	                
	            }}
			);*/
			
			player.add(playername);
			
			add(player);
			
			//Levelset
			JPanel levelset = new JPanel();
			levelset.setLayout(new BorderLayout());
			JButton last = new JButton("<");
			last.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					if(controller.logic.previousLevelSet())
					{
						lvlset.setText(controller.logic.levelset.getLevelSet().getName());
					}
				}
				});
			lvlset = new JLabel(controller.logic.levelset.getLevelSet().getName());
			lvlset.setHorizontalAlignment(JLabel.CENTER);
			JButton next = new JButton(">");
			next.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					if(controller.logic.nextLevelSet())
					{
						lvlset.setText(controller.logic.levelset.getLevelSet().getName());
					}
				}
				});
			
			levelset.add(last, BorderLayout.LINE_START);
			levelset.add(lvlset, BorderLayout.CENTER);
			levelset.add(next, BorderLayout.LINE_END);
			
			
			add(levelset);
			//Game Start Button
			AbstractButton game_start= guiBuilder.generateJButton("startButton");
			game_start.addActionListener(new ActionListener(){
											public void actionPerformed(ActionEvent e)
											{
												controller.view.showGameWindow();
											}
								
				});
			add(game_start);

			
			//Highscore Button
			AbstractButton highscore_button = guiBuilder.generateJButton("highscoreButton");
			highscore_button.addActionListener(new ActionListener(){
												   public void actionPerformed(ActionEvent e)
												   {
													   	System.out.println("Here you can see the Highscore");
														controller.view.showHighscoreWindow();

												   	}
				});
			add(highscore_button);

			//Directions Button
			AbstractButton options_button = guiBuilder.generateJButton("directionsButton");
			options_button.addActionListener(new ActionListener(){
												public void actionPerformed(ActionEvent e)
												{
													controller.view.showDirectionsWindow();
												}
				});
			add(options_button);

			//About Button
			AbstractButton about_button = guiBuilder.generateJButton("aboutButton");
			about_button.addActionListener(new ActionListener(){
												public void actionPerformed(ActionEvent e)
												{
													controller.view.showAboutWindow();
												}
				});
			add(about_button);

			//Exit Button
			AbstractButton exit_button = guiBuilder.generateJButton("exit");
			exit_button.addActionListener(new ActionListener(){
											public void actionPerformed(ActionEvent e)
											{
												controller.exit();
											}
				});
			add(exit_button);
			
			//Window Size
			Dimension frameSize = new Dimension (250, 250);
			setSize(frameSize);
			
			//window Location
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