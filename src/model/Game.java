package model;

public class Game {

  private boolean didGameEnd;
  private boolean playerWon;
  
  private Map pokeTown;
  private Trainer Ash;
  
  private final int camWidth = 10;
  private final int camHeight = 10;
  private Items[][] cameraArray; 
  
  public Game() {
	 
	 didGameEnd = false;
	 playerWon = false;
	 
	 pokeTown = new Map();
	 pokeTown.setPokemonTown();
	 
	 Ash = new Trainer("Ash");
	 Ash.setLocation(27, 25);
	 
	 this.cameraArray = new Items[camHeight][camWidth];
  }
  
  
}
