package pacmon.control.action;

import pacmon.view.screen.Screen;

public class ExitAction implements Action 
{

	@Override
	public void execute(Screen screen) 
	{
		System.exit(0);
	}

}
