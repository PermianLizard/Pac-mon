package pacmon.view.screen;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import pacmon.control.RootManager;

public class GameMenuScreen extends MenuScreen {

	public GameMenuScreen(String name, RootManager rootManager) {
		super(name, rootManager);
	}

	public void onShow()
	{
		super.onShow();
	}
	
	public void onHide()
	{
		super.onHide();
	}
	
	public void render(Graphics2D g)
	{
		super.render(g);	
	}
	
	@Override
	public void keyPressed(KeyEvent keyEvent) {
		super.keyPressed(keyEvent);
		
		if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) 
		{
			this.onEventTriggered("Continue", null);
		}
	}
	
}
