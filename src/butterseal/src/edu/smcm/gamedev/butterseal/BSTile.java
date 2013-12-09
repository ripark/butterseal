package edu.smcm.gamedev.butterseal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;

public class BSTile {
    public int x, y;
    public BSTile(int row, int column) {
        this.x = column;
        this.y = row;
    }
    public BSTile(BSTile other) {
        this(other.y, other.x);
    }

    public Map<String, HashMap<String, String>> getProperties(BSMap map) {
        Map<String, HashMap<String, String>> ret = new HashMap<String, HashMap<String, String>>();

        for(MapLayer mlayer : map.map.getLayers()) {
            TiledMapTileLayer layer = (TiledMapTileLayer) mlayer;

            try {
                Cell c = layer.getCell(x, y);
                if (c == null) {
                    //System.err.printf("%s:%s::%d,%d:no cell%n", map.asset.assetPath, layer.getName(), x, y);
                    layer.setCell(x, y, new Cell());
                    c = layer.getCell(x, y);
                    c.setTile(BSTile.getTileForProperty(map, "invisible", "true"));
                }
                TiledMapTile t = c.getTile();
                MapProperties prop = t.getProperties();
                HashMap<String, String> thislayer = new HashMap<String, String>();
                for(Iterator<String> i = prop.getKeys(); i.hasNext();) {
                    String mykey = i.next();
                    if(ButterSeal.DEBUG > 3) {
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
    public boolean isContainedIn(TiledMapTileLayer l) {
        return 0 <= x && x < l.getWidth() && 0 <= y && y < l.getHeight();
    }
    public void setID(TiledMapTileLayer layer, int id) {
        layer.getCell(x, y).getTile().setId(id);
    }
    public void setTile(TiledMapTileLayer layer, TiledMapTile tile) {
        layer.getCell(x, y).setTile(tile);
    }

    public static TiledMapTile getTileForProperty(BSMap map, String key, String value) {
        for (TiledMapTileSet i : map.map.getTileSets()) {
            Iterator<TiledMapTile> j = i.iterator();
            while(j.hasNext()) {
                TiledMapTile t = j.next();
                MapProperties p = t.getProperties();
                if (p.containsKey(key) && p.get(key).equals(value)) {
                    return t;
                }
            }
        }
        return null;
    }

    public static TextureRegion getTextureRegionForProperty(BSMap map, String key, String value) {
        for (TiledMapTileSet i : map.map.getTileSets()) {
            Iterator<TiledMapTile> j = i.iterator();
            while(j.hasNext()) {
                TiledMapTile t = j.next();
                MapProperties p = t.getProperties();
                if (p.containsKey(key) && p.get(key).equals(value)) {
                    return t.getTextureRegion();
                }
            }
        }
        return null;
    }
    public TiledMapTile getTile(TiledMapTileLayer layer) {
        return this.getCell(layer).getTile();
    }
    public TiledMapTileLayer.Cell getCell(TiledMapTileLayer layer) {
        return layer.getCell(x, y);
    }
    public boolean hasProperty(BSMap map, TiledMapTileLayer layer, String key, String value) {
        if (!this.isContainedIn(layer)) {
            return false;
        }
        Cell c = getCell(layer);
        TiledMapTile t;
        if (c == null) {
            t = BSTile.getTileForProperty(map, "invisible", "true");
        }
        else {
            t = c.getTile();
        }
        MapProperties p = t.getProperties();
        if(value == null) {
            return p.containsKey(key);
        }
        return BSUtil.propertyEquals(p, key, value);
    }
    public void setProperty(TiledMapTileLayer map, String key, String value) {
        map.getProperties().put(key, value);
    }
    public void transpose(int dx, int dy) {
        x += dx;
        y += dy;
    }
}

// Local Variables:
// indent-tabs-mode: nil
// End:
