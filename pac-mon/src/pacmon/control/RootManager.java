package pacmon.control;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

import pacmon.view.screen.Screen;

public class RootManager implements KeyListener
{
	
	public RootManager(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		screenMap = new HashMap<String,Screen>();
		
		keyStateBitSet = new BitSet(525);
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	@Override
	public void keyPressed(KeyEvent keyEvent) 
	{
		keyStateBitSet.set(keyEvent.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) 
	{
		keyStateBitSet.set(keyEvent.getKeyCode(), false);
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {}

	public void render(Graphics2D g)
	{
		if (currentScreen != null)
		{
			currentScreen.render(g);
		}
	}
	
	public void update()
	{
		if (currentScreen != null)
		{
			currentScreen.update(keyStateBitSet);
		}
	}

	public void showScreen(String name)
	{
		Screen screen = screenMap.get(name);
		if (screen != null)
		{
			if (currentScreen != null)
			{
				//SoundManager.getInstance().stopAll();
				
				currentScreen.getSoundManager().setPaused(true);
				currentScreen.onHide();
			}			
			currentScreen = screen;
			
			currentScreen.onShow();
			currentScreen.getSoundManager().setPaused(screen.isSoundPausedSetting());
		}
	}
	
	public void setScreen(Screen screen)
	{
		if (screen.getRootManager() != this)
			throw new IllegalArgumentException("Screen object must belong to this Game object");
		
		screenMap.put(screen.getName(), screen);
	}
	
	private int width;
	private int height;
	
	private Map<String,Screen> screenMap;
	private Screen currentScreen;
	
	private BitSet keyStateBitSet;
	
	//private Game game;
}
