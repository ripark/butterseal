package edu.smcm.gamedev.butterseal;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Contains program logic for the user interface.
 * 
 * This class handles such things as
 *   the pause menu,
 *   the directional pad,
 *   and power selection.
 * 
 * @author Sean
 *
 */
public class BSInterface {
	BSSession session;
	SpriteBatch batch;
	AssetManager assets;
	
	public BSInterface(BSSession session, SpriteBatch batch, AssetManager assets) {
		this.session = session;
		this.batch = batch;
		this.assets = assets;
	}
	
	/**
	 * Polls the given {@link Input} for valid player interaction
	 *   and handles it appropriately. 
	 * @param input
	 */
	public void poll(Input input) {
		
	}
	
	/**
	 * Draws the interface on the screen.
	 */
	public void draw() {
		/* If the game is in session, make the major interface elements.
		 * If the game is additionally paused, handle that as well.
		 * 
		 * If we are not in a game, then draw the title screen.
		 */
		
		if (session.isInGame) {
			MakePowerBar();
			MakePowerSelector();
			MakeDirectionalPad();
			MakePauseButton();

			if (session.isPaused) {
				MakePauseScreen();
			}
		} else {
			MakeTitleScreen();
		}
	}

	private void MakePowerBar() {
		
	}
	
	private void MakePowerSelector() {
		
	}
	
	private void MakeDirectionalPad() {
		
	}
	
	/**
	 * Dims the screen and displays the pause menu
	 */
	private void MakePauseButton() {
		
	}
	
	private void MakePauseScreen() {
		
	}
	
	private void MakeTitleScreen() {
		
	}
}
