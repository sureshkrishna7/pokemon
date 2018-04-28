package model;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import model.MainMap.EnermyTown;
import model.MainMap.FryslaSafariZone;
import model.MainMap.LilyCoveCity;
import model.MainMap.MainMap;
import model.MainMap.MartCity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game {

  private boolean didGameEnd;
  private boolean playerWon;

  private MainMap currentMap;
  private static MainMap currentMapStatic = new EnermyTown();;

  private MainMap enermyTown;
  private MainMap mart;
  private MainMap lilyCoveCity;
  private MainMap fryslaSafariZone;
  private Trainer ash;
  
  private boolean isTrainerAlreadyOnDoor;
  private boolean areWeInSafariZone;
  private int totalSafariZoneSteps; //would be 500
  private int totalSafariZoneBalls;
  private int currentSafariSteps;   //at start of a safari Zone, would be 0
  
  
  private Map<String, String> allPokemonList;
  private ArrayList<String> pokemonNameList;


  public Game() {

    didGameEnd = false;
    playerWon = false;

    enermyTown = new EnermyTown();
    mart = new MartCity();
    lilyCoveCity = new LilyCoveCity();
    fryslaSafariZone = new FryslaSafariZone();
    initializePokeLists();
    
    currentMap = enermyTown;

    ash = new Trainer(new String("Ash"));
    int x = currentMap.getMapPlayerPosition().x;
    int y = currentMap.getMapPlayerPosition().y;
    ash.setLocation(x, y);
    isTrainerAlreadyOnDoor = false;
    areWeInSafariZone = false;
  }
  
  public static char getCharAtIndex(int row, int col) {
	  return currentMapStatic.getCharacterFromLocation(new Point (row,col));
  }
  
  public boolean getIsTrainerAlreadyOnDoor() {
	  return isTrainerAlreadyOnDoor;
  }

  /*
   * initializePokeLists() -- initialize the Map object for the list of Pokemon as a HashMap,
   * adds all the Pokemon with their names as keys and their rareDegree and types as values, contained
   * in a two character String where the first character is the rareDegree and the second is the type.
   * Also initializes and fills an ArrayList of all the names of the Pokemon.
   */
  private void initializePokeLists() {
    this.allPokemonList = new HashMap<>();
    this.pokemonNameList = new ArrayList<String>();

    this.pokemonNameList.add("Charmeleon");
    this.pokemonNameList.add("Charmander");
    this.pokemonNameList.add("Vulpix");
    this.pokemonNameList.add("Sandslash");
    this.pokemonNameList.add("Glaceon");
    this.pokemonNameList.add("Seadra");
    this.pokemonNameList.add("Starmie");
    this.pokemonNameList.add("Seaking");
    this.pokemonNameList.add("Cubone");
    this.pokemonNameList.add("Rhydon");

    this.allPokemonList.put("Charmeleon", "MF");
    this.allPokemonList.put("Charmander", "RF");
    this.allPokemonList.put("Vulpix", "RF");
    this.allPokemonList.put("Sandslash", "CI");
    this.allPokemonList.put("Glaceon", "MI");
    this.allPokemonList.put("Seadra", "CW");
    this.allPokemonList.put("Starmie", "MW");
    this.allPokemonList.put("Seaking", "RW");
    this.allPokemonList.put("Cubone", "CE");
    this.allPokemonList.put("Rhydon", "ME");
  }// end initializePokeLists() 
  
  public void setCurrCameraMap(MainMap map) {
    currentMap = map;
  }

  public void setTrainerLocation(Point point) {
    ash.setLocation(point);
  }

  // direction could be n,s,e,w or N, S, E, W
  public char playerMove(char direction) {

    Point player = ash.getLocation();
    Point newPoint = new Point();
    
    isTrainerAlreadyOnDoor = false;
    
    if(direction == 'n' || direction == 'N') {
      newPoint.x = player.x - 1;
      newPoint.y = player.y;

      if(currentMap.isWalkable(newPoint)) {
        ash.setLocation(newPoint);
        if(areWeInSafariZone) {
          currentSafariSteps++;
        }
        //System.out.println("Walkable test 1");
        return currentMap.getCharacterFromLocation(newPoint);
      }
      else if('D' == getCharAtIndex(ash.getLocation().x, ash.getLocation().y)) {
    	 System.out.println("Already on door!");
    	 isTrainerAlreadyOnDoor = true;
      }
    }
    else if(direction == 's' || direction == 'S') {
      newPoint.x = player.x + 1;
      newPoint.y = player.y;

      if(currentMap.isWalkable(newPoint)) {
        ash.setLocation(newPoint);
        if(areWeInSafariZone) {
          currentSafariSteps++;
        }
        //System.out.println("Walkable test 2");
        return currentMap.getCharacterFromLocation(newPoint);
      }
    }
    else if(direction == 'e' || direction == 'E') {
      newPoint.x = player.x;
      newPoint.y = player.y + 1;

      if(currentMap.isWalkable(newPoint)) {
        ash.setLocation(newPoint);
        if(areWeInSafariZone) {
          currentSafariSteps++;
        }
        //System.out.println("Walkable test 3");
        return currentMap.getCharacterFromLocation(newPoint);
      }
    }
    else if(direction == 'w' || direction == 'W') {
      newPoint.x = player.x;
      newPoint.y = player.y - 1;

      if(currentMap.isWalkable(newPoint)) {
        ash.setLocation(newPoint);
        if(areWeInSafariZone) {
          currentSafariSteps++;
        }
        //System.out.println("Walkable test 4");
        return currentMap.getCharacterFromLocation(newPoint);
      }
    }
    return 'Z';
  }

  public Point getTrainerLocation() {
    return ash.getLocation();
  }

  public MainMap getFryslaSafariZone() {
    return fryslaSafariZone;
  }
  
  public MainMap getLilyCoveCity() {
    return lilyCoveCity;
  }
  
  public MainMap getEnermyTown() {
    return enermyTown;
  }

  public Trainer getTrainer() {
    return ash;
  }

  public MainMap getCurrCameraMap() {
    return currentMap;
  }

  public Map<String, String> getAllPokemonList() {
    return allPokemonList;
  }

  public ArrayList<String> getPokemonNameList(){
    return pokemonNameList;
  }
  
  public void weAreInSafariZone() {
    areWeInSafariZone = true;
    totalSafariZoneSteps = ash.getAllowedSafariSteps();
    totalSafariZoneBalls = ash.getAllowedCurrentBalls();
    currentSafariSteps = 0;
  }
  
  public void weAreOutSafariZone() {
    areWeInSafariZone = false;
    currentSafariSteps = 0;
  }
  
  public boolean haveExhaustedSafariZone() {
    return currentSafariSteps >= 500;
  }

}
