package edu.smcm.gamedev.butterseal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public enum BSMap {
    HOME(BSAsset.HOUSE, "house", new Runnable() {
            public void run() {
                // game logic
            }
	}),
    ICE_CAVE_ENTRY(BSAsset.ICE_CAVE_ENTRY, null, new Runnable() {
            public void run() {
                // game logic
            }
	}),
    ICE_CAVE(BSAsset.ICE_CAVE, "ice-cave", new Runnable() {
            public void run() {
                // game logic
            }
	});

    static final float PIXELS_PER_TILE = 32;
	
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;

    BSMap(BSAsset asset, String key, Runnable action) {
        this.map = new TmxMapLoader().load(asset.assetPath);
        this.renderer = new OrthogonalTiledMapRenderer(this.map, 1f/BSMap.PIXELS_PER_TILE);
                
        for(MapLayer layer : map.getLayers()) {
            TiledMapTileLayer tlayer = (TiledMapTileLayer) layer;
            System.out.printf("Analyzing %s:%s%n", asset.assetPath, tlayer.getName());

            for(int col = 0; col < tlayer.getWidth(); col++) {
                for(int row = 0; row < tlayer.getHeight(); row++) {
                    try{
                        Cell c = tlayer.getCell(0, 0);
                        TiledMapTile t = c.getTile();
                        MapProperties prop = t.getProperties();
                        for(Iterator<String> i = prop.getKeys(); i.hasNext();) {
                            String mykey = i.next();
                            //System.out.printf("%s=%s%n", mykey, prop.get(mykey));
                        }
                        int tileid = t.getId();
                        //System.out.println(tileid);
                        MapProperties tprop = map.getTileSets().getTile(t.getId()).getProperties();
                        for(Iterator<String> i = tprop.getKeys(); i.hasNext();) {
                            String mykey = i.next();
                            //System.out.printf("%s=%s%n", mykey, tprop.get(mykey));
                        }
                        if(tprop.containsKey("player")) {
                            System.out.printf("Found player=%s%n", prop.get("player", String.class));
                        }
                    } catch (Exception e) {

                    }
                }
            }
        }
        //for( )
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
}

// Local Variables:
// indent-tabs-mode: nil
// End:
