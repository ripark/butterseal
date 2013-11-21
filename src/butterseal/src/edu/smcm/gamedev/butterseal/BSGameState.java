package edu.smcm.gamedev.butterseal;

/**
 * Maintains the state of the game.
 *
 * @author Sean
 *
 */
public class BSGameState {
    BSPower selectedPower;
    BSDirection facing;

    BSWorld world;
    BSMap currentMap;
    BSTile currentTile;
    boolean isMoving;
    boolean isSelectingPower;
    boolean isUsingPower;

    /**
     * The save slot.  The session manager will have arbitrarily many of these available.
     */
    public final int slot;

    /**
     * Opens this game on a particular game slot.
     * If the game does not exist on this slot, it will create a new one upon {@link #save()}.
     * If one exists, this constructor will load that game.
     * @param slot
     */
    public BSGameState(int slot) {
        /*
         * Set the slot and try to load it.
         * If loading returns false,
         * that means the slot doesn't exist, so make a new one.
         */
        this.slot = slot;
        if(!load(slot)) {
            this.world = new BSWorld();
            world.addRoute(BSMap.HOME, BSMap.ICE_CAVE, null);
            currentMap = BSMap.ICE_CAVE;
        }
    }

    public boolean load(int slot) {
        return false;
    }

    /**
     * Saves this game to the slot it was opened with.
     * @throws BSException throws an exception if a save is attempted while in motion; see {@link #isMoving}.
     */
    public void save() throws BSException {
        if(isMoving){
            throw new BSException("Cannot save while moving.");
        }
    }

    public void begin() {

    }
}

// Local Variables:
// indent-tabs-mode: nil
// End:
