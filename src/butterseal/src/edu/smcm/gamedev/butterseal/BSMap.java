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
    ICE_CAVE(BSAssets.ICE_CAVE.getAssetPath(), "ice-cave", new Runnable() {
            public void run() {
                // game logic
            }
	});
	
    BSGameState state;

    BSMap(String asset, String key, Runnable action) {
        // TODO Auto-generated constructor stub
    }
	
    void draw(SpriteBatch batch) {
		
    }

    public Map<String, String> getTileProperties(BSTile tile) {
        return null;
    }

    public void draw() {
        // TODO Auto-generated method stub
		
    }
}

// Local Variables:
// indent-tabs-mode: nil
// End:
