package pacmon.model.entity;

import pacmon.model.level.Level;
import pacmon.model.maze.Maze;
import pacmon.model.maze.Position;

public class Inky extends Monster 
{

	public Inky(Level level)
	{
		super(level);
		scatterPosition = new Position(Maze.WIDTH - 1, Maze.HEIGHT - 1);			
		homePosition = new Position(11, 14);
		startPosition = new Position(11, 14);
		regeneratePosition = new Position(11, 14);
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
				targetPosition.y -= 2;
			}
			else if (pacMon.direction == Entity.DIRECTION_LEFT)
			{
				targetPosition.x -= 2;
			}
			else if (pacMon.direction == Entity.DIRECTION_DOWN)
			{
				targetPosition.y += 2;
			}
			else if (pacMon.direction == Entity.DIRECTION_RIGHT)
			{
				targetPosition.x += 2;
			}
			
			Blinky blinky = level.getBlinky();
			int blinkyXDiff = (blinky.posX / Maze.TILE_SIZE) - targetPosition.x;  
			int blinkyYDiff = (blinky.posY / Maze.TILE_SIZE) - targetPosition.y;
			
			targetPosition.x += blinkyXDiff * -1;
			targetPosition.y += blinkyYDiff * -1;
		}
		else
		{
			super.calcTargetPosition();
		}
	}

}
