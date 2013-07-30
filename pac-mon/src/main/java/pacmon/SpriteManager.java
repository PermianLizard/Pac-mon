package pacmon;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import pacmon.model.maze.Maze;

public class SpriteManager
{

	public static final String SPRITE_IMAGE_PATH = Globals.RESOURCE_PATH+File.separator
			+ "images"+File.separator
			+ Maze.TILE_SIZE
			+ "x"
			+ Maze.TILE_SIZE
			+ File.separator
			+ "sprites"
			+ File.separator;
	
	public static final String PACMON_SPRITE_DEFAULT = "pacmon.png";
	public static final String BLINKY_SPRITE_DEFAULT = "blinky.png";
	public static final String PINKY_SPRITE_DEFAULT = "pinky.png";
	public static final String INKY_SPRITE_DEFAULT = "inky.png";
	public static final String CLYDE_SPRITE_DEFAULT = "clyde.png";
	public static final String BONUS_SPRITE_DEFAULT = "bonus.png";
	public static final String CONSUMABLES_SPRITE_DEFAULT = "consumables.png";
	public static final String GATE_SPRITE_DEFAULT = "gate.png";
	
	private static final String[] spriteFiles = {PACMON_SPRITE_DEFAULT, BLINKY_SPRITE_DEFAULT, 
		PINKY_SPRITE_DEFAULT, INKY_SPRITE_DEFAULT,
		CLYDE_SPRITE_DEFAULT, BONUS_SPRITE_DEFAULT, 
		CONSUMABLES_SPRITE_DEFAULT, GATE_SPRITE_DEFAULT};
	
	private static final Map<String,BufferedImage> spriteImageMap;
	
	static
	{
		spriteImageMap = new HashMap<String,BufferedImage>();
	}
	
	public static void loadImages() throws IOException
	{
		for (String imageFile : spriteFiles)
		{
			spriteImageMap.put(imageFile, ImageIO.read(new File(SPRITE_IMAGE_PATH + imageFile)));
		}	
	}
	
	public static BufferedImage getImage(String tileset)
	{
		return spriteImageMap.get(tileset);
	}
	
}
