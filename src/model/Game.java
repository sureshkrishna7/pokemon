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

  private char[][] camera;
  
  public Game() {

	 didGameEnd = false;
	 playerWon = false;

	 pokeTown = new Map();
	 pokeTown.setPokemonTown();

	 ash = new Trainer(new String("Ash"));
	 ash.setLocation(27, 25);

	 this.cameraArray = new Items[camHeight][camWidth];
	 this.camera = new char[camHeight][camWidth];
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

  public char[][] getCamera(){
	 Point playerPos = ash.getLocation(); 
	 
	 playerPos.x = playerPos.x - 4;
	 
	 playerPos.y = playerPos.y - 4;
	 
	 int i = 0;
	 int j = 0;
	 
	 int x;
	 int y;
	 
	 while(i < camHeight) {
		j = 0;
		while(j < camWidth) {
		  
		  x = playerPos.x  + i;
		  y = playerPos.y  + j;
		  
		  if(x < 0 || x >= camHeight || y < 0 || y >= camWidth) {
			 camera[i][j] = 'B';
		  }
		  else {
			 camera[i][j] = pokeTown.getCharacterFromLocation(x,y);
		  }
		  j++;
		}
		i++;
	 }
	 
	 return camera; 
  }
  
}
