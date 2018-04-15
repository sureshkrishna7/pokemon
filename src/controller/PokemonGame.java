package controller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Battle;
import model.Door;
import model.Game;
import model.Pokemon;
import model.SafariEncounter;
import model.UsableItems.UsableItem;

//Simply Create the User and insert User into PokeTownMap, the rest of the maps will be embedded within PokeTownMap

public class PokemonGame {

  private static Scanner sc;
  private static Game theGame;
  private static Point playerStartLocation = new Point(11, 25);
  private static Point playerOldLocation = new Point();
  private static boolean foundPokemon;
  private static boolean wonBattle;
  private static final double encounterChance = 0.6;

  public static void main(String[] args) {
    theGame = new Game();
    foundPokemon = false;
    wonBattle = false;
    sc = new Scanner(System.in);
    char north = 'n';
    char south = 's';
    char west = 'w';
    char east = 'e';
    String direction = "n";
    char gameLogic = 0;
    theGame.setCurrCameraMap(theGame.getPokeTown());
    /*
     * if(direction.equals(""+north)) { System.out.print("It's True\n"); }
     */
    // scanner the next direction and act accordingly

    while (true) {
      int i = 0;
      int j = 0;

      while (i < 10) {
        j = 0;
        while (j < 10) {
          System.out.print(" " + theGame.getCamera()[i][j]);
          j++;
        }
        System.out.println();
        i++;
      }

      if (theGame.getCurrCameraMap() == theGame.getPokeTown().getSafariZoneMap())
        System.out.print("Move (n, e, s, w)? pt for PokemonTown!: ");
      else
        System.out.print("Move (n, e, s, w)? sz for Safari Zone!: ");

      //sc = new Scanner(System.in);
      if (sc.hasNext()) {
    	  direction = sc.nextLine().toLowerCase();
      }
      

      if (direction.equals("" + north)) {
        gameLogic = theGame.playerMove(north);
      } // hunter moved south
      else if (direction.equals("" + south)) {
        gameLogic = theGame.playerMove(south);
      } // hunter moved west
      else if (direction.equals("" + west)) {
        gameLogic = theGame.playerMove(west);
      } // hunter moved east
      else if (direction.equals("" + east)) {
        gameLogic = theGame.playerMove(east);
      } else if (direction.equals("sz") && !(theGame.getCurrCameraMap() == theGame.getPokeTown().getSafariZoneMap())) {
        theGame.setTrainerLocation(playerStartLocation);
        theGame.setCurrCameraMap(theGame.getPokeTown().getSafariZoneMap());
      } else if (direction.equals("pt") && theGame.getCurrCameraMap() == theGame.getPokeTown().getSafariZoneMap()) {
        theGame.setTrainerLocation(playerStartLocation);
        theGame.setCurrCameraMap(theGame.getPokeTown());
      }

      if (gameLogic == 'D') {

        System.out.print("Encountered a Door\n");
        playerOldLocation.x = theGame.getTrainerLocation().x;
        playerOldLocation.y = theGame.getTrainerLocation().y;

        Door door = (Door) theGame.getPokeTown().getItemsBoard()[theGame.getTrainerLocation().x][theGame
            .getTrainerLocation().y];

        theGame.setCurrCameraMap(door.getInsideMap());
        theGame.setTrainerLocation(door.getInsideMap().getPlayerLocation());

        theGame.getCamera();
      } else if (gameLogic == 'P') {
        System.out.print("Encountered a Pokemon\n");
      } else if (gameLogic == 'N') {
        System.out.print("Encountered a NPC\n");
      } else if (gameLogic == ' ') {
        theGame.setTrainerLocation(playerOldLocation);
        theGame.setCurrCameraMap(theGame.getPokeTown());
      } else if (gameLogic == 'S') {
        theGame.setTrainerLocation(playerStartLocation);
        theGame.setCurrCameraMap(theGame.getPokeTown().getSafariZoneMap());
      }
      // X represents Safari Pokemon for now, until Safari Map is created
      else if (gameLogic == 'X') {
        System.out.println("Encountered a Safari Pokemon");
        Pokemon b = new Pokemon("Sandslash", 3, 'C', 'I', null);
        
        SafariEncounter.safariEncounter(theGame.getTrainer(), b, sc);
      }
      // after exhausting 500 steps in Safari Zone, eject back to PokemonTown
      else if (theGame.getTrainer().getSafariSteps() >= 500) {
        theGame.setTrainerLocation(playerStartLocation);
        theGame.setCurrCameraMap(theGame.getPokeTown());
        
      // bush, check will battle at random, start battle with randomly instantiated Pokemon
      } else if (gameLogic == 'B') {
        foundPokemon = checkBush();
        if (foundPokemon) {
          Pokemon wildPoke = getWildPoke();
          wonBattle = Battle.battle(theGame.getTrainer(), wildPoke, sc);
        }
      }
    }
    // sc.close();
  }


