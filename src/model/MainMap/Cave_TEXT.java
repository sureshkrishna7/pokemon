package model.MainMap;


import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javafx.scene.image.Image;

public class Cave_TEXT extends TextFileReader{


  public Cave_TEXT() {
    initializeBoard();
    try {
      createMapGridFromTxtFile("src/cave.txt");
    } catch (FileNotFoundException e) {
      System.out.println("Cave text file isn't found");
    }
    mainMap = new Image("file:src/images/cave.png", false);
  }


}

