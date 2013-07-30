package pacmon.model.level;

import pacmon.TilesetManager;
import pacmon.model.maze.MazeItem;
import pacmon.model.maze.MazeManager;

public class LevelConfig {

	public static LevelState[] LEVEL_STATES = {
		// 1
		new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_1, TilesetManager.TILESET_1)
				, LevelMode.SCATTER
				, 0.8f
				, 0.9f
				, 0.75f
				, 0.5f
				, 0.4f
				, 0.8f
				, 0.85f
				, true
				, 360
				, 5
				, 420
				, 1200
				, 420
				, 1200
				, 300
				, 1200
				, 2
				, MazeItem.BonusWeed  // cherries
				, 2)
		,
		// 2
		new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_2, TilesetManager.TILESET_2)
				, LevelMode.SCATTER
				, 0.9f
				, 0.95f
				, 0.85f
				, 0.55f
				, 0.45f
				, 0.9f
				, 0.95f
				, true
				, 360
				, 5
				, 420
				, 1200
				, 420
				, 1200
				, 300
				, 61980
				, 2
				, MazeItem.BonusEcstacy
				, 2)
		,
		// 3
		new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_1, TilesetManager.TILESET_3)
				, LevelMode.SCATTER
				, 0.9f
				, 0.95f
				, 0.85f
				, 0.55f
				, 0.45f
				, 0.9f
				, 0.95f
				, true
				, 360
				, 5
				, 420
				, 1200
				, 420
				, 1200
				, 300
				, 61980
				, 2
				, MazeItem.BonusShroom
				, 2)
		,
		// 4
		new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_2, TilesetManager.TILESET_4)
				, LevelMode.SCATTER
				, 0.9f
				, 0.95f
				, 0.85f
				, 0.55f
				, 0.45f
				, 0.9f
				, 0.95f
				, true
				, 360
				, 5
				, 420
				, 1200
				, 420
				, 1200
				, 300
				, 61980
				, 2
				, MazeItem.BonusShroom
				, 2)
		,
		// 5
		new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_3, TilesetManager.TILESET_1)
				, LevelMode.SCATTER
				, 1f
				, 1f
				, 0.95f
				, 0.6f
				, 0.5f
				, 1f
				, 1.05f
				, true
				, 360
				, 5
				, 300
				, 1200
				, 300
				, 1200
				, 300
				, 62220
				, 2
				, MazeItem.BonusLSD
				, 2)
		,
		// 6
		new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_2, TilesetManager.TILESET_2)
				, LevelMode.SCATTER
				, 1f
				, 1f
				, 0.95f
				, 0.6f
				, 0.5f
				, 1f
				, 1.05f
				, true
				, 360
				, 5
				, 300
				, 1200
				, 300
				, 1200
				, 300
				, 62220
				, 2
				, MazeItem.BonusLSD
				, 2)
		,
		// 7
		new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_2, TilesetManager.TILESET_3)
				, LevelMode.SCATTER
				, 1f
				, 1f
				, 0.95f
				, 0.6f
				, 0.5f
				, 1f
				, 1.05f
				, true
				, 360
				, 5
				, 300
				, 1200
				, 300
				, 1200
				, 300
				, 62220
				, 2
				, MazeItem.BonusCoke
				, 2)
		,
		// 8
		new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_3, TilesetManager.TILESET_4)
				, LevelMode.SCATTER
				, 1f
				, 1f
				, 0.95f
				, 0.6f
				, 0.5f
				, 1f
				, 1.05f
				, true
				, 360
				, 5
				, 300
				, 1200
				, 300
				, 1200
				, 300
				, 62220
				, 2
				, MazeItem.BonusCoke
				, 2)
		,
		// 9
		new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_1, TilesetManager.TILESET_1)
				, LevelMode.SCATTER
				, 1f
				, 1f
				, 0.95f
				, 0.6f
				, 0.5f
				, 1f
				, 1.05f
				, true
				, 360
				, 5
				, 300
				, 1200
				, 300
				, 1200
				, 300
				, 62220
				, 2
				, MazeItem.BonusSteroids
				, 2)
		,
		// 10
		new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_2, TilesetManager.TILESET_2)
				, LevelMode.SCATTER
				, 1f
				, 1f
				, 0.95f
				, 0.6f
				, 0.5f
				, 1f
				, 1.05f
				, true
				, 360
				, 5
				, 300
				, 1200
				, 300
				, 1200
				, 300
				, 62220
				, 2
				, MazeItem.BonusSteroids
				, 2)
		,
		// 11
		new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_2, TilesetManager.TILESET_3)
				, LevelMode.SCATTER
				, 1f
				, 1f
				, 0.95f
				, 0.6f
				, 0.5f
				, 1f
				, 1.05f
				, true
				, 360
				, 5
				, 300
				, 1200
				, 300
				, 1200
				, 300
				, 62220
				, 2
				, MazeItem.BonusHeroin
				, 2)
		,
		// 12
		new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_3, TilesetManager.TILESET_4)
				, LevelMode.SCATTER
				, 1f
				, 1f
				, 0.95f
				, 0.6f
				, 0.5f
				, 1f
				, 1.05f
				, true
				, 360
				, 5
				, 300
				, 1200
				, 300
				, 1200
				, 300
				, 62220
				, 2
				, MazeItem.BonusHeroin
				, 2)
		,
		// 13
		new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_1, TilesetManager.TILESET_1)
				, LevelMode.SCATTER
				, 1f
				, 1f
				, 0.95f
				, 0.6f
				, 0.5f
				, 1f
				, 1.05f
				, true
				, 360
				, 5
				, 300
				, 1200
				, 300
				, 1200
				, 300
				, 62220
				, 2
				, MazeItem.BonusMeth
				, 2)
		,
		// 14
		new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_1, TilesetManager.TILESET_2)
				, LevelMode.SCATTER
				, 1f
				, 1f
				, 0.95f
				, 0.6f
				, 0.5f
				, 1f
				, 1.05f
				, true
				, 360
				, 5
				, 300
				, 1200
				, 300
				, 1200
				, 300
				, 62220
				, 2
				, MazeItem.BonusMeth
				, 2)
		,
		// 15
		new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_3, TilesetManager.TILESET_3)
				, LevelMode.SCATTER
				, 1f
				, 1f
				, 0.95f
				, 0.6f
				, 0.5f
				, 1f
				, 1.05f
				, true
				, 360
				, 5
				, 300
				, 1200
				, 300
				, 1200
				, 300
				, 62220
				, 2
				, MazeItem.BonusMeth
				, 2)
		,
		// 16
		new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_2, TilesetManager.TILESET_4)
				, LevelMode.SCATTER
				, 1f
				, 1f
				, 0.95f
				, 0.6f
				, 0.5f
				, 1f
				, 1.05f
				, true
				, 360
				, 5
				, 300
				, 1200
				, 300
				, 1200
				, 300
				, 62220
				, 2
				, MazeItem.BonusMeth
				, 2)
		,
		// 17
		new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_2, TilesetManager.TILESET_1)
				, LevelMode.SCATTER
				, 1f
				, 0f
				, 0.95f
				, 0f
				, 0.5f
				, 1f
				, 1.05f
				, false
				, 0
				, 0
				, 300
				, 1200
				, 300
				, 1200
				, 300
				, 62220
				, 2
				, MazeItem.BonusMeth
				, 2)
		,
		// 18
		new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_1, TilesetManager.TILESET_2)
				, LevelMode.SCATTER
				, 1f
				, 1f
				, 0.95f
				, 0.6f
				, 0.5f
				, 1f
				, 1.05f
				, true
				, 360
				, 5
				, 300
				, 1200
				, 300
				, 1200
				, 300
				, 62220
				, 2
				, MazeItem.BonusMeth
				, 2)
		,
		// 19
		new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_1, TilesetManager.TILESET_3)
				, LevelMode.SCATTER
				, 1f
				, 0f
				, 0.95f
				, 0f
				, 0.5f
				, 1f
				, 1.05f
				, false
				, 0
				, 0
				, 300
				, 1200
				, 300
				, 1200
				, 300
				, 62220
				, 2
				, MazeItem.BonusMeth
				, 2)
		,
		// 20
		new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_1, TilesetManager.TILESET_4)
				, LevelMode.SCATTER
				, 1f
				, 0f
				, 0.95f
				, 0f
				, 0.5f
				, 1f
				, 1.05f
				, false
				, 0
				, 0
				, 300
				, 1200
				, 300
				, 1200
				, 300
				, 62220
				, 2
				, MazeItem.BonusMeth
				, 2)
		,
		// 21+
		new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_2, TilesetManager.TILESET_1)
				, LevelMode.SCATTER
				, 0.9f
				, 0f
				, 0.95f
				, 0f
				, 0.5f
				, 1f
				, 1.05f
				, false
				, 0
				, 0
				, 300
				, 1200
				, 300
				, 1200
				, 300
				, 62220
				, 2
				, MazeItem.BonusMeth
				, 2)
	};

}
