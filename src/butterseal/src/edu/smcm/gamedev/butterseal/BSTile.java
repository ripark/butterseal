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
        return getProperties(map, true);
    }
    public Map<String, HashMap<String, String>> getProperties(BSMap map, boolean debug) {
        Map<String, HashMap<String, String>> ret = new HashMap<String, HashMap<String, String>>();

        for(MapLayer mlayer : map.map.getLayers()) {
            TiledMapTileLayer layer = (TiledMapTileLayer) mlayer;

            try {
                Cell c = layer.getCell(x, y);
                TiledMapTile t = c.getTile();
                MapProperties prop = t.getProperties();
                HashMap<String, String> thislayer = new HashMap<String, String>();
                for(Iterator<String> i = prop.getKeys(); i.hasNext();) {
                    String mykey = i.next();
                    if(debug) {
                        System.out.printf("%s:%s::%d,%d:%s=%s%n", map.asset.assetPath, layer.getName(), x, y, mykey, prop.get(mykey));
                    }
                    thislayer.put(mykey, prop.get(mykey).toString());
                }
                ret.put(layer.getName(), thislayer);
            } catch (Exception e) {
                System.err.printf("Error retrieving property for %s:%s::%d,%d%n",
                            map.asset.assetPath, layer.getName(), x, y);
                e.printStackTrace();
            }
        }

        return ret;
    }
    public boolean isContainedIn(BSMap map) {
        TiledMapTileLayer t = (TiledMapTileLayer) map.map.getLayers().get("player");
        return 0 <= x && x < t.getWidth() && 0 <= y && y < t.getHeight();
    }
}

// Local Variables:
// indent-tabs-mode: nil
// End:
