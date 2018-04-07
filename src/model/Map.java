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

  /*
   * *********************************************************
   */
  public Map() {

	 didGameEnd = false;
	 initializeBoard();
  }

  public void initializeBoard() {

	 this.board = new Items[height][width];		//Access elements x,y style
	 this.cameraArray = new Items[camHeight][camWidth];
	 this.listOfItems = new ArrayList<Items>(5);
	 this.listOfItems = new ArrayList<Items>(5);
  }

  public int getMapWidth() {
	 return width;
  }

  public int getMapHeight() {
	 return height;
  }

  /*
   * *********************************************************
   */
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
	 setHouse1(12,12);
	 setHouse2(10,35);
	 setHouse3(24,24);

	 // setMall()

  }

  public boolean setCharacter(int x, int y, char z) {

	 if(characterBoard[x][y] == 'G' || characterBoard[x][y] == 'T') {
		characterBoard[x][y] = z;
		return true;
	 }
	 return false;
  }

  /*
   * (row,col)   []     []
   *     []      []     []
   *     []      [D]    (row+2,col+2)
   */
  public void setHouse1(int row, int col) {

	 setCharacter(row, col,'H');
	 setCharacter(row, col + 1,'H');
	 setCharacter(row, col + 2,'H');

	 setCharacter(row + 1, col,'H');
	 setCharacter(row + 1, col + 1,'H');
	 setCharacter(row + 1, col + 2,'H');

	 setCharacter(row + 2, col,'H');
	 setCharacter(row + 2, col + 1,'D');  	//Door to the house
	 setCharacter(row + 2, col + 2,'H');
  }
  
  public void setHouse2(int row, int col) {
	 setCharacter(row, col,'H');
	 setCharacter(row, col + 1,'H');
	 setCharacter(row, col + 2,'H');
	 setCharacter(row, col + 3,'H');
	 setCharacter(row, col + 4,'H');

	 setCharacter(row + 1, col,'H');
	 setCharacter(row + 1, col + 1,'H');
	 setCharacter(row + 1, col + 2,'H');
	 setCharacter(row + 1, col + 3,'H');
	 setCharacter(row + 1, col + 4,'H');
	 
	 
	 setCharacter(row + 2, col,'H');
	 setCharacter(row + 2, col + 1,'H');
	 setCharacter(row + 2, col + 2,'H');
	 setCharacter(row + 2, col + 3,'H');
	 setCharacter(row + 2, col + 4,'H');
	 
	 setCharacter(row + 3, col,'H');
	 setCharacter(row + 3, col + 1,'H');
	 setCharacter(row + 3, col + 2,'D');
	 setCharacter(row + 3, col + 3,'H');
	 setCharacter(row + 3, col + 4,'H');
	 
  }
  
  
  public void setHouse3(int row, int col) {
	 setCharacter(row, col,'H');
	 setCharacter(row, col + 1,'H');
	 setCharacter(row, col + 2,'H');
	 setCharacter(row, col + 3,'H');
	 setCharacter(row, col + 4,'H');
	 setCharacter(row, col + 5,'H');
	 setCharacter(row, col + 6,'H');

	 setCharacter(row + 1, col,'H');
	 setCharacter(row + 1, col + 1,'H');
	 setCharacter(row + 1, col + 2,'H');
	 setCharacter(row + 1, col + 3,'H');
	 setCharacter(row + 1, col + 4,'H');
	 setCharacter(row + 1, col + 5,'H');
	 setCharacter(row + 1, col + 6,'H');
	 
	 
	 setCharacter(row + 2, col,'H');
	 setCharacter(row + 2, col + 1,'H');
	 setCharacter(row + 2, col + 2,'H');
	 setCharacter(row + 2, col + 3,'H');
	 setCharacter(row + 2, col + 4,'H');
	 setCharacter(row + 2, col + 5,'H');
	 setCharacter(row + 2, col + 6,'H');
	 

	 setCharacter(row + 3, col + 2,'H');
	 setCharacter(row + 3, col + 3,'H');
	 setCharacter(row + 3, col + 4,'H');
	 
	 setCharacter(row + 4, col + 2,'H');
	 setCharacter(row + 4, col + 3,'D');
	 setCharacter(row + 4, col + 4,'H');
	 
  }

  /*
   * *********************************************************
   */

  /*
   * *********************************************************
   */

  /*
   * Get the character from the location the player moved
   * If we cannot access that point then return 0 (ZERO) 
   */
  public char getCharacterFromLocation(Point pos) {

	 if((pos.x >= 0 && pos.x < height) && (pos.y>= 0 && pos.y < width)) {
		return characterBoard[pos.x][pos.y];
	 }
	 return '0';
  }

  public char getCharacterFromLocation(int x, int y) {

	 if((x >= 0 && x < height) && (y>= 0 && y < width)) {
		return characterBoard[x][y];
	 }
	 return '0';
  }

  /*
   * *********************************************************
   */

  /*
   * *********************************************************
   */
  public boolean isWalkable(int x, int y) {

	 // Checking if its a valid existing point
	 // If not then return false

	 // If its a valid range of access
	 if((x >= 0 && x < height) && (y>= 0 && y < width)) {

		/*
		 * *************
		 * Note:
		 * 
		 * WE NEED TO INITIALIZE THE MAP AND RUN A LOOP CREATING ALL THE APPROPRIATE OBJECTS
		 * *************** COMMENTED OUT THIS SO THAT WE CAN TEST THIS ***************
		 * *************
		 */
		//if(board[x][y].isWalkable()) {

		if(characterBoard[x][y] == 'G' || characterBoard[x][y] == 'B' || characterBoard[x][y] == 'D' || characterBoard[x][y] == 'P') {
		  return true;
		}
	 }
	 return false;
  }

  public boolean isWalkable(Point pos) {

	  return isWalkable(pos.x, pos.y);
  }


}
