package ca.mcgill.ecse223.block.view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.List;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOGame;
import ca.mcgill.ecse223.block.application.Block223Application;

public class EditorMenu extends JFrame{

	
	private String error;
	private static final long serialVersionUID = 1L;

	private JPanel editorPanel;
	private JLabel errorMessage;
	private JLabel nameLabel;
	private JTextField gamenameField;
	private JComboBox<String> existGameBox;
	private JButton createGameButton;
	private JButton loadGameButton;
	private JButton deleteGameButton;
	private JButton logoutButton;
	
	
	public EditorMenu() {
		initComponents();
		refreshData();
		centreWindow();
	}
	
	private void initComponents() {
		/*Init*/
		editorPanel = new JPanel();
		errorMessage = new JLabel();
		nameLabel= new JLabel();
		gamenameField = new JTextField();
		existGameBox = new JComboBox<String>();
		createGameButton = new JButton();
		loadGameButton = new JButton();
		deleteGameButton = new JButton();
		logoutButton = new JButton();
		
		/*set value*/
		errorMessage.setForeground(Color.RED);
		errorMessage.setText("");
		
		nameLabel.setText("ADMIN NAME:");
		loadgamenames();
		
		
		createGameButton.setText("Create a new game");
		loadGameButton.setText("Load existing Game");
		deleteGameButton.setText("Delete");
		logoutButton.setText("Logout");
		
		
		/*Add listener*/
		createGameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				createGameAction(evt);
			}
		});
		
		loadGameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loadGameAction(evt);
			}
		});
		
		deleteGameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				deleteGameAction(evt);
			}
		});
		
		logoutButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				logoutAction(evt);
			}
		});
		
		/*Layout*/
		this.setTitle("Block223 Editor");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100,100,900,200);
		Dimension minimunsiDimension = new Dimension(500,200);
		setMinimumSize(minimunsiDimension);
		
		GroupLayout layout = new GroupLayout(editorPanel);
		editorPanel.setLayout(layout);
		layout.setAutoCreateGaps(true);
	    layout.setAutoCreateContainerGaps(true);
	    
	   /*horizontal*/
	   GroupLayout.ParallelGroup creareGameGroup = layout.createParallelGroup().addComponent(nameLabel).addComponent(gamenameField).addComponent(createGameButton);
	   GroupLayout.ParallelGroup gameNameBoxGroup = layout.createParallelGroup().addComponent(errorMessage).addComponent(existGameBox).addComponent(loadGameButton).addComponent(deleteGameButton);
	   GroupLayout.SequentialGroup hSeqGroup = layout.createSequentialGroup().addGroup(creareGameGroup).addGroup(gameNameBoxGroup);
	   GroupLayout.ParallelGroup hGroupLayout = layout.createParallelGroup().addGroup(hSeqGroup).addComponent(logoutButton, Alignment.CENTER);
	   layout.setHorizontalGroup(hGroupLayout);
	   
	   /*vertical*/
	   GroupLayout.ParallelGroup line1Group = layout.createParallelGroup().addComponent(nameLabel).addComponent(errorMessage);
	   GroupLayout.ParallelGroup line2Group = layout.createParallelGroup().addComponent(gamenameField).addComponent(existGameBox);
	   GroupLayout.ParallelGroup line3Group = layout.createParallelGroup().addComponent(createGameButton).addComponent(loadGameButton);
	   GroupLayout.SequentialGroup  vGroupLayout = layout.createSequentialGroup().addGroup(line1Group).addGroup(line2Group).addGroup(line3Group).addComponent(deleteGameButton).addComponent(logoutButton);
	   layout.setVerticalGroup(vGroupLayout);
	   
	   this.setContentPane(editorPanel);
	   
	   pack();
	   //setVisible(true);
	}
	
	public void centreWindow() {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
	    this.setLocation(x, y);
	}
	
	private void refreshData() {
		errorMessage.setText(error);
		loadgamenames();
		if (error == null || error.length() == 0) {
			gamenameField.setText("");
			
		}
	}
	
	
	
	/*Button actions*/
	private void createGameAction(java.awt.event.ActionEvent evt) {
		error = null;
		try {
			Block223Controller.createGame(gamenameField.getText());
			Block223Application.openDefineGamePage();
			dispose();
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		refreshData();
	}
	
	
		
	private void loadGameAction(java.awt.event.ActionEvent evt) {
		error = null;
		
		try {
			Block223Controller.selectGame((String)existGameBox.getSelectedItem());
			Block223Application.openGameSettingPage();
			dispose();
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		refreshData();
	}
	private void deleteGameAction(java.awt.event.ActionEvent evt) {
		
		error = null;
		try {
			Block223Controller.deleteGame((String)existGameBox.getSelectedItem());
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		refreshData();
	}
	private void logoutAction(java.awt.event.ActionEvent evt) {
		Block223Controller.logout();
		Block223Application.openLoginPage();
		dispose();
		
	}
	private void loadgamenames() {
		error = null;
		existGameBox.removeAllItems();
		java.util.List<TOGame> games;
		try {
			games = Block223Controller.getDesignableGames();
			for (TOGame game : games) {
				existGameBox.addItem(game.getName());
			}
		} catch (InvalidInputException e) {
			
			error = e.getMessage();
		}
		
	}
	
	
}
