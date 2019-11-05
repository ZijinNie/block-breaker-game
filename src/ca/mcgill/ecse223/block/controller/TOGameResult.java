/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.controller;

// line 47 "../../../../../Block223TransferObjects.ump"
public class TOGameResult
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOGameResult Attributes
  private String name;
  private int score;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOGameResult(String aName, int aScore)
  {
    name = aName;
    score = aScore;
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

  public boolean setScore(int aScore)
  {
    boolean wasSet = false;
    score = aScore;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public int getScore()
  {
    return score;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "score" + ":" + getScore()+ "]";
  }
}