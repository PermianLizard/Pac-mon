package pacmon.view.screen.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import pacmon.SpriteManager;
import pacmon.model.entity.Blinky;
import pacmon.model.entity.Clyde;
import pacmon.model.entity.Entity;
import pacmon.model.entity.EntityState;
import pacmon.model.entity.Inky;
import pacmon.model.entity.Monster;
import pacmon.model.entity.MonsterMode;
import pacmon.model.entity.PacMon;
import pacmon.model.entity.Pinky;
import pacmon.model.level.LevelMode;
import pacmon.model.level.LevelState;

public class EntityRenderer 
{
	public static void renderPacMon(Graphics2D g, PacMon pacMon, int xOffset, int yOffset, boolean isDead, boolean isPaused)
	{
		BufferedImage spriteImage = SpriteManager.getImage(SpriteManager.PACMON_SPRITE_DEFAULT);
		
		if (!isDead)
			timePacMonDead = -1;
		
		EntityState pacMonState = pacMon.getState();					
		
		int row = 0;
		if (pacMon.getDirection() == Entity.DIRECTION_UP)
		{
			row = 0;
		}
		else if (pacMon.getDirection() == Entity.DIRECTION_LEFT)
		{
			row = 1;
		}
		else if (pacMon.getDirection() == Entity.DIRECTION_DOWN)
		{
			row = 2;
		}
		else if (pacMon.getDirection() == Entity.DIRECTION_RIGHT)
		{
			row = 3;
		}
		
		if (isDead)
		{
			if (!isPaused)
				++timePacMonDead;
			
			int stateFrame = 6;
			
			if (timePacMonDead > 40)
				stateFrame = 10;
			else if (timePacMonDead > 30)
				stateFrame = 9;
			else if (timePacMonDead > 20)
				stateFrame = 8;
			else if (timePacMonDead > 10)
				stateFrame = 7;
			
			g.drawImage(spriteImage, pacMon.getPosX() - 10 + xOffset, pacMon.getPosY() - 10 + yOffset, pacMon.getPosX() + 10 + xOffset, pacMon.getPosY() + 10 + yOffset, 20 * stateFrame, 20 * row, (20 * stateFrame) + 20, (20 * row) + 20, null);
		}
		else if (pacMonState.equals(EntityState.STILL))
		{
			g.drawImage(spriteImage, pacMon.getPosX() - 10 + xOffset, pacMon.getPosY() - 10 + yOffset, pacMon.getPosX() + 10 + xOffset, pacMon.getPosY() + 10 + yOffset, 20 * 0, 20 * row, (20 * 0) + 20, (20 * row) + 20, null);
		}
		else if (pacMonState.equals(EntityState.MOVING))
		{
			LevelState levelState = pacMon.getLevel().getState();
			
			int frameTime = 0;
			if (!levelState.getMode().equals(LevelMode.FRIGHTENED))
			{
				frameTime = 2 + ((int)(10 * levelState.getPacManNormalSpeed()));
			}
			else
			{
				frameTime = 2 + ((int)(10 * levelState.getPacManFrightSpeed()));
			}
			
			int stateFrame = (int)(pacMon.timeInState % frameTime);
			if (stateFrame >= 0 && stateFrame < 4)
				stateFrame = 1;
			else if (stateFrame >= 4 && stateFrame < 8)
				stateFrame = 2;
			else if (stateFrame >= 8 && stateFrame < 12)
				stateFrame = 3;
			else if (stateFrame >= 12 && stateFrame < 16)
				stateFrame = 4;
			else if (stateFrame >= 16)
				stateFrame = 5;
			
			g.drawImage(spriteImage, pacMon.getPosX() - 10 + xOffset, pacMon.getPosY() - 10 + yOffset, pacMon.getPosX() + 10 + xOffset, pacMon.getPosY() + 10 + yOffset, 20 * stateFrame, 20 * row, (20 * stateFrame) + 20, (20 * row) + 20, null);
		}
	}
	
	public static void renderBlinky(Graphics2D g, Blinky blinky, int xOffset, int yOffset, boolean isFlashOn)
	{
		renderMonster(g, blinky, xOffset, yOffset, SpriteManager.getImage(SpriteManager.BLINKY_SPRITE_DEFAULT), isFlashOn);
	}
	
	public static void renderPinky(Graphics2D g, Pinky pinky, int xOffset, int yOffset, boolean isFlashOn)
	{
		renderMonster(g, pinky, xOffset, yOffset, SpriteManager.getImage(SpriteManager.PINKY_SPRITE_DEFAULT), isFlashOn);
	}
	
	public static void renderInky(Graphics2D g, Inky inky, int xOffset, int yOffset, boolean isFlashOn)
	{
		renderMonster(g, inky, xOffset, yOffset, SpriteManager.getImage(SpriteManager.INKY_SPRITE_DEFAULT), isFlashOn);
	}
	
	public static void renderClyde(Graphics2D g, Clyde clyde, int xOffset, int yOffset, boolean isFlashOn)
	{
		renderMonster(g, clyde, xOffset, yOffset, SpriteManager.getImage(SpriteManager.CLYDE_SPRITE_DEFAULT), isFlashOn);	
	}
	
	private static void renderMonster(Graphics2D g, Monster monster, int xOffset, int yOffset, BufferedImage spriteImage, boolean isFlashOn)
	{
		EntityState state = monster.getState();					
		
		int row = 0;
		if (monster.getDirection() == Entity.DIRECTION_UP)
		{
			row = 0;
		}
		else if (monster.getDirection() == Entity.DIRECTION_LEFT)
		{
			row = 1;
		}
		else if (monster.getDirection() == Entity.DIRECTION_DOWN)
		{
			row = 2;
		}
		else if (monster.getDirection() == Entity.DIRECTION_RIGHT)
		{
			row = 3;
		}
		
		int stateFrame = 0;
		if (state.equals(EntityState.STILL))
		{
			if (monster.getMode().equals(MonsterMode.FRIGHTENED))
			{
				stateFrame = 4;
				if (isFlashOn)
					stateFrame = 7;
			}
			else if (monster.getMode().equals(MonsterMode.DEAD))
			{
				stateFrame = 10;
			}
			
			g.drawImage(spriteImage, monster.getPosX() - 10 + xOffset, monster.getPosY() - 10 + yOffset, monster.getPosX() + 10 + xOffset, monster.getPosY() + 10 + yOffset, 20 * stateFrame, 20 * row, (20 * stateFrame) + 20, (20 * row) + 20, null);
		}
		else if (state.equals(EntityState.MOVING))
		{
			stateFrame = (int)(monster.timeInState % 12);
			if (stateFrame >= 0 && stateFrame < 4)
				stateFrame = 1;
			else if (stateFrame >= 4 && stateFrame < 8)
				stateFrame = 2;
			else if (stateFrame >= 8 && stateFrame <= 12)
				stateFrame = 3;

			if (monster.getMode().equals(MonsterMode.FRIGHTENED))
			{
				stateFrame += 3;
				if (isFlashOn)
					stateFrame += 3;
			}
			else if (monster.getMode().equals(MonsterMode.DEAD))
			{
				stateFrame += 9;
			}
				
			g.drawImage(spriteImage, monster.getPosX() - 10 + xOffset, monster.getPosY() - 10 + yOffset, monster.getPosX() + 10 + xOffset, monster.getPosY() + 10 + yOffset, 20 * stateFrame, 20 * row, (20 * stateFrame) + 20, (20 * row) + 20, null);
		}
	}
	
	private static long timePacMonDead = -1;

}
