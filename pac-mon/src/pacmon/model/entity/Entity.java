package pacmon.model.entity;

import pacmon.model.level.Level;
import pacmon.model.maze.Maze;
import pacmon.model.maze.MazeTile;
import pacmon.model.maze.Position;

public abstract class Entity 
{

	public static byte DIRECTION_UP = 0;
	public static byte DIRECTION_LEFT = 1;
	public static byte DIRECTION_DOWN = 2;
	public static byte DIRECTION_RIGHT = 3;
	public static byte DIRECTION_NONE = 4;
	
	protected static int MOVE_RATE = 2;
	
	public Entity(Level level)
	{
		this.level = level;
		this.direction = DIRECTION_LEFT;
		this.state = EntityState.STILL;
		this.timeInState = 0;		
		this.halfTilePassed = false;
		this.snapToTile = true;
		
		lastTileX = -1;
		lastTileY = -1;
	}
	
	/*
	 * Sets the entity position in absolute, not tile coordinates
	 */
	public void setPosition(int x, int y)
	{
		this.posX = x;
		this.posY = y;
	}
	
	public int getPosX()
	{
		return this.posX;
	}
	
	public int getPosY()
	{
		return this.posY;
	}
	
	/*
	 * Sets the direction in which the entity is facing
	 */
	public void setDirection(byte direction)
	{
		this.direction = direction;
	}	
	
	public EntityState getState()
	{
		return this.state;
	}
	
	public Level getLevel() 
	{
		return level;
	}

	public void setState(EntityState state)
	{
		this.state = state;
	}
	
	public Position getPosition()
	{
		return new Position(posX / Maze.TILE_SIZE, posY / Maze.TILE_SIZE);
	}	
	
	public byte getDirection() 
	{
		return direction;
	}

	public boolean isSnapToTile() 
	{
		return snapToTile;
	}

	/*
	 * If true, the entity will be forced to align to the Maze's grid
	 */
	public void setSnapToTile(boolean snapToTile) 
	{
		this.snapToTile = snapToTile;
	}
	
	public Position getNextPosition()
	{
		Position position = getPosition();
		
		if (direction == DIRECTION_UP)
		{
			position.y -= 1;
		}
		else if (direction == DIRECTION_LEFT)
		{
			position.x -= 1;
		}
		else if (direction == DIRECTION_DOWN)
		{
			position.y += 1;
		}
		else if (direction == DIRECTION_RIGHT)
		{
			position.x += 1;
		}
		
		return position;
	}	
	
	public Position getAdjacentPosition(byte direction)
	{
		Position position = getPosition();
		if (direction == DIRECTION_UP)
		{
			position.y -= 1;
		}
		else if (direction == DIRECTION_LEFT)
		{
			position.x -= 1;
		}
		else if (direction == DIRECTION_DOWN)
		{
			position.y += 1;
		}
		else if (direction == DIRECTION_RIGHT)
		{
			position.x += 1;
		}
		
		return position;
	}
	
	public final void advance(Maze maze)
	{
		if (beforeMove(maze))
		{
			move(maze);
		}		
	}	
	
	public boolean canPassTile(MazeTile tile)
	{
		return tile.equals(MazeTile.GROUND) || tile.equals(MazeTile.TUNNEL);
	}
	
	public void attemptReverseDirection(Maze maze)
	{
		byte[] nextDirections = new byte[3];
		
		if (direction == Entity.DIRECTION_UP)
		{
			nextDirections[0] = Entity.DIRECTION_DOWN;
			nextDirections[1] = Entity.DIRECTION_LEFT;
			nextDirections[2] = Entity.DIRECTION_RIGHT;
		}
		else if (direction == Entity.DIRECTION_LEFT)
		{
			nextDirections[0] = Entity.DIRECTION_RIGHT;
			nextDirections[1] = Entity.DIRECTION_UP;
			nextDirections[2] = Entity.DIRECTION_DOWN;
		}
		else if (direction == Entity.DIRECTION_DOWN)
		{
			nextDirections[0] = Entity.DIRECTION_UP;
			nextDirections[1] = Entity.DIRECTION_LEFT;
			nextDirections[2] = Entity.DIRECTION_RIGHT;
		}
		else if (direction == Entity.DIRECTION_RIGHT)
		{
			nextDirections[0] = Entity.DIRECTION_LEFT;
			nextDirections[1] = Entity.DIRECTION_UP;
			nextDirections[2] = Entity.DIRECTION_DOWN;
		}
		
		Position currPos = getPosition();
		for (byte dir : nextDirections)
		{
			if (dir == Entity.DIRECTION_UP && maze.isTilePassable(currPos.x, currPos.y - 1, this))
			{
				direction = Entity.DIRECTION_UP;
				break;
			}
			else if (dir == Entity.DIRECTION_LEFT && maze.isTilePassable(currPos.x - 1, currPos.y, this))
			{
				direction = Entity.DIRECTION_LEFT;
				break;
			}
			else if (dir == Entity.DIRECTION_DOWN && maze.isTilePassable(currPos.x, currPos.y + 1, this))
			{
				direction = Entity.DIRECTION_DOWN;
				break;
			}
			else if (dir == Entity.DIRECTION_RIGHT && maze.isTilePassable(currPos.x + 1, currPos.y, this))
			{
				direction = Entity.DIRECTION_RIGHT;
				break;
			}
		}
	}
	
