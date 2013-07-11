package pacmon.model.level;

import pacmon.model.maze.Maze;
import pacmon.model.maze.MazeItem;

public class LevelState implements Cloneable
{

	public LevelState(Maze maze
			, LevelMode mode
			, float pacManNormalSpeed
			, float pacManFrightSpeed
			, float monsterNormalSpeed
			, float monsterFrightSpeed
			, float monsterTunnelSpeed		
			, float elroy1Speed
			, float elroy2Speed
			, boolean hasFrightenedMode
			, int frightenedDuration
			, int frightenedMonsterFlashes			
			, int scatter1Duration
			, int chase1Duration
			, int scatter2Duration
			, int chase2Duration
			, int scatter3Duration
			, int chase3Duration
			, int scatter4Duration	
			, MazeItem bonus
			, int bonusAvailable
			) 
	{
		super();
		this.maze = maze;
		this.score = 0;
		this.dotsConsumed = 0;		
		this.mode = mode;		
		this.pacManNormalSpeed = pacManNormalSpeed;
		this.pacManFrightSpeed = pacManFrightSpeed;
		this.monsterNormalSpeed = monsterNormalSpeed;
		this.monsterFrightSpeed = monsterFrightSpeed;
		this.monsterTunnelSpeed = monsterTunnelSpeed;
		
		this.elroy1Speed = elroy1Speed;
		this.elroy2Speed = elroy2Speed;
		
		this.hasFrightenedMode = hasFrightenedMode;
		
		this.frightenedDuration = frightenedDuration;
		this.frightenedMonsterFlashes = frightenedMonsterFlashes;
		
		this.scatter1Duration = scatter1Duration;
		this.chase1Duration = chase1Duration;
		this.scatter2Duration = scatter2Duration;
		this.chase2Duration = chase2Duration;
		this.scatter3Duration = scatter3Duration;
		this.chase3Duration = chase3Duration;
		this.scatter4Duration = scatter4Duration;
		
		this.bonus = bonus;
		
		this.bonusAvailable = bonusAvailable;
		
		// internal
		totalFlashInterval = Level.MONSTER_FLASH_DURATION * frightenedMonsterFlashes * 2;
	}
	
	public int getScore()
	{
		return this.score;
	}
	
	public void addToScore(int amount)
	{
		score += amount;
	}
	
	public int getDotsConsumed()
	{
		return dotsConsumed;
	}
	
	public void incrementDotsConsumed()
	{
		dotsConsumed++;
	}
	
	public LevelMode getMode()
	{
		return this.mode;
	}
	
	public void setMode(LevelMode mode)
	{
		this.mode = mode;
	}
	
	public float getPacManNormalSpeed() 
	{
		return pacManNormalSpeed;
	}

	public float getPacManFrightSpeed() 
	{
		return pacManFrightSpeed;
	}
	
	public float getMonsterNormalSpeed() 
	{
		return monsterNormalSpeed;
	}

	public float getMonsterFrightSpeed() 
	{
		return monsterFrightSpeed;
	}
	
	public float getMonsterTunnelSpeed() 
	{
		return monsterTunnelSpeed;
	}
	
	public float getElroy1Speed() 
	{
		return elroy1Speed;
	}

	public float getElroy2Speed() 
	{
		return elroy2Speed;
	}
	
	public boolean getHasFrightenedMode() 
	{
		return hasFrightenedMode;
	}

	public int getFrightenedDuration() 
	{
		return frightenedDuration;
	}
	
	public int getFrightenedMonsterFlashes() 
	{
		return frightenedMonsterFlashes;
	}
	
	public int getScatter1Duration() 
	{
		return scatter1Duration;
	}

	public int getChase1Duration() 
	{
		return chase1Duration;
	}

	public int getScatter2Duration() 
	{
		return scatter2Duration;
	}

	public int getChase2Duration() 
	{
		return chase2Duration;
	}

	public int getScatter3Duration() 
	{
		return scatter3Duration;
	}

	public int getChase3Duration() 
	{
		return chase3Duration;
	}

	public int getScatter4Duration() 
	{
		return scatter4Duration;
	}
	
	public MazeItem getBonus() 
	{
		return bonus;
	}
	
	public int getBonusAvailable() 
	{
		return bonusAvailable;
	}

	public void setBonusAvailable(int bonusAvailable) 
	{
		this.bonusAvailable = bonusAvailable;
	}

	public boolean isMonsterFlashingFrame(int frame)
	{		
		if (frame < totalFlashInterval)
		{
			int parityCheck = frame / Level.MONSTER_FLASH_DURATION;
			
			if (parityCheck % 2 == 0)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		
		return false;
	}
	
	public Maze getMaze() 
	{
		return maze;
	}

	public void setMaze(Maze maze) 
	{
		this.maze = maze;
	}
	
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("mode: "+mode).append("\n");
		builder.append("pacManNormalSpeed: "+pacManNormalSpeed).append("\n");
		builder.append("pacManFrightSpeed: "+pacManFrightSpeed).append("\n");
		
		builder.append("monsterNormalSpeed: "+monsterNormalSpeed).append("\n");
		builder.append("monsterFrightSpeed: "+monsterFrightSpeed).append("\n");
		builder.append("monsterTunnelSpeed: "+monsterTunnelSpeed).append("\n");
		
		builder.append("elroy1Speed: "+elroy1Speed).append("\n");
		builder.append("elroy2Speed: "+elroy2Speed).append("\n");
		
		builder.append("hasFrightenedMode: "+hasFrightenedMode).append("\n");
		builder.append("frightenedDuration: "+frightenedDuration).append("\n");
		builder.append("frightenedMonsterFlashes: "+frightenedMonsterFlashes).append("\n");
		builder.append("scatter1Duration: "+scatter1Duration).append("\n");
		builder.append("chase1Duration: "+chase1Duration).append("\n");
		builder.append("scatter2Duration: "+scatter2Duration).append("\n");
		
		builder.append("chase2Duration: "+chase2Duration).append("\n");
		builder.append("scatter3Duration: "+scatter3Duration).append("\n");
		builder.append("chase3Duration: "+chase3Duration).append("\n");
		builder.append("scatter4Duration: "+scatter4Duration).append("\n");
		builder.append("bonus: "+bonus).append("\n");
		builder.append("bonusAvailable: "+bonusAvailable);
		
		return builder.toString();
	}
	
	private int score;
	private int dotsConsumed;
	private LevelMode mode;	
	private float pacManNormalSpeed;
	private float pacManFrightSpeed;	
	private float monsterNormalSpeed;
	private float monsterFrightSpeed;
	private float monsterTunnelSpeed;
	
	private float elroy1Speed;
	private float elroy2Speed;
	
	private boolean hasFrightenedMode;
	
	private int frightenedDuration;
	private int frightenedMonsterFlashes;
	
	private int scatter1Duration;
	private int chase1Duration;
	private int scatter2Duration;
	private int chase2Duration;
	private int scatter3Duration;
	private int chase3Duration;
	private int scatter4Duration;
	
	private int totalFlashInterval;
	
	private MazeItem bonus;
	
	private int bonusAvailable;
	
	private Maze maze;

}
