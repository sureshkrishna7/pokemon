package model;

/*The idea for this class is to allow Polymorphism of this class.
  This class will simply provide the DIMENSIONS and the Board 
  We can use "HouseMap extends Map" "SafariMap extends Map" ""PokeTown extends Map" etc.*/

public abstract class Map {

  private Items[][] board;
  int width;
  int height;
  

  public Map() {
  }
  
  public Items[][] getBoard() {
	  return board;
  }
  
  public void initializeBoard(int w, int h) {
	  this.width = w;
	  this.height = h;
	  this.board = new Items[w][h];		//Access elements x,y style
  }
  
  public void add(int xpos, int ypos, Items newElement) {		//This fxn adds newElement to the board
	  this.board[xpos][ypos] = newElement;					
  }
  public void add(Items newElement) {						//overloading fxn for ease of implementation
	  this.board[newElement.getLocation().x][newElement.getLocation().y] = newElement;
  }
}
