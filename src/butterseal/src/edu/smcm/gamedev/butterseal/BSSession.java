package edu.smcm.gamedev.butterseal;

/**
 * Handles a ButterSeal session overall.
 * This state is not tied to any particular game,
 *   but rather the current game is kept track of through {@link #state}.
 * @author Sean
 *
 */
public class BSSession {
	boolean isInGame;
	boolean isPaused;
	BSGameState state;
	
	public BSSession() {
		isInGame = false;
		isPaused = true;
	}
	
	public void start(int slot) {
		state = new BSGameState(slot);
		state.begin();
	}
}
