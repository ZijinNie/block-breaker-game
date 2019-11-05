/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.controller;

// line 37 "../../../../../Block223TransferObjects.ump"
public class TOSpecificGame
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOSpecificGame Attributes
  private String name;
  private int life;
  private int level;
  private double ballposX;
  private double ballposY;
  private double paddleposX;
  private int point;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOSpecificGame(String aName, int aLife, int aLevel, double aBallposX, double aBallposY, double aPaddleposX, int aPoint)
  {
    name = aName;
    life = aLife;
    level = aLevel;
    ballposX = aBallposX;
    ballposY = aBallposY;
    paddleposX = aPaddleposX;
    point = aPoint;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setLife(int aLife)
  {
    boolean wasSet = false;
    life = aLife;
    wasSet = true;
    return wasSet;
  }

  public boolean setLevel(int aLevel)
  {
    boolean wasSet = false;
    level = aLevel;
    wasSet = true;
    return wasSet;
  }

  public boolean setBallposX(double aBallposX)
  {
    boolean wasSet = false;
    ballposX = aBallposX;
    wasSet = true;
    return wasSet;
  }

  public boolean setBallposY(double aBallposY)
  {
    boolean wasSet = false;
    ballposY = aBallposY;
    wasSet = true;
    return wasSet;
  }

  public boolean setPaddleposX(double aPaddleposX)
  {
    boolean wasSet = false;
    paddleposX = aPaddleposX;
    wasSet = true;
    return wasSet;
  }

  public boolean setPoint(int aPoint)
  {
    boolean wasSet = false;
    point = aPoint;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public int getLife()
  {
    return life;
  }

  public int getLevel()
  {
    return level;
  }

  public double getBallposX()
  {
    return ballposX;
  }

  public double getBallposY()
  {
    return ballposY;
  }

  public double getPaddleposX()
  {
    return paddleposX;
  }

  public int getPoint()
  {
    return point;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "life" + ":" + getLife()+ "," +
            "level" + ":" + getLevel()+ "," +
            "ballposX" + ":" + getBallposX()+ "," +
            "ballposY" + ":" + getBallposY()+ "," +
            "paddleposX" + ":" + getPaddleposX()+ "," +
            "point" + ":" + getPoint()+ "]";
  }
}