package pacmon.model.level;

import pacmon.model.maze.MazeItem;


public class Bonus 
{

	public Bonus(int x, int y, int cooldown, MazeItem item)
	{
		this.x = x;
		this.y = y;
		this.cooldown = cooldown;
		this.item = item;
	}
	
	public int getX() 
	{
		return x;
	}
	
	public int getY() 
	{
		return y;
	}
	
	public int getCooldown() 
	{
		return cooldown;
	}
	
	public MazeItem getItem() 
	{
		return item;
	}

	public void decrementCooldown()
	{
		--cooldown;
	}
	
	private int x;
	private int y;
	private int cooldown;
	private MazeItem item;
}
