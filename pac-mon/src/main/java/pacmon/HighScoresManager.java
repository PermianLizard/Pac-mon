package pacmon;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;

import pacmon.highscore.HighScoreTable;

public class HighScoresManager {

	public static final String HIGHSCORES_FILENAME = "scores.dat";
	
	public static void registerScore(String name, int score, int level) throws IOException, ClassNotFoundException
	{
		HighScoreTable highScoreTable = loadHighScores();
		highScoreTable.processScore(name, score, level, Calendar.getInstance().getTime());
		HighScoresManager.saveHighScores(highScoreTable);
	}
	
	public static HighScoreTable loadHighScores() throws IOException, ClassNotFoundException
	{
		try
		{
			FileInputStream fileIn = new FileInputStream(HIGHSCORES_FILENAME);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			HighScoreTable highScoreTable = (HighScoreTable)in.readObject();
			in.close();
			fileIn.close();
			
			return highScoreTable;
		}
		catch (FileNotFoundException e) 
		{
			System.out.println("high scores not found: creating new");
			
			return new HighScoreTable();
		}
	}
	
	public static void saveHighScores(HighScoreTable highScoreTable) throws IOException
	{
		try {
			FileOutputStream fileOut = new FileOutputStream(HIGHSCORES_FILENAME);
			
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			
			out.writeObject(highScoreTable);
			out.close();
			fileOut.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

}
