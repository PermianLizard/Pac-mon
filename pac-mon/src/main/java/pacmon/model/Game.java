package pacmon.model;

import java.util.LinkedList;

import pacmon.model.level.Level;
import pacmon.model.level.LevelConfig;
import pacmon.model.level.LevelMode;
import pacmon.model.level.LevelState;
import pacmon.sound.SoundLoader;

public class Game 
{

	private static final int LEVEL_START_DELEY = 300;
	private static final int LEVEL_COMPLETE_DELEY = 100;
	private static final int PAC_MAN_DEATH_DELEY = 150;
	
	private static final int PAC_MAN_LIVES = 2;
	
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
		System.out.println("game reset");
	}
	
	public void clear()
	{
		instance = null;
		System.out.println("game cleared");
	}
	
	public Level getLevel()
	{
		return level;
	}
	
	private Game()
	{
		GameState state = new GameState(1, 0, PAC_MAN_LIVES);	
		setState(state);
		
		paused = false;
		level = new Level();
		level.setState(LevelConfig.LEVEL_STATES[0]);
		gameOver = false;
		
		levelStartCountdown = LEVEL_START_DELEY;
		
		levelCompleteCountdown = 0;
		pacMonDeathCountdown = 0;
		
		started = false;
		
		soundEventQueue = new LinkedList<GameSoundEvent>();
	}
	
	public GameSoundEvent pollSoundEvent() 
	{
		return this.soundEventQueue.poll();
	}
	
	public boolean hasSoundEvent() {
		return !this.soundEventQueue.isEmpty();
	}
	
	public void addSoundEvent(int type) {
		this.addSoundEvent(type, null);
	}
	
	public void addSoundEvent(int type, String name) {
		this.addSoundEvent(type, name, false);
	}
	
	public void addSoundEvent(int type, String name, boolean loop) {
		this.soundEventQueue.add(new GameSoundEvent(type, name, loop));
	}
	
	// Game control
	public void controlUp()
	{
		if (!this.paused)
			level.controlUp();
	}
	
	public void controlLeft()
	{
		if (!this.paused)
			level.controlLeft();
	}
	
	public void controlDown()
	{
		if (!this.paused)
			level.controlDown();
	}
	
	public void controlRight()
	{
		if (!this.paused)
			level.controlRight();
	}
	
	public void controlPause()
	{
		paused = !paused;
		
		if (paused)
		{
			addSoundEvent(GameSoundEvent.TYPE_PAUSE);
			//SoundLoader.getInstance().setPaused(true);
		}
		else 
		{
			addSoundEvent(GameSoundEvent.TYPE_UNPAUSE);
			//SoundLoader.getInstance().setPaused(false);
		}
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
		if (!started)
		{
			this.addSoundEvent(GameSoundEvent.TYPE_STOP_ALL);
			this.addSoundEvent(GameSoundEvent.TYPE_PLAY, SoundLoader.BEGINNING);
			
			started = true;
		}
		
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
					if (nextLevel >= LevelConfig.LEVEL_STATES.length)
					{
						nextState = LevelConfig.LEVEL_STATES[LevelConfig.LEVEL_STATES.length - 1];	
					}
					else
					{
						nextState = LevelConfig.LEVEL_STATES[nextLevel - 1];
					}
					
					level = new Level();					
					level.setState(nextState);
					
					state.setLevelNum(nextLevel);
					state.addToTotalScore(currentState.getScore());
					state.setLivesLeft(this.getState().getLivesLeft());				
					
					levelStartCountdown = LEVEL_START_DELEY;
					
					this.addSoundEvent(GameSoundEvent.TYPE_STOP_ALL);
					this.addSoundEvent(GameSoundEvent.TYPE_PLAY, SoundLoader.BEGINNING);
					
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
					
					if (state.getLivesLeft() == 0)
					{
						gameOver = true;
					}
					else
					{
						levelStartCountdown = LEVEL_START_DELEY;
						
						this.addSoundEvent(GameSoundEvent.TYPE_STOP_ALL);
						this.addSoundEvent(GameSoundEvent.TYPE_PLAY, SoundLoader.BEGINNING);
						
						state.setLivesLeft(state.getLivesLeft() - 1);
						level.setState(currentState);
					}
				}
			}
			else
			{
				level.update();
				
				if (level.isComplete())
				{
					levelCompleteCountdown = LEVEL_COMPLETE_DELEY;
					this.addSoundEvent(GameSoundEvent.TYPE_STOP_ALL);
					
					System.out.println("Level "+state.getLevelNum() + " complete");
				}
				
				if (level.isPacMonDead())
				{
					pacMonDeathCountdown = PAC_MAN_DEATH_DELEY;

					this.addSoundEvent(GameSoundEvent.TYPE_STOP, SoundLoader.INTERMISSION);
					this.addSoundEvent(GameSoundEvent.TYPE_PLAY, SoundLoader.DEATH);
				}
			}
		}
	}	
	
	private LinkedList<GameSoundEvent> soundEventQueue;
	
	private GameState state;
	
	private Level level;
	
	public boolean paused;
	
	private boolean gameOver;
	
	private boolean started;
	
	private int levelStartCountdown;
	private int levelCompleteCountdown;
	private int pacMonDeathCountdown;
	
	
}
