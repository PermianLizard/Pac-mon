package pacmon.model;

import pacmon.model.level.Level;
import pacmon.model.level.LevelMode;
import pacmon.model.level.LevelState;
import pacmon.model.maze.MazeItem;
import pacmon.model.maze.MazeManager;

public class Game 
{

	private static final int LEVEL_START_DELEY = 100;
	private static final int LEVEL_COMPLETE_DELEY = 100;
	private static final int PAC_MAN_DEATH_DELEY = 150;
	
	public static Game getInstance()
	{
		if (instance == null)
		{
			instance = new Game();
		}
		return instance;
	}
	
	private static Game instance;
	
	public GameState getState()
	{
		return state;
	}
	
	public void setState(GameState state)
	{	
		this.state = state;
	}
	
	public void reset()
	{
		instance = new Game();
	}
	
	public Level getLevel()
	{
		return level;
	}
	
	private Game()
	{
		GameState state = new GameState(1, 0, 3);	
		setState(state);
		
		paused = false;
		level = new Level();
		level.setState(levelStates[0]);
		gameOver = false;
		
		levelStartCountdown = LEVEL_START_DELEY;
		
		levelCompleteCountdown = 0;
		pacMonDeathCountdown = 0;
	}
	
	// Game control
	public void controlUp()
	{
		level.controlUp();
	}
	
	public void controlLeft()
	{
		level.controlLeft();
	}
	
	public void controlDown()
	{
		level.controlDown();
	}
	
	public void controlRight()
	{
		level.controlRight();
	}
	
	public void controlPause()
	{
		paused = !paused;
	}
	
	public boolean isPaused()
	{
		return paused;
	}
	
	public boolean isGameOver()
	{
		return gameOver;
	}
	
	public boolean isInLevelStartDelay()
	{
		return levelStartCountdown > 0;
	}
	
	public boolean isInPacManDeath()
	{
		return pacMonDeathCountdown > 0;
	}
	
	public void update()
	{
		if (!paused && !gameOver)
		{
			if (levelStartCountdown > 0)
			{
				levelStartCountdown--;
				
				if (levelStartCountdown == 0)
				{
					System.out.println("--- Level "+state.getLevelNum()+" ---");
					
					System.out.println(level.getState().toString());
					
					System.out.println("---");
				}
			}
			else if (levelCompleteCountdown > 0)
			{
				levelCompleteCountdown--;
				
				if (levelCompleteCountdown == 0)
				{
					int nextLevel = state.getLevelNum() + 1;
					
					LevelState currentState = level.getState();	
					
					LevelState nextState = null;
					if (nextLevel >= levelStates.length)
					{
						nextState = levelStates[levelStates.length - 1];	
					}
					else
					{
						nextState = levelStates[nextLevel - 1];
					}
					
					level = new Level();					
					level.setState(nextState);
					
					state.setLevelNum(nextLevel);
					state.addToTotalScore(currentState.getScore());
					state.setLivesLeft(3);				
					
					levelStartCountdown = LEVEL_START_DELEY;
					
					System.out.println("Level "+state.getLevelNum());
				}
			}
			else if (pacMonDeathCountdown > 0)
			{
				pacMonDeathCountdown--;
				
				if (pacMonDeathCountdown == 0)
				{
					LevelState currentState = level.getState();
					currentState.setMode(LevelMode.SCATTER);
					
					//level = new Level(this);
					levelStartCountdown = LEVEL_START_DELEY;
					state.setLivesLeft(state.getLivesLeft() - 1);
					level.setState(currentState);
					
					if (state.getLivesLeft() == 0)
					{
						gameOver = true;
					}
				}
			}
			else
			{
				level.update();
				
				if (level.isComplete())
				{
					levelCompleteCountdown = LEVEL_COMPLETE_DELEY;
					
					System.out.println("Level "+state.getLevelNum() + " complete");
				}
				
				if (level.isPacMonDead())
				{
					pacMonDeathCountdown = PAC_MAN_DEATH_DELEY;
				}
			}
		}
	}	
	
	private GameState state;
	
	private Level level;
	
	public boolean paused;
	
	private boolean gameOver;
	
	private int levelStartCountdown;
	private int levelCompleteCountdown;
	private int pacMonDeathCountdown;
	
	private LevelState[] levelStates = {
			// 1
			new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_1)
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
					, MazeItem.BonusCherries
					, 2)
			,
			// 2
			new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_1)
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
					, MazeItem.BonusStrawberry
					, 2)
			,
			// 3
			new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_1)
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
					, MazeItem.BonusPeach
					, 2)
			,
			// 4
			new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_1)
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
					, MazeItem.BonusPeach
					, 2)
			,
			// 5
			new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_1)
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
					, MazeItem.BonusApple
					, 2)
			,
			// 6
			new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_1)
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
					, MazeItem.BonusApple
					, 2)
			,
			// 7
			new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_1)
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
					, MazeItem.BonusMelon
					, 2)
			,
			// 8
			new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_1)
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
					, MazeItem.BonusMelon
					, 2)
			,
			// 9
			new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_1)
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
					, MazeItem.BonusGalaxian
					, 2)
			,
			// 10
			new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_1)
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
					, MazeItem.BonusGalaxian
					, 2)
			,
			// 11
			new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_1)
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
					, MazeItem.BonusBell
					, 2)
			,
			// 12
			new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_1)
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
					, MazeItem.BonusBell
					, 2)
			,
			// 13
			new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_1)
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
					, MazeItem.BonusKey
					, 2)
			,
			// 14
			new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_1)
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
					, MazeItem.BonusKey
					, 2)
			,
			// 15
			new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_1)
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
					, MazeItem.BonusKey
					, 2)
			,
			// 16
			new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_1)
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
					, MazeItem.BonusKey
					, 2)
			,
			// 17
			new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_1)
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
					, MazeItem.BonusKey
					, 2)
			,
			// 18
			new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_1)
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
					, MazeItem.BonusKey
					, 2)
			,
			// 19
			new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_1)
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
					, MazeItem.BonusKey
					, 2)
			,
			// 20
			new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_1)
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
					, MazeItem.BonusKey
					, 2)
			,
			// 21+
			new LevelState(MazeManager.getInstance().buildMaze(MazeManager.MAZE_1)
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
					, MazeItem.BonusKey
					, 2)
			
			
	};
}
