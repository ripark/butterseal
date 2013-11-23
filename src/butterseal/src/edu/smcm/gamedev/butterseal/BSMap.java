package edu.smcm.gamedev.butterseal;

import java.util.Map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public enum BSMap {
    ICE_CAVE_ENTRY(BSAsset.ICE_CAVE_ENTRY, "ice-cave-entry", new Runnable() {
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
	}),
	HOUSE(BSAsset.HOUSE, "house", new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub

        }

	});

    static final float PIXELS_PER_TILE = 64;

    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    BSAsset asset;
    String key;
    Runnable action;

    TiledMapTileLayer playerLevel;

    BSMap(BSAsset asset, String key, Runnable action) {
        this.map = new TmxMapLoader().load(asset.assetPath);
        this.renderer = new OrthogonalTiledMapRenderer(this.map, 1f/BSMap.PIXELS_PER_TILE);
        this.asset = asset;
        this.key = key;
        this.action = action;

        this.playerLevel =(TiledMapTileLayer)map.getLayers().get("player");
    }

    void draw(OrthographicCamera camera) {
        this.renderer.setView(camera);
        this.renderer.render();
    }

    BSTile getPlayer(String key) {
        BSTile ret = null;
        int h = playerLevel.getHeight();
        int w = playerLevel.getWidth();
        for(int row = 0; row < h; row++) {
            for(int col = 0; col < w; col++) {
                ret = new BSTile(col, row);
                Map<String, String> prop = ret.getProperties(this).get("player");
                if (prop.containsKey("player") && prop.get("player").equals(key)) {
                    return ret;
                }
            }
        }
        return ret;
    }

    public static BSMap getByKey(String key) {
        for (BSMap m : BSMap.values()) {
            if (m.key.equals(key)) {
                return m;
            }
        }
        return null;
    }
}

// Local Variables:
// indent-tabs-mode: nil
// End:
