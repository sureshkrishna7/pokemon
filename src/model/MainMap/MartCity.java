package model.MainMap;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javafx.scene.image.Image;

public class MartCity extends TextFileReader{

  public MartCity() {
    initializeBoard();
    try {
      //createMapGridFromTxtFile("src/house_6.txt.txt");
    	createMapGridFromTxtFile("src/Mart.txt");
    } catch (FileNotFoundException e) {
      System.out.println("Mart text file isn't found");
    }
    mainMap = new Image("file:src/images/house_6GRID.png", false);
    //createDoorObjects();
    setMapPlayerPosition(9,19);
  }

}
