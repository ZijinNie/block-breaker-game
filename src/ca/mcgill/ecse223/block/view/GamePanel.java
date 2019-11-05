package ca.mcgill.ecse223.block.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.JPanel;


import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOCurrentBlock;
import ca.mcgill.ecse223.block.controller.TOCurrentlyPlayedGame;

public class GamePanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5925989867802016376L;

	public GamePanel(Rectangle r) {
		this.setBounds(r);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.white);
		//System.out.println("paint!");
		try {
			TOCurrentlyPlayedGame game = Block223Controller.getCurrentPlayableGame();
			List<TOCurrentBlock> blocks = game.getBlocks();
			for (TOCurrentBlock block:blocks) {
				int x = block.getX();
				int y = block.getY();
				g.setColor(new Color(block.getRed(), block.getGreen(), block.getBlue()));
				g.fillRect(x, y, 20, 20);
			}
			int pdl = (int)game.getCurrentPaddleLength();
			int pdx = (int)game.getCurrentPaddleX();
			//System.out.println("Paiting paddle x "+ pdx);
			g.setColor(Color.black);
			g.fillRect(pdx, 354, pdl, 10);
			int bx = (int)game.getCurrentBallX();
			int by = (int)game.getCurrentBallY();
			g.setColor(Color.cyan);
			g.fillOval(bx-5, by-5, 10, 10);
		} catch (InvalidInputException e) {
			System.out.println("error in printing blocks");
			e.printStackTrace();
		}
		
	}
	
	public void refresh() {
		this.repaint();
	}

	
}
