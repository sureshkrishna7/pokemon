package model.MainMap;


import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javafx.scene.image.Image;

public class ProfessorLab_TEXT extends TextFileReader{


  public ProfessorLab_TEXT() {
    initializeBoard();
    try {
      createMapGridFromTxtFile("src/professor_lab.txt");
    } catch (FileNotFoundException e) {
      System.out.println("professor_lab text file isn't found");
    }
    mainMap = new Image("file:src/images/professor_lab.png", false);
  }


}


