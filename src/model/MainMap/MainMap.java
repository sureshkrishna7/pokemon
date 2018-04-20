package model.MainMap;

import java.awt.Point;

import javafx.scene.image.Image;

public interface MainMap {
  
 
  public Door enteredDoor(int x, int y);

  public int getMapWidth();
  public int getMapHeight();

  public boolean isWalkable(int x, int y);
  public char getCharacterFromLocation(int x, int y);

  public MainMap getMapObject();

  public Image getMapImage();

  public Point getMapPlayerPosition();

  public boolean isWalkable(Point newPoint);

  public char getCharacterFromLocation(Point newPoint);
  
}