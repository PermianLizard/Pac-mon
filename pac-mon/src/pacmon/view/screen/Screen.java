package pacmon.view.screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pacmon.control.RootManager;
import pacmon.control.action.Action;
import pacmon.control.event.EventGenerator;
import pacmon.control.event.EventListener;
import pacmon.sound.SoundManager;

public class Screen  implements EventListener
{
	
	public Screen(String name, RootManager rootManager, int soundThreads)
	{
		this.name = name;
		this.rootManager = rootManager;
		
		rootManager.setScreen(this);
		image = new BufferedImage(rootManager.getWidth(), rootManager.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g = image.createGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, image.getWidth(), image.getHeight());
		g.dispose();
		
		actionMap = new HashMap<String,List<Action>>();
		
		soundManager = new SoundManager(soundThreads);
	}
	
	public String getName()
	{
		return name;
	}
	
	public RootManager getRootManager()
	{
		return rootManager;
	}	
	
	public void render(Graphics2D g)
	{
		g.drawImage(image, 0, 0, null);
	}
	
	public void update(BitSet keyStateBitSet)
	{
	}	
	
	public void keyPressed(KeyEvent keyEvent) 
	{
	}

	public void keyReleased(KeyEvent keyEvent) 
	{
	}
	
	public void mouseClicked(MouseEvent mouseEvent) 
	{

	}

	public void mouseEntered(MouseEvent mouseEvent) 
	{
		
	}

	public void mouseExited(MouseEvent mouseEvent) 
	{
		
	}

	public void mousePressed(MouseEvent mouseEvent) 
	{
		
	}

	public void mouseReleased(MouseEvent arg0) 
	{
		
	}
	
	public void mouseDragged(MouseEvent mouseEvent) 
	{
		
	}

	public void mouseMoved(MouseEvent mouseEvent) 
	{
		
	}
	
	public void onShow()
	{

	}
	
	public void onHide()
	{
		
	}
	
	@Override
	public void onEventTriggered(String eventName, EventGenerator source) 
	{
		executeActions(eventName);
	}
	
	public void addAction(String actionGroup, Action action)
	{
		List<Action> actionList = actionMap.get(actionGroup);		
		if (actionList == null)
		{
			actionList = new ArrayList<Action>();
			actionMap.put(actionGroup, actionList);
		}
		if (!actionList.contains(action))
		{
			actionList.add(action);
		}
	}
	
	public BufferedImage getImage() 
	{
		return this.image;
	}
	
	public void setImage(BufferedImage image) 
	{
		this.image = image;
	}

	protected void executeActions(String actionGroup)
	{
		List<Action> actionList = actionMap.get(actionGroup);
		if (actionList != null)
		{
			for (Action a : actionList)
			{
				a.execute(this);
			}
		}
	}
	
	public boolean isSoundPausedSetting() {
		return soundPausedSetting;
	}

	public void setSoundPausedSetting(boolean soundPausedSetting) {
		this.soundPausedSetting = soundPausedSetting;
	}

	public SoundManager getSoundManager()
	{
		return soundManager;
	}
	
	private String name;	
	private RootManager rootManager;	
	private BufferedImage image;
	
	private Map<String,List<Action>> actionMap;
	
	private SoundManager soundManager;
	private boolean soundPausedSetting;
}
