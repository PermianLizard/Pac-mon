package pacmon.model.level;

public class LevelLabel 
{

	public LevelLabel(String text, int x, int y, int ttl)
	{
		this.text = text;
		this.x = x;
		this.y = y;
		this.ttl = ttl;	
	}
	
	public String getText()
	{
		return text;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getTtl()
	{
		return ttl;
	}
	
	public boolean isExpired()
	{
		return ttl <= 0;
	}
	
	protected void update()
	{
		if (ttl > 0)
			--ttl;
	}
	
	private final String text;
	private int x;
	private int y;
	private int ttl;
}
