package pacmon;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import pacmon.model.maze.Maze;

public class ImageManager 
{

	public static final String SCREEN_MAIN = "scr_main.png";
	public static final String SCREEN_GAME_OPTIONS = "scr_game_options.png";
	public static final String SCREEN_GAME_OVER = "scr_game_over.png";
	
	public static final String BUTTON_NEW_GAME = "btn_new_game.png";
	public static final String BUTTON_HIGH_SCORES = "btn_high_scores.png";
	public static final String BUTTON_EXIT = "btn_exit.png";
	public static final String BUTTON_CONTINUE = "btn_continue.png";
	public static final String BUTTON_QUIT = "btn_quit.png";

	public static void loadAll() throws IOException
	{
		screenMainImage = ImageIO.read(new File((new File(".")).getAbsolutePath().replaceAll(".", "")+"images"+File.separator+Maze.TILE_SIZE+"x"+Maze.TILE_SIZE+File.separator+"interface"+File.separator+SCREEN_MAIN));
		screenGameOptionsImage = ImageIO.read(new File((new File(".")).getAbsolutePath().replaceAll(".", "")+"images"+File.separator+Maze.TILE_SIZE+"x"+Maze.TILE_SIZE+File.separator+"interface"+File.separator+SCREEN_GAME_OPTIONS));
		screenGameOverImage = ImageIO.read(new File((new File(".")).getAbsolutePath().replaceAll(".", "")+"images"+File.separator+Maze.TILE_SIZE+"x"+Maze.TILE_SIZE+File.separator+"interface"+File.separator+SCREEN_GAME_OVER));
		
		buttonNewGameImage = ImageIO.read(new File((new File(".")).getAbsolutePath().replaceAll(".", "")+"images"+File.separator+Maze.TILE_SIZE+"x"+Maze.TILE_SIZE+File.separator+"interface"+File.separator+BUTTON_NEW_GAME));
		buttonHighScoresImage = ImageIO.read(new File((new File(".")).getAbsolutePath().replaceAll(".", "")+"images"+File.separator+Maze.TILE_SIZE+"x"+Maze.TILE_SIZE+File.separator+"interface"+File.separator+BUTTON_HIGH_SCORES));
		buttonExitImage = ImageIO.read(new File((new File(".")).getAbsolutePath().replaceAll(".", "")+"images"+File.separator+Maze.TILE_SIZE+"x"+Maze.TILE_SIZE+File.separator+"interface"+File.separator+BUTTON_EXIT));
		buttonContinueImage = ImageIO.read(new File((new File(".")).getAbsolutePath().replaceAll(".", "")+"images"+File.separator+Maze.TILE_SIZE+"x"+Maze.TILE_SIZE+File.separator+"interface"+File.separator+BUTTON_CONTINUE));
		buttonQuitImage = ImageIO.read(new File((new File(".")).getAbsolutePath().replaceAll(".", "")+"images"+File.separator+Maze.TILE_SIZE+"x"+Maze.TILE_SIZE+File.separator+"interface"+File.separator+BUTTON_QUIT));
	}
	
	public static void unloadAll()
	{
		screenMainImage = null;
		screenGameOptionsImage = null;
		screenGameOverImage = null;
		
		buttonNewGameImage = null;
		buttonHighScoresImage = null;
		buttonExitImage = null;
		buttonContinueImage = null;
		buttonQuitImage = null;
	}
	
	public static BufferedImage getScreenMainImage()
	{
		return screenMainImage;
	}
	
	public static BufferedImage getScreenGameOptionsImage()
	{
		return screenGameOptionsImage;
	}
	
	public static BufferedImage getScreenGameOverImage()
	{
		return screenGameOverImage;
	}
	
	public static BufferedImage getButtonNewGameImage()
	{
		return buttonNewGameImage;
	}
	
	public static BufferedImage getButtonHighScoresImage()
	{
		return buttonHighScoresImage;
	}
	
	public static BufferedImage getButtonExitImage()
	{
		return buttonExitImage;
	}
	
	public static BufferedImage getButtonContinueImage()
	{
		return buttonContinueImage;
	}
	
	public static BufferedImage getButtonQuitImage()
	{
		return buttonQuitImage;
	}
		
	private static BufferedImage screenMainImage;
	private static BufferedImage screenGameOptionsImage;
	private static BufferedImage screenGameOverImage;
	
	private static BufferedImage buttonNewGameImage;
	private static BufferedImage buttonHighScoresImage;
	private static BufferedImage buttonExitImage;
	private static BufferedImage buttonContinueImage;
	private static BufferedImage buttonQuitImage;
}
