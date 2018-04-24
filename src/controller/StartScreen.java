package controller;

import controller.PokemonGame.STATE;
import javafx.animation.AnimationTimer;
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
  
  private GraphicsContext gc;
  private Stage primaryStage;
  private Scene scene;
  private Canvas canvas;
  private final double width = 600;
  private final double height = 400;
  
  private PokemonGame controller;
  
  public StartScreen(Stage theStage, GraphicsContext g2D, PokemonGame pokemonGame) {
    
    this.primaryStage = theStage;
    this.controller = pokemonGame;
    
    StackPane root = new StackPane();
    root.setPrefSize(width, height);
    root.setPadding(new Insets(15,15,15,15));
    
    canvas = new Canvas(width, height);
    gc = canvas.getGraphicsContext2D();
    root.getChildren().add(canvas);
    
    scene = new Scene(root);

  }

  public void render() {
    
    
    //this.g = g2D;
    
    Image logo = new Image("file:src/Images/GameLogo.png", 400, 200, true, true);
    
    
    final long startNanoTime = System.nanoTime();
    //System.out.println("start ="+startNanoTime);
    new AnimationTimer()
    {
        public void handle(long currentNanoTime)
        {
          //System.out.println("current ="+currentNanoTime);  
          double t = (currentNanoTime - startNanoTime) / 1000000000.0; 
 
          //System.out.println("double ="+t);
            double x = 72 + 128 * Math.sin(t);
            System.out.println("x ="+x);
            double y = 0 + 158 * Math.cos(t);
            System.out.println("y ="+y);
            // background image clears canvas
            //gc.drawImage( space, 0, 0 );
            gc.clearRect(0, 0, width, height);
            gc.drawImage( logo, x, y );
            
            scene.setOnKeyPressed(event -> {
              if (event.getCode() == KeyCode.ENTER) {
                controller.setState(STATE.MENU);
                this.stop();
                afterEnterKeyIsPressed();
                //System.out.println("This runs");
              }});
            //gc.drawImage( sun, 196, 196 );
        }
    }.start();
    
    //System.out.println("This runs");
    //gc.drawImage(logo, 200, 10);
    primaryStage.setTitle("Welcome to Pokemon World");
    primaryStage.setScene(scene);
    
    primaryStage.show();
  }

  protected void afterEnterKeyIsPressed() {
    
    
  }
}
