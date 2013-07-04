package pacmon;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;

import pacmon.control.RootManager;
import pacmon.control.action.EndGameAction;
import pacmon.control.action.ExitAction;
import pacmon.control.action.ResetGameAction;
import pacmon.control.action.ShowGameScreenAction;
import pacmon.model.maze.Maze;
import pacmon.sound.SoundLoader;
import pacmon.view.component.Component;
import pacmon.view.screen.GameMenuScreen;
import pacmon.view.screen.GameOverScreen;
import pacmon.view.screen.GameScreen;
import pacmon.view.screen.HighScoresScreen;
import pacmon.view.screen.MenuScreen;
import pacmon.view.screen.Screen;
import pacmon.view.screen.effect.MainMenuScreenEffect;

public class Main implements Runnable
{

	public static Main getInstance()
	{
		if (instance == null)
			instance = new Main();
		return instance;
	}
	
	public static void initResources()
	{
		FontManager.loadFonts();
		try 
		{
			GraphicsManager.loadTilesets();
		} 
		catch (IOException e) 
		{			
			System.out.println("Unable to load graphics");
			e.printStackTrace();
			System.exit(-1);
		}
		
		try 
		{
			ImageManager.loadAll();
		} 
		catch (IOException e) 
		{			
			System.out.println("Unable to load images");
			e.printStackTrace();
			System.exit(-1);
		}
		
		try 
		{
			SpriteManager.loadPacmonSprites();
			SpriteManager.loadBlinkySprites();
			SpriteManager.loadPinkySprites();
			SpriteManager.loadInkySprites();
			SpriteManager.loadClydeSprites();
			SpriteManager.loadBonusSprites();
			SpriteManager.loadConsumablesSprites();
		} 
		catch (IOException e) 
		{			
			System.out.println("Unable to load sprites");
			e.printStackTrace();
			System.exit(-1);
		}
		
		try 
		{
			SoundLoader.getInstance().loadAll();
		} 
		catch (IOException e) 
		{
			System.out.println("Unable to load sounds");
			e.printStackTrace();
			System.exit(-1);
		} 
		catch (UnsupportedAudioFileException e) 
		{
			System.out.println("Unable to load sounds");
			e.printStackTrace();
			System.exit(-1);
		}		
	}
	
	public static void createMainMenuScreen(RootManager rootManager)
	{
		MenuScreen mainMenuScreen = new MenuScreen(SCREEN_MAIN_MENU, rootManager);	
		
		mainMenuScreen.getImage().getGraphics().drawImage(ImageManager.getScreenMainImage(), 0, 0, null);
		
		mainMenuScreen.addAction("New Game", new ShowGameScreenAction(SCREEN_GAME));
		mainMenuScreen.addAction("New Game", new ResetGameAction());
		mainMenuScreen.addAction("High Scores", new ShowGameScreenAction(SCREEN_HIGH_SCORE));
		mainMenuScreen.addAction("Exit", new ExitAction());
		
		BufferedImage buttonNewGameImage = ImageManager.getButtonNewGameImage();
		Component newGameButton = new Component(buttonNewGameImage.getWidth(), buttonNewGameImage.getHeight());
		newGameButton.setX(WIDTH / 2 - (newGameButton.getWidth() / 2)); 
		newGameButton.setY(150);
		Graphics2D g1 = newGameButton.getImage().createGraphics();
		GraphicsManager.initializeGraphicsObject(g1);		
		g1.drawImage(buttonNewGameImage, 0, 0, null);
		g1.dispose();
		mainMenuScreen.addComponent(newGameButton);
		mainMenuScreen.setComponentEventActionGroup(newGameButton, Component.EVENT_TRIGGER, "New Game");
		
		BufferedImage buttonHighScoresImage = ImageManager.getButtonHighScoresImage();
		Component highScoresButton = new Component(buttonNewGameImage.getWidth(), buttonNewGameImage.getHeight());
		highScoresButton.setX(WIDTH / 2 - (newGameButton.getWidth() / 2)); 
		highScoresButton.setY(205);
		Graphics2D g2 = highScoresButton.getImage().createGraphics();
		GraphicsManager.initializeGraphicsObject(g2);		
		g2.drawImage(buttonHighScoresImage, 0, 0, null);
		g2.dispose();
		mainMenuScreen.addComponent(highScoresButton);
		mainMenuScreen.setComponentEventActionGroup(highScoresButton, Component.EVENT_TRIGGER, "High Scores");
		
		BufferedImage buttonExitImage = ImageManager.getButtonExitImage();
		Component exitButton = new Component(buttonExitImage.getWidth(), buttonExitImage.getHeight());
		exitButton.setX(WIDTH / 2 - (exitButton.getWidth() / 2));
		exitButton.setY(260);
		Graphics2D g3 = exitButton.getImage().createGraphics();
		GraphicsManager.initializeGraphicsObject(g3);
		g3.drawImage(buttonExitImage, 0, 0, null);		
		g3.dispose();		
		mainMenuScreen.addComponent(exitButton);
		mainMenuScreen.setComponentEventActionGroup(exitButton, Component.EVENT_TRIGGER, "Exit");
		
		mainMenuScreen.addEffect(new MainMenuScreenEffect());
	}
	
	public static void createGameOptionsScreen(RootManager rootManager)
	{
		MenuScreen gameMenuScreen = new GameMenuScreen(SCREEN_GAME_MENU, rootManager);	
		
		gameMenuScreen.getImage().getGraphics().drawImage(ImageManager.getScreenGameOptionsImage(), 0, 0, null);
		
		gameMenuScreen.addAction("Continue", new ShowGameScreenAction(SCREEN_GAME));
		gameMenuScreen.addAction("Quit", new ShowGameScreenAction(SCREEN_MAIN_MENU));
		gameMenuScreen.addAction("Quit", new EndGameAction());
		
		BufferedImage buttonContinueImage = ImageManager.getButtonContinueImage();
		Component continueGameButton = new Component(buttonContinueImage.getWidth(), buttonContinueImage.getHeight());
		continueGameButton.setX(WIDTH / 2 - (continueGameButton.getWidth() / 2));
		continueGameButton.setY(150);
		Graphics2D g3 = continueGameButton.getImage().createGraphics();
		GraphicsManager.initializeGraphicsObject(g3);
		g3.drawImage(buttonContinueImage, 0, 0, null);		
		g3.dispose();
		gameMenuScreen.addComponent(continueGameButton);
		gameMenuScreen.setComponentEventActionGroup(continueGameButton, Component.EVENT_TRIGGER, "Continue");
		
		BufferedImage buttonQuitImage = ImageManager.getButtonQuitImage();
		Component quitGameButton = new Component(buttonQuitImage.getWidth(), buttonQuitImage.getHeight());
		quitGameButton.setX(WIDTH / 2 - (quitGameButton.getWidth() / 2));
		quitGameButton.setY(205);
		Graphics2D g4 = quitGameButton.getImage().createGraphics();
		GraphicsManager.initializeGraphicsObject(g4);
		g4.drawImage(buttonQuitImage, 0, 0, null);	
		g4.dispose();		
		gameMenuScreen.addComponent(quitGameButton);
		gameMenuScreen.setComponentEventActionGroup(quitGameButton, Component.EVENT_TRIGGER, "Quit");
	}
	
	public static void createGameScreen(RootManager rootManager)
	{
		new GameScreen(SCREEN_GAME, rootManager, SCREEN_GAME_MENU, SCREEN_GAME_OVER);
	}
	
	public static void createGameOverScreen(RootManager rootManager)
	{
		Screen screen = new GameOverScreen(SCREEN_GAME_OVER, rootManager, SCREEN_MAIN_MENU);
		
		screen.addAction("Quit", new EndGameAction());
		screen.addAction("Quit", new ShowGameScreenAction(SCREEN_MAIN_MENU));
		
		screen.getImage().getGraphics().drawImage(ImageManager.getScreenGameOverImage(), 0, 0, null);
		
		Font mainFont = FontManager.getFont(FontManager.DEFAULT).deriveFont((float)Maze.TILE_SIZE);	
		Font subFont = FontManager.getFont(FontManager.DEFAULT).deriveFont((float)Maze.TILE_SIZE - 3);
		
		BufferedImage image = screen.getImage(); 
		Graphics2D g = image.createGraphics();
		g.setColor(Color.WHITE);
		
		g.setFont(mainFont);
		Rectangle2D mainBounds = g.getFontMetrics().getStringBounds(TEXT_GAME_OVER, g);
		g.drawString(TEXT_GAME_OVER, 
				(int)(image.getWidth() / 2) - (int)(mainBounds.getWidth() / 2), 
				(int)(image.getHeight() / 2) - (int)(mainBounds.getHeight() / 2));
		
		g.setFont(subFont);
		Rectangle2D subBounds = g.getFontMetrics().getStringBounds(TEXT_GAME_OVER_SUB, g);
		g.drawString(TEXT_GAME_OVER_SUB, 
				(int)(image.getWidth() / 2) - (int)(subBounds.getWidth() / 2), 
				(int)(image.getHeight() / 2) - (int)(subBounds.getHeight() / 2) + (int)(mainBounds.getHeight()) + 20);
		
		g.dispose();
	}
	
