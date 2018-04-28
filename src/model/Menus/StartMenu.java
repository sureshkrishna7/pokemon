package model.Menus;

import controller.PokemonGame;
import controller.PokemonGame.STATE;
import controller.States.IState;
import javafx.animation.PathTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class StartMenu implements IState{

  private static final int width = 600;
  private static final int height = 400;

  private int currentItem = 0;
  private static MenuItem start;
  private static MenuItem instructions;
  private static MenuItem quit;
  public Scene scene;
  private VBox menuBox;
  
  private static final Duration SCALE_DURATION = Duration.seconds(3);
  private static final double SCALE_FACTOR = 600;
  private PathTransition pathTransition;
  private ScaleTransition scaler;

  public StartMenu() {
    scene = new Scene(getStartMenu());
    scene.getStylesheets().add("file:src/style.css");

    scene.setOnKeyPressed(event -> {
      if(event.getCode() == KeyCode.UP) {
        if(currentItem > 0) {
          getMenuItem(currentItem).setActive(false);
          getMenuItem(--currentItem).setActive(true);
        }
      }

      if(event.getCode() == KeyCode.DOWN) {
        if(currentItem < menuBox.getChildren().size() - 1) {
          getMenuItem(currentItem).setActive(false);
          getMenuItem(++currentItem).setActive(true);
        }
      }

      if (event.getCode() == KeyCode.ENTER) {
        getMenuItem(currentItem).activate();
      }
    });

    /*
    primaryStage.setScene(scene);
    primaryStage.sizeToScene();
    primaryStage.show();
    primaryStage.setMinWidth(width);
    primaryStage.setMinHeight(height);
     */
  }
  
  private Parent getStartMenu() {
    BorderPane all = new BorderPane();
    GridPane imagesPane = new GridPane();
    GridPane pokePane = new GridPane();

    /*
    Button start = new Button("START");
    Button save = new Button("SAVE");
    Button inrt = new Button("INSTRUCTIONS");
    Button exit = new Button("EXIT");
     */

    Image logo = new Image("file:src/images/GameLogo.png",400,200,true,true);
    
    Image displayPoke = new Image("file:src/images/223.gif",100,100,false,true);
    Image displayPoke1 = new Image("file:src/images/335.gif",100,100,false,true);
    Image displayPoke2 = new Image("file:src/images/474.gif",100,100,false,true);
    Image displayPoke3 = new Image("file:src/images/696.gif",100,100,false,true);
    Image displayPoke4 = new Image("file:src/images/111.gif",100,100,false,true);
    Image displayPoke5 = new Image("file:src/images/112.gif",105,100,false,true);
    Image displayPoke6 = new Image("file:src/images/113.gif",100,100,false,true);
    Image displayPoke7 = new Image("file:src/images/114.gif",100,100,false,true);

    imagesPane.add(addImageToNode(logo), 0, 0);

    pokePane.add(new ImageView(displayPoke6), 0, 0);
    pokePane.add(new ImageView(displayPoke1), 1, 0);
    pokePane.add(new ImageView(displayPoke), 0, 1);
    pokePane.add(new ImageView(displayPoke4), 1, 1);
    pokePane.add(new ImageView(displayPoke3), 2, 0);
    pokePane.add(new ImageView(displayPoke2), 2, 1);
    pokePane.add(new ImageView(displayPoke5), 3, 1);
    pokePane.add(new ImageView(displayPoke7), 3, 0);


    imagesPane.add(pokePane, 0, 1);

    BackgroundImage myBI= new BackgroundImage(new Image("file:src/images/GameBackground1.gif",width,height,false,true),
        BackgroundRepeat.NO_REPEAT , BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
        BackgroundSize.DEFAULT);

    //then you set to your node
    all.setBackground(new Background(myBI));

    initMenuItems();

    menuBox = new VBox(30,start, instructions, quit);
    menuBox.setPadding(new Insets(60, 0, 0, 60));
  
    /*final Rectangle redBorder = new Rectangle(0, 0, Color.TRANSPARENT);
    start.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {

      @Override
      public void changed(ObservableValue<? extends Bounds> observable,
              Bounds oldValue, Bounds newValue) {
          redBorder.setLayoutX(start.getBoundsInParent().getMinX());
          redBorder.setLayoutY(start.getBoundsInParent().getMinY());
          redBorder.setWidth(start.getBoundsInParent().getWidth());
          redBorder.setHeight(start.getBoundsInParent().getHeight());
      }

  });
    menuBox.setStyle("-fx-border-color: red;");
    */
    
    //closingSceneAnimateCircle();
    
   // Group group = new Group(menuBox, closingSceneAnimateCircle());
    all.setLeft(menuBox);
    all.setCenter(closingSceneAnimateCircle());
    all.setRight(imagesPane);
    return all;

  }

  private void initMenuItems() {
    start = new MenuItem("START");
    start.setOnActivate(() -> {
      PokemonGame.currentState = STATE.COBVILLETOWN;
      PokemonGame.stateChanged = true;
    });

    instructions = new MenuItem("INSTRUCTIONS");
    quit = new MenuItem("QUIT");
    quit.setOnActivate(() -> System.exit(0));

  }

  private static class MenuItem extends HBox {
    private Text text;
    private Runnable script;
    
    public MenuItem(String name) {
      super(15);
      setAlignment(Pos.CENTER);
      text = new Text(name);

      getChildren().addAll(text);
      if(name == "START") setActive(true);
      else setActive(false);
      setOnActivate(() -> System.out.println(name + " activated"));
      
    }

    public void setActive(boolean b) {
      //        text.setFill(b ? Color.WHITE : Color.AQUA);
   
      if(b) {
        text.setStyle("-fx-font-weight: bold;\n" + 
            "    -fx-background-color: white;\n" + 
            "    -fx-background-radius: 22;\n" + 
            "    -fx-font-size: 16;\n" + 
            "    -fx-text-fill: #2196f3;\n" + 
            "    -fx-border-color: #2196f3;\n" + 
            "    -fx-border-radius: 20;\n" + 
            "    -fx-border-width: 3;\n" + 
            "    -fx-rotate: 10;");
        text.setFill(Color.valueOf("#cc0000"));
      }
      else {
        text.setStyle("-fx-font-weight: bold;\n" + 
            "   -fx-background-color: #2196f3;\n" + 
            "   -fx-background-radius: 22;\n" + 
            "   -fx-font-size: 16;\n" + 
            "   -fx-text-fill: white;");
        text.setFill(Color.valueOf("#003366"));
      }
    }

    public void setOnActivate(Runnable r) {
      script = r;
    }

    public void activate() {
      if (script != null)
        script.run();
    }
  }
  
  public ImageView addImageToNode(Image logo) {
    ImageView imageNode = new ImageView(logo);
    logo = ((ImageView) imageNode ).getImage();
    Path path = new Path();
    
    path.getElements().add(new MoveTo(900, 74));
    //path.getElements().add(new LineTo(1000, 74));
    path.getElements().add(new LineTo(397-(logo.getWidth()/2), 74));
    pathTransition = new PathTransition();
    pathTransition.setDuration(Duration.millis(1600));
    
 // Logo (Image) converted to a node is built above
    pathTransition.setNode(imageNode);
    pathTransition.setPath(path);
    
    return imageNode;
  }
  
  private StackPane closingSceneAnimateCircle() {
    
    Circle blackCircle = new Circle(width/2, height/2, 1, Color.BLACK);
    final StackPane circlePane = new StackPane(blackCircle);
//    circlePane.setPrefSize(width-400, height-100);
    
    scaler = new ScaleTransition(
        SCALE_DURATION,
        blackCircle
        );
   
    scaler.setFromX(SCALE_FACTOR);
    scaler.setToX(0);
    scaler.setFromY(SCALE_FACTOR);
    scaler.setToY(0);

    scaler.setAutoReverse(true);
    
    // Cycle count is reduced from infinity to 2
    scaler.setCycleCount(1);
    return circlePane;
  }


  public void playAnimationLogo() {
    pathTransition.play();
    scaler.play();
    return;
  }
  
  private MenuItem getMenuItem(int index) {
    return (MenuItem)menuBox.getChildren().get(index);
  }

  @Override
  public String getName() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void update(float elapsedTime) {
    // TODO Auto-generated method stub

  }

  @Override
  public Scene render() {
    return this.scene;
  }

  @Override
  public void onEnter() {
    // TODO Auto-generated method stub

  }

  @Override
  public void onExit() {

  }
}
