package ca.mcgill.ecse223.block.controller;

import java.util.ArrayList;

import java.util.List;

import ca.mcgill.ecse223.block.model.*;
import ca.mcgill.ecse223.block.model.PlayedGame.PlayStatus;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;
import ca.mcgill.ecse223.block.view.Block223PlayModeInterface;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.application.*;
import ca.mcgill.ecse223.block.controller.TOUserMode.Mode;
import javax.sound.sampled.*;
import java.io.File;


public class Block223Controller {

	// ****************************
	// Modifier methods
	// ****************************

	// Editor: Xinran Li 2.26
	public static void createGame(String name) throws InvalidInputException {
		UserRole admin = Block223Application.getCurrentUserRole();
		if (!(admin instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to create a game.");
		}

		Block223 block223 = Block223Application.getBlock223();
		// System.out.println(name + " length: " + name.length());
		if (name == null || name.length() <= 0) {
			throw new InvalidInputException("The name of a game must be specified.");
		}


		Game game = findGame(name);
		if (game != null) {
			throw new InvalidInputException("The name of a game must be unique.");
		}
		Game newGame = new Game(name, 1, (Admin) admin, 1, 1, 1.0, 10, 10, block223);
		Block223Application.setCurrentGame(newGame);
		System.out.println("create game with name "+name);

		try {
			Block223Persistence.save(Block223Application.getBlock223());
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	// author: Glen Xu
	// last edit: Glen Xu Mar 3
	public static void setGameDetails(int nrLevels, int nrBlocksPerLevel, int minBallSpeedX, int minBallSpeedY,
			Double ballSpeedIncreaseFactor, int maxPaddleLength, int minPaddleLength) throws InvalidInputException {
		String error = "";
		if (Block223Application.getCurrentUserRole() instanceof Admin == false) {
			throw new InvalidInputException("Admin privileges are required to define game settings.");
		}
		if (Block223Application.getCurrentGame() == null) {
			throw new InvalidInputException("A game must be selected to define game settings.");
		}
		if (minBallSpeedX == 0 && minBallSpeedY == 0) {
			throw new InvalidInputException("The minimum speed of the ball must be greater than zero.");
		}

		Game game = Block223Application.getCurrentGame();
		if (game != null && Block223Application.getCurrentUserRole().equals(game.getAdmin()) == false) {
			throw new InvalidInputException("Only the admin who created the game can define its game settings.");
		}
		if(game == null) {
			throw new InvalidInputException("A game must be selected to define game settings.");
		}
		if (nrLevels < 1 || nrLevels > 99) {
			throw new InvalidInputException("The number of levels must be between 1 and 99.");
		}

		if (maxPaddleLength <= 0 || maxPaddleLength > 390) {
			throw new InvalidInputException("The maximum length of the paddle must be greater than zero and less than or equal to 390.");
		}

		if (maxPaddleLength < minPaddleLength) {
			throw new InvalidInputException("The minimum length of the paddle cannot be greater than the maximum length of the paddle.");
		}

		if (game != null && nrBlocksPerLevel < game.getBlockAssignments().size()) {
			error = "The maximum number of blocks per level cannot be less than the number of existing blocks in a level.";
		}

		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}

		try {
			game.setNrBlocksPerLevel(nrBlocksPerLevel);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}

		Ball ball = game.getBall();
		try {
			ball.setMinBallSpeedX(minBallSpeedX);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		try {
			ball.setMinBallSpeedY(minBallSpeedY);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		try {
			ball.setBallSpeedIncreaseFactor(ballSpeedIncreaseFactor);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}

		Paddle paddle = game.getPaddle();
		if (maxPaddleLength < 0 || maxPaddleLength > 390) {
			throw new InvalidInputException(
					"The maximum length of the paddle must be greater than zero and less than or equal to 390.");
		}

		try {
			paddle.setMaxPaddleLength(maxPaddleLength);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		try {
			paddle.setMinPaddleLength(minPaddleLength);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}

		List<Level> levels = game.getLevels();

		int size = levels.size();

		while (nrLevels > size) {
			game.addLevel();
			size = levels.size();
		}

		while (nrLevels < size) {
			Level level = game.getLevel(size - 1);
			level.delete();
			size = levels.size();
		}

		try {
			Block223Persistence.save(Block223Application.getBlock223());
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		// Block223Application.setCurrentGame(null);
	}

	// Editor: Xinran Li
	public static void deleteGame(String name) throws InvalidInputException {
		UserRole admin = Block223Application.getCurrentUserRole();

		if (!(admin instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to delete a game.");
		}

		Game game = findGame(name);

		if (game != null) {
			if (!game.getAdmin().equals(admin)) {
				throw new InvalidInputException("Only the admin who created the game can delete the game.");
			}
			if(game.getPublished()==true) {
				throw new InvalidInputException( "A published game cannot be deleted.");
			}
			Block223 block223 = Block223Application.getBlock223();
			System.out.println("delete game with name "+name);
			game.delete();

			Block223Persistence.save(block223);
		}
	}

	public static void selectGame(String name) throws InvalidInputException {

		UserRole admin = Block223Application.getCurrentUserRole();
		Game game = findGame(name);

		if (game == null) {
			throw new InvalidInputException("A game with name " + name + " does not exist.");
		}

		if (!(admin instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to select a game.");
		}

		if (!game.getAdmin().equals(admin)) {
			throw new InvalidInputException("Only the admin who created the game can select the game.");
		}

		if(game.getPublished() == true) {
			throw new InvalidInputException( "A published game cannot be changed.");
		}
		Block223Application.setCurrentGame(game);

	}

	// author: Glen Xu
	// last edit: Glen Xu Mar 3
	public static void updateGame(String name, int nrLevels, int nrBlocksPerLevel, int minBallSpeedX, int minBallSpeedY,
			Double ballSpeedIncreaseFactor, int maxPaddleLength, int minPaddleLength) throws InvalidInputException {
		String error = "";
		Game game = Block223Application.getCurrentGame();

		if (Block223Application.getCurrentUserRole() instanceof Admin == false) {
			throw new InvalidInputException("Admin privileges are required to define game settings.");
		}
		if (Block223Application.getCurrentGame() == null) {
			throw new InvalidInputException("A game must be selected to define game settings.");
		}
		if (Block223Application.getCurrentUserRole().equals(game.getAdmin()) == false) {
			throw new InvalidInputException("Only the admin who created the game can define its game settings.");
		}
		if(game.getPublished()==true) {
			throw new InvalidInputException("Only unpublished game can be modified.");
		}

		if (name == null || name.isEmpty()) {
			throw new InvalidInputException("The name of a game must be specified.");
		}
		Game tryFindGame = findGame(name);
		if (tryFindGame != null && !tryFindGame.equals(game)) {
			System.out.println("Name not unique");

			throw new InvalidInputException("The name of a game must be unique.");

		}
		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}


		String currentName = game.getName();
		try {
			if (currentName != name) {
				game.setName(name);
			}
		} catch (RuntimeException e) {
			error = e.getMessage();
			if (error.equals("Cannot create due to duplicate name")) {
				error = "The name of a game must be unique.";
				System.out.println("Name not unique");
			}
			throw new InvalidInputException(error);
		}
		Block223Controller.setGameDetails(nrLevels, nrBlocksPerLevel, minBallSpeedX, minBallSpeedY,
				ballSpeedIncreaseFactor, maxPaddleLength, minPaddleLength);

		try {
			Block223Persistence.save(Block223Application.getBlock223());
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		// Block223Application.setCurrentGame(null);
	}

	// author Zijin Nie
	// last edit 2.26 by Zijin Nie
	public static void addBlock(int red, int green, int blue, int points) throws InvalidInputException {
		String error = "";

		UserRole admin = Block223Application.getCurrentUserRole();
		if (!(admin instanceof Admin)) {
			error = error + "Admin privileges are required to add a block.";
		}

		Game game = Block223Application.getCurrentGame();

		if (game == null) {
			throw new InvalidInputException("A game must be selected to add a block.");
		}

		if (!game.getAdmin().equals(admin)) {
			error = error + "Only the admin who created the game can add a block.";
		}

		if(game.getPublished()==true) {
			error= error+ "Only unpublished game can be modified.";
		}

		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}

		for (Block block : game.getBlocks()) {
			if ((block.getRed() == red) && (block.getGreen() == green) && (block.getBlue() == blue)) {
				throw new InvalidInputException("A block with the same color already exists for the game.");
			}
		}

		try {
			new Block(red, green, blue, points, game);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}

	}

	// author Yuankang Wei
	// last edit 3.4 By Wei
	public static void deleteBlock(int id) throws InvalidInputException {
		UserRole admin = Block223Application.getCurrentUserRole();

		if (!(admin instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to delete a block.");
		}

		Game game = Block223Application.getCurrentGame();

		if (game == null) {
			throw new InvalidInputException("A game must be selected to delete a block.");
		}

		if (!game.getAdmin().equals(admin)) {
			throw new InvalidInputException("Only the admin who created the game can delete a block.");
		}

		if(game.getPublished()==true) {
			throw new InvalidInputException( "Only unpublished game can be modified.");
		}

		Block block = game.findBlock(id);
		if (!(block == null)) {
			block.delete();
		}

	}

	public static void updateBlock(int id, int red, int green, int blue, int points) throws InvalidInputException {
		UserRole admin = Block223Application.getCurrentUserRole();
		if (!(admin instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to update a block.");
		}

		Game game = Block223Application.getCurrentGame();

		if (game == null) {
			throw new InvalidInputException("A game must be selected to update a block.");
		}

		if (!game.getAdmin().equals(admin)) {
			throw new InvalidInputException("Only the admin who created the game can update a block.");
		}
		if(game.getPublished()==true) {
			throw new InvalidInputException("Only unpublished game can be modified.");
		}
		for (Block block : game.getBlocks()) {
			if ((block.getRed() == red) && (block.getGreen() == green) && (block.getBlue() == blue)
					&& block.getId() != id) {
				throw new InvalidInputException("A block with the same color already exists for the game.");
			}
		}


		Block block = game.findBlock(id);

		if (block == null) {
			throw new InvalidInputException("The block does not exist.");
		}

		try {
			block.setRed(red);
			block.setGreen(green);
			block.setBlue(blue);
			block.setPoints(points);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}

	}

	// author: Erdong Luo
	// last edit 2.26 by Erdong Luo
	public static void positionBlock(int id, int level, int gridHorizontalPosition, int gridVerticalPosition)
			throws InvalidInputException {
		UserRole admin = Block223Application.getCurrentUserRole();
		if (!(admin instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to position a block.");
		}
		Game game = Block223Application.getCurrentGame();
		if (game == null) {
			throw new InvalidInputException("A game must be selected to position a block.");
		}
		if (!game.getAdmin().equals(admin)) {
			throw new InvalidInputException("Only the admin who created the game can position a block.");
		}

		if(game.getPublished()==true) {
			throw new InvalidInputException( "Only unpublished game can be modified.");
		}
		Level alevel;
		if(level>game.getLevels().size()) {
			throw new InvalidInputException("Level "+level+" does not exist for the game.");
		}
		if(level<=0) {
			throw new InvalidInputException("Level "+level+" does not exist for the game.");
		}
		try {
			alevel = game.getLevel(level-1);

		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		int nrBlocksPerLevel = game.getNrBlocksPerLevel();
		if (alevel.numberOfBlockAssignments() == nrBlocksPerLevel) {
			throw new InvalidInputException("The number of blocks has reached the maximum number (" + nrBlocksPerLevel
					+ ") allowed for this game.");
		}
		List<BlockAssignment> blockassignments = alevel.getBlockAssignments();
		for (BlockAssignment assignment : blockassignments) {
			if ((assignment.getGridHorizontalPosition() == gridHorizontalPosition)
					&& (assignment.getGridVerticalPosition() == gridVerticalPosition)) {
				throw new InvalidInputException("A block already exists at location " + gridHorizontalPosition + "/"
						+ gridVerticalPosition + ".");
			}
		}
		Block block = game.findBlock(id);
		if (block == null) {
			throw new InvalidInputException("The block does not exist.");
		}
		try {
			new BlockAssignment(gridHorizontalPosition, gridVerticalPosition, alevel, block, game);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	// author: Erdong Luo
	// last edit 2.26 by Erdong Luo
	public static void moveBlock(int level, int oldGridHorizontalPosition, int oldGridVerticalPosition,
			int newGridHorizontalPosition, int newGridVerticalPosition) throws InvalidInputException {

		UserRole admin = Block223Application.getCurrentUserRole();
		if (!(admin instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to move a block.");
		}
		Game game = Block223Application.getCurrentGame();
		if (game == null) {
			throw new InvalidInputException("A game must be selected to move a block.");
		}
		if (!game.getAdmin().equals(admin)) {
			throw new InvalidInputException("Only the admin who created the game can move a block.");
		}
		if(game.getPublished()==true) {
			throw new InvalidInputException( "Only unpublished game can be modified.");
		}
		Level alevel;
		if(newGridVerticalPosition<=0|| newGridVerticalPosition>15) {
			throw new InvalidInputException( "The vertical position must be between 1 and 15.");
		}
		if(newGridHorizontalPosition<=0|| newGridHorizontalPosition>15) {
			throw new InvalidInputException( "The horizontal position must be between 1 and 15.");
		}
		if(level>game.getLevels().size()) {
			throw new InvalidInputException("Level "+level+" does not exist for the game.");
		}
		if(level<=0) {
			throw new InvalidInputException("Level "+level+" does not exist for the game.");
		}
		try {
			alevel = game.getLevel(level-1);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}

		BlockAssignment assignment = alevel.findBlockAssignment(oldGridHorizontalPosition, oldGridVerticalPosition);
		if (assignment == null) {
			throw new InvalidInputException("A block does not exist at location " + oldGridHorizontalPosition + "/"
					+ oldGridVerticalPosition + ".");
		}

		List<BlockAssignment> blockassignments = alevel.getBlockAssignments();

		for (BlockAssignment oldAssignment : blockassignments) {
			if ((oldAssignment.getGridHorizontalPosition() == newGridHorizontalPosition)
					&& (oldAssignment.getGridVerticalPosition() == newGridVerticalPosition)) {
				throw new InvalidInputException("A block already exists at location " + newGridHorizontalPosition + "/"
						+ newGridVerticalPosition + ".");

			}
		}
		try {
			assignment.setGridHorizontalPosition(newGridHorizontalPosition);
			assignment.setGridVerticalPosition(newGridVerticalPosition);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}

	}

	// author Yuankang Wei
	// last edit: 3.4 Yuankang Wei

	public static void removeBlock(int level, int gridHorizontalPosition, int gridVerticalPosition)
			throws InvalidInputException {
		UserRole admin = Block223Application.getCurrentUserRole();

		if (!(admin instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to remove a block.");
		}

		Game game = Block223Application.getCurrentGame();

		if (game == null) {
			throw new InvalidInputException("A game must be selected to remove a block.");
		}

		if (!game.getAdmin().equals(admin)) {
			throw new InvalidInputException("Only the admin who created the game can remove a block.");
		}
		if(game.getPublished()==true) {
			throw new InvalidInputException( "Only unpublished game can be modified.");
		}
		Level alevel = game.getLevel(level-1);

		if (alevel == null) {
			throw new InvalidInputException("cannot find the level based on input");
		}

		BlockAssignment assignment = alevel.findBlockAssignment(gridHorizontalPosition, gridVerticalPosition);
		if (!(assignment == null)) {
			//System.out.println("remove!");
			assignment.delete();
		}

	}

	// author Han Zhou
	// last edit: 2.15 By Han Zhou
	public static void saveGame() throws InvalidInputException {
		UserRole curRole = Block223Application.getCurrentUserRole();
		Game curGame = Block223Application.getCurrentGame();
		//PlayedGame curpgame = Block223Application.getCurrentPlayableGame();
		if (!(curRole instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to save a game.");
		}
		if (curGame == null) {
			throw new InvalidInputException("A game must be selected to save it.");
		} else {
			if (curRole != curGame.getAdmin()) {
				throw new InvalidInputException("Only the admin who created the game can save it.");
			}
		}

		//curpgame.setBounce(null)
		Block223 block223 = Block223Application.getBlock223();
		try {
			Block223Persistence.save(block223);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	// author Han Zhou
	// last edit: 2.15 By Han Zhou
	public static void register(String username, String playerPassword, String adminPassword)
			throws InvalidInputException {
		String error = "";
		UserRole curRole = Block223Application.getCurrentUserRole();
		if (curRole != null) {
			throw new InvalidInputException("Cannot register a new user while a user is logged in.");
		}
		if (playerPassword == adminPassword) {
			throw new InvalidInputException("The passwords have to be different.");
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}

		Block223 block223 = Block223Application.getBlock223();
		User user = null;
		Player player = null;
		Admin admin = null;
		try {
			player = new Player(playerPassword, block223);
			// System.out.println("suc create player");
			user = new User(username, block223, player);
			// System.out.println("suc create user");
			if (!(adminPassword == null) && !(adminPassword.equals(""))) {
				admin = new Admin(adminPassword, block223);
				user.addRole(admin);
				// System.out.println("suc create admin");
			}
		} catch (RuntimeException e) {
			error = e.getMessage();
			if (error.equals("player password is null or empty")) {
				error = "The player password needs to be specified.";
			} else if (error.equals("Cannot create due to duplicate username")) {
				error = "The username has already been taken.";
			} else if (error.equals("username is null or empty")) {
				error = "The username must be specified.";
			}
			if (player != null) {
				player.delete();
				// System.out.println("suc delete player");
			}
			if (user != null) {
				user.delete();
				// System.out.println("suc delete user");
			}
			if (admin != null) {
				admin.delete();
				// System.out.println("suc delete admin");
			}
			throw new InvalidInputException(error);
		}
		try {
			Block223Persistence.save(block223);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	// author Han Zhou
	// last edit: 2.15 By Han Zhou
	public static void login(String username, String password) throws InvalidInputException {
		String error = "";
		boolean login = false;
		Block223Application.resetBlock223();
		User user = User.getWithUsername(username);
		if (Block223Application.getCurrentUserRole() != null) {
			error = error + "Cannot login a user while a user is already logged in.";
		}
		if (user == null) {
			error = error + "The username and password do not match.";
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}
		List<UserRole> roles = user.getRoles();
		for (UserRole role : roles) {
			String rolePassword = role.getPassword();
			if (rolePassword.equals(password)) {
				Block223Application.setCurrentUserRole(role);
				login = true;
			}
		}
		if (login == false) {
			throw new InvalidInputException("The username and password do not match.");
		}
	}

	// author Han Zhou
	// last edit: 2.15 By Han Zhou
	public static void logout() {
		Block223Application.setCurrentUserRole(null);
	}

	// ****************************
	// Query methods
	// ****************************

	// author Zijin Nie
	// last edit: 2.15 By Zijin Nie
	public static List<TOGame> getDesignableGames() throws InvalidInputException {
		Block223 block223 = Block223Application.getBlock223();

		UserRole admin = Block223Application.getCurrentUserRole();

		if (!(admin instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to access game information.");
		}

		List<TOGame> result = new ArrayList<TOGame>();

		List<Game> games = block223.getGames();

		for (Game game : games) {
			Admin gameAdmin = game.getAdmin();
			if (gameAdmin.equals(admin) && !game.isPublished()) {
				System.out.println("find a game with "+game.getName());
				TOGame to = new TOGame(game.getName(), game.getLevels().size(), game.getNrBlocksPerLevel(),
						game.getBall().getMinBallSpeedX(), game.getBall().getMinBallSpeedY(),
						game.getBall().getBallSpeedIncreaseFactor(), game.getPaddle().getMaxPaddleLength(),
						game.getPaddle().getMinPaddleLength());
				result.add(to);
			}
		}

		return result;

	}

	// author: Glen Xu
	// last edit: Glen Xu Apr 26
	public static TOGame getCurrentDesignableGame() throws InvalidInputException {
		String error = "";
		if (Block223Application.getCurrentUserRole() instanceof Admin == false) {
			throw new InvalidInputException("Admin privileges are required to access game information.");
		}
		if (Block223Application.getCurrentGame() == null) {
			throw new InvalidInputException("A game must be selected to access its information.");
		}

		Game game = Block223Application.getCurrentGame();
		if (Block223Application.getCurrentUserRole().equals(game.getAdmin()) == false) {
			error = "Only the admin who created the game can access its information.";
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}

		TOGame to = new TOGame(game.getName(), game.getLevels().size(), game.getNrBlocksPerLevel(),
				game.getBall().getMinBallSpeedX(), game.getBall().getMinBallSpeedY(),
				game.getBall().getBallSpeedIncreaseFactor(), game.getPaddle().getMaxPaddleLength(),
				game.getPaddle().getMinPaddleLength());

		return to;
	}

	public static List<TOBlock> getBlocksOfCurrentDesignableGame() throws InvalidInputException {
		UserRole admin = Block223Application.getCurrentUserRole();

		if (!(admin instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to access game information.");
		}

		Game game = Block223Application.getCurrentGame();

		if (game == null) {
			throw new InvalidInputException("A game must be selected to access its information.");
		}

		if (!game.getAdmin().equals(admin)) {
			throw new InvalidInputException("Only the admin who created the game can access its information.");
		}

		List<TOBlock> result = new ArrayList<TOBlock>();

		List<Block> blocks = game.getBlocks();

		for (Block block : blocks) {
			TOBlock to = new TOBlock(block.getId(), block.getRed(), block.getGreen(), block.getBlue(),
					block.getPoints());
			result.add(to);
		}

		return result;
	}

	public static TOBlock getBlockOfCurrentDesignableGame(int id) throws InvalidInputException {

		UserRole admin = Block223Application.getCurrentUserRole();
		if (!(admin instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to access game information.");
		}

		Game game = Block223Application.getCurrentGame();

		if (game == null) {
			throw new InvalidInputException("A game must be selected to access its information.");
		}

		if (!game.getAdmin().equals(admin)) {
			throw new InvalidInputException("Only the admin who created the game can access its information.");
		}

		Block block = game.findBlock(id);

		if (block == null) {
			throw new InvalidInputException("The block does not exist.");
		}

		TOBlock to = new TOBlock(block.getId(), block.getRed(), block.getGreen(), block.getBlue(), block.getPoints());

		return to;
	}

	// author: Erdong Luo
	// last edit 2.26 by Erdong Luo
	public static List<TOGridCell> getBlocksAtLevelOfCurrentDesignableGame(int level) throws InvalidInputException {
		UserRole admin = Block223Application.getCurrentUserRole();

		if (!(admin instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to access game information.");
		}
		Game game = Block223Application.getCurrentGame();
		if (game == null) {
			throw new InvalidInputException("A game must be selected to access its information.");
		}
		if (!game.getAdmin().equals(admin)) {
			throw new InvalidInputException("Only the admin who created the game can access its information.");
		}

		List<TOGridCell> result = new ArrayList<TOGridCell>();
		if(level>game.getLevels().size()) {
			throw new InvalidInputException("Level "+level+" does not exist for the game.");
		}
		if(level<=0) {
			throw new InvalidInputException("Level "+level+" does not exist for the game.");
		}

		Level alevel;
		try {
			alevel = game.getLevel(level-1);

		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		List<BlockAssignment> assignments = alevel.getBlockAssignments();

		for (BlockAssignment assignment : assignments) {
			TOGridCell to = new TOGridCell(assignment.getGridHorizontalPosition(), assignment.getGridVerticalPosition(),
					assignment.getBlock().getId(), assignment.getBlock().getRed(), assignment.getBlock().getGreen(),
					assignment.getBlock().getBlue(), assignment.getBlock().getPoints());
			result.add(to);
		}
		return result;

	}

	// author Han Zhou
	// last edit: 2.15 By Han Zhou
	public static TOUserMode getUserMode() {
		UserRole userrole = Block223Application.getCurrentUserRole();
		if (userrole == null) {
			return new TOUserMode(Mode.None);
		} else if (userrole instanceof Player) {
			return new TOUserMode(Mode.Play);
		} else if (userrole instanceof Admin) {
			return new TOUserMode(Mode.Design);
		}
		return null;
	}

	// author: zijin nie
	// last edit 2.26 by zijin nie

	public static Game findGame(String gameName) {
		Block223 block223 = Block223Application.getBlock223();
		List<Game> games = block223.getGames();
		Game foundGame = null;
		for (Game game : games) {
			if (game.getName().equals(gameName)) {
				foundGame = game;
				break;
			}
		}
		return foundGame;
	}

	// Editor: 2019-3-1 by Xinran Li
	public static ArrayList<String> getGameNames() {
		Block223 block223 = Block223Application.getBlock223();
		List<Game> games = block223.getGames();
		ArrayList<String> gamenames = new ArrayList<String>();
		UserRole user = Block223Application.getCurrentUserRole();
		for (Game game : games) {
			if (game.getAdmin().equals(user)) {
				gamenames.add(game.getName());
			}
		}
		return gamenames;
	}

	// Editor : Han 03/02
	public static ArrayList<Integer> getLevels() {
		Game game = Block223Application.getCurrentGame();
		List<Level> levels = game.getLevels();
		int count = 1;
		ArrayList<Integer> levelnums = new ArrayList<Integer>();
		for (Level lv : levels) {
			levelnums.add(count);
			count++;
		}
		return levelnums;
	}

	//PlayMode features
	public static void selectPlayableGame(String name, int id) throws InvalidInputException  {
		Game game= findGame(name);
		Block223 block223=Block223Application.getBlock223();
		PlayedGame pgame = null;
		if(game !=null) {
			UserRole player=Block223Application.getCurrentUserRole();
			if(!(player instanceof Player)){
				throw new InvalidInputException("Player privileges are required to play a game.");
			}
			String userName= User.findUsername(player);
			pgame=new PlayedGame(userName,game,block223);

			pgame.setPlayer((Player)player);
		}else {
			System.out.println("selectPlayableGame id is "+id);
			pgame= block223.findPlayableGame(id);
			if(pgame==null) {
				throw new InvalidInputException("The game does not exist.");
			}
			if(!(Block223Application.getCurrentUserRole().equals(pgame.getPlayer()))) {
				throw new InvalidInputException("Only the player that started a game can continue the game.");
			}
			
		}
		Block223Application.setCurrentPlayableGame(pgame);
	}

	public static void startGame(Block223PlayModeInterface ui) throws InvalidInputException {
		if(Block223Application.getCurrentUserRole()==null) {
			throw new InvalidInputException("Player privileges are required to play a game.");
		}
		if(Block223Application.getCurrentPlayableGame()==null) {
			throw new InvalidInputException("A game must be selected to play it.");
		}
		if((Block223Application.getCurrentUserRole() instanceof Admin)
				&&(Block223Application.getCurrentPlayableGame().getPlayer()!=null)){
			throw new InvalidInputException("Player privileges are required to play a game.");
		}
		if((Block223Application.getCurrentUserRole() instanceof Admin)
				&&(!(Block223Application.getCurrentPlayableGame().getGame().getAdmin().equals(Block223Application.getCurrentUserRole())))){
			throw new InvalidInputException("Only the admin of a game can test the game.");
				}
		if((Block223Application.getCurrentUserRole() instanceof Player)
				&&(Block223Application.getCurrentPlayableGame().getPlayer()==null)){
			throw new InvalidInputException("Admin privileges are required to test a game.");
		}


		PlayedGame game= Block223Application.getCurrentPlayableGame();
		game.play();
		ui.takeInputs();

		while(game.getPlayStatus()==PlayStatus.Moving) {
			String userInputs=ui.takeInputs();
			
			updatePaddlePosition(userInputs);
			game.move();
			if(userInputs.contains(" ")) {
				game.pause();
				//String inputBeforeSpace = userInputs.substring(0, (userInputs.indexOf(" ")));
				//System.out.println(" Input received by CONTROLLER: " + inputBeforeSpace + " then paused");
				
			}
			ui.refresh();
			//System.out.println(" Input received by CONTROLLER: " + userInputs);
			try {
				Thread.sleep(PlayedGame.INITIAL_WAIT_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if(game.getPlayStatus()==PlayStatus.GameOver) {
			//Block223Application.setCurrentPlayableGame(null);
			ui.endGame(game.getLives(), getHallOfFameWithMostRecentEntry(1).getEntry(0));
		}
		if(game.getPlayer()!=null) {
			Block223 block223= Block223Application.getBlock223();
			Block223Persistence.save(block223);
		}

	}

	private static void updatePaddlePosition(String userInputs) {
		PlayedGame game = Block223Application.getCurrentPlayableGame();
		int move=0;
		for(int i =0; i<userInputs.length();i++) {
			if (userInputs.charAt(i)=='l') {
				move+=PlayedGame.PADDLE_MOVE_LEFT;
			}else if (userInputs.charAt(i)=='r') {
				move+=PlayedGame.PADDLE_MOVE_RIGHT;
			}
		}
			
		double px = game.getCurrentPaddleX();
		double npx = px+move;
		//System.out.println("Paddle position after move is "+npx);
		if ( npx>=400-game.getCurrentPaddleLength()) {
			game.setCurrentPaddleX(400-game.getCurrentPaddleLength());
		}else if(npx<=0 ) {
			game.setCurrentPaddleX(0);
		}else {
			game.setCurrentPaddleX(npx);
		}
	}
	public static void testGame(Block223PlayModeInterface ui) throws InvalidInputException {
		Game game = Block223Application.getCurrentGame();

		if (game == null) {
			throw new InvalidInputException("A game must be selected to test it.");
		}

		UserRole admin = Block223Application.getCurrentUserRole();

		if (!(admin instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to test a game.");
		}
		
		if (game.isPublished()==true) {
			throw new InvalidInputException("You have just published the game.");
		}

		if (admin != game.getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can test it.");
		}

		String username = User.findUsername(admin);
		Block223 block223 = Block223Application.getBlock223();
		PlayedGame pgame = new PlayedGame(username, game, block223);
		pgame.setPlayer(null);
		Block223Application.setCurrentPlayableGame(pgame);
		startGame(ui);
	}

	public static void publishGame () throws InvalidInputException {
		UserRole role = Block223Application.getCurrentUserRole();
		if (!(role instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to publish a game.");
		}

		Game game = Block223Application.getCurrentGame();

		if (game == null) {
			throw new InvalidInputException("A game must be selected to publish it.");
		}

		if (role != game.getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can publish it.");
		}

		if (game.getBlocks().size()<1) {
			throw new InvalidInputException("At least one block must be defined for a game to be published.");
		}
		game.setPublished(true);
		System.out.println("publish game");
	}

	// play mode query method

	public static List<TOPlayableGame> getPlayableGames() throws InvalidInputException {
		UserRole player=Block223Application.getCurrentUserRole();
		if(player==null || !(player instanceof Player)) {
			throw new InvalidInputException("Player privileges are required to play a game. ");
		}

		Block223 block223=Block223Application.getBlock223();

		List<TOPlayableGame> result= new ArrayList<TOPlayableGame>();

		List<Game> games=block223.getGames();

		TOPlayableGame to;
		for(Game game:games) {
			Boolean published = game.isPublished();
			if(published) {
				to = new TOPlayableGame(game.getName(),-1,0);
				result.add(to);
			}
		}

		Player aPlayer=(Player) player;

		List<PlayedGame> pgames;
		pgames= aPlayer.getPlayedGames();

		for(PlayedGame pgame: pgames) {
			to=new TOPlayableGame(pgame.getGame().getName(),pgame.getId(),pgame.getCurrentLevel());
			result.add(to);
		}
		return result;
	}

	public static TOCurrentlyPlayedGame getCurrentPlayableGame() throws InvalidInputException {
		UserRole player=Block223Application.getCurrentUserRole();
		if(player==null ) {
			throw new InvalidInputException("Player privileges are required to play a game.");
		}

		PlayedGame pgame=Block223Application.getCurrentPlayableGame();

		if(pgame==null) {
			throw new InvalidInputException("A game must be selected to play it.");
		}
		
		if(player instanceof Player && (pgame.getPlayer()==null)) {
			throw new InvalidInputException("Admin privileges are required to test a game.");
		}
		
		if(player instanceof Admin && (!pgame.getGame().getAdmin().equals(player))) {
			throw new InvalidInputException("Only the admin of a game can test the game.");
		}
		if(player instanceof Admin && pgame.getPlayer()!=null) {
			throw new InvalidInputException("Player privileges are required to play a game.");
		}
		Boolean paused = false;
		if(pgame.getPlayStatus()==PlayStatus.Ready || pgame.getPlayStatus() == PlayStatus.Paused){
			paused=true;
		}

		TOCurrentlyPlayedGame result=new TOCurrentlyPlayedGame(pgame.getGame().getName(),paused,pgame.getScore(),pgame.getLives(),
																pgame.getCurrentLevel(),pgame.getPlayername(),pgame.getCurrentBallX(),
																pgame.getCurrentBallY(), pgame.getCurrentPaddleLength(),pgame.getCurrentPaddleX());

		List<PlayedBlockAssignment> blocks= pgame.getBlocks();

		for(PlayedBlockAssignment pblock: blocks) {
			Block b= pblock.getBlock();
			new TOCurrentBlock(b.getRed(),b.getGreen(),b.getBlue(),b.getPoints(),pblock.getX(),pblock.getY(),result);

		}

		return result;
	}

	public static TOHallOfFame getHallOfFame(int start, int end) throws InvalidInputException {
		UserRole player=Block223Application.getCurrentUserRole();
		if(player==null || !(player instanceof Player)) {
			throw new InvalidInputException("Player privileges are required to access a game's hall of fame.");
		}


		PlayedGame pgame=Block223Application.getCurrentPlayableGame();

		if(pgame==null) {
			throw new InvalidInputException("A game must be selected to view its hall of fame.");
		}

		Game game=pgame.getGame();

		TOHallOfFame result=new TOHallOfFame(game.getName());

		if (start < 1 ) start=1;
		if (end > game.numberOfHallOfFameEntries()) end= game.numberOfHallOfFameEntries();

		start=game.numberOfHallOfFameEntries()-start;
		end=game.numberOfHallOfFameEntries()-end;

		for(int index=start ; index>= end ; index--) {
			new TOHallOfFameEntry(index+1,game.getHallOfFameEntry(index).getPlayername(),
														game.getHallOfFameEntry(index).getScore(),result);
			System.out.println("Successfully create with index "+index);
		}

		return result;
	}

	public static TOHallOfFame getHallOfFameWithMostRecentEntry(int numberOfEntries) throws InvalidInputException {
		UserRole player=Block223Application.getCurrentUserRole();
		if(player==null || !(player instanceof Player)) {
			throw new InvalidInputException("Player privileges are required to access a game's hall of fame.");
		}


		PlayedGame pgame=Block223Application.getCurrentPlayableGame();

		if(pgame==null) {
			throw new InvalidInputException("A game must be selected to view its hall of fame.");
		}

		Game game=pgame.getGame();

		TOHallOfFame result=new TOHallOfFame(game.getName());

		HallOfFameEntry mostRecent= game.getMostRecentEntry();

		int indexR = 0;
		if(mostRecent!=null) {
			indexR=game.indexOfHallOfFameEntry(mostRecent);
		}

		int start = indexR + numberOfEntries/2;

		if(start > game.numberOfHallOfFameEntries()-1) {
			start=game.numberOfHallOfFameEntries()-1;
		}
		int end=start-numberOfEntries+1;

		if(end<0) {
			end=0;
		}


		for(int index=start ; index>= end ; index--) {
			new TOHallOfFameEntry(index+1,game.getHallOfFameEntry(index).getPlayername(),
								game.getHallOfFameEntry(index).getScore(),result);
		}

		return result;
	}

	public static void music() {
		File musicFile =new File( "src/music.wav");
		try {
			Clip clip=AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(musicFile));
			clip.start();
		}catch (Exception e) {

		}
	}
	public static void backGroundMusic() {
		File musicFile =new File( "src/Background.wav");
		try {
			Clip clip=AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(musicFile));
			clip.start();
		}catch (Exception e) {

		}
	}
	
	public static void hitBlockMusic() {
		File musicFile =new File( "src/hit.wav");
		try {
			Clip clip=AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(musicFile));
			clip.start();
		}catch (Exception e) {

		}
	}
	
	
	public static void pause() {
		PlayedGame game= Block223Application.getCurrentPlayableGame();
		game.pause();
	}
	
	public static void deletePlayedGame() {
		Block223Application.getCurrentPlayableGame().delete();
	}
}
