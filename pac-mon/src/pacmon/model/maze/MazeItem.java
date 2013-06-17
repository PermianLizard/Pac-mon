package pacmon.model.maze;

public enum MazeItem 
{
	
	  Dot(10, false)
	, Energizer(50, false)
	, BonusWeed(100, true)
	, BonusEcstacy(300, true)
	, BonusShroom(500, true)
	, BonusLSD(700, true)
	, BonusCoke(1000, true)
	, BonusSteroids(2000, true)
	, BonusHeroin(3000, true)
	, BonusMeth(5000, true)
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
