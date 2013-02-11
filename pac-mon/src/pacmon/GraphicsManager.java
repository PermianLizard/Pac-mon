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
	
	public static final String TILESET_DEFAULT = "default.gif";
	
	public static void initializeGraphicsObject(Graphics2D g)
	{
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
	}
	
	public static void loadTilesets() throws IOException
	{
		if (tilesetMap == null)
			tilesetMap = new HashMap<String, BufferedImage>();
		
		for (String tf : tilesetFiles)
		{
			tilesetMap.put(tf, ImageIO.read(new File((new File(".")).getAbsolutePath().replaceAll(".", "")+"images"+File.separator+Maze.TILE_SIZE+"x"+Maze.TILE_SIZE+File.separator+"tilesets"+File.separator+tf)));
		}	
	}
	
	public static BufferedImage getTileset(String tileset)
	{
		if (tilesetMap == null)
			return null;
		
		return tilesetMap.get(tileset);
	}
	
	private static String[] tilesetFiles = { TILESET_DEFAULT };
	private static Map<String,BufferedImage> tilesetMap;
}
