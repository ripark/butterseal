package edu.smcm.ButterSealGame;
 
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObjectGroup;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
 
public class Seal implements ApplicationListener
{
	private int width;
	private int height;
	private int camWidth = 332;
	private int camHeight = 192;
	private int mapWidth;
	private int mapHeight;
	
	private int currentTileX;
	private int currentTileY;
	
	private int uiHeight;
	private int uiWidth;
	private int uiHeight3;
	private int uiWidth3;
	
	private Texture dpadImage;
	private Texture aButtonImage;
	private Rectangle dpad;
	private Rectangle aButton;
	private Music BestMusic;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Vector3 touchPos;
	private TileMapRenderer tileMapRenderer;
	private Player player;
	private Matrix4 matrix;
	private TiledMap tiledMap;
	
	private long lastActionTime;
	
	private boolean isMoving;
	private boolean isPressed;
	private int moveAmount;
	
	private enum MapQuadrant
	{
		NORTH,
		EAST,
		SOUTH,
		WEST,
		INVALID
	};
	
	private MapQuadrant quadrant;
	
	private static class Tile
	{
		public int tileId;
		
		public enum Kind
		{
			WALL,
			playTheBestMusic
		};
		public Kind kind;
	}
	private Tile currentTile;
	
	
	/**
	 * sets up everything. (in order, somewhat) Gets the screen dimensions, sets the initial screen color, creates the d-pad texture,
	 * gets the d-pad dimensions, sets up music (currently commented out, music not included), makes the d-pad sprite and sets it 
	 * in the bottom left corner, imports everything about the tiled map, sets up the camera dimensions, and gets the initial player 
	 * spawn point and sets the camera and player to that point.
	 */

	@Override
	public void create() 
	{
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		Gdx.gl.glClearColor(0f,0,0.0f,1);
		
		dpadImage = new Texture(Gdx.files.internal("dpad1.png"));
		aButtonImage = new Texture(Gdx.files.internal("aButton.png"));
		uiHeight = dpadImage.getHeight();
		uiWidth = dpadImage.getWidth();
		uiHeight3 = uiHeight/3;
		uiWidth3 = uiWidth/3;
		BestMusic = Gdx.audio.newMusic(Gdx.files.internal("Best.mp3"));
		
		dpad = new Rectangle();
		dpad.x = 0;
		dpad.y = 0;
		dpad.width = uiWidth;
		dpad.height = uiHeight;
		
		aButton = new Rectangle();
		aButton.x = width-uiWidth;
		aButton.y = 0;
		
		batch = new SpriteBatch();
		
		touchPos = new Vector3();
		
		tiledMap = new TiledMap();
		tiledMap = TiledLoader.createMap(Gdx.files.internal("collisionmap/collisionTestDesert.tmx"));
		TileAtlas tileAtlas = new TileAtlas(tiledMap, Gdx.files.internal("collisionmap"));
		tileMapRenderer = new TileMapRenderer(tiledMap, tileAtlas, 12, 12);
		
		mapWidth = tiledMap.width * tiledMap.tileWidth;
		mapHeight = tiledMap.height * tiledMap.tileHeight;
		
		BestMusic.setLooping(true);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, camWidth*2, camHeight*2);
		//camera.position.set(camWidth, camHeight, 0);
		
		matrix = camera.combined;
		
		for (TiledObjectGroup group : tiledMap.objectGroups) {
	         for (TiledObject object : group.objects) {
	        	 if("player".equals(object.type)){
	            	player = new Player(object.x + 16, mapHeight - (object.y+16));
	            	camera.position.set(object.x + 16, mapHeight - (object.y+16), 0);
	        	 }
	         }
		}
				
		currentTileX = 26;
		currentTileY = 45;
		currentTile = new Tile();
		currentTile.tileId = tiledMap.layers.get(0).tiles[currentTileY][currentTileX];
		
		quadrant = MapQuadrant.INVALID;
		isMoving = false;
		isPressed = false;
		moveAmount = 0;
	}
 
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}
 
	/**
	 * if the player is not currently moving, handles input.
	 * otherwise, it checks which quadrant of the d-pad was pressed, then translates the camera and the player at the same rate
	 * through the map in the specified direction. It currently moves 4 pixels per frame, and counts down from the initial move
	 * value of 32 until it has moved exactly one tile (32 pixels).
	 */
	private void movePlayerCamera()
	{
		if(!isMoving)
			handleInput();
		else if(moveAmount > 0)
		{
			switch(quadrant)
			{
			case NORTH:
				camera.translate(0, 4, 0);
				player.Translate(0, 4);
				break;
			case EAST:
				camera.translate(4, 0, 0);
				player.Translate(4, 0);
				break;
			case SOUTH:
				camera.translate(0, -4, 0);
				player.Translate(0, -4);
				break;	
			case WEST:
				camera.translate(-4, 0, 0);
				player.Translate(-4, 0);
				break;
			default:
				break;			
			}
			moveAmount -= 4;
		}
		else
			isMoving = false;			
	}
 
	/**
	 * basic render function. Calls for input first, then clears the screen. Then, it updates the camera, renders the map
	 * to the camera, draws the player to scale of the camera, and draws the d-pad and 'A' button.
	 */
	@Override
	public void render() 
	{		
 
		movePlayerCamera();
		
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT );
		
		camera.update();
		tileMapRenderer.render(camera);
		
		player.Draw(matrix);
		
		batch.begin();
		batch.draw(dpadImage, dpad.x, dpad.y);
		batch.draw(aButtonImage, aButton.x, aButton.y);
		batch.end();
	}
	
	// Helper functions for handleInput
	private boolean isInDpad()
	{
		if((touchPos.y < (height - uiHeight)))
			return false;
		if(touchPos.x > uiWidth)
			return false;
		
		return true;		
	}
	
	private boolean isInAbutton()
	{
		if((touchPos.y < (height - uiHeight3)) && (touchPos.y > (height - (uiHeight3*2))) && (touchPos.x < (width - uiWidth3)) &&
				(touchPos.x > (width - (uiWidth3*2))))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * checks to see the property of the tile the player wants to move onto by getting the tile ID and
	 * checking the property value. Checks property value against Property, if check goes through, returns true.
	 * @param Property the property you are checking for to check for interaction.
	 * @return
	 */
	private boolean Interaction(String Property)
	{
		switch(quadrant)
		{
		case WEST:
			currentTile.tileId = tiledMap.layers.get(0).tiles[currentTileY][currentTileX-1];
			break;
		case EAST:
			currentTile.tileId = tiledMap.layers.get(0).tiles[currentTileY][currentTileX+1];
			break;
		case NORTH:
			currentTile.tileId = tiledMap.layers.get(0).tiles[currentTileY-1][currentTileX];
			break;
		case SOUTH:
			currentTile.tileId = tiledMap.layers.get(0).tiles[currentTileY+1][currentTileX];
			break;
		default:
			break;
		}
		if (tiledMap.getTileProperty(currentTile.tileId, Property) != null) {
			if(Property == "playTheBestMusic") {
				return true;
			}
			else if(Property == "wall")	{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * finds the quadrant of the d-pad image the player is touching
	 */
	private void findQuadrant()
	{		
		if(touchPos.y > (height - (uiHeight3*2)) && (touchPos.y < (height - uiHeight3)) && (touchPos.x < uiWidth3))
			quadrant = MapQuadrant.WEST;
		else if(touchPos.y > (height - (uiHeight3*2)) && (touchPos.y < (height - uiHeight3)) && (touchPos.x > (uiWidth3*2)))
			quadrant = MapQuadrant.EAST;
		else if((touchPos.y < (height - uiHeight3*2)) && (touchPos.x < (uiWidth3*2)) && (touchPos.x > uiWidth3))
			quadrant = MapQuadrant.NORTH;
		else if((touchPos.y > (height - uiHeight3)) && (touchPos.x > uiWidth3) && (touchPos.x < (uiWidth3*2)))
			quadrant = MapQuadrant.SOUTH;
		else
			quadrant = MapQuadrant.INVALID;
 
	}
	
	/**
	 * checks to see if the player is touching inside the d-pad, then checks to see which quadrant of the d-pad is being touched.
	 * if input is valid, calls Interaction() for certain properties. If property checks go through, acts on those properties
	 * by calling related functions.
	 */
	private void handleInput()
	{
		if(Gdx.input.isTouched()) 
		{
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			
			if(isInAbutton())
			{
				if(Interaction("playTheBestMusic")) {
					checkToggleMusic();
				}
				isPressed = true;
				return;
			}
			
			if(!isInDpad())
			{
				return;
			}
			
			findQuadrant();
			
			switch(quadrant)
			{
			case WEST:				
				if(!Interaction("wall")) {
					moveAmount = 32;
					isMoving = true;
					currentTileX--;
				}
				break;
			case EAST:				
				if(!Interaction("wall")) {
					moveAmount = 32;
					isMoving = true;
					currentTileX++;
					
				}
				break;
			case NORTH:				
				if(!Interaction("wall")) {
					moveAmount = 32;
					isMoving = true;
					currentTileY--;
				}
				break;
			case SOUTH:
				if(!Interaction("wall")) {
					moveAmount = 32;
					isMoving = true;
					currentTileY++;
				}
				break;
			default:
				break;
			}			
		}
		isPressed = false;
	}
	
	/**
	 * toggles music. Only applies the first frame the button is pressed.
	 */
	public void checkToggleMusic() 
	{
		if(!isPressed) {
			if(BestMusic.isPlaying()) {
					BestMusic.pause();	 
			}
			else {
					BestMusic.play();	 
			}
		}
	}
 
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
 
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
 
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
 
}