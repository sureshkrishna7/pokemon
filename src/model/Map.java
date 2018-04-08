package model;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javafx.scene.shape.Line;

/*The idea for this class is to allow Polymorphism of this class.
  This class will simply provide the DIMENSIONS and the Board 
  We can use "HouseMap extends Map" "SafariMap extends Map" ""PokeTown extends Map" etc.*/

public class Map{

  private Items[][] board;
  private char[][] characterBoard;

  //Crucial for walking of player to work
  private int col;
  private int row;

  private ArrayList<Items> listOfItems; // list of items in the map
  private ArrayList<Pokemon> listOfPokemon; // list of pokemon in the map

  private boolean didGameEnd;

  /*
   * *********************************************************
   */
  public Map() {

	 didGameEnd = false;
	 initializeBoard();
  }

  public void initializeBoard() {

	 this.board = new Items[row][col];		//Access elements x,y style
	 this.characterBoard = new char[row][col];
	 this.listOfPokemon = new ArrayList<Pokemon>(5);
	 this.listOfItems = new ArrayList<Items>(5);
  }

  public int getMapWidth() {
	 return col;
  }

  public int getMapHeight() {
	 return row;
  }

  /*
   * *********************************************************
   */

  /*
   * *********************************************************
   */
  public void geniusMethod(String file) throws FileNotFoundException {

	 System.out.println(file);

	 int numberOfLines = 0;
	 int charInEachLine = 0;

	 Scanner sc = new Scanner(new File(file));

	 while(sc.hasNext()) {

		String line = sc.nextLine();
		charInEachLine = line.length();
		numberOfLines += 1;
	 }

	 this.row = numberOfLines;
	 this.col = charInEachLine;

	 this.board = new Items[row][col];		//Access elements x,y style
	 this.characterBoard = new char[row][col];

	 System.out.println("row "+row+", col "+col);

	 int i = 0;
	 int j = 0;

	 sc.close();
	 Scanner ac = new Scanner(new File(file));

	 while(ac.hasNext()) {

		String read = ac.nextLine();

		System.out.println(read);
		i = 0;
		while(i < read.length()) {
		  characterBoard[j][i] = read.charAt(i);
		  i++;
		}
		j++;
	 }

	 ac.close();

	 initializeMapObjects();
  }


  /*
   * *********************************************************
   */

  private void initializeMapObjects() throws FileNotFoundException {

	 Scanner rp = new Scanner(new File("src/PokemonNames.txt"));

	 int doorCount = 1;
	 int i = 0;
	 int j = 0;

	 while(i < row) {

		j = 0;
		while(j < col) {

		  if(characterBoard[i][j] == 'P') {
			 try{
				String line = rp.nextLine();
				String[] pokemonInitializer = new String[3];
				pokemonInitializer = line.split("\\s+");

				Pokemon currentPokemon = new Pokemon(pokemonInitializer[0], pokemonInitializer[1], pokemonInitializer[2]);
				//Location of the pokemon is important
				board[i][j] = (currentPokemon);
				currentPokemon.setLocation(i, j);
			 }
			 catch (NoSuchElementException e) {
				rp = new Scanner(new File("src/PokemonNames.txt"));
			 }
		  }
		  else if(characterBoard[i][j] == 'D') {

			 /*
			  * IF THERE IS MORE THAN 4 DOOR Files
			  * change the number '4' to something else
			  */
			 if(doorCount > 4) {
				doorCount = 1;
			 }
			 
			 String file = "src/Door"+doorCount+".txt";

			 Door currentDoor = new Door(i, j, file);
			 board[i][j] = currentDoor;
			 doorCount += 1;
		  }
		  else if(characterBoard[i][j] == 'N') {
			 
		  }
		  else {
			 
		  }
		  
		  j++;
		}
		i++;
	 }

  }

  /*
   * Get the character from the location the player moved
   * If we cannot access that point then return 0 (ZERO) 
   */
  public char getCharacterFromLocation(Point pos) {

	 if((pos.x >= 0 && pos.x < row) && (pos.y>= 0 && pos.y < col)) {
		return characterBoard[pos.x][pos.y];
	 }
	 return '0';
  }

  public char getCharacterFromLocation(int x, int y) {

	 if((x >= 0 && x < row) && (y>= 0 && y < col)) {
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
	 if((x >= 0 && x < row) && (y>= 0 && y < col)) {

		/*
		 * *************
		 * Note:
		 * 
		 * WE NEED TO INITIALIZE THE MAP AND RUN A LOOP CREATING ALL THE APPROPRIATE OBJECTS
		 * *************** COMMENTED OUT THIS SO THAT WE CAN TEST THIS ***************
		 * *************
		 */
		//if(board[x][y].isWalkable()) {

		if(characterBoard[x][y] == 'G' || characterBoard[x][y] == 'B' || characterBoard[x][y] == 'D' || characterBoard[x][y] == 'P' || characterBoard[x][y] == 'I') {
		  return true;
		}
	 }
	 return false;
  }

  public boolean isWalkable(Point pos) {

	 return isWalkable(pos.x, pos.y);
  }


}
