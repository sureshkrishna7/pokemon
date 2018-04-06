package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Observable;

/*The idea for this class is to allow Polymorphism of this class.
  This class will simply provide the DIMENSIONS and the Board 
  We can use "HouseMap extends Map" "SafariMap extends Map" ""PokeTown extends Map" etc.*/

public class Map{

  private Items[][] board;

  private char[][] characterBoard;

  //Crucial for walking of player to work
  private final int width = 50;
  private final int height = 50;

  private final int camWidth = 10;
  private final int camHeight = 10;
  private Items[][] cameraArray;    		// Camera Array relative to the position of the Player

  private ArrayList<Items> listOfItems; // list of items in the map
  private ArrayList<Items> listOfPokemon; // list of pokemon in the map

  private boolean didGameEnd;

  public Map() {

	 didGameEnd = false;
	 initializeBoard();
  }

  public void setPokemonTown() {
	 /*
	  * G for grass
	  * B for Bush (Pokemon can only be found here), So maintain a list of its position
	  * P for Pokemon
	  * O for Trainer
	  * T for Tree
	  * R for River
	  * H for House
	  *	 N for NPC 
	  *	 D for Door
	  */

	 characterBoard = new char[height][width];

	 for(int i = 0; i < width; i++) {
		for(int j = 0; j < height; j++) {
		  characterBoard[i][j] = 'G';
		}
	 }

	 /*
	  * (24,24) []     []
	  * []      []     []
	  * []      [D]    (26,26)
	  */
	 setHouse1(24,24);

	// setMall()

  }
  public Items[][] getBoard() {
	 return board;
  }

  public void initializeBoard() {

	 this.board = new Items[height][width];		//Access elements x,y style
	 this.cameraArray = new Items[camHeight][camWidth];
	 this.listOfItems = new ArrayList<Items>(5);
	 this.listOfItems = new ArrayList<Items>(5);
  }

  public boolean setCharacter(int x, int y, char z) {

	 if(characterBoard[x][y] == 'G' || characterBoard[x][y] == 'T') {
		characterBoard[x][y] = z;
		return true;
	 }
	 return false;
  }

  /*
   * (X,Y)   []     []
   * []      []     []
   * []      [D]    (X+2,Y+2)
   */
  public void setHouse1(int X, int Y) {

	 setCharacter(X,Y,'H');
	 setCharacter(X,Y+1,'H');
	 setCharacter(X,Y+2,'H');

	 setCharacter(X+1,Y,'H');
	 setCharacter(X+1,Y+1,'H');
	 setCharacter(X+1,Y+2,'H');

	 setCharacter(X+2,Y,'H');
	 setCharacter(X+2,Y+1,'D');  	//Door to the house
	 setCharacter(X+2,Y+2,'H');
  }

  public boolean isWalkable(int x, int y) {

	 // Checking if its a valid existing point
	 // If not then return false

	 // If its a valid range of access
	 if((x >= 0 && x < height) && (y>= 0 && y < width)) {
		if(board[x][y].isWalkable()) {
		  return true;
		}
	 }
	 return false;
  }

  public boolean isWalkable(Point pos) {

	 // Checking if its a valid existing point
	 // If not then return false

	 // If its a valid range of access
	 if((pos.x >= 0 && pos.x < height) && (pos.y>= 0 && pos.y < width)) {
		if(board[pos.x][pos.y].isWalkable()) {
		  return true;
		}
	 }
	 return false;
  }

}
