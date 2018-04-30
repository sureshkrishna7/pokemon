package controller.BattleView;

import java.awt.Insets;

import controller.GameBackground;
import controller.PokemonGame;
import controller.PokemonGame.STATE;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Game;
import model.Pokemon;
import model.Trainer;

public class SafariView {

	public static STATE currentState;
	public static boolean wonBattle;
	
	public static Trainer trainer;
	public static Pokemon trainerPokemon;
	public static Pokemon wildPokemon;
	
	public static Stage primaryStage;
	public static Scene currentScene;
	public static GraphicsContext gc;
	private static BorderPane pane;
	
	private static ObservableList<String> observableListInventory;
	private static ListView<String> listViewInventory;
	
	private static ObservableList<String> observableListPokemon;
	private static ListView<String> listViewPokemon;
	public static Canvas canvas;
	public static Circle circle;
	
	public static Line maxHealth1;
	public static Line maxHealth2;
	public static Line trainerHealth;
	public static Line wildHealth;
	public static Text trainerPokeName;
	public static Text wildPokeName;
		
	public static Image image1;
	public static Image image2;
	public static Image image3;
	public static Image image4;
	public static Image image5;
	public static Image image6;
	public static Image image7;
	public static ImageView iv1;
	public static ImageView iv2;
	public static ImageView iv3;
	public static ImageView iv4;
	public static ImageView iv5;
	public static ImageView iv6;
	public static ImageView iv7;
	public static ImageView currentIV;
	
	public static int moveUsed;
	public static int itemUsed;
	public static boolean playerRun;
	public static boolean changePokemon = false;
	public static boolean captured;
	
	public enum STATE {
	    INIT, // Initial Animations
	    MAINMENU, // Main Menu, second state
	    FIGHT, // Show Fight moves
	    USEMOVE,//MOVE ANIMATIONS
	    USEMOVE1,
	    USEMOVE2,
	    CPUMOVE,
	    INVENTORY, // Use Inventory
	    POKEMON,		// Change Pokemon
	    RUN,	
	    CAPTURE,    //Attempt run
	    END,		//END Battle
	  };

