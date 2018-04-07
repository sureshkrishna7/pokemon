package model;

import java.awt.Point;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Items {

  // Have door class implement Items
  private char type;
  private Image picture;
  private boolean walkable = false;		//set this to FALSE because most items are NOT walkable (MUST SET TRUE FOR WALKABLE)
  private Point location;

  /*
   * NEED to chnage the constructor so that it takes at most no parameters
   * This is were dynamic initialization shines
   */


  /*
   * G for grass
   * B for Bush (Pokemon can only be found here), So maintain a list of its position
   * P for Pokemon
   * O for Trainer
   * T for Tree
   * R for River
   * H for House
   * N for NPC 
   * D for Door
   */
  public Items(char item) {
	 location = new Point();
	 type = item;

	 if(type == 'G' || type == 'B' || type == 'D') {
		setWalkable();
	 }
  }


  public Items(char item, int xpos, int ypos) {
	 location = new Point();
	 type = item;

	 if(type == 'G' || type == 'B' || type == 'D') {
		setWalkable();
	 }
	 setLocation(xpos, ypos);
  }

  public Items(char item, Point pos) {
	 location = new Point();
	 type = item;

	 if(type == 'G' || type == 'B' || type == 'D') {
		setWalkable();
	 }
	 setLocation(pos);
  }

  public void setLocation(int xpos, int ypos) {		//Location setters and getters
	 this.location.x = xpos;
	 this.location.y = ypos;
  }

  public void setLocation(Point newLocation) {		//Method overloading for ease of implementation
	 this.location = newLocation;
  }

  public Point getLocation() {
	 return location;
  }

  public void setImage(String filepath) {			//Sets the image according to "filepath" (file:images/newImage.png)
	 this.picture = new Image(filepath, 20, 20, false, false);		//Sets image to 20X20 size (can change later)
  }

  public Image getImage() {
	 return picture;
  }

  public Boolean isWalkable() {			//Walkable getters and setters
	 return walkable;
  }

  public void setWalkable() {				
	 this.walkable = true;
  }

  public char getType( ) {			//Type getters and setters
	 return type;
  }

  public void setType(char newType) {
	 this.type = newType;
  }

  public void draw(GraphicsContext gc)		//this should allow us to easily draw all objects regardless of type
  {
	 gc.drawImage(picture, 20*location.getX(), 20*location.getY());
  }
}
