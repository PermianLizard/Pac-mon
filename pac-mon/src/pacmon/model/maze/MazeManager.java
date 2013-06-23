package pacmon.model.maze;

import java.util.HashMap;
import java.util.Map;

public class MazeManager 
{
	
	public static String MAZE_1 = "MAZE_1";
	public static String MAZE_2 = "MAZE_2";
	public static String MAZE_3 = "MAZE_3";	
	
	public static MazeManager getInstance()
	{
		if (instance == null)
		{
			instance = new MazeManager();
		}
		return instance;
	}

	private static String[] MAZE_1_DESIGN = 
	{
		 "############################"
		,"#............##............#"
		,"#.####.#####.##.#####.####.#"
		,"#0####.#####.##.#####.####0#"
		,"#.####.#####.##.#####.####.#"
		,"#..........................#"
		,"#.####.##.########.##.####.#"
		,"#.####.##.########.##.####.#"
		,"#......##....##....##......#"
		,"######.##### ## #####.######"
		,"######.##### ## #####.######"
		,"######.##          ##.######"
		,"######.## MMMMMMMM ##.######"
		,"######.## MMMMMMMM ##.######"
		,"TTTTTT.   MMMMMMMM   .TTTTTT"
		,"######.## MMMMMMMM ##.######"
		,"######.## MMMMMMMM ##.######"
		,"######.## #        ##.######"
		,"######.## ######## ##.######"
		,"######.## ######## ##.######"
		,"#............##............#"
		,"#.####.#####.##.#####.####.#"
		,"#.####.#####.##.#####.####.#"
		,"#0..##......    ......##..0#"
		,"###.##.##.########.##.##.###"
		,"###.##.##.########.##.##.###"
		,"#......##....##....##......#"
		,"#.##########.##.##########.#"
		,"#.##########.##.##########.#"
		,"#..........................#"
		,"############################"
	};
	
	private static String[] MAZE_2_DESIGN = 
	{
		 "############################"
		,"#............##............#"
		,"#.####.#####.##.#####.####.#"
		,"#0####.#####.##.#####.####0#"
		,"#.####.#####.##.#####.####.#"
		,"#..........................#"
		,"#.####.##.########.##.####.#"
		,"#.####.##.########.##.####.#"
		,"#......##....##....##......#"
		,"######.##### ## #####.######"
		,"######.##### ## #####.######"
		,"######.##          ##.######"
		,"######.## MMMMMMMM ##.######"
		,"######.## MMMMMMMM ##.######"
		,"TTTTTT.   MMMMMMMM   .TTTTTT"
		,"######.## MMMMMMMM ##.######"
		,"######.## MMMMMMMM ##.######"
		,"######.##          ##.######"
		,"######.## ######## ##.######"
		,"######.## ######## ##.######"
		,"#............##............#"
		,"#.####.#####.##.#####.####.#"
		,"#.####.#####.##.#####.####.#"
		,"#0..##.....      .....##..0#"
		,"###.##.##.########.##.##.###"
		,"###.##.##.########.##.##.###"
		,"#......##....##....##......#"
		,"#.##########.##.##########.#"
		,"#.##########.##.##########.#"
		,"#..........................#"
		,"############################"
	};
	
	private static String[] MAZE_3_DESIGN = 
	{
		 "############################"
		,"#..........................#"
		,"#.##.####.########.####.##.#"
		,"#0##.####.########.####.##0#"
		,"#.##......##....##......##.#"
		,"#.####.##.##.##.##.##.####.#"
		,"#.####.##.##.##.##.##.####.#"
		,"#.####.##.##.##.##.##.####.#"
		,"#......##....##....##......#"
		,"###.######## ## ########.###"
		,"###.######## ## ########.###"
		,"###....##          ##....###"
		,"######.## MMMMMMMM ##.######"
		,"######.## MMMMMMMM ##.######"
		,"TTTTTT.   MMMMMMMM   .TTTTTT"
		,"######.## MMMMMMMM ##.######"
		,"######.## MMMMMMMM ##.######"
		,"###....##          ##....###"
		,"###.##.##### ## #####.##.###"
		,"###.##.##### ## #####.##.###"
		,"###.##....   ##   ....##.###"
		,"###.#####.########.#####.###"
		,"###.#####.########.#####.###"
		,"#......##.        .##......#"
		,"#.####.##.########.##.####.#"
		,"#.####.##.########.##.####.#"
		,"#.##...##....##....##...##.#"
		,"#0##.#######.##.#######.##0#"
		,"#.##.#######.##.#######.##.#"
		,"#............##............#"
		,"############################"
	};
	
	public Maze buildMaze(String mazeDesign)	
	{
		String[] rows = mazeDesignMap.get(mazeDesign);
		
		MazeTile[][] tileMatrix = new MazeTile[Maze.WIDTH][Maze.HEIGHT];
		MazeItem[][] itemMatrix = new MazeItem[Maze.WIDTH][Maze.HEIGHT];
		
		//Random r = new Random();
		
		for (int i = 0; i < Maze.HEIGHT; ++i)
		{			
			String row = rows[i];
			for (int j = 0; j < Maze.WIDTH; ++j)
			{
				char c = row.charAt(j);
				
				/*if (r.nextFloat() > 0.35)
					tileMatrix[j][i] = MazeTile.WALL;
				else				
					tileMatrix[j][i] = MazeTile.GROUND;*/
				
				if (c == '#')
				{
					tileMatrix[j][i] = MazeTile.WALL;
				}
				else if (c == '.')
				{
					tileMatrix[j][i] = MazeTile.GROUND;
					itemMatrix[j][i] = MazeItem.Dot;
				}
				else if (c == ' ')
				{
					tileMatrix[j][i] = MazeTile.GROUND;
				}							
				else if (c == 'M')
				{
					tileMatrix[j][i] = MazeTile.WALL;
				}
				else if (c == '0')
				{
					tileMatrix[j][i] = MazeTile.GROUND;
					itemMatrix[j][i] = MazeItem.Energizer;
				}
				else if (c == 'P')
				{
					tileMatrix[j][i] = MazeTile.GROUND;					
				}
				else if (c == 'T')
				{
					tileMatrix[j][i] = MazeTile.TUNNEL;
				}
			}
		}
		
		Maze maze = new Maze(tileMatrix, itemMatrix); 
		return maze;
	}
	
	private static MazeManager instance;
	
	private MazeManager()
	{
		mazeDesignMap = new HashMap<String,String[]>();
		
		mazeDesignMap.put(MAZE_1, MAZE_1_DESIGN);		
		mazeDesignMap.put(MAZE_2, MAZE_2_DESIGN);
		mazeDesignMap.put(MAZE_3, MAZE_3_DESIGN);
	}
	
	private Map<String,String[]> mazeDesignMap;
}