	private static void initializeGameForFirstTime() {
		//****USE pokemon.getName() to get the image filepath****//
		//***USE pokemon.getPokemon
		currentState = STATE.INIT;
	    wonBattle = false;
	    playerRun = false;
	    
	    String imageName1 = "file:images/" + trainerPokemon.getName() + ".png";
	    String imageName2 = "file:images/" + wildPokemon.getName() + ".png";
	    //String imageName3 = "file:images/" + trainerPokemon.getPokemonType() + ".gif";
	    //String imageName4 = "file:images/" + wildPokemon.getPokemonType() + ".gif";
		image1 = new Image(imageName1,100,100,false,false); //the image that is going to be drawn
		image2 = new Image(imageName2,100,100,false,false); //the image that is going to be drawn
		if (trainerPokemon.getPokemonType().toLowerCase() == "fire")
			image3 = new Image("file:images/fire.gif",100,100,false,false);
		else if (trainerPokemon.getPokemonType().toLowerCase() == "ice")
			image3 = new Image("file:images/ice.gif",100,100,false,false);
		else if (trainerPokemon.getPokemonType().toLowerCase() == "earth")
			image3 = new Image("file:images/earth.gif",100,100,false,false);
		else if (trainerPokemon.getPokemonType().toLowerCase() == "water")
			image3 = new Image("file:images/water.gif",100,100,false,false);
		if (wildPokemon.getPokemonType().toLowerCase() == "fire")
			image4 = new Image("file:images/fire.gif",100,100,false,false);
		else if (wildPokemon.getPokemonType().toLowerCase() == "ice")
			image4 = new Image("file:images/ice.gif",100,100,false,false);
		else if (wildPokemon.getPokemonType().toLowerCase() == "earth")
			image4 = new Image("file:images/earth.gif",100,100,false,false);
		else if (wildPokemon.getPokemonType().toLowerCase() == "water")
			image4 = new Image("file:images/water.gif",100,100,false,false);
		image5 = new Image("file:images/earth.gif",100,100,false,false);
		image6 = new Image("file:images/bait.gif",100,100,false,false);
		image7 = new Image("file:images/pokeball.gif",100,100,false,false);
		iv1 = new ImageView(); //this allows the image to be a node so it can be resized
	    iv2 = new ImageView(); //this allows the image to be a node so it can be resized
	    iv3 = new ImageView();
	    iv4 = new ImageView();
	    iv5 = new ImageView();
	    iv6 = new ImageView();
	    iv7 = new ImageView();
	    iv1.setImage(image1);
		iv2.setImage(image2);
		iv3.setImage(image3);
		iv4.setImage(image4);
		iv5.setImage(image5);
		iv6.setImage(image6);
		iv7.setImage(image7);
		/*
		maxHealth1 = new Line(100, 200, 200, 200);
		maxHealth1.setStrokeWidth(6);
		int current1 = (trainerPokemon.getCurHP()/trainerPokemon.getMaxHP())*100;
		trainerHealth = new Line(100, 200, 100 + current1, 200);
		trainerHealth.setStroke(Color.GREEN);
		trainerHealth.setStrokeWidth(3);
		
		maxHealth2 = new Line(212, 10, 112, 10);
		maxHealth2.setStrokeWidth(6);
		int current2 = (wildPokemon.getCurHP()/wildPokemon.getMaxHP())*100;
		wildHealth = new Line(212, 10, 212 - current2, 10);
		wildHealth.setStroke(Color.GREEN);
		wildHealth.setStrokeWidth(3);
		*/
		trainerPokeName = new Text(trainerPokemon.getName() + "-" + trainerPokemon.getLevel());
		trainerPokeName.setLayoutX(10);
		trainerPokeName.setLayoutY(100);
		wildPokeName = new Text(wildPokemon.getName() + "-" + trainerPokemon.getLevel());
		wildPokeName.setLayoutX(220);
		wildPokeName.setLayoutY(110);
		pane = new BorderPane();
	}
	
	public static void start(Stage stage, Trainer user, Pokemon wild) throws Exception {
		// initialization
	    trainer = user;
	    trainerPokemon = user.getCurPokemon();
	    wildPokemon = wild;
	    primaryStage = stage;
	    initializeGameForFirstTime();
	    playGame();
	}
	
