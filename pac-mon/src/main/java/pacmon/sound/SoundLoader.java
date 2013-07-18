package pacmon.sound;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundLoader 
{

	private static SoundLoader instance;

	public static final String DEATH = "death.wav";
	public static final String CHOMP = "chomp.wav";
	public static final String BEGINNING = "beginning.wav";
	public static final String EAT_FRUIT = "eatfruit.wav";
	public static final String EAT_GHOST = "eatghost.wav";
	public static final String INTERMISSION = "intermission.wav";
	
	public static final String[] SOUNDS = {DEATH, CHOMP, BEGINNING, EAT_FRUIT, EAT_GHOST, INTERMISSION};
	
	public static SoundLoader getInstance() 
	{
		if (instance == null)
			instance = new SoundLoader();
		return instance;
	}
	
	private SoundLoader() 
	{
		soundMap = new HashMap<String, Sound>();		
	}
	
	public void loadAll() throws IOException, UnsupportedAudioFileException
	{
		for (String name : SOUNDS)
		{
			loadSound(name);	
		}
	}
	
	public Sound getSound(String name) 
	{
		return this.soundMap.get(name);
	}
	
	private void loadSound(String name) throws IOException, UnsupportedAudioFileException
	{
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(String.format("resources/sounds/%s", name)));
		
		int length = (int)(audioStream.getFrameLength() * audioStream.getFormat().getFrameSize());
		
		byte[] samples = new byte[length];
		DataInputStream is = new DataInputStream(audioStream);
        is.readFully(samples);
        is.close();
        
        AudioFormat audioFormat = audioStream.getFormat();
        
        Sound sound = new Sound(samples, audioFormat);
		
        soundMap.put(name, sound);
	}
	
	private Map<String, Sound> soundMap;

}
