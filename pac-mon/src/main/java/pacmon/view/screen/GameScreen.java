package pacmon.view.screen;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.BitSet;

import pacmon.control.RootManager;
import pacmon.model.Game;
import pacmon.model.GameSoundEvent;
import pacmon.view.screen.game.GameRenderer;

public class GameScreen extends Screen 
{

	public GameScreen(String name, RootManager rootManager, String optionsScreenName, String gameOverScreenName)
	{
		super(name, rootManager, 10);
		
		pausedKeyDownCache = false;
		
		this.optionsScreenName = optionsScreenName;
		this.gameOverScreenName = gameOverScreenName;
	}
	
	public void render(Graphics2D g)
	{
		super.render(g);		
		GameRenderer.renderGame(g, Game.getInstance());
	}
	
	@Override
	public void keyPressed(KeyEvent keyEvent) {
		super.keyPressed(keyEvent);
		
		if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) 
		{
			getRootManager().showScreen(optionsScreenName);
		}
	}
	
	public void update(BitSet keyStateBitSet)
	{
		super.update(keyStateBitSet);
		
		//boolean escKey = keyStateBitSet.get(KeyEvent.VK_ESCAPE);
		boolean upKey = keyStateBitSet.get(KeyEvent.VK_UP);
		boolean leftKey = keyStateBitSet.get(KeyEvent.VK_LEFT);
		boolean downKey = keyStateBitSet.get(KeyEvent.VK_DOWN);
		boolean rightKey = keyStateBitSet.get(KeyEvent.VK_RIGHT);
		boolean pauseKey = keyStateBitSet.get(KeyEvent.VK_P);
		
		Game game = Game.getInstance();
		
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
		
		while (theGame.hasSoundEvent())
		{
			GameSoundEvent event = theGame.pollSoundEvent();
			
			if (event.getType() == GameSoundEvent.TYPE_PLAY) 
			{
				getSoundManager().play(event.getName(), event.isLoop());
			}
			else if (event.getType() == GameSoundEvent.TYPE_STOP)
			{
				getSoundManager().stop(event.getName());
			}
			else if (event.getType() == GameSoundEvent.TYPE_STOP_ALL)
			{
				getSoundManager().stopAll();
			}
			else if (event.getType() == GameSoundEvent.TYPE_PAUSE)
			{
				this.getSoundManager().setPaused(true);
				this.setSoundPausedSetting(true);
			}
			else if (event.getType() == GameSoundEvent.TYPE_UNPAUSE)
			{
				this.getSoundManager().setPaused(false);
				this.setSoundPausedSetting(false);
			}
		}
	}	
	
	private boolean pausedKeyDownCache;
	private String gameOverScreenName;
	private String optionsScreenName;

}
