package edu.smcm.gamedev.butterseal;

public enum BSAsset {
    HOUSE            ( -1,  -1, "data/maps/house.tmx"),
    ICE_CAVE         ( -1,  -1, "data/maps/ice-cave.tmx"),
    ICE_CAVE_ENTRY   ( -1,  -1, "data/maps/ice-cave-entry.tmx"),
    TITLE            (718, 546, "data/images/flame.png"),
    PLAYER_WALK_UP   ( 64,  64, "data/spritesheets/player_walk_up.png"),
    PLAYER_WALK_DOWN ( 64,  64, "data/spritesheets/player_walk_down.png"),
    PLAYER_WALK_LEFT ( 64,  64, "data/spritesheets/player_walk_left.png"),
    PLAYER_WALK_RIGHT( 64,  64, "data/spritesheets/player_walk_right.png"),
    PLAYER_IDLE_STATE( 64,  64, "data/spritesheets/player_idle_state.png"),
    DIRECTIONAL_PAD  (256, 256, "data/interface/dpad1.png");

    /**
     * Intended height of this sprite in pixels 
     */
    final int height;
    
    /**
     * Intended width of this sprite in pixels
     */
    final int width;
    
    /**
     * file path of the sprite
     */
    final String assetPath;

    /**
     * Creates a new SpriteDimension
     * @param width the intended width of this sprite in pixels
     * @param height the intended height of this sprite in pixels
     * @param <T>
     */
    <T> BSAsset(int width, int height, String assetPath) {
        this.height = height;
        this.width = width;
        this.assetPath = assetPath;
    }
}
// Local Variables:
// indent-tabs-mode: nil
// End:
