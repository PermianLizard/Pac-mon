package pacmon.sound;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import pacmon.sound.util.LoopingByteInputStream;
import pacmon.sound.util.ThreadPool;

public class SoundManager extends ThreadPool 
{

	private static SoundManager instance;

	public static final String DEATH = "death.wav";
	public static final String CHOMP = "chomp.wav";
	
	public static final String[] SOUNDS = {DEATH, CHOMP};
	
	private boolean stopAll;
	
	public static SoundManager getInstance() 
	{
		if (instance == null)
			instance = new SoundManager();
		return instance;
	}
	
	private SoundManager() 
	{
		super(10);
		
		soundMap = new HashMap<String, Sound>();
		
		stopAll = false;
	}
	
	public void loadAll() throws IOException, UnsupportedAudioFileException
	{
		for (String name : SOUNDS)
		{
			loadSound(name);	
		}
	}
	
	public void play(String name, boolean loop)
	{
		Sound sound = soundMap.get(name);
		if (sound == null)
		{
			// TODO
		}
	
		runTask(new SoundPlayer(name, sound, loop));
		stopAll = false;
	}
	
	public void stopAll()
	{
		stopAll = true;
	}
	
	private void loadSound(String name) throws IOException, UnsupportedAudioFileException
	{
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(String.format("sounds/%s", name)));
		
		int length = (int)(audioStream.getFrameLength() * audioStream.getFormat().getFrameSize());
		
		byte[] samples = new byte[length];
		DataInputStream is = new DataInputStream(audioStream);
        is.readFully(samples);
        is.close();
        
        AudioFormat audioFormat = audioStream.getFormat();
        
        Sound sound = new Sound(samples, audioFormat);
		
        soundMap.put(name, sound);
	}
	
	protected void threadStarted() 
	{
        synchronized (this) 
        {
            try 
            {
                wait();
            }
            catch (InterruptedException ex) { }
        }
        
        System.out.println("thread started");
    }
	
	protected void threadStopped() 
	{
		System.out.println("thread stopped");
	}
	
	protected class SoundPlayer implements Runnable 
	{
		//private String name;
		private Sound sound;
		private boolean loop;

        public SoundPlayer(String name, Sound sound, boolean loop) 
        {
        	//this.name = name;
            this.sound = sound;
            this.loop = loop;
        }
        
        public void run() 
        {
        	System.out.println("sound player running");
        	
        	InputStream is;
        	
        	if (loop)
        	{
        		is = new LoopingByteInputStream(sound.getSamples());
        	}
        	else
        	{
        		is = new ByteArrayInputStream(sound.getSamples());
        	}

        	int bufferSize = sound.getFormat().getFrameSize() * Math.round(sound.getFormat().getSampleRate() / 10);
        	
        	SourceDataLine line;
            DataLine.Info lineInfo = new DataLine.Info(SourceDataLine.class, sound.getFormat());
        	
            try 
            {
                line = (SourceDataLine)AudioSystem.getLine(lineInfo);
                line.open(sound.getFormat(), bufferSize);
            }
            catch (LineUnavailableException ex) 
            {
                // the line is unavailable
                return;
            }
            
            line.start();
            
            byte[] buffer = new byte[bufferSize];

            if (line == null || buffer == null) 
            {
                // the line is unavailable
                return;
            }
            
            try 
            {
                int numBytesRead = 0;
                while (numBytesRead != -1) 
                {
                	 if (stopAll)
                	 {
                		 System.out.println("dad's telling me to stop");
                		 break;
                	 }
                	
                    // copy data
                    numBytesRead = is.read(buffer, 0, buffer.length);
                    if (numBytesRead != -1) 
                    {
                        line.write(buffer, 0, numBytesRead);
                    }
                }
            }
            catch (IOException ex) 
            {
                ex.printStackTrace();
            }
            
            line.drain();
            line.close();
        }
	}
	
	private Map<String, Sound> soundMap;

}
