package pacmon.view.screen;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pacmon.control.RootManager;
import pacmon.control.event.EventGenerator;
import pacmon.view.component.Component;

public class MenuScreen extends Screen 
{

	private static final int FOCUS_PADDING = 4;
	
	public MenuScreen(String name, RootManager rootManager)
	{
		super(name, rootManager, 3);
		
		components = new ArrayList<Component>();
		componentEventActionMap = new HashMap<Component,Map<String,String>>();
	}
	
	public void addComponent(Component component)
	{
		if (!components.contains(component))
		{
			components.add(component);
			component.attachToScreen(this);
		}
	}
	
	public void setComponentEventActionGroup(Component component, String eventName, String actionGroup)
	{	
		Map<String, String> eventActionMap = componentEventActionMap.get(component);
		
		if (eventActionMap == null)
		{			
			eventActionMap = new HashMap<String,String>();
			componentEventActionMap.put(component, eventActionMap);
			component.addEventListener(eventName, this);
		}
		
		eventActionMap.put(eventName, actionGroup);
	}
	
	public void render(Graphics2D g)
	{
		super.render(g);
		
		for (Component c : components)
		{
			g.drawImage(c.getImage(), c.getX(), c.getY(), null);
			
			if (c == focusedComponent)
			{
				g.setColor(Color.WHITE);
				g.setStroke(new BasicStroke(1.5f));
				g.drawRoundRect(c.getX() - FOCUS_PADDING, c.getY() - FOCUS_PADDING, c.getWidth() + FOCUS_PADDING * 2 - 1, c.getHeight() + FOCUS_PADDING * 2 - 1, 10, 10);
			}
		}
	}
	
	@Override
	public void update(BitSet keyStateBitSet) 
	{
		super.update(keyStateBitSet);

		if (components.size() > 0)
		{		
			boolean upKey = keyStateBitSet.get(KeyEvent.VK_UP);
			boolean downKey = keyStateBitSet.get(KeyEvent.VK_DOWN);
			boolean enterKey = keyStateBitSet.get(KeyEvent.VK_ENTER);
			//boolean escKey = keyStateBitSet.get(KeyEvent.VK_ESCAPE);
			
			if (upKey && !downKey && !enterKey)
			{
				focusPrevComponent();
				keyStateBitSet.set(KeyEvent.VK_UP, false);
			}
			else if (downKey && !upKey && !enterKey)
			{
				focusNextComponent();
				keyStateBitSet.set(KeyEvent.VK_DOWN, false);				
			}
			else if (enterKey)
			{
				if (focusedComponent != null)
				{
					focusedComponent.trigger();
				}
				keyStateBitSet.set(KeyEvent.VK_ENTER, false);
			}
		}
	}
	
	@Override
	public void onEventTriggered(String eventName, EventGenerator source) 
	{
		if (source.getClass().isAssignableFrom(Component.class))
		{
			Component component = (Component)source;
			
			Map<String, String> eventActionMap = componentEventActionMap.get(component);
			
			if (eventActionMap != null)
			{
				String actionGroup = eventActionMap.get(eventName);
				if (actionGroup != null)
				{
					executeActions(actionGroup);
				}
			}
		}
		else
		{
			executeActions(eventName);
		}
	}
	
	public void onShow()
	{
		this.focusNextComponent();
	}
	
	public void onHide()
	{
		this.focusedComponent = null;
	}
	
	private void focusNextComponent()
	{
		if (components.size() > 0)
		{		
			if (focusedComponent == null)
			{
				focusedComponent = components.get(0);
			}
			else
			{
				int idx = components.indexOf(focusedComponent);				
				if (idx == components.size() - 1)
				{
					focusedComponent = components.get(0);
				}
				else
				{
					focusedComponent = components.get(idx + 1);
				}
			}
		}
	}
	
	private void focusPrevComponent()
	{
		if (components.size() > 0)
		{		
			if (focusedComponent == null)
			{
				focusedComponent = components.get(components.size() - 1);
			}
			else
			{
				int idx = components.indexOf(focusedComponent);				
				if (idx == 0)
				{
					focusedComponent = null;
				}
				else
				{
					focusedComponent = components.get(idx - 1);
				}
			}
		}
	}
	
	private List<Component> components;	
	private Component focusedComponent;
	private Map<Component,Map<String,String>> componentEventActionMap;
}
