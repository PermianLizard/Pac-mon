package pacmon.control.event;

public interface EventListener 
{
	
	public void onEventTriggered(String eventName, EventGenerator source);	
}
