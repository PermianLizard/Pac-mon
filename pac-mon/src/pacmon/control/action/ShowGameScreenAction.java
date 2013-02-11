package pacmon.control.action;

import pacmon.control.RootManager;
import pacmon.view.screen.Screen;

public class ShowGameScreenAction implements Action 
{

	public ShowGameScreenAction(String screenName)
	{
		this.screenName = screenName;
	}
	
	@Override
	public void execute(Screen screen) 
	{		
		RootManager rootManager = screen.getRootManager();
		if (rootManager != null)
		{			
			rootManager.showScreen(screenName);
		}
	}
	
	private String screenName;
}
