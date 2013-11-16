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
    HOME(null,null, new Runnable() {
            public void run() {
                // game logic
            }
	}),
    ICE_CAVE_ENTRY(null,null, new Runnable() {
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
        // TODO: compare with L148 of @9a50ff1735; possibly incorrect syntax
        //this.atlas = new TileAtlas(this.map, Gdx.files.internal(asset.getAssetPath()));
        //this.renderer = new OrthogonalTiledMapRenderer(this.map, this.atlas, 12, 12);
        // TODO: or (from https://code.google.com/p/libgdx/wiki/GraphicsTileMaps#Map_Renderer)
        this.renderer = new OrthogonalTiledMapRenderer(this.map, 1/BSMap.PIXELS_PER_TILE);
    }

    void draw(OrthographicCamera camera) {
        this.renderer.setView(camera);
        this.renderer.render();
    }

    public List<Map<String, String>> getTileProperties(BSPlayer player) {
        // TODO: I think the following constructor exists.
        // Double-check.  I wand to create the array list with the
        // same size as how many layers there are in the map.
        List<Map<String, String>> ret = new ArrayList<Map<String, String>>(this.map.getLayers().getCount());
        for(MapLayer layer : this.map.getLayers()) {
            // TODO: cast may cause error
            Cell cell = ((TiledMapTileLayer) layer).getCell(player.currentTile.x, player.currentTile.y);
            TiledMapTile tile = cell.getTile();
            // TODO: this function returns `MapProperties`, but what exactly is that?
            
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
