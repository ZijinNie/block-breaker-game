package ca.mcgill.ecse223.block.view;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOHallOfFameEntry;

public class HallOfFamePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 234523452345L;

	int start =0;
	
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
	/**
	/**
	 * Create the panel.
	 */
	public HallOfFamePanel() {
		initialize();
	
	}
	private void initialize() {
		setLayout(null);
			
//		groupNameLabel = new JLabel("ex. McGill ");
//		groupNameLabel.setBounds(268, 24, 83, 34);
//		this.add(groupNameLabel);
//		
		nameLabel1 = new JLabel();
		nameLabel1.setText("1");
		nameLabel1.setBounds(38, 10, 120, 30);
		this.add(nameLabel1);
		
		nameLabel2 = new JLabel();
		nameLabel2.setText("2");
		nameLabel2.setBounds(38, 40, 120, 30);
		this.add(nameLabel2);
		
		nameLabel3 = new JLabel();
		nameLabel3.setText("3");
		nameLabel3.setBounds(38, 70, 120, 30);
		this.add(nameLabel3);
		
		nameLabel4 = new JLabel();
		nameLabel4.setText("4");
		nameLabel4.setBounds(38, 100, 120, 30);
		this.add(nameLabel4);
		
		nameLabel5 = new JLabel();
		nameLabel5.setText("5");
		nameLabel5.setBounds(38, 130, 120, 30);
		this.add(nameLabel5);
		
		nameLabel6 = new JLabel();
		nameLabel6.setText("6");
		nameLabel6.setBounds(38, 160, 120, 30);
		this.add(nameLabel6);
		
		nameLabel7 = new JLabel();
		nameLabel7.setText("7");
		nameLabel7.setBounds(38, 190, 120, 30);
		this.add(nameLabel7);
		
		nameLabel8 = new JLabel();
		nameLabel8.setText("8");
		nameLabel8.setBounds(38, 220, 120, 30);
		this.add(nameLabel8);
		
		nameLabel9 = new JLabel();
		nameLabel9.setText("9");
		nameLabel9.setBounds(38, 250, 120, 30);
		this.add(nameLabel9);
		
		nameLabel10 = new JLabel();
		nameLabel10.setText("10");
		nameLabel10.setBounds(38, 280, 120, 30);
		this.add(nameLabel10);
		
		lButton = new JButton("L");
		lButton.setBounds(25, 335, 66, 28);
		this.add(lButton);
		
		rButton = new JButton("R");
		rButton.setBounds(114, 335, 61, 28);
		this.add(rButton);
	
		
		
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
	
	public void refreshPage(int start) throws InvalidInputException {
		List<TOHallOfFameEntry> entries;
		try {
			
		 entries= Block223Controller.getHallOfFame(start,start+9).getEntries();
		
		}catch (InvalidInputException e) {
			throw new InvalidInputException(e.getMessage());
		}

		TOHallOfFameEntry entry ;
		
		try {
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
		}catch(IndexOutOfBoundsException e) {
			
		}
	}
}
