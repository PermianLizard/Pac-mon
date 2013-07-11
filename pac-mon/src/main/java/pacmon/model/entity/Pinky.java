package pacmon.model.entity;

import pacmon.model.level.Level;
import pacmon.model.maze.Maze;
import pacmon.model.maze.Position;

public class Pinky extends Monster 
{

	public Pinky(Level level)
	{
		super(level);
		
		scatterPosition = new Position(0, 0);		
		homePosition = new Position(13, 14);
		startPosition = new Position(13, 14);
		regeneratePosition = new Position(13, 14);
	}	
	
	@Override
	protected void calcTargetPosition() 
	{		
		if (mode.equals(MonsterMode.CHASE))
		{
			PacMon pacMon = level.getPacMon();		
			targetPosition.x = pacMon.posX / Maze.TILE_SIZE;
			targetPosition.y = pacMon.posY / Maze.TILE_SIZE;
			
			if (pacMon.direction == Entity.DIRECTION_UP)
			{
				targetPosition.y -= 4;
			}
			else if (pacMon.direction == Entity.DIRECTION_LEFT)
			{
				targetPosition.x -= 4;
			}
			else if (pacMon.direction == Entity.DIRECTION_DOWN)
			{
				targetPosition.y += 4;
			}
			else if (pacMon.direction == Entity.DIRECTION_RIGHT)
			{
				targetPosition.x += 4;
			}
		}
		else
		{
			super.calcTargetPosition();
		}
	}

}
