package pacmon;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FontManager 
{
	
	//public static final String ADVENTURE_SUBTITLES = "Adventure Subtitles.ttf";
	public static final String DEFAULT = "Standard0756.ttf";
	
	
	public static void loadFonts() 
	{
	    for (String name : names) 
	    {
	    	System.out.println("Loading font: "+name);
	    	cache.put(name, getFont(name));
	    }
	}
	
	public static Font getFont(String name) 
	{
		Font font = null;
	    if (cache != null) 
	    {
	    	if ((font = cache.get(name)) != null) 
	    	{
	    		return font;
	    	}
	    }
	    
	    String fName = (new File(".")).getAbsolutePath().replaceAll(".", "")+"fonts"+File.separator+name;
	    
	    try 
	    {
	    	InputStream is = new FileInputStream(fName);
	    	
	    	font = Font.createFont(Font.TRUETYPE_FONT, is);	    
	    } 
	    catch (Exception ex) 
	    {
	    	ex.printStackTrace();
	    	System.err.println(fName + " not loaded.  Using serif font.");
	    	font = new Font("serif", Font.PLAIN, 1);
	    }
	    return font;
	}
	
	private static String[] names = { DEFAULT };
	private static Map<String, Font> cache = new ConcurrentHashMap<String,Font>(names.length);	
	
}
