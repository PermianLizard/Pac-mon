package pacmon.view.screen;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

import pacmon.control.RootManager;
import pacmon.model.Game;
import pacmon.view.screen.game.GameRenderer;

public class GameMenuScreen extends MenuScreen {

	public GameMenuScreen(String name, RootManager rootManager) {
		super(name, rootManager);
	}

	public void onShow()
	{
		super.onShow();

		GameRenderer.renderGame((Graphics2D)getImage().getGraphics(), Game.getInstance());
		
		float[] factors = new float[] {
			0.4f, 0.4f, 0.4f, 1.0f
	    };
	    float[] offsets = new float[] {
	    	0.0f, 0.0f, 0.0f, 1.0f
	    };
	    
	    RescaleOp op = new RescaleOp(factors, offsets, null);
	    BufferedImage darker = op.filter(getImage(), null);
		
	    this.setImage(darker);
	}
	
	public void onHide()
	{
		super.onHide();
	}
	
	public void render(Graphics2D g)
	{
		super.render(g);	
		//GameRenderer.renderGame(g, Game.getInstance());
	}
	
}
