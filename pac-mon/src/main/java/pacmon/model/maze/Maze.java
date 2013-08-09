package pacmon.model.maze;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import pacmon.model.entity.Entity;

public class Maze 
{
	
	public static final int WIDTH = 28;
	public static final int HEIGHT = 31;
	
	public static final int TILE_SIZE = 16;
	public static final int TILE_HALF_SIZE = 8;
	
	public Maze(MazeTile[][] tileMatrix, MazeItem[][] itemMatrix, String tileset)
	{
		this.tileMatrix = tileMatrix;
		this.itemMatrix = itemMatrix;
		this.tileset = tileset;

		// Set up the monster house		
		for (int i = 9; i < 19; ++i)
		{
			for (int j = 11; j < 18; ++j)
			{
				tileMatrix[i][j] = MazeTile.GROUND;
			}
		}
		
		for (int i = 10; i < 18; ++i)
		{
			tileMatrix[i][12] = MazeTile.MONSTER_HOUSE_WALL;
			tileMatrix[i][16] = MazeTile.MONSTER_HOUSE_WALL;
		}		
		for (int i = 12; i < 17; ++i)
		{
			tileMatrix[10][i] = MazeTile.MONSTER_HOUSE_WALL;
			tileMatrix[17][i] = MazeTile.MONSTER_HOUSE_WALL;
		}
		for (int i = 11; i < 17; ++i)
		{
			tileMatrix[i][13] = MazeTile.GROUND;
			tileMatrix[i][14] = MazeTile.GROUND;
			tileMatrix[i][15] = MazeTile.GROUND;
		}
		tileMatrix[13][12] = MazeTile.MONSTER_GATE;
		tileMatrix[14][12] = MazeTile.MONSTER_GATE;
		
		// Set up pac man start pos
		for (int i = 12; i < 16; ++i)
		{
			tileMatrix[i][23] = MazeTile.GROUND;
		}
		
		for (int i = 0; i < WIDTH; ++i)
		{
			for (int j = 0; j < HEIGHT; ++j)
			{
				MazeItem item = itemMatrix[i][j];
				if (item != null)
				{
					if (item.equals(MazeItem.Dot))
					{
						++totalStartDots;
					}
					else if (item.equals(MazeItem.Energizer))
					{
						++totalStartEnergizers;
					}
				}
			}
		}	
		
		energizerCount = totalStartEnergizers;
		dotCount = totalStartDots;	
		
		modifiedTiles = new HashSet<Position>();
	}
	
	public Maze(Maze other) {
		this.tileMatrix = new MazeTile[other.tileMatrix.length][other.tileMatrix[0].length];
		for (int i=0; i < other.tileMatrix.length; ++i) {
			this.tileMatrix[i] = Arrays.copyOf(other.tileMatrix[i], other.tileMatrix[i].length);
		}
		
		this.itemMatrix = new MazeItem[other.itemMatrix.length][other.itemMatrix[0].length];
		for (int i=0; i < other.itemMatrix.length; ++i) {
			this.itemMatrix[i] = Arrays.copyOf(other.itemMatrix[i], other.itemMatrix[i].length);
		}
		
		this.totalStartEnergizers = other.totalStartEnergizers;
		this.totalStartDots = other.totalStartDots;
		this.energizerCount = other.energizerCount;
		this.dotCount = other.dotCount;
		this.modifiedTiles = new HashSet<Position>();
		this.tileset = other.tileset;
	}
	
	public MazeTile getMazeTile(int x, int y)
	{
		return tileMatrix[x][y];
	}
	
	public MazeItem getMazeItem(int x, int y)
	{
		return itemMatrix[x][y];
	}
	
	public int getTotalStartEnergizers() 
	{
		return totalStartEnergizers;
	}

	public int getTotalStartDots() 
	{
		return totalStartDots;
	}

	public int getEnergizerCount() 
	{
		return energizerCount;
	}

	public int getDotCount() 
	{
		return dotCount;
	}

	public void placeMazeItem(int x, int y, MazeItem item)
	{
		itemMatrix[x][y] = item;
		setModifiedTile(x, y);
	}
	
	public void clearMazeItem(int x, int y)
	{
		MazeItem item = itemMatrix[x][y]; 
		
		if (item == null)
			return;
		
		if (item.equals(MazeItem.Dot))
		{
			--dotCount;
		}		
		else if (item.equals(MazeItem.Energizer))
		{
			--energizerCount;
		}
		
		itemMatrix[x][y] = null;
		
		setModifiedTile(x, y);
	}
	
	public boolean isTilePassable(Position p, Entity entity)
	{
		return isTilePassable(p.x, p.y, entity);
	}
	
	public boolean isTilePassable(int x, int y, Entity entity)
	{
		if (x > Maze.WIDTH - 1)
		{
			x = x % Maze.WIDTH;
		}
		else if (x < 0)
		{
			x = Maze.WIDTH - 2 - x;
		}
		
		if (y > Maze.HEIGHT - 1)
		{
			y = y % Maze.HEIGHT;
		}
		else if (y < 0)
		{
			y = Maze.HEIGHT - 2 - y;
		}
		
		if (x < 0 || y < 0 || x > Maze.WIDTH - 1 || y > Maze.HEIGHT - 1)
		{
			return false;
		}
		return entity.canPassTile(this.getMazeTile(x, y));
	}
	
	public boolean isComplete()
	{
		return dotCount == 0;
	}
	
	public void setModifiedTile(int x, int y)
	{
		modifiedTiles.add(new Position(x, y));
	}
	
	public Set<Position> getModifiedTilesSet()
	{
		return modifiedTiles;
	}
	
	public void clearModifiedTiles()
	{
		modifiedTiles.clear();
	}

	public String getTileset() 
	{
		return tileset;
	}

	public void setTileset(String tileset) 
	{
		this.tileset = tileset;
	}

	private MazeTile[][] tileMatrix;
	private MazeItem[][] itemMatrix;
	private int totalStartEnergizers;
	private int totalStartDots;
	private int energizerCount;
	private int dotCount;
	private Set<Position> modifiedTiles;
	private String tileset;
	
}
