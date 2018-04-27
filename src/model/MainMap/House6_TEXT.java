package model.MainMap;


import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javafx.scene.image.Image;

public class House6_TEXT extends TextFileReader{

  public House6_TEXT() {
    initializeBoard();
    try {
      createMapGridFromTxtFile("src/house_6.txt");
    } catch (FileNotFoundException e) {
      System.out.println("house_6 text file isn't found");
    }
    mainMap = new Image("file:src/images/house_6GRID.png", false);
    setMapPlayerPosition(9,19);
  }

}

