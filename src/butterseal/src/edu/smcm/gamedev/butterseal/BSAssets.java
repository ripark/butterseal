package edu.smcm.gamedev.butterseal;

import com.badlogic.gdx.Gdx;

public enum BSAssets {
    TITLE(718, 546, _asset("data/flame.png")), // flame
    HOUSE(-1, -1, _asset("data/maps/house.tmx")),
    ICE_CAVE(-1, -1, _asset("data/maps/ice-cave.tmx")),
    PLAYER_WALK_UP(-1, -1, _asset("")),
    PLAYER_WALK_DOWN(-1, -1, _asset("")),
    PLAYER_WALK_LEFT(-1, -1, _asset("")),
    PLAYER_WALK_RIGHT(-1, -1, _asset("")),
    PLAYER_IDLE(-1, -1, _asset(""));
	
    private static String _asset(String p) {
        return p;
        //return String.format("data/../../../assets/%s", p);
    }
	
    /**
     * Intended height of this sprite in pixels 
     */
    private final int height;
    /**
     * Intended width of this sprite in pixels
     */
    private final int width;
    /**
     * file path of the sprite
     */
    private final String assetPath;

    /**
     * Creates a new SpriteDimension
     * @param height the intended height of this sprite in pixels
     * @param width the intended width of this sprite in pixels
     */
    BSAssets(int width, int height, String assetPath) {
        this.height = height;
        this.width = width;
        this.assetPath = assetPath;
    }

    public String getAssetPath() {
        return assetPath;
    }
}
// Local Variables:
// indent-tabs-mode: nil
// End:
