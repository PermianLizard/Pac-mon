package pacmon.view.screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import pacmon.FontManager;
import pacmon.HighScoresManager;
import pacmon.control.RootManager;
import pacmon.highscore.HighScoreTable;

public class HighScoresScreen extends MenuScreen 
{

	private static DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final int TITLE_START_HEIGHT = 30;
	
	private static final int TABLE_HEADER_START_HEIGHT = 80;
	private static final int TABLE_START_HEIGHT = 120;
	private static final int TABLE_ROW_HEIGHT = 33;
	
	private static final int COL_1_POS = 40;
	private static final int COL_2_POS = 120;
	private static final int COL_3_POS = 270;
	//private static final int COL_4_POS = 260;
	
	private static final String HIGH_SCORES_TEXT = "High Scores";
	private static final String NO_ENTRIES_TEXT = "-No Entries-";
	
	private static Color[] ENTRY_COLORS;
	
	private static String[] ENTRY_RANK = new String[] {"1st",
		"2nd",
		"3rd",
		"4th",
		"5th",
		"6th",
		"7th",
		"8th",
		"9th",
		"10th"};
	
	static {
		ENTRY_COLORS = new Color[10];

		int r = 0;
		int g = 255;
		int b = 127;
		for (int i=0; i<10; ++i) {
			ENTRY_COLORS[i] = new Color(r, g, b);
			g -= 12;
			b -= 12;
		}
	}
	
	private List<HighScoreTable.HighScoreEntry> entryList;
	
	public HighScoresScreen(String name, RootManager rootManager, String exitScreenName) 
	{
		super(name, rootManager);
	}

	@Override
	public void onShow() 
	{
		super.onShow();
		
		try 
		{
			HighScoreTable highScoresTable = HighScoresManager.loadHighScores();
			
			entryList =  highScoresTable.getEntries();
		} 
		catch (IOException e) 
		{
			System.err.println("unable to get high scores table");
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			System.err.println("unable to get high scores table");
			e.printStackTrace();
		}
	}

	public void onHide()
	{
		//entryList = null;
	}
	
	public void render(Graphics2D g)
	{
		super.render(g);
		
		Font defaultFont = FontManager.getFont(FontManager.DEFAULT).deriveFont(16.0f).deriveFont(Font.BOLD);
		
		g.setFont(defaultFont);
		g.setColor(Color.CYAN);
		Rectangle textBounds = g.getFontMetrics(defaultFont).getStringBounds(HIGH_SCORES_TEXT, g).getBounds();
		g.drawString(HIGH_SCORES_TEXT, (int)((getRootManager().getWidth() / 2) - (textBounds.getWidth() / 2)), TITLE_START_HEIGHT);
		
		g.setFont(defaultFont);
		g.setColor(Color.YELLOW);
		g.drawString("RANK", COL_1_POS, TABLE_HEADER_START_HEIGHT);
		
		g.setFont(defaultFont);
		g.setColor(Color.YELLOW);
		g.drawString("SCORE", COL_2_POS, TABLE_HEADER_START_HEIGHT);
		
		g.setFont(defaultFont);
		g.setColor(Color.YELLOW);
		g.drawString("DATE", COL_3_POS, TABLE_HEADER_START_HEIGHT);
		
		if (entryList == null || entryList.size() == 0)
		{
			g.setFont(defaultFont);
			g.setColor(Color.WHITE);
			textBounds = g.getFontMetrics(defaultFont).getStringBounds(NO_ENTRIES_TEXT, g).getBounds();
			g.drawString(NO_ENTRIES_TEXT, (int)((getRootManager().getWidth() / 2) - (textBounds.getWidth() / 2)), TABLE_START_HEIGHT);
		}
		else
		{
			int i = 0;
			for (HighScoreTable.HighScoreEntry entry : entryList)
			{
				Color entryCol = ENTRY_COLORS[i];
				
				g.setFont(defaultFont);
				g.setColor(entryCol);
				g.drawString(ENTRY_RANK[i], COL_1_POS, TABLE_START_HEIGHT + (i * TABLE_ROW_HEIGHT));
				
				g.setFont(defaultFont);
				g.setColor(entryCol);
				g.drawString(String.format("%s", entry.getScore()), COL_2_POS, TABLE_START_HEIGHT + (i * TABLE_ROW_HEIGHT));
				
				g.setFont(defaultFont);
				g.setColor(entryCol);
				g.drawString(String.format("%s", dateFormatter.format(entry.getDate())), COL_3_POS, TABLE_START_HEIGHT + (i * TABLE_ROW_HEIGHT));
				
				//g.setFont(defaultFont);
				//g.setColor(Color.WHITE);
				//g.drawString(String.format("%s", entry.getLevel()), COL_2_POS, TABLE_START_HEIGHT + (i * TABLE_ROW_HEIGHT));
				
				
				
				//g.setFont(defaultFont);
				//g.setColor(Color.WHITE);
				//g.drawString(String.format("%s", dateFormatter.format(entry.getDate())), COL_4_POS, TABLE_START_HEIGHT + (i * TABLE_ROW_HEIGHT));
				
				++i;
			}
		}
	}
}
