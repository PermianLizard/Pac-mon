package pacmon.model.entity;

import pacmon.model.level.Level;
import pacmon.model.level.LevelMode;
import pacmon.model.maze.Maze;
import pacmon.model.maze.Position;

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
		return true;//;level.consumeMazeItem(position.x, position.y) == null;	
	}	
	
	protected float getSpeed()
	{
		float speed = 1.0f;
		LevelMode mode = level.getState().getMode();
		Position position = getPosition();
		
		level.consumeMazeItem(position.x, position.y);
		
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
	
}
