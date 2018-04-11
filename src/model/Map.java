package model;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

import model.UsableItems.FullTonic;
import model.UsableItems.MidTonic;
import model.UsableItems.Tonic;

/*The idea for this class is to allow Polymorphism of this class.
  This class will simply provide the DIMENSIONS and the Board 
  We can use "HouseMap extends Map" "SafariMap extends Map" ""PokeTown extends Map" etc.*/

public class Map {

  private Items[][] board;
  private char[][] characterBoard;
  private Point currentPlayerPos;
  private Map safariZone;

  // ten entries for usable items
  private String[] usableItemCode = { "t", "mt", "ft", "e", "me", "fe", "el", "mel", "rev", "heal"};

  // Crucial for walking of player to work
  private int col;
  private int row;
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

    this.board = new Items[row][col]; // Access elements x,y style
    this.characterBoard = new char[row][col];
    this.listOfPokemon = new ArrayList<Pokemon>(5);
  }

  public int getMapWidth() {
    return col;
  }

  public int getMapHeight() {
    return row;
  }

  public Items[][] getItemsBoard() {
    return board;
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
    boolean start = true;

    Scanner sc = new Scanner(new File(file));

    while (sc.hasNext()) {

      String line = sc.nextLine();
      // System.out.println("line: " + line.length());
      if (start)
        charInEachLine = line.length();
      start = false;

      numberOfLines += 1;
    }

    this.row = numberOfLines;
    this.col = charInEachLine;

    this.board = new Items[row][col]; // Access elements x,y style
    this.characterBoard = new char[row][col];

    System.out.println("row " + row + ", col " + col);

    int i = 0;
    int j = 0;

    sc.close();
    Scanner ac = new Scanner(new File(file));

    currentPlayerPos = new Point();
    while (ac.hasNext()) {

      String read = ac.nextLine();

      System.out.println(read);
      i = 0;
      while (i < read.length()) {
        characterBoard[j][i] = read.charAt(i);

        if (characterBoard[j][i] == ' ') {
          currentPlayerPos.x = j;
          currentPlayerPos.y = i;
        }

        i++;

      }
      j++;
    }

    ac.close();

    initializeMapObjects();
  }

  public Point getPlayerLocation() {
    return currentPlayerPos;
  }

  /*
   * *********************************************************
   */

  private void initializeMapObjects() throws FileNotFoundException {

    Scanner rp = new Scanner(new File("src/PokemonNames.txt"));
    Scanner rui = new Scanner(new File("src/UsableItems.txt"));

    int doorCount = 1;
    int i = 0;
    int j = 0;

    while (i < row) {

      j = 0;
      while (j < col) {

        /*
        if (characterBoard[i][j] == 'P') {
          try {
            String line = rp.nextLine();
            String[] pokemonInitializer = new String[3];
            pokemonInitializer = line.split("\\s+");

            Pokemon currentPokemon = new Pokemon(pokemonInitializer[0], pokemonInitializer[1], pokemonInitializer[2]);
            // Location of the pokemon is important
            board[i][j] = (currentPokemon);
            currentPokemon.setLocation(i, j);
          } catch (NoSuchElementException e) {
            rp = new Scanner(new File("src/PokemonNames.txt"));
          }
        }  
        */  
        
        if (characterBoard[i][j] == 'D') {

          /*
           * IF THERE IS MORE THAN 4 DOOR Files change the number '4' to something else
           */
          if (doorCount > 4) {
            doorCount = 1;
          }

          String file = "src/Door" + doorCount + ".txt";

          Door currentDoor = new Door(i, j, file);
          // Map doorMap = new Map();
          board[i][j] = currentDoor;
          doorCount += 1;
        } else if (characterBoard[i][j] == 'S') {
          String file = "src/SafariZone.txt";
          safariZone = new Map();
          // safariZone.get

        } else if (characterBoard[i][j] == 'N') {

        } else if (characterBoard[i][j] == 'U') {
          // here we find a random index in the list of identifiers for usable items
          Random ran = new Random();
          int index = ran.nextInt(10);
          String id = usableItemCode[index];
          // this method is called which creates the correct object due to the identifier
          // and returns it to be
          // placed on the board.
          board[i][j] = placeUsableItem(id, i, j);
        }
        j++;
      }
      i++;
    }

  }

  // "t", "mt", "ft", "e", "me", "fe", "el", "mel", "rev", "heal"
  private Items placeUsableItem(String id, int i, int j) {
	  switch(id) {
	  case "t":
		Tonic t = new Tonic('U', i, j);
	    return t;
	  case "mt":
	    MidTonic mt = new MidTonic('U', i, j);
	    return mt;
	  case "ft":
	    FullTonic ft = new FullTonic('U', i, j);
	    return ft;
	  default:
	    return null;
	  }
  }

  public Map getSafariZoneMap() {
    return safariZone;
  }

  /*
   * Get the character from the location the player moved If we cannot access that
   * point then return 0 (ZERO)
   */
  public char getCharacterFromLocation(Point pos) {

    if ((pos.x >= 0 && pos.x < row) && (pos.y >= 0 && pos.y < col)) {
      return characterBoard[pos.x][pos.y];
    }
    return '0';
  }

  public char getCharacterFromLocation(int x, int y) {

    if ((x >= 0 && x < row) && (y >= 0 && y < col)) {
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
    if ((x >= 0 && x < row) && (y >= 0 && y < col)) {

      /*
       * ************* Note:
       * 
       * WE NEED TO INITIALIZE THE MAP AND RUN A LOOP CREATING ALL THE APPROPRIATE
       * OBJECTS *************** COMMENTED OUT THIS SO THAT WE CAN TEST THIS
       * *************** *************
       */
      // if(board[x][y].isWalkable()) {

      if (characterBoard[x][y] == 'U' || characterBoard[x][y] == 'G' || characterBoard[x][y] == 'B'
          || characterBoard[x][y] == 'D' || characterBoard[x][y] == 'P' || characterBoard[x][y] == 'I'
          || characterBoard[x][y] == 'S' || characterBoard[x][y] == 'X' || characterBoard[x][y] == ' ') {
        return true;
      }
    }
    return false;
  }

  public boolean isWalkable(Point pos) {

    return isWalkable(pos.x, pos.y);
  }

}
