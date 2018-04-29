package model.MainMap;


import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javafx.scene.image.Image;

public class House2_TEXT extends TextFileReader{


  public House2_TEXT() {
    initializeBoard();
    try {
      createMapGridFromTxtFile("src/house_2.txt");
    } catch (FileNotFoundException e) {
      System.out.println("house_2 text file isn't found");
    }
    mainMap = new Image("file:src/images/house_2.png", false);
  }


}


