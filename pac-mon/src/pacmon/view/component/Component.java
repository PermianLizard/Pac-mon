package pacmon.view.component;

import java.awt.image.BufferedImage;

import pacmon.control.event.EventGenerator;
import pacmon.view.screen.Screen;

public class Component extends EventGenerator
{
	
	public static final String EVENT_TRIGGER = "trigger";
	
	public Component(int width, int height)
	{
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);	
	}
	
	public int getX()
	{
		return x;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public int getWidth()
	{
		return image.getWidth();
	}
	
	public int getHeight()
	{
		return image.getHeight();
	}
	
	public BufferedImage getImage()
	{
		return image;
	}
	
	public boolean isFocusEnabled()
	{
		return focusEnabled;
	}
	
	public void setFocusEnabled(boolean b)
	{
		focusEnabled = b;
	}
	
	public void attachToScreen(Screen screen)
	{
		this.screen = screen;
	}	
	
	public Screen getScreen()
	{
		return screen;
	}	

	public void trigger()
	{
		triggerEvent(EVENT_TRIGGER, this);
	}

	private Screen screen;
	
	private int x;
	private int y;
	
	private BufferedImage image;
	
	private boolean focusEnabled;
	
	
}