	public boolean intersectsEntity(Entity otherEntity)
	{
		if (this == otherEntity)
			return true;	
		
		int thisTileX = posX / Maze.TILE_SIZE;
		int thisTileY = posY / Maze.TILE_SIZE;
		
		int otherTileX = otherEntity.posX / Maze.TILE_SIZE;
		int otherTileY = otherEntity.posY / Maze.TILE_SIZE;
		
		if (thisTileX == otherTileX && thisTileY == otherTileY)
			return true;
		
		return false;
	}
	
	protected boolean beforeMove(Maze maze)
	{
		return true;
	}
	
	protected float getSpeed()
	{
		return 1.0f;
	}
	
	protected void onMovementBlocked(int currentTileX, int currentTileY)
	{
		
	}
	
	protected void onHalfTilePassed(int currentTileX, int currentTileY, Maze maze)
	{
		
	}
	
	protected void reverseDirection()
	{
		if (direction == Entity.DIRECTION_UP)
		{
			direction = Entity.DIRECTION_DOWN;
		}			
		else if (direction == Entity.DIRECTION_LEFT)
		{
			direction = Entity.DIRECTION_RIGHT;
		}				
		else if (direction == Entity.DIRECTION_DOWN)
		{
			direction = Entity.DIRECTION_UP;
		}					
		else if (direction == Entity.DIRECTION_RIGHT)
		{
			direction = Entity.DIRECTION_LEFT;
		}
	}
	
