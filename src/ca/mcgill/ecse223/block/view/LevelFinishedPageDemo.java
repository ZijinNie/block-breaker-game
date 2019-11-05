package ca.mcgill.ecse223.block.view;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LevelFinishedPageDemo {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LevelFinishedPageDemo window = new LevelFinishedPageDemo();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LevelFinishedPageDemo() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel titleLabel = new JLabel("Pass Level!!!");
		titleLabel.setBounds(156, 32, 106, 43);
		frame.getContentPane().add(titleLabel);
		
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
		
		JButton continueButton = new JButton("Continue Next Level");
		continueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		continueButton.setBounds(41, 160, 167, 23);
		frame.getContentPane().add(continueButton);
		
		JButton exitButton = new JButton("Save & Exit");
		exitButton.setBounds(234, 160, 134, 23);
		frame.getContentPane().add(exitButton);
	}

}
