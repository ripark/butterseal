package edu.smcm.gamedev.butterseal;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.x = cfg.y = 0;
		cfg.title = "ButterSeal";
		cfg.useGL20 = false;
		cfg.width = 1280;
		cfg.height = 800;
		
		ButterSeal.ANDROID_MODE = false;
		new LwjglApplication(new ButterSeal(), cfg);
	}
}
