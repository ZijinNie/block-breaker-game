package ca.mcgill.ecse223.block.view;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOHallOfFame;
import ca.mcgill.ecse223.block.controller.TOHallOfFameEntry;

public class HallOfFamePageDemo {

	private JFrame frame;
	
	int start =0;
	
	JLabel titleLabel;
	JLabel groupNameLabel;
	JLabel nameLabel1;
	JLabel nameLabel2;
	JLabel nameLabel3;
	JLabel nameLabel4;
	JLabel nameLabel5;
	JLabel nameLabel6;
	JLabel nameLabel7;
	JLabel nameLabel8;
	JLabel nameLabel9;
	JLabel nameLabel10;
	JButton lButton;
	JButton rButton;
	JButton cancelButton;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HallOfFamePageDemo window = new HallOfFamePageDemo();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws InvalidInputException 
	 */
	public HallOfFamePageDemo() throws InvalidInputException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws InvalidInputException 
	 */
	private void initialize() throws InvalidInputException {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		titleLabel = new JLabel("Hall Of Fame");
		titleLabel.setBounds(162, 0, 83, 40);
		frame.getContentPane().add(titleLabel);
		
//		groupNameLabel = new JLabel("ex. McGill ");
//		groupNameLabel.setBounds(268, 24, 83, 34);
//		frame.getContentPane().add(groupNameLabel);
//		
		nameLabel1 = new JLabel();
		nameLabel1.setBounds(57, 49, 83, 34);
		frame.getContentPane().add(nameLabel1);
		
		nameLabel2 = new JLabel();
		nameLabel2.setBounds(57, 80, 58, 34);
		frame.getContentPane().add(nameLabel2);
		
		nameLabel3 = new JLabel();
		nameLabel3.setBounds(57, 107, 58, 34);
		frame.getContentPane().add(nameLabel3);
		
		nameLabel4 = new JLabel();
		nameLabel4.setBounds(57, 144, 58, 34);
		frame.getContentPane().add(nameLabel4);
		
		nameLabel5 = new JLabel();
		nameLabel5.setBounds(57, 175, 58, 40);
		frame.getContentPane().add(nameLabel5);
		
		nameLabel6 = new JLabel();
		nameLabel6.setBounds(217, 50, 58, 28);
		frame.getContentPane().add(nameLabel6);
		
		nameLabel7 = new JLabel();
		nameLabel7.setBounds(217, 83, 58, 28);
		frame.getContentPane().add(nameLabel7);
		
		nameLabel8 = new JLabel();
		nameLabel8.setBounds(217, 113, 58, 23);
		frame.getContentPane().add(nameLabel8);
		
		nameLabel9 = new JLabel();
		nameLabel9.setBounds(217, 144, 58, 28);
		frame.getContentPane().add(nameLabel9);
		
		nameLabel10 = new JLabel();
		nameLabel10.setBounds(217, 181, 58, 28);
		frame.getContentPane().add(nameLabel10);
		
		lButton = new JButton("L");
		lButton.setBounds(122, 223, 39, 28);
		frame.getContentPane().add(lButton);
		
		rButton = new JButton("R");
		rButton.setBounds(226, 223, 39, 28);
		frame.getContentPane().add(rButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(311, 226, 97, 23);
		frame.getContentPane().add(cancelButton);
		
		refreshPage(start);
		
		lButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
	
				try {
					lButtonActionPerformed(evt);
				} catch (InvalidInputException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
			}
		}
		);
		
		rButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
	
				try {
					rButtonActionPerformed(evt);
				} catch (InvalidInputException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
			}
		}
		);
		
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
	
				cancelButtonActionPerformed(evt);
	
			}
		}
		);
	}
	
	public void lButtonActionPerformed(java.awt.event.ActionEvent evt) throws InvalidInputException {

		if(start >9) {
			start -=10;
			refreshPage(start);
		}
	}
	
	public void rButtonActionPerformed(java.awt.event.ActionEvent evt) throws InvalidInputException {
		List<TOHallOfFameEntry> entries= Block223Controller.getHallOfFame(start,start+9).getEntries();
		int size =entries.size();
		if(start+10 < size) {
			start +=10;
			refreshPage(start);
		}
	}
	
	public void cancelButtonActionPerformed (java.awt.event.ActionEvent evt) {
		frame.dispose();
	}
	
	public void refreshPage(int start) throws InvalidInputException {
		List<TOHallOfFameEntry> entries;
		try {
			
		 entries= Block223Controller.getHallOfFame(start,start+9).getEntries();
		
		}catch (InvalidInputException e) {
			throw new InvalidInputException(e.getMessage());
		}

		TOHallOfFameEntry entry ;
		if(entries.get(start+0)!= null) {
			entry =entries.get(start+0);
			nameLabel1.setText(entry.getPlayername()+" : "+entry.getScore());
		}	
		if(entries.get(start+1)!= null) {
			entry =entries.get(start+1);
			nameLabel2.setText(entry.getPlayername()+" : "+entry.getScore());
		}
		if(entries.get(start+2)!= null) {
			entry =entries.get(start+2);
			nameLabel3.setText(entry.getPlayername()+" : "+entry.getScore());
		}
		if(entries.get(start+3)!= null) {
			entry =entries.get(start+3);
			nameLabel4.setText(entry.getPlayername()+" : "+entry.getScore());
		}
		if(entries.get(start+4)!= null) {
			entry =entries.get(start+4);
			nameLabel5.setText(entry.getPlayername()+" : "+entry.getScore());
		}
		if(entries.get(start+5)!= null) {
			entry =entries.get(start+5);
			nameLabel6.setText(entry.getPlayername()+" : "+entry.getScore());
		}
		if(entries.get(start+6)!= null) {
			entry =entries.get(start+6);
			nameLabel7.setText(entry.getPlayername()+" : "+entry.getScore());
		}
		if(entries.get(start+7)!= null) {
			entry =entries.get(start+7);
			nameLabel8.setText(entry.getPlayername()+" : "+entry.getScore());
		}
		if(entries.get(start+8)!= null) {
			entry =entries.get(start+8);
			nameLabel9.setText(entry.getPlayername()+" : "+entry.getScore());
		}
		if(entries.get(start+9)!= null) {
			entry =entries.get(start+9);
			nameLabel10.setText(entry.getPlayername()+" : "+entry.getScore());
		}
	}
	
	
	
	
	
		
}
