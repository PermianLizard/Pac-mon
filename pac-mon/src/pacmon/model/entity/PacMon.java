package pacmon.model.entity;

import pacmon.model.level.Level;
import pacmon.model.level.LevelMode;
import pacmon.model.maze.Maze;
import pacmon.model.maze.MazeItem;
import pacmon.model.maze.Position;
import pacmon.sound.SoundManager;

public class PacMon extends Entity 
{

	public PacMon(Level level)
	{
		super(level);		
		snapToTile = true;
	}
	
	protected boolean beforeMove(Maze maze)
	{
		Blinky blinky = level.getBlinky();
		Pinky pinky = level.getPinky();
		Inky inky = level.getInky();
		Clyde clyde = level.getClyde();
		
		boolean intersectsBlinky = intersectsEntity(blinky);
		boolean intersectsPinky = intersectsEntity(pinky);
		boolean intersectsInky = intersectsEntity(inky);
		boolean intersectsClyde = intersectsEntity(clyde);
		
		if (intersectsBlinky || intersectsPinky || intersectsInky || intersectsClyde)
		{
			if (intersectsBlinky)
			{
				if (blinky.getMode().equals(MonsterMode.FRIGHTENED))
				{
					blinky.startDeadMode();
					level.incrementFrightenedMostersEaten(blinky);					
				}
				else if (blinky.getMode().equals(MonsterMode.CHASE) || blinky.getMode().equals(MonsterMode.SCATTER))
				{
					level.setPacMonDead(true);
				}
			}
			if (intersectsPinky )
			{		
				if (pinky.getMode().equals(MonsterMode.FRIGHTENED))
				{					
					pinky.startDeadMode();
					level.incrementFrightenedMostersEaten(pinky);
				}
				else if (pinky.getMode().equals(MonsterMode.CHASE) || pinky.getMode().equals(MonsterMode.SCATTER))
				{
					level.setPacMonDead(true);
				}
			}				
			if (intersectsInky)
			{
				if (inky.getMode().equals(MonsterMode.FRIGHTENED))
				{
					inky.startDeadMode();
					level.incrementFrightenedMostersEaten(inky);
				}
				else if (inky.getMode().equals(MonsterMode.CHASE) || inky.getMode().equals(MonsterMode.SCATTER))
				{
					level.setPacMonDead(true);

				}
			}
			if (intersectsClyde)
			{
				if (clyde.getMode().equals(MonsterMode.FRIGHTENED))
				{
					clyde.startDeadMode();
					level.incrementFrightenedMostersEaten(clyde);
				}
				else if (clyde.getMode().equals(MonsterMode.CHASE) || clyde.getMode().equals(MonsterMode.SCATTER))
				{
					level.setPacMonDead(true);
				}
			}
		}
		
		//Position position = getPosition();
		return true;//;level.fMazeItem(position.x, position.y) == null;	
	}	
	
	public void move(Maze maze) 
	{
		super.move(maze);
	}
	
	protected float getSpeed()
	{
		float speed = 1.0f;
		LevelMode mode = level.getState().getMode();
		
		
		//MazeItem item = level.getState().getMaze().getMazeItem(position.x, position.y);
		
		if (mode.equals(LevelMode.FRIGHTENED))
		{
			speed = level.getState().getPacManFrightSpeed();
		}
		else
		{
			speed = level.getState().getPacManNormalSpeed();
		}
		
		return speed;
	}
	
	protected void onEnterTile() 
	{
		//System.out.println("entered a new tile");
		
		Position position = getPosition();
		//MazeItem item = level.getState().getMaze().getMazeItem(position.x, position.y);
		MazeItem item = level.consumeMazeItem(position.x, position.y);
		
		if (item != null)
		{
			if (item.equals(MazeItem.Dot))
			{
				//if (!SoundManager.getInstance().isPlaying(SoundManager.CHOMP))
				SoundManager.getInstance().play(SoundManager.CHOMP, false);
			}
			else if (item.equals(MazeItem.Energizer))
			{
				
			}
			else if (item.isBonus())
			{
				SoundManager.getInstance().play(SoundManager.EAT_FRUIT, false);
			}
		}
		else 
		{
			SoundManager.getInstance().stop(SoundManager.CHOMP);
		}
	}
	
	protected void onMovementBlocked(int currentTileX, int currentTileY)
	{
		//if (SoundManager.getInstance().isPlaying(SoundManager.CHOMP))
			//SoundManager.getInstance().stop(SoundManager.CHOMP);
	}
	
}
