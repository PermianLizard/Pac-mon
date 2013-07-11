package pacmon.model;

public class GameSoundEvent {

	public static final int TYPE_PLAY = 1;
	public static final int TYPE_STOP = 2;
	public static final int TYPE_STOP_ALL = 3;
	public static final int TYPE_PAUSE = 4;
	public static final int TYPE_UNPAUSE = 5;
	
	private final int type;
	private final String name;
	private final boolean loop;
	
	public GameSoundEvent(int type) {
		this(type, null, false);
	}
	
	public GameSoundEvent(int type, String name) {
		this(type, name, false);
	}
	
	public GameSoundEvent(int type, String name, boolean loop) {
		this.type = type;
		this.name = name;
		this.loop = loop;
	}

	public int getType() 
	{
		return type;
	}

	public String getName() 
	{
		return name;
	}

	public boolean isLoop() 
	{
		return loop;
	}
}
