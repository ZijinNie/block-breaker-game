package ca.mcgill.ecse223.block.view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOHallOfFame;
import ca.mcgill.ecse223.block.controller.TOHallOfFameEntry;

public class DeadPageDemo extends JFrame {

	private JFrame frame;
	
	
	// UI element
	private JLabel errorMessage;
	private JLabel successMessage;
	
	//UI Label
	private JLabel titleLabel;
	private JLabel finalScoreLabel;
	private JLabel scoreNumberLabel;
	private JButton cancelButton;
	
	// data elements
	private String error = null;
	private String success = null;
	
	
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					DeadPageDemo window = new DeadPageDemo();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 * @throws InvalidInputException 
	 */
	public DeadPageDemo() throws InvalidInputException {
		initialize();
		refreshData();
		this.centreWindow();
	}

	
	/**
	 * Initialize the contents of the frame.
	 * @throws InvalidInputException 
	 */
	private void initialize() throws InvalidInputException {
		
		//global 
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		//initial bust label
		titleLabel = new JLabel("Busted");
		titleLabel.setBounds(179, 52, 58, 15);
		frame.getContentPane().add(titleLabel);
		
		//initial final score label
		finalScoreLabel = new JLabel("Final Score: ");
		finalScoreLabel.setBounds(105, 114, 83, 26);
		frame.getContentPane().add(finalScoreLabel);
		
		//initial score number label
		TOHallOfFame entries =  Block223Controller.getHallOfFameWithMostRecentEntry(1);
		TOHallOfFameEntry entry = entries.getEntry(0);	
		scoreNumberLabel = new JLabel(""+entry.getScore());
		scoreNumberLabel.setBounds(243, 120, 58, 15);
		frame.getContentPane().add(scoreNumberLabel);
		
		//initial cancel button
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(137, 174, 142, 50);
		frame.getContentPane().add(cancelButton);
		
		
		//listener
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelButtonActionPerformed(evt);
			}
		});
	}
	
	/**
	 * Refresh the contents of the frame.
	 */
	private void refreshData() {
		errorMessage.setText(error);
		successMessage.setText(success);
	}
	
	
	
	/**
	 * CentreWindow of the frame.
	 */
	public void centreWindow(){
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
	    this.setLocation(x, y);
	}
	
	
	/**
	 * Button Action of the frame.
	 */
	private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error = null;
		Block223Application.openSignUpPage();
		dispose();
	}
}
