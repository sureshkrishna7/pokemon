package model.MainMap;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.image.Image;

public class LilyCoveCity_TEXT implements MainMap{

  private char[][] characterBoard;
  private Point MainMapPlayerPos;
  private ArrayList<Door> listOfDoors;

  private int col;
  private int row;
  private Image mainMap;

  public LilyCoveCity_TEXT() {
    initializeBoard();
    try {
      createMapGridFromTxtFile("src/LilyCoveCity.txt");
    } catch (FileNotFoundException e) {
      System.out.println("LilyCoveCity's text file isn't found");
    }
    mainMap = new Image("file:src/images/LilyCoveCity.png", false);
    createDoorObjects();
    //setMapPlayerPosition(22,38);
  }

  private void createDoorObjects() { 
    listOfDoors.add(new Door(7,96,"Mart.png","Mart.txt"));
    listOfDoors.add(new Door(10,16,10,17, "Mart.png", "Mart.txt"));

    listOfDoors.add(new Door(12,37,"Mart.png","Mart.txt")); 
    listOfDoors.add(new Door(12,51,"Mart.png","Mart.txt")); 
    listOfDoors.add(new Door(12,59,"Mart.png","Mart.txt")); 
    listOfDoors.add(new Door(19,19,"Mart.png","Mart.txt")); 
    listOfDoors.add(new Door(19,54,"Mart.png","Mart.txt"));
    listOfDoors.add(new Door(21,75,"Mart.png","Mart.txt")); 
    listOfDoors.add(new Door(22,30,"Mart.png","Mart.txt")); 
    listOfDoors.add(new Door(29,19,"Mart.png","Mart.txt")); 
    listOfDoors.add(new Door(32,51,"Mart.png","Mart.txt")); 
    listOfDoors.add(new Door(33,28,33,29, "Mart.png","Mart.txt")); 
    listOfDoors.add(new Door(46,23,"Mart.png","Mart.txt"));
  }

  /*
   * ********************************IMPORTANT****************************************
   * THESE 4 METHODS are what you would use to CHANGE FROM ONE MAP TO ANOTHER, ALSO
   * 
   * WHEN YOU ENTER THE DOOR, PASS DOWN THAT LOCATION OF THE DOOR
   * 
   * ********************************IMPORTANT****************************************
   */
  public MainMap getMapObject() {
    return this;
  }

  public Image getMapImage() {
    return mainMap;
  }

  public char[][] getMapBoard(){
    return characterBoard;
  }

  public boolean setMapPlayerPosition(int x, int y) {
    if(isWalkable(x, y)) {
      MainMapPlayerPos = new Point(x,y);
      return true;
    }
    return false;
  }

  public Point getMapPlayerPosition() {
    return MainMapPlayerPos;
  }

  public Door enteredDoor(int x, int y) {
    for (Door yo: listOfDoors) {
      if(yo.doesDoorExist(x, y)) {
        return yo;
      }
    }
    return null;
  }

  /*
   * ********************************************************************************
   */

  private void createMapGridFromTxtFile(String file) throws FileNotFoundException {
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

    this.characterBoard = new char[row][col];
    System.out.println("row " + row + ", col " + col);

    int i = 0;
    int j = 0;

    sc.close();
    Scanner ac = new Scanner(new File(file));

    MainMapPlayerPos = new Point();
    while (ac.hasNext()) {

      String read = ac.nextLine();

      System.out.println(read);
      i = 0;
      while (i < read.length()) {
        characterBoard[j][i] = read.charAt(i);
        if (characterBoard[j][i] == 'E') {
        	System.out.println("******************                    EXIT ROW = " + j);
        	System.out.println("******************                    EXIT COL = " + i + "\n\n");
        }
        i++;
      }
      j++;
    }

    ac.close();
  }

  private void initializeBoard() {
    row = 0;
    col = 0;
    this.characterBoard = new char[row][col];
    this.listOfDoors = new ArrayList<Door>(0);
  }

  public int getMapWidth() {
    return col;
  }

  public int getMapHeight() {
    return row;
  }

  /*
   * ********LEGEND************LEGEND*************LEGEND*********LEGEND************LEGEND*************LEGEND***************
   * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   * 1) Walkable is represented by "+" (PLUS)
   * 2) Not Walkable then it's a "-" (MINUS)
   * 3) 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 represent elevation in the Map 
   * 4) 0 (ZERO) is always walkable
   * 5) X is a place where there exists a "GHOST IMAGE" of the Trainer
   * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   * 
   * T - Tree                             / Animate them, Hush Sound, Wind Blow, Leaves fall
   * W - Water                            / Moves, Splash
   * G + PlainGround or Grass             / Dirt Animation, Diamond Pokemon Game
   * B + Bush or PokemonEncounter Logic   / Battle View
   * R - Rock                             / Just a Rock
   * H - House Component
   * D + Door                             / Can switch to DIFFERENT maps, can also jump to different spot in the SAME map
   * 0 + Elevation or Ground Level        / ZERO is always walkable
   * X + GHOST IMAGE                      / When PLAYER walks under a bridge or behind a House
   * 1,2,3,4,5,6,7,8,9 - Elevation        / Used it to describe elevation in a Map, ALWAYS Unwalkable
   * 
   * Dynamic Position
   * ~~~~~~~~~~~~~~~~~
   * N - NPC                              / Dialogues, Battle, Win Money from
   * O - Trainer                          / Our Main Player, ASH
   * U - Items                            / Get from NPC/SHOP or Placed dynamically on the Map
   * **********************************************************************************************************************
   */
  public boolean isWalkable(int x, int y) {

    // Checking if its a valid existing point
    if ((x >= 0 && x < row) && (y >= 0 && y < col)) {
        if (characterBoard[x][y] == 'G' || characterBoard[x][y] == 'B' || characterBoard[x][y] == 'S' || characterBoard[x][y] == '0' || characterBoard[x][y] == 'X' || characterBoard[x][y] == ' ') {
              return true;
            }
        
        // NOTE: doors not implemented yet, nor exits
//      if (characterBoard[x][y] == 'G' || characterBoard[x][y] == 'B' || characterBoard[x][y] == 'D'
//          || characterBoard[x][y] == 'S' || characterBoard[x][y] == '0' || characterBoard[x][y] == 'X' || characterBoard[x][y] == ' ') {
//        return true;
//      }
    }
    return false;
  }
  
  public boolean isWalkable(Point pos) {
    return isWalkable(pos.x, pos.y);
  }

  public char getCharacterFromLocation(int x, int y) {
    if ((x >= 0 && x < row) && (y >= 0 && y < col)) {
      return characterBoard[x][y];
    }
    return 'Z';
  }
  
  public char getCharacterFromLocation(Point pos) {
    return getCharacterFromLocation(pos.x, pos.y);
  }

}
