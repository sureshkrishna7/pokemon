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

public class TausifBattleView {

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
	public static ImageView iv1;
	public static ImageView iv2;
	public static ImageView iv3;
	public static ImageView iv4;
	
	public static int moveUsed;
	public static boolean run;
	public static boolean changePokemon = false;
	
	public enum STATE {
	    INIT, // Initial Animations
	    MAINMENU, // Main Menu, second state
	    FIGHT, // Show Fight moves
	    USEMOVE, //MOVE ANIMATIONS
	    CPUMOVE,
	    INVENTORY, // Use Inventory
	    POKEMON,		// Change Pokemon
	    RUN,		//Attempt run
	    END,		//END Battle
	  };

	private static void initializeGameForFirstTime() {
		//****USE pokemon.getName() to get the image filepath****//
		//***USE pokemon.getPokemon
		currentState = STATE.INIT;
	    wonBattle = false;
	    run = false;
	    
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
		iv1 = new ImageView(); //this allows the image to be a node so it can be resized
	    iv2 = new ImageView(); //this allows the image to be a node so it can be resized
	    iv3 = new ImageView();
	    iv4 = new ImageView();
	    iv1.setImage(image1);
		iv2.setImage(image2);
		iv3.setImage(image3);
		iv4.setImage(image4);
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
		trainerHealth = new Line(100, 200, 100 + current1, 200);
		trainerHealth.setStroke(Color.GREEN);
		trainerHealth.setStrokeWidth(3);
		
		maxHealth2 = new Line(212, 10, 112, 10);
		maxHealth2.setStrokeWidth(6);
		double current2 = ((double)wildPokemon.getCurHP()/(double)wildPokemon.getMaxHP())*100;
		wildHealth = new Line(212, 10, 212 - current2, 10);
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
		    		Button fight = new Button("FIGHT");
		    		fight.setOnAction(event -> {
		    			currentState = STATE.FIGHT;
		    			System.out.println(event.toString());
		    			playGame();
		    			});
		    		Button inventory = new Button("INVENTORY");
		    		inventory.setOnAction(event -> {
		    			currentState = STATE.INVENTORY;
		    			System.out.println(event.toString());
		    			playGame();
		    			});
		    		Button pokemon = new Button("POKEMON");
		    		pokemon.setOnAction(event -> {
		    			currentState = STATE.POKEMON;
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
		    }
		}
		if (currentState == STATE.INVENTORY) {
			Pane root = new Pane();
			ScrollPane pane = new ScrollPane();
			observableListInventory = FXCollections.observableArrayList();
		    listViewInventory = new ListView(observableListInventory);
		    listViewInventory.setPrefHeight(100);
		    listViewInventory.setPrefWidth(312);
		    listViewInventory.setLayoutY(212);
		    int i = 0;
		    if (trainer.getInventory().containsKey("heal"))
		    		observableListInventory.add("heal");
		    if (trainer.getInventory().containsKey("full tonic"))
	    			observableListInventory.add("full tonic");
		    if (trainer.getInventory().containsKey("full ether"))
    				observableListInventory.add("full ether");
		    if (trainer.getInventory().containsKey("elixir"))
	    			observableListInventory.add("elixir");
		    if (trainer.getInventory().containsKey("ether"))
	    			observableListInventory.add("ether");
		    if (trainer.getInventory().containsKey("mid ether"))
    				observableListInventory.add("mid ether");
		    if (trainer.getInventory().containsKey("mid tonic"))
    				observableListInventory.add("mid tonic");
		    if (trainer.getInventory().containsKey("tonic"))
				observableListInventory.add("tonic");
		    
		    listViewInventory.setItems(observableListInventory);
		    pane.prefWidthProperty().bind(listViewInventory.widthProperty());
		    pane.prefHeightProperty().bind(listViewInventory.heightProperty());
		    pane.setContent(listViewInventory);
		    pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		    pane.setLayoutY(212);
		    root.getChildren().add(pane);
		    
		    Button use = new Button("USE");
    				use.setOnAction(event -> {
    					currentState = STATE.MAINMENU;
    					if (listViewInventory.getSelectionModel().getSelectedItem() != null) {
    						trainer.getInventory().get(listViewInventory.getSelectionModel().getSelectedItem()).get(0).use(trainer, trainerPokemon);
    					 }
    					System.out.println(event.toString());
    					playGame();
    				});
    				
    			use.setLayoutX(250);
    			use.setLayoutY(230);
    			root.getChildren().add(use);
		    root.getChildren().add(iv1);
			root.getChildren().add(iv2);
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
		if (currentState == STATE.POKEMON) {
			Pane root = new Pane();
			ScrollPane pane = new ScrollPane();
			if(trainer.allPokemonExhausted() == true) {
				currentState = STATE.END;
				playGame();
			}
			else {
				observableListPokemon = FXCollections.observableArrayList();
			    listViewPokemon = new ListView(observableListPokemon);
			    listViewPokemon.setPrefHeight(100);
			    listViewPokemon.setPrefWidth(312);
			    listViewPokemon.setLayoutY(212);
			    for (int i = 0; i < trainer.getPokeList().size(); i++) {
			    		if (!trainer.getPokeList().get(i).isExhausted() && trainer.getPokeList().get(i) != trainerPokemon && !trainer.getPokeList().get(i).isExhausted()) {
			    			observableListPokemon.add(trainer.getPokeList().get(i).getName());
			    		}
			    }
			    
			    listViewPokemon.setItems(observableListPokemon);
			    pane.prefWidthProperty().bind(listViewPokemon.widthProperty());
			    pane.prefHeightProperty().bind(listViewPokemon.heightProperty());
			    pane.setContent(listViewPokemon);
			    pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
			    pane.setLayoutY(212);
			    root.getChildren().add(pane);
			    
			    Button use = new Button("USE");
				use.setOnAction(event -> {
					
					currentState = STATE.INIT;
					if (listViewPokemon.getSelectionModel().getSelectedItem() != null) {
						changePokemon = true;
						for (int i = 0; i < trainer.getPokeList().size(); i++) {
							if (trainer.getPokeList().get(i).getName() == listViewPokemon.getSelectionModel().getSelectedItem()) {
								trainer.setBattlePokemon(trainer.getPokeList().get(i));
								trainerPokemon = trainer.getPokeList().get(i);
							}
						}
						initializeGameForFirstTime();
					 }
					System.out.println(event.toString());
					playGame();
				});
				
				use.setLayoutX(250);
				use.setLayoutY(230);
				root.getChildren().add(use);
			    root.getChildren().add(iv1);
				root.getChildren().add(iv2);
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
		if (currentState == STATE.FIGHT) {
			Pane root = new Pane();
			Line line = new Line(0, 212,   312,   212);
			
			Button move1 = new Button(trainerPokemon.getAttacks().get(0).getName());
			move1.setOnAction(event -> {
				currentState = STATE.USEMOVE;
				//trainerPokemon.attack(0, wildPokemon);
				//wildPokemon.attack(wildPokemon.randomMove(), trainerPokemon);
				moveUsed = 0;
				System.out.println(event.toString());
				playGame();
				});
			Button move2 = new Button(trainerPokemon.getAttacks().get(1).getName());
			move2.setOnAction(event -> {
				currentState = STATE.MAINMENU;
				trainerPokemon.attack(1, wildPokemon);
				wildPokemon.attack(wildPokemon.randomMove(), trainerPokemon);
				moveUsed = 1;
				System.out.println(event.toString());
				playGame();
				});
			Button move3 = new Button(trainerPokemon.getAttacks().get(2).getName());
			move3.setOnAction(event -> {
				currentState = STATE.MAINMENU;
				trainerPokemon.attack(2, wildPokemon);
				wildPokemon.attack(wildPokemon.randomMove(), trainerPokemon);
				moveUsed = 2;
				System.out.println(event.toString());
				playGame();
				});
			Button move4 = new Button(trainerPokemon.getAttacks().get(3).getName());
			move4.setOnAction(event -> {
				currentState = STATE.MAINMENU;
				trainerPokemon.attack(3, wildPokemon);
				wildPokemon.attack(wildPokemon.randomMove(), trainerPokemon);
				moveUsed = 3;
				System.out.println(event.toString());
				playGame();
				});
			
		    root.getChildren().add(iv1);
		    root.getChildren().add(iv2);
		    
		    HBox vbButtons = new HBox();
			vbButtons.setSpacing(8);
			vbButtons.getChildren().addAll(move1, move2, move3, move4);
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
		if (currentState == STATE.USEMOVE) {
			Pane root = new Pane();
			Line line = new Line(0, 212,   312,   212);
			if (trainerPokemon.attack(moveUsed, wildPokemon) == false) {
				iv3.setVisible(false);
				System.out.println("User MISSED");
			}
			else
				iv3.setVisible(true);
			
			Path path1 = new Path();
			path1.getElements().add(new MoveTo(-100, 200));
			path1.getElements().add(new LineTo(280, 50));
			PathTransition pathTransition = new PathTransition();
			pathTransition.setDuration(Duration.millis(1500));
			// Circle is built above
			pathTransition.setNode(iv3);
			pathTransition.setPath(path1);
			pathTransition.play();
			root.getChildren().add(iv3);
		    
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
				currentState = STATE.CPUMOVE;
				System.out.println(event.toString());
				playGame();
				});
		
			currentScene = new Scene(root, 312, 312);
			primaryStage.setScene(currentScene);
			primaryStage.show();
		}
		
		if (currentState == STATE.CPUMOVE) {
			Pane root = new Pane();
			Line line = new Line(0, 212,   312,   212);
			if (wildPokemon.isExhausted()) {
				currentState = STATE.MAINMENU;
				playGame();
				return;
			}
			if (wildPokemon.attack(wildPokemon.randomMove(), trainerPokemon) == false) {
				iv4.setVisible(false);
				System.out.println("Wild Pokemon MISSED");
			}
			else
				iv4.setVisible(true);
			
			Path path1 = new Path();
			path1.getElements().add(new MoveTo(412, 0));
			path1.getElements().add(new LineTo(30, 150));
			PathTransition pathTransition = new PathTransition();
			pathTransition.setDuration(Duration.millis(1500));
			// Circle is built above
			pathTransition.setNode(iv4);
			pathTransition.setPath(path1);
			pathTransition.play();
			root.getChildren().add(iv4);
		    
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
				currentState = STATE.MAINMENU;
				System.out.println(event.toString());
				playGame();
				});
			
			currentScene = new Scene(root, 312, 312);
			primaryStage.setScene(currentScene);
			primaryStage.show();
		}
		if (currentState == STATE.RUN)
		{
			run = true;
			currentState = STATE.END;
			playGame();
			return;
		}
		if (currentState == STATE.END)
		{
			Line line = new Line(0, 212,   312,   212);
			Pane root = new Pane();
		    canvas = new Canvas(312, 212);
			
			Button fight = new Button("FIGHT");
    			fight.setOnAction(event -> {
    				currentState = STATE.FIGHT;
    				System.out.println(event.toString());
    				playGame();
    				});
    			Button inventory = new Button("INVENTORY");
    			inventory.setOnAction(event -> {
    				currentState = STATE.INVENTORY;
    				System.out.println(event.toString());
    				playGame();
    				});
    			Button pokemon = new Button("POKEMON");
    			pokemon.setOnAction(event -> {
    				currentState = STATE.POKEMON;
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
			
			if (wonBattle == true) {
				System.out.println("You Win");
				PokemonGame.getOutOfBattle();
			
			}
			else if (trainer.allPokemonExhausted()) {
				System.out.println("You Lose");
				PokemonGame.getOutOfBattle();
			}
			else {
				System.out.println("You Ran Away");
				PokemonGame.getOutOfBattle();
			}
			
		}
	}

}
