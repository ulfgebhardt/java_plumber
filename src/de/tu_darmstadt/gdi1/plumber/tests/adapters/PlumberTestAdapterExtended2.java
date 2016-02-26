package de.tu_darmstadt.gdi1.plumber.tests.adapters;

import de.tu_darmstadt.gdi1.plumber.view.tudframework.GameWindow;

/**
 * This is the test adapter for the second extended stage of completion.
 * Implement all method stubs in order for the tests to work.
 * <br><br>
 * <i>Note:</i> This test adapter inherits from the first extended test adapter
 * 
 * @see PlumberTestAdapterMinimal
 * @see PlumberTestAdapterExtended1
 * 
 * @author Jonas Marczona
 * @author Christian Merfels
 * @author Fabian Vogt
 */
public class PlumberTestAdapterExtended2 extends PlumberTestAdapterExtended1 {

	/**
	 * Undo the last action. The state of the game shall be the same as before
	 * the last action. Action is here defined as "rotating game elements".
	 * Do nothing if there is no action to undo.
	 */
	public void undo() {
		controller.logic.level.UnDo();
	}
	
	/**
	 * Redo the last action. The state of the game shall be the same as before
	 * the last "undo". 
	 * Do nothing if there is no action to redo.
	 */
	public void redo() {
		controller.logic.level.ReDo();
	}
	
	/**
	 * Like {@link GameWindow#keySpacePressed()}
	 */
	public void handleKeyPressedSpace() {
		controller.logic.level.rotateSelected();
	}
	
	/**
	 * Like {@link GameWindow#keyUndoPressed()}
	 */
	public void handleKeyPressedUndo() {
		// TODO: check
		controller.logic.level.UnDo();
	}
	
	/**
	 * Like {@link GameWindow#keyRedoPressed()}
	 */
	public void handleKeyPressedRedo() {
		// TODO: check
		controller.logic.level.ReDo();
	}
	
	/**
	 * Like {@link GameWindow#keyUpPressed()}
	 */
	public void handleKeyPressedUp() {
		controller.logic.level.moveSelectionUp();
	}
	
	/**
	 * Like {@link GameWindow#keyDownPressed()}
	 */
	public void handleKeyPressedDown() {
		controller.logic.level.moveSelectionDown();
	}
	
	/**
	 * Like {@link GameWindow#keyLeftPressed()}
	 */
	public void handleKeyPressedLeft() {
		controller.logic.level.moveSelectionLeft();
	}
	
	/**
	 * Like {@link GameWindow#keyRightPressed()}
	 */
	public void handleKeyPressedRight() {
		controller.logic.level.moveSelectionRight();
	}
}