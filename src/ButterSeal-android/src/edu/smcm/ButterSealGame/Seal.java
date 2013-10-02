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
 
public class Seal implements ApplicationListener
{
	private int width;
	private int height;
	private int camWidth = 332;
	private int camHeight = 192;
	private int mapWidth;
	private int mapHeight;
	
	private int tileType;
	private int currentTileX;
	private int currentTileY;
	
	private int dpadHeight;
	private int dpadWidth;
	private int dpadHeight3;
	private int dpadWidth3;
	
	private Texture dpadImage;
	private Rectangle dpad;
	private Music DreamMusic;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Vector3 touchPos;
	private TileMapRenderer tileMapRenderer;
	private Player player;
	private Matrix4 matrix;
	private TiledMap tiledMap;
	
	private boolean isMoving;
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
	
	//sets up everything. (in order, somewhat) Gets the screen dimensions, sets the initial screen color, creates the d-pad texture,
	//gets the d-pad dimensions, sets up music (currently commented out, music not included), makes the d-pad sprite and sets it 
	//in the bottom left corner, imports everything about the tiled map, sets up the camera dimensions, and gets the initial player 
	//spawn point and sets the camera and player to that point.
	@Override
	public void create() 
	{
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		Gdx.gl.glClearColor(0f,0,0.0f,1);
		
		dpadImage = new Texture(Gdx.files.internal("dpad.png"));
		dpadHeight = dpadImage.getHeight();
		dpadWidth = dpadImage.getWidth();
		dpadHeight3 = dpadHeight/3;
		dpadWidth3 = dpadWidth/3;
		DreamMusic = Gdx.audio.newMusic(Gdx.files.internal("Dream.mp3"));
		
		dpad = new Rectangle();
		dpad.x = 0;
		dpad.y = 0;
		dpad.width = dpadWidth;
		dpad.height = dpadHeight;
		
		batch = new SpriteBatch();
		
		touchPos = new Vector3();
		
		tiledMap = new TiledMap();
		tiledMap = TiledLoader.createMap(Gdx.files.internal("collisionmap/collisionTestDesert.tmx"));
		TileAtlas tileAtlas = new TileAtlas(tiledMap, Gdx.files.internal("collisionmap"));
		tileMapRenderer = new TileMapRenderer(tiledMap, tileAtlas, 12, 12);
		
		mapWidth = tiledMap.width * tiledMap.tileWidth;
		mapHeight = tiledMap.height * tiledMap.tileHeight;
		
		//DreamMusic.setLooping(true);
		//DreamMusic.play();
		
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
		
		quadrant = MapQuadrant.INVALID;
		isMoving = false;
		moveAmount = 0;
	}
 
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}
 
	//if the player is not currently moving, handles input.
	//otherwise, it checks which quadrant of the d-pad was pressed, then translates the camera and the player at the same rate
	//through the map in the specified direction. It currently moves 4 pixels per frame, and counts down from the initial move
	//value of 32 until it has moved exactly one tile (32 pixels).
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
 
	//basic render function. Calls for input first, then clears the screen. Then, it updates the camera, renders the map
	//to the camera, draws the player to scale of the camera, and draws the d-pad at the bottom left corner.
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
		batch.end();
	}
	
	// Helper functions for handleInput
	private boolean isInDpad()
	{
		if((touchPos.y < (height - dpadHeight)))
			return false;
		if(touchPos.x > dpadWidth)
			return false;
		
		return true;		
	}
	
	
	//checks to see if the tile the player wants to move onto is a wall by getting the tile ID (tileType) and
	//checking the property value.
	private boolean isWall()
	{
		if(quadrant == MapQuadrant.WEST) {
			tileType = tiledMap.layers.get(0).tiles[currentTileY][currentTileX-1];
		}
		else if(quadrant == MapQuadrant.EAST) {
			tileType = tiledMap.layers.get(0).tiles[currentTileY][currentTileX+1];
		}
		else if(quadrant == MapQuadrant.NORTH) {
			tileType = tiledMap.layers.get(0).tiles[currentTileY-1][currentTileX];
		}
		else if(quadrant == MapQuadrant.SOUTH) {
			tileType = tiledMap.layers.get(0).tiles[currentTileY+1][currentTileX];
		}
		if ("1".equals(tiledMap.getTileProperty(tileType, "wall"))) {
		    return true;
		}
		return false;
	}
	
	//finds the quadrant of the d-pad image the player is touching
	private void findQuadrant()
	{		
		if(touchPos.y > (height - (dpadHeight3*2)) && (touchPos.y < (height - dpadHeight3)) && (touchPos.x < dpadWidth3))
			quadrant = MapQuadrant.WEST;
		else if(touchPos.y > (height - (dpadHeight3*2)) && (touchPos.y < (height - dpadHeight3)) && (touchPos.x > (dpadWidth3*2)))
			quadrant = MapQuadrant.EAST;
		else if((touchPos.y < (height - dpadHeight3*2)) && (touchPos.x < (dpadWidth3*2)) && (touchPos.x > dpadWidth3))
			quadrant = MapQuadrant.NORTH;
		else if((touchPos.y > (height - dpadHeight3)) && (touchPos.x > dpadWidth3) && (touchPos.x < (dpadWidth3*2)))
			quadrant = MapQuadrant.SOUTH;
		else
			quadrant = MapQuadrant.INVALID;
 
	}
	
	//checks to see if the player is touching inside the d-pad, then checks to see which quadrant of the d-pad is being touched.
	//if input is valid, checks to make sure the camera won't go out of bounds, then checks for potential collision.
	//if all checks go through, sets the camera up to move 32 pixels and changes isMoving status to true.
	//Also, updates the CurrentTile coordinates.
	private void handleInput()
	{
		if(Gdx.input.isTouched()) 
		{
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			
			if(!isInDpad())
				return;
			
			findQuadrant();
			
			switch(quadrant)
			{
			case WEST:
				if(camera.position.x - 32 < camWidth) {
					break;
				}
				if(!isWall()) {
					moveAmount = 32;
					isMoving = true;
					currentTileX--;
				}
				break;
			case EAST:
				if(camera.position.x + 32 > (mapWidth-camWidth)) {
					break;
				}
				if(!isWall()) {
					moveAmount = 32;
					isMoving = true;
					currentTileX++;
					
				}
				break;
			case NORTH:
				if(camera.position.y + 32 > (mapHeight-camHeight)) {
					break;
				}
				if(!isWall()) {
					moveAmount = 32;
					isMoving = true;
					currentTileY--;
				}
				break;
			case SOUTH:
				if(camera.position.y - 32 < (camHeight)) {
					break;
				}
				if(!isWall()) {
					moveAmount = 32;
					isMoving = true;
					currentTileY++;
				}
				break;
			default:
				break;
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