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
import ca.mcgill.ecse223.block.controller.TOUserMode;
import ca.mcgill.ecse223.block.controller.TOUserMode.Mode;


public class LoginPage extends JFrame{
	
	private static final long serialVersionUID = -4426310869335015542L;
	
	// UI elements
	private JLabel errorMessage;
	// login
	private JTextField usernameTextfield;
	private JLabel usernameLabel;
	private JTextField passwordTextfield;
	private JLabel passwordLabel;
	private JButton loginButton;
	//create account
	private JButton createAccountButton;
	
	// data elements
	private String error = null;
	
	public LoginPage() {
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
		// elements for password
		passwordTextfield = new JTextField();
		passwordLabel = new JLabel();
		passwordLabel.setText("Password:");
		// elements for login button
		loginButton = new JButton();
		loginButton.setText("Login");
		createAccountButton = new JButton();
		createAccountButton.setText("Create Account");
		
		// global settings
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Login Page");
		
		//listeners for login
		loginButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loginActionPerformed(evt);
			}
		});
		//listeners for createAccount
		createAccountButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				createaccountActionPerformed(evt);
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
						.addComponent(passwordLabel)
						.addComponent(passwordTextfield)
						)
				.addGroup(
						layout.createSequentialGroup()
						.addComponent(loginButton)
						.addComponent(createAccountButton)
						)
		);
		layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] 
				{usernameTextfield,passwordTextfield,loginButton,createAccountButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] 
				{usernameTextfield,passwordTextfield,loginButton,createAccountButton});
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
						.addComponent(passwordLabel)
						.addComponent(passwordTextfield)
						)
				.addGroup(
						layout.createParallelGroup()
						.addComponent(loginButton)
						.addComponent(createAccountButton)
						)
		);
		pack();
	}
	private void refreshData() {
		// error
		errorMessage.setText(error);
		if (error == null || error.length() == 0) {
			// populate page with data
			usernameTextfield.setText("");
			passwordTextfield.setText("");
		}
		pack();
	}
	private void loginActionPerformed(java.awt.event.ActionEvent evt){
		// clear error message
		error = null;
		// call the controller
		try {
			Block223Controller.login(usernameTextfield.getText(), passwordTextfield.getText());
		}catch (InvalidInputException e) {
			error = e.getMessage();
		}
		TOUserMode to = Block223Controller.getUserMode();
		if (to.getMode() == Mode.Design) {
			Block223Application.openEditorMenu();
			dispose();
		}else if (to.getMode() == Mode.Play) {
			Block223Application.openPlayerMenuPage();
			dispose();
		}
		//update visuals
		refreshData();
	}
	private void createaccountActionPerformed(java.awt.event.ActionEvent evt){
		// clear error message
		
		// call the controller
		
			Block223Application.openSignUpPage();
			dispose();
		
		//update visuals
		refreshData();
	}
	public void centreWindow() {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
	    this.setLocation(x, y);
	}

}
