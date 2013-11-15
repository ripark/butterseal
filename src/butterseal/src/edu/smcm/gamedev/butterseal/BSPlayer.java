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
    private static final int FRAME_ROWS = 2;
    private static final int FRAME_COLS = 2;

    BSGameState state;
    AssetManager assets;
    SpriteBatch batch;

    private static class BSAnimation {
        Animation animation;
        Texture spritesheet;
        TextureRegion frames;
        float time;

        public BSAnimation(String spritesheet) {
            this.spritesheet = new Texture(spritesheet);
            this.setAnimations();
        }

        public void setAnimations() {
            TextureRegion[][] tmp =
                TextureRegion.split(this.spritesheet,
                                    this.spritesheet.getWidth() / FRAME_COLS,
                                    this.spritesheet.getHeight() / FRAME_ROWS);
            this.frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
            int index = 0;
            for (int i = 0; i < FRAME_ROWS; i++) {
                for (int j = 0; j < FRAME_COLS; j++) {
                    this.frames[index] = tmp[i][j];
                    index += 1;
                }
            }
            this.animation = new Animation(0.25f, frames);
            this.time = 0f;
        }
    }

    BSAnimation walkUp, walkDown, walkRight, walkLeft, idle;
    TextureRegion currentFrame;

    public BSPlayer(float x, float y) {
        walkUp    = new BSAnimation(assetManager.load(AssetInfo.PLAYER_WALK_UP.getAssetPath()));
        walkDown  = new BSAnimation(assetManager.load(AssetInfo.PLAYER_WALK_DOWN.getAssetPath()));
        walkRight = new BSAnimation(assetManager.load(AssetInfo.PLAYER_WALK_RIGHT.getAssetPath()));
        walkLeft  = new BSAnimation(assetManager.load(AssetInfo.PLAYER_WALK_LEFT.getAssetPath()));
        idle      = new BSAnimation(assetManager.load(AssetInfo.PLAYER_IDLE.getAssetPath()));

        this.x = x - 16;
        this.y = y - 16;
    }

    float x, y;
        
    /**
     * Draws the player on the screen.
     */
    public void draw() {
        this.batch.draw(this.currentFrame, this.x, this.y);
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
        BSAnimation target;
        switch(state.facing) {
        case NORTH:
            target = walkUp;
            break;
        case SOUTH:
            target = walkDown;
            break;
        case EAST:
            target = walkRight
                break;
        case WEST:
            target = walkLeft;
            break;
        default:
            target = idle;
        }
        target.time += Gdx.graphics.getDeltaTime();
        this.currentFrame = target.animation.getKeyFrame(target.time, true);
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

    public void translate(float x, float y) {
        this.x += x;
        this.y += y;
    }
}

// Local Variables:
// indent-tabs-mode: nil
// End:
