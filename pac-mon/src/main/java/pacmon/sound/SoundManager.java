package pacmon.sound;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import pacmon.sound.util.LoopingByteInputStream;
import pacmon.sound.util.ThreadPool;

public class SoundManager extends ThreadPool  {

	private Object pausedLock;
    private boolean paused;
    
    private List<SoundPlayer> activeSoundList;
	
	public SoundManager(int threads) {
		super(threads);
		
		pausedLock = new Object();
		
		activeSoundList = Collections.synchronizedList(new ArrayList<SoundPlayer>());
		
		// notify threads in pool it's ok to start
		synchronized (this) 
		{
            notifyAll();
        }
	}
	
	public void setPaused(boolean paused) 
	{
        if (this.paused != paused) 
        {
            synchronized (pausedLock) 
            {
                this.paused = paused; 
                if (!paused) 
                {
                    // restart sounds
                    pausedLock.notifyAll();
                }
            }
        }
    }
	
	public boolean isPaused() 
	{
        return paused;
    }
	
	public void play(String name, boolean loop)
	{
		if (isPlaying(name))
			this.stop(name);
		
		Sound sound = SoundLoader.getInstance().getSound(name);
		if (sound == null)
		{
			// TODO
		}
	
		SoundPlayer player = new SoundPlayer(this, name, sound, loop);
		runTask(player);
	}
	
	public void stopAll()
	{
		System.out.println("stopping all sounds");
		for (SoundPlayer player : this.activeSoundList)
		{
			player.stop();
		}
	}
	
	public void stop(String name)
	{
		for (SoundPlayer player : this.activeSoundList)
		{
			if (player.getName().equals(name))
				player.stop();
		}
	}
	
	public boolean isPlaying(String name)
	{
		for (SoundPlayer player : this.activeSoundList)
		{
			if (player.getName().equals(name))
				return true;
		}
		return false;
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
    }
	
	protected void threadStopped() 
	{
	}
	
	private void soundStarted(SoundPlayer player)
	{
		activeSoundList.add(player);
	}
	
	private void soundEnded(SoundPlayer player)
	{
		activeSoundList.remove(player);
	}
	
	protected class SoundPlayer implements Runnable 
	{
		
		private final SoundManager manager;
		
		private final String name;
		private final Sound sound;
		private boolean loop;
		private boolean forceStop;

        public SoundPlayer(SoundManager manager, String name, Sound sound, boolean loop) 
        {
        	this.manager = manager;
        	this.name = name;
            this.sound = sound;
            this.loop = loop;
            
            this.forceStop = false;
        }
        
        public String getName() 
        {
			return name;
		}

		public boolean isLoop() 
		{
			return loop;
		}

		/*public void endLoop()
		{
			if (loop)
				this.loop = false;
		}*/
		
		public void stop()
		{
			this.forceStop = true;
		}
		
		public void run() 
        {
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

            this.manager.soundStarted(this);
            
            try 
            {
                int numBytesRead = 0;
                while (numBytesRead != -1) 
                {
                	synchronized (pausedLock) 
                	{
                		if (paused) 
                		{
                            try 
                            {
                                pausedLock.wait();
                            }
                            catch (InterruptedException ex) 
                            {
                                return;
                            }
                        }
                	}
                	
                	if (forceStop)
                	{
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

            this.manager.soundEnded(this);
            
            line.drain();
            line.close();
        }
	}

}
