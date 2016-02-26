package de.tu_darmstadt.gdi1.plumber.view.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.tu_darmstadt.gdi1.plumber.controller.Controller;
import de.tu_darmstadt.gdi1.plumber.controller.logic.LogicController;
import de.tu_darmstadt.gdi1.plumber.view.ViewLogicListener;

import translator.TranslatableGUIElement;
/**
 * Plumber Window, in dem das Panel(Spielfeld) eingefügt wird.
 */
public class Window extends de.tu_darmstadt.gdi1.plumber.view.tudframework.GameWindow 
{
	public final static String WINDOW_NAME = "Plumber - Game";

	private static final long serialVersionUID = -5796601737808769009L;

	public Controller controller;
	private ViewLogicListener logiclistener;
	public JLabel timeleft;
	public JLabel level;
	public JLabel levelset;
	private JDialog gameover;


	/**
	 * zur Optischen darstellung der Levelnummer im Set
	 * @param levelnumber Nummer des Levels
	 */
	public void levelnumber (int levelnumber){
		level.setText("    Level: " + String.valueOf(levelnumber)); //aktuelles Level anzeigen
	}
	
	/**
	 * zur Optischen darstellung des Levelsetnamens (Ordner)
	 * @param levelset name des Levelsets
	 */
	public void levelsetname(String levelset){
		this.levelset.setText(levelset); //levelset Name
	}
	
	/** zum optischen runterzählen der Zeit
	 * @param time
	 */
	public void settime (int time){

		timeleft.setText(String.valueOf(time/1000));
		
		if(time <= 9999) //weniger als 10sec
		{
			timeleft.setForeground(Color.RED); //rote Schrift
		}

	}

	/**
	 * Dialog:
	 * wird angezeigt, wenn das Level verloren wurde
	 */
	public void gameoverdialog (){ //bei game over 
		JLabel game = controller.view.getTranslator().getGenerator().generateJLabel("gameover");
		
		JOptionPane.showMessageDialog(gameover, game); //zeige gameoverText in eigenem Fenster an
		timeleft.setForeground(Color.BLACK);
	}
	
	/**
	 * Dialog
	 * Wird angezeigt, wenn das Level gewonnen wurde.
	 */
	public void gamewindialog (){ //bei gewinn des Levels
	JLabel game = controller.view.getTranslator().getGenerator().generateJLabel("mastered");

	JOptionPane.showMessageDialog(gameover, game); //zeige masteredText in eigenem Fenster an
	timeleft.setForeground(Color.BLACK); 
	}
	
	/**
	 * Dialog
	 * Wird angezeigt, wenn das Levelset zu ende ist.
	 */
	public void levelsetenddialog() {
		JLabel game = controller.view.getTranslator().getGenerator().generateJLabel("ended");
		
		JOptionPane.showMessageDialog(gameover, game);
		controller.view.showMainMenuWindow();
	}
	
	/**
	 * Wenn "W" gedrückt wird, startet der Wasserfluss sofort
	 */
	protected void keyWaterPressed(){	
		controller.logic.level.startWater();
	}
	
	/**
	 * Wenn "Q" gedrückt wird, wird das Programm vom Spielfeld aus beendet.
	 */
	protected void keyQuitPressed(){	
		controller.exit();
	}
	
	/**
	 * Wenn "T" gedrückt wird, dann wird das Theme im Spiel geändert. 
	 */
	protected void keyThemePressed(){
		if(!controller.view.nextTheme())
		{
			controller.view.loadTheme(0);
		}
		((Panel)gamePanel).OnLevelChange(controller.logic.level.getLevel());
	}
	
	/**
	 * Wenn "N" gedrückt wird, wird das aktuelle Level neu gestartet.
	 */
	protected void keyNewGamePressed() {
		controller.logic.level.restart();
	}
	
	/**
	 * Wenn "Backspace" gedrückt wird, dann wird der vorherige 
	 * Schritt Rückgänig gemacht.
	 */
	protected void keyUndoPressed() {	
		controller.logic.level.UnDo();
	}
	
	/**
	 * Wenn "Enter" gedrückt wird, dann werden die Schritte die
	 * Rückgängig gemacht wurden wiedergeholt
	 */
	protected void keyRedoPressed() {	
		controller.logic.level.ReDo();
	}
	
	/**
	 * Wenn "Leertaste" gedrückt, dann wird das selektierte
	 * Feld rotiert.
	 */
	protected void keySpacePressed() {	
		controller.logic.level.rotateSelected();
	}
	
