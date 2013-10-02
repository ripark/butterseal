package edu.smcm.ButterSealGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Player {
	
	private Texture pikaImage;
	private Rectangle pika;
	private Vector3 position;
	private SpriteBatch batch;
	
	//makes pika. gives him a Texture and a Rectangle.
	public Player(float x, float y) {
		pikaImage = new Texture(Gdx.files.internal("pika.png"));
		
		pika = new Rectangle();
		pika.x = x - (pikaImage.getWidth()/2);
		pika.y = y - (pikaImage.getHeight()/2);
		pika.width = pikaImage.getWidth();
		pika.height = pikaImage.getHeight();
		
		batch = new SpriteBatch();
	}
	
	//takes in a Matrix4 from the camera to set pika to scale, then draws him.
	public void Draw(Matrix4 matrix) {
		batch.setProjectionMatrix(matrix);
		batch.begin();
		batch.draw(pikaImage, pika.x, pika.y);
		batch.end();
	}
	
	//returns pika's Texture. I'm not sure why I made this one, but it does it.
	public Texture Image() {
		return pikaImage;
	}
	
	//returns the x coordinate of pika
	public float x() {
		return pika.x;
	}
	
	//returns the y coordinate of p ika
	public float y() {
		return pika.y;
	}
	
	//moves pika by x and y values
	public void Translate(float x, float y) {
		pika.x += x;
		pika.y += y;
	}
}
