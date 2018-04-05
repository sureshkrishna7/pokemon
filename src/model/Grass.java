package model;

import java.awt.Point;

//Grass can contain pokemon

public class Grass extends Items {
	
	private String filepath;			//TODO: need to set this to the correct filepath that contains the image
	private Boolean containsPokemon;
	
	public Grass(int x, int y) {
		setLocation(x,y);			//sets location in Parent class "Items"
		setWalkable();				//Sets to walkable bc it is grass
		setType("grass");			
		setImage(filepath);
		this.containsPokemon = false;	//Initialized to not contain any pokemon
	}

	public Boolean isOccupied() {return containsPokemon;}
	
	public void setOccupied() {this.containsPokemon = true;}
	public void setVacant() {this.containsPokemon = false;}
}
