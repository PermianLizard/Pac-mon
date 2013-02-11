package pacmon.model.maze;

public enum MazeItem 
{
	
	  Dot(10, false)
	, Energizer(50, false)
	, BonusCherries(100, true)
	, BonusStrawberry(300, true)
	, BonusPeach(500, true)
	, BonusApple(700, true)
	, BonusMelon(1000, true)
	, BonusGalaxian(2000, true)
	, BonusBell(3000, true)
	, BonusKey(5000, true)
	;
	
	public int getPointWorth()
	{
		return this.pointWorth;
	}
	
	public boolean isBonus() 
	{
		return isBonus;
	}
	
	private MazeItem(int pointWorth, boolean isBonus)
	{
		this.pointWorth = pointWorth;
		this.isBonus = isBonus;
	}
	  
	private int pointWorth;
	private boolean isBonus;
}
