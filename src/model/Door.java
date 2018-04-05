package model;

import java.awt.Point;

public class Door extends Items {
	private Map nextMap;
	private String filepath;			//TODO: Need to add picture of door to filepath
	
	public Door(int xpos, int ypos, Map newMap) {
		setLocation(xpos, ypos);
		setImage(filepath);
		setWalkable();			//might need to change this later based on implementation
		setType("door");
		this.nextMap = newMap;
	}
}
