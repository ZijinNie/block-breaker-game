package ca.mcgill.ecse223.block.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;

public class SignUpPage extends JFrame {
	
	private static final long serialVersionUID = 2L;
	
	// UI elements
	private JLabel errorMessage;
	private JLabel successMessage;
	// Label and Textfield
	private JTextField usernameTextfield;
	private JLabel usernameLabel;
	private JTextField playerpasswordTextfield;
	private JLabel playerpasswordLabel;
	private JTextField adminpasswordTextfield;
	private JLabel adminpasswordLabel;
	// Buttons
	private JButton createaccountButton;
	private JButton cancelButton;
	
	// data elements
	private String error = null;
	private String success = null;

	
	public SignUpPage() {
		initComponents();
		refreshData();
		this.centreWindow();
	}
	
	private void initComponents() {
		// elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		// elements for username
		usernameTextfield = new JTextField();
		usernameLabel = new JLabel();
		usernameLabel.setText("Username:");
		// elements for playerpassword
		playerpasswordTextfield = new JTextField();
		playerpasswordLabel = new JLabel();
		playerpasswordLabel.setText("Player Password:");
		// elements for adminpassword
		adminpasswordTextfield = new JTextField();
		adminpasswordLabel = new JLabel();
		adminpasswordLabel.setText("Admin Password:");
		// elements for button
		createaccountButton = new JButton();
		createaccountButton.setText("Create");
		cancelButton = new JButton();
		cancelButton.setText("Return");
		
		successMessage=new JLabel();
		successMessage.setBounds(15,5,206,15);
		this.getContentPane().add(successMessage);
		successMessage.setForeground(Color.GREEN);
		
		// global settings
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("SignUp Page");
		
		//listeners for create account
		createaccountButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				createaccountActionPerformed(evt);
			}
		});
		//listeners for cancel
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelActionPerformed(evt);
			}
		});
		
		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(
				layout.createParallelGroup()
				.addComponent(errorMessage)
				.addGroup(
						layout.createSequentialGroup()
						.addComponent(usernameLabel)
						.addComponent(usernameTextfield)
						)
				.addGroup(
						layout.createSequentialGroup()
						.addComponent(playerpasswordLabel)
						.addComponent(playerpasswordTextfield)
						)
				.addGroup(
						layout.createSequentialGroup()
						.addComponent(adminpasswordLabel)
						.addComponent(adminpasswordTextfield)
						)
				.addGroup(
						layout.createSequentialGroup()
						.addComponent(createaccountButton)
						.addComponent(cancelButton)
						)
		);
		layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] 
				{usernameTextfield,playerpasswordTextfield,adminpasswordTextfield,createaccountButton,cancelButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] 
				{usernameTextfield,playerpasswordTextfield,adminpasswordTextfield,createaccountButton,cancelButton});
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)
				.addGroup(
						layout.createParallelGroup()
						.addComponent(usernameLabel)
						.addComponent(usernameTextfield)
						)
				.addGroup(
						layout.createParallelGroup()
						.addComponent(playerpasswordLabel)
						.addComponent(playerpasswordTextfield)
						)
				.addGroup(
						layout.createParallelGroup()
						.addComponent(adminpasswordLabel)
						.addComponent(adminpasswordTextfield)
						)
				.addGroup(
						layout.createParallelGroup()
						.addComponent(createaccountButton)
						.addComponent(cancelButton)
						)
		);
		pack();
	}
	
	private void refreshData() {
		// error
		errorMessage.setText(error);
		successMessage.setText(success);
		if (error == null || error.length() == 0) {
			// populate page with data
			usernameTextfield.setText("");
			playerpasswordTextfield.setText("");
			adminpasswordTextfield.setText("");
		}
		pack();		
	}
	
	private void createaccountActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		error = null;
		success = null;
		// call the controller
		try {
			Block223Controller.register(usernameTextfield.getText(), playerpasswordTextfield.getText(), adminpasswordTextfield.getText());
			success = "Successfully create account!";
		}catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		//update visuals
		refreshData();
	}
	private void cancelActionPerformed(java.awt.event.ActionEvent evt) {
		Block223Application.openLoginPage();
		dispose();
	}
	public void centreWindow() {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
	    this.setLocation(x, y);
	}
}
