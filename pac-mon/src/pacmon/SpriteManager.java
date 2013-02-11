package pacmon;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import pacmon.model.maze.Maze;

public class SpriteManager
{

	public static final String PACMON_SPRITE_DEFAULT = "pacmon.png";
	public static final String BLINKY_SPRITE_DEFAULT = "blinky.png";
	public static final String PINKY_SPRITE_DEFAULT = "pinky.png";
	public static final String INKY_SPRITE_DEFAULT = "inky.png";
	public static final String CLYDE_SPRITE_DEFAULT = "clyde.png";
	public static final String BONUS_SPRITE_DEFAULT = "bonus.png";
	public static final String CONSUMABLES_SPRITE_DEFAULT = "consumables.png";
	
	public static void loadPacmonSprites() throws IOException
	{
		pacmonSpriteImage = ImageIO.read(new File((new File(".")).getAbsolutePath().replaceAll(".", "")+"images"+File.separator+Maze.TILE_SIZE+"x"+Maze.TILE_SIZE+File.separator+"sprites"+File.separator+PACMON_SPRITE_DEFAULT));	
	}
	
	public static void loadBlinkySprites() throws IOException
	{
		blinkySpriteImage = ImageIO.read(new File((new File(".")).getAbsolutePath().replaceAll(".", "")+"images"+File.separator+Maze.TILE_SIZE+"x"+Maze.TILE_SIZE+File.separator+"sprites"+File.separator+BLINKY_SPRITE_DEFAULT));	
	}
	
	public static void loadPinkySprites() throws IOException
	{
		pinkySpriteImage = ImageIO.read(new File((new File(".")).getAbsolutePath().replaceAll(".", "")+"images"+File.separator+Maze.TILE_SIZE+"x"+Maze.TILE_SIZE+File.separator+"sprites"+File.separator+PINKY_SPRITE_DEFAULT));	
	}
	
	public static void loadInkySprites() throws IOException
	{
		inkySpriteImage = ImageIO.read(new File((new File(".")).getAbsolutePath().replaceAll(".", "")+"images"+File.separator+Maze.TILE_SIZE+"x"+Maze.TILE_SIZE+File.separator+"sprites"+File.separator+INKY_SPRITE_DEFAULT));	
	}
	
	public static void loadClydeSprites() throws IOException
	{
		clydeSpriteImage = ImageIO.read(new File((new File(".")).getAbsolutePath().replaceAll(".", "")+"images"+File.separator+Maze.TILE_SIZE+"x"+Maze.TILE_SIZE+File.separator+"sprites"+File.separator+CLYDE_SPRITE_DEFAULT));	
	}
	
	public static void loadBonusSprites() throws IOException
	{
		bonusSpriteImage = ImageIO.read(new File((new File(".")).getAbsolutePath().replaceAll(".", "")+"images"+File.separator+Maze.TILE_SIZE+"x"+Maze.TILE_SIZE+File.separator+"sprites"+File.separator+BONUS_SPRITE_DEFAULT));	
	}
	
	public static void loadConsumablesSprites() throws IOException
	{
		consumablesSpriteImage = ImageIO.read(new File((new File(".")).getAbsolutePath().replaceAll(".", "")+"images"+File.separator+Maze.TILE_SIZE+"x"+Maze.TILE_SIZE+File.separator+"sprites"+File.separator+CONSUMABLES_SPRITE_DEFAULT));	
	}
	
	public static BufferedImage getPacMonSpriteImage()
	{
		return pacmonSpriteImage;
	}
	
	public static BufferedImage getBlinkySpriteImage()
	{
		return blinkySpriteImage;
	}
	
	public static BufferedImage getPinkySpriteImage()
	{
		return pinkySpriteImage;
	}
	
	public static BufferedImage getInkySpriteImage()
	{
		return inkySpriteImage;
	}
	
	public static BufferedImage getClydeSpriteImage()
	{
		return clydeSpriteImage;
	}
	
	public static BufferedImage getBonusSpriteImage()
	{
		return bonusSpriteImage;
	}
	
	public static BufferedImage getConsumablesSpriteImage()
	{
		return consumablesSpriteImage;
	}
	
	private static BufferedImage pacmonSpriteImage;
	private static BufferedImage blinkySpriteImage;
	private static BufferedImage pinkySpriteImage;
	private static BufferedImage inkySpriteImage;
	private static BufferedImage clydeSpriteImage;
	private static BufferedImage bonusSpriteImage;
	private static BufferedImage consumablesSpriteImage;
	
}
