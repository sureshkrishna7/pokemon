package controller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observer;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.stage.Stage;
import model.Battle;
import model.Game;
import model.Pokemon;
import model.SafariEncounter;
import model.MainMap.Door;
import model.MainMap.MainMap;
import model.UsableItems.UsableItem;

//Simply Create the User and insert User into PokeTownMap, the rest of the maps will be embedded within PokeTownMap

public class PokemonGame extends Application {

  private static Scanner sc;
  private static Game theGame;
  private static Point playerStartLocation = new Point();
  private static Point playerOldLocation = new Point();
  private static MainMap oldCurrentMap;

  private static boolean foundPokemon;
  private static boolean wonBattle;
  private static final double encounterChance = 0.6;
  private static CobvilleTown cobvilleTown, town;
  private static BorderPane pane;
  private static char gameLogic;
  private static Observer currentView, imageView, textAreaView;

  /*
   * This is kinda wonky right now, just using it to test the Alert for the Safari
   * Stats. As of now it's skeletal as SafariZone hasn't been coded. As it works
   * now, it calls start with the launch(args) call in main and shows the alert,
   * then returns to main for the system test.
   * 
   * I believe this Class should be transformed into the GUI, instead of having
   * the GameGUI class, as we can't have a GUI class with a main that calls start
   * and use another class that runs main.
   */

  public static void main(String[] args) {
    launch(args);
  }


  private static void initializeGameForFirstTime() {
    theGame = new Game();

    playerStartLocation.x = theGame.getTrainerLocation().x;
    playerStartLocation.y = theGame.getTrainerLocation().y;

    playerOldLocation.x = theGame.getTrainerLocation().x;
    playerOldLocation.y = theGame.getTrainerLocation().y;

    oldCurrentMap = theGame.getCurrCameraMap();

    foundPokemon = false;
    wonBattle = false;
  }


  @Override
  public void start(Stage stage) throws Exception {
    
    
    initializeGameForFirstTime();
    getGameMenu();
    getSafariStatSheet();

    pane = new BorderPane();
    cobvilleTown = new CobvilleTown(theGame.getTrainerLocation(), theGame.getCurrCameraMap().getMapImage());
    pane.setCenter(cobvilleTown);
    Scene scene = new Scene(pane, cobvilleTown.getCameraViewWidth(), cobvilleTown.getCameraViewHeight());
    scene.setOnKeyReleased(new AnimateStarter());
    stage.setScene(scene);
    stage.show();
  }

  // Add a listener that will start the Timeline's animation 
  public class StartTimerButtonListener implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
      cobvilleTown.animate();
    }
  }

  // Add a listener that will start the Timeline's animation 
  private class AnimateStarter implements EventHandler<KeyEvent> {
    @Override
    public void handle(KeyEvent event) {
      System.out.println("Animate Starter in PokemonGame.java line 115");

      /**
       * NOTE: If user inputs moves too fast, the player will move 
       * on the grid faster than the animation timeline can draw the image, 
       * and will crash (runs into things on grid before image).
       * So if animation is on, ignore button clicked
       */
      if (((GameBackground) pane.getCenter()).isTimelineAnimating()) {
    	  return;
      }

      
      
      char newLocationObject = 'Z';
      if (KeyCode.UP == event.getCode()) {
    	  System.out.println("Test1");
          newLocationObject = theGame.playerMove('n');
        }
      else if (KeyCode.UP == event.getCode()) {
    	  System.out.println("Test2");
        newLocationObject = theGame.playerMove('n');
      }
      else if (KeyCode.DOWN == event.getCode()) {
    	  System.out.println("Test3");
        newLocationObject = theGame.playerMove('s');
      }
      else if (KeyCode.LEFT == event.getCode()) {
    	  System.out.println("Test4");
        newLocationObject = theGame.playerMove('w');
      }
      else if (KeyCode.RIGHT == event.getCode()) {
    	  System.out.println("Test5");
        newLocationObject = theGame.playerMove('e');
      }
      else if (KeyCode.S == event.getCode() && theGame.getCurrCameraMap() != theGame.getFryslaSafariZone()) {
    	  System.out.println("Test6");
    	  playerOldLocation = theGame.getTrainerLocation();
    	  oldCurrentMap = theGame.getCurrCameraMap();
    	  theGame.setTrainerLocation(theGame.getFryslaSafariZone().getMapPlayerPosition());
    	  theGame.setCurrCameraMap(theGame.getFryslaSafariZone());
          theGame.weAreInSafariZone();
      } else if (KeyCode.P == event.getCode() && theGame.getCurrCameraMap() == theGame.getFryslaSafariZone()) {
    	  System.out.println("Test7");
    	  theGame.setTrainerLocation(playerOldLocation);
    	  theGame.setCurrCameraMap(oldCurrentMap);
    	  theGame.weAreOutSafariZone();
      }else {
    	  //System.out.println("---KeyCode ------  " +  event.getCode());
      }

      System.out.println("Game logic = " + newLocationObject);
      
      System.out.println("Old location = " + theGame.getTrainerLocation());
      
      if (newLocationObject == 'D') {
        System.out.print("Encountered a Door\n");

        playerOldLocation.x = theGame.getTrainerLocation().x;
        playerOldLocation.y = theGame.getTrainerLocation().y;
        oldCurrentMap = theGame.getCurrCameraMap();
//    	if (KeyCode.DOWN == event.getCode())
//    		  playerOldLocation.x += 1;
        System.out.println("REAL Old location = " + playerOldLocation);
        Door door = (Door) theGame.getCurrCameraMap().enteredDoor(theGame.getTrainerLocation().x, theGame.getTrainerLocation().y);

        /* 
         * ****We would be in safari Zone if the door is null****
         * ****Because we magically hop to different places****
         */
        if(door == null) {
        	System.out.println("door == null" );
          theGame.setTrainerLocation(theGame.getCurrCameraMap().getMapPlayerPosition());
        }
        else {
          //theGame.setCurrCameraMap(door.getInsideMapObject());
          // ********* START: ********
          // pane.setCenter instead?
          //theGame.setTrainerLocation(door.getMapPlayerPos());
          System.out.println("Old location = " + theGame.getTrainerLocation());
          System.out.println("New location = " + door.getMapPlayerPos());
          theGame.setTrainerLocation(door.getMapPlayerPos());
          theGame.setCurrCameraMap(door.getInsideMapObject());
          System.out.println(door.getInsideMapObject().getMapHeight());
          System.out.println(door.getInsideMapObject().getMapWidth());
          pane.setCenter(door.getGameBackground());
          System.out.println(door.getGameBackground());
          drawGameBackground((GameBackground)pane.getCenter(), event, newLocationObject);
          return;
        }
      } else if (newLocationObject == 'E') {
    	  System.out.println("Pokemon(201) Setting to old Map:");
    	  System.out.println("setting to --> " + playerOldLocation);
//    	  if (KeyCode.DOWN == event.getCode())
//    		  playerOldLocation.x 
        theGame.setTrainerLocation(playerOldLocation);
        theGame.setCurrCameraMap(oldCurrentMap);
        
        // adjust animation after exiting house 
        cobvilleTown.setDy(cobvilleTown.getDy() - 16);
        pane.setCenter(cobvilleTown);
      } 
      else if (gameLogic == 'S') {
        playerOldLocation = theGame.getTrainerLocation();
        oldCurrentMap = theGame.getCurrCameraMap();
        theGame.setTrainerLocation(theGame.getFryslaSafariZone().getMapPlayerPosition());
        theGame.setCurrCameraMap(theGame.getFryslaSafariZone());
        theGame.weAreInSafariZone();
      }
      // after exhausting 500 steps in Safari Zone, eject back to PokemonTown
      else if (theGame.haveExhaustedSafariZone()) {
        theGame.setTrainerLocation(playerOldLocation);
        theGame.setCurrCameraMap(oldCurrentMap);
        theGame.weAreOutSafariZone();

        // bush, check will battle at random, start battle with randomly instantiated
        // Pokemon
      } else if (gameLogic == 'B') {
        foundPokemon = checkBush();
        if (foundPokemon) {
          Pokemon wildPoke = getWildPoke();
          wonBattle = Battle.battle(theGame.getTrainer(), wildPoke, sc);
        }
        else if (gameLogic == 'N') {
          System.out.print("Encountered a NPC\n");
        } 
      }
      
//      else {
//    	  System.out.println(gameLogic == 'E');
//    	  System.out.println((int)gameLogic);
//    	  System.out.println((int) ' ');
//      }
      
      // cast is a promise to compiler that pane's center node is a gameBackground obj
      drawGameBackground((GameBackground)pane.getCenter(), event, newLocationObject);
      

    }
    
    public void drawWithCanvas(Door door, KeyEvent event, char newLocationObject) {
    	
    }

	public void drawGameBackground(GameBackground gameBackground, KeyEvent event, char newLocationObject) {
		// z is a char returned by theGame.playerMove() that's not used in map 
		// to represent an obj, thus can be used to detect null 
		if ((!(newLocationObject == 'Z')) && (!(newLocationObject == 'X'))) {  
			// We need to setBackGroundImage() only after entering/exiting doors
		    //cobvilleTown.setBackGroundImage(theGame.getCurrCameraMap().getMapImage());
			gameBackground.setPlayerLocation(theGame.getTrainerLocation());
			gameBackground.movePlayer(event.getCode(), "over");
		 }
		  
		// Draw character under X objects
		else if ((!(newLocationObject == 'Z')) && (newLocationObject == 'X')) {
		    //cobvilleTown.setBackGroundImage(theGame.getCurrCameraMap().getMapImage());
			gameBackground.setPlayerLocation(theGame.getTrainerLocation());
			gameBackground.movePlayer(event.getCode(), "under");
		}
	}
  }

  public static char getUserInputChar() {
    return gameLogic;
  }

  /*
   * getWildPoke() -- after checkBush() returns true (it found a Pokemon), this
   * method determines what Pokemon is being encountered. The level is determined
   * at random. The name, type and rareDegree will be pulled randomly from a list
   * in the Game class. Will always return a Pokemon.
   */
  private static Pokemon getWildPoke() {
    // this is a simple random generator, looks weird but works better than Random
    // double pivot = ThreadLocalRandom.current().nextDouble(0.1, 1.0);
    Random ran = new Random();

    int level = getRandomLevel();

    // will return the index for use in Pokemon name ArrayList
    int indexOfPoke = ran.nextInt(10);
    // get other fields from list in Game object
    String name = theGame.getPokemonNameList().get(indexOfPoke);
    char rarity = theGame.getAllPokemonList().get(name).charAt(0);
    char type = theGame.getAllPokemonList().get(name).charAt(1);

    // need to create list in Game with all of the special moves, same order as name
    // list for correlation.
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
    // double pivot = ThreadLocalRandom.current().nextDouble(0.1, 1.0);
    Random ran = new Random();
    double pivot = ran.nextDouble();

    // return level 16-20
    if (pivot > 0.90) {
      return ran.nextInt(5) + 16;
    }
    // return level 11-15
    else if (pivot > 0.70) {
      return ran.nextInt(5) + 11;
    }
    // return level 6-10
    else if (pivot > 0.40) {
      return ran.nextInt(5) + 6;
    }
    // return level 1-5
    else {
      return ran.nextInt(5) + 1;
    }
  }// end getRandomLevel()

  private static boolean checkBush() {
    // this is a simple random generator, looks weird but works better than Random
    // double pivot = ThreadLocalRandom.current().nextDouble(0.1, 1.0);
    Random ran = new Random();
    double pivot = ran.nextDouble();

    if (pivot > encounterChance) {
      return true;
    }
    return false;
  }// end checkBush()

  /*
   * getSafariStatSheet() -- method that creates an Alert with
   * AlertType.INFORMATION. Will show stats upon exiting Safari Zone. Will show:
   * Pokemon caught, safari zone items used (rock, bait, safariball), items gained
   * in Safari Zone, and number of steps taken.
   * 
   * Considering displaying NPCs encountered. And a count? ie <count
   * encountered>/<total num NPCs>
   */
  private static void getSafariStatSheet() {

    StringBuilder sb = new StringBuilder();
    Alert statSheet = new Alert(AlertType.INFORMATION);
    statSheet.setHeaderText("End of Safari Zone Quest");
    sb.append("Pokemon caught: \n");
    /*
     * Trainer's beforeSafariPokeList will be set before entering Safari Zone. This
     * loop prints Pokemon that have been added to Trainer's pokeList that aren't in
     * Trainer's beforeSafariPokeList
     */
    for (Pokemon p : theGame.getTrainer().getPokeList()) {
      if (!theGame.getTrainer().getBeforeSafariPokeList().contains(p)) {
        sb.append(p.getData());
      }
    }
    sb.append("\nSafari Inventory items used: \n");
    sb.append("\tbait: " + (10 - theGame.getTrainer().getSafariInventory().get("bait").size()) + "\n");
    sb.append("\trock: " + (10 - theGame.getTrainer().getSafariInventory().get("rock").size()) + "\n");
    sb.append("\tsafari ball: " + (30 - theGame.getTrainer().getSafariInventory().get("safariball").size()) + "\n");

    sb.append("\nItems gained: \n");
    if (theGame.getTrainer().getItemsGainedInSafariZone().size() == 0) {
      sb.append("\tNone\n");
    } else {
      for (Map.Entry<String, ArrayList<UsableItem>> entry : theGame.getTrainer().getItemsGainedInSafariZone()
          .entrySet()) {
        sb.append("\t" + entry.getKey() + ", " + entry.getValue().size());
        sb.append("\n");
      }
    }

    sb.append("\nSteps Allowed: \n\t" + theGame.getTrainer().getAllowedSafariSteps() + "/500\n");

    statSheet.setContentText(sb.toString());
    Optional<ButtonType> result = statSheet.showAndWait();

  }

  /*
   * getGameMenu() -- method that creates an Alert with AlertType.INFORMATION. Can
   * be opened at any time during the game (any map). Will display: Trainer's list
   * of Pokemon, Trainer's items in inventory.
   * 
   * Considering displaying NPCs encountered. And a count? ie <count
   * encountered>/<total num NPCs>
   * 
   * Also will have save button, will need to be linked with persistence logic.
   * 
   */
  private static void getGameMenu() {
    StringBuilder sb = new StringBuilder();
    ButtonType save = new ButtonType("Save Game?", ButtonBar.ButtonData.OK_DONE);

    sb.append("Pokemon: \n");
    for (Pokemon p : theGame.getTrainer().getPokeList()) {
      sb.append(p.getData());
    }
    sb.append("\nItems: \n");
    for (Map.Entry<String, ArrayList<UsableItem>> entry : theGame.getTrainer().getInventory().entrySet()) {
      sb.append("\t" + entry.getKey() + " " + entry.getValue().size() + "\n");
    }
    Alert gameMenu = new Alert(AlertType.INFORMATION, sb.toString(), save);
    gameMenu.setTitle("Game Menu");
    gameMenu.setHeaderText(theGame.getTrainer().getName());
    Optional<ButtonType> result = gameMenu.showAndWait();
  }




}