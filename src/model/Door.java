package model;

import java.awt.Point;

public class Door extends Items {
  private String filepath;			//TODO: Need to add picture of door to filepath
  private Map nextMap;
  
  public Door(int x, int y) {
	 super('D', x, y);
	 //setImage(filepath);
	 this.nextMap = new Map();
  }
  
  public Door(Point pos) {
	 super('D', pos);
	 //setImage(filepath);
	 this.nextMap = new Map();
  }
  
  
}
