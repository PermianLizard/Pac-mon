package pacmon.highscore;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class HighScoreTable implements Serializable 
{

	private static final long serialVersionUID = 1L;
	
	private static final int SLOTS = 10;
	
	private List<HighScoreEntry> entryList;
	
	public HighScoreTable()
	{
		entryList = new LinkedList<HighScoreEntry>();
	}

	private void writeObject(ObjectOutputStream s) throws IOException
	{
		s.defaultWriteObject();
		s.writeObject(entryList);
	}
	
	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException 
	{
		s.defaultReadObject();
		entryList = (List<HighScoreEntry>)s.readObject();
	}
	
	public List<HighScoreEntry> getEntries()
	{
		return Collections.unmodifiableList(entryList);
	}
	
	public boolean processScore(String name, int score, int level, Date date)
	{
		HighScoreEntry candidateEntry = new HighScoreEntry(name, score, level, date);
		
		if (entryList.size() == 0) // first entry
		{
			entryList.add(candidateEntry);
			return true;
		}
		
		int insertIdx = -1;
		for (HighScoreEntry entry : entryList)
		{
			if (score >= entry.getScore())
			{
				insertIdx = entryList.indexOf(entry);
				break;
			}
		}
		
		if (insertIdx != -1) // slot found
		{
			entryList.add(insertIdx, candidateEntry);	
			while (entryList.size() > SLOTS) // trim entries
			{
				entryList.remove(entryList.size() - 1);
			}
		}
		else if (entryList.size() < SLOTS) // append to end of list if slots not full
		{
			entryList.add(candidateEntry);			
		}
		
		return false;
	}
	
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		
		for (HighScoreEntry entry : entryList)
		{
			builder.append(entry.toString());
			
		}
		
		return builder.toString();
	}
	
	public class HighScoreEntry implements Serializable
	{
		private static final long serialVersionUID = 1L;
		
		private final String name;
		private final int score;
		private final int level;
		private final Date date;
		
		public HighScoreEntry(String name, int score, int level, Date date)
		{
			this.name = name;
			
			this.score = score;
			this.level = level;
			this.date = date;
		}
		
		public String toString()
		{
			StringBuilder builder = new StringBuilder();
			
			builder.append(name).append(" ");
			builder.append(score).append(" ");
			builder.append(date);
			
			return builder.toString();
		}

		public String getName() 
		{
			return name;
		}

		public int getScore() 
		{
			return score;
		}

		public int getLevel() 
		{
			return level;
		}

		public Date getDate() 
		{
			return date;
		}
	}

}
