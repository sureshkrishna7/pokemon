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
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
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
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
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
  private static MenuItem continueGame;
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
    Image displayPoke5 = new Image("file:src/images/112.gif",100,100,false,true);
    Image displayPoke6 = new Image("file:src/images/haunter-3.gif",90,90,false,true);
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

    BackgroundImage myBI= new BackgroundImage(new Image("file:src/images/GameBackground1.gif",width,height,false,false),
        BackgroundRepeat.NO_REPEAT , BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
        BackgroundSize.DEFAULT);

    //then you set to your node
    all.setBackground(new Background(myBI));

    initMenuItems();

    menuBox = new VBox(70,start, continueGame, instructions, quit);
    menuBox.setPadding(new Insets(30, 5, 5, 60));


    // Group group = new Group(menuBox, closingSceneAnimateCircle());
    all.setLeft(menuBox);
    all.setCenter(closingSceneAnimateCircle());
    all.setRight(imagesPane);
    return all;

  }

  private void initMenuItems() {
    start = new MenuItem("START");
    start.setId("fancytext");

    DropShadow dropShadow = new DropShadow();
    dropShadow.setColor(Color.GREEN);
    dropShadow.setRadius(25);
    dropShadow.setSpread(1);
    dropShadow.setBlurType(BlurType.GAUSSIAN);
    start.setEffect(dropShadow);


    start.setOnActivate(() -> {
      PokemonGame.currentState = STATE.COBVILLETOWN;
      PokemonGame.stateChanged = true;
      PokemonGame.setDefaultData();
    });

    continueGame = new MenuItem("CONTINUE");
    continueGame.setId("fancytext");
    
    DropShadow dropShadow4 = new DropShadow();
    dropShadow4.setColor(Color.BLUE);
    dropShadow4.setRadius(25);
    dropShadow4.setSpread(1);
    dropShadow4.setBlurType(BlurType.GAUSSIAN);
    continueGame.setEffect(dropShadow4);
    
    continueGame.setOnActivate(() -> {
      PokemonGame.currentState = STATE.COBVILLETOWN;
      PokemonGame.stateChanged = true;
      PokemonGame.readPersistentObjects();
    });
    
    
    instructions = new MenuItem("INSTRUCTIONS");
    instructions.setId("fancytext");
    
    DropShadow dropShadow2 = new DropShadow();
    dropShadow2.setColor(Color.YELLOW);
    dropShadow2.setRadius(25);
    dropShadow2.setSpread(1);
    dropShadow2.setBlurType(BlurType.GAUSSIAN);
    instructions.setEffect(dropShadow2);

    quit = new MenuItem("QUIT");
    quit.setId("fancytext");
    
    DropShadow dropShadow3 = new DropShadow();
    dropShadow3.setColor(Color.RED);
    dropShadow3.setRadius(25);
    dropShadow3.setSpread(1);
    dropShadow3.setBlurType(BlurType.GAUSSIAN);
    quit.setEffect(dropShadow3);
    
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
        text.setStyle("-fx-rotate: 12;");
        //text.setFill(Color.WHITE);
        LinearGradient linearGradient = new LinearGradient(0, 0, 20, 20, false, CycleMethod.REFLECT, new Stop(0,Color.valueOf("ff7eb3")),new Stop(1,Color.valueOf("#96deda")));
        text.setStroke(Color.TRANSPARENT);
        text.setFill(linearGradient);
      }
      else {
        text.setStyle("");
        text.setFill(Color.BLACK);
        text.setStroke(Color.TRANSPARENT);
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
