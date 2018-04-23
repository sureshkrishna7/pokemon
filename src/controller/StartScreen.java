package controller;

import controller.PokemonGame.STATE;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class StartScreen {

  /**
   * @param primaryStage
   * @param g2d
   * 
   * This class is reference material for how other classes need to be written
   * If this style is followed, it would greatly help us to render appropriate states
   * 
   * This class displays the pokemon logo at the beginning of the game
   * This class has a render method that is called upon by our main controller
   */
  
  private GraphicsContext g;
  private Stage primaryStage;
  private Scene scene;
  private Canvas canvas;
  private final double width = 800;
  private final double height = 800;
  
  private PokemonGame controller;
  
  public StartScreen(Stage theStage, GraphicsContext g2D, PokemonGame pokemonGame) {
    
    this.primaryStage = theStage;
    this.controller = pokemonGame;
    
    StackPane root = new StackPane();
    root.setPrefSize(600, 600);
    root.setPadding(new Insets(15,15,15,15));
    
    canvas = new Canvas(width, height);
    g = canvas.getGraphicsContext2D();
    root.getChildren().add(canvas);
    
    scene = new Scene(root);

  }

  public void render() {
    
    scene.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        controller.setState(STATE.MENU);
      }});
    
    //this.g = g2D;
    
    Image logo = new Image("file:src/Images/GameLogo.png", height/2, width/2, false, false);
    
    g.drawImage(logo, 0, 0);
    primaryStage.setTitle("Welcome to Pokemon World");
    primaryStage.setScene(scene);
    
    primaryStage.show();
  }
}
