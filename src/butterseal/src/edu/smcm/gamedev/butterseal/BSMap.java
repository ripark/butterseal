package edu.smcm.gamedev.butterseal;

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
}
