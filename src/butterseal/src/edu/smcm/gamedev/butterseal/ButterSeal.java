package edu.smcm.gamedev.butterseal;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class ButterSeal implements ApplicationListener {
	private OrthographicCamera camera;
	private AssetManager assetManager;
	private BitmapFont font;
	private SpriteBatch batch;
	private TiledMap map;
	private TiledMapRenderer renderer;
	
	BSSession session;
	
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		session = new BSSession();
		session.start(0);
		
		camera = new OrthographicCamera();
		assetManager = new AssetManager();
		batch = new SpriteBatch();
		font = new BitmapFont();
		
		camera.setToOrtho(false, w/h * 10, 10);
		camera.update();
		
		SetAssetLoaderLoaders();
		LoadAssets();

		map = assetManager.get(BSAssets.HOUSE.getAssetPath());
		
		renderer = new OrthogonalTiledMapRenderer(map, 1f/64f);
	}
	
	/**
	 * Sets all the loaders needed for the {@link #assetManager}.
	 */
	private void SetAssetLoaderLoaders() {
		assetManager.setLoader(TiledMap.class,
				new TmxMapLoader(
						new InternalFileHandleResolver()));
	}
	
	/**
	 * Loads all game assets
	 */
	private void LoadAssets() {
		assetManager.load(BSAssets.ICE_CAVE.getAssetPath(), TiledMap.class);
		assetManager.load(BSAssets.HOUSE.getAssetPath(), TiledMap.class);
		assetManager.finishLoading();
	}

	@Override
	public void dispose() {
		batch.dispose();
		assetManager.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		renderer.setView(camera);
		renderer.render();
		batch.begin();
		font.draw(batch,
				String.format("FPS: %d", Gdx.graphics.getFramesPerSecond()),
				20, Gdx.graphics.getHeight()-20);
		batch.end();
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
