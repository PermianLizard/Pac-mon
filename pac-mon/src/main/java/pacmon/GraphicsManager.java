package pacmon;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import pacmon.model.maze.Maze;

public class GraphicsManager 
{
	
	public static final String TILESET_IMAGE_PATH = Globals.RESOURCE_PATH+File.separator
			+ "images"+File.separator
			+ Maze.TILE_SIZE
			+ "x"
			+ Maze.TILE_SIZE
			+ File.separator
			+ "tilesets"
			+ File.separator;
	
	public static final String TILESET_1 = "t1.gif";
	public static final String TILESET_2 = "t2.gif";
	public static final String TILESET_3 = "t3.png";
	public static final String GATE = "gate.png";
	
	public static void initializeGraphicsObject(Graphics2D g)
	{
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
	}
	
	public static void loadTilesets() throws IOException
	{
		if (tilesetMap == null)
			tilesetMap = new HashMap<String, BufferedImage>();
		
		for (String tilesetFile : tilesetFiles)
		{
			tilesetMap.put(tilesetFile, ImageIO.read(new File(TILESET_IMAGE_PATH + tilesetFile)));
		}	
	}
	
	public static BufferedImage getTileset(String tileset)
	{
		if (tilesetMap == null)
			return null;
		
		return tilesetMap.get(tileset);
	}
	
	private static String[] tilesetFiles = { TILESET_1, TILESET_2, TILESET_3, GATE };
	private static Map<String,BufferedImage> tilesetMap;
}
