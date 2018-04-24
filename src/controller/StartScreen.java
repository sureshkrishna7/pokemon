package controller;

import controller.PokemonGame.STATE;
import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;


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
  private Image logo;

  private static final Duration SCALE_DURATION = Duration.seconds(3);
  private static final double SCALE_FACTOR = 370;
  private PathTransition pathTransition;
  private ScaleTransition scaler;

  public StartScreen(Stage theStage, GraphicsContext g2D, PokemonGame pokemonGame) {

    this.primaryStage = theStage;
    this.controller = pokemonGame;

    StackPane root = new StackPane();

    canvas = new Canvas(width, height);
    gc = canvas.getGraphicsContext2D();
    root.getChildren().add(canvas);

    scene = new Scene(root, width, height);
  }

  public void render() {

    // Our pokemon Logo
    logo = new Image("file:src/Images/GameLogo.png", 400, 200, true, true);

    final long startNanoTime = System.nanoTime();
    //System.out.println("start ="+startNanoTime);
    new AnimationTimer()
    {
      public void handle(long currentNanoTime)
      {
        //System.out.println("current ="+currentNanoTime);  
        double t = (currentNanoTime - startNanoTime) / 1000000000.0; 

        //System.out.println("double ="+t);
        double x = 94 + 108 * Math.sin(t);
        //System.out.println("x ="+x);
        double y = 0 + 108 * Math.cos(t);
        //System.out.println("y ="+y);

        // Background image also clears canvas
        gc.clearRect(0, 0, width, height);
        gc.drawImage( logo, x, y+100 );
        gc.fillText("Press Enter to Continue!", 215, 375, 575);


        /**
         * PRESS ENTER to proceed further
         */
        scene.setOnKeyPressed(event -> {
          if (event.getCode() == KeyCode.ENTER) {
            controller.setState(STATE.MENU);
            this.stop();
            afterEnterKeyIsPressed(x, y);
          }});
      }
    }.start();

    primaryStage.setTitle("Welcome to Pokemon World");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  protected void afterEnterKeyIsPressed(double x, double y) {

    /**
     * Image and pathTransition (How they work together?)
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
     * Image cannot be directly added to the path Transition
     * So it is converted to a node using ImageView
     * That node is inturn added to path transition
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */
    ImageView imageNode = new ImageView(logo);
    logo = ((ImageView) imageNode ).getImage();
    Path path = new Path();

    // Start the logo from the center of the screen
    // path.getElements().add(new MoveTo(width/2, height/2));

    // Start the logo from the bottom of the screen
    // path.getElements().add(new MoveTo(width/2, height+60));

    // Start the logo from the top of the screen
    // path.getElements().add(new MoveTo(width/2, -60));

    // Start the logo at the position you clicked the enter button
    path.getElements().add(new MoveTo(x+(logo.getWidth()/2),y+(logo.getHeight()/2)+100));

    path.getElements().add(new LineTo(width/2, 80));
    path.getElements().add(new LineTo(-30, 30));
    path.getElements().add(new LineTo(-85, 200));
    path.getElements().add(new LineTo(200, 600));
    path.getElements().add(new LineTo(700, 200));

    // Going from center to correct postion
    //path.getElements().add(new LineTo(250, 350));

    // Going from side to correct postion
    path.getElements().add(new LineTo(800, 74));

    path.getElements().add(new LineTo(397, 74));
    pathTransition = new PathTransition();
    pathTransition.setDuration(Duration.millis(5000));

    // Logo (Image) converted to a node is built above
    pathTransition.setNode(imageNode);
    pathTransition.setPath(path);

    // A circle black object is created here, Start the circle from the center of the screen
    Circle blackCircle = new Circle(width/2, height/2, 1, Color.BLACK);
    final StackPane circlePane = new StackPane(blackCircle);
    circlePane.setPrefSize(width, height);

    // Both the nodes are added to a Group
    Group root = new Group(imageNode,circlePane);

    // The Group is added to one scene
    Scene scene = new Scene(root, width, height);
    primaryStage.setScene(scene);
    primaryStage.show();

    // This method plays both the animation at the same time
    // By calling play at the very end
    closingSceneAnimateCircle(blackCircle);
  }

  private void closingSceneAnimateCircle(Circle circle) {
    scaler = new ScaleTransition(
        SCALE_DURATION,
        circle
        );
    scaler.setFromX(1);
    scaler.setToX(SCALE_FACTOR);
    scaler.setFromY(1);
    scaler.setToY(SCALE_FACTOR);

    scaler.setAutoReverse(true);

    // Cycle count is reduced from infinity to 2
    scaler.setCycleCount(2);

    // Play both the animation at the same time
    scaler.play();
    pathTransition.play();
  }
}
