package pacmon.view.screen.effect;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

import pacmon.GraphicsManager;
import pacmon.model.Game;
import pacmon.view.screen.Screen;
import pacmon.view.screen.game.GameRenderer;

public class InGameScreenEffect extends ScreenEffect 
{

	@Override
	public void screenShow(Screen screen) 
	{
		BufferedImage screenImage = screen.getImage();
		
		Graphics2D g = (Graphics2D)screenImage.getGraphics();
		
		GraphicsManager.initializeGraphicsObject(g);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, screenImage.getWidth(), screenImage.getHeight());
		
		GameRenderer.renderGame((Graphics2D)g, Game.getInstance());
		
		float[] factors = new float[] {
				0.4f, 0.4f, 0.4f, 1.0f
		    };
		    float[] offsets = new float[] {
		    	0.0f, 0.0f, 0.0f, 1.0f
		    };
		    
		RescaleOp op = new RescaleOp(factors, offsets, null);
		BufferedImage darker = op.filter(screenImage, null);
		
		screen.setImage(darker);
	}

	@Override
	public void screenHide(Screen screen) 
	{

	}

	@Override
	public void update(Screen screen) 
	{

	}

	@Override
	public void render(Screen screen) 
	{

	}

}