	public static void playGame() {
		maxHealth1 = new Line(100, 200, 200, 200);
		maxHealth1.setStrokeWidth(6);
		double current1 = ((double)trainerPokemon.getCurHP()/(double)trainerPokemon.getMaxHP())*100;
		trainerHealth = new Line(100, 200, 100 + 100, 200);
		trainerHealth.setStroke(Color.GREEN);
		trainerHealth.setStrokeWidth(3);
		
		maxHealth2 = new Line(212, 10, 112, 10);
		maxHealth2.setStrokeWidth(6);
		double current2 = ((double)wildPokemon.getCurHP()/(double)wildPokemon.getMaxHP())*100;
		wildHealth = new Line(212, 10, 212 - 100, 10);
		wildHealth.setStroke(Color.GREEN);
		wildHealth.setStrokeWidth(3);
		
		if (currentState == STATE.INIT) {
			//primaryStage.setScene(currentScene);
			StackPane root = new StackPane();
			
			Path path1 = new Path();
		    path1.getElements().add(new MoveTo(500, 500));
		    path1.getElements().add(new LineTo(-54, 54));
		    PathTransition pathTransition = new PathTransition();
		    pathTransition.setDuration(Duration.millis(1000));
		    pathTransition.setNode(iv1);
		    pathTransition.setPath(path1);
		    pathTransition.play();
		    root.getChildren().add(iv1);
		    
		    Path path2 = new Path();
		    path2.getElements().add(new MoveTo(-500, 500));
		    path2.getElements().add(new LineTo(154, -54));
		    PathTransition pathTransition2 = new PathTransition();
		    pathTransition2.setDuration(Duration.millis(1000));
		    pathTransition2.setNode(iv2);
		    pathTransition2.setPath(path2);
		    pathTransition2.play();
		    	root.getChildren().add(iv2);
		    
		    pathTransition.setOnFinished(event -> {
				currentState = STATE.MAINMENU;
				System.out.println(event.toString());
				playGame();
				});
		    
			currentScene = new Scene(root, 312, 312);
			primaryStage.setScene(currentScene);
			primaryStage.show();
		}
		if (currentState == STATE.MAINMENU) {
			//primaryStage.setScene(currentScene);
			Line line = new Line(0, 212,   312,   212);
			Pane root = new Pane();
		    canvas = new Canvas(312, 212);
		    
		    if (wildPokemon.isExhausted() == true) {
				wonBattle = true;
				iv2.setVisible(false);
				currentState = STATE.END;
				playGame();
				return;
			}
		    
		    else if (trainerPokemon.isExhausted() == true && !trainer.allPokemonExhausted()) {
				wonBattle = false;
				iv1.setVisible(false);
				currentState = STATE.POKEMON;
				playGame();
				return;
			}
			
		    else if (trainer.allPokemonExhausted()) {
		    		wonBattle = false;
				iv1.setVisible(false);
				currentState = STATE.END;
				playGame();
				return;
		    }
		    
		    else {
		    		Button rock = new Button("ROCK");
		    		rock.setOnAction(event -> {
		    			currentState = STATE.USEMOVE;
		    			itemUsed = 0;
		    			System.out.println(event.toString());
		    			playGame();
		    			});
		    		Button bait = new Button("BAIT");
		    		bait.setOnAction(event -> {
		    			currentState = STATE.USEMOVE1;
		    			itemUsed = 1;
		    			System.out.println(event.toString());
		    			playGame();
		    			});
		    		Button pokeball = new Button("SAFARIBALL");
		    		pokeball.setOnAction(event -> {
		    			currentState = STATE.USEMOVE2;
		    			itemUsed = 2;
		    			System.out.println(event.toString());
					playGame();
		    			});
		    		Button run = new Button("RUN");
		    		run.setOnAction(event -> {
		    			currentState = STATE.RUN;
		    			System.out.println(event.toString());
		    			playGame();
		    			});
		    		root.getChildren().add(iv1);
		    		root.getChildren().add(iv2);
			
		    		HBox vbButtons = new HBox();
		    		vbButtons.setSpacing(8);
		    		if (trainer.getSafariInventory().containsKey("rock"))
		    			vbButtons.getChildren().add(rock);
		    		if (trainer.getSafariInventory().containsKey("bait"))
		    			vbButtons.getChildren().add(bait);
		    		if (trainer.getSafariInventory().containsKey("safariball"))
		    			vbButtons.getChildren().add(pokeball);
		    		vbButtons.getChildren().add(run);
		    		vbButtons.setLayoutX(2);
		    		vbButtons.setLayoutY(220);
				root.getChildren().add(vbButtons);
				root.getChildren().add(line);
				root.getChildren().add(maxHealth1);
				root.getChildren().add(maxHealth2);
				root.getChildren().add(trainerPokeName);
				root.getChildren().add(wildPokeName);
				root.getChildren().add(trainerHealth);
				root.getChildren().add(wildHealth);
		    
				currentScene = new Scene(root, 312, 312);
				primaryStage.setScene(currentScene);
				primaryStage.show();
		    }
		}
		
		if (currentState == STATE.USEMOVE) {
			Pane root = new Pane();
			Line line = new Line(0, 212,   312,   212);
/*
			if (itemUsed == 0) {
				currentIV = iv5;
				currentState = STATE.CPUMOVE;

				if (trainer.getSafariInventory().containsKey("rock")) {
					trainer.getSafariInventory().get("rock").get(0).use(trainer, wildPokemon);
					currentState = STATE.CPUMOVE;
				}
			}
			if (itemUsed == 1) {
				currentIV = iv6;
				currentState = STATE.CPUMOVE;
				if (trainer.getSafariInventory().containsKey("bait")) {
					trainer.getSafariInventory().get("bait").get(0).use(trainer, wildPokemon);
					currentState = STATE.CPUMOVE;
				}
			}
			if (itemUsed == 2) {
				currentIV = iv7;
				currentState = STATE.CPUMOVE;
				if (trainer.getSafariInventory().containsKey("safariball")) {
					if (trainer.getSafariInventory().get("safariball").get(0).use(trainer, wildPokemon) == "Caught!!") {
						captured = true;
						currentState = STATE.END;
					}
					else {
						captured = false;
						currentState = STATE.CPUMOVE;
					}
				}
			}
			*/
			trainer.getSafariInventory().get("rock").get(0).use(trainer, wildPokemon);
			Path path1 = new Path();
			path1.getElements().add(new MoveTo(-100, 200));
			path1.getElements().add(new LineTo(280, 50));
			PathTransition pathTransition = new PathTransition();
			pathTransition.setDuration(Duration.millis(1500));
			// Circle is built above
			pathTransition.setNode(iv5);
			pathTransition.setPath(path1);
			pathTransition.play();
			root.getChildren().add(iv5);
		    
			root.getChildren().add(iv1);
			root.getChildren().add(iv2);
			root.getChildren().add(line);
			root.getChildren().add(maxHealth1);
			root.getChildren().add(maxHealth2);
			root.getChildren().add(trainerPokeName);
			root.getChildren().add(wildPokeName);
			root.getChildren().add(trainerHealth);
			root.getChildren().add(wildHealth);
			
			pathTransition.setOnFinished(event -> {
				System.out.println(event.toString());
				currentState = STATE.CPUMOVE;
				playGame();
				});
		
			currentScene = new Scene(root, 312, 312);
			primaryStage.setScene(currentScene);
			primaryStage.show();
		}
		
		if (currentState == STATE.CPUMOVE) {
			if (wildPokemon.timeToRun())
				currentState = STATE.END;
			else 
				currentState = STATE.MAINMENU;
			playGame();
			return;
		}
		if (currentState == STATE.RUN)
		{
			playerRun = true;
			currentState = STATE.END;
			playGame();
			return;
		}
		
		if (currentState == STATE.USEMOVE1) {
			trainer.getSafariInventory().get("bait").get(0).use(trainer, wildPokemon);
			Pane root = new Pane();
			Line line = new Line(0, 212,   312,   212);
			Path path1 = new Path();
			path1.getElements().add(new MoveTo(-100, 200));
			path1.getElements().add(new LineTo(280, 50));
			PathTransition pathTransition = new PathTransition();
			pathTransition.setDuration(Duration.millis(1500));
			// Circle is built above
			pathTransition.setNode(iv6);
			pathTransition.setPath(path1);
			pathTransition.play();
			root.getChildren().add(iv6);
		    
			root.getChildren().add(iv1);
			root.getChildren().add(iv2);
			root.getChildren().add(line);
			root.getChildren().add(maxHealth1);
			root.getChildren().add(maxHealth2);
			root.getChildren().add(trainerPokeName);
			root.getChildren().add(wildPokeName);
			root.getChildren().add(trainerHealth);
			root.getChildren().add(wildHealth);
			
			pathTransition.setOnFinished(event -> {
				System.out.println(event.toString());
				currentState = STATE.CPUMOVE;
				playGame();
				});
		
			currentScene = new Scene(root, 312, 312);
			primaryStage.setScene(currentScene);
			primaryStage.show();
		}
		
		if (currentState == STATE.USEMOVE2) {
			if(trainer.getSafariInventory().get("safariball").get(0).use(trainer, wildPokemon)=="Caught!!")
				captured = true;
			Pane root = new Pane();
			Line line = new Line(0, 212,   312,   212);
			Path path1 = new Path();
			path1.getElements().add(new MoveTo(-100, 200));
			path1.getElements().add(new LineTo(280, 50));
			PathTransition pathTransition = new PathTransition();
			pathTransition.setDuration(Duration.millis(1500));
			// Circle is built above
			pathTransition.setNode(iv7);
			pathTransition.setPath(path1);
			pathTransition.play();
			root.getChildren().add(iv7);
		    
			root.getChildren().add(iv1);
			root.getChildren().add(iv2);
			root.getChildren().add(line);
			root.getChildren().add(maxHealth1);
			root.getChildren().add(maxHealth2);
			root.getChildren().add(trainerPokeName);
			root.getChildren().add(wildPokeName);
			root.getChildren().add(trainerHealth);
			root.getChildren().add(wildHealth);
			
			pathTransition.setOnFinished(event -> {
				System.out.println(event.toString());
				if (captured = true)
					currentState = STATE.END;
				else
					currentState = STATE.CPUMOVE;
				playGame();
				});
		
			currentScene = new Scene(root, 312, 312);
			primaryStage.setScene(currentScene);
			primaryStage.show();
		}
		
		if (currentState == STATE.END)
		{
			Line line = new Line(0, 212,   312,   212);
			Pane root = new Pane();
		    canvas = new Canvas(312, 212);
			
			Button fight = new Button("ROCK");
    			fight.setOnAction(event -> {
    				currentState = STATE.FIGHT;
    				System.out.println(event.toString());
    				//playGame();
    				});
    			Button inventory = new Button("BAIT");
    			inventory.setOnAction(event -> {
    				currentState = STATE.INVENTORY;
    				System.out.println(event.toString());
    				//playGame();
    				});
    			Button pokemon = new Button("SAFARIBALL");
    			pokemon.setOnAction(event -> {
    				currentState = STATE.POKEMON;
    				System.out.println(event.toString());
    				//playGame();
    				});
    			Button run = new Button("RUN");
    			run.setOnAction(event -> {
    				currentState = STATE.RUN;
    				System.out.println(event.toString());
    				//playGame();
    				});
    			if (playerRun == true) {
    				iv1.setVisible(false);
    			}
    			else if (captured == true) {
    				iv2 = iv7;
    				iv7.setLayoutX(0);
    				iv7.setLayoutY(0);
    				iv7.setVisible(true);
    				trainer.addPokemon(wildPokemon);
    			}
    			else
    				iv2.setVisible(false);
    			root.getChildren().add(iv1);
    			root.getChildren().add(iv2);
	
    			HBox vbButtons = new HBox();
    			vbButtons.setSpacing(8);
    			vbButtons.getChildren().addAll(fight, inventory, pokemon, run);
    			vbButtons.setLayoutX(2);
    			vbButtons.setLayoutY(220);
    			root.getChildren().add(vbButtons);
    			root.getChildren().add(line);
    			root.getChildren().add(maxHealth1);
    			root.getChildren().add(maxHealth2);
    			root.getChildren().add(trainerPokeName);
    			root.getChildren().add(wildPokeName);
			root.getChildren().add(trainerHealth);
			root.getChildren().add(wildHealth);
    
			currentScene = new Scene(root, 312, 312);
			primaryStage.setScene(currentScene);
			primaryStage.show();
			
			if (captured == true) {
				System.out.println("Captured Pokemon");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				PokemonGame.getOutSafari();
			}
			else if (playerRun == true) {
				System.out.println("You Ran Away");
				PokemonGame.getOutSafari();
			}
			else
				System.out.println("Pokemon Ran Away");
				PokemonGame.getOutSafari();
		}
	}

}
