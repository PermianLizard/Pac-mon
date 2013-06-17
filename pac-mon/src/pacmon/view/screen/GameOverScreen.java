package pacmon.view.screen;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.BitSet;

import pacmon.control.RootManager;

public class GameOverScreen extends Screen 
{

	private String exitScreenName;
	
	public GameOverScreen(String name, RootManager rootManager, String exitScreenName) 
	{
		super(name, rootManager, 3);
		
		this.exitScreenName = exitScreenName;
	}

	public void render(Graphics2D g)
	{
		super.render(g);
	}
	
	public void update(BitSet keyStateBitSet)
	{
		super.update(keyStateBitSet);
		
		boolean enterKey = keyStateBitSet.get(KeyEvent.VK_ENTER);
		
		if (enterKey)
		{
			getRootManager().showScreen(exitScreenName);
		}
	}
	
}
