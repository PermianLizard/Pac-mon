package pacmon.view.screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import pacmon.FontManager;
import pacmon.HighScoresManager;
import pacmon.control.RootManager;
import pacmon.highscore.HighScoreTable;

public class HighScoresScreen extends Screen 
{

	private static DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final int TABLE_START_HEIGHT = 120;
	private static final int TABLE_ROW_HEIGHT = 33;
	
	private static final int COL_1_POS = 90;
	private static final int COL_2_POS = 115;
	private static final int COL_3_POS = 260;
	
	private static final String NO_ENTRIES_TEXT = "-No Entries-";
	
	private String exitScreenName;
	
	private List<HighScoreTable.HighScoreEntry> entryList;
	
	public HighScoresScreen(String name, RootManager rootManager, String exitScreenName) 
	{
		super(name, rootManager, 3);
		
		this.exitScreenName = exitScreenName;
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
		entryList = null;
	}
	
	public void render(Graphics2D g)
	{
		super.render(g);
		
		if (entryList == null || entryList.size() == 0)
		{
			Font defaultFont = FontManager.getFont(FontManager.DEFAULT).deriveFont(16.0f);
			
			g.setFont(defaultFont);
			g.setColor(Color.WHITE);
			Rectangle textBounds = g.getFontMetrics(defaultFont).getStringBounds(NO_ENTRIES_TEXT, g).getBounds();
			g.drawString(NO_ENTRIES_TEXT, (int)((getRootManager().getWidth() / 2) - (textBounds.getWidth() / 2)), TABLE_START_HEIGHT);
		}
		else
		{
			Font rankFont = FontManager.getFont(FontManager.DEFAULT).deriveFont(16.0f);	
			Font scoreFont = FontManager.getFont(FontManager.DEFAULT).deriveFont(18.0f);
			Font dateFont = FontManager.getFont(FontManager.DEFAULT).deriveFont(12.0f);
			
			int i = 0;
			for (HighScoreTable.HighScoreEntry entry : entryList)
			{
				g.setFont(rankFont);
				g.setColor(Color.WHITE);
				g.drawString(String.format("%s", i+1), COL_1_POS, TABLE_START_HEIGHT + (i * TABLE_ROW_HEIGHT));
				
				g.setFont(scoreFont);
				g.setColor(Color.WHITE);
				g.drawString(String.format("%s", entry.getScore()), COL_2_POS, TABLE_START_HEIGHT + (i * TABLE_ROW_HEIGHT));
				
				g.setFont(dateFont);
				g.setColor(Color.WHITE);
				g.drawString(String.format("%s", dateFormatter.format(entry.getDate())), COL_3_POS, TABLE_START_HEIGHT + (i * TABLE_ROW_HEIGHT));
				
				++i;
			}
		}
	}
	
	@Override
	public void keyPressed(KeyEvent keyEvent) 
	{
		super.keyPressed(keyEvent);
		
		if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			getRootManager().showScreen(exitScreenName);
		}
	}

}
