package edu.smcm.gamedev.butterseal;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum BSAsset {
    HOUSE            ( -1,  -1, "data/maps/house.tmx"),
    ICE_CAVE_ENTRY   ( -1,  -1, "data/maps/ice-cave-entry.tmx"),
    ICE_CAVE         ( -1,  -1, "data/maps/ice-cave.tmx"),
    TITLE            (800,1280, "data/interface/title.png"),
    PLAYER_WALK_UP   ( 64,  64, "data/spritesheets/player_walk_up.png"),
    PLAYER_WALK_DOWN ( 64,  64, "data/spritesheets/player_walk_down.png"),
    PLAYER_WALK_LEFT ( 64,  64, "data/spritesheets/player_walk_left.png"),
    PLAYER_WALK_RIGHT( 64,  64, "data/spritesheets/player_walk_right.png"),
    PLAYER_IDLE_STATE( 64,  64, "data/spritesheets/player_idle_state.png"),
    DIRECTIONAL_PAD  (512, 512, "data/interface/dpad.png"),
    MENU_BUTTON      (120, 123, "data/interface/menu-button.png"),
    MENU             (800,1280, "data/interface/menu.png"),
    POWERBAR_ACTION  (128, 448, "data/interface/abilities/action.png"),
    POWERBAR_FIRE    (128, 448, "data/interface/abilities/fire.png"),
    MENU_ABOUT1      (800,1280, "data/interface/about1.png"),
    MENU_ABOUT2      (800,1280, "data/interface/about2.png"),
    MENU_ABOUT3      (800,1280, "data/interface/about3.png"),
    MENU_ABOUT4      (800,1280, "data/interface/about4.png"),
    MENU_ABOUT5      (800,1280, "data/interface/about5.png"),
    MENU_ABOUT6      (800,1280, "data/interface/about6.png"),
    SPLASH_SCREEN    (800,1280, "data/interface/splash.png"),
    FIRST_MUSIC      ( -1,  -1, "data/music/part1.mp3"),
    SECOND_MUSIC     ( -1,  -1, "data/music/part2.mp3"),
    TITLE_MUSIC      ( -1,  -1, "data/music/title.mp3"),
    CREDITS_SCREEN   (800,1280, "data/interface/credits.png"),
    ICE_CAVE_EXIT    ( -1,  -1, "data/maps/ice-cave-exit.tmx"),
    PLAYER           ( -1,  -1, "data/spritesheets/player.txt"),
    MAZE             ( -1,  -1, "data/maps/maze.tmx");

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
     */
    BSAsset(int width, int height, String assetPath) {
        this.height = height;
        this.width = width;
        this.assetPath = assetPath;
    }

    /**
     * Gets a texture region for the asset if appropriate, null if not
     * @param assets
     * @return a texture region that covers the full length
     */
    TextureRegion getTextureRegion(AssetManager assets) {
        if(this.assetPath.endsWith(".png")) {
            return new TextureRegion(assets.get(this.assetPath, Texture.class), 0, 0, this.height, this.width);
        }
        return null;
    }

    void playMusic() {
        if(this.assetPath.endsWith(".mp3")) {
            BSGameState.ASSETS.get(this.assetPath, Music.class).play();
        }
    }

    void pauseMusic() {
        if(this.assetPath.endsWith(".mp3")) {
            BSGameState.ASSETS.get(this.assetPath, Music.class).pause();
        }
    }

    public void stopMusic() {
        if(this.assetPath.endsWith(".mp3")) {
            BSGameState.ASSETS.get(this.assetPath, Music.class).stop();
        }
    }
}
// Local Variables:
// indent-tabs-mode: nil
// End:
