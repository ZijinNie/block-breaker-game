package ca.mcgill.ecse223.block.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Window;
import javax.swing.JButton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOBlock;
import ca.mcgill.ecse223.block.controller.TOGridCell;
import ca.mcgill.ecse223.block.model.Level;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;

import java.awt.Choice;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class GameSettingPageDemo {

	private static final long serialVersionUID =-45132154515L;
	
	JFrame frame;
	private JTextField xTextField;
	private JTextField yTextField;
	private JTextField addXTextField;
	private JTextField addYTextField;
	private JTextField moveFromXTextField;
	private JTextField moveFromYTextField;
	private JTextField moveToXTextField;
	private JTextField moveToYTextField;

	
	private JLabel errorMessage;
	private String error=null;
	
	private JButton clearButton;
	private JButton blockSettingButton;
	private JLabel xLabel;
	private JLabel yLabel;
	private JComboBox<String> levelList;
	private JButton checkButton;
	private JButton deleteButton;
	private JLabel selectedSlotLabel;
	private JLabel selectedXLabel;
	private JLabel selectedYLabel;
	private JLabel selectedPointsLabel;
	private JLabel selectedRLabel;
	private JLabel selectedGLabel;
	private JLabel selectedBLabel;
	private JLabel selectedXValueLabel;
	private JLabel selectedYValueLabel;
	private JLabel selectedPointsValueLabel;
	private JLabel selectedRedValueLabel;
	private JLabel selectedGreenValueLabel;
	private JLabel selectedBlueValueLabel;
	private JLabel addXLabel;
	private JLabel addYLabel;
	private JLabel addTypeLabel;
	private JButton addButton;
	private JLabel moveFromXLabel;
	private JLabel moveFromYLabel;
	private JLabel moveToXLabel;
	private JLabel moveToYLabel;
	private JLabel toLabel;
	private JButton moveButton;
	private JLabel levelNumLabel;
	private JButton cancelButton;
	private JButton saveButton;
	private JButton gameSettingsButton;

	
	
	private HashMap<Integer,Integer> levels;
	private HashMap<Integer,Integer> blocks;
	private JComboBox<String> blockList;
	
	private int currentLevel;
	private JSeparator separator;
	private JSeparator separator_1;
	private JSeparator separator_2;

	/**
	 * Create the application.
	 */
	public GameSettingPageDemo() {
		initialize();
	}
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameSettingPageDemo window = new GameSettingPageDemo();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 617, 404);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		levelList = new JComboBox<String>();
		levelList.setBounds(38, 30, 178, 23);
		frame.getContentPane().add(levelList);
		
		clearButton = new JButton("Clear");
		clearButton.setBounds(253, 30, 97, 23);
		frame.getContentPane().add(clearButton);
		
		blockSettingButton = new JButton("Edit Block");
		blockSettingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		blockSettingButton.setBounds(401, 85, 122, 23);
		frame.getContentPane().add(blockSettingButton);
		
		xLabel = new JLabel("X");
		xLabel.setBounds(38, 100, 58, 15);
		frame.getContentPane().add(xLabel);
		
		yLabel = new JLabel("Y");
		yLabel.setBounds(38, 125, 58, 15);
		frame.getContentPane().add(yLabel);
		
		xTextField = new JTextField();
		xTextField.setBounds(64, 97, 66, 21);
		frame.getContentPane().add(xTextField);
		xTextField.setColumns(10);
		
		yTextField = new JTextField();
		yTextField.setBounds(64, 122, 66, 21);
		frame.getContentPane().add(yTextField);
		yTextField.setColumns(10);
		
		checkButton = new JButton("Check");
		checkButton.setBounds(58, 153, 72, 23);
		frame.getContentPane().add(checkButton);
		
		deleteButton = new JButton("Delete");
		deleteButton.setBounds(58, 188, 72, 23);
		frame.getContentPane().add(deleteButton);
		
		selectedSlotLabel = new JLabel("Selected Slot");
		selectedSlotLabel.setForeground(Color.DARK_GRAY);
		selectedSlotLabel.setBackground(Color.GRAY);
		selectedSlotLabel.setBounds(168, 102, 97, 23);
		frame.getContentPane().add(selectedSlotLabel);
		
		selectedXLabel = new JLabel("X");
		selectedXLabel.setBounds(168, 135, 20, 15);
		frame.getContentPane().add(selectedXLabel);
		
		selectedYLabel = new JLabel("Y");
		selectedYLabel.setBounds(168, 160, 12, 15);
		frame.getContentPane().add(selectedYLabel);
		
		selectedPointsLabel = new JLabel("Points");
		selectedPointsLabel.setBounds(168, 188, 48, 15);
		frame.getContentPane().add(selectedPointsLabel);
		
		selectedRLabel = new JLabel("R");
		selectedRLabel.setBounds(275, 135, 12, 15);
		frame.getContentPane().add(selectedRLabel);
		
		selectedGLabel = new JLabel("G");
		selectedGLabel.setBounds(275, 160, 12, 15);
		frame.getContentPane().add(selectedGLabel);
		
		selectedBLabel = new JLabel("B");
		selectedBLabel.setBounds(275, 188, 16, 15);
		frame.getContentPane().add(selectedBLabel);
		
		selectedXValueLabel = new JLabel("5");
		selectedXValueLabel.setBounds(207, 135, 58, 15);
		frame.getContentPane().add(selectedXValueLabel);
		
		selectedYValueLabel = new JLabel("3");
		selectedYValueLabel.setBounds(207, 160, 58, 15);
		frame.getContentPane().add(selectedYValueLabel);
		
		selectedPointsValueLabel = new JLabel("1");
		selectedPointsValueLabel.setBounds(217, 188, 39, 15);
		frame.getContentPane().add(selectedPointsValueLabel);
		
		selectedRedValueLabel = new JLabel("123");
		selectedRedValueLabel.setBounds(297, 135, 58, 15);
		frame.getContentPane().add(selectedRedValueLabel);
		
		selectedGreenValueLabel = new JLabel("145");
		selectedGreenValueLabel.setBounds(297, 160, 58, 15);
		frame.getContentPane().add(selectedGreenValueLabel);
		
		selectedBlueValueLabel = new JLabel("23");
		selectedBlueValueLabel.setBounds(297, 188, 58, 15);
		frame.getContentPane().add(selectedBlueValueLabel);
		
		addXTextField = new JTextField();
		addXTextField.setBounds(57, 269, 66, 21);
		frame.getContentPane().add(addXTextField);
		addXTextField.setColumns(10);
		
		addXLabel = new JLabel("X");
		addXLabel.setBounds(33, 272, 58, 15);
		frame.getContentPane().add(addXLabel);
		
		addYLabel = new JLabel("Y");
		addYLabel.setBounds(134, 272, 58, 15);
		frame.getContentPane().add(addYLabel);
		
		addYTextField = new JTextField();
		addYTextField.setBounds(154, 269, 66, 21);
		frame.getContentPane().add(addYTextField);
		addYTextField.setColumns(10);
		
		
		addTypeLabel = new JLabel("Type");
		addTypeLabel.setBounds(230, 272, 58, 15);
		frame.getContentPane().add(addTypeLabel);
		
		addButton = new JButton("Add");
		addButton.setBounds(460, 268, 97, 23);
		frame.getContentPane().add(addButton);
		
		moveFromXLabel = new JLabel("X");
		moveFromXLabel.setBounds(33, 238, 58, 15);
		frame.getContentPane().add(moveFromXLabel);
		
		moveFromXTextField = new JTextField();
		moveFromXTextField.setColumns(10);
		moveFromXTextField.setBounds(57, 235, 66, 21);
		frame.getContentPane().add(moveFromXTextField);
		
		moveFromYLabel = new JLabel("Y");
		moveFromYLabel.setBounds(134, 238, 58, 15);
		frame.getContentPane().add(moveFromYLabel);
		
		moveFromYTextField = new JTextField();
		moveFromYTextField.setColumns(10);
		moveFromYTextField.setBounds(154, 235, 66, 21);
		frame.getContentPane().add(moveFromYTextField);
		
		toLabel = new JLabel("to");
		toLabel.setFont(new Font("ËÎÌו", Font.PLAIN, 14));
		toLabel.setBounds(230, 238, 20, 15);
		frame.getContentPane().add(toLabel);
		
		moveToXLabel = new JLabel("X");
		moveToXLabel.setBounds(253, 238, 58, 15);
		frame.getContentPane().add(moveToXLabel);
		
		moveToXTextField = new JTextField();
		moveToXTextField.setColumns(10);
		moveToXTextField.setBounds(277, 235, 66, 21);
		frame.getContentPane().add(moveToXTextField);
		
		moveToYLabel = new JLabel("Y");
		moveToYLabel.setBounds(354, 238, 58, 15);
		frame.getContentPane().add(moveToYLabel);
		
		moveToYTextField = new JTextField();
		moveToYTextField.setColumns(10);
		moveToYTextField.setBounds(374, 235, 66, 21);
		frame.getContentPane().add(moveToYTextField);
		
		moveButton = new JButton("Move");
		moveButton.setBounds(460, 234, 97, 23);
		frame.getContentPane().add(moveButton);
		
	levelNumLabel = new JLabel("Level");
		levelNumLabel.setBounds(38, 10, 58, 15);
		frame.getContentPane().add(levelNumLabel);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(142, 312, 97, 23);
		frame.getContentPane().add(cancelButton);
		
		saveButton = new JButton("Update & Save");
		saveButton.setBounds(275, 312, 122, 23);
		frame.getContentPane().add(saveButton);
		
		gameSettingsButton = new JButton("Game Settings");
		gameSettingsButton.setBounds(401, 118, 122, 23);
		frame.getContentPane().add(gameSettingsButton);
		
		blockList = new JComboBox<String>();
		blockList.setBounds(275, 268, 165, 23);
		frame.getContentPane().add(blockList);
		
		separator = new JSeparator();
		separator.setBounds(154, 93, 178, 121);
		frame.getContentPane().add(separator);
		
		separator_1 = new JSeparator();
		separator_1.setBounds(10, 67, 584, 298);
		frame.getContentPane().add(separator_1);
		
		separator_2 = new JSeparator();
		separator_2.setBounds(10, 227, 584, 138);
		frame.getContentPane().add(separator_2);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(10, 5, 206, 15);
		frame.getContentPane().add(lblNewLabel);
		
		
		
		int a=38, b=111,c=28,d=20;
		
		List<Choice> choices=new ArrayList<Choice>();
		
		for(int i=0;i<15;i++) {
			for(int j=0;j<15;j++) {
				Choice choice =new Choice();
				choice.setBounds(a+34*j,b+26*i,c,d);
				frame.getContentPane().add(choice);
				choices.add(choice);
			}
		}
		
		checkButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
					checkButtonActionPerformed(evt);
			
			}
		});
		
		clearButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
					clearButtonActionPerformed(evt);
			
			}
		});
		
		blockSettingButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
					blockSettingButtonActionPerformed(evt);
			
			}
		});
		
		deleteButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
					deleteButtonActionPerformed(evt);
			
			}
		});
		
		moveButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
					moveButtonActionPerformed(evt);
			
			}
		});
		
		addButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
					addButtonActionPerformed(evt);
			
			}
		});
		
		
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
					cancelButtonActionPerformed(evt);
			
			}
		});
		
		saveButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				saveButtonActionPerformed(evt);
			
			}
		});
		
		gameSettingsButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
					gameSettingsButtonActionPerformed(evt);
			
			}
		});
	
	}
	
	//TODO
	private void refreshData()  {
		// error
		errorMessage.setText(error);
		
		//REINITIALIZE CURRENT LEVEL
		
		
		currentLevel=levels.get(levelList.getSelectedIndex());
		
		
		if (error == null || error.length() == 0) {
//			xTextField.setText("");
//			yTextField.setText("");
//			addXTextField.setText("");
//			addYTextField.setText("");
//			moveFromXTextField.setText("");
//			moveFromYTextField.setText("");
//			moveToXTextField.setText("");
//			moveToYTextField.setText("");
//		
		
			blocks = new HashMap<Integer, Integer>();
			blockList.removeAllItems();
			Integer index = 0;
			
			try {
				for (TOBlock block : Block223Controller.getBlocksOfCurrentDesignableGame()) {
					blocks.put(index, block.getId());
					blockList.addItem("#" + block.getId()+" "+"pts:"+block.getPoints()+"("+block.getRed()+block.getGreen()+block.getBlue()+")");
					index++;
				};
				blockList.setSelectedIndex(-1);
			}
			catch (InvalidInputException e) {
				error=e.getMessage();
			}
			
			levels = new HashMap<Integer, Integer>();
			levelList.removeAllItems();
			Integer levelIndex = 0;
			
			for (Level level : Block223Application.getCurrentGame().getLevels()) {
				levels.put(index, index);
				levelList.addItem(index.toString());
				index++;
			};
			
		}
		
		
	}		
			

	private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error = null;
		try {
			List<TOGridCell> cells =Block223Controller.getBlocksAtLevelOfCurrentDesignableGame(currentLevel);
			for (TOGridCell cell:cells) {
				Block223Controller.removeBlock(currentLevel, 
						cell.getGridHorizontalPosition(), 
						cell.getGridVerticalPosition());
			}
		}catch (InvalidInputException e) {
			error = e.getMessage();
		}
		refreshData();
	}
	
	private void blockSettingButtonActionPerformed(java.awt.event.ActionEvent evt) {
		Block223Application.openBlockSettingPage();
		frame.dispose();
		refreshData();
	}
	
	//TODO
	private void checkButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error = null;
		
			int x = Integer.parseInt(xTextField.getText());
			int y = Integer.parseInt(yTextField.getText());
			if (x<=0 || y<=0 || x>15 || y>15) {
				error = "X and Y cannot be less than 1 or greater than 15";
			}else {
				updateselectedslot(x,y);
			}
	
			
		
		refreshData();
		
	}
	
	private void updateselectedslot(int x, int y) {
		List<TOGridCell> cells = new ArrayList<TOGridCell>();;
		try {
			cells = Block223Controller.getBlocksAtLevelOfCurrentDesignableGame(currentLevel);
			
		} catch (InvalidInputException e) {
			error=e.getMessage();
		}
		boolean findcell = false;
		
		TOGridCell fcell = null;
		
		for (TOGridCell cell :cells) {
			int cx = cell.getGridHorizontalPosition();
			int cy = cell.getGridVerticalPosition();
			if (x == cx && y == cy) {
				findcell = true;
				fcell = cell;
				break;
			}
		}
		selectedXLabel.setText("x:"+x);
		selectedYLabel.setText("y:"+y);
		if (findcell == false) {
			//slelectedBlockLabel.setText("id: null");
			selectedRLabel.setText("R: ");
			selectedGLabel.setText("G: ");
			selectedBLabel.setText("B: ");
			selectedPointsLabel.setText("Points: ");
		}else {
			//blocknumLabel.setText("id:"+fcell.getId());
			selectedRedValueLabel.setText("R: "+fcell.getRed());
			selectedGreenValueLabel.setText("G: "+fcell.getGreen());
			selectedBlueValueLabel.setText("B: "+fcell.getBlue());
			selectedPointsValueLabel.setText("Worth: "+fcell.getPoints());
		}
		
	}
	
	private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error = null;
		boolean findcell = false;
		TOGridCell fcell = null;
		int x,y;
		x=Integer.parseInt(xTextField.getText());
		y=Integer.parseInt(yTextField.getText());
		if (x <=0 || y<=0) {
			error = "you must enter the x y position first";
		}else {
			try {
				List<TOGridCell> cells = Block223Controller.getBlocksAtLevelOfCurrentDesignableGame(currentLevel);
				for (TOGridCell cell :cells) {
					int cx = cell.getGridHorizontalPosition();
					int cy = cell.getGridVerticalPosition();
					if (cx == x && cy == y) {
						findcell = true;
						fcell = cell;
						break;
					}
				}
				if (findcell == true) {
					fcell.delete();
				}else {
					error = "No block is placing here";
				}
			}catch(InvalidInputException e) {
				error = e.getMessage();
			}
		}
	}
	
	private void moveButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error = null;
		int oldX, oldY, newX, newY;
		
		oldX=Integer.parseInt(moveFromXTextField.getText());
		oldY=Integer.parseInt(moveFromYTextField.getText());
		newX=Integer.parseInt(moveToXTextField.getText());
		newY=Integer.parseInt(moveToYTextField.getText());
		
		
		try {
			Block223Controller.moveBlock(currentLevel,oldX,oldY,newX,newY);
		} catch (InvalidInputException e) {
			error=e.getMessage();
		}
			
		
		
		refreshData();
	}
	
	private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error=null;
		int x,y;
		
		x=Integer.parseInt(addXTextField.getText());
		y=Integer.parseInt(addYTextField.getText());
		int selectedBlock =blockList.getSelectedIndex();
		
		if(selectedBlock<0) {
			error="A block needs to be selected!";
		}else {
			
		    try {
						
				Block223Controller.positionBlock(blocks.get(selectedBlock),currentLevel,x,y);
				}catch(InvalidInputException e) {
					error = e.getMessage();
				}
		}
				
	}
	
	private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error=null;
		Block223Application.openEditorMenu();
		frame.dispose();
	}
	
	private void gameSettingsButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error=null;
		Block223Application.openUpdateGamePage();
		frame.dispose();
	}
	
	//TODO
	private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error=null;
		Block223Persistence.save(Block223Application.getBlock223());
		Block223Application.openEditorMenu();
		frame.dispose();
	}
}