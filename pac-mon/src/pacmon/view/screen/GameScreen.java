package pacmon.view.screen;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.BitSet;

import pacmon.control.RootManager;
import pacmon.model.Game;
import pacmon.sound.SoundManager;
import pacmon.view.screen.game.GameRenderer;

public class GameScreen extends Screen 
{

	public GameScreen(String name, RootManager rootManager, String optionsScreenName, String gameOverScreenName)
	{
		super(name, rootManager);
		
		pausedKeyDownCache = false;
		
		this.optionsScreenName = optionsScreenName;
		this.gameOverScreenName = gameOverScreenName;
	}
	
	public void render(Graphics2D g)
	{
		super.render(g);		
		GameRenderer.renderGame(g, Game.getInstance());
	}
	
	public void onShow()
	{
		
	}
	
	public void update(BitSet keyStateBitSet)
	{
		super.update(keyStateBitSet);
		
		boolean escKey = keyStateBitSet.get(KeyEvent.VK_ESCAPE);
		boolean upKey = keyStateBitSet.get(KeyEvent.VK_UP);
		boolean leftKey = keyStateBitSet.get(KeyEvent.VK_LEFT);
		boolean downKey = keyStateBitSet.get(KeyEvent.VK_DOWN);
		boolean rightKey = keyStateBitSet.get(KeyEvent.VK_RIGHT);
		boolean pauseKey = keyStateBitSet.get(KeyEvent.VK_P);
		
		Game game = Game.getInstance();
		
		if (escKey)
		{
			getRootManager().showScreen(optionsScreenName);
		}
		
		if (pauseKey && !pausedKeyDownCache)
		{
			game.controlPause();
		}
		
		pausedKeyDownCache = pauseKey;	
		
		if (upKey && !leftKey && !downKey && !rightKey)
		{
			game.controlUp();
		}
		else if (!upKey && leftKey && !downKey && !rightKey)
		{
			game.controlLeft();
		}
		if (!upKey && !leftKey && downKey && !rightKey)
		{
			game.controlDown();
		}
		if (!upKey && !leftKey && !downKey && rightKey)
		{
			game.controlRight();
		}
		
		//keyStateBitSet.set(KeyEvent.VK_UP, false);
		//keyStateBitSet.set(KeyEvent.VK_LEFT, false);
		//keyStateBitSet.set(KeyEvent.VK_DOWN, false);
		//keyStateBitSet.set(KeyEvent.VK_RIGHT, false);
		
		Game theGame = Game.getInstance();
		theGame.update();
		
		if (theGame.isGameOver())
		{
			getRootManager().showScreen(gameOverScreenName);
		}
	}	
	
	private boolean pausedKeyDownCache;
	private String gameOverScreenName;
	private String optionsScreenName;

}
