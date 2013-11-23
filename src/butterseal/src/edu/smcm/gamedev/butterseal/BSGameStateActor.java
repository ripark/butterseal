package edu.smcm.gamedev.butterseal;

public interface BSGameStateActor {
    /**
     * Perform the appropriate actions given the game state.
     * @param player
     */
    public void act(BSGameState state);
}
