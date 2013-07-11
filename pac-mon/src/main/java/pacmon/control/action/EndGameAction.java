package pacmon.control.action;

import java.io.IOException;

import pacmon.HighScoresManager;
import pacmon.model.Game;
import pacmon.view.screen.Screen;
import pacmon.view.screen.game.MazeRenderer;

public class EndGameAction implements Action 
{

	@Override
	public void execute(Screen screen) 
	{
		Game game = Game.getInstance();
		
		int score = game.getState().getTotalScore();
		
		if (!game.getLevel().isComplete())
		{
			score += game.getLevel().getState().getScore();
		}
		
		if (score > 0)
		{
			System.out.println(String.format("registering score %s", score));
			
			try 
			{
				HighScoresManager.registerScore("", score, game.getState().getLevelNum());
			} 
			catch (IOException e) 
			{
				System.err.println("unable to register game score");
				e.printStackTrace();
			} 
			catch (ClassNotFoundException e) 
			{
				System.err.println("unable to register game score");
				e.printStackTrace();
			}
		}
		
		Game.getInstance().clear();
		MazeRenderer.cleanup();
	}

}