	/**
	 * "Linke Pfeiltaste" das Feld links vom selektierten
	 * Feld wird nun selektiert. Wenn möglich.
	 */
	protected void keyLeftPressed() {
		controller.logic.level.moveSelectionLeft();
	}
	
	/**
	 * "Rechte Pfeiltaste" das Feld rechts vom selektierten
	 *  Feld wird nun selektiert. Wenn möglich.
	 */
	protected void keyRightPressed() {
		controller.logic.level.moveSelectionRight();
	}
	
	/**
	 * "Pfeiltaste Hoch" das Feld über dem selektierten
	 * Feld wird nun selektiert. Wenn möglich.
	 */
	protected void keyUpPressed() {
		controller.logic.level.moveSelectionUp();
	}
	
	/**
	 * "Pfeiltaste Runter" das Feld unter dem selektierten
	 * Feld wird nun selektiert. Wenn m�glich.
	 */
	protected void keyDownPressed() {
		controller.logic.level.moveSelectionDown();
	}
		
	protected void keyOtherPressed (KeyEvent key) {
		switch(key.getKeyCode())
		{
			case KeyEvent.VK_W: //"W" Taste definierne
			{
				keyWaterPressed();
				break;
			}
			
			case KeyEvent.VK_T://"T" Taste definieren
			{
				keyThemePressed();
				break;
			}
			
		}
	}

	/**
	 * Listener deregistrieren.
	 */
	public void finalize()
	{
		LogicController.deregisterListener(logiclistener);
	} 

	public Window(final Controller controller) {
		super(WINDOW_NAME, controller);		

		logiclistener = new ViewLogicListener(this);
		LogicController.registerListener(logiclistener);
		
		this.controller = controller;
		
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent arg0) {
				controller.view.showMainMenuWindow();
			}
					
		});
		
		//Frame Position
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int top = (screenSize.height - getSize().height) / 2 ;
		int left = (screenSize.width - getSize().width) / 2 ;
		setLocation(left , top);
		
		//setSize(400,400);
				
	}
	protected @Override de.tu_darmstadt.gdi1.plumber.view.tudframework.GamePanel createGamePanel(Controller controller)
	{   
	    // retrieve the GUI element builder
		TranslatableGUIElement guiBuilder =  controller.view.getTranslator().getGenerator();
		
		//set panel
		//Layout in Window
		setLayout(new BorderLayout());
		
		//Levelinfo -> North
		JPanel levelinfo = new JPanel();
		levelinfo.setLayout(new GridLayout(1,2));
		
		//Label f�r Level und Levelset
		level = new JLabel();
		levelset = new JLabel();
		
		//Labels hinzuf�gen
		levelinfo.add(levelset);
		levelinfo.add(level);
		add(levelinfo, BorderLayout.NORTH);
		
		//Label f�r Timeleft
		timeleft = new JLabel();
		
		//ButtonPanel -> South
		JPanel buttonpanel = new JPanel();
		buttonpanel.setLayout(new GridLayout(4,2));

		//create translatable labels and buttons
		JLabel timetext = guiBuilder.generateJLabel("timeLeft");
		AbstractButton restart = guiBuilder.generateJButton("restart");
		restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				keyNewGamePressed();
			}});
		
		//Undo Button - translatable
		AbstractButton undo = guiBuilder.generateJButton("undo");
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				keyUndoPressed();
			}});
		
		//Redo Button - translatable
		AbstractButton redo = guiBuilder.generateJButton("redo");
		redo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				keyRedoPressed();
			}});
		

		//Exit Button - translatable
		AbstractButton exit = guiBuilder.generateJButton("exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				keyQuitPressed();
			}});
		
		//Theme Button - translatable
		AbstractButton theme = guiBuilder.generateJButton("theme");
		theme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				keyThemePressed();
			}});
		
		//Water Button - translatable
		AbstractButton water = guiBuilder.generateJButton("Wasser");
		water.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				keyWaterPressed();
			}});
		
		//add buttons to panel
		buttonpanel.add(timetext);
		buttonpanel.add(timeleft);
		buttonpanel.add(undo);
		buttonpanel.add(redo);
		buttonpanel.add(water);
		buttonpanel.add(theme);
		buttonpanel.add(restart);
		buttonpanel.add(exit);
		add(buttonpanel, BorderLayout.SOUTH);
		
		//GamePanel
		gamePanel = new Panel(this);
		add(gamePanel, BorderLayout.CENTER);
		
		return gamePanel;
	}
	
	public void showWindow()
	{	
		//window visibility
		setVisible(true);
	}
}