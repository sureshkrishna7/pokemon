package model.MainMap;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import controller.GameBackground;
import controller.House1;
import controller.House6;
import controller.Mart;
import javafx.scene.image.Image;

public class Door implements MainMap{
//Because a Enum can have more than one door position, cases where there are two doors
  private ArrayList<Point> listOfDoorPositions;
  private Image insideMapImage;
  private MainMap insideMap;
  private char[][] insideBoard;
  private Point playerPositionInsideMap;
  private int row;
  private int col;
  private GameBackground gameBackground;

  // For House with one Door
  public Door(int posx, int posy, String img, String txt){
	 
    this.row = 0;
    this.col = 0;
    this.listOfDoorPositions = new ArrayList<Point>(0);
    this.listOfDoorPositions.add(new Point(posx,posy));
    insideMapImage = new Image("file:src/images/"+img, false);
    if (txt.equals("Mart.txt")) {
    	System.out.println("Creating Mart image!");
    	gameBackground = new Mart(new Point(9,19), insideMapImage);
    	//gameBackground = new Mart(new Point(10,5), new Image("file:src/images/house_6GRID.png", false));
    	insideMap = new MartCity();
    }else if(txt.equals("house_6.txt")){
    	System.out.println("Creating House 6 image!");
    	gameBackground = new House6(new Point(5, 10), insideMapImage);
    	//gameBackground = new Mart(new Point(10,5), new Image("file:src/images/house_6GRID.png", false));
    	insideMap = new House6_TEXT();
    }
    else if(txt.equals("house_1.txt")){
    	System.out.println("Creating House 1 image!");
    	gameBackground = new House1(new Point(6, 9), insideMapImage);
    	//gameBackground = new Mart(new Point(10,5), new Image("file:src/images/house_6GRID.png", false));
    	insideMap = new House1_TEXT();
    }
    else {
    
    	System.out.println("ANS -->" + txt);
    }
    try {
      createMapFromTxtFile("src/"+txt);
    } catch (FileNotFoundException e) {
      System.out.println("File: "+txt+" is not found.");
    }
  }
  
//For House with two Doors
  Door(int firx, int firy, int secx, int secy, String img, String txt){
    this.listOfDoorPositions = new ArrayList<Point>(0);
    this.listOfDoorPositions.add(new Point(firx,firy));
    this.listOfDoorPositions.add(new Point(secx,secy));
    insideMapImage = new Image("file:src/images/"+img, false);
    try {
      createMapFromTxtFile(txt);
    } catch (FileNotFoundException e) {
      System.out.println("File: "+txt+" is not found.");
    }
  }

  public MainMap getInsideMapObject() {
	  System.out.println("getting door map obj");
	  //setMapPlayerPosition(9, 19);
    //return this;
	  return insideMap;
  }
  
  public GameBackground getGameBackground() {
	  return gameBackground;
  }
//  public MainMap getGameBackground() {
//	  return gameBackground;
//  }
  public boolean setMapPlayerPosition(int x, int y) {
	    if(isWalkable(x, y)) {
	      //MainMapPlayerPos = new Point(x,y);
	    	playerPositionInsideMap = new Point(x,y);
	      return true;
	    }
	    return false;
  }
  
  public Point getMapPlayerPos() {
	  return playerPositionInsideMap;
  }

  public Image getInsideMapImage() {
	  System.out.println("getting door map");
    return insideMapImage;
  }

  public char[][] getInsideBoard(){
    return insideBoard;
  }

  public boolean doesDoorExist(int x, int y) {
    for(Point doorPosition: listOfDoorPositions) {
      if(doorPosition.x == x && doorPosition.y == y) {
        return true;
      }
    }
    return false;
  }

  public Point getPlayerPosition() {
    return playerPositionInsideMap;
  }

  private void createMapFromTxtFile(String file) throws FileNotFoundException {

    int numberOfLines = 0;
    int charInEachLine = 0;
    boolean start = true;

    Scanner sc = new Scanner(new File(file));

    while (sc.hasNext()) {

      String line = sc.nextLine();
      if (start)
        charInEachLine = line.length();
      start = false;

      numberOfLines += 1;
    }

    row = numberOfLines;
    col = charInEachLine;

    this.insideBoard = new char[row][col];

    int y = 0;
    int x = 0;

    sc.close();
    Scanner ac = new Scanner(new File(file));

    while (ac.hasNext()) {

      String read = ac.nextLine();

      System.out.println(read);
      y = 0;
      while (y < read.length()) {
        insideBoard[x][y] = read.charAt(y);

        if(insideBoard[x][y] == 'E') {
          playerPositionInsideMap = new Point(x,y);
        }

        y++;
      }
      x++;
    }

    ac.close();
  }

  public Door enteredDoor(int x, int y) {
    return this;
  }

  public int getMapWidth() {
    return col;
  }

  public int getMapHeight() {
    return row;
  }

  public boolean isWalkable(int x, int y) {

    // Checking if its a valid existing point
    if ((x >= 0 && x < row) && (y >= 0 && y < col)) {

      if (insideBoard[x][y] == 'G' || insideBoard[x][y] == 'B' || insideBoard[x][y] == 'D'
          || insideBoard[x][y] == 'S' || insideBoard[x][y] == '0' || insideBoard[x][y] == 'X' || insideBoard[x][y] == ' ') {
        return true;
      }
    }
    return false;
  }

  public boolean isWalkable(Point pos) {
    return isWalkable(pos.x, pos.y);
  }

  public char getCharacterFromLocation(int x, int y) {
    if ((x >= 0 && x < row) && (y >= 0 && y < col)) {
      return insideBoard[x][y];
    }
    return 'Z';
  }

  public MainMap getMapObject() {
    return this;
  }

  public Image getMapImage() {
    return insideMapImage;
  }

  public Point getMapPlayerPosition() {
    return playerPositionInsideMap;
  }

  public char getCharacterFromLocation(Point pos) {
    return getCharacterFromLocation(pos.x, pos.y);
  }
}