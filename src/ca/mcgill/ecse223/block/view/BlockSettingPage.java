package ca.mcgill.ecse223.block.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOBlock;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;

public class BlockSettingPage extends JFrame {
	
	
	private static final long serialVersionUID =-45132154515L;
	
	private JLabel errorMessage;
	
	//select block 
	private JComboBox<String> blockList;
	private JButton deleteBlockTypeButton;
	
	private JLabel blockIdLabel;
	private JLabel worthLabel;
	//private JLabel nameLabel;
	private JLabel rLabel;
	private JLabel gLabel;
	private JLabel bLabel;
	private JTextField worthJTextField;
	//private JTextField nameJTextField;
	private JTextField rJTextField;
	private JTextField gJTextField;
	private JTextField bJTextField;
	
	
	//buttons
	private JButton chooseBlockTypeButton;
	private JButton backButton;
	private JButton addBlockTypeButton;
	private JButton saveButton;
	
	private JLabel successMessage;
	private String success="";
	
	//data elements
	private String error=null;
	private Integer selectedBlock = null;
	
	private HashMap<Integer,Integer> blocks;
	
	public BlockSettingPage(){
		initComponents();
		refreshData();
		centreWindow();
	}
	
	public void initComponents() {
		//elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		
		successMessage=new JLabel();
		successMessage.setBounds(15,5,206,15);
		this.getContentPane().add(successMessage);
		successMessage.setForeground(Color.GREEN);
		
		//elements for select block
		blockList=new JComboBox<String>();
		deleteBlockTypeButton=new JButton();
		deleteBlockTypeButton.setText("Delete Block Type");
		
		//elements for block attributes
		blockIdLabel=new JLabel();
		blockIdLabel.setText("ID");
		worthLabel = new JLabel();
		worthLabel.setText("Worth");
		//nameLabel = new JLabel();
		//nameLabel.setText("Name");
		rLabel = new JLabel();
		rLabel.setText("R");
		gLabel = new JLabel();
		gLabel.setText("G");
		bLabel = new JLabel();
		bLabel.setText("B");
		
		worthJTextField = new JTextField();
		//nameJTextField= new JTextField();
		rJTextField= new JTextField();
		gJTextField= new JTextField();
		bJTextField= new JTextField();
		
		//elements for buttons
		chooseBlockTypeButton = new JButton();
		chooseBlockTypeButton.setText("Edit");
		backButton = new JButton();
		backButton.setText("Back");
		addBlockTypeButton= new JButton();
		addBlockTypeButton.setText("Add Block Type");
		saveButton= new JButton();
		saveButton.setText("Save");
		
		// global settings
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Block Setting");
		
		chooseBlockTypeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
					chooseBlockTypeButtonActionPerformed(evt);
			
			}
		});
		deleteBlockTypeButton.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						
							deleteBlockTypeButtonActionPerformed(evt);
					
					}
		});
		backButton.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						
							backButtonActionPerformed(evt);
						
					}
		});
		addBlockTypeButton.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						
							addBlockTypeButtonActionPerformed(evt);
					
					}
		});
		saveButton.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						
							try {
								saveButtonActionPerformed(evt);
							} catch (InvalidInputException e) {
								
								e.printStackTrace();
							}
					
					}
		});
		
		GroupLayout layout = new GroupLayout( getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
	
		layout.setHorizontalGroup(
				layout.createParallelGroup()
				.addComponent(errorMessage)
				.addGroup(
						layout.createSequentialGroup()
						.addGroup(
								layout.createParallelGroup()
								.addComponent(blockList,50,150,500)
								.addGroup(
										layout.createSequentialGroup()
										.addGroup(
												layout.createParallelGroup()
												.addComponent(worthLabel)
												.addComponent(rLabel)
												.addComponent(gLabel)
												.addComponent(bLabel)
												)
										.addGroup(
												layout.createParallelGroup()
												.addComponent(worthJTextField,50,60,500)
												.addComponent(rJTextField)
												.addComponent(gJTextField)
												.addComponent(bJTextField)
												)										
										)								
								)
						
						.addGroup(
								layout.createParallelGroup()
								.addComponent(deleteBlockTypeButton)
								.addComponent(chooseBlockTypeButton)
								.addComponent(addBlockTypeButton)
								.addComponent(saveButton)
								.addComponent(backButton)
								)
						
				)																
		);
		

		
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {deleteBlockTypeButton,backButton,addBlockTypeButton,saveButton,chooseBlockTypeButton});
		layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] {deleteBlockTypeButton,backButton,addBlockTypeButton,saveButton});
		layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] {deleteBlockTypeButton,blockList});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {worthJTextField, rJTextField, gJTextField, bJTextField});
		layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] {worthJTextField, rJTextField, gJTextField, bJTextField});
		

		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)
				.addGroup(
						layout.createParallelGroup()
						.addComponent(blockList)
						.addComponent(deleteBlockTypeButton)
						)
				.addGroup(
						layout.createParallelGroup()
						.addComponent(worthLabel)
						.addComponent(worthJTextField)
						.addComponent(chooseBlockTypeButton)
						)
				
						
				.addGroup(
						layout.createParallelGroup()
						.addComponent(rLabel)
						.addComponent(rJTextField)
						.addComponent(saveButton)
						)
				.addGroup(
						layout.createParallelGroup()
						.addComponent(gLabel)
						.addComponent(gJTextField)
						.addComponent(addBlockTypeButton)
						)
				.addGroup(
						layout.createParallelGroup()
						.addComponent(bLabel)
						.addComponent(bJTextField)
						.addComponent(backButton)
						)
				
			);
;
		updateBlocklist();
		pack();
		
	}
	
	private void refreshData() {
		// error
		errorMessage.setText(error);
		successMessage.setText(success);
		selectedBlock = blockList.getSelectedIndex();
		
		if (error == null || error.length() == 0) {
			// populate page with data
			
			if(selectedBlock<0) {
				worthJTextField.setText("");
				rJTextField.setText("");
				bJTextField.setText("");
				
				gJTextField.setText("");
			}	
			if(selectedBlock>=0) {
				Integer blockId=blocks.get(selectedBlock);
				Game currentGame =Block223Application.getCurrentGame();
				
				rJTextField.setText(String.valueOf(currentGame.findBlock(blockId).getRed()));
				worthJTextField.setText(String.valueOf(currentGame.findBlock(blockId).getPoints()));
				bJTextField.setText(String.valueOf(currentGame.findBlock(blockId).getBlue()));
				
				gJTextField.setText(String.valueOf(currentGame.findBlock(blockId).getGreen()));
			}
			
			
		}
		pack();
	}
	


	private void deleteBlockTypeButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		error = null;
		success=null;
		boolean ifSuccess =true;
		selectedBlock =blockList.getSelectedIndex();
		// call the controller
		if(selectedBlock<0) {
			ifSuccess=false;
			error="Block needs to be selected to delete!";
		}
		
		if(error == null || error.length()==0) {
			try {
				Block223Controller.deleteBlock(blocks.get(selectedBlock));
				updateBlocklist();
			} catch (InvalidInputException e) {
				ifSuccess=false;
				error = e.getMessage();
			}
		}
		
		if(ifSuccess) {
			success="Successfully deleted the block Type";
		}
		// update visuals
		
		refreshData();
	}
	
	private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message

		Block223Application.openGameSettingPage();
		dispose();

	}
	
	
	private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) throws InvalidInputException {
		// clear error message
		error = null;
			
		success=null;
		boolean ifSuccess =true;
		try {
			Block223Persistence.save(Block223Application.getBlock223());
			updateBlocklist();
		} catch (RuntimeException e) {
			ifSuccess=false;
			error="Failed to save data";
			throw new InvalidInputException(e.getMessage());
		}

		if(ifSuccess) success="Data saved";

		refreshData();
	}
	
	private void chooseBlockTypeButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error="";
		success=null;
		boolean ifSuccess =true;
		selectedBlock =blockList.getSelectedIndex();
		// call the controller
		if(selectedBlock<0) {
			ifSuccess=false;
			error="A block needs to be selected!";
		}
		
		if(!rJTextField.getText().matches("^[0-9]+$")|!gJTextField.getText().matches("^[0-9]+$")|!bJTextField.getText().matches("^[0-9]+$")|!worthJTextField.getText().matches("^[0-9]+$")) {
			ifSuccess=false;
			error="Input can only be numbers";
		}
		
		if(error.length()==0) {
			try {
				Block223Controller.updateBlock(blocks.get(selectedBlock),
						Integer.parseInt(rJTextField.getText()),
						Integer.parseInt(gJTextField.getText()),
						Integer.parseInt(bJTextField.getText()),
						Integer.parseInt(worthJTextField.getText())
						);
				updateBlocklist();
			} catch (InvalidInputException e) {

				//System.out.println("1");

				System.out.println("error update block");
				ifSuccess=false;

				error = e.getMessage();
			}
		}
		
		if(ifSuccess) {
			success="Block updated";
		}
		// update visuals
		
		refreshData();
	}
	
	private void addBlockTypeButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		error = null;
		success=null;
		boolean ifSuccess =true;
		if(!rJTextField.getText().matches("^[0-9]+$")|!gJTextField.getText().matches("^[0-9]+$")|!bJTextField.getText().matches("^[0-9]+$")|!worthJTextField.getText().matches("^[0-9]+$")) {
			ifSuccess=false;
			error="Input can only be numbers";
		}else {
		
			try {
				Block223Controller.addBlock(
						Integer.parseInt(rJTextField.getText()),
						Integer.parseInt(gJTextField.getText()),
						Integer.parseInt(bJTextField.getText()),
						Integer.parseInt(worthJTextField.getText())
						);
				selectedBlock = null;
				updateBlocklist();
			} catch (InvalidInputException e) {
				ifSuccess=false;
				error = e.getMessage();
			}
		}
		// update visuals
		if(ifSuccess) {
			success="Block added Successfully";
		}

		refreshData();
	}
	
	private void updateBlocklist() {
		blocks = new HashMap<Integer, Integer>();
		blockList.removeAllItems();
		selectedBlock = null;
		Integer index = 0;
		
		try {
			for (TOBlock block : Block223Controller.getBlocksOfCurrentDesignableGame()) {
				blocks.put(index, block.getId());
				blockList.addItem("#" + block.getId()+" "+"pts:"+block.getPoints()+"("+block.getRed()+"-"+block.getGreen()+"-"+block.getBlue()+")");
				index++;
			}
		}
		catch (InvalidInputException e) {
			error=e.getMessage();
		}
		
	}
	public void centreWindow() {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
	    this.setLocation(x, y);
	    //System.out.println("centre");
	}
	
	
	
}
