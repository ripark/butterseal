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

    BSGameState state;
    static SpriteBatch batch;
    static AssetManager assets;

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

    BSAnimation walkUp, walkDown, walkRight, walkLeft, idle;
    TextureRegion currentFrame;
    OrthographicCamera camera;

    public BSPlayer(float x, float y,
                    BSGameState state,
                    OrthographicCamera camera) {
        walkUp    = new BSAnimation(BSAsset.PLAYER_WALK_UP);
        walkDown  = new BSAnimation(BSAsset.PLAYER_WALK_DOWN);
        walkRight = new BSAnimation(BSAsset.PLAYER_WALK_RIGHT);
        walkLeft  = new BSAnimation(BSAsset.PLAYER_WALK_LEFT);
        idle      = new BSAnimation(BSAsset.PLAYER_IDLE_STATE);

        // TODO I got these from your old code --- what is the purpose?
        this.x = x;// - BSMap.PIXELS_PER_TILE ;
        this.y = y;// - BSMap.PIXELS_PER_TILE ;
        this.state = state;
        this.state.facing = BSDirection.NORTH;
        this.state.selectedPower = BSPower.ACTION;
        
        this.camera = camera;
        
        
        System.out.println(this);
    }

    float x, y;
    
    /**
     * The pixels yet to move
     */
    float dx, dy;

    BSTile currentTile;
    
    /**
     * take sixteen frames per move
     */
    private static final int SPEED = (int)(BSMap.PIXELS_PER_TILE / 16);

    /**
     * Draws the player on the screen.
     */
    public void draw() {
        if(!state.isMoving) {
            move(BSDirection.IDLE);
        }

        this.doTranslate();
        
        // update moving state based on whether we have more to move
        this.state.isMoving = dy != 0 || dx != 0;
        
        Sprite s = new Sprite(currentFrame);
        s.setPosition(-50, -50);
        s.setScale(2f);
        
        s.draw(batch);
        
        //batch.draw(this.currentFrame, x, y, 64, 64);
    }

    private Vector2 doTranslate() {
        // TODO this code can be simplified [made more expressive], I'm just brain-fried right now
        float ddx = 0, ddy = 0; // Deedee!  What are you doing in my laboratory!?
        if(dx > 0) {
            if(dx >= SPEED) {
                ddx = SPEED;
                dx -= SPEED;
                x += SPEED;
            } else {
                ddx = dx;
                x += dx;
                dx = 0;
            }
        } else if(dx < 0) {
            if(dx <= SPEED) {
                ddx = -SPEED;
                dx += SPEED;
                x -= SPEED;
            } else {
                ddx = dx;
                x += dx;
                dx = 0;
            }
        }
        if(dy > 0) {
            if(dy >= SPEED) {
                ddy = SPEED;
                dy -= SPEED;
                y += SPEED;
            } else {
                ddy = dy;
                y += dy;
                dy = 0;
            }
        } else if (dy < 0) {
            if(dy <= SPEED) {
                ddy = -SPEED;
                dy += SPEED;
                y -= SPEED;
            } else {
                ddy = dy;
                y += dy;
                dy = 0;
            }
        }
        camera.translate(Math.signum(ddx) / BSMap.PIXELS_PER_TILE * camera.zoom,
                         Math.signum(ddy) / BSMap.PIXELS_PER_TILE * camera.zoom);
        return new Vector2(ddx, ddy);
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
            dy += 50;//BSMap.PIXELS_PER_TILE/2;
            break;
        case SOUTH:
            target = walkDown;
            dy -= 50;//BSMap.PIXELS_PER_TILE/2;
            break;
        case EAST:
            target = walkRight;
            dx += 50;//BSMap.PIXELS_PER_TILE/2;
            break;
        case WEST:
            target = walkLeft;
            dx -= 50;//BSMap.PIXELS_PER_TILE/2;
            break;
        case IDLE:
        default:
            target = idle;
            this.state.isMoving = false;
            break;
        }
        target.time += Gdx.graphics.getDeltaTime();
        this.currentFrame = target.animation.getKeyFrame(target.time, true);
        this.state.facing = direction;
    }

    private boolean canMove(BSDirection direction) {
        // If we are already moving,
        //   we should not be able to move again until we finish.
        if(state.isMoving) {
            return false;
        }

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

    public void translate(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public void setPower(int i) {
        // TODO May be error-prone
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
        // TODO Auto-generated method stub
        if(!state.isUsingPower) {
            System.out.println("Using power " + this.state.selectedPower);
        }
        this.state.isSelectingPower = false;
        this.state.isUsingPower = false;
    }
}

// Local Variables:
// indent-tabs-mode: nil
// End:
