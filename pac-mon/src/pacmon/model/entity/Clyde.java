package pacmon.model.entity;

import pacmon.model.level.Level;
import pacmon.model.maze.Maze;
import pacmon.model.maze.Position;

public class Clyde extends Monster 
{

	public Clyde(Level level)
	{
		super(level);
		scatterPosition = new Position(0, Maze.HEIGHT - 1);		
		homePosition = new Position(15, 14);
		startPosition = new Position(15, 14);	
		regeneratePosition = new Position(15, 14);
	}
	
	@Override
	protected void calcTargetPosition() 
	{
		if (mode.equals(MonsterMode.CHASE))
		{
			PacMon pacMan = level.getPacMon();
			
			int mPosX = posX / Maze.TILE_SIZE;
			int mPosY = posY / Maze.TILE_SIZE;
			
			int pMPosX = pacMan.posX / Maze.TILE_SIZE;
			int pMPosY = pacMan.posY / Maze.TILE_SIZE;
			
			if (Position.distance(mPosX, mPosY, pMPosX, pMPosY) > 8)
			{
				targetPosition.x = pMPosX / Maze.TILE_SIZE;
				targetPosition.y = pMPosY / Maze.TILE_SIZE;
			}
			else
			{
				targetPosition.x = scatterPosition.x;
				targetPosition.y = scatterPosition.y;
			}		
		}
		else
		{
			super.calcTargetPosition();
		}	
	}

}
