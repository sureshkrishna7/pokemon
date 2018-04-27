package model.MainMap;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.image.Image;

public class EnermyTown extends TextFileReader{

  public EnermyTown() {
    initializeBoard();
    try {
      createMapGridFromTxtFile("src/EnermyTown.txt");
    } catch (FileNotFoundException e) {
      System.out.println("EnermyTown's text file isn't found");
    }
    mainMap = new Image("file:src/images/EnermyTown.png", false);
    createDoorObjects();
    setMapPlayerPosition(12,22);
  }

  private void createDoorObjects() { 
    listOfDoors.add(new Door(11,10,"house_6.png","house_6.txt"));
    listOfDoors.add(new Door(11,23,"Mart.png","Mart.txt")); 
    listOfDoors.add(new Door(12,34,"Mart.png","Mart.txt"));
    //listOfDoors.add(new Door(12,34,"house_1.png","house_1.txt")); 
    listOfDoors.add(new Door(19,10,"house_1.png","house_1.txt"));
    listOfDoors.add(new Door(19,21,"Mart.png","Mart.txt")); 
  }

}
