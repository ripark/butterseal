package edu.smcm.gamedev.butterseal;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Handles player state and movement on-screen.
 * 
 * @author Sean
 *
 */
public class BSPlayer {
	BSGameState state;
	AssetManager assets;
	SpriteBatch batch;
	
	/**
	 * Draws the player on the screen.
	 */
	public void draw() {
		// TODO: idea: have a timer event that is done every 250ms...
		// that sets the current sprite from the batch 
	}
	
	/**
	 * Moves in the given direction.
	 * 
	 * If this is not the facing direction,
	 *   then the player will turn into that direction, updating
	 *   the {@link #state} and {@link #facingTile} appropriately.
	 * Otherwise, it's a very simple move.
	 * 
	 * @param direction the direction in which to move
	 */
	public void move(BSDirection direction) {
		switch(state.facing) {
		case NORTH:
			break;
		case SOUTH:
			break;
		case EAST:
			break;
		case WEST:
			break;
		}
	}
	
	/**
	 * 
	 * @return a tile describing the one we're facing
	 */
	public BSTile getFacingTile() {
		BSTile facing = new BSTile(state.currentTile);
		switch(state.facing) {
		case NORTH:
			facing.y += 1;
			break;
		case SOUTH:
			facing.y -= 1;
			break;
		case EAST:
			facing.x -= 1;
			break;
		case WEST:
			facing.x += 1;
			break;
		}
		return facing;
	}
}
