package edu.smcm.gamedev.butterseal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public enum BSMap {
    HOME(BSAssets.HOUSE, "house", new Runnable() {
            public void run() {
                // game logic
            }
	}),
    ICE_CAVE_ENTRY(BSAssets.ICE_CAVE_ENTRY, null, new Runnable() {
            public void run() {
                // game logic
            }
	}),
    ICE_CAVE(BSAssets.ICE_CAVE, "ice-cave", new Runnable() {
            public void run() {
                // game logic
            }
	});

    static final float PIXELS_PER_TILE = 32;
	
    BSGameState state;
    TiledMap map;
    //TileAtlas atlas;
    OrthogonalTiledMapRenderer renderer;

    BSMap(BSAssets asset, String key, Runnable action) {
        this.map = new TmxMapLoader().load(asset.getAssetPath());
        this.renderer = new OrthogonalTiledMapRenderer(this.map, 1/BSMap.PIXELS_PER_TILE);
    }

    void draw(OrthographicCamera camera) {
        this.renderer.setView(camera);
        this.renderer.render();
    }

    public List<Map<String, String>> getTileProperties(BSPlayer player) {
        List<Map<String, String>> ret = new ArrayList<Map<String, String>>(this.map.getLayers().getCount());
        for(MapLayer layer : this.map.getLayers()) {
            // TODO: cast may cause error
            Cell cell = ((TiledMapTileLayer) layer).getCell(player.currentTile.x, player.currentTile.y);
            TiledMapTile tile = cell.getTile();
            Iterator<String> keys = tile.getProperties().getKeys();
            Map<String, String> this_map = new HashMap<String, String>();
            while(keys.hasNext()) {
                String key = keys.next();
                String val = (String) tile.getProperties().get(key);
                this_map.put(key, val);
            }
            ret.add(this_map);
        }
        return ret;
    }

    public void draw() {
        // TODO Auto-generated method stub
    }
}

// Local Variables:
// indent-tabs-mode: nil
// End:
