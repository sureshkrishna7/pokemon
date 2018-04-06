package model;

import java.awt.Point;

public class Game {

  private boolean didGameEnd;
  private boolean playerWon;

  private Map pokeTown;
  private Trainer ash;

  private final int camWidth = 10;
  private final int camHeight = 10;
  private Items[][] cameraArray; 

  public Game() {

	 didGameEnd = false;
	 playerWon = false;

	 pokeTown = new Map();
	 pokeTown.setPokemonTown();

	 ash = new Trainer("Ash");
	 ash.setLocation(27, 25);

	 this.cameraArray = new Items[camHeight][camWidth];
  }

  // direction could be n,s,e,w or N, S, E, W
  public boolean playerMove(char direction) {

	 Point player = ash.getLocation();
	 
	 if(direction == 'n' || direction == 'N') {

		if(pokeTown[][])
		return true;
	 }
	 else if(direction == 's' || direction == 'S') {

		return true;
	 }
	 else if(direction == 'e' || direction == 'E') {

		return true;
	 }
	 else if(direction == 'w' || direction == 'W') {

		return true;
	 }
	 
	 return false;
  }

}
