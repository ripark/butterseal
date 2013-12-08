package edu.smcm.gamedev.butterseal;

import java.util.ArrayList;

import com.badlogic.gdx.assets.AssetManager;


/**
 * Maintains the state of the game.
 *
 * @author Sean
 *
 */
public class BSGameState {
    static AssetManager ASSETS;
    BSPower selectedPower;
    BSDirection facing;
    BSPlayer player;
    BSWorld world;
    BSMap currentMap, nextMap;
    BSTile currentTile;
    long lastMapMove;
    boolean isMoving;
    boolean isSelectingPower;
    boolean isUsingPower;
    boolean hasbeentouching = false;

    /**
     * The save slot.  The session manager will have arbitrarily many of these available.
     */
    public final int slot;
    public boolean isWTF;
    BSAsset music;
    public ArrayList<BSPower> available_powers;
    public boolean justchangedmaps;

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
            world.addRoute(BSMap.HOUSE, BSMap.ICE_CAVE_ENTRY, null);
            world.addRoute(BSMap.ICE_CAVE_ENTRY, BSMap.ICE_CAVE, null);
            world.addRoute(BSMap.ICE_CAVE_EXIT, BSMap.MAZE, null);
            currentMap = BSMap.HOUSE;
            music = BSAsset.FIRST_MUSIC;
        }
    }

    public boolean load(int slot) {
        return false;
    }

    /**
     * Saves this game to the slot it was opened with.
     */
    public void save() {
        if(ButterSeal.DEBUG > 0) {
            System.out.println("GameState saving game.");
        }
    }

    public void begin() {

    }

    public void setMusic(BSAsset music) {
        if(music == null && this.music != null) {
            this.music.stopMusic();
        } else if(this.music != music) {
            this.music.stopMusic();
            this.music = music;
            this.music.playMusic();
        }
    }
}

// Local Variables:
// indent-tabs-mode: nil
// End:
