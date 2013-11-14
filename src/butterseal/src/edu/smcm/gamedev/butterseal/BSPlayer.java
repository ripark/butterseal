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
	
	private boolean canMove(BSDirection direction) {
		BSTile target = this.getAdjacentTile(direction);
		state.currentMap.getTileProperties(target);
		return true;
	}

	/**
	 * 
	 * @return a tile describing the one we're facing
	 */
	public BSTile getFacingTile() {
		return this.getAdjacentTile(state.facing);
	}
	
	public BSTile getAdjacentTile(BSDirection direction) {
		BSTile adj = new BSTile(state.currentTile);
		switch(direction) {
		case NORTH:
			adj.y += 1;
			break;
		case SOUTH:
			adj.y -= 1;
			break;
		case EAST:
			adj.x -= 1;
			break;
		case WEST:
			adj.x += 1;
			break;
		}
		return adj;
	}
}
