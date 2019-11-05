/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;
import java.io.Serializable;
import java.lang.Math;
import java.awt.geom.*;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.model.BouncePoint.BounceDirection;
import java.util.*;

/**
 * iter4 PlayMode features
 */
// line 114 "../../../../../Block223Persistence.ump"
// line 11 "../../../../../Block223PlayMode.ump"
// line 1 "../../../../../Block223States.ump"
public class PlayedGame implements Serializable
{

  //------------------------
  // STATIC VARIABLES
  //------------------------


  /**
   * at design time, the initial wait time may be adjusted as seen fit
   */
  public static final int INITIAL_WAIT_TIME = 40;
  private static int nextId = 1;
  public static final int NR_LIVES = 3;

  /**
   * the PlayedBall and PlayedPaddle are not in a separate class to avoid the bug in Umple that occurred for the second constructor of Game
   * no direct link to Ball, because the ball can be found by navigating to Game and then Ball
   */
  public static final int BALL_INITIAL_X = Game.PLAY_AREA_SIDE / 2;
  public static final int BALL_INITIAL_Y = Game.PLAY_AREA_SIDE / 2;

  /**
   * no direct link to Paddle, because the paddle can be found by navigating to Game and then Paddle
   * pixels moved when right arrow key is pressed
   */
  public static final int PADDLE_MOVE_RIGHT = 5;

  /**
   * pixels moved when left arrow key is pressed
   */
  public static final int PADDLE_MOVE_LEFT = -5;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PlayedGame Attributes
  private int score;
  private int lives;
  private int currentLevel;
  private double waitTime;
  private String playername;
  private double ballDirectionX;
  private double ballDirectionY;
  private double currentBallX;
  private double currentBallY;
  private double currentPaddleLength;
  private double currentPaddleX;
  private double currentPaddleY;

  //Autounique Attributes
  private int id;

  //PlayedGame State Machines
  public enum PlayStatus { Ready, Moving, Paused, GameOver }
  private PlayStatus playStatus;

  //PlayedGame Associations
  private Player player;
  private Game game;
  private List<PlayedBlockAssignment> blocks;
  private BouncePoint bounce;
  private Block223 block223;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PlayedGame(String aPlayername, Game aGame, Block223 aBlock223)
  {
    // line 49 "../../../../../Block223PlayMode.ump"
    boolean didAddGameResult = setGame(aGame);
          if (!didAddGameResult)
          {
             throw new RuntimeException("Unable to create playedGame due to game");
          }
    // END OF UMPLE BEFORE INJECTION
    score = 0;
    lives = NR_LIVES;
    currentLevel = 1;
    waitTime = INITIAL_WAIT_TIME;
    playername = aPlayername;
    resetBallDirectionX();
    resetBallDirectionY();
    resetCurrentBallX();
    resetCurrentBallY();
    currentPaddleLength = getGame().getPaddle().getMaxPaddleLength();
    resetCurrentPaddleX();
    currentPaddleY = Game.PLAY_AREA_SIDE - Paddle.VERTICAL_DISTANCE - Paddle.PADDLE_WIDTH;
    id = nextId++;
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create playedGame due to game");
    }
    blocks = new ArrayList<PlayedBlockAssignment>();
    boolean didAddBlock223 = setBlock223(aBlock223);
    if (!didAddBlock223)
    {
      throw new RuntimeException("Unable to create playedGame due to block223");
    }
    setPlayStatus(PlayStatus.Ready);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setScore(int aScore)
  {
    boolean wasSet = false;
    score = aScore;
    wasSet = true;
    return wasSet;
  }

  public boolean setLives(int aLives)
  {
    boolean wasSet = false;
    lives = aLives;
    wasSet = true;
    return wasSet;
  }

  public boolean setCurrentLevel(int aCurrentLevel)
  {
    boolean wasSet = false;
    currentLevel = aCurrentLevel;
    wasSet = true;
    return wasSet;
  }

  public boolean setWaitTime(double aWaitTime)
  {
    boolean wasSet = false;
    waitTime = aWaitTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setPlayername(String aPlayername)
  {
    boolean wasSet = false;
    playername = aPlayername;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setBallDirectionX(double aBallDirectionX)
  {
    boolean wasSet = false;
    ballDirectionX = aBallDirectionX;
    wasSet = true;
    return wasSet;
  }

  public boolean resetBallDirectionX()
  {
    boolean wasReset = false;
    ballDirectionX = getDefaultBallDirectionX();
    wasReset = true;
    return wasReset;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setBallDirectionY(double aBallDirectionY)
  {
    boolean wasSet = false;
    ballDirectionY = aBallDirectionY;
    wasSet = true;
    return wasSet;
  }

  public boolean resetBallDirectionY()
  {
    boolean wasReset = false;
    ballDirectionY = getDefaultBallDirectionY();
    wasReset = true;
    return wasReset;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setCurrentBallX(double aCurrentBallX)
  {
    boolean wasSet = false;
    currentBallX = aCurrentBallX;
    wasSet = true;
    return wasSet;
  }

  public boolean resetCurrentBallX()
  {
    boolean wasReset = false;
    currentBallX = getDefaultCurrentBallX();
    wasReset = true;
    return wasReset;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setCurrentBallY(double aCurrentBallY)
  {
    boolean wasSet = false;
    currentBallY = aCurrentBallY;
    wasSet = true;
    return wasSet;
  }

  public boolean resetCurrentBallY()
  {
    boolean wasReset = false;
    currentBallY = getDefaultCurrentBallY();
    wasReset = true;
    return wasReset;
  }

  public boolean setCurrentPaddleLength(double aCurrentPaddleLength)
  {
    boolean wasSet = false;
    currentPaddleLength = aCurrentPaddleLength;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setCurrentPaddleX(double aCurrentPaddleX)
  {
    boolean wasSet = false;
    currentPaddleX = aCurrentPaddleX;
    wasSet = true;
    return wasSet;
  }

  public boolean resetCurrentPaddleX()
  {
    boolean wasReset = false;
    currentPaddleX = getDefaultCurrentPaddleX();
    wasReset = true;
    return wasReset;
  }

  public int getScore()
  {
    return score;
  }

  public int getLives()
  {
    return lives;
  }

  public int getCurrentLevel()
  {
    return currentLevel;
  }

  public double getWaitTime()
  {
    return waitTime;
  }

  /**
   * added here so that it only needs to be determined once
   */
  public String getPlayername()
  {
    return playername;
  }

  /**
   * 0/0 is the top left corner of the play area, i.e., a directionX/Y of 0/1 moves the ball down in a straight line
   */
  public double getBallDirectionX()
  {
    return ballDirectionX;
  }
  /* Code from template attribute_GetDefaulted */
  public double getDefaultBallDirectionX()
  {
    return getGame().getBall().getMinBallSpeedX();
  }

  public double getBallDirectionY()
  {
    return ballDirectionY;
  }
  /* Code from template attribute_GetDefaulted */
  public double getDefaultBallDirectionY()
  {
    return getGame().getBall().getMinBallSpeedY();
  }

  /**
   * the position of the ball is at the center of the ball
   */
  public double getCurrentBallX()
  {
    return currentBallX;
  }
  /* Code from template attribute_GetDefaulted */
  public double getDefaultCurrentBallX()
  {
    return BALL_INITIAL_X;
  }

  public double getCurrentBallY()
  {
    return currentBallY;
  }
  /* Code from template attribute_GetDefaulted */
  public double getDefaultCurrentBallY()
  {
    return BALL_INITIAL_Y;
  }

  public double getCurrentPaddleLength()
  {
    return currentPaddleLength;
  }

  /**
   * the position of the paddle is at its top right corner
   */
  public double getCurrentPaddleX()
  {
    return currentPaddleX;
  }
  /* Code from template attribute_GetDefaulted */
  public double getDefaultCurrentPaddleX()
  {
    return (Game.PLAY_AREA_SIDE - currentPaddleLength) / 2;
  }

  public double getCurrentPaddleY()
  {
    return currentPaddleY;
  }

  public int getId()
  {
    return id;
  }

  public String getPlayStatusFullName()
  {
    String answer = playStatus.toString();
    return answer;
  }

  public PlayStatus getPlayStatus()
  {
    return playStatus;
  }

  public boolean play()
  {
    boolean wasEventProcessed = false;

    PlayStatus aPlayStatus = playStatus;
    switch (aPlayStatus)
    {
      case Ready:
        setPlayStatus(PlayStatus.Moving);
        wasEventProcessed = true;
        break;
      case Paused:
        setPlayStatus(PlayStatus.Moving);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean pause()
  {
    boolean wasEventProcessed = false;

    PlayStatus aPlayStatus = playStatus;
    switch (aPlayStatus)
    {
      case Moving:
        setPlayStatus(PlayStatus.Paused);
        
        System.out.println("Paused");
        
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean move()
  {
    boolean wasEventProcessed = false;

    PlayStatus aPlayStatus = playStatus;
    switch (aPlayStatus)
    {
      case Moving:
        if (hitPaddle())
        {
        // line 15 "../../../../../Block223States.ump"
          doHitPaddleOrWall();
          setPlayStatus(PlayStatus.Moving);
          wasEventProcessed = true;
          break;
        }
        if (isOutOfBoundsAndLastLife())
        {
        // line 16 "../../../../../Block223States.ump"
          doOutOfBounds();
          setPlayStatus(PlayStatus.GameOver);
          wasEventProcessed = true;
          break;
        }
        if (isOutOfBounds())
        {
        // line 17 "../../../../../Block223States.ump"
          doOutOfBounds();
          setPlayStatus(PlayStatus.Paused);
          wasEventProcessed = true;
          break;
        }
        if (hitLastBlockAndLastLevel())
        {
        // line 18 "../../../../../Block223States.ump"
          doHitBlock();
          setPlayStatus(PlayStatus.GameOver);
          wasEventProcessed = true;
          break;
        }
        if (hitLastBlock())
        {
        // line 19 "../../../../../Block223States.ump"
          doHitBlockNextLevel();
          setPlayStatus(PlayStatus.Ready);
          wasEventProcessed = true;
          break;
        }
        if (hitBlock())
        {
        // line 20 "../../../../../Block223States.ump"
          doHitBlock();
          setPlayStatus(PlayStatus.Moving);
          wasEventProcessed = true;
          break;
        }
        if (hitWall())
        {
        // line 21 "../../../../../Block223States.ump"
          doHitPaddleOrWall();
          setPlayStatus(PlayStatus.Moving);
          wasEventProcessed = true;
          break;
        }
        // line 22 "../../../../../Block223States.ump"
        doHitNothingAndNotOutOfBounds();
        setPlayStatus(PlayStatus.Moving);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }
    setBounce(null);
    return wasEventProcessed;
  }

  private void setPlayStatus(PlayStatus aPlayStatus)
  {
    playStatus = aPlayStatus;

    // entry actions and do activities
    switch(playStatus)
    {
      case Ready:
        // line 10 "../../../../../Block223States.ump"
        doSetup();
        break;
      case GameOver:
        // line 28 "../../../../../Block223States.ump"
        doGameOver();
        
        break;
    }
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }

  public boolean hasPlayer()
  {
    boolean has = player != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_GetMany */
  public PlayedBlockAssignment getBlock(int index)
  {
    PlayedBlockAssignment aBlock = blocks.get(index);
    return aBlock;
  }

  public List<PlayedBlockAssignment> getBlocks()
  {
    List<PlayedBlockAssignment> newBlocks = Collections.unmodifiableList(blocks);
    return newBlocks;
  }

  public int numberOfBlocks()
  {
    int number = blocks.size();
    return number;
  }

  public boolean hasBlocks()
  {
    boolean has = blocks.size() > 0;
    return has;
  }

  public int indexOfBlock(PlayedBlockAssignment aBlock)
  {
    int index = blocks.indexOf(aBlock);
    return index;
  }
  /* Code from template association_GetOne */
  public BouncePoint getBounce()
  {
    return bounce;
  }

  public boolean hasBounce()
  {
    boolean has = bounce != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Block223 getBlock223()
  {
    return block223;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setPlayer(Player aPlayer)
  {
    boolean wasSet = false;
    Player existingPlayer = player;
    player = aPlayer;
    if (existingPlayer != null && !existingPlayer.equals(aPlayer))
    {
      existingPlayer.removePlayedGame(this);
    }
    if (aPlayer != null)
    {
      aPlayer.addPlayedGame(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    if (aGame == null)
    {
      return wasSet;
    }

    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      existingGame.removePlayedGame(this);
    }
    game.addPlayedGame(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBlocks()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public PlayedBlockAssignment addBlock(int aX, int aY, Block aBlock)
  {
    return new PlayedBlockAssignment(aX, aY, aBlock, this);
  }

  public boolean addBlock(PlayedBlockAssignment aBlock)
  {
    boolean wasAdded = false;
    if (blocks.contains(aBlock)) { return false; }
    PlayedGame existingPlayedGame = aBlock.getPlayedGame();
    boolean isNewPlayedGame = existingPlayedGame != null && !this.equals(existingPlayedGame);
    if (isNewPlayedGame)
    {
      aBlock.setPlayedGame(this);
    }
    else
    {
      blocks.add(aBlock);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBlock(PlayedBlockAssignment aBlock)
  {
    boolean wasRemoved = false;
    //Unable to remove aBlock, as it must always have a playedGame
    if (!this.equals(aBlock.getPlayedGame()))
    {
      blocks.remove(aBlock);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBlockAt(PlayedBlockAssignment aBlock, int index)
  {
    boolean wasAdded = false;
    if(addBlock(aBlock))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBlocks()) { index = numberOfBlocks() - 1; }
      blocks.remove(aBlock);
      blocks.add(index, aBlock);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBlockAt(PlayedBlockAssignment aBlock, int index)
  {
    boolean wasAdded = false;
    if(blocks.contains(aBlock))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBlocks()) { index = numberOfBlocks() - 1; }
      blocks.remove(aBlock);
      blocks.add(index, aBlock);
      wasAdded = true;
    }
    else
    {
      wasAdded = addBlockAt(aBlock, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setBounce(BouncePoint aNewBounce)
  {
    boolean wasSet = false;
    bounce = aNewBounce;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBlock223(Block223 aBlock223)
  {
    boolean wasSet = false;
    if (aBlock223 == null)
    {
      return wasSet;
    }

    Block223 existingBlock223 = block223;
    block223 = aBlock223;
    if (existingBlock223 != null && !existingBlock223.equals(aBlock223))
    {
      existingBlock223.removePlayedGame(this);
    }
    block223.addPlayedGame(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    if (player != null)
    {
      Player placeholderPlayer = player;
      this.player = null;
      placeholderPlayer.removePlayedGame(this);
    }
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removePlayedGame(this);
    }
    while (blocks.size() > 0)
    {
      PlayedBlockAssignment aBlock = blocks.get(blocks.size() - 1);
      aBlock.delete();
      blocks.remove(aBlock);
    }

    bounce = null;
    Block223 placeholderBlock223 = block223;
    this.block223 = null;
    if(placeholderBlock223 != null)
    {
      placeholderBlock223.removePlayedGame(this);
    }
  }

  // line 120 "../../../../../Block223Persistence.ump"
   public static  void reinitializeAutouniqueId(List<Game> games){
    nextId = 0;

	    for (Game game : games) {
	    	game.setHallOfFameEntriesPriority(new Comparator<HallOfFameEntry>(){
				     @Override
				     public int compare(HallOfFameEntry arg0, HallOfFameEntry arg1)
				     {
				          return ((Integer)arg0.getScore()).compareTo(
				          ((Integer)arg1.getScore()));
				     }
				});
	    	for(PlayedGame pgame : game.getPlayedGames()) {



		      if (pgame.getId() > nextId) {
		        nextId = pgame.getId();
		      }
		    }
	    }
	    nextId++;
  }


  /**
   * Guards
   */
  // line 35 "../../../../../Block223States.ump"
   private boolean hitPaddle(){
    bounce = calculateBouncePointPaddle();
    setBounce(bounce);
    if(bounce!=null) {
    	System.out.println("hit paddle");
    }
    return (bounce != null);
  }

  // line 41 "../../../../../Block223States.ump"
   private boolean isOutOfBoundsAndLastLife(){

     if(lives == 1 & isBallOutOfBounds()){
    	return true;
    }
    return false;
  }


  // line 49 "../../../../../Block223States.ump"
   private boolean isBallOutOfBounds(){
    if (currentBallY >=390){
  		return true;
  	}
  	return false;
  }

  // line 56 "../../../../../Block223States.ump"
   private boolean isOutOfBounds(){
    boolean outOfBounds = isBallOutOfBounds();
    return outOfBounds;
  }

  // line 61 "../../../../../Block223States.ump"
   private boolean hitLastBlockAndLastLevel(){
    game = getGame();
    int nrLevels = game.numberOfLevels();
    setBounce(null);
    if(nrLevels==currentLevel) {
    	int nrBlocks = numberOfBlocks();
    	if(nrBlocks==1) {
    		PlayedBlockAssignment block= getBlock(0);
    		BouncePoint bp = calculateBouncePointBlock(block);
    		setBounce(bp);
    		return (bp!=null);
    	}
    }
    return false;
  }

  // line 77 "../../../../../Block223States.ump"
   private boolean hitLastBlock(){
    int nrBlocks = numberOfBlocks();
    setBounce(null);
    if(nrBlocks==1) {
    	PlayedBlockAssignment block=getBlock(0);
    	BouncePoint bp = calculateBouncePointBlock(block);
    	setBounce(bp);
    	return (bp!=null);
    }
    return false;
  }

  // line 89 "../../../../../Block223States.ump"
   private boolean hitBlock(){
    int nrBlocks = numberOfBlocks();
	setBounce(null);
	for(int index=0;index<=numberOfBlocks()-1;index++) {
		PlayedBlockAssignment block = getBlock(index);
		BouncePoint bp = calculateBouncePointBlock(block);
		BouncePoint bounce = getBounce();
		Boolean closer = isCloser(bp, bounce);
		if(closer) {
			setBounce(bp);
		}

	}
    return (getBounce()!=null);
  }

  // line 105 "../../../../../Block223States.ump"
   private boolean isCloser(BouncePoint first, BouncePoint second){
    if(first ==null) {
		  return false;
	  }
	  else if(second == null) {
		  return true;
	  }
	  else {
		  double currentX = getCurrentBallX();
		  double currentY = getCurrentBallY();
		  double firstX = first.getX();
		  double firstY = first.getY();
		  double secondX = second.getX();
		  double secondY = second.getY();
		  double firstDis = Math.pow((firstX-currentX),2)+Math.pow((firstY-currentY),2);
		  double secondDis = Math.pow((secondX-currentX),2)+Math.pow((secondY-currentY),2);
		  if(firstDis<=secondDis) return true;
		  else return false;

	  }
  }

  // line 127 "../../../../../Block223States.ump"
   private boolean hitWall(){
    bounce = calculateBouncePointWall();
    setBounce(bounce);
    return (bounce != null);
  }


  /**
   * Actions
   */
  // line 135 "../../../../../Block223States.ump"
   private void doSetup(){
    resetCurrentBallX();
    resetCurrentBallY();
    resetBallDirectionX();
    resetBallDirectionY();
    resetCurrentPaddleX();
     game=getGame();
  	Level level =game.getLevel(currentLevel-1);
  	List<BlockAssignment> assignments= level.getBlockAssignments();
  	for(BlockAssignment a : assignments){

  		int x= Game.WALL_PADDING+(Block.SIZE+Game.COLUMNS_PADDING)*(a.getGridHorizontalPosition()-1);
  		int y= Game.WALL_PADDING+(Block.SIZE+Game.ROW_PADDING)*(a.getGridVerticalPosition()-1);
  		PlayedBlockAssignment pblock
  	              = new PlayedBlockAssignment(x,y,a.getBlock(),this);
  	}

  	while(numberOfBlocks() < game.getNrBlocksPerLevel()){
  	    Random rn =  new Random();
  	    int ax, ay;
  	    ax = rn.nextInt(14)+1;
  	    ay = rn.nextInt(10)+1;


  	    boolean ident =false;

  	    for (PlayedBlockAssignment a: getBlocks()) {
  	    	//meet existed block
  	    	if (ax == a.getX() && ay == a.getY()) {
  	    		ident=true;

	  	    	while(ident) {
	  	    		ax++;
	  	    		if(ax==15) {
	  	    			ax=1;
	  	    			if(ay==15) {
	  	    				ay=1;
	  	    			}else ay++;
	  	    		}
	  	    		for (PlayedBlockAssignment b: getBlocks()) {
	  	    			if (ax == b.getX() && ay == b.getY()) {
	  	    	    		ident=true;

	  	    			}else {ident = false;}
	  	    		}
	  	    	}
	  	    	break;
  	    	}

		}

  	    int x= Game.WALL_PADDING+(Block.SIZE+Game.COLUMNS_PADDING)*(ax-1);
  		int y= Game.WALL_PADDING+(Block.SIZE+Game.ROW_PADDING)*(ay-1);
  	    new PlayedBlockAssignment(x,y,game.getRandomBlock(),this);

  	}
    // TODO implement
  }

  // line 161 "../../../../../Block223States.ump"
   private void doHitPaddleOrWall(){
    bounceBall();
  }

  // line 166 "../../../../../Block223States.ump"
   private void bounceBall(){
	double dx = getBallDirectionX();
	double dy = getBallDirectionY();
	double cx = getCurrentBallX();
	double cy = getCurrentBallY();
	double tx = cx + getBallDirectionX(); // theoretical
	double ty = cy + getBallDirectionY(); // theoretical
    double bx = bounce.getX();
    double by = bounce.getY();
    BounceDirection bd = bounce.getDirection();
    if (bd == BounceDirection.FLIP_X) {
    	  double sign = 1.0;
    	  if (dy < 0) {
    		  sign = -1.0;
    	  }
    	  else if (dy >= 0) {
    		  sign = 1.0;
    	  }
      setBallDirectionX(-dx);
      setBallDirectionY(dy + sign * 0.1 * Math.abs(dx));
      setCurrentBallX(2 * bx - tx);
      setCurrentBallY(by + (dy - by + cy) / dx * getBallDirectionY());
    }
    else if (bd == BounceDirection.FLIP_Y) {
    	double sign = 1.0;
  	  if (dx < 0) {
  		  sign = -1.0;
  	  }
  	  else if (dx >= 0) {
  		  sign = 1.0;
  	  }
    setBallDirectionY(-dy);
    setBallDirectionX(dx + sign * 0.1 * Math.abs(dy));
    setCurrentBallY(2 * by - ty);
    setCurrentBallX(bx + (dx - bx + cx) / dy * getBallDirectionY());
    }
    else if (bd == BounceDirection.FLIP_BOTH) {
      setCurrentBallX(2 * bx - tx);
      setCurrentBallY(2 * by - ty);
      setBallDirectionX(-dx);
      setBallDirectionY(-dy);
    }

	if (ballDirectionX >=10) {
		setBallDirectionX(ballDirectionX/10);
		setBallDirectionY(ballDirectionY/10);
	}
	if (ballDirectionY >=10) {
		setBallDirectionX(ballDirectionX/10);
		setBallDirectionY(ballDirectionY/10);
	}

  }

  // line 184 "../../../../../Block223States.ump"
   private void doOutOfBounds(){
    setLives(lives-1);
    resetCurrentBallX();
    resetCurrentBallY();
    resetBallDirectionX();
    resetBallDirectionY();
    resetCurrentPaddleX();
  }

  // line 193 "../../../../../Block223States.ump"
   private void doHitBlock(){
    int score = getScore();
    BouncePoint bounce = getBounce();
    PlayedBlockAssignment pblock = bounce.getHitBlock();
    Block block = pblock.getBlock();
    int points = block.getPoints();
    setScore(score+points);
    pblock.delete();
    System.out.println("Block hit and deleted");
    bounceBall();
    Block223Controller.hitBlockMusic();
    
  }

  // line 204 "../../../../../Block223States.ump"
   private void doHitBlockNextLevel(){
	    doHitBlock();
	    int level = getCurrentLevel();
	    setCurrentLevel(level+1);
	    setCurrentPaddleLength(getGame().getPaddle().getMaxPaddleLength()-(
	    		getGame().getPaddle().getMaxPaddleLength()-getGame().getPaddle().getMinPaddleLength())
	    		/(getGame().numberOfLevels()-1)*(getCurrentLevel()-1));
	    setWaitTime( INITIAL_WAIT_TIME * Math.pow(getGame().getBall().getBallSpeedIncreaseFactor(), getCurrentLevel()-1));
	  
  }


   private boolean contain(Line2D line, double x, double y){
	   boolean onPoint =
			y == line.getY2() && x == line.getX2();
		boolean onLine =
			(y - line.getY1()) / (x- line.getX1())
			== (y - line.getY2()) / (x - line.getX2());
		boolean inRange =
			(Math.min(line.getX1(), line.getX2()) <= x && x <= Math.max(line.getX1(), line.getX2()))
			&& (Math.min(line.getY1(), line.getY2()) <= y && y <= Math.max(line.getY1(), line.getY2()));
		return onPoint || (onLine && inRange);
	}

  // line 214 "../../../../../Block223States.ump"
   private BouncePoint calculateBouncePointWall(){
    double ballRadius = Ball.BALL_DIAMETER / 2;
  double x = getCurrentBallX();
  double y = getCurrentBallY();
  double dx = getBallDirectionX();
  double dy = getBallDirectionY();
  double nextX = x + dx;
  double nextY = y + dy;
  double bounceX = 0.0;
  double bounceY = 0.0;
  double leftBorder = ballRadius; // 0.0
  double upBorder = ballRadius; // 0.0
  double rightBorder = Game.PLAY_AREA_SIDE - ballRadius; // 385.0
  Line2D line = new Line2D.Double();
  line.setLine(x, y, nextX, nextY);
  BounceDirection bd = BounceDirection.FLIP_Y; //default
  if (contain(line, leftBorder, upBorder)) { // top-left
    bounceX = leftBorder;
    bounceY = upBorder;
    bd = BounceDirection.FLIP_BOTH;
  }
  else if (contain(line, rightBorder, upBorder)) { // top-right
    bounceX = rightBorder;
    bounceY = upBorder;
    bd = BounceDirection.FLIP_BOTH;
  }
  else if (nextX <= leftBorder) { // left wall
    bounceX = leftBorder;
    if (dy > 0.0) { // downward
      bounceY = y + (Math.abs(y - nextY) / Math.abs(x - nextX) * Math.abs(x - bounceX));
    }
    else if (dy <= 0.0) { // upward
      bounceY = y - (Math.abs(y - nextY) / Math.abs(x - nextX) * Math.abs(x - bounceX));
    }
    bd = BounceDirection.FLIP_X;
  }
  else if (nextX >= rightBorder) { // right wall
    bounceX = rightBorder;
    if (dy > 0.0) { // downward
      bounceY = y + (Math.abs(y - nextY) / Math.abs(x - nextX) * Math.abs(bounceX - x));
    }
    else if (dy <= 0.0) { // upward
      bounceY = y - (Math.abs(y - nextY) / Math.abs(x - nextX) * Math.abs(bounceX - x));
    }
    bd = BounceDirection.FLIP_X;
  }
  else if (nextY <= upBorder) { // top wall
    bounceY = upBorder;
    if (dx > 0.0) { // leftward
      bounceX = x + (Math.abs(x - nextX) / Math.abs(y - nextY) * Math.abs(y - bounceY));
    }
    else if (dx <= 0.0) { // rightward
      bounceX = x - (Math.abs(x - nextX) / Math.abs(y - nextY) * Math.abs(y - bounceY));
    }
    bd = BounceDirection.FLIP_Y;
  }
  else {
    return null;
  }
  BouncePoint bp = new BouncePoint(bounceX, bounceY, bd);
  return bp;
  }

	private static boolean onArc(Arc2D arc, double r, double x, double y) {
		double centerX = arc.getCenterX();
		double centerY = arc.getCenterY();
		double distancePoint = Math.sqrt(Math.pow((x - centerX), 2) + Math.pow((y - centerY), 2));
		return distancePoint == r;
	}

  // line 277 "../../../../../Block223States.ump"
   private BouncePoint calculateBouncePointPaddle(){
    double ballRadius = Ball.BALL_DIAMETER / 2;
  double paddleY = Game.PLAY_AREA_SIDE - Paddle.VERTICAL_DISTANCE - Paddle.PADDLE_WIDTH;
  double x = getCurrentBallX();
  double y = getCurrentBallY();
  double dx = getBallDirectionX();
  double dy = getBallDirectionY();
  double nextX = x + dx;
  double nextY = y + dy;
  double bounceX = 0.0;
  double bounceY = 0.0;

  BounceDirection bd = BounceDirection.FLIP_Y; //default

  // initialize rectangle for estimation
  double rectX = getCurrentPaddleX() - ballRadius;
  double rectY = paddleY - ballRadius;
  double rectWidth = getCurrentPaddleLength() + 2 * ballRadius;
  double rectHeight = Paddle.PADDLE_WIDTH + 2 * ballRadius;
  double rectR = rectX + rectWidth;
  Rectangle2D paddleRect = new Rectangle2D.Double
  (rectX, rectY, rectWidth, rectHeight);

  boolean isIntersectWithRect = paddleRect.intersectsLine(x, y, nextX, nextY);
  if (isIntersectWithRect == false) {
    return null;
  }

  Line2D line = new Line2D.Double();
  line.setLine(x, y, nextX, nextY);
  math.geom2d.line.Line2D line2D = new math.geom2d.line.Line2D(x, y, nextX, nextY);

  math.geom2d.conic.CircleArc2D topLeft = new math.geom2d.conic.CircleArc2D
  (getCurrentPaddleX(), paddleY, ballRadius, 270.0, 0.0);

  math.geom2d.conic.CircleArc2D topRight = new math.geom2d.conic.CircleArc2D
  (getCurrentPaddleX() + getCurrentPaddleLength(), paddleY, ballRadius, 90.0, 0.0);

  Arc2D topLeftArc = new Arc2D.Double();
  topLeftArc.setArcByCenter(getCurrentPaddleX(), paddleY, ballRadius, 270.0, 360.0, Arc2D.OPEN);

  Arc2D topRightArc = new Arc2D.Double();
  topRightArc.setArcByCenter(getCurrentPaddleX() + getCurrentPaddleLength(), paddleY, ballRadius, 0.0, 90.0, Arc2D.OPEN);

  Line2D left = new Line2D.Double();
  left.setLine(getCurrentPaddleX() - ballRadius, paddleY,
  getCurrentPaddleX() - ballRadius, paddleY + Paddle.PADDLE_WIDTH);

  Line2D right = new Line2D.Double();
  right.setLine(getCurrentPaddleX() + getCurrentPaddleLength() + ballRadius, paddleY,
  getCurrentPaddleX() + getCurrentPaddleLength() + ballRadius, paddleY + Paddle.PADDLE_WIDTH);

  Line2D top = new Line2D.Double();
  top.setLine(getCurrentPaddleX(), getCurrentPaddleY() - ballRadius,
  getCurrentPaddleX() + getCurrentPaddleLength(), getCurrentPaddleY() - ballRadius);

  if (line.intersectsLine(right) && !(rectR == line.getX1())) { // C
    bounceX = getCurrentPaddleX() + getCurrentPaddleLength() + ballRadius;
    if (dy > 0.0) { // downward
      bounceY = y + (Math.abs(y - nextY) / Math.abs(x - nextX) * Math.abs(x - bounceX));
    }
    else if (dy <= 0.0) { // upward
      bounceY = y - (Math.abs(y - nextY) / Math.abs(x - nextX) * Math.abs(x - bounceX));
    }
    bd = BounceDirection.FLIP_X;
  }
  else if (line.intersectsLine(left) && !(rectX == line.getX1())) { // B
    bounceX = getCurrentPaddleX() - ballRadius;
    if (dy > 0.0) { // downward
      bounceY = y + (Math.abs(y - nextY) / Math.abs(x - nextX) * Math.abs(bounceX - x));
    }
    else if (dy <= 0.0) { // upward
      bounceY = y - (Math.abs(y - nextY) / Math.abs(x - nextX) * Math.abs(bounceX - x));
    }
    bd = BounceDirection.FLIP_X;
  }

  else if (line.intersectsLine(top) && !(rectY == line.getY1())) { // A
    bounceY = paddleY - ballRadius;
    if (dx > 0.0) { // leftward
      bounceX = x + (Math.abs(x - nextX) / Math.abs(y - nextY) * Math.abs(y - bounceY));
    }
    else if (dx <= 0.0) { // rightward
      bounceX = x - (Math.abs(x - nextX) / Math.abs(y - nextY) * Math.abs(y - bounceY));
    }
    bd = BounceDirection.FLIP_Y;
  }

  else if (!topLeft.intersections(line2D).isEmpty() && !onArc(topLeftArc, ballRadius, x, y)) { // E
    Collection<math.geom2d.Point2D> intersections = topLeft.intersections(line2D);
    if (intersections.isEmpty()) {
    	  return null;
    }
    else if (intersections.size() == 1) {
      math.geom2d.Point2D[] intersectionArray
      = new math.geom2d.Point2D[intersections.size()];
      intersections.toArray(intersectionArray);
      math.geom2d.Point2D intersection = intersectionArray[0];
      bounceX = intersection.getX();
      bounceY = intersection.getY();
    }
    else if (intersections.size() == 2) {
      math.geom2d.Point2D[] intersectionArray
      = new math.geom2d.Point2D[intersections.size()];
      intersections.toArray(intersectionArray);
      math.geom2d.Point2D intersection1 = intersectionArray[0];
      math.geom2d.Point2D intersection2 = intersectionArray[1];
      double x1 = intersection1.getX();
      double y1 = intersection1.getY();
      double x2 = intersection2.getX();
      double y2 = intersection2.getY();
      if (Math.abs(x1 - x) <= Math.abs(x2 - x)) { // 1 is nearer
        bounceX = x1;
        bounceY = y1;
      }
      else if (Math.abs(x1 - x) > Math.abs(x2 - x)) { // 2 is nearer
        bounceX = x2;
        bounceY = y2;
      }
    }
    if (dx < 0.0) { // leftward, from the right
      bd = BounceDirection.FLIP_Y;
    }
    else if (dx >= 0.0) { // rightward, from the left
      bd = BounceDirection.FLIP_X;
    }
  }

  else if (!topRight.intersections(line2D).isEmpty() && !onArc(topRightArc, ballRadius, x, y)) { // F
    Collection<math.geom2d.Point2D> intersections = topRight.intersections(line2D);
    if (intersections.isEmpty()) {
    	  return null;
    }
    else if (intersections.size() == 1) {
      math.geom2d.Point2D[] intersectionArray
      = new math.geom2d.Point2D[intersections.size()];
      intersections.toArray(intersectionArray);
      math.geom2d.Point2D intersection = intersectionArray[0];
      bounceX = intersection.getX();
      bounceY = intersection.getY();
    }
    else if (intersections.size() == 2) {
      math.geom2d.Point2D[] intersectionArray
      = new math.geom2d.Point2D[intersections.size()];
      intersections.toArray(intersectionArray);
      math.geom2d.Point2D intersection1 = intersectionArray[0];
      math.geom2d.Point2D intersection2 = intersectionArray[1];
      double x1 = intersection1.getX();
      double y1 = intersection1.getY();
      double x2 = intersection2.getX();
      double y2 = intersection2.getY();
      if (Math.abs(x1 - x) <= Math.abs(x2 - x)) { // 1 is nearer
        bounceX = x1;
        bounceY = y1;
      }
      else if (Math.abs(x1 - x) > Math.abs(x2 - x)) { // 2 is nearer
        bounceX = x2;
        bounceY = y2;
      }
    }
    if (dx < 0.0) { // leftward, from the right
      bd = BounceDirection.FLIP_X;
    }
    else if (dx >= 0.0) { // rightward, from the left
      bd = BounceDirection.FLIP_Y;
    }
  }

  else {
    return null;
  }

  BouncePoint bp = new BouncePoint(bounceX, bounceY, bd);
  return bp;
  }

  // line 453 "../../../../../Block223States.ump"
   private BouncePoint calculateBouncePointBlock(PlayedBlockAssignment block){
			    double ballRadius = Ball.BALL_DIAMETER / 2;
			    double blockX = block.getX();
			    double blockY = block.getY();
			    double x = getCurrentBallX();
			    double y = getCurrentBallY();
			    double dx = getBallDirectionX();
			    double dy = getBallDirectionY();
			    double nextX = x + dx;
			    double nextY = y + dy;
			    double bounceX = 0.0;
			    double bounceY = 0.0;
			    
			    BounceDirection bd = BounceDirection.FLIP_Y; //default
			    BouncePoint bp = new BouncePoint(bounceX, bounceY, bd);
			    // initialize rectangle for estimation
			    double rectX = blockX - ballRadius;
			    double rectY = blockY - ballRadius;
			    

			    double rectWidth = Block.SIZE + 2 * ballRadius;
			    double rectHeight = Block.SIZE + 2 * ballRadius;
			    double rectR = rectX + rectWidth;
			    double rectB = rectY + rectHeight;
			    Rectangle2D blockRect = new Rectangle2D.Double
			    (rectX, rectY, rectWidth, rectHeight);
			    

			    boolean isIntersectWithRect = blockRect.intersectsLine(x, y, nextX, nextY);
			    if (isIntersectWithRect == false) {
			      return null;
			    }
			    
			    Line2D line = new Line2D.Double();
			    line.setLine(x, y, nextX, nextY);
			    math.geom2d.line.Line2D line2D = new math.geom2d.line.Line2D(x, y, nextX, nextY);
			    

			    math.geom2d.conic.CircleArc2D topLeft = new math.geom2d.conic.CircleArc2D
			    (blockX, blockY, ballRadius, 270.0, 0.0);

			    math.geom2d.conic.CircleArc2D topRight = new math.geom2d.conic.CircleArc2D
			    (blockX + Block.SIZE,  blockY, ballRadius, 90.0, 0.0);
			    
			    math.geom2d.conic.CircleArc2D bottomLeft = new math.geom2d.conic.CircleArc2D
			    (blockX ,  blockY+ Block.SIZE, ballRadius, 180.0, 270.0);
			    
			    math.geom2d.conic.CircleArc2D bottomRight = new math.geom2d.conic.CircleArc2D
			    (blockX+ Block.SIZE ,  blockY+ Block.SIZE, ballRadius, 180.0, 270.0);

			    Arc2D topLeftArc = new Arc2D.Double();
			    topLeftArc.setArcByCenter(blockX,  blockY, ballRadius, 270.0, 360.0, Arc2D.OPEN);

			    Arc2D topRightArc = new Arc2D.Double();
			    topRightArc.setArcByCenter(blockX + Block.SIZE,  blockY, ballRadius, 0.0, 90.0, Arc2D.OPEN);
			    
			    Arc2D bottomLeftArc = new Arc2D.Double();
			    bottomLeftArc.setArcByCenter(blockX ,  blockY+ Block.SIZE, ballRadius, 180.0, 270.0, Arc2D.OPEN);

			    Arc2D bottomRightArc = new Arc2D.Double();
			    bottomRightArc.setArcByCenter(blockX+Block.SIZE,blockY+Block.SIZE, ballRadius, 180.0, 270.0, Arc2D.OPEN);

			    Line2D left = new Line2D.Double();
			    left.setLine(blockX - ballRadius,  blockY,
			        blockX - ballRadius,  blockY + Block.SIZE);

			    Line2D right = new Line2D.Double();
			    right.setLine(blockX + Block.SIZE + ballRadius,  blockY,
			        blockX + Block.SIZE + ballRadius,  blockY + Block.SIZE);

			    Line2D top = new Line2D.Double();
			    top.setLine(blockX, blockY - ballRadius,
			        blockX + Block.SIZE, blockY - ballRadius);
			    
			    Line2D bottom = new Line2D.Double();
			    bottom.setLine(blockX, blockY + ballRadius + Block.SIZE,
			        blockX + Block.SIZE, blockY + ballRadius + Block.SIZE);


			    if (line.intersectsLine(right) && !(rectR == line.getX1())) { // C
			      bounceX = blockX + Block.SIZE + ballRadius;
			      bp.setHitBlock(block);
			      if (dy > 0.0) { // downward
			        bounceY = y + (Math.abs(y - nextY) / Math.abs(x - nextX) * Math.abs(x - bounceX));
			      }
			      else if (dy <= 0.0) { // upward
			        bounceY = y - (Math.abs(y - nextY) / Math.abs(x - nextX) * Math.abs(x - bounceX));
			      }
			      bd = BounceDirection.FLIP_X;
			    }
			    else if (line.intersectsLine(left) && !(rectX == line.getX1())) { // B
			      bounceX = blockX - ballRadius;
			      bp.setHitBlock(block);
			      if (dy > 0.0) { // downward
			        bounceY = y + (Math.abs(y - nextY) / Math.abs(x - nextX) * Math.abs(bounceX - x));
			      }
			      else if (dy <= 0.0) { // upward
			        bounceY = y - (Math.abs(y - nextY) / Math.abs(x - nextX) * Math.abs(bounceX - x));
			      }
			      bd = BounceDirection.FLIP_X;
			    }

			    else if (line.intersectsLine(top) && !(rectY == line.getY1())) { // A
			      bounceY = blockY - ballRadius;
			      bp.setHitBlock(block);
			      if (dx > 0.0) { // leftward
			        bounceX = x + (Math.abs(x - nextX) / Math.abs(y - nextY) * Math.abs(y - bounceY));
			      }
			      else if (dx <= 0.0) { // rightward
			        bounceX = x - (Math.abs(x - nextX) / Math.abs(y - nextY) * Math.abs(y - bounceY));
			      }
			      bd = BounceDirection.FLIP_Y;
			    }
			    else if (line.intersectsLine(bottom) && !(rectB == line.getY1())) { // D
			        bounceY = blockY + ballRadius + Block.SIZE;
			        bp.setHitBlock(block);
			        if (dx > 0.0) { // leftward
			          bounceX = x + (Math.abs(x - nextX) / Math.abs(y - nextY) * Math.abs(bounceY - y));
			        }
			        else if (dx <= 0.0) { // rightward
			          bounceX = x - (Math.abs(x - nextX) / Math.abs(y - nextY) * Math.abs(bounceY - y));
			        }
			        bd = BounceDirection.FLIP_Y;
			      }
			    
			    
			    else if (!topLeft.intersections(line2D).isEmpty() && !onArc(topLeftArc, ballRadius, x, y)) { // E
			      bp.setHitBlock(block);
			        Collection<math.geom2d.Point2D> intersections = topLeft.intersections(line2D);
			        if (intersections.isEmpty()) {
			            return null;
			        }
			        else if (intersections.size() == 1) {
			          math.geom2d.Point2D[] intersectionArray
			          = new math.geom2d.Point2D[intersections.size()];
			          intersections.toArray(intersectionArray);
			          math.geom2d.Point2D intersection = intersectionArray[0];
			          bounceX = intersection.getX();
			          bounceY = intersection.getY();
			        }
			        else if (intersections.size() == 2) {
			          math.geom2d.Point2D[] intersectionArray
			          = new math.geom2d.Point2D[intersections.size()];
			          intersections.toArray(intersectionArray);
			          math.geom2d.Point2D intersection1 = intersectionArray[0];
			          math.geom2d.Point2D intersection2 = intersectionArray[1];
			          double x1 = intersection1.getX();
			          double y1 = intersection1.getY();
			          double x2 = intersection2.getX();
			          double y2 = intersection2.getY();
			          if (Math.abs(x1 - x) <= Math.abs(x2 - x)) { // 1 is nearer
			            bounceX = x1;
			            bounceY = y1;
			          }
			          else if (Math.abs(x1 - x) > Math.abs(x2 - x)) { // 2 is nearer
			            bounceX = x2;
			            bounceY = y2;
			          }
			        }
			        if (dx < 0.0) { // leftward, from the right
			          bd = BounceDirection.FLIP_Y;
			        }
			        else if (dx >= 0.0) { // rightward, from the left
			          bd = BounceDirection.FLIP_X;
			        }
			      }

			      

			      else if (!topRight.intersections(line2D).isEmpty() && !onArc(topRightArc, ballRadius, x, y)) { // F
			      bp.setHitBlock(block);
			        Collection<math.geom2d.Point2D> intersections = topRight.intersections(line2D);
			        if (intersections.isEmpty()) {
			            return null;
			        }
			        else if (intersections.size() == 1) {
			          math.geom2d.Point2D[] intersectionArray
			          = new math.geom2d.Point2D[intersections.size()];
			          intersections.toArray(intersectionArray);
			          math.geom2d.Point2D intersection = intersectionArray[0];
			          bounceX = intersection.getX();
			          bounceY = intersection.getY();
			        }
			        else if (intersections.size() == 2) {
			          math.geom2d.Point2D[] intersectionArray
			          = new math.geom2d.Point2D[intersections.size()];
			          intersections.toArray(intersectionArray);
			          math.geom2d.Point2D intersection1 = intersectionArray[0];
			          math.geom2d.Point2D intersection2 = intersectionArray[1];
			          double x1 = intersection1.getX();
			          double y1 = intersection1.getY();
			          double x2 = intersection2.getX();
			          double y2 = intersection2.getY();
			          if (Math.abs(x1 - x) <= Math.abs(x2 - x)) { // 1 is nearer
			            bounceX = x1;
			            bounceY = y1;
			          }
			          else if (Math.abs(x1 - x) > Math.abs(x2 - x)) { // 2 is nearer
			            bounceX = x2;
			            bounceY = y2;
			          }
			        }
			        if (dx < 0.0) { // leftward, from the right
			          bd = BounceDirection.FLIP_X;
			        }
			        else if (dx >= 0.0) { // rightward, from the left
			          bd = BounceDirection.FLIP_Y;
			        }
			      }
			    
			    
			      else if (!bottomLeft.intersections(line2D).isEmpty() && !onArc(bottomLeftArc, ballRadius, x, y)) { // G
			      bp.setHitBlock(block);
			        Collection<math.geom2d.Point2D> intersections = bottomLeft.intersections(line2D);
			        if (intersections.isEmpty()) {
			            return null;
			        }
			        else if (intersections.size() == 1) {
			          math.geom2d.Point2D[] intersectionArray
			          = new math.geom2d.Point2D[intersections.size()];
			          intersections.toArray(intersectionArray);
			          math.geom2d.Point2D intersection = intersectionArray[0];
			          bounceX = intersection.getX();
			          bounceY = intersection.getY();
			        }
			        else if (intersections.size() == 2) {
			          math.geom2d.Point2D[] intersectionArray
			          = new math.geom2d.Point2D[intersections.size()];
			          intersections.toArray(intersectionArray);
			          math.geom2d.Point2D intersection1 = intersectionArray[0];
			          math.geom2d.Point2D intersection2 = intersectionArray[1];
			          double x1 = intersection1.getX();
			          double y1 = intersection1.getY();
			          double x2 = intersection2.getX();
			          double y2 = intersection2.getY();
			          if (Math.abs(x1 - x) <= Math.abs(x2 - x)) { // 1 is nearer
			            bounceX = x1;
			            bounceY = y1;
			          }
			          else if (Math.abs(x1 - x) > Math.abs(x2 - x)) { // 2 is nearer
			            bounceX = x2;
			            bounceY = y2;
			          }
			        }
			        if (dx < 0.0) { // leftward, from the right
			          bd = BounceDirection.FLIP_Y;
			        }
			        else if (dx >= 0.0) { // rightward, from the left
			          bd = BounceDirection.FLIP_X;
			        }
			      }

			      else if (!bottomRight.intersections(line2D).isEmpty() && !onArc(bottomRightArc, ballRadius, x, y)) { // H
			      System.out.println("!!!!!!!!!!!!!!");
			      bp.setHitBlock(block);
			        Collection<math.geom2d.Point2D> intersections = bottomRight.intersections(line2D);
			        if (intersections.isEmpty()) {
			            return null;
			        }
			        else if (intersections.size() == 1) {
			          math.geom2d.Point2D[] intersectionArray
			          = new math.geom2d.Point2D[intersections.size()];
			          intersections.toArray(intersectionArray);
			          math.geom2d.Point2D intersection = intersectionArray[0];
			          bounceX = intersection.getX();
			          bounceY = intersection.getY();
			        }
			        else if (intersections.size() == 2) {
			          math.geom2d.Point2D[] intersectionArray
			          = new math.geom2d.Point2D[intersections.size()];
			          intersections.toArray(intersectionArray);
			          math.geom2d.Point2D intersection1 = intersectionArray[0];
			          math.geom2d.Point2D intersection2 = intersectionArray[1];
			          double x1 = intersection1.getX();
			          double y1 = intersection1.getY();
			          double x2 = intersection2.getX();
			          double y2 = intersection2.getY();
			          if (Math.abs(x1 - x) <= Math.abs(x2 - x)) { // 1 is nearer
			            bounceX = x1;
			            bounceY = y1;
			          }
			          else if (Math.abs(x1 - x) > Math.abs(x2 - x)) { // 2 is nearer
			            bounceX = x2;
			            bounceY = y2;
			          }
			        }
			        if (dx < 0.0) { // leftward, from the right
			          bd = BounceDirection.FLIP_X;
			        }
			        else if (dx >= 0.0) { // rightward, from the left
			          bd = BounceDirection.FLIP_Y;
			        }
			      }

			    
			    else {
			        return null;
			     }
			    
			    bp.setDirection(bd);
			    bp.setX(bounceX);
			    bp.setY(bounceY);
			    return bp;
			    
			}

  // line 457 "../../../../../Block223States.ump"
   private Point2D findIntersection(Line2D l1, Line2D l2){
    double a1 = l1.getY2() - l1.getY1();
      double b1 = l1.getX1() - l1.getX2();
      double c1 = a1 * l1.getX1() + b1 * l1.getY1();

      double a2 = l2.getY2() - l2.getY1();
      double b2 = l2.getX1() - l2.getX2();
      double c2 = a2 * l2.getX1() + b2 * l2.getY1();

      double delta = a1 * b2 - a2 * b1;

      if (delta == 0) {
        return null;
      } else {
        Point2D intersection = new Point2D.Double(
          (b2 * c1 - b1 * c2) / delta,
          (a1 * c2 - a2 * c1) / delta
        );

        if (l1.contains(intersection) && l2.contains(intersection)) {
          return intersection;
        } else {
          return null;
        }
      }
  }

  // line 485 "../../../../../Block223States.ump"
   private Point2D findIntersection(Point2D circleCenter, Line2D line, int quadrant){
    // Set circleCenter to (0, 0), make the line relative to that
    double x1 = line.getX1() - circleCenter.getX();
    double y1 = line.getY1() - circleCenter.getY();
    double x2 = line.getX2() - circleCenter.getX();
    double y2 = line.getY2() - circleCenter.getY();


    double dx = x2 - x1;
    double dy = y2 - y1;
    double dr = Math.sqrt(dx * dx + dy * dy);
    double det = x1 * y2 - x2 * y1;
    double discriminant = Math.pow(Ball.BALL_DIAMETER, 2) * dr * dr - det * det;

    if (discriminant < 0) { // no intersection
      return null;
    } else {
      double closerX;
      double closerY;

      if (discriminant == 0) {
        closerX = (det * dy) / (dr * dr);
        closerY = (-1 * det * dx) / (dr * dr);
      } else {
        double sqrtDiscriminant = Math.sqrt(discriminant);
        double xA = (det * dy + Math.signum(dy) * dx * sqrtDiscriminant) / (dr * dr);
        double yA = (-1 * det * dx + Math.abs(dy) * sqrtDiscriminant) / (dr * dr);
        double xB = (det * dy - Math.signum(dy) * dx * sqrtDiscriminant) / (dr * dr);
        double yB = (-1 * det * dx - Math.abs(dy) * sqrtDiscriminant) / (dr * dr);

        double distBallToA = Point2D.distance(x1, y1, xA, yA);
        double distBallToB = Point2D.distance(x1, y1, xB, yB);

        if (distBallToA <= distBallToB) { // == should never happen
          closerX = xA;
          closerY = yA;
        } else {
          closerX = xB;
          closerY = yB;
        }
      }

      if (
        line.contains(closerX, closerY)
        && quadrant == 1 && closerX > 0 && closerY > 0
        || quadrant == 2 && closerX > 0 && closerY < 0
        || quadrant == 3 && closerX > 0 && closerY > 0
        || quadrant == 4 && closerX > 0 && closerY > 0
      ) {
        // relative point -> actual point
        return new Point2D.Double(closerX + circleCenter.getX(), closerY + circleCenter.getY());
      } else {
        return null;
      }
    }
  }

  // line 543 "../../../../../Block223States.ump"
   private void doHitNothingAndNotOutOfBounds(){
    double x = getCurrentBallX();
  	double y = getCurrentBallY();
  	double dx = getBallDirectionX();
  	double dy = getBallDirectionY();
  	setCurrentBallX(x+dx);
  	setCurrentBallY(y+dy);
  }

  // line 552 "../../../../../Block223States.ump"
   private void doGameOver(){
    block223 = getBlock223();
    player = getPlayer();
    if(player != null){
    	game = getGame();
    	HallOfFameEntry hof = new HallOfFameEntry(score, playername, player, game, block223);
    	System.out.println("Game Over");
    	
 		game.setMostRecentEntry(hof);
 		
    }
    //delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "score" + ":" + getScore()+ "," +
            "lives" + ":" + getLives()+ "," +
            "currentLevel" + ":" + getCurrentLevel()+ "," +
            "waitTime" + ":" + getWaitTime()+ "," +
            "playername" + ":" + getPlayername()+ "," +
            "ballDirectionX" + ":" + getBallDirectionX()+ "," +
            "ballDirectionY" + ":" + getBallDirectionY()+ "," +
            "currentBallX" + ":" + getCurrentBallX()+ "," +
            "currentBallY" + ":" + getCurrentBallY()+ "," +
            "currentPaddleLength" + ":" + getCurrentPaddleLength()+ "," +
            "currentPaddleX" + ":" + getCurrentPaddleX()+ "," +
            "currentPaddleY" + ":" + getCurrentPaddleY()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "player = "+(getPlayer()!=null?Integer.toHexString(System.identityHashCode(getPlayer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "bounce = "+(getBounce()!=null?Integer.toHexString(System.identityHashCode(getBounce())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "block223 = "+(getBlock223()!=null?Integer.toHexString(System.identityHashCode(getBlock223())):"null");
  }
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------

  // line 117 "../../../../../Block223Persistence.ump"
  private static final long serialVersionUID = 8597675110221231714L ;


}
