package edu.smcm.gamedev.butterseal;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    BSSession session;
    SpriteBatch batch;
    AssetManager assets;
    Map<Rectangle, BSGameStateActor> activeRegions;
    BSPlayer player;
	
    public BSInterface(BSSession session, SpriteBatch batch, AssetManager assets) {
        this.session = session;
        this.batch = batch;
        this.assets = assets;
        this.activeRegions = new HashMap<Rectangle, BSGameStateActor>();
        this.player = new BSPlayer(0, 0, this.session.state, this.batch);
		
        // test active region
        activeRegions.put(new Rectangle().set(0, 0, 100, 100), new BSGameStateActor() {
                @Override
                public void act(BSPlayer player) {
                    // TODO Auto-generated method stub
                    System.out.println("test active region");
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
                activeRegions.get(r).act(player);
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
    }

    /**
     * 
     * @param input
     * @param region
     * @return true if input is being touched within the given region, false otherwise
     */
    public boolean isTouchingInside(Input input, Rectangle region) {
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
		
        if (session.isInGame) {
            session.state.currentMap.draw();
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
    }

    private void MakePowerBar() {
		
    }
	
    private void MakePowerSelector() {
		
    }
	
    private void MakeDirectionalPad() {
		
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
    }
}

// Local Variables:
// indent-tabs-mode: nil
// End:
