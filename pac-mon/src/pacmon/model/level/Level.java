package pacmon.model.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pacmon.model.Game;
import pacmon.model.GameSoundEvent;
import pacmon.model.entity.Blinky;
import pacmon.model.entity.Clyde;
import pacmon.model.entity.Entity;
import pacmon.model.entity.EntityState;
import pacmon.model.entity.Inky;
import pacmon.model.entity.Monster;
import pacmon.model.entity.MonsterMode;
import pacmon.model.entity.PacMon;
import pacmon.model.entity.Pinky;
import pacmon.model.maze.Maze;
import pacmon.model.maze.MazeItem;
import pacmon.sound.SoundLoader;

public class Level 
{

	public static final int MONSTER_FLASH_DURATION = 20;
	
	public Level()
	{
		pacMon = new PacMon(this);
		blinky = new Blinky(this);
		pinky = new Pinky(this);
		inky = new Inky(this);
		clyde = new Clyde(this);
		
		randomGenerator = new Random();
		
		//setState(LevelState.createInitialState());
	}
	
	public LevelState getState()
	{
		return state;
	}
	
	public void setState(LevelState state)
	{	
		this.state = state;	
		
		isPacMonDead = false;
		
		pacMon.setPosition(13 * Maze.TILE_SIZE + Maze.TILE_SIZE, 23 * Maze.TILE_SIZE + Maze.TILE_HALF_SIZE);		
		blinky.setPosition(blinky.getStartPosition().x * Maze.TILE_SIZE + Maze.TILE_HALF_SIZE, blinky.getStartPosition().y * Maze.TILE_SIZE + Maze.TILE_HALF_SIZE);
		pinky.setPosition((pinky.getStartPosition().x * Maze.TILE_SIZE) + Maze.TILE_SIZE, pinky.getStartPosition().y * Maze.TILE_SIZE + Maze.TILE_HALF_SIZE);		
		inky.setPosition((inky.getStartPosition().x * Maze.TILE_SIZE) + Maze.TILE_SIZE, inky.getStartPosition().y * Maze.TILE_SIZE + Maze.TILE_HALF_SIZE);		
		clyde.setPosition((clyde.getStartPosition().x * Maze.TILE_SIZE) + Maze.TILE_SIZE, clyde.getStartPosition().y * Maze.TILE_SIZE + Maze.TILE_HALF_SIZE);
		
		pacMon.setState(EntityState.STILL);
		blinky.setState(EntityState.STILL);
		pinky.setState(EntityState.STILL);
		inky.setState(EntityState.STILL);
		clyde.setState(EntityState.STILL);
		
		//setMonsterMode();
		
		blinky.setMode(MonsterMode.SCATTER);
		pinky.setMode(MonsterMode.HOME);
		inky.setMode(MonsterMode.HOME);
		clyde.setMode(MonsterMode.HOME);
		
		pacMon.setDirection(Entity.DIRECTION_NONE);
		pinky.setDirection(Entity.DIRECTION_UP);
		inky.setDirection(Entity.DIRECTION_DOWN);
		clyde.setDirection(Entity.DIRECTION_DOWN);
		
		blinkyHomeCounter = 0;
		pinkyHomeCounter = 0;
		inkyHomeCounter = 0;
		clydeHomeCounter = 0;
		
		frightenedCooldown = -1;
		frightenedMonstersEaten = 0;
		
		intervalCooldown = 0;		
		currentInterval = 0;
		
		//pacPanFrozenCooldown = -1;
		
		if (bonusList != null)
		{
			List<Bonus> removeList = new ArrayList<Bonus>(bonusList);
			for (Bonus bonus : removeList)
			{
				removeBonus(bonus.getX(), bonus.getY());
			}
		}
		bonusList = new ArrayList<Bonus>();
		
		labelList = new ArrayList<LevelLabel>();
		
		/*intervalCooldown = state.getScatter1Duration();
		currentInterval = 1;
		startScatterMode();		
		System.out.println("Scatter 1 : "+intervalCooldown+" intervals");*/
	}
	
	public PacMon getPacMon()
	{
		return pacMon;
	}
	
	public Blinky getBlinky()
	{
		return blinky;
	}
	
	public Pinky getPinky()
	{
		return pinky;
	}
	
	public Inky getInky()
	{
		return inky;
	}
	
	public Clyde getClyde()
	{
		return clyde;
	}
	
	public void controlUp()
	{
		if (state.getMaze().isTilePassable(pacMon.getAdjacentPosition(Entity.DIRECTION_UP), pacMon))
		{
			pacMon.setDirection(Entity.DIRECTION_UP);
		}
	}
	
	public void controlLeft()
	{
		if (state.getMaze().isTilePassable(pacMon.getAdjacentPosition(Entity.DIRECTION_LEFT), pacMon))
		{
			pacMon.setDirection(Entity.DIRECTION_LEFT);
		}
	}
	
	public void controlDown()
	{
		if (state.getMaze().isTilePassable(pacMon.getAdjacentPosition(Entity.DIRECTION_DOWN), pacMon))
		{
			pacMon.setDirection(Entity.DIRECTION_DOWN);
		}
	}
	
	public void controlRight()
	{
		if (state.getMaze().isTilePassable(pacMon.getAdjacentPosition(Entity.DIRECTION_RIGHT), pacMon))
		{
			pacMon.setDirection(Entity.DIRECTION_RIGHT);
		}
	}
	
	public boolean isComplete()
	{
		return state.getMaze().isComplete();
	}
	
	public boolean isMonsterFlashOn()
	{
		if (frightenedCooldown >= 0)
		{		
			return state.isMonsterFlashingFrame(frightenedCooldown);
		}		
		
		return false;
	}
	
	public void update()
	{
		state.getMaze().clearModifiedTiles();
		
		// Monster exiting
		if (blinky.getMode().equals(MonsterMode.HOME))
		{
			blinkyHomeCounter++;			
			if (blinkyHomeCounter == 1)
			{
				blinky.setMode(MonsterMode.EXIT_HOME);
			}
		}
		else if (blinkyHomeCounter != 0)
		{
			blinkyHomeCounter = 0;
		}
		
		if (pinky.getMode().equals(MonsterMode.HOME))
		{
			pinkyHomeCounter++;			
			if (pinkyHomeCounter == 320)
			{
				pinky.setMode(MonsterMode.EXIT_HOME);
			}
		}
		else if (pinkyHomeCounter != 0)
		{
			pinkyHomeCounter = 0;
		}
		
		if (inky.getMode().equals(MonsterMode.HOME))
		{
			inkyHomeCounter++;			
			if (inkyHomeCounter == 520)
			{
				inky.setMode(MonsterMode.EXIT_HOME);
			}
		}
		else if (inkyHomeCounter != 0)
		{
			inkyHomeCounter = 0;
		}
		
		if (clyde.getMode().equals(MonsterMode.HOME))
		{
			clydeHomeCounter++;			
			if (clydeHomeCounter == 720)
			{
				clyde.setMode(MonsterMode.EXIT_HOME);
			}
		}
		else if (clydeHomeCounter != 0)
		{
			clydeHomeCounter = 0;
		}
		
		// Frightened mode
		if (frightenedCooldown == 0)
		{
			frightenedCooldown = -1;
			frightenedMonstersEaten = 0;
			
			//SoundManager.getInstance().stop(SoundManager.INTERMISSION);
			Game.getInstance().addSoundEvent(GameSoundEvent.TYPE_STOP, SoundLoader.INTERMISSION);
			
			resumeLevelMode();			
		}
		else if (frightenedCooldown > 0)
		{
			frightenedCooldown--;		
		}
		else
		{			
			// Scatter-chase intervals
			if (intervalCooldown == 0)
			{
				currentInterval++;
				
				if (currentInterval == 1)
				{
					intervalCooldown = state.getScatter1Duration();
					startScatterMode();
					
					System.out.println("Scatter 1 : "+intervalCooldown+" intervals");
				}
				else if (currentInterval == 2)
				{
					intervalCooldown = state.getChase1Duration();
					startChaseMode();
					
					System.out.println("Chase 1 : "+intervalCooldown+" intervals");
				}
				else if (currentInterval == 3)
				{
					intervalCooldown = state.getScatter2Duration();
					startScatterMode();
					
					System.out.println("Scatter 2 : "+intervalCooldown+" intervals");
				}
				else if (currentInterval == 4)
				{
					intervalCooldown = state.getChase2Duration();
					startChaseMode();
					
					System.out.println("Chase 2 : "+intervalCooldown+" intervals");
				}
				else if (currentInterval == 5)
				{
					intervalCooldown = state.getScatter3Duration();
					startScatterMode();
					
					System.out.println("Scatter 3 : "+intervalCooldown+" intervals");
				}
				else if (currentInterval == 6)
				{
					intervalCooldown = state.getChase3Duration();
					startChaseMode();
					
					System.out.println("Chase 3 : "+intervalCooldown+" intervals");
				}				
				else if (currentInterval == 7)
				{
					intervalCooldown = state.getScatter4Duration();
					startScatterMode();
					
					System.out.println("Scatter 4 : "+intervalCooldown+" intervals");
				}				
				else
				{
					intervalCooldown = -1; // end of intervals
					startChaseMode();
					
					System.out.println("Chase (last) : "+intervalCooldown+" intervals");
				}				
			}
			else if (intervalCooldown > 0)
			{
				intervalCooldown--;
			}
		}
		
		/*if (pacPanFrozenCooldown > 0)
		{
			--pacPanFrozenCooldown;
		}
		else if (pacPanFrozenCooldown == 0)
		{
			pacPanFrozenCooldown = -1;
		}
		else
		{*/
			pacMon.advance(state.getMaze());
		//}
		
		blinky.advance(state.getMaze());
		pinky.advance(state.getMaze());
		inky.advance(state.getMaze());
		clyde.advance(state.getMaze());
		
		updateBonuses();
		updateLabels();
	}
	
	public void startChaseMode()
	{
		reverseMonsterDirections();
		state.setMode(LevelMode.CHASE);
		setMonsterMode();
	}
	
	public void startScatterMode()
	{
		reverseMonsterDirections();
		state.setMode(LevelMode.SCATTER);
		setMonsterMode();
	}
	
	public void startFrightenedMode()
	{
		int duration = state.getFrightenedDuration();
		
		if (duration > 0)
		{
			reverseMonsterDirections();
			state.setMode(LevelMode.FRIGHTENED);
			setMonsterMode();
			
			frightenedCooldown = state.getFrightenedDuration();	
			frightenedMonstersEaten = 0;
			
			//SoundManager.getInstance().play(SoundManager.INTERMISSION, true);
			Game.getInstance().addSoundEvent(GameSoundEvent.TYPE_PLAY, SoundLoader.INTERMISSION, false);
		}
	}
	
	public void resumeLevelMode()
	{
		reverseMonsterDirections();
		
		if (currentInterval % 2 != 0)
		{
			state.setMode(LevelMode.SCATTER);
		}
		else
		{
			state.setMode(LevelMode.CHASE);
		}
		
		setMonsterMode();
	}	

	public void incrementFrightenedMostersEaten(Monster monster)
	{
		frightenedMonstersEaten++;
		
		int score = frightenedMonstersEaten * 200;
		state.addToScore(score);
		
		this.labelList.add(new LevelLabel(Integer.toString(score), monster.getPosition().x, monster.getPosition().y, 100));
	}
	
	public int getFrightenedMosterseaten()
	{
		return frightenedMonstersEaten;
	}
	
	public void setMonsterMode()
	{	
		if (state.getMode().equals(LevelMode.CHASE))
		{
			if (canSetMosterMode(blinky))
				blinky.setMode(MonsterMode.CHASE);
			
			if (canSetMosterMode(pinky))
				pinky.setMode(MonsterMode.CHASE);
			
			if (canSetMosterMode(inky))
				inky.setMode(MonsterMode.CHASE);
			
			if (canSetMosterMode(clyde))
				clyde.setMode(MonsterMode.CHASE);
		}
		else if (state.getMode().equals(LevelMode.SCATTER))
		{
			if (canSetMosterMode(blinky))
				blinky.setMode(MonsterMode.SCATTER);
			
			if (canSetMosterMode(pinky))
				pinky.setMode(MonsterMode.SCATTER);
			
			if (canSetMosterMode(inky))
				inky.setMode(MonsterMode.SCATTER);
			
			if (canSetMosterMode(clyde))
				clyde.setMode(MonsterMode.SCATTER);
		}
		else if (state.getMode().equals(LevelMode.FRIGHTENED))
		{
			if (canSetMosterMode(blinky))
				blinky.setMode(MonsterMode.FRIGHTENED);
			
			if (canSetMosterMode(pinky))
				pinky.setMode(MonsterMode.FRIGHTENED);
			
			if (canSetMosterMode(inky))
				inky.setMode(MonsterMode.FRIGHTENED);
			
			if (canSetMosterMode(clyde))
				clyde.setMode(MonsterMode.FRIGHTENED);
		}
	}
	
	public MazeItem consumeMazeItem(int x, int y)
	{
		MazeItem item = state.getMaze().getMazeItem(x, y);		
		if (item != null)
		{
			if (item.equals(MazeItem.Energizer))
			{
				if (state.getHasFrightenedMode())
				{
					startFrightenedMode();
				}
				else
				{
					reverseMonsterDirections();
				}
				
				//pacPanFrozenCooldown = 6;
			}
			else if (item.equals(MazeItem.Dot))
			{
				state.incrementDotsConsumed();
				//pacPanFrozenCooldown = 1;
				
				int dotsCleared = state.getMaze().getTotalStartDots() - state.getMaze().getDotCount();
				
				if (dotsCleared == 70 || dotsCleared == 170) // FIXME: magic numbers
				{
					if (state.getBonusAvailable() > 0)
					{
						System.out.println("Bonus");
						
						int bonusInterval = (int)(450 + (randomGenerator.nextDouble() * 100));
						
						System.out.println("Bonus interval: " + bonusInterval);
						
						addBonus(new Bonus(10 + randomGenerator.nextInt(9), 17, bonusInterval, getState().getBonus()));
						
						// decrement available bonuses
						state.setBonusAvailable(state.getBonusAvailable() - 1);
					}
				}
			}
			else if (item.isBonus())
			{
				removeBonus(x, y);
				
				this.labelList.add(new LevelLabel(Integer.toString(item.getPointWorth()), x, y, 100));
			}
			
			state.addToScore(item.getPointWorth());				
			state.getMaze().clearMazeItem(x, y);
			
			if (state.getMaze().getDotCount() == 20)
			{
				System.out.println("Elroy 1");
				
				blinky.setElroyLevel(1);
			}
			else if (state.getMaze().getDotCount() == 10)
			{
				System.out.println("Elroy 2");
				
				blinky.setElroyLevel(2);
			}

			return item;
		}
		return null;
	}
	
	public boolean isPacMonDead() 
	{
		return isPacMonDead;
	}

	public void setPacMonDead(boolean isPacMonDead) 
	{
		this.isPacMonDead = isPacMonDead;
	}
	
	public List<LevelLabel> getLabels()
	{
		return labelList;
	}
	
	private void updateLabels()
	{
		List<LevelLabel> removeList = null;
		
		for (LevelLabel l : labelList)
		{
			l.update();
			
			if (l.isExpired())
			{
				if (removeList == null)
					removeList = new ArrayList<LevelLabel>(labelList.size());
				removeList.add(l);
			}
		}
		
		if (removeList != null)
		{
			for (LevelLabel l : removeList)
			{
				labelList.remove(l);
			}
		}
	}
	
	private void updateBonuses()
	{
		List<Bonus> removeList = null;
		
		for (Bonus b : bonusList)
		{
			b.decrementCooldown();
			
			if (b.getCooldown() <= 0)
			{
				if (removeList == null)
					removeList = new ArrayList<Bonus>(bonusList.size());
				removeList.add(b);
			}
		}
		
		if (removeList != null)
		{
			for (Bonus b : removeList)
			{
				this.removeBonus(b.getX(), b.getY());
			}
		}
	}
	
	private void addBonus(Bonus bonus)
	{
		if (state.getMaze().getMazeItem(bonus.getX(), bonus.getY()) != null)
		{
			throw new IllegalArgumentException("Maze item exists in this position");
		}
		
		for (Bonus b : bonusList)
		{
			if (b.getX() == bonus.getX() && b.getY() == bonus.getY())
			{
				throw new IllegalArgumentException("Bonus already exists in this position");
			}		
		}
		
		bonusList.add(bonus);
		
		getState().getMaze().placeMazeItem(bonus.getX(), bonus.getY(), bonus.getItem());
	}
	
	private void removeBonus(int x, int y)
	{
		int removeIdx = -1;
		
		for (int i = 0; i < bonusList.size(); ++i)
		{
			Bonus b = bonusList.get(i);
			
			if (b.getX() == x && b.getY() == y)
			{
				removeIdx = i;
				break;
			}
		}
		
		if (removeIdx != -1)
		{
			bonusList.remove(removeIdx);
			getState().getMaze().clearMazeItem(x, y);
		}
	}
	
	private boolean canSetMosterMode(Monster monster)
	{
		return !monster.getMode().equals(MonsterMode.DEAD) 
			&& !monster.getMode().equals(MonsterMode.EXIT_HOME)
			&& !monster.getMode().equals(MonsterMode.HOME);
	}
	
	public void reverseMonsterDirections()
	{
		reverseIndivMonsterDirection(blinky);
		reverseIndivMonsterDirection(pinky);
		reverseIndivMonsterDirection(inky);
		reverseIndivMonsterDirection(clyde);
	}
	
	private void reverseIndivMonsterDirection(Monster monster)
	{
		if (monster.getMode().equals(MonsterMode.CHASE) || monster.getMode().equals(MonsterMode.SCATTER))
		{
			monster.attemptReverseDirection(state.getMaze());
		}
	}
	
	private LevelState state;
	private PacMon pacMon;
	private Blinky blinky;
	private Pinky pinky;
	private Inky inky;
	private Clyde clyde;
	
	private int intervalCooldown;
	private int currentInterval;
	private int frightenedCooldown;
	private int frightenedMonstersEaten;
	
	private int blinkyHomeCounter;
	private int pinkyHomeCounter;
	private int inkyHomeCounter;
	private int clydeHomeCounter;
	
	//private int pacPanFrozenCooldown;
	
	private boolean isPacMonDead;
	
	private List<Bonus> bonusList;
	private List<LevelLabel> labelList;
	
	private Random randomGenerator;
}
