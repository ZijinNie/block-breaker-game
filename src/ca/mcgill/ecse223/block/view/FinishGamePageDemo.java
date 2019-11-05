package ca.mcgill.ecse223.block.view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOHallOfFame;
import ca.mcgill.ecse223.block.controller.TOHallOfFameEntry;
import ca.mcgill.ecse223.block.controller.TOUserMode.Mode;

import javax.swing.JButton;

public class FinishGamePageDemo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JFrame frame;
	
	private JLabel titleLabel; 
	private JLabel finalScoreLabel;
	private JLabel scoreNumberLabel;
	private JButton cancelButton;
	private JPanel halloffamePanel;


	/**
	 * Create the application.
	 * @throws InvalidInputException 
	 */
	public FinishGamePageDemo() throws InvalidInputException {
		initialize();
		((HallOfFamePanel) halloffamePanel).refreshPage(0);
		this.centreWindow();
		Block223Controller.deletePlayedGame();
		Block223Application.setCurrentPlayableGame(null);	
		
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(200, 800, 97, 23);
		frame.getContentPane().add(cancelButton);
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws InvalidInputException 
	 */
	private void initialize() throws InvalidInputException {
		frame = new JFrame();
		frame.setVisible(true);
		
		//global 
		frame.setBounds(100, 100, 500, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		halloffamePanel =new HallOfFamePanel();
		halloffamePanel.setBounds(120,140,200,550);
		frame.getContentPane().add(halloffamePanel);
		
		
		//initialzize
		titleLabel = new JLabel("Congrats You Lucky Bustard !!!");
		titleLabel.setBounds(120, 65, 214, 15);
		frame.getContentPane().add(titleLabel);
		
		//initialize
		finalScoreLabel = new JLabel("Final Score: ");
		finalScoreLabel.setBounds(141, 114, 83, 26);
		frame.getContentPane().add(finalScoreLabel);
		
		TOHallOfFame entries =  Block223Controller.getHallOfFameWithMostRecentEntry(1);
		
		TOHallOfFameEntry entry = entries.getEntry(0);
		
		scoreNumberLabel = new JLabel(""+entry.getScore());
		scoreNumberLabel.setBounds(234, 120, 58, 15);
		frame.getContentPane().add(scoreNumberLabel);
		
	    


		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
	
				cancelButtonActionPerformed(evt);	
			}		
		}
		);	
	}	
	public void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
		Block223Application.openPlayerMenuPage();
		this.dispose();
	}

	
	
	/**
	 * Refresh the contents of the frame.
	 */
	@SuppressWarnings("unused")
	private void refreshData() {
	
		
	}
	
	/**
	 * centreWindow the contents of the frame.
	 */
	public void centreWindow(){
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
	    this.setLocation(x, y);
	}
}