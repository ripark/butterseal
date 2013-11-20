package edu.smcm.gamedev.butterseal;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ButterSeal implements ApplicationListener {
    BSSession session;
    BSInterface gui;
    
    Texture dpad;
    Sprite sdpad;
	
    @Override
    public void create() {
        session = new BSSession();
        session.start(0);
        
        gui = new BSInterface(session);
        
        
        dpad = gui.assets.get(BSAsset.DIRECTIONAL_PAD.assetPath);
        dpad.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        TextureRegion region = new TextureRegion(dpad, 0,0,256,256);
        
        sdpad = new Sprite(region);
        sdpad.setSize(0.9f, 0.9f*sdpad.getHeight()/sdpad.getWidth());
        sdpad.setOrigin(sdpad.getWidth()/2, sdpad.getHeight()/2);
        sdpad.setPosition(-sdpad.getWidth()/2, -sdpad.getHeight()/2);
        
        
    }


    @Override
    public void dispose() {
        gui.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        
        gui.poll(Gdx.input);
        gui.draw();
        
//        gui.batch.setProjectionMatrix(gui.camera.combined);
//        gui.batch.begin();
//        sdpad.draw(gui.batch);
//        gui.batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}

// Local Variables:
// indent-tabs-mode: nil
// End:
