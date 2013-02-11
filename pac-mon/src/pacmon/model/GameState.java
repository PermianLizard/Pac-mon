package pacmon.model;

public class GameState 
{

	public GameState(int levelNum, int totalScore, int livesLeft)
	{
		this.levelNum = levelNum;
		this.totalScore = totalScore;
		this.livesLeft = livesLeft;
	}
	
	public int getLevelNum() 
	{
		return levelNum;
	}
	
	public void setLevelNum(int levelNum) 
	{
		this.levelNum = levelNum;
	}
	
	public int getTotalScore()
	{
		return this.totalScore;
	}
	
	public void addToTotalScore(int amount)
	{
		totalScore += amount;
	}
	
	public int getLivesLeft() 
	{
		return livesLeft;
	}

	public void setLivesLeft(int livesLeft) 
	{
		this.livesLeft = livesLeft;
	}
	
	private int levelNum;
	private int totalScore;
	private int livesLeft;
}
