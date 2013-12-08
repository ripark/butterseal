package edu.smcm.gamedev.butterseal;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

public class ButterSeal implements ApplicationListener {
    public static       boolean ANDROID_MODE = true;
    public static final boolean PLAY_MUSIC   = true;
    public static       int     DEBUG        = 5;
    BSSession session;
    BSInterface gui;

    @Override
    public void create() {
        session = new BSSession();
        session.start(0);

        gui = new BSInterface(session);
    }


    @Override
    public void dispose() {
        gui.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        if(!gui.player.state.isMoving) {
            gui.poll(Gdx.input);
        }
        gui.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        if(ButterSeal.ANDROID_MODE) {
            gui.dispose();
            System.exit(0);
        }
    }

    @Override
    public void resume() {
    }
}

// Local Variables:
// indent-tabs-mode: nil
// End:
