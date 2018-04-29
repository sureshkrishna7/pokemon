package controller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observer;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;


import controller.States.CobvilleTown;
import controller.States.Mart;
import controller.States.StartScreen;
import controller.States.StateMachine;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Battle;
import model.Game;
import model.Pokemon;
import model.MainMap.Door;
import model.MainMap.MainMap;
import model.Menus.MainMenu;
import model.Menus.StartMenu;
import model.UsableItems.UsableItem;

//Simply Create the User and insert User into PokeTownMap, the rest of the maps will be embedded within PokeTownMap

public class PokemonGame extends Application {

  private final double cameraViewSize = 20 * 16;

  public static int renderCount = 1;
  
  public static Stage primaryStage;
  public static Scene currentScene;
  public static GraphicsContext g2D;

  private static Scanner sc;
  private static Game theGame;
  private static Point playerStartLocation = new Point();
  private static Point playerOldLocation = new Point();
  private static MainMap oldCurrentMap;

  private static boolean firstState;
  private static boolean running;
  private static boolean foundPokemon;
  private static boolean wonBattle;
  public static boolean stateChanged;
  private static final double encounterChance = 0.6;
  private static boolean trainerWasAlreadyOnDoor;
  private static GameBackground currBackground;
  private static CobvilleTown cobvilleTown, town;
  private static Mart mart;
  private static BorderPane pane;
  private static Door door;
  private static Observer currentView, imageView, textAreaView;
  //private static AnimateStarter animateStart;
  private AnimateStarter animateStarter = new AnimateStarter();;
  private KeyHandler keyHandler = new KeyHandler();
  private MainMenu menu;
  private boolean notAcceptingInput = false;
  
  // IState objects
  private StateMachine stateMachine;
  private StartScreen start;
  private StartMenu startMenu;
  private KeyEvent currKeyEvent;
  private char currLocationChar;

  public static STATE currentState;
  public static STATE previousState;

  public static void main(String[] args) {
    launch(args);
  }

  /**
   * 
   * @author Suresh Different States signified by the enum The render method will
   *         check what state it is right now Then it will call the appropriate
   *         classes that draw to the graphics context
   * 
   *         ALL drawing classes like start, menu, game, instruction, battle will
   *         take in the graphics context and primaryStage parameters in their
   *         constructor
   * 
   *         All those classes should have a public method that renders/ draws
   *         stuff to the graphics context So render() method in the
   *         controller(pokemonGame) could call those methods at the appropriate
   *         time
   */
  public enum STATE {
    START, // Start screen, first state
    STARTMENU, // StartMenu, second state
    MENU, // Menu screen
    COBVILLETOWN, // Actual Game
    INSIDE_BUILDING,		// inside house
    INSTRUCTION, // How to play the Game
    BATTLE, // Battle screen
  };

  private static void initializeGameForFirstTime() {
    theGame = new Game();
    pane = new BorderPane();
    currBackground = new GameBackground(theGame.getTrainerLocation(), theGame.getCurrCameraMap().getMapImage());
    
    playerStartLocation.x = theGame.getTrainerLocation().x;
    playerStartLocation.y = theGame.getTrainerLocation().y;

    playerOldLocation.x = theGame.getTrainerLocation().x;
    playerOldLocation.y = theGame.getTrainerLocation().y;

    oldCurrentMap = theGame.getCurrCameraMap();
    trainerWasAlreadyOnDoor = false;

    foundPokemon = false;
    wonBattle = false;
  }

  @Override
  public void start(Stage stage) throws Exception {

    // initialization
    initializeGameForFirstTime();

    // initialize state to start
    currentState = STATE.COBVILLETOWN;
    //currentState = STATE.STARTMENU;
    
    stateChanged = true;

    stateMachine = new StateMachine(theGame, stage, this);
    primaryStage = stage;
    
    new AnimationTimer() {
      
      private long last = 0;
      
      @Override
      public void handle(long current) {
        if(current - last >= 28000000) {
          render();
          last = current;
        }
      }
    }.start();

    stage.show();
  }

  private void tick() {
    // TODO Auto-generated method stub

  }

  /**
   * @author Suresh This method would call all the classes that needs to be
   *         rendered The render method will check what state it is right now Then
   *         it will call the appropriate classes that draw to the graphics
   *         context
   * 
   *         ALL drawing classes like start, menu, game, instruction, battle will
   *         take in the graphics context and primaryStage parameters in their
   *         constructor
   * 
   *         All those classes should have a public method that renders/ draws
   *         stuff to the graphics context So render() method in the
   *         controller(pokemonGame) could call those methods when the STATE
   *         changes
   */
  
  private void render() {

    if(stateChanged) {
      if(previousState != null) {
        System.out.println("previousState: " + previousState);
        System.out.println("currentState: " + currentState);
        stateMachine.getIState(previousState).onExit();
      }
      
      switch (currentState) {
      case START:
        stateChanged = false;
        currentState = STATE.START;
        previousState = STATE.START;
        
        start = (StartScreen) stateMachine.getIState(STATE.START);
        currentScene = start.render();
        if (currentScene == null) {
          System.out.println("Current scene is null");
        }
        primaryStage.setScene(currentScene);
        System.out.println("Start case");
        break;
      case STARTMENU:
        stateChanged = false;
        currentState = STATE.STARTMENU;
        startMenu = (StartMenu) stateMachine.getIState(STATE.STARTMENU);
        currentScene = startMenu.render();
        // Play logo animation
        startMenu.playAnimationLogo();
        primaryStage.setScene(currentScene);
        break;
      case COBVILLETOWN:
        stateChanged = false;
        currentState = STATE.COBVILLETOWN;
        System.out.println("Cobville case");
        cobvilleTown = (CobvilleTown) stateMachine.getIState(STATE.COBVILLETOWN);
        currBackground = cobvilleTown;
        currentScene = cobvilleTown.render();
        currentScene.setOnKeyReleased(animateStarter);
        currentScene.setOnKeyPressed(keyHandler);
        primaryStage.setScene(currentScene);
        break;
      case INSIDE_BUILDING:
        stateChanged = false;
        currentState = STATE.INSIDE_BUILDING;
        GameBackground mapInsideBuilding = door.getGameBackground();
        currBackground = mapInsideBuilding;
        //drawGameBackground(currBackground, currKeyEvent, currLocationChar);
        currentScene = ((GameBackground) mapInsideBuilding).render();
        currentScene.setOnKeyReleased(animateStarter);
        currentScene.setOnKeyPressed(keyHandler);
        primaryStage.setScene(currentScene);
        
        break;
      case BATTLE:
        break;
      case INSTRUCTION:
        break;
      case MENU:
        stateChanged = false;
        menu = (MainMenu) stateMachine.getIState(STATE.MENU);
        menu.onEnter();
        currentScene = menu.render();
        currentState = STATE.MENU;
        primaryStage.setScene(currentScene);
        break;
      default:
        break;
      }
    }
  }// end render()

  public class KeyHandler implements EventHandler<KeyEvent> {

    @Override
    public void handle(KeyEvent event) {
      if (event.getCode() == KeyCode.ESCAPE) {
        previousState = currentState;
        currentState = STATE.MENU;
        stateChanged = true;
      }
    }
  }

  public void setState(STATE newState) {
    currentState = newState;
  }

  // Add a listener that will start the Timeline's animation
  public class AnimateStarter implements EventHandler<KeyEvent> {
    @Override
    public void handle(KeyEvent event) {

    	System.out.println("State = " + currentState);
      /**
       * We monitor what arrow keys we are pressing only when we are in STATE.GAME
       * This is to avoid key pressing conflicts when we not in GAME state
       */
      if (currentState == STATE.COBVILLETOWN || currentState == STATE.INSIDE_BUILDING) {
        /**
         * NOTE: If user inputs moves too fast, the player will move on the grid faster
         * than the animation timeline can draw the image, and will crash (runs into
         * things on grid before image). So if animation is on, ignore button clicked
         */
        if (currBackground.isTimelineAnimating() || notAcceptingInput) {
          return;
        }
        
        currKeyEvent = event;
        char newLocationObject = 'Z';
        currLocationChar = newLocationObject;
        if (KeyCode.UP == event.getCode()) {
          newLocationObject = theGame.playerMove('n');

          if (theGame.getIsTrainerAlreadyOnDoor()) {
              // when the player is already on a door, they want to press up and 
              // enter the building, but the map isWalkable() will not allow them
              // to walk on the house object. newLocationObj == 'z' 
        	  newLocationObject = 'D';
        	  trainerWasAlreadyOnDoor = true;
          }
        } else if (KeyCode.DOWN == event.getCode()) {
          newLocationObject = theGame.playerMove('s');
        } else if (KeyCode.LEFT == event.getCode()) {
          newLocationObject = theGame.playerMove('w');
        } else if (KeyCode.RIGHT == event.getCode()) {
          newLocationObject = theGame.playerMove('e');
        } else if (KeyCode.S == event.getCode() && theGame.getCurrCameraMap() != theGame.getFryslaSafariZone()) {
          playerOldLocation = theGame.getTrainerLocation();
          oldCurrentMap = theGame.getCurrCameraMap();
          theGame.setTrainerLocation(theGame.getFryslaSafariZone().getMapPlayerPosition());
          theGame.setCurrCameraMap(theGame.getFryslaSafariZone());
          theGame.weAreInSafariZone();
        } else if (KeyCode.P == event.getCode() && theGame.getCurrCameraMap() == theGame.getFryslaSafariZone()) {
          theGame.setTrainerLocation(playerOldLocation);
          theGame.setCurrCameraMap(oldCurrentMap);
          theGame.weAreOutSafariZone();
        } else {
          // System.out.println("KeyCode =" + event.getCode());
        }

        //System.out.println("Game logic = " + newLocationObject);
        currLocationChar = newLocationObject;

        if (newLocationObject == 'D') {
            System.out.print("Encountered a Door\n");

            playerOldLocation.x = theGame.getTrainerLocation().x;
            playerOldLocation.y = theGame.getTrainerLocation().y;
            oldCurrentMap = theGame.getCurrCameraMap();
            door = (Door) theGame.getCurrCameraMap().enteredDoor(theGame.getTrainerLocation().x, theGame.getTrainerLocation().y);

            /* 
             * ****We would be in safari Zone if the door is null****
             * ****Because we magically hop to different places****
             */

	        if(door == null) {
	        	System.out.println("DOOR = NULL");
	            theGame.setTrainerLocation(theGame.getCurrCameraMap().getMapPlayerPosition());
	        }
	        else {
	            theGame.setTrainerLocation(door.getMapPlayerPos());
	            theGame.setCurrCameraMap(door.getInsideMapObject());
	            System.out.println("Curr Background = " + currBackground);
	            notAcceptingInput = true;
	            currBackground.closingSceneAnimateCircle(this, currBackground.getTransitionViewCircle(), "entering");
	            pane.setCenter(door.getGameBackground());
	            
	        	currBackground = (GameBackground)pane.getCenter();
	        	
	        	/** 
	        	 * NOTE: new background (entering door) will be drawn when transition view
	        	 * currBackground.closingSceneAnimateCircle() is done animating which
	        	 * will call continueEnteringBuildingAnimation() in this class to finish
	        	 * off the code that should run at this point. 
	        	 */
	            return;
	        }
        } 
        else if (newLocationObject == 'E') {
        	
        	GameBackground backgroundToRemove = currBackground;
        	backgroundToRemove.setDy(backgroundToRemove.getDy() + 16);
        	theGame.setTrainerLocation(playerOldLocation);
            theGame.setCurrCameraMap(oldCurrentMap);
            
            // align back to mainmap destination after exitin building
        	// since animation increments/decrements the location on GUI
        	// when moving on different maps (grids)
            notAcceptingInput = true;
        	currBackground.closingSceneAnimateCircle(this, currBackground.getTransitionViewCircle(), "exiting");
        	
        	// -32 (16) to adjust animation after exiting house 
        	// (16) to make sure that when he takes a step out the door, 
        	// he is put in the correct pos relative to grid
        	// less adjusting if was already on door
        	if (trainerWasAlreadyOnDoor) {
        		cobvilleTown.setDy(cobvilleTown.getDy() - 16);
        		trainerWasAlreadyOnDoor = false;
        	}else {
        		cobvilleTown.setDy(cobvilleTown.getDy() - 32);
        	}
        	
        	pane.setCenter(cobvilleTown);
        	currBackground = (GameBackground)pane.getCenter();

        	return;
        	  
        }  else if (newLocationObject == ' ') {
        	theGame.setTrainerLocation(playerOldLocation);
        	theGame.setCurrCameraMap(oldCurrentMap);

        } else if (newLocationObject == 'S') {
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
        } else if (newLocationObject == 'B') {
	          foundPokemon = checkBush();
	          if (foundPokemon) {
	
	            /** The Battle is about to start */
	            currentState = STATE.BATTLE;
	
	            Pokemon wildPoke = getWildPoke();
	            wonBattle = Battle.battle(theGame.getTrainer(), wildPoke, sc);
	          }
	
	          /** Once the battle is done set the state back to Game */
	          currentState = STATE.COBVILLETOWN;

        } else if (newLocationObject == 'N') {
            System.out.print("Encountered a NPC\n");
        }
        
        drawGameBackground( currBackground, event, newLocationObject);
        
        
      }// end if (currentState == State.COBVILLE)
      
      
    }
    
    public void continueEnteringBuildingAnimation() {
    	notAcceptingInput = false;
        currentState = STATE.INSIDE_BUILDING;
        stateChanged = true;
        drawGameBackground(currBackground, currKeyEvent, currLocationChar);
    }
    
    public void continueExitingBuildingAnimation() {
    	notAcceptingInput = false;
    	currentState = STATE.COBVILLETOWN;
    	stateChanged = true;
        drawGameBackground(currBackground, currKeyEvent, currLocationChar);
    }
 

  } // end AnimateStarter
  
  /**
   * drawGameBackground()
   * z is a char returned by theGame.playerMove() that's not used in map
   * to represent an obj, thus can be used to detect null
   * We need to setBackGroundImage() only after entering/exiting doors
   */
  public void drawGameBackground(GameBackground gameBackground, KeyEvent event, char newLocationObject) {
  	if ((!(newLocationObject == 'Z')) && (!(newLocationObject == 'X'))) {
  		gameBackground.setPlayerLocation(theGame.getTrainerLocation());
  		gameBackground.movePlayer(event.getCode(), "over");
  	}

  	// Draw character under X objects
  	else if ((!(newLocationObject == 'Z')) && (newLocationObject == 'X')) {
  		gameBackground.setPlayerLocation(theGame.getTrainerLocation());
  		gameBackground.movePlayer(event.getCode(), "under");
  	}
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

}