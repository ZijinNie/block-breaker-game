package ca.mcgill.ecse223.block.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOHallOfFameEntry;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;


public class GamePlayPage extends JFrame implements Block223PlayModeInterface{
	private static final long serialVersionUID = -6915461336943118607L;
	private GamePanel gamePanel;
	private HallOfFamePanel HOFPanel;
	private final Rectangle gamePanelsize = new Rectangle(40,150,400,400);
	private final Rectangle gamePagesize = new Rectangle(0, 0, 700,650);
	private final Rectangle HOFsize = new Rectangle(470,100,200,400);
	
	//structural
	private JLabel block223Label;
	private JLabel levelLabel;
	private JLabel levelNumberLabel;
	private JLabel liveLabel;
	private JLabel lifeNumberLabel;
	private JLabel scoreLabel;
	private JLabel scoreNumberLabel;
	private JButton startButton;
	private JButton exitButton;
	private JLabel dummylabel;
	private JTextArea gameArea;
	private GamePlayListener bp;
	private JLabel hofLabel;
	
	
	public GamePlayPage() {
		initialize();
	}
	
	
	private void initialize() {
		this.setTitle("Block223 PlayMode Example");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(gamePagesize);

		// Add components to window pane
		this.addComponentsToPane();

		// Display the window.
		this.setVisible(true);
		
	}


	private void addComponentsToPane() {
		startButton = new JButton("Start Game");
		exitButton = new JButton("exit");
		exitButton.setVisible(false);
		HOFPanel = new HallOfFamePanel();
		getContentPane().add(HOFPanel);
		HOFPanel.setBounds(HOFsize);
		HOFPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		HOFPanel.setVisible(true);

		gamePanel = new GamePanel(gamePanelsize);
		getContentPane().add(gamePanel);
		
		gameArea = new JTextArea();
		gameArea.setEditable(false);
		//gameArea.setVisible(false);
		JScrollPane scrollPane = new JScrollPane(gameArea);
		scrollPane.setPreferredSize(new Dimension(375, 125));

		getContentPane().add(scrollPane, BorderLayout.CENTER);
		getContentPane().add(startButton, BorderLayout.PAGE_END);
		
		
		hofLabel = new JLabel("Hall of Fame");
		hofLabel.setFont(new Font("Orator Std", Font.PLAIN, 10));
		hofLabel.setBounds(470, 50, 200, 50);
		add(hofLabel);
		
		block223Label = new JLabel("BLOCK223");
		block223Label.setFont(new Font("Orator Std", Font.PLAIN, 20));
		block223Label.setBounds(10, 10, 120, 50);
		block223Label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		add(block223Label);
		
		levelLabel = new JLabel("Level: ");	
		levelLabel.setBounds(10,50, 50, 50);
		levelNumberLabel = new JLabel("0");		
		levelNumberLabel.setBounds(60,50, 50, 50);
		liveLabel = new JLabel("Live: ");
		liveLabel.setBounds(110, 50, 50, 50);
		lifeNumberLabel = new JLabel("0");
		lifeNumberLabel.setBounds(160, 50, 50, 50);
		scoreLabel = new JLabel("Score:");
		scoreLabel.setBounds(210, 50, 50, 50);
		scoreNumberLabel = new JLabel("0");
		scoreNumberLabel.setBounds(260, 50, 50, 50);
		exitButton.setBounds(470, 500, 200, 50);
		
		getContentPane().add(gameArea);
		getContentPane().add(gamePanel);
		getContentPane().add(levelLabel);
		getContentPane().add(levelNumberLabel);
		getContentPane().add(liveLabel);
		getContentPane().add(lifeNumberLabel);
		getContentPane().add(scoreLabel);
		getContentPane().add(scoreNumberLabel);
		getContentPane().add(exitButton);
		
		dummylabel = new JLabel();
		getContentPane().add(dummylabel);
		
		exitButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
			Block223Persistence.save(Block223Application.getBlock223());
			GamePlayPage.this.dispose();
			Block223Application.setCurrentGamePlayPage(null);
			Block223Application.setCurrentPlayableGame(null);
			Block223Application.openPlayerMenuPage();
			}
			
		});
		
		
		startButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				startButton.setVisible(false);
				// initiating a thread to start listening to keyboard inputs
				bp = new GamePlayListener();
				Runnable r1 = new Runnable() {
					@Override
					public void run() {
						// in the actual game, add keyListener to the game window
						gameArea.addKeyListener(bp);
					}
				};
				Thread t1 = new Thread(r1);
				t1.start();
				// to be on the safe side use join to start executing thread t1 before executing
				// the next thread
				try {
					t1.join();
				} catch (InterruptedException e1) {
				}

				// initiating a thread to start the game loop
				Runnable r2 = new Runnable() {
					@Override
					public void run() {
						try {
							Block223Controller.startGame(GamePlayPage.this);
							startButton.setVisible(true);
							exitButton.setVisible(true);
						} catch (InvalidInputException e) {
						}
					}
				};
				Thread t2 = new Thread(r2);
				t2.start();
			}
		});
	}

	@Override
	public String takeInputs() {
		if (bp == null) {
			return "";
		}
		return bp.takeInputs();
	
	}


	@Override
	public void refresh() {
		//System.out.println("UI is refreshing now...");
		updateheader();
		gamePanel.refresh();
		try {
			HOFPanel.refreshPage(0);
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.repaint();
		
		
	}
	public void updateheader() {
		levelNumberLabel.setText(""+Block223Application.getCurrentPlayableGame().getCurrentLevel());
		lifeNumberLabel.setText(""+Block223Application.getCurrentPlayableGame().getLives());
		scoreNumberLabel.setText(""+Block223Application.getCurrentPlayableGame().getScore());
	}
	
	@Override
	public void endGame(int nrOfLives, TOHallOfFameEntry hof) {
			Block223Application.openFinishGamePage();
	}

}
