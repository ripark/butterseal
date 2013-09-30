package edu.smcm.ButterSealGame;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Seal implements ApplicationListener
{
	private int WIDTH;
	private int HEIGHT;
	private int cWIDTH = 400;
	private int cHEIGHT = 240;
	private int mWIDTH;
	private int mHEIGHT;
	
	private Texture buzzImage;
	private Music DreamMusic;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Vector3 touchPos;
	private TileMapRenderer tileMapRenderer;
	
	@Override
	public void create() 
	{
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		Gdx.gl.glClearColor(0f,0,0.0f,1);
		
		buzzImage = new Texture(Gdx.files.internal("buzz.png"));
		DreamMusic = Gdx.audio.newMusic(Gdx.files.internal("Dream.mp3"));
		
		touchPos = new Vector3();
		
		TiledMap tiledMap = TiledLoader.createMap(Gdx.files.internal("maps/desertTest.tmx"));
		TileAtlas tileAtlas = new TileAtlas(tiledMap, Gdx.files.internal("maps"));
		tileMapRenderer = new TileMapRenderer(tiledMap, tileAtlas, 12, 12);
		
		mWIDTH = tiledMap.width * tiledMap.tileWidth;
		mHEIGHT = tiledMap.height * tiledMap.tileHeight;
		
		//DreamMusic.setLooping(true);
		//DreamMusic.play();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, cWIDTH*2, cHEIGHT*2);
		camera.position.set(cWIDTH, cHEIGHT, 0);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() 
	{
		handleInput();

		
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT );
		camera.update();
		tileMapRenderer.render(camera);
		
		
	}
	
	private void handleInput()
	{
		if(Gdx.input.isTouched()) 
		{
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			
			if(touchPos.y < (HEIGHT/2))
			{
				if(touchPos.x < touchPos.y)				//move left
				{
					if(camera.position.x > (cWIDTH))		//out of bounds check
						camera.translate(-3, 0, 0);
				}
				else if(touchPos.y > (WIDTH - touchPos.x)) 		//move right
				{
					if(camera.position.x < (mWIDTH-cWIDTH))		//out of bounds check
						camera.translate(3, 0, 0);
				}
				else							//move up
				{
					if(camera.position.y < (mHEIGHT-cHEIGHT))	//out of bounds check
						camera.translate(0, 3, 0);
				}
			}
			
			else
			{
				if(touchPos.x < (HEIGHT - touchPos.y))			//move left
				{
					if(camera.position.x > (cWIDTH))		//out of bounds check
						camera.translate(-3, 0, 0);
				}
				else if((HEIGHT - touchPos.y) < (WIDTH - touchPos.x))	//move down
				{
					if(camera.position.y > (cHEIGHT))		//out of bounds check
						camera.translate(0, -3, 0);					
				}
				else							//move right
				{
					if(camera.position.x < (mWIDTH-cWIDTH))		//out of bounds check
						camera.translate(3, 0, 0);
				}
			}
			
		}
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
