package ca.mcgill.ecse223.block.view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;


import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOPlayableGame;



public class PlayerMenuDemo extends JFrame{

		/**
	 *
	 */
	private static final long serialVersionUID = 2570624968612511742L;

		//private static final long serialVersionUID = L;
		private JFrame frmPlayerMenu;

		// UI elements
		private JLabel errorMessage;

		// Label and combocbox and button
		private JButton continueButton ;
		private JButton newgameButton;
		private JButton exitButton;
		private JComboBox<String> gameSaveComboBox;
		private JLabel playableGameLabel;
		private JComboBox<String> gameModelComboBox;
		private JLabel gameModeLabel;

		// data elements
		private String error = null;
//		private String success = null;

		//hashmap elment
		private HashMap<Integer,TOPlayableGame> gamesaves;
		private HashMap<Integer,TOPlayableGame> gamemodels;


//		/**
//		 * Launch the application.
//		 */
//		public static void main(String[] args) {
//			EventQueue.invokeLater(new Runnable() {
//				public void run() {
//					try {
//						PlayerMenuDemo window = new PlayerMenuDemo();
//						window.frmPlayerMenu.setVisible(true);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			});
//		}




		/**
		 * Create the application.
		 */
		public PlayerMenuDemo() {
			initialize();
			refreshData();
			this.centreWindow();
		}



	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {


		frmPlayerMenu = new JFrame();


		//global setting
		frmPlayerMenu.setTitle("Player Menu");
		frmPlayerMenu.setBounds(100, 100, 600, 379);
		frmPlayerMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPlayerMenu.getContentPane().setLayout(null);
		frmPlayerMenu.setVisible(true);


		//initialize buttons
		continueButton = new JButton();
		continueButton.setText("Continue");
		newgameButton = new JButton();
		newgameButton.setText("New Game");
		exitButton = new JButton();
		exitButton.setText("EXIT");

		//initialize loadgame == playableGame
		gameSaveComboBox = new JComboBox<String>();
		playableGameLabel = new JLabel();
		playableGameLabel.setText("Load Game:");

		//initialize choose new game == gameMode
		gameModelComboBox = new JComboBox<String>();
		gameModeLabel = new JLabel();
		gameModeLabel.setText("Choose a new game:");


		// elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		errorMessage.setBounds(10, 0, 200, 50);
		frmPlayerMenu.add(errorMessage);

		//elements for success message
//		successMessage=new JLabel();
//		successMessage.setBounds(15,5,206,15);
//		this.getContentPane().add(successMessage);
//		successMessage.setForeground(Color.GREEN);






		//JComboBox playableGameComboBox = new JComboBox();
		gameSaveComboBox.setBounds(57, 48, 172, 40);
		frmPlayerMenu.getContentPane().add(gameSaveComboBox);

		//JComboBox gameModelComboBox = new JComboBox();
		gameModelComboBox.setBounds(57, 136, 172, 45);
		frmPlayerMenu.getContentPane().add(gameModelComboBox);

		//JButton continueButton = new JButton("Continue");
		continueButton.setBounds(359, 48, 146, 40);
		frmPlayerMenu.getContentPane().add(continueButton);

		//JButton newGameButton = new JButton("New Game");
		newgameButton.setBounds(359, 136, 146, 45);
		frmPlayerMenu.getContentPane().add(newgameButton);

		//JButton exitButton = new JButton("EXIT");
		exitButton.setBounds(359, 237, 146, 45);
		frmPlayerMenu.getContentPane().add(exitButton);

		//JLabel gameModeLabel = new JLabel("Choose a new game");
		gameModeLabel.setBounds(57, 118, 109, 15);
		frmPlayerMenu.getContentPane().add(gameModeLabel);

		//JLabel playableGameLabel = new JLabel("Load Game :");
		playableGameLabel.setBounds(57, 32, 93, 15);
		frmPlayerMenu.getContentPane().add(playableGameLabel);



		//listeners for continueButton
		continueButton.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						continueButtonActionPerformed(evt);
					}
				});

		//listeners for newgameButton
				newgameButton.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						newgameActionPerformed(evt);
					}
				});

		//listeners for exitButton
				exitButton.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						exitActionPerformed(evt);
					}
				});

		//layout
//		GroupLayout layout = new GroupLayout(getContentPane());
//		getContentPane().setLayout(layout);
//		layout.setAutoCreateGaps(true);
//		layout.setAutoCreateContainerGaps(true);
//
//		layout.setHorizontalGroup(
//				layout.createParallelGroup()
//				.addComponent(errorMessage)
//				.addGroup(
//						layout.createSequentialGroup()
//						.addComponent(playableGameLabel)
//						)
//				.addGroup(
//						layout.createSequentialGroup()
//						.addComponent(playableGameComboBox)
//						.addComponent(continueButton)
//						)
//				.addGroup(
//						layout.createSequentialGroup()
//						.addComponent(gameModeLabel)
//						)
//				.addGroup(
//						layout.createSequentialGroup()
//						.addComponent(gameModelComboBox)
//						.addComponent(newgameButton)
//						)
//				.addGroup(
//						layout.createSequentialGroup()
//						.addComponent(exitButton)
//						)
//		);
//
//		layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[]
//				{playableGameComboBox,continueButton,gameModelComboBox,newgameButton,exitButton});
//		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[]
//				{playableGameComboBox,continueButton,gameModelComboBox,newgameButton,exitButton});
//		layout.setVerticalGroup(
//				layout.createSequentialGroup()
//				.addComponent(errorMessage)
//				.addGroup(
//						layout.createSequentialGroup()
//						.addComponent(playableGameLabel)
//						)
//				.addGroup(
//						layout.createSequentialGroup()
//						.addComponent(playableGameComboBox)
//						.addComponent(continueButton)
//						)
//				.addGroup(
//						layout.createSequentialGroup()
//						.addComponent(gameModeLabel)
//						)
//				.addGroup(
//						layout.createSequentialGroup()
//						.addComponent(gameModelComboBox)
//						.addComponent(newgameButton)
//						)
//				.addGroup(
//						layout.createSequentialGroup()
//						.addComponent(exitButton)
//						)
//		);
//		pack();
//

		//



	}





	/**
	 * Refresh the contents of the frame.
	 */
	public void refreshData(){
		errorMessage.setText(error);
//		successMessage.setText(success);
		//unfinsihed...

		updateplayablegamelist();



	}


	private void updateplayablegamelist() {
		gamesaves = new HashMap<Integer, TOPlayableGame>();
		gamemodels = new HashMap<Integer, TOPlayableGame>();
		gameModelComboBox.removeAllItems();
		gameSaveComboBox.removeAllItems();

		try {
			Integer index1 = 0;
			Integer index2 = 0;
			for(TOPlayableGame game: Block223Controller.getPlayableGames()) {
				if (game.getNumber() == -1) {
					gamemodels.put(index1, game);
					gameModelComboBox.addItem(index1+" "+game.getName());
					System.out.println("Models have " + game.getName());
					index1++;
				}else {
					gamesaves.put(index2, game);
					gameSaveComboBox.addItem(index2+" "+game.getName());
					System.out.println("Saves have " + game.getName());

					index2++;
				}
			}

		}
		catch (InvalidInputException e) {
			error=e.getMessage();
		}

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
	private void continueButtonActionPerformed(java.awt.event.ActionEvent evt) {

		error = null;
		try {
			int index = gameSaveComboBox.getSelectedIndex();
			if (index == -1) {
				//System.out.println("no game selected.");
				throw new InvalidInputException("no game selected.");
			}
			TOPlayableGame game = gamesaves.get(index);
			Block223Controller.selectPlayableGame(game.getName(), game.getNumber());
			frmPlayerMenu.dispose();
			//Block223Controller.startGame(new GamePlayPageDemo());
			Block223Application.openGamePlayPage();
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		refreshData();
	}

	private void newgameActionPerformed(java.awt.event.ActionEvent evt) {
		error = null;
		try {
			int index = gameModelComboBox.getSelectedIndex();
			if (index == -1) {
				//System.out.println("no game selected.");
				throw new InvalidInputException("no game selected.");
			}
			TOPlayableGame game = gamemodels.get(index);
			Block223Controller.selectPlayableGame(game.getName(), game.getNumber());
			frmPlayerMenu.dispose();
			Block223Application.openGamePlayPage();

			//Block223Controller.startGame(new GamePlayPageDemo());
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		refreshData();
	}

	private void exitActionPerformed(java.awt.event.ActionEvent evt) {
		Block223Application.openLoginPage();
		frmPlayerMenu.dispose();
		Block223Controller.logout();
		dispose();
	}

}