	public static void createHighScoresScreen(RootManager rootManager)
	{
		Screen screen = new HighScoresScreen(SCREEN_HIGH_SCORE, rootManager, SCREEN_MAIN_MENU);
		
		//screen.getImage().getGraphics().drawImage(ImageManager.getScreenGameOverImage(), 0, 0, null);
		
		Font mainFont = FontManager.getFont(FontManager.DEFAULT).deriveFont((float)Maze.TILE_SIZE);	
		Font subFont = FontManager.getFont(FontManager.DEFAULT).deriveFont((float)Maze.TILE_SIZE - 3);
		
		BufferedImage image = screen.getImage();
		Graphics2D g = image.createGraphics();
		g.setColor(Color.WHITE);
		
		/*g.setFont(mainFont);
		Rectangle2D mainBounds = g.getFontMetrics().getStringBounds(TEXT_GAME_OVER, g);
		g.drawString(TEXT_GAME_OVER, 
				(int)(image.getWidth() / 2) - (int)(mainBounds.getWidth() / 2), 
				(int)(image.getHeight() / 2) - (int)(mainBounds.getHeight() / 2));
		
		g.setFont(subFont);
		Rectangle2D subBounds = g.getFontMetrics().getStringBounds(TEXT_GAME_OVER_SUB, g);
		g.drawString(TEXT_GAME_OVER_SUB, 
				(int)(image.getWidth() / 2) - (int)(subBounds.getWidth() / 2), 
				(int)(image.getHeight() / 2) - (int)(subBounds.getHeight() / 2) + (int)(mainBounds.getHeight()) + 20);*/
		
		//g.dispose();
	}

	public static void main(String[] args)
	{
		System.out.println("--- Pac Mon! ---");
		
		initResources();

		Main main = Main.getInstance();		
		
		main.setDesiredFps(FPS);
		main.setRunning(true);	
		
		RootManager rootManager = new RootManager(WIDTH, HEIGHT);
		
		createMainMenuScreen(rootManager);
		createGameOptionsScreen(rootManager);
		createGameScreen(rootManager);
		createGameOverScreen(rootManager);
		createHighScoresScreen(rootManager);
		
		rootManager.showScreen(SCREEN_MAIN_MENU);
		main.setRootManager(rootManager);		
		
		ImageManager.unloadAll();
		
		new Thread(main).start();
	}
	
	private static Main instance;
	
	private static final int WIDTH = 448;
	private static final int HEIGHT = 576;
	private static final String FRAME_TITLE = "Pac Mon";
	private static final int FPS = 60;
	private static final String SCREEN_MAIN_MENU = "MAIN MENU";
	private static final String SCREEN_GAME_MENU = "GAME MENU";
	private static final String SCREEN_GAME = "GAME";
	private static final String SCREEN_GAME_OVER = "GAME OVER";
	private static final String SCREEN_HIGH_SCORE = "HIGH_SCORE";
	
	private static final String TEXT_GAME_OVER = "Game Over!";
	private static final String TEXT_GAME_OVER_SUB = "Press <Enter> to continue";
	
	public void setRunning(boolean b)
	{
		running = b;
	}
	
	public void setDesiredFps(int fps)
	{
		if (fps <= 0)
			throw new IllegalArgumentException(fps+" is not a valid FPS setting");
		desiredFPS = fps;
	    desiredDeltaLoop = (1000*1000*1000)/desiredFPS;
	}
	
	@Override
	public void run() 
	{
		long beginLoopTime;
		long endLoopTime;
		long deltaLoop;
		
		while (running)
		{
			beginLoopTime = System.nanoTime();
			
			render();
			update();
			
			endLoopTime = System.nanoTime();
			
			deltaLoop = endLoopTime - beginLoopTime;
			
			if (deltaLoop > desiredDeltaLoop)
			{
				//System.out.println("Loop running late");
			}
			else
			{
				//System.out.println("Sleep for "+((desiredDeltaLoop - deltaLoop)/(1000000))+" milliseconds");
				try
				{
		            Thread.sleep(((desiredDeltaLoop - deltaLoop)/(1000000)));
		        }
				catch(InterruptedException e) 
				{
					running = false;
				}
			}
		}		
	}

	private Main()
	{       
		frame = new JFrame(FRAME_TITLE);
		
		JPanel panel = (JPanel) frame.getContentPane();
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		panel.setLayout(null);
		
		canvas = new Canvas();
		canvas.setBounds(0, 0, WIDTH, HEIGHT);
		canvas.setIgnoreRepaint(true);
		
		panel.add(canvas);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(false);
			
		frame.setVisible(true);
		
		canvas.createBufferStrategy(2);
		bufferStrategy = canvas.getBufferStrategy();
		
		canvas.requestFocus();
	}
	
	private void setRootManager(RootManager rootManager)
	{
		if (this.rootManager != null)
		{
			this.canvas.removeKeyListener(rootManager);
			this.canvas.removeMouseListener(rootManager);
			this.canvas.removeMouseMotionListener(rootManager);
		}		
		this.rootManager = rootManager;
		if (rootManager != null)
		{
			this.canvas.addKeyListener(rootManager);
			this.canvas.addMouseListener(rootManager);
			this.canvas.addMouseMotionListener(rootManager);
		}
	}
	
	private void render()
	{		
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();

		GraphicsManager.initializeGraphicsObject(g);
		
		if (rootManager != null)
		{	
			rootManager.render(g);
		}
		
		g.dispose();
		bufferStrategy.show();
	}
	
	private void update()
	{
		if (rootManager != null)
		{
			rootManager.update();
		}
	}
	
	private JFrame frame;
	private Canvas canvas;
	private BufferStrategy bufferStrategy;
	
	private boolean running;
	
	private int desiredFPS;
	private long desiredDeltaLoop;
	
	private RootManager rootManager;
}
