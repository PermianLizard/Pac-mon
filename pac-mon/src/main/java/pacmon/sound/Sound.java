package pacmon.sound;

import javax.sound.sampled.AudioFormat;

public class Sound 
{
	private byte[] samples;
	private AudioFormat format;

    public Sound(byte[] samples, AudioFormat format) 
    {
        this.samples = samples;
        this.format = format;
    }
    
    public byte[] getSamples() 
    {
        return samples;
    }

	public AudioFormat getFormat() 
	{
		return format;
	}    
    
}
