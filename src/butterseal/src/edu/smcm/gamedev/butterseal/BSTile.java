package edu.smcm.gamedev.butterseal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

public class BSTile {
    public int x, y;
    public BSTile(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public BSTile(BSTile other) {
        this(other.x, other.y);
    }

    public Map<String, HashMap<String, String>> getProperties(BSMap map) {
        Map<String, HashMap<String, String>> ret = new HashMap<String, HashMap<String, String>>();

        for(MapLayer mlayer : map.map.getLayers()) {
            TiledMapTileLayer layer = (TiledMapTileLayer) mlayer;

            try {
                Cell c = layer.getCell(x, y);
                TiledMapTile t = c.getTile();
                MapProperties prop = t.getProperties();
                for(Iterator<String> i = prop.getKeys(); i.hasNext();) {
                    String mykey = i.next();
                    System.out.printf("%s:%s::%d,%d:%s=%s%n", map.asset.assetPath, layer.getName(), x, y, mykey, prop.get(mykey));
                }
            } catch (Exception e) {
                System.err.printf("Error retrieving property for %s:%s::%d,%d%n",
                            map.asset.assetPath, layer.getName(), x, y);
            }
        }

        return ret;
    }
}

// Local Variables:
// indent-tabs-mode: nil
// End:
