package edu.smcm.gamedev.butterseal;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;

/**
 * Contains program logic for the user interface.
 * 
 * This class handles such things as
 *   the pause menu,
 *   the directional pad,
 *   and power selection.
 * 
 * @author Sean
 *
 */
public class BSInterface {
    static final boolean DEBUG_MODE = true;
    
    BSSession session;
    BSPlayer player;
    
    SpriteBatch batch;
    AssetManager assets;
    OrthographicCamera camera;
    BitmapFont font;
    
    Map<Rectangle, BSGameStateActor> activeRegions;
    
    Texture dpad;
	
    public BSInterface(BSSession session) {
        font = new BitmapFont();
        assets = new AssetManager();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        SetAssetLoaders();
        LoadAssets();
        
        BSPlayer.assets = assets;
        BSPlayer.batch = batch;
        
        this.session = session;
        this.player = new BSPlayer(session.state, 0, 0);
        
        camera.setToOrtho(false, Gdx.graphics.getWidth() / Gdx.graphics.getHeight() * 10, 10);
        batch.setProjectionMatrix(camera.combined);
		
        activeRegions = new HashMap<Rectangle, BSGameStateActor>();
        LoadTestRegions();
    }

    private void LoadTestRegions() {
        activeRegions.put(new Rectangle().set(0, 0, 100, 100), new BSGameStateActor() {
            public void act(BSInterface gui) {
                System.out.println("test active region 1");
            }
        });
        activeRegions.put(new Rectangle().set(100, 100, 200, 200), new BSGameStateActor() {
            public void act(BSInterface gui) {
                System.out.println("test active region 2");
            }
        });
    }
	
    /**
     * Polls the given {@link Input} for valid player interaction
     *   and handles it appropriately.
     * @param input
     */
    public void poll(Input input) {
        pollKeyboard(input);

        for(Rectangle r : activeRegions.keySet()){
            if (isTouchingInside(input, r)){
                activeRegions.get(r).act(this);
            }
        }
    }
	
    /**
     * Poll the keyboard for input.
     * @param input an input stream to analyze
     */
    private void pollKeyboard(Input input) {
        if(!player.state.isMoving) {
            // poll for movement
            BSDirection toMove;
            if(input.isKeyPressed(Input.Keys.RIGHT)) {
                toMove = BSDirection.EAST;
            } else if(input.isKeyPressed(Input.Keys.UP)) {
                toMove = BSDirection.NORTH;
            } else if(input.isKeyPressed(Input.Keys.LEFT)) {
                toMove = BSDirection.WEST;
            } else if(input.isKeyPressed(Input.Keys.DOWN)) {
                toMove = BSDirection.SOUTH;
            } else {
                toMove = BSDirection.IDLE;
            }
            player.move(toMove);
        }

        if(!player.state.isSelectingPower) {
            // poll for power chooser
            if(input.isKeyPressed(Input.Keys.Z)) {
                player.setPower(-1);
            } else if (input.isKeyPressed(Input.Keys.C)) {
                player.setPower(1);
            }
        }
        if (input.isKeyPressed(Input.Keys.X)) {
            player.usePower();
        }
        
        if (input.isKeyPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();
    }

    /**
     * 
     * @param input
     * @param region
     * @return true if input is being touched within the given region, false otherwise
     */
    public boolean isTouchingInside(Input input, Rectangle region) {
        if (!input.isTouched())
            return false;
        int x = input.getX();
        int y = input.getY();
        return region.x < x && x < region.width
            && region.y < y && y < region.height;
    }
	
    /**
     * Draws the interface on the screen.
     */
    public void draw() {
        /* If the game is in session, make the major interface elements.
         * If the game is additionally paused, handle that as well.
         * 
         * If we are not in a game, then draw the title screen.
         */
		batch.begin();
        if (session.isInGame) {
            session.state.currentMap.draw(camera);
            player.draw(camera);
            MakePowerBar();
            MakePowerSelector();
            MakeDirectionalPad();
            MakePauseButton();
            if (session.isPaused) {
                MakePauseScreen();
            }
        } else {
            MakeTitleScreen();
        }
        
        if(DEBUG_MODE) {
            font.draw(batch,
                    String.format("FPS: %d", Gdx.graphics.getFramesPerSecond()),
                    1, Gdx.graphics.getHeight()-1);
        }
        batch.end();
    }

    private void MakePowerBar() {
		
    }
	
    private void MakePowerSelector() {
		
    }
	
    private void MakeDirectionalPad() {
		this.drawScaled(batch, this.dpad, 0.5, 0, 0, BSAsset.DIRECTIONAL_PAD);
    }
	
    /**
     * Draws a scaled version of the texture to the batch at the given coordinates.
     * Remember that it is anchored to the bottom-left.
     * @param batch
     * @param texture
     * @param scale
     * @param x
     * @param y
     * @param asset
     */
    private void drawScaled(SpriteBatch batch, Texture texture, double scale, int x, int y, BSAsset asset) {
        //batch.draw(texture, x, y,
        //        (int)(asset.width * scale),
        //        (int)(asset.height * scale),
        //        0, 0,
        //        asset.width,
        //        asset.height,
        //        false, false);
        Sprite s = new Sprite(texture);
        s.setPosition(x, y);
        
        s.draw(batch);
    }

    /**
     * Dims the screen and displays the pause menu
     */
    private void MakePauseButton() {
		
    }
	
    private void MakePauseScreen() {
		
    }
	
    private void MakeTitleScreen() {
        //batch.draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
        //this.drawScaled(batch, `, scale, x, y, asset)
    }
    
    /**
     * Sets all the loaders needed for the {@link #assetManager}.
     */
    private void SetAssetLoaders() {
        assets.setLoader(TiledMap.class,
                               new TmxMapLoader(
                                 new InternalFileHandleResolver()));
        
    }
    
    /**
     * Loads all game assets
     */
    private void LoadAssets() {
        for(BSAsset asset : BSAsset.values()) {
            if(asset.assetPath.endsWith(".png")) {
                assets.load(asset.assetPath, Texture.class);
            } else if (asset.assetPath.endsWith(".tmx")) {
                assets.load(asset.assetPath, TiledMap.class);
            } else {
                System.err.print("No loader found for " + asset.assetPath);
                System.exit(1);
            }
        }
        assets.finishLoading();
        
        dpad = assets.get(BSAsset.DIRECTIONAL_PAD.assetPath);
    }

    
    public void dispose() {
        // TODO Auto-generated method stub
        batch.dispose();
        assets.dispose();
    }
}

// Local Variables:
// indent-tabs-mode: nil
// End:
