package pacmon.view.screen.effect;

import pacmon.view.screen.Screen;

public abstract class ScreenEffect {

	public abstract void screenShow(Screen screen);
	
	public abstract void screenHide(Screen screen);
	
	public abstract void update(Screen screen);
	public abstract void render(Screen screen);

}
