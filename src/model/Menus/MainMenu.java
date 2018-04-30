package model.Menus;

import java.util.ArrayList;
import java.util.Map;

import controller.PokemonGame;
import controller.States.IState;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Game;
import model.Pokemon;
import model.UsableItems.UsableItem;

public class MainMenu implements IState {

  private static final int width = 600;
  private static final int height = 400;

  private static final Font FONT1 = Font.font("Serif",FontWeight.NORMAL, 18);
  private static final Font FONT2 = Font.font("Serif", FontWeight.BOLD, 26);
  private Scene scene;
  private static Scene prevScene;
  private VBox menuBox;
  private int currentItem = 0;
  private static MenuItem save;
  private static MenuItem quit;
  private static MenuItem exit;
<<<<<<< HEAD
  
  //temp comment
  public MainMenu(Game theGame) {  
    scene = new Scene(getGameMenu(theGame));
=======

  private StringBuilder sb;

  public MainMenu(Game theGame) {

    listExcludedMainMenu(theGame);

    arrowKeyMovement();
  }

  public void arrowKeyMovement() {
>>>>>>> 1708822f8c40c5d5aec0e7c3b16b012cb1ff0758
    scene.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.UP) {
        if (currentItem > 0) {
          getMenuItem(currentItem).setActive(false);
          getMenuItem(--currentItem).setActive(true);
        }
      }

      if (event.getCode() == KeyCode.DOWN) {
        if (currentItem < menuBox.getChildren().size() - 1) {
          getMenuItem(currentItem).setActive(false);
          getMenuItem(++currentItem).setActive(true);
        }
      }

      if (event.getCode() == KeyCode.ENTER) {
        getMenuItem(currentItem).activate();
      }
    });
  }

  public void listExcludedMainMenu(Game theGame) {

    StackPane root = new StackPane();
    Rectangle textBox = new Rectangle(100,240,Paint.valueOf("BLACK"));

    ImageView bGround = new ImageView("file:src/images/GameBackground1.gif");
    bGround.prefHeight(height);
    bGround.prefWidth(width);

    initMenuItems();
    menuBox = new VBox(50, save, exit, quit);
    menuBox.setPadding(new Insets(25, 5, 20, 90));
    root.getChildren().addAll(bGround, textBox, menuBox);
    root.setAlignment(Pos.TOP_LEFT);
    //root.setRight(textPane);
    //root.setLeft(menuBox);

    scene = new Scene(root);
    scene.getStylesheets().add("file:src/style.css");
  }

  public Scene getScene() {
    return this.scene;
  }


  public void listIncludedMainMenu(Game theGame) {
    StackPane root = new StackPane();
    Rectangle textBox = new Rectangle(100,240,Paint.valueOf("BLACK"));

    ImageView bGround = new ImageView("file:src/images/GameBackground1.gif");
    bGround.prefHeight(height);
    bGround.prefWidth(width);

    System.out.println("Main Menu is this running?");

    sb = new StringBuilder();

    sb.append("Pokemon: \n");

    ArrayList<Pokemon> pokes = theGame.getTrainer().getPokeList();
    System.out.println("Main Menu Pokemon List ="+pokes.toString());

    for (Pokemon p : pokes) {
      System.out.println("MainMenu Pokemon = "+p.getData());
      sb.append(p.getData());
    }
    sb.append("\nItems: \n");
    for (Map.Entry<String, ArrayList<UsableItem>> entry : theGame.getTrainer().getInventory().entrySet()) {
      System.out.println("MainMenu items = "+entry.getKey() + " "+ entry.getValue().size() + "\n");
      sb.append("\t" + entry.getKey() + " " + entry.getValue().size() + "\n");
    }


    Text text = new Text(sb.toString());
    text.setFont(FONT1);
    LinearGradient linearGradient = new LinearGradient(0, 0, 20, 20, false, CycleMethod.REFLECT, new Stop(0,Color.valueOf("ff7eb3")),new Stop(1,Color.valueOf("#96deda")));
    text.setStroke(Color.TRANSPARENT);
    text.setFill(linearGradient);

    initMenuItems();
    menuBox = new VBox(50, save, exit, quit);
    menuBox.setPadding(new Insets(25, 5, 20, 90));
    root.getChildren().addAll(bGround, textBox, text, menuBox);
    root.setAlignment(Pos.TOP_LEFT);
    //root.setRight(textPane);
    //root.setLeft(menuBox);
    scene = new Scene(root);

    arrowKeyMovement();
  }

  private static void initMenuItems() {
    save = new MenuItem("Save");
    save.setId("fancytext");
    DropShadow dropShadow = new DropShadow();
    dropShadow.setColor(Color.GREEN);
    dropShadow.setRadius(25);
    dropShadow.setSpread(1);
    dropShadow.setBlurType(BlurType.GAUSSIAN);
    save.setEffect(dropShadow);

    save.setOnActivate(() -> {
      PokemonGame.writePersistentObject();
    });

    exit = new MenuItem("Exit Menu");
    exit.setId("fancytext");
    DropShadow dropShadow2 = new DropShadow();
    dropShadow2.setColor(Color.YELLOW);
    dropShadow2.setRadius(25);
    dropShadow2.setSpread(1);
    dropShadow2.setBlurType(BlurType.GAUSSIAN);
    exit.setEffect(dropShadow2);


    exit.setOnActivate(() -> {
      //System.out.println(prevScene);
      //PokemonGame.primaryStage.setScene(prevScene);
      PokemonGame.currentState = PokemonGame.previousState;
      PokemonGame.stateChanged = true;
    });

    quit = new MenuItem("Quit");
    quit.setOnActivate(() -> System.exit(0));

    quit.setId("fancytext");

    DropShadow dropShadow3 = new DropShadow();
    dropShadow3.setColor(Color.RED);
    dropShadow3.setRadius(25);
    dropShadow3.setSpread(1);
    dropShadow3.setBlurType(BlurType.GAUSSIAN);
    quit.setEffect(dropShadow3);

  }

  private static class MenuItem extends VBox {
    private Text text;
    private Runnable script;

    public MenuItem(String name) {
      super(15);
      setAlignment(Pos.CENTER);

      text = new Text(name);
      text.setFont(FONT2);

      getChildren().addAll(text);
      if(name == "SAVE") setActive(true);
      else setActive(false);
      setOnActivate(() -> System.out.println(name + " activated"));
    }

    public void setActive(boolean b) {

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

  private MenuItem getMenuItem(int index) {
    return (MenuItem)menuBox.getChildren().get(index);
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
    prevScene = PokemonGame.currentScene;
    System.out.println("Save prevScene: " + prevScene);
  }

  @Override
  public void onExit() {
    PokemonGame.primaryStage.setScene(prevScene);
  }

  @Override
  public String getName() {
    return "mainMenu";
  }


}
