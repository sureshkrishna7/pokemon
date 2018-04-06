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
  public char playerMove(char direction) {

	 Point player = ash.getLocation();

	 if(direction == 'n' || direction == 'N') {

		player.x = player.x - 1;
		if(pokeTown.isWalkable(player)) {
		  ash.setLocation(player);
		  return pokeTown.getCharacterFromLocation(player);
		}
	 }
	 else if(direction == 's' || direction == 'S') {

		player.x = player.x + 1;
		if(pokeTown.isWalkable(player)) {
		  ash.setLocation(player);
		  return pokeTown.getCharacterFromLocation(player);
		}
	 }
	 else if(direction == 'e' || direction == 'E') {

		player.y = player.y + 1;
		if(pokeTown.isWalkable(player)) {
		  ash.setLocation(player);
		  return pokeTown.getCharacterFromLocation(player);
		}
	 }
	 else if(direction == 'w' || direction == 'W') {

		player.y = player.y - 1;
		if(pokeTown.isWalkable(player)) {
		  ash.setLocation(player);
		  return pokeTown.getCharacterFromLocation(player);
		}
	 }
	 return '0';
  }

}
