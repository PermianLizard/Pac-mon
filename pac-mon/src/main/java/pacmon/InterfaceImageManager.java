package pacmon;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import pacmon.model.maze.Maze;

public class InterfaceImageManager 
{

	public static final String INTERFACE_IMAGE_PATH = Globals.RESOURCE_PATH+File.separator
			+ "images"+File.separator
			+ Maze.TILE_SIZE
			+ "x"
			+ Maze.TILE_SIZE
			+ File.separator
			+ "interface"
			+ File.separator;
	
	public static final String TITLE = "title.png";
	
	public static final String SCREEN_MAIN = "scr_main.png";
	public static final String SCREEN_GAME_OPTIONS = "scr_game_options.png";
	public static final String SCREEN_GAME_OVER = "scr_game_over.png";
	
	public static final String BUTTON_NEW_GAME = "btn_new_game.png";
	public static final String BUTTON_HIGH_SCORES = "btn_high_scores.png";
	public static final String BUTTON_EXIT = "btn_exit.png";
	public static final String BUTTON_CONTINUE = "btn_continue.png";
	public static final String BUTTON_QUIT = "btn_quit.png";

	private static final String[] imagesFiles = {TITLE, SCREEN_MAIN, SCREEN_GAME_OPTIONS, 
		SCREEN_GAME_OVER, BUTTON_NEW_GAME, 
		BUTTON_HIGH_SCORES, BUTTON_EXIT, 
		BUTTON_CONTINUE, BUTTON_QUIT};
	
	private static final Map<String,BufferedImage> imagesMap;
	
	static
	{
		imagesMap = new HashMap<String,BufferedImage>();
	}
	
	public static void loadImages() throws IOException
	{
		for (String imageFile : imagesFiles)
		{
			imagesMap.put(imageFile, ImageIO.read(new File(INTERFACE_IMAGE_PATH + imageFile)));
		}	
	}
	
	public static BufferedImage getImage(String tileset)
	{
		return imagesMap.get(tileset);
	}
	
}
