package ca.mcgill.ecse223.block.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;

import java.util.Dictionary;
import java.util.Hashtable;

import java.lang.Thread;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;

public class DefineGamePage extends JFrame {

	private static final long serialVersionUID = -4426310869335015542L;

	// DEFINITION

	// error
	private JLabel errorMessage;

	// game name
	private JLabel gameNameLabel;

	// general setting
	private JLabel generalSettingLabel;
	private JLabel nrLevelsLabel;
	private JTextField nrLevelsTextField;
	private JSlider nrLevelsSlider;
	private JLabel nrBlocksLabel;
	private JTextField nrBlocksTextField;
	private JSlider nrBlocksSlider;

	// paddle setting
	private JLabel paddleSettingLabel;
	private JLabel maxPaddleLengthLabel;
	private JTextField maxPaddleLengthTextField;
	private JSlider maxPaddleLengthSlider;
	private JLabel minPaddleLengthLabel;
	private JTextField minPaddleLengthTextField;
	private JSlider minPaddleLengthSlider;

	// ball setting
	private JLabel ballSettingLabel;
	private JLabel minBallSpeedXLabel;
	private JSlider minBallSpeedXSlider;
	private JTextField minBallSpeedXTextField;
	private JLabel minBallSpeedYLabel;
	private JSlider minBallSpeedYSlider;
	private JTextField minBallSpeedYTextField;
	private JLabel ballSpeedIncreaseFactorLabel;
	private JSlider ballSpeedIncreaseFactorSlider;
	private JTextField ballSpeedIncreaseFactorTextField;

	private String bsifType;

	// buttons
	private JButton cancelButton;
	private JButton applyButton;

	// listener & adapter
	ChangeListener changeListener;
	KeyAdapter keyAdapter;

	// data elements
	private String error = null;

	public DefineGamePage() {
		initComponents();
		this.centreWindow();
	}

	// INITIALIZATION
	private void initComponents() {

		// error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);

		// game name
		gameNameLabel = new JLabel();
		gameNameLabel.setText("Game: " + Block223Application.getCurrentGame().getName());

		// number of levels & blocks
		generalSettingLabel = new JLabel();
		generalSettingLabel.setText("GENERAL SETTING");
		nrLevelsLabel = new JLabel();
		nrLevelsLabel.setText("Number of levels");
		nrLevelsSlider = new JSlider();
		nrLevelsSlider.setValue(Block223Application.getCurrentGame().numberOfLevels());
		nrLevelsSlider.setMaximum(99);
		nrLevelsSlider.setMinimum(1);
		nrLevelsSlider.setPaintLabels(true);
		nrLevelsSlider.setMajorTickSpacing(98);
		nrLevelsTextField = new JTextField("1");
		nrBlocksLabel = new JLabel();
		nrBlocksLabel.setText("Number of blocks per level");
		nrBlocksSlider = new JSlider();
		nrBlocksSlider.setMinimum(1);
		nrBlocksSlider.setValue(1);
		nrBlocksSlider.setPaintLabels(true);
		nrBlocksSlider.setMajorTickSpacing(99);
		nrBlocksTextField = new JTextField("1");

		// ball setting
		ballSettingLabel = new JLabel();
		ballSettingLabel.setText("BALL SETTING");
		minBallSpeedXLabel = new JLabel();
		minBallSpeedXLabel.setText("Min X Speed");
		minBallSpeedXSlider = new JSlider(0, 100, 1);
		minBallSpeedXSlider.setValue(1);
		minBallSpeedXSlider.setPaintLabels(true);
		minBallSpeedXSlider.setMajorTickSpacing(100);
		minBallSpeedXTextField = new JTextField("1");
		minBallSpeedYLabel = new JLabel();
		minBallSpeedYLabel.setText("Min Y Speed");
		minBallSpeedYSlider = new JSlider(0, 100, 1);
		minBallSpeedYSlider.setValue(1);
		minBallSpeedYSlider.setPaintLabels(true);
		minBallSpeedYSlider.setMajorTickSpacing(100);
		minBallSpeedYTextField = new JTextField("1");
		ballSpeedIncreaseFactorLabel = new JLabel();
		ballSpeedIncreaseFactorLabel.setText("Speed Increase Factor");
		ballSpeedIncreaseFactorSlider = new JSlider(1, 1000, 10);
		Dictionary<Integer, JLabel> bsifTable = new Hashtable<Integer, JLabel>();
		bsifTable.put(1, new JLabel("0.1"));
		bsifTable.put(1000, new JLabel("100.0"));
		ballSpeedIncreaseFactorSlider.setPaintLabels(true);
		ballSpeedIncreaseFactorSlider.setLabelTable(bsifTable);
		ballSpeedIncreaseFactorTextField = new JTextField("1.0");

		// paddle setting
		paddleSettingLabel = new JLabel();
		paddleSettingLabel.setText("PADDLE SETTING");
		maxPaddleLengthLabel = new JLabel();
		maxPaddleLengthLabel.setText("Max Length");
		maxPaddleLengthSlider = new JSlider();
		maxPaddleLengthSlider.setValue(10);
		maxPaddleLengthSlider.setMinimum(1);
		maxPaddleLengthSlider.setMaximum(390);
		maxPaddleLengthSlider.setPaintLabels(true);
		maxPaddleLengthSlider.setMajorTickSpacing(389);
		maxPaddleLengthTextField = new JTextField("10");
		minPaddleLengthLabel = new JLabel();
		minPaddleLengthLabel.setText("Min Length");
		minPaddleLengthSlider = new JSlider();
		minPaddleLengthSlider.setValue(10);
		minPaddleLengthSlider.setMinimum(1);
		minPaddleLengthSlider.setMaximum(390);
		minPaddleLengthSlider.setPaintLabels(true);
		minPaddleLengthTextField = new JTextField("10");
		minPaddleLengthSlider.setMajorTickSpacing(389);

		// buttons
		cancelButton = new JButton();
		cancelButton.setText("Cancel");
		applyButton = new JButton();
		applyButton.setText("Apply");

		// global
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Define Game Settings");

		// change listener
		changeListener = new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if ((JSlider) e.getSource() == nrLevelsSlider) {
					nrLevelsTextField.setText(String.valueOf(nrLevelsSlider.getValue()));
				} else if ((JSlider) e.getSource() == nrBlocksSlider) {
					nrBlocksTextField.setText(String.valueOf(nrBlocksSlider.getValue()));
				} else if ((JSlider) e.getSource() == minBallSpeedXSlider) {
					minBallSpeedXTextField.setText(String.valueOf(minBallSpeedXSlider.getValue()));
				} else if ((JSlider) e.getSource() == minBallSpeedYSlider) {
					minBallSpeedYTextField.setText(String.valueOf(minBallSpeedYSlider.getValue()));
				} else if ((JSlider) e.getSource() == ballSpeedIncreaseFactorSlider) {
					ballSpeedIncreaseFactorTextField
							.setText(String.valueOf(((double) ballSpeedIncreaseFactorSlider.getValue()) / 10));
				} else if ((JSlider) e.getSource() == minPaddleLengthSlider) {
					minPaddleLengthTextField.setText(String.valueOf(minPaddleLengthSlider.getValue()));
				} else if ((JSlider) e.getSource() == maxPaddleLengthSlider) {
					minPaddleLengthSlider.setMaximum(maxPaddleLengthSlider.getValue());
					Dictionary<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
					labelTable.put(1, new JLabel("1"));
					labelTable.put(maxPaddleLengthSlider.getValue(),
							new JLabel("" + (maxPaddleLengthSlider.getValue())));
					minPaddleLengthSlider.setLabelTable(labelTable);
					if (minPaddleLengthSlider.getValue() > minPaddleLengthSlider.getMaximum()) {
						minPaddleLengthSlider.setValue(minPaddleLengthSlider.getMaximum());
					}
					maxPaddleLengthTextField.setText(String.valueOf(maxPaddleLengthSlider.getValue()));
				}
			}
		};

		nrLevelsSlider.addChangeListener(changeListener);
		nrBlocksSlider.addChangeListener(changeListener);
		minBallSpeedXSlider.addChangeListener(changeListener);
		minBallSpeedYSlider.addChangeListener(changeListener);
		ballSpeedIncreaseFactorSlider.addChangeListener(changeListener);
		minPaddleLengthSlider.addChangeListener(changeListener);
		maxPaddleLengthSlider.addChangeListener(changeListener);

		// key adapter

		keyAdapter = new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent ke) {
				int keyChar = ke.getKeyChar();
				if (keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) {
					if ((JTextField) ke.getSource() == nrLevelsTextField) {
						String typed = nrLevelsTextField.getText();
						nrLevelsSlider.setValue(0);
						if (!typed.matches("^[-\\+]?[\\d]*$")) {
							return;
						}
						nrLevelsSlider.setValue(Integer.parseInt(typed));
					} else if ((JTextField) ke.getSource() == nrBlocksTextField) {
						String typed = nrBlocksTextField.getText();
						nrBlocksSlider.setValue(0);
						if (!typed.matches("^[-\\+]?[\\d]*$")) {
							return;
						}
						nrBlocksSlider.setValue(Integer.parseInt(typed));
					} else if ((JTextField) ke.getSource() == minBallSpeedXTextField) {
						String typed = minBallSpeedXTextField.getText();
						minBallSpeedXSlider.setValue(0);
						if (!typed.matches("^[-\\+]?[\\d]*$")) {
							return;
						}
						minBallSpeedXSlider.setValue(Integer.parseInt(typed));
					} else if ((JTextField) ke.getSource() == minBallSpeedYTextField) {
						String typed = minBallSpeedYTextField.getText();
						minBallSpeedYSlider.setValue(0);
						if (!typed.matches("^[-\\+]?[\\d]*$")) {
							return;
						}
						minBallSpeedYSlider.setValue(Integer.parseInt(typed));
					} else if ((JTextField) ke.getSource() == minPaddleLengthTextField) {
						String typed = minPaddleLengthTextField.getText();
						minPaddleLengthSlider.setValue(0);
						if (!typed.matches("^[-\\+]?[\\d]*$")) {
							return;
						}
						minPaddleLengthSlider.setValue(Integer.parseInt(typed));
					} else if ((JTextField) ke.getSource() == maxPaddleLengthTextField) {
						String typed = maxPaddleLengthTextField.getText();
						maxPaddleLengthSlider.setValue(0);
						if (!typed.matches("^[-\\+]?[\\d]*$")) {
							return;
						}
						maxPaddleLengthSlider.setValue(Integer.parseInt(typed));
						minPaddleLengthSlider.setMaximum(Integer.parseInt(typed));
						Dictionary<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
						labelTable.put(1, new JLabel("1"));
						labelTable.put(maxPaddleLengthSlider.getValue(),
								new JLabel("" + (maxPaddleLengthSlider.getValue())));
						minPaddleLengthSlider.setLabelTable(labelTable);
						if (minPaddleLengthSlider.getValue() > minPaddleLengthSlider.getMaximum()) {
							minPaddleLengthSlider.setValue(minPaddleLengthSlider.getMaximum());
						}
					} else if ((JTextField) ke.getSource() == ballSpeedIncreaseFactorTextField) {
						bsifType = ballSpeedIncreaseFactorTextField.getText();
						String currentValue = ballSpeedIncreaseFactorTextField.getText();
						// ballSpeedIncreaseFactorSlider.setValue(0);
						if (!bsifType.matches("\\d+(\\.\\d+)?")) {
							return;
						}
						Thread thread = new Thread(new Runnable() {

							@Override
							public void run() {
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if (bsifType.equals(currentValue)) {
									ballSpeedIncreaseFactorSlider
											.setValue((int) (Double.parseDouble(currentValue) * 10));
								}

							}
						});
						thread.start();
					}
				} else {
					ke.consume();
				}
			}

		};

		nrLevelsTextField.addKeyListener(keyAdapter);
		nrBlocksTextField.addKeyListener(keyAdapter);
		minBallSpeedXTextField.addKeyListener(keyAdapter);
		minBallSpeedYTextField.addKeyListener(keyAdapter);
		ballSpeedIncreaseFactorTextField.addKeyListener(keyAdapter);
		minPaddleLengthTextField.addKeyListener(keyAdapter);
		maxPaddleLengthTextField.addKeyListener(keyAdapter);

		// listeners for cancel
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelActionPerformed(evt);
			}
		});

		// listeners for apply
		applyButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					applyActionPerformed(evt);
				} catch (InvalidInputException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(layout.createParallelGroup()

				.addComponent(errorMessage).addComponent(gameNameLabel)

				.addComponent(generalSettingLabel)
				.addGroup(layout.createSequentialGroup().addComponent(nrLevelsLabel).addComponent(nrLevelsSlider)
						.addComponent(nrLevelsTextField))
				.addGroup(layout.createSequentialGroup().addComponent(nrBlocksLabel).addComponent(nrBlocksSlider)
						.addComponent(nrBlocksTextField))

				.addComponent(ballSettingLabel)
				.addGroup(layout.createSequentialGroup().addComponent(minBallSpeedXLabel)
						.addComponent(minBallSpeedXSlider).addComponent(minBallSpeedXTextField))
				.addGroup(layout.createSequentialGroup().addComponent(minBallSpeedYLabel)
						.addComponent(minBallSpeedYSlider).addComponent(minBallSpeedYTextField))
				.addGroup(layout.createSequentialGroup().addComponent(ballSpeedIncreaseFactorLabel)
						.addComponent(ballSpeedIncreaseFactorSlider).addComponent(ballSpeedIncreaseFactorTextField))

				.addComponent(paddleSettingLabel)
				.addGroup(layout.createSequentialGroup().addComponent(maxPaddleLengthLabel)
						.addComponent(maxPaddleLengthSlider).addComponent(maxPaddleLengthTextField))
				.addGroup(layout.createSequentialGroup().addComponent(minPaddleLengthLabel)
						.addComponent(minPaddleLengthSlider).addComponent(minPaddleLengthTextField))

				.addGroup(layout.createSequentialGroup().addComponent(cancelButton).addComponent(applyButton)));

		layout.linkSize(SwingConstants.VERTICAL,
				new java.awt.Component[] { gameNameLabel, nrLevelsTextField, nrBlocksTextField, minBallSpeedXTextField,
						minBallSpeedYTextField, ballSpeedIncreaseFactorTextField, maxPaddleLengthTextField,
						minPaddleLengthTextField, cancelButton, applyButton });
		layout.linkSize(SwingConstants.HORIZONTAL,
				new java.awt.Component[] { gameNameLabel, nrLevelsTextField, nrBlocksTextField, minBallSpeedXTextField,
						minBallSpeedYTextField, ballSpeedIncreaseFactorTextField, maxPaddleLengthTextField,
						minPaddleLengthTextField, cancelButton, applyButton });
		layout.linkSize(SwingConstants.HORIZONTAL,
				new java.awt.Component[] { nrLevelsSlider, nrBlocksSlider, minBallSpeedXSlider, minBallSpeedYSlider,
						ballSpeedIncreaseFactorSlider, maxPaddleLengthSlider, minPaddleLengthSlider, cancelButton,
						applyButton });
		layout.setVerticalGroup(layout.createSequentialGroup()

				.addComponent(errorMessage).addComponent(gameNameLabel)

				.addComponent(generalSettingLabel)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(nrLevelsLabel)
						.addComponent(nrLevelsSlider).addComponent(nrLevelsTextField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(nrBlocksLabel)
						.addComponent(nrBlocksSlider).addComponent(nrBlocksTextField))

				.addComponent(ballSettingLabel)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(minBallSpeedXLabel)
						.addComponent(minBallSpeedXSlider).addComponent(minBallSpeedXTextField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(minBallSpeedYLabel)
						.addComponent(minBallSpeedYSlider).addComponent(minBallSpeedYTextField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(ballSpeedIncreaseFactorLabel).addComponent(ballSpeedIncreaseFactorSlider)
						.addComponent(ballSpeedIncreaseFactorTextField))

				.addComponent(paddleSettingLabel)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(maxPaddleLengthLabel)
						.addComponent(maxPaddleLengthSlider).addComponent(maxPaddleLengthTextField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(minPaddleLengthLabel)
						.addComponent(minPaddleLengthSlider).addComponent(minPaddleLengthTextField))

				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(cancelButton)
						.addComponent(applyButton)));
		pack();
	}

	private void refreshData() {

		// error
		errorMessage.setText(error);

		if (error == null || error.length() == 0) {
			// populate page with data
			nrLevelsSlider.setValue(1);
			nrBlocksSlider.setValue(1);
			;
			maxPaddleLengthSlider.setValue(10);
			minPaddleLengthSlider.setValue(10);
			minBallSpeedXSlider.setValue(1);
			minBallSpeedYSlider.setValue(1);
			ballSpeedIncreaseFactorSlider.setValue(1);

			nrLevelsTextField.setText("");
			nrBlocksTextField.setText("");
			maxPaddleLengthTextField.setText("");
			minPaddleLengthTextField.setText("");
			minBallSpeedXTextField.setText("");
			minBallSpeedYTextField.setText("");
			ballSpeedIncreaseFactorTextField.setText("");
		}
		pack();
	}

	private void applyActionPerformed(java.awt.event.ActionEvent evt) throws InvalidInputException {

		// clear error message
		error = null;
		String nrl = nrLevelsTextField.getText();
		String nrb = nrBlocksTextField.getText();
		String mbsx = minBallSpeedXTextField.getText();
		String mbsy = minBallSpeedYTextField.getText();
		String bsif = ballSpeedIncreaseFactorTextField.getText();
		String maxpl = maxPaddleLengthTextField.getText();
		String minpl = minPaddleLengthTextField.getText();
		// call the controller
		try {
			Block223Controller.setGameDetails(Integer.parseInt(nrl), Integer.parseInt(nrb), Integer.parseInt(mbsx),
					Integer.parseInt(mbsy), Double.parseDouble(bsif), Integer.parseInt(maxpl), Integer.parseInt(minpl));
			// String name, int nrLevels, int nrBlocksPerLevel, int minBallSpeedX, int
			// minBallSpeedY,
			// Double ballSpeedIncreaseFactor, int maxPaddleLength, int minPaddleLength

			Block223Application.openGameSettingPage();

			dispose();
		} catch (InvalidInputException | RuntimeException e) {
			if (e.getMessage().indexOf("For") != -1 || e.getMessage().length() == 0 || e.getMessage() == null) {
				error = "All the fields must be filled with valid input";
			} else {
				error = e.getMessage();
			}
		}

		// update visuals
		refreshData();

		try {
			Block223Persistence.save(Block223Application.getBlock223());
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	private void cancelActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			Block223Controller.deleteGame(Block223Application.getCurrentGame().getName());
		} catch (InvalidInputException e) {
			// error = e.getMessage();
		}
		Block223Application.openEditorMenu();
		dispose();
	}

	public void centreWindow() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
		this.setLocation(x, y);
	}
}