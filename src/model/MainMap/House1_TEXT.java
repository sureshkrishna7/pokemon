package model.MainMap;


import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javafx.scene.image.Image;

public class House1_TEXT extends TextFileReader{


  public House1_TEXT() {
    initializeBoard();
    try {
      createMapGridFromTxtFile("src/house_1.txt");
    } catch (FileNotFoundException e) {
      System.out.println("house_1 text file isn't found");
    }
    mainMap = new Image("file:src/images/house_1.png", false);
  }


}

