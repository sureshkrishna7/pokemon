package model;

import java.awt.Point;
import java.io.FileNotFoundException;

public class Door extends Items {
  private String filepath;			//TODO: Need to add picture of door to filepath
  private Map insideMap;
  
  public Door(int x, int y, String file) {
	 super('D', x, y);
	 //setImage(filepath);
	 this.insideMap = new Map();
	 try {
		insideMap.geniusMethod(file);
	 } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	 }
  }
  
  public Door(Point pos, String file) {
	 super('D', pos);
	 //setImage(filepath);
	 this.insideMap = new Map();
	 try {
		insideMap.geniusMethod(file);
	 } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	 }
  }
  
  
}
