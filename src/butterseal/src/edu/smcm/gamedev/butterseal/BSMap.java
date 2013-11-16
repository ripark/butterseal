package edu.smcm.gamedev.butterseal;

import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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

    const static final float PIXELS_PER_TILE = 32;
	
    BSGameState state;
    TiledMap map;
    TileAtlas atlas;
    TileMapRenderer renderer;

    BSMap(BSAssets asset, String key, Runnable action) {
        this.map = TiledLoader.createMap(Gdx.files.internal(asset.getAssetPath()));
        // TODO: compare with L148 of @9a50ff1735; possibly incorrect syntax
        this.atlas = new TileAtlas(this.map, Gdx.files.internal(asset.getAssetPath()));
        this.renderer = new OrthogonalTiledMapRenderer(this.map, this.atlas, 12, 12);
        // TODO: or (from https://code.google.com/p/libgdx/wiki/GraphicsTileMaps#Map_Renderer)
        this.renderer = new OrthogonalTiledMapRenderer(this.map, 1/BSMap.PIXELS_PER_TILE);
    }

    void draw(OrthographicCamera camera) {
        this.renderer.render(camera);
    }

    public List<Map<String, String>> getTileProperties(BSPlayer player) {
        // TODO: I think the following constructor exists.
        // Double-check.  I wand to create the array list with the
        // same size as how many layers there are in the map.
        List<Map<String, String>> ret = new ArrayList<Map<String, String>>(this.map.layers.size());
        for(TiledMapTileLayer layer : this.map.layers) {
            Cell cell = layer.getCell(player.currentTile.x, player.currentTile.y);
            TiledMapTile tile = cell.getTile();
            // TODO: this function returns `MapProperties`, but what exactly is that?
            ret.add(tile.getProperties());
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
