package pacmon;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FontManager 
{

	public static final String FONT_PATH = Globals.RESOURCE_PATH+File.separator+"fonts"+File.separator;
	
	public static final String DEFAULT = "Standard0756.ttf";

	public static void loadFonts() throws FileNotFoundException, FontFormatException, IOException
	{
	    for (String name : fontFiles) 
	    {
	    	loadFont(name);
	    }
	}
	
	public static Font loadFont(String name) throws FileNotFoundException, FontFormatException, IOException
	{
		Font font = Font.createFont(Font.TRUETYPE_FONT, 
				new FileInputStream(FONT_PATH + name));
		
		cache.put(name, font);
		
		System.out.println("Loaded font: "+name);
		
		return font;
	}
	
	public static Font getFont(String name)
	{
	    return cache.get(name);
	}
	
	private static String[] fontFiles = { DEFAULT };
	private static Map<String, Font> cache = new HashMap<String,Font>(fontFiles.length);	
	
}