  /*
   * getWildPoke() -- after checkBush() returns true (it found a Pokemon), this
   * method determines what Pokemon is being encountered. The level is determined
   * at random. The name, type and rareDegree will be pulled randomly from a list
   * in the Game class. Will always return a Pokemon.
   */
  private static Pokemon getWildPoke() {
    // this is a simple random generator, looks weird but works better than Random
    //double pivot = ThreadLocalRandom.current().nextDouble(0.1, 1.0);
    Random ran = new Random();
    
    int level = getRandomLevel();
    
    // will return the index for use in Pokemon name ArrayList
    int indexOfPoke = ran.nextInt(10);
    // get other fields from list in Game object
    String name = theGame.getPokemonNameList().get(indexOfPoke);
    char rarity = theGame.getAllPokemonList().get(name).charAt(0);
    char type = theGame.getAllPokemonList().get(name).charAt(1);
    
    // need to create list in Game with all of the special moves, same order as name list for correlation.
    return new Pokemon(name, level, rarity, type, null);
  }// end getWildPoke()

  /*
   * getRandomLevel() -- this method picks a pivot between 0.0 and 1.0 that will
   * determine the "level class" that will be chosen (so higher levels are
   * encountered less frequently). Inside each conditional statement, a random
   * number is chosen within the respective "level class" and is returned.
   */
  private static int getRandomLevel() {
    // this is a simple random generator, looks weird but works better than Random
    //double pivot = ThreadLocalRandom.current().nextDouble(0.1, 1.0);
    Random ran = new Random();
    double pivot = ran.nextDouble();
    
    // return level 16-20
    if(pivot > 0.90) {
      return ran.nextInt(5) + 16;
    }
    // return level 11-15
    else if(pivot > 0.70) {
      return ran.nextInt(5) + 11;
    }
    // return level 6-10
    else if(pivot > 0.40) {
      return ran.nextInt(5) + 6;
    }
    // return level 1-5
    else {
      return ran.nextInt(5) + 1;
    }
  }// end getRandomLevel()

  private static boolean checkBush() {
    // this is a simple random generator, looks weird but works better than Random
    //double pivot = ThreadLocalRandom.current().nextDouble(0.1, 1.0);
    Random ran = new Random();
    double pivot = ran.nextDouble();
    
    if (pivot > encounterChance) {
      return true;
    }
    return false;
  }// end checkBush()

  private static void getSafariStatSheet() {
    
    StringBuilder sb = new StringBuilder();
    Alert statSheet = new Alert(AlertType.INFORMATION);
    statSheet.setHeaderText("End of Safari Zone Quest");
    sb.append("Pokemon caught: \n");
    for(Pokemon p : theGame.getTrainer().getPokeList()) {
      if(!theGame.getTrainer().getBeforeSafariPokeList().contains(p)) {
        sb.append(p.getData());
      }
    }
    sb.append("\nSafari Inventory items used: \n");
    sb.append("\t       bait: " + (10 - theGame.getTrainer().getSafariInventory().get("bait").size()) + "\n");
    sb.append("\t       rock: " + (10 - theGame.getTrainer().getSafariInventory().get("rock").size()) + "\n");
    sb.append("\tsafari ball: " + (30 - theGame.getTrainer().getSafariInventory().get("safariball").size()) + "\n");
    
    sb.append("\nItems gained: \n");
    for (Map.Entry<String, ArrayList<UsableItem>> entry : theGame.getTrainer().getItemsGainedInSafariZone().entrySet()) {
      sb.append("\t" + entry.getKey() + ", " + entry.getValue().size());
      sb.append("\n");
    }
    sb.append("\nSteps taken: " + theGame.getTrainer().getSafariSteps() + "/500\n");
    
    statSheet.setContentText(sb.toString());
    
  }
  
  private static void getGameMenu() {
    StringBuilder sb = new StringBuilder();
    Alert gameMenu = new Alert(AlertType.INFORMATION);
    gameMenu.setHeaderText("Game Menu");
    sb.append("");
    gameMenu.setContentText(sb.toString());
  }
  
}