package ca.mcgill.ecse223.block.view;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOHallOfFameEntry;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

public class GamePlayPageDemo extends JFrame implements Block223PlayModeInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6915461336943118607L;

	private GamePanel gamePanel;
	private HallOfFamePanel HOFPanel;
	private final Rectangle gamePanelsize = new Rectangle(40,150,400,400);
	private final Rectangle gamePagesize = new Rectangle(0, 0, 700,650);
	private final Rectangle HOFsize = new Rectangle(470,50,200,550);
	
	private String inputs;
	
	//structural
	private JLabel block223Label;
	private JLabel levelLabel;
	private JLabel levelNumberLabel;
	private JLabel liveLabel;
	private JLabel lifeNumberLabel;
	private JLabel scoreLabel;
	private JLabel scoreNumberLabel;
	private JButton pauseButton;
	private JTextArea gameArea;
	private GamePlayListener bp;
	
	public GamePlayPageDemo() {
		initialize();
		centreWindow();
		refresh();
	}
	private void initialize() {
		

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setTitle("GamePage");
		setVisible(true);
		setBounds(gamePagesize);
		inputs = "";

		gameArea = new JTextArea();
		gameArea.setEditable(false);
		
		//pause button initialize
		pauseButton = new JButton("pause");
		pauseButton.setBounds(200, 48, 146, 40);
		add(pauseButton);
		
		block223Label = new JLabel("BLOCK223");
		block223Label.setFont(new Font("Orator Std", Font.PLAIN, 20));
		block223Label.setBounds(10, 10, 120, 50);
		block223Label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		add(block223Label);
		
		levelLabel = new JLabel("Level: ");	
		levelLabel.setBounds(10,50, 50, 50);
		levelNumberLabel = new JLabel("ex. 99");		
		levelNumberLabel.setBounds(60,50, 50, 50);
		liveLabel = new JLabel("Live: ");
		liveLabel.setBounds(110, 50, 50, 50);
		lifeNumberLabel = new JLabel("ex.3");
		lifeNumberLabel.setBounds(160, 50, 50, 50);
		scoreLabel = new JLabel("Score:");
		scoreLabel.setBounds(210, 50, 50, 50);
		scoreNumberLabel = new JLabel("ex. 1000");
		scoreNumberLabel.setBounds(260, 50, 50, 50);

		gamePanel = new GamePanel(gamePanelsize);
//		HOFPanel = new HallOfFamePanel(HOFsize);
		getContentPane().add(gameArea);
		getContentPane().add(gamePanel);
		getContentPane().add(HOFPanel);
		getContentPane().add(levelLabel);
		getContentPane().add(levelNumberLabel);
		getContentPane().add(liveLabel);
		getContentPane().add(lifeNumberLabel);
		getContentPane().add(scoreLabel);
		getContentPane().add(scoreNumberLabel);
		
		
		bp = new GamePlayListener();
		Runnable r1 = new Runnable() {
			@Override
			public void run() {
				// in the actual game, add keyListener to the game window
				gameArea.addKeyListener(bp);
				//pauseButton.setFocusable(false);
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
					Block223Controller.startGame(GamePlayPageDemo.this);
				} catch (InvalidInputException e) {
				}
			}
		};
		Thread t2 = new Thread(r2);
		t2.start();
				
//		addKeyListener(new KeyListener() {
//			@Override
//			public void keyTyped(KeyEvent e) {
//				
//			}
//
//
//			@Override
//			public void keyPressed(KeyEvent e) {
//				//System.out.println(e.getKeyCode());
//				if (e.getKeyCode()==KeyEvent.VK_LEFT) {
//					appendinput('l');
//				}else if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
//					appendinput('r');
//				}else if (e.getKeyCode()==KeyEvent.VK_SPACE) {
//					appendinput(' ');
//				}else {
//					//appendinput(e.getKeyChar());
//				}
//				
//				refresh();
//			}
//
//			@Override
//			public void keyReleased(KeyEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		
		
		
		//listeners for pauseButton
			pauseButton.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						try {
							pauseActionPerformed(evt);
						} catch (InvalidInputException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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
		gamePanel.refresh();
		//HOFPanel.update();
		block223Label.setText(inputs);

		this.repaint();
	}
	@Override
	public void endGame(int nrOfLives, TOHallOfFameEntry hof) {
		// TODO Auto-generated method stub
		
	}
	public void centreWindow() {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
	    this.setLocation(x, y);
	}
//	public void appendinput(char a) {
//		inputs+=a;
//	}
	
	public void pauseActionPerformed(java.awt.event.ActionEvent evt) throws InvalidInputException {		
		try {
			Block223Persistence.save(Block223Application.getBlock223());
			Block223Controller.pause();
		} catch (RuntimeException e) {
			
		}
		Block223Application.openPausePage();
	}

}