	protected void move(Maze maze)
	{
		EntityState stateAtStart = getState();
		
		boolean move = direction != DIRECTION_NONE;
		
		float speed = getSpeed();		
		
		if (speed < 1.0 && speed > 0.0f)
		{
			slownessAccum += (1.0f - speed);
			if (slownessAccum >= 1.0f)
			{
				slownessAccum -= 1.0f;
				move = false;
			}
		}
		
		if (move)
		{
			boolean localHalfTilePassed = halfTilePassed;
			
			int tileX = posX / Maze.TILE_SIZE;
			int tileY = posY / Maze.TILE_SIZE;
			
			if (tileX != lastTileX || tileY != lastTileY)
				this.onEnterTile();
			
			lastTileX = tileX;
			lastTileY = tileY;
			
			int nextTileX = tileX;
			int nextTileY = tileY;
			
			int moveX = 0;
			int moveY = 0;
			
			if (direction == DIRECTION_UP)
			{
				nextTileY -= 1;
			}
			else if (direction == DIRECTION_LEFT)
			{
				nextTileX -= 1;
			}
			else if (direction == DIRECTION_DOWN)
			{
				nextTileY += 1;
			}
			else if (direction == DIRECTION_RIGHT)
			{
				nextTileX += 1;
			}
			
			if (nextTileX < 0)
			{
				nextTileX = Maze.WIDTH - 1;
			}
			if (nextTileY < 0)
			{
				nextTileY = Maze.HEIGHT - 1;
			}
			
			if (nextTileX > Maze.WIDTH - 1)
			{
				nextTileX = 0;
			}
			if (nextTileY > Maze.HEIGHT - 1)
			{
				nextTileY = 0;
			}
			
			boolean nextTilePassable= maze.isTilePassable(nextTileX,nextTileY, this);
			
			int tileCenterX = tileX * Maze.TILE_SIZE + Maze.TILE_HALF_SIZE;
			int tileCenterY = tileY * Maze.TILE_SIZE + Maze.TILE_HALF_SIZE;	
			
			if (direction == DIRECTION_LEFT)
			{
				if (posX - MOVE_RATE > tileCenterX)
				{
					if (!nextTilePassable)
						moveX -= MOVE_RATE;
					
					if (!localHalfTilePassed)
					{							
						localHalfTilePassed = true;
					}
				}
			}
			else if (direction == DIRECTION_RIGHT)
			{
				if (posX + MOVE_RATE < tileCenterX)
				{
					if (!nextTilePassable)
						moveX += MOVE_RATE;
					
					if (!localHalfTilePassed)
					{							
						localHalfTilePassed = true;
					}
				}
			}
			
			if (direction == DIRECTION_UP)
			{
				if (posY - MOVE_RATE > tileCenterY)
				{
					if (!nextTilePassable)
						moveY -= MOVE_RATE;
					
					if (!localHalfTilePassed)
					{							
						localHalfTilePassed = true;
					}
				}
			}
			else if (direction == DIRECTION_DOWN)
			{
				if (posY + MOVE_RATE < tileCenterY)
				{
					if (!nextTilePassable)
						moveY += MOVE_RATE;
					
					if (!localHalfTilePassed)
					{							
						localHalfTilePassed = true;							
					}						
				}
			}
			
			onMovementBlocked(tileX, tileY);
				
			if (nextTilePassable)
			{
				if (direction == DIRECTION_UP)
				{
					moveY -= MOVE_RATE;
				}
				else if (direction == DIRECTION_LEFT)
				{
					moveX -= MOVE_RATE;
				}
				else if (direction == DIRECTION_DOWN)
				{
					moveY += MOVE_RATE;
				}
				else if (direction == DIRECTION_RIGHT)
				{
					moveX += MOVE_RATE;
				}
			}
			
			if (snapToTile)
			{
				if (direction == DIRECTION_UP || direction == DIRECTION_DOWN)
				{
					int xDiff = tileCenterX - posX;			
					if (xDiff > 0)
					{
						moveX += MOVE_RATE;
					}
					else if (xDiff < 0)
					{
						moveX -= MOVE_RATE;
					}
				}
				else if (direction == DIRECTION_LEFT || direction == DIRECTION_RIGHT)
				{
					int yDiff = tileCenterY - posY;			
					if (yDiff > 0)
					{
						moveY += MOVE_RATE;
					}
					else if (yDiff < 0)
					{
						moveY -= MOVE_RATE;
					}
				}
			}
			
			if (moveX != 0 || moveY != 0)
			{
				if  (!getState().equals(EntityState.MOVING))
						setState(EntityState.MOVING);
			}
			else if ((moveX == 0 && moveY == 0) && !getState().equals(EntityState.STILL))
			{
				setState(EntityState.STILL);
			}
			
			posX += moveX;
			posY += moveY;		
			
			if (posX < 0)
			{
				posX = Maze.WIDTH * Maze.TILE_SIZE - Maze.TILE_HALF_SIZE;
			}
			if (posY < 0)
			{
				posY = Maze.HEIGHT * Maze.TILE_SIZE - Maze.TILE_HALF_SIZE;
			}
			
			if (posX > Maze.WIDTH * Maze.TILE_SIZE - 1)
			{
				posX = Maze.TILE_HALF_SIZE;
			}
			if (posY > Maze.HEIGHT * Maze.TILE_SIZE - 1)
			{
				posY = Maze.TILE_HALF_SIZE;
			}			
			
			int newTileX = posX / Maze.TILE_SIZE;
			int newTileY = posY / Maze.TILE_SIZE;
			
			if (tileX != newTileX || tileY != newTileY)
			{
				halfTilePassed = false;				
			}			
			else if (localHalfTilePassed)
			{
				if (!halfTilePassed)
				{
					onHalfTilePassed(tileX, tileY, maze);
					halfTilePassed = localHalfTilePassed;
				}
			}
			
		}
		
		/*else
		{
			if (!state.equals(EntityState.STILL))
			{
				state = EntityState.STILL;
			}
		}*/
		
		if (!stateAtStart.equals(getState()))
		{
			timeInState = 0;
		}
		else
		{
			++timeInState;
		}
	}
	
	protected void onEnterTile() 
	{
		
	}
	
	public long timeInState;
	
	protected Level level;	
	protected int posX;
	protected int posY;
	protected byte direction;	
	protected boolean snapToTile;
	
	protected int lastTileX;
	protected int lastTileY;

	private EntityState state;
	private float slownessAccum;
	private boolean halfTilePassed;
}
