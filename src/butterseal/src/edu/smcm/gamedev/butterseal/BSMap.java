package edu.smcm.gamedev.butterseal;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public enum BSMap {
    HOME(BSAsset.HOUSE, "house", new Runnable() {
            @Override
            public void run() {
                // game logic
            }
	}),
    ICE_CAVE_ENTRY(BSAsset.ICE_CAVE_ENTRY, null, new Runnable() {
            @Override
            public void run() {
                // game logic
            }
	}),
    ICE_CAVE(BSAsset.ICE_CAVE, "ice-cave", new Runnable() {
            @Override
            public void run() {
                // game logic
            }
	});

    static final float PIXELS_PER_TILE = 32;

    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    BSAsset asset;
    String key;
    Runnable action;

    BSMap(BSAsset asset, String key, Runnable action) {
        this.map = new TmxMapLoader().load(asset.assetPath);
        this.renderer = new OrthogonalTiledMapRenderer(this.map, 1f/BSMap.PIXELS_PER_TILE);
        this.asset = asset;
        this.key = key;
        this.action = action;
    }

    void draw(OrthographicCamera camera) {
        this.renderer.setView(camera);
        this.renderer.render();
    }
}

// Local Variables:
// indent-tabs-mode: nil
// End:
