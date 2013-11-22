package edu.smcm.gamedev.butterseal;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
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

    SpriteBatch cambatch;
    SpriteBatch controls;
    AssetManager assets;
    OrthographicCamera camera;
    BitmapFont font;

    Map<Rectangle, BSGameStateActor> activeRegions;

    Sprite dpad;
    Sprite menubutton;

    public BSInterface(BSSession session) {
        font = new BitmapFont();
        assets = new AssetManager();
        cambatch = new SpriteBatch();
        controls = new SpriteBatch();
        camera = new OrthographicCamera();
        SetAssetLoaders();
        LoadAssets();

        BSPlayer.assets = assets;
        BSPlayer.batch = cambatch;
        BSPlayer.camera = camera;

        this.session = session;
        this.player = new BSPlayer(session.state);


        final int TILE_HEIGHT=20, TILE_WIDTH=30;
        camera.setToOrtho(false, Gdx.graphics.getWidth() / Gdx.graphics.getHeight() * TILE_WIDTH, TILE_HEIGHT);

        player.place("gamestart");

        activeRegions = new HashMap<Rectangle, BSGameStateActor>();
        LoadTestRegions();
    }

    private void LoadTestRegions() {
        activeRegions.put(vFlipRectangle(menubutton.getBoundingRectangle()), new BSGameStateActor() {
            @Override
            public void act(BSInterface gui) {
                if(!gui.session.isPaused) {
                    gui.session.isPaused = true;
                    System.out.println("Pausing game.");
                } else {
                    System.out.println("Game paused.");
                }
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
                toMove = null;
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

        if (input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    /**
     *
     * @param input
     * @param region
     * @return true if input is being touched within the given region, false otherwise
     */
    public boolean isTouchingInside(Input input, Rectangle region) {
        if (!input.isTouched()) {
            return false;
        }
        int x = input.getX();
        int y = input.getY();
        return region.x < x && x < region.x + region.width
            && region.y < y && y < region.y + region.height;
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
        if (session.isInGame) {
            session.state.currentMap.draw(camera);
            cambatch.begin();
            player.draw();
            cambatch.end();
            controls.begin();
            MakePowerBar();
            MakePowerSelector();
            MakeDirectionalPad();
            MakePauseButton();
            if (session.isPaused) {
                MakePauseScreen();
            }
            controls.end();
        } else {
            controls.begin();
            MakeTitleScreen();
            controls.end();
        }
        DrawActiveRegions();

        if(DEBUG_MODE) {
            controls.begin();
            font.draw(controls,
                    String.format("FPS: %d", Gdx.graphics.getFramesPerSecond()),
                    1, Gdx.graphics.getHeight()-1);
            controls.end();
        }
        camera.update();
        cambatch.setProjectionMatrix(camera.combined);
    }

    private void DrawActiveRegions() {
        ShapeRenderer rend = new ShapeRenderer();
        int h = Gdx.graphics.getHeight();
        rend.begin(ShapeType.Line);
        for(Rectangle r : activeRegions.keySet()) {
            rend.setColor(Color.RED);
            rend.rect(r.x, Math.abs(r.y-h)-r.height, r.width, r.height);
        }
        rend.end();
    }

    private void MakePowerBar() {

    }
    private void MakePowerSelector() {

    }
    private void MakeDirectionalPad() {
        dpad.draw(controls);
    }

    private void MakePauseButton() {
        menubutton.draw(controls);
    }

    /**
     * Dims the screen and displays the pause menu
     */
    private void MakePauseScreen() {
    }
    private void MakeTitleScreen() {
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

        dpad = new Sprite(BSAsset.DIRECTIONAL_PAD.getTextureRegion(assets));
        dpad.setBounds(0, 0, 196, 196);
        menubutton = new Sprite(BSAsset.MENU_BUTTON.getTextureRegion(assets));
        menubutton.setPosition(Gdx.graphics.getWidth()  - menubutton.getWidth(),
                               Gdx.graphics.getHeight() - menubutton.getHeight());
    }
    public void dispose() {
        cambatch.dispose();
        controls.dispose();
        assets.dispose();
    }

    /**
     * Flips a rectangle so that it uses the bottom-left as its origin (instead of top-left)
     * @param rect
     * @return
     */
    private static Rectangle vFlipRectangle(Rectangle rect) {
        Rectangle r = new Rectangle(rect);
        r.y = Math.abs(Gdx.graphics.getHeight()-r.y) - r.height;
        return r;
    }

    private static Rectangle vFlipRectangle(float x, float y, float width, float height) {
        return vFlipRectangle(new Rectangle(x, y, width, height));
    }
}

// Local Variables:
// indent-tabs-mode: nil
// End:
