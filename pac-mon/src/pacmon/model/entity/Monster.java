package pacmon.model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pacmon.model.level.Level;
import pacmon.model.level.LevelMode;
import pacmon.model.maze.Maze;
import pacmon.model.maze.MazeTile;
import pacmon.model.maze.Position;
import pacmon.sound.SoundManager;

public abstract class Monster extends Entity 
{

	public Monster(Level level)
	{
		super(level);
		
		targetPosition = new Position(0, 0);
		scatterPosition = new Position(0, 0);
		homePosition = new Position(0, 0);
		startPosition = new Position(0, 0);
		regeneratePosition= new Position(0, 0);
		
		mode = MonsterMode.NONE;
	}
	
	public Position getTargetPosition()
	{
		return targetPosition;
	}	
	
	public Position getScatterPosition()
	{
		return scatterPosition;
	}
	
	public Position getHomePosition()
	{
		return homePosition;
	}
	
	public Position getStartPosition()
	{
		return startPosition;
	}
	
	public void setMode(MonsterMode mode)
	{
		this.mode = mode;
		
		if (this.mode.equals(MonsterMode.HOME))
		{
			this.setSnapToTile(false);
		}
		/*if (this.mode.equals(MonsterMode.EXIT_HOME))
		{
			this.direction = Entity.DIRECTION_UP;
		}*/
		else
		{
			this.setSnapToTile(true);
		}
	}
	
	public MonsterMode getMode() 
	{
		return mode;
	}

	public void startDeadMode()
	{
		setMode(MonsterMode.DEAD);
		
		SoundManager.getInstance().play(SoundManager.EAT_GHOST, false);
	}
	
	protected void decideDirection(int currentTileX, int currentTileY, Maze maze)
	{
		Position upPosition = new Position(currentTileX, currentTileY - 1);
		Position leftPosition = new Position(currentTileX - 1, currentTileY);
		Position downPosition = new Position(currentTileX, currentTileY + 1);
		Position rightPosition = new Position(currentTileX + 1, currentTileY);
		
		boolean upPassable = maze.isTilePassable(upPosition, this); 
		boolean leftPassable = maze.isTilePassable(leftPosition, this);
		boolean downPassable = maze.isTilePassable(downPosition, this);
		boolean rightPassable = maze.isTilePassable(rightPosition, this);		
		
		int availableDirectionsCount = 0;
		if (upPassable)
			++availableDirectionsCount;
		if (leftPassable)
			++availableDirectionsCount;
		if (downPassable)
			++availableDirectionsCount;
		if (rightPassable)
			++availableDirectionsCount;			
			
		if (mode.equals(MonsterMode.HOME))
		{
			if (direction == Entity.DIRECTION_UP && !upPassable)
			{
				//reverseDirection();
				direction = Entity.DIRECTION_DOWN;
			}
			else if (direction == Entity.DIRECTION_LEFT && !leftPassable)
			{
				//reverseDirection();
				direction = Entity.DIRECTION_UP;
			}
			else if (direction == Entity.DIRECTION_DOWN && !downPassable)
			{
				//reverseDirection();
				direction = Entity.DIRECTION_UP;
			}
			else if (direction == Entity.DIRECTION_RIGHT && !rightPassable)
			{
				//reverseDirection();
				direction = Entity.DIRECTION_DOWN;
			}
		}
		else
		{				
			if (availableDirectionsCount == 1)
			{
				reverseDirection();
			}
			else
			{			
				List<Byte> availableDirectionsPrecedenceList = new ArrayList<Byte>(3);
								
				if (direction == Entity.DIRECTION_UP)
				{				
					if (upPassable)
					{
						availableDirectionsPrecedenceList.add(Entity.DIRECTION_UP);
					}
					if (leftPassable)
					{
						availableDirectionsPrecedenceList.add(Entity.DIRECTION_LEFT);
					}
					if (rightPassable)
					{
						availableDirectionsPrecedenceList.add(Entity.DIRECTION_RIGHT);
					}
				}
				else if (direction == Entity.DIRECTION_LEFT)
				{
					if (upPassable)
					{
						availableDirectionsPrecedenceList.add(Entity.DIRECTION_UP);
					}
					if (leftPassable)
					{
						availableDirectionsPrecedenceList.add(Entity.DIRECTION_LEFT);
					}
					if (downPassable)
					{
						availableDirectionsPrecedenceList.add(Entity.DIRECTION_DOWN);
					}
				}
				else if (direction == Entity.DIRECTION_DOWN)
				{
					if (leftPassable)
					{
						availableDirectionsPrecedenceList.add(Entity.DIRECTION_LEFT);
					}
					if (downPassable)
					{
						availableDirectionsPrecedenceList.add(Entity.DIRECTION_DOWN);
					}
					if (rightPassable)
					{
						availableDirectionsPrecedenceList.add(Entity.DIRECTION_RIGHT);
					}
				}
				else if (direction == Entity.DIRECTION_RIGHT)
				{
					if (upPassable)
					{
						availableDirectionsPrecedenceList.add(Entity.DIRECTION_UP);
					}				
					if (downPassable)
					{
						availableDirectionsPrecedenceList.add(Entity.DIRECTION_DOWN);
					}
					if (rightPassable)
					{
						availableDirectionsPrecedenceList.add(Entity.DIRECTION_RIGHT);
					}
				}
				
				if (this.mode.equals(MonsterMode.FRIGHTENED))
				{
					Random rand = new Random();				
					direction = availableDirectionsPrecedenceList.get(rand.nextInt(availableDirectionsPrecedenceList.size()));
				}
				else
				{			
					int shortestDistance = Integer.MAX_VALUE;
					byte shortestDistanceDirection = Entity.DIRECTION_UP;
					
					for (byte dir : availableDirectionsPrecedenceList)
					{
						Position dirPos = null;				
						if (dir == Entity.DIRECTION_UP)
						{
							dirPos = upPosition;
						}
						else if (dir == Entity.DIRECTION_LEFT)
						{
							dirPos = leftPosition;
						}
						else if (dir == Entity.DIRECTION_DOWN)
						{
							dirPos = downPosition;
						}
						else if (dir == Entity.DIRECTION_RIGHT)
						{
							dirPos = rightPosition;
						}
						
						int distance = Position.distance(dirPos, targetPosition);
						
						if (distance < shortestDistance)
						{
							shortestDistance = distance;
							shortestDistanceDirection = dir;
						}
					}
					
					direction = shortestDistanceDirection;
				}
			}
		}		
	}
	
	protected void onHalfTilePassed(int currentTileX, int currentTileY, Maze maze)
	{
		decideDirection(currentTileX, currentTileY, maze);
	}
	
	protected boolean beforeMove(Maze maze)
	{
		calcTargetPosition();		
		return true;
	}
	
	protected float getSpeed()
	{
		float speed = 1.0f;
		Position p = this.getPosition();
		if (level.getState().getMaze().getMazeTile(p.x, p.y).equals(MazeTile.TUNNEL))
		{
			speed = level.getState().getMonsterTunnelSpeed();
		}		
		else if (!mode.equals(MonsterMode.FRIGHTENED))
		{
			speed = level.getState().getMonsterNormalSpeed();
		}
		else
		{
			speed = level.getState().getMonsterFrightSpeed();
		}
		
		return speed;
	}
	
	protected void onMovementBlocked(int currentTileX, int currentTileY)
	{
		// need to make choices here
	}
	
	public boolean canPassTile(MazeTile tile)
	{
		if (mode.equals(MonsterMode.EXIT_HOME) && tile.equals(MazeTile.MONSTER_GATE))
		{
			return true;
		}
		
		if (mode.equals(MonsterMode.DEAD) && tile.equals(MazeTile.MONSTER_GATE))
		{
			return true;
		}
		
		return super.canPassTile(tile);
	}
	
	protected void calcTargetPosition()
	{
		if (mode.equals(MonsterMode.EXIT_HOME))
		{
			Position pos = this.getPosition();			
			
			targetPosition.x = 14;
			targetPosition.y = 11;
			
			if (pos.x == 14 && pos.y == 11)
			{
				if (level.getState().getMode().equals(LevelMode.SCATTER))
				{
					this.setMode(MonsterMode.SCATTER);	
				}
				else if (level.getState().getMode().equals(LevelMode.CHASE))
				{
					this.setMode(MonsterMode.CHASE);	
				}				
				else if (level.getState().getMode().equals(LevelMode.FRIGHTENED))
				{
					this.setMode(MonsterMode.CHASE);	
				}
				
				direction = Entity.DIRECTION_LEFT;
			}
		}
		else if (mode.equals(MonsterMode.DEAD)) // move to regeneration point
		{
			Position pos = this.getPosition();			
			
			targetPosition.x = regeneratePosition.x; //14
			targetPosition.y = regeneratePosition.y; //14
			
			if (pos.x == regeneratePosition.x && pos.y == regeneratePosition.y)
			{
				posX = (regeneratePosition.x * Maze.TILE_SIZE) + Maze.TILE_SIZE;
				direction = Entity.DIRECTION_UP;
				
				setMode(MonsterMode.HOME);
			}
		}
		else if (mode.equals(MonsterMode.SCATTER))
		{
			targetPosition.x = scatterPosition.x;
			targetPosition.y = scatterPosition.y;
		}
		else if (mode.equals(MonsterMode.FRIGHTENED))
		{
			targetPosition.x = 0;
			targetPosition.y = 0;
		}
	}
		
	protected Position targetPosition;
	protected Position scatterPosition;	
	protected Position homePosition;
	protected Position startPosition;
	protected Position regeneratePosition;
	
	protected MonsterMode mode;
	
}
