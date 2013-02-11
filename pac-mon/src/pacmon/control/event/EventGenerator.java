package pacmon.control.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventGenerator 
{
	
	public EventGenerator()
	{
		eventListenerMap = new HashMap<String, List<EventListener>>(); 
	}
	
	public void addEventListener(String eventName, EventListener listener)
	{
		List<EventListener> listenerList = eventListenerMap.get(eventName);
		if (listenerList == null)
		{
			listenerList = new ArrayList<EventListener>();
			eventListenerMap.put(eventName, listenerList);
		}		
		if (!listenerList.contains(listener))
		{
			listenerList.add(listener);
		}
	}	
	
	public void triggerEvent(String eventName, EventGenerator source)
	{
		List<EventListener> listenerList = eventListenerMap.get(eventName);
		if (listenerList != null)
		{
			for (EventListener listener : listenerList)
			{
				listener.onEventTriggered(eventName, source);
			}
		}		
	}
	
	private Map<String, List<EventListener>> eventListenerMap;	
}
