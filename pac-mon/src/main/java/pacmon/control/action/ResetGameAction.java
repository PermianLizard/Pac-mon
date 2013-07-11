package pacmon.control.action;

import pacmon.model.Game;
import pacmon.view.screen.Screen;
import pacmon.view.screen.game.MazeRenderer;

public class ResetGameAction implements Action {

	@Override
	public void execute(Screen screen) 
	{
		Game.getInstance().reset();
		MazeRenderer.cleanup();
	}

}
