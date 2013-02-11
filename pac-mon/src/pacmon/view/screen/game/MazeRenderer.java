package pacmon.view.screen.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.BitSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import pacmon.GraphicsManager;
import pacmon.SpriteManager;
import pacmon.model.maze.Maze;
import pacmon.model.maze.MazeItem;
import pacmon.model.maze.MazeTile;
import pacmon.model.maze.Position;

public class MazeRenderer 
{
	
	public static void cleanup() 
	{
		mazeImageCacheMap = null;
		tilesetImage = null;
	}
	
	public static void renderMaze(Graphics2D g, Maze maze, int xOffset, int yOffset)
	{
		BufferedImage image = null;		
		if (mazeImageCacheMap != null)
		{
			if (!mazeImageCacheMap.containsKey(maze) && mazeImageCacheMap.size() > 0)
			{
				mazeImageCacheMap = null;
			}
			else
			{
				image = mazeImageCacheMap.get(maze);
			}
		}
		
		if (image == null)
		{
			// get the tileset
			if (tilesetImage == null)
				tilesetImage = GraphicsManager.getTileset(GraphicsManager.TILESET_DEFAULT);	
			
			image = new BufferedImage(Maze.WIDTH*Maze.TILE_SIZE, Maze.HEIGHT*Maze.TILE_SIZE, BufferedImage.TYPE_INT_RGB);			
			Graphics2D gl = image.createGraphics();
			
			for (int i = 0; i < Maze.WIDTH; ++i)
			{
				for (int j = 0; j < Maze.HEIGHT; ++j)
				{
					renderTile(maze, i, j, gl, tilesetImage);					
				}
			}		
			
			gl.dispose();			
			cacheMazeImage(maze, image);		
		}
		else
		{
			Set<Position> modifiedTiles = maze.getModifiedTilesSet();
			
			if (modifiedTiles.size() > 0)
			{
				if (tilesetImage == null)
					tilesetImage = GraphicsManager.getTileset(GraphicsManager.TILESET_DEFAULT);
				
				Graphics2D gl = image.createGraphics();
				
				for (Position p : modifiedTiles)
				{
					MazeRenderer.renderTile(maze, p.x, p.y, gl, tilesetImage);
				}
			}
		}
		
		g.drawImage(image, xOffset, yOffset, null);
	}
	
	private static void renderTile(Maze maze, int x, int y, Graphics2D gl, BufferedImage tilesetImage)
	{
		int refX = x * Maze.TILE_SIZE;
		int refY = y * Maze.TILE_SIZE;
		
		if (maze.getMazeTile(x, y).equals(MazeTile.GROUND))
		{
			gl.setColor(Color.BLACK);
			gl.fillRect(refX, refY, Maze.TILE_SIZE, Maze.TILE_SIZE);						
		}
		if (maze.getMazeTile(x, y).equals(MazeTile.TUNNEL))
		{
			gl.setColor(Color.BLACK);
			gl.fillRect(refX, refY, Maze.TILE_SIZE, Maze.TILE_SIZE);						
		}
		else if (maze.getMazeTile(x, y).equals(MazeTile.WALL) || maze.getMazeTile(x, y).equals(MazeTile.MONSTER_HOUSE_WALL))
		{
			boolean monsterHouse = maze.getMazeTile(x, y).equals(MazeTile.MONSTER_HOUSE_WALL);
			
			MazeTile tileUp = MazeTile.WALL;						
			MazeTile tileLeft = MazeTile.WALL;
			MazeTile tileDown = MazeTile.WALL;
			MazeTile tileRight = MazeTile.WALL;
			
			MazeTile tileUpLeft = MazeTile.WALL;						
			MazeTile tileDownLeft = MazeTile.WALL;
			MazeTile tileDownRight = MazeTile.WALL;
			MazeTile tileUpRight = MazeTile.WALL;

			boolean boundaryUp = false;
			boolean boundaryLeft = false;
			boolean boundaryDown = false;
			boolean boundaryRight = false;
			
			if (y == 0)
				boundaryUp = true;

			if (x == 0)
				boundaryLeft = true;						
			
			if (y == Maze.HEIGHT - 1)
				boundaryDown = true;						
			
			if (x == Maze.WIDTH - 1)
				boundaryRight = true;												
			
			// cardinal
			if (!boundaryUp)							
				tileUp = maze.getMazeTile(x, y - 1);
			
			if (!boundaryLeft)
				tileLeft = maze.getMazeTile(x - 1, y);
			
			if (!boundaryDown)
				tileDown =  maze.getMazeTile(x, y + 1);
			
			if (!boundaryRight)
				tileRight = maze.getMazeTile(x + 1, y);
			
			// corners
			if (!boundaryUp && !boundaryLeft)
				tileUpLeft = maze.getMazeTile(x - 1, y - 1);
			
			if (!boundaryDown && !boundaryLeft)
				tileDownLeft = maze.getMazeTile(x - 1, y + 1);
			
			if (!boundaryDown && !boundaryRight)
				tileDownRight = maze.getMazeTile(x + 1, y + 1);
			
			if (!boundaryUp && !boundaryRight)
				tileUpRight = maze.getMazeTile(x + 1, y - 1);
			
			BitSet nSet = new BitSet(8);
						
			if (!monsterHouse)
			{
				nSet.set(0, !tileUpRight.equals(MazeTile.WALL));
				nSet.set(1, tileRight.equals(MazeTile.WALL));
				nSet.set(2, !tileDownRight.equals(MazeTile.WALL));
				nSet.set(3, tileDown.equals(MazeTile.WALL));
				nSet.set(4, !tileDownLeft.equals(MazeTile.WALL));
				nSet.set(5, tileLeft.equals(MazeTile.WALL));
				nSet.set(6, !tileUpLeft.equals(MazeTile.WALL));
				nSet.set(7, tileUp.equals(MazeTile.WALL));
			}
			else
			{
				nSet.set(0, !tileUpRight.equals(MazeTile.MONSTER_HOUSE_WALL));
				nSet.set(1, tileRight.equals(MazeTile.MONSTER_HOUSE_WALL));
				nSet.set(2, !tileDownRight.equals(MazeTile.MONSTER_HOUSE_WALL));
				nSet.set(3, tileDown.equals(MazeTile.MONSTER_HOUSE_WALL));
				nSet.set(4, !tileDownLeft.equals(MazeTile.MONSTER_HOUSE_WALL));
				nSet.set(5, tileLeft.equals(MazeTile.MONSTER_HOUSE_WALL));
				nSet.set(6, !tileUpLeft.equals(MazeTile.MONSTER_HOUSE_WALL));
				nSet.set(7, tileUp.equals(MazeTile.MONSTER_HOUSE_WALL));
			}
			
			int res = 0, pow = 1;
	        for ( int p = 0 ; p < 32 ; p++ , pow <<= 1 ) 
	        {
	            if ( nSet.get( p ) ) 
	            {
	                res |= pow;
	            }
	        }
			int col = res % 16;  
			int row = res / 16;
			
			gl.drawImage(tilesetImage, refX, refY, refX+Maze.TILE_SIZE, refY+Maze.TILE_SIZE, Maze.TILE_SIZE * col, Maze.TILE_SIZE * row, (Maze.TILE_SIZE * col) + Maze.TILE_SIZE, (Maze.TILE_SIZE * row) + Maze.TILE_SIZE, null);						
		}	
		/*else if (maze.getMazeTile(x, y).equals(MazeTile.MONSTER_HOUSE_WALL))
		{
			gl.setColor(Color.ORANGE);
			gl.fillRect(refX, refY, Maze.TILE_SIZE, Maze.TILE_SIZE); 
		}*/
		else if (maze.getMazeTile(x, y).equals(MazeTile.MONSTER_GATE))
		{
			gl.setColor(Color.PINK);
			gl.fillRect(refX, refY, Maze.TILE_SIZE, Maze.TILE_SIZE); 
		}
		
		// item
		int refCenterX = refX + Maze.TILE_SIZE / 2;
		int refCenterY = refY + Maze.TILE_SIZE / 2;
		
		MazeItem item = maze.getMazeItem(x, y);
		if (item != null)
		{
			BufferedImage consumablesSpriteImage = SpriteManager.getConsumablesSpriteImage();
			
			switch (item)
			{
				case Dot :
					
					//gl.setColor(Color.WHITE);
					//gl.fillRect(refCenterX - 2, refCenterY - 2, 4, 4);
					
					
					gl.drawImage(consumablesSpriteImage, refX, refY, refX + 16, refY + 16, 0, 0, 16, 16, null);
					
					break;
				case Energizer :
					//gl.setColor(Color.WHITE);
					//gl.fillOval(refX + 2, refY + 2, Maze.TILE_SIZE - 4, Maze.TILE_SIZE - 4);

					gl.drawImage(consumablesSpriteImage, refX, refY, refX + 16, refY + 16, 16, 0, 32, 16, null);
					
					break;
			default:
				break;						
			}	
			
			BufferedImage bonusSpriteImage = SpriteManager.getBonusSpriteImage();
			if (item.isBonus())
			{
				int frame = 0;
				
				switch(item)
				{
				case BonusCherries : frame = 0; break;
				case BonusStrawberry : frame = 1; break;
				case BonusPeach : frame = 2; break;
				case BonusApple : frame = 3; break;
				case BonusMelon : frame = 4; break;
				case BonusGalaxian : frame = 5; break;
				case BonusBell : frame = 6; break;
				case BonusKey : frame = 7; break;
				default:
					break;
				}
				
				gl.drawImage(bonusSpriteImage, refCenterX - 8, refCenterY - 8, refCenterX + 8, refCenterY + 8, 16 * frame, 0,16 * frame + 16, 16, null);
				
				//gl.setColor(Color.RED);
				//gl.fillRect(refCenterX - 3, refCenterY - 3, 6, 6);
			}
		}
	}
	
	private static void cacheMazeImage(Maze maze, BufferedImage image)
	{
		if (mazeImageCacheMap == null)
		{
			mazeImageCacheMap = new ConcurrentHashMap<Maze,BufferedImage>();
		}
		else if (mazeImageCacheMap.size() > 0)
		{
			if (!mazeImageCacheMap.containsKey(maze))
			{
				mazeImageCacheMap.clear();
			}
		}
		mazeImageCacheMap.put(maze, image);
	}
	
	private static Map<Maze,BufferedImage> mazeImageCacheMap;
	private static BufferedImage tilesetImage;

}
