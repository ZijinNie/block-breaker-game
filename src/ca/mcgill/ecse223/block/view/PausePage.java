package ca.mcgill.ecse223.block.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;

import java.awt.Font;
import javax.swing.JButton;

public class PausePage {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					PausePage window = new PausePage();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public PausePage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblPaused = new JLabel("Paused");
		lblPaused.setFont(new Font("����", Font.PLAIN, 40));
		lblPaused.setBounds(126, 32, 145, 32);
		frame.getContentPane().add(lblPaused);
		
		JLabel lifeLabel = new JLabel("Life: ");
		lifeLabel.setBounds(41, 93, 58, 15);
		frame.getContentPane().add(lifeLabel);
		
		JLabel lifeNumberLabel = new JLabel("ex.2");
		lifeNumberLabel.setBounds(89, 93, 58, 15);
		frame.getContentPane().add(lifeNumberLabel);
		
		JLabel levelLabel = new JLabel("Current Level: ");
		levelLabel.setBounds(205, 93, 103, 15);
		frame.getContentPane().add(levelLabel);
		
		JLabel levelNumberLabel = new JLabel("ex. 32");
		levelNumberLabel.setBounds(298, 93, 58, 15);
		frame.getContentPane().add(levelNumberLabel);
		
		JButton resumeButton = new JButton("Resume");
		resumeButton.setBounds(41, 160, 97, 23);
		frame.getContentPane().add(resumeButton);
		
		JButton exitButton = new JButton("Save & Exit");
		exitButton.setBounds(234, 160, 110, 23);
		frame.getContentPane().add(exitButton);
	
	
	
		resumeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
	
				try {
					resumeButtonActionPerformed(evt);
				} catch (InvalidInputException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
			}
		}
		);
		
		exitButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
	
				try {
					exitButtonActionPerformed(evt);
				} catch (InvalidInputException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
			}
		}
		);
	}
	
	//TODO
	public void resumeButtonActionPerformed(java.awt.event.ActionEvent evt) throws InvalidInputException {
		Block223Application.openGamePlayPage();
		Block223PlayModeInterface currentGamePage =Block223Application.getCurrentGamePlayPage();
		try {
			Block223Controller.startGame(currentGamePage);

		} catch (Exception e) {
			e.printStackTrace();
		}
		frame.dispose();
		
	}
	
	public void exitButtonActionPerformed(java.awt.event.ActionEvent evt) throws InvalidInputException {
		try {
			Block223Persistence.save(Block223Application.getBlock223());
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		frame.dispose();
		
	}
}
