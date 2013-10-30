package edu.smcm.ButterSealGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;

public class Player {
	
	private SpriteBatch batch;
	
	private static final int        FRAME_COLS = 2;
    private static final int        FRAME_ROWS = 2;
	
    Animation                       walkUpAnimation;
    Animation                       walkDownAnimation;
    Animation                       walkRightAnimation;
    Animation                       walkLeftAnimation;
    Animation                       idleStateAnimation;
    Texture                         walkUpSheet;
    Texture                         walkLeftSheet;
    Texture                         walkRightSheet;
    Texture                         walkDownSheet;
    Texture 						idleStateSheet;
    TextureRegion[]                 walkUpFrames;
    TextureRegion[]                 walkDownFrames;
    TextureRegion[]                 walkLeftFrames;
    TextureRegion[]                 walkRightFrames;
    TextureRegion[]                 idleStateFrames;
    TextureRegion                   currentFrame;
    
    float rightStateTime;
    float leftStateTime;
    float upStateTime;
    float downStateTime;
    float idleStateTime;
    
    float playerX;
    float playerY;
	
	//makes player. sets all animations and coordinates.
	public Player(float x, float y) {
	
		walkUpSheet = new Texture(Gdx.files.internal("walkUp.png"));
		walkDownSheet = new Texture(Gdx.files.internal("walkDown.png"));
		walkRightSheet = new Texture(Gdx.files.internal("walkRight.png"));
		walkLeftSheet = new Texture(Gdx.files.internal("walkLeft.png"));
		idleStateSheet = new Texture(Gdx.files.internal("idleState.png"));
		
		SetUpAnimations();
		SetDownAnimations();
		SetRightAnimations();
		SetLeftAnimations();
		SetIdleAnimations();
		
		playerX = x - (16);
		playerY = y - (16);
		
		batch = new SpriteBatch();
	}
	
	public void SetUpAnimations()
	{
		TextureRegion[][] tmp = TextureRegion.split(walkUpSheet, walkUpSheet.getWidth() / FRAME_COLS, walkUpSheet.getHeight() / FRAME_ROWS);
		walkUpFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkUpFrames[index++] = tmp[i][j];
			}
		}
		walkUpAnimation = new Animation(0.25f, walkUpFrames);
		batch = new SpriteBatch();
		upStateTime = 0f;  
	}
	
	public void SetDownAnimations()
	{
		TextureRegion[][] tmp = TextureRegion.split(walkDownSheet, walkDownSheet.getWidth() / FRAME_COLS, walkDownSheet.getHeight() / FRAME_ROWS);
		walkDownFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkDownFrames[index++] = tmp[i][j];
			}
		}
		walkDownAnimation = new Animation(0.25f, walkDownFrames);
		downStateTime = 0f;  
	}
	
	public void SetLeftAnimations()
	{
		TextureRegion[][] tmp = TextureRegion.split(walkLeftSheet, walkLeftSheet.getWidth() / FRAME_COLS, walkLeftSheet.getHeight() / FRAME_ROWS);
		walkLeftFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkLeftFrames[index++] = tmp[i][j];
			}
		}
		walkLeftAnimation = new Animation(0.25f, walkLeftFrames);
		leftStateTime = 0f;  
	}
	
	public void SetRightAnimations()
	{
		TextureRegion[][] tmp = TextureRegion.split(walkRightSheet, walkRightSheet.getWidth() / FRAME_COLS, walkRightSheet.getHeight() / FRAME_ROWS);
		walkRightFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkRightFrames[index++] = tmp[i][j];
			}
		}
		walkRightAnimation = new Animation(0.25f, walkRightFrames);
		rightStateTime = 0f;  
	}
	
	public void SetIdleAnimations()
	{
		TextureRegion[][] tmp = TextureRegion.split(idleStateSheet, idleStateSheet.getWidth() / FRAME_COLS, idleStateSheet.getHeight() / FRAME_ROWS);
		idleStateFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				idleStateFrames[index++] = tmp[i][j];
			}
		}
		idleStateAnimation = new Animation(0.25f, idleStateFrames);
		idleStateTime = 0f;  
	}
	
	public void SetCurrentFrame(Seal.MapQuadrant quadrant)
	{
		switch(quadrant)
		{
		case WEST:
			leftStateTime += Gdx.graphics.getDeltaTime();
			currentFrame = walkLeftAnimation.getKeyFrame(leftStateTime, true);
			break;
		case EAST:
			rightStateTime += Gdx.graphics.getDeltaTime();
			currentFrame = walkRightAnimation.getKeyFrame(rightStateTime, true);
			break;
		case NORTH:
			upStateTime += Gdx.graphics.getDeltaTime();
			currentFrame = walkUpAnimation.getKeyFrame(upStateTime, true);
			break;
		case SOUTH:
			downStateTime += Gdx.graphics.getDeltaTime();
			currentFrame = walkDownAnimation.getKeyFrame(downStateTime, true);
			break;
		default:
			break;			
		}
	}
	
	public void SetCurrentFrame()
	{
		idleStateTime += Gdx.graphics.getDeltaTime();
        currentFrame = idleStateAnimation.getKeyFrame(idleStateTime, true);
	}
	
	//takes in a Matrix4 from the camera to set player to scale, then draws it.
	public void Draw(Matrix4 matrix) {
		batch.setProjectionMatrix(matrix);
		batch.begin();
		batch.draw(currentFrame, playerX, playerY);
		batch.end();
	}
	
	//moves player by x and y values
	public void Translate(float x, float y) {
		playerX += x;
		playerY += y;
	}
}
