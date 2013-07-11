package pacmon.model.entity;

import pacmon.model.level.Level;
import pacmon.model.maze.Maze;
import pacmon.model.maze.MazeTile;
import pacmon.model.maze.Position;

public class Blinky extends Monster 
{

	public Blinky(Level level) 
	{
		super(level);
		scatterPosition = new Position(Maze.WIDTH - 1, 0);		
		homePosition = new Position(14, 13);
		startPosition = new Position(14, 11);
		regeneratePosition = new Position(13, 14);
		
		elroyLevel = 0;
	}
	
	protected void calcTargetPosition()
	{
		if (mode.equals(MonsterMode.CHASE) || (mode.equals(MonsterMode.SCATTER) && elroyLevel != 0))
		{
			PacMon pacMon = level.getPacMon();		
			targetPosition.x = pacMon.posX / Maze.TILE_SIZE;
			targetPosition.y = pacMon.posY / Maze.TILE_SIZE;
		}
		else
		{
			super.calcTargetPosition();
		}
	}
	
	protected float getSpeed()
	{
		float speed = super.getSpeed();		
		
		Position p = this.getPosition();
		
		if (!mode.equals(MonsterMode.FRIGHTENED) && !level.getState().getMaze().getMazeTile(p.x, p.y).equals(MazeTile.TUNNEL))
		{
			if (elroyLevel == 1)
			{
				speed = level.getState().getElroy1Speed();
			}
			else if (elroyLevel == 2)
			{
				speed = level.getState().getElroy2Speed();
			}
		}
		
		return speed;
	}
	
	public int getElroyLevel() 
	{
		return elroyLevel;
	}

	public void setElroyLevel(int elroyLevel) 
	{
		this.elroyLevel = elroyLevel;
	}

	private int elroyLevel;
	
}
