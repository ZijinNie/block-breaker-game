package ca.mcgill.ecse223.block.persistence;

import java.awt.List;
import java.util.ArrayList;

import ca.mcgill.ecse223.block.model.*;

public class Block223Persistence {
		
	private static String filename = "data.block223";
	
	public static void save(Block223 block223) { 
		java.util.List<PlayedGame> games = block223.getPlayedGames();
		for (PlayedGame game:games) {
			game.setBounce(null);
		}
		PersistenceObjectStream.serialize(block223); 
	} 
	
	public static Block223 load() {
		PersistenceObjectStream.setFilename(filename);
		Block223 block223= (Block223) PersistenceObjectStream.deserialize(); // model cannot be loaded - create empty block223
		if   (block223== null) 
			{block223= new Block223();} 
		else {
			block223.reinitialize();
		}
		return block223;
		
	}
	
	public static String getFilename() {
		return filename;
	}
	public static void setFilename (String fileName) {
		filename=fileName;
	}
	

}
