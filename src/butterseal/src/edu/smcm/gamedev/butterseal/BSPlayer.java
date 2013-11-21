package edu.smcm.gamedev.butterseal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Handles player state and movement on-screen.
 * 
 * @author Sean
 *
 */
public class BSPlayer {
    private static final int FRAME_ROWS = 2;
    private static final int FRAME_COLS = 2;
    
    private static class BSAnimation {
        Animation animation;
        Texture spritesheet;
        TextureRegion[] frames;
        float time;

        public BSAnimation(BSAsset asset) {
            this.spritesheet = assets.get(asset.assetPath);
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

    BSGameState state;
    static SpriteBatch batch;
    static AssetManager assets;
    BSAnimation walkUp, walkDown, walkRight, walkLeft, idle;
    Sprite currentFrame;
    /**
     * The pixels yet to move
     */
    Vector2 displacement;
    BSTile currentTile;
    private static final float SCALE = 3;
    /**
     * Frames to take per move
     */
    private static final float SPEED = 3;
    private static final float NORMSPEED = SPEED / BSMap.PIXELS_PER_TILE;

    public BSPlayer(BSGameState state,
                    float x, float y) {
        walkUp    = new BSAnimation(BSAsset.PLAYER_WALK_UP);
        walkDown  = new BSAnimation(BSAsset.PLAYER_WALK_DOWN);
        walkRight = new BSAnimation(BSAsset.PLAYER_WALK_RIGHT);
        walkLeft  = new BSAnimation(BSAsset.PLAYER_WALK_LEFT);
        idle      = new BSAnimation(BSAsset.PLAYER_IDLE_STATE);
        
        this.currentFrame = new Sprite(idle.frames[0]);
        this.currentFrame.setOrigin(0, 0);
        this.currentFrame.setScale(SCALE / BSMap.PIXELS_PER_TILE);
        this.displacement = new Vector2();
        this.state = state;
        this.state.facing = BSDirection.NORTH;
        this.state.selectedPower = BSPower.ACTION;
    }

    /**
     * Draws the player on the screen.
     */
    public void draw(OrthographicCamera cam) {
        if(!state.isMoving) {
            move(BSDirection.IDLE);
        }

        this.doTranslate(cam);
        
        // update moving state based on whether we have more to move
        this.state.isMoving = displacement.x != 0 ||
                              displacement.y != 0;

        this.currentFrame.draw(batch);
    }

    private void doTranslate(OrthographicCamera cam) {
        float ddx = 0, ddy = 0;

        if(displacement.x != 0) {
            ddx = Math.abs(displacement.x) < NORMSPEED ?
                    displacement.x : Math.signum(displacement.x) * NORMSPEED;
        }

        if (displacement.y != 0) {
            ddy = Math.abs(displacement.y) < NORMSPEED ?
                    displacement.y : Math.signum(displacement.y) * NORMSPEED;
        }

        displacement.sub(ddx, ddy);
        currentFrame.translate(ddx,ddy);
        float mystery = currentFrame.getScaleX() * BSMap.PIXELS_PER_TILE;
        System.out.println(mystery);
        cam.translate(ddx, ddy);
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
        this.state.isMoving = true;
        if(direction != state.facing) {
            System.out.println("Moving " + direction);
        }
        BSAnimation target;
        switch(direction) {
        case NORTH:
            target = walkUp;
            displacement.y += BSMap.PIXELS_PER_TILE * currentFrame.getScaleX() / SCALE;
            break;
        case SOUTH:
            target = walkDown;
            displacement.y -= BSMap.PIXELS_PER_TILE * currentFrame.getScaleX() / SCALE;
            break;
        case EAST:
            target = walkRight;
            displacement.x += BSMap.PIXELS_PER_TILE * currentFrame.getScaleX() / SCALE;
            break;
        case WEST:
            target = walkLeft;
            displacement.x -= BSMap.PIXELS_PER_TILE * currentFrame.getScaleX() / SCALE;
            break;
        case IDLE:
        default:
            target = idle;
            this.state.isMoving = false;
            break;
        }
        target.time += Gdx.graphics.getDeltaTime();
        this.currentFrame.setRegion(target.animation.getKeyFrame(target.time, true));
        this.state.facing = direction;
    }

    @SuppressWarnings("unused")
    private boolean canMove(BSDirection direction) {
        // If we are already moving,
        //   we should not be able to move again until we finish.
        if(state.isMoving) {
            return false;
        }

        // TODO do this
        state.currentMap.getTileProperties(this);

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
        default:
            break;
        }
        return adj;
    }

    public void setPower(int i) {
        int l = BSPower.values().length;
        int o = this.state.selectedPower.ordinal();
        int current = o + l;
        int next = (current + i) % l;
        this.setPower(BSPower.values()[next]);
    }

    public void setPower(BSPower power) {
        if(this.state.selectedPower != power) {
            this.state.isSelectingPower = true;
            System.out.println("Setting power to " + power);
            this.state.selectedPower = power;
        }
    }

    public void usePower() {
        if(!state.isUsingPower) {
            System.out.println("Using power " + this.state.selectedPower);
        }
        this.state.isSelectingPower = false;
        this.state.isUsingPower = false;
    }

    public Vector2 getV2() {
        return new Vector2(currentFrame.getX(), currentFrame.getY());
    }
}

// Local Variables:
// indent-tabs-mode: nil
// End:
