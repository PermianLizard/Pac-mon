package pacmon.view.screen;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import pacmon.InterfaceImageManager;
import pacmon.control.RootManager;

public class MainMenuScreen extends MenuScreen 
{

	public MainMenuScreen(String name, RootManager rootManager) 
	{
		super(name, rootManager);
	}

	@Override
	public void render(Graphics2D g) 
	{
		super.render(g);
		
		BufferedImage titleImage = InterfaceImageManager.getImage(InterfaceImageManager.TITLE);

		g.drawImage(titleImage, getRootManager().getWidth() / 2 - (titleImage.getWidth() / 2), 10, null);
	}
	
}
