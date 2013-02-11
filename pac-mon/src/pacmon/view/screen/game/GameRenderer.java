package pacmon.view.screen.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;

import pacmon.FontManager;
import pacmon.SpriteManager;
import pacmon.model.Game;
import pacmon.model.entity.Blinky;
import pacmon.model.entity.Clyde;
import pacmon.model.entity.Inky;
import pacmon.model.entity.PacMon;
import pacmon.model.entity.Pinky;
import pacmon.model.level.LevelLabel;
import pacmon.model.maze.Maze;

public class GameRenderer 
{
	
	public static void renderGame(Graphics2D g, Game game)
	{
		Font labelFont = FontManager.getFont(FontManager.DEFAULT).deriveFont((float)Maze.TILE_SIZE - 2);		

		g.setColor(Color.WHITE);
		g.setFont(labelFont);
		g.drawString("Lvl: "+Integer.toString(game.getState().getLevelNum()), Maze.TILE_SIZE, Maze.TILE_SIZE * 2);
		g.drawString("Score: "+Integer.toString(game.getState().getTotalScore() + game.getLevel().getState().getScore()), Maze.TILE_SIZE * 17, Maze.TILE_SIZE * 2);
		//g.drawString("Total Score: "+Integer.toString(game.getState().getTotalScore()), Maze.TILE_SIZE * 15, Maze.TILE_SIZE * 2);
		
		int xOffset = 0;
		int yOffset = Maze.TILE_SIZE * 3;
		
		MazeRenderer.renderMaze(g, game.getLevel().getState().getMaze(), xOffset, yOffset);
		
		PacMon pacMon = game.getLevel().getPacMon();
		Blinky blinky = game.getLevel().getBlinky();
		Pinky pinky = game.getLevel().getPinky();
		Inky inky = game.getLevel().getInky();
		Clyde clyde = game.getLevel().getClyde();
		
		boolean flashOn = game.getLevel().isMonsterFlashOn();
		
		EntityRenderer.renderBlinky(g, blinky, xOffset, yOffset, flashOn);
		EntityRenderer.renderPinky(g, pinky, xOffset, yOffset, flashOn);
		EntityRenderer.renderInky(g, inky, xOffset, yOffset, flashOn);
		EntityRenderer.renderClyde(g, clyde, xOffset, yOffset, flashOn);
		EntityRenderer.renderPacMon(g, pacMon, xOffset, yOffset, game.getLevel().isPacMonDead());
		
		if (game.isInLevelStartDelay())
		{
			g.setColor(Color.WHITE);
			g.drawString("Ready!", (Maze.TILE_SIZE * 12) + 12, Maze.TILE_SIZE * 21);
		}
		
		if (game.paused)
		{
			g.setColor(Color.WHITE);
			g.drawString("Paused", Maze.TILE_SIZE, Maze.TILE_SIZE * 1);
		}
		else if (game.isInPacManDeath())
		{
			g.setColor(Color.WHITE);
			g.drawString("Death!", Maze.TILE_SIZE, Maze.TILE_SIZE * 1);
		}
		
		// render lives
		BufferedImage spriteImage = SpriteManager.getPacMonSpriteImage();
		int lx = Maze.TILE_SIZE;
		int ly = Maze.TILE_SIZE * 35;
		int row = 3;
		
		for (int i=0; i < game.getState().getLivesLeft(); ++i)
		{
			g.drawImage(spriteImage, lx - 10, ly - 10, lx + 10, ly + 10, 20 * 2, 20 * row, (20 * 2) + 20, (20 * row) + 20, null);
			
			lx += Maze.TILE_SIZE * 2;
		}
		
		// render bonus symbols
		if (game.getLevel().getState().getBonusAvailable() > 0)
		{
			int bx = Maze.TILE_SIZE * 22;
			int by = Maze.TILE_SIZE * 35;
			
			for (int i=0; i < game.getLevel().getState().getBonusAvailable(); ++i)
			{
				BufferedImage bonusSpriteImage = SpriteManager.getBonusSpriteImage();

				int frame = 0;
				switch(game.getLevel().getState().getBonus())
				{
				case BonusCherries : frame = 0; break;
				case BonusStrawberry : frame = 1; break;
				case BonusPeach : frame = 2; break;
				case BonusApple : frame = 3; break;
				case BonusMelon : frame = 4; break;
				case BonusGalaxian : frame = 5; break;
				case BonusBell : frame = 6; break;
				case BonusKey : frame = 7; break;
				default:
					break;
				}
				
				g.drawImage(bonusSpriteImage, bx - 8, by - 8, bx + 8, by + 8, 16 * frame, 0,16 * frame + 16, 16, null);
				
				bx += Maze.TILE_SIZE * 2;
			}
		}
		
		List<LevelLabel> labelList = game.getLevel().getLabels();
		g.setColor(Color.WHITE);
		for (LevelLabel label : labelList)
		{	
			Rectangle2D labelBounds = g.getFontMetrics().getStringBounds(label.getText(), g);
			g.drawString(label.getText(), label.getX() * Maze.TILE_SIZE + xOffset + Maze.TILE_SIZE - (int)(labelBounds.getWidth() / 2), label.getY() * Maze.TILE_SIZE + yOffset + Maze.TILE_SIZE);
		}
	}
}
