package model.Menus;

import java.util.ArrayList;
import java.util.Map;

import controller.PokemonGame;
import controller.States.IState;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
  
  //temp comment
  public MainMenu(Game theGame) {  
    scene = new Scene(getGameMenu(theGame));
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
  
  public Scene getScene() {
    return this.scene;
  }
  
  
  private Parent getGameMenu(Game theGame) {
    BorderPane root = new BorderPane();
    
    BackgroundImage myBI= new BackgroundImage(new Image("file:src/images/GameBackground1.gif",width,height,false,true),
        BackgroundRepeat.NO_REPEAT , BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
        BackgroundSize.DEFAULT);
    
    root.setBackground(new Background(myBI));
    
    StringBuilder sb = new StringBuilder();

    sb.append("Pokemon: \n");
    for (Pokemon p : theGame.getTrainer().getPokeList()) {
      sb.append(p.getData());
    }
    sb.append("\nItems: \n");
    for (Map.Entry<String, ArrayList<UsableItem>> entry : theGame.getTrainer().getInventory().entrySet()) {
      sb.append("\t" + entry.getKey() + " " + entry.getValue().size() + "\n");
    }
    
    Text text = new Text(sb.toString());
    text.setFont(FONT1);
    text.setFill(Color.ANTIQUEWHITE);
    
    initMenuItems();
    
    menuBox = new VBox(10, save, exit, quit);
    menuBox.setPadding(new Insets(60.0, 0, 0, 80.0));
    root.setRight(text);
    root.setCenter(menuBox);
    return root;
  }
  
  private static void initMenuItems() {
    save = new MenuItem("Save");
    exit = new MenuItem("Exit Menu");
    exit.setOnActivate(() -> {
      //System.out.println(prevScene);
      //PokemonGame.primaryStage.setScene(prevScene);
      PokemonGame.currentState = PokemonGame.previousState;
      PokemonGame.stateChanged = true;
    });
    
    quit = new MenuItem("Quit");
    quit.setOnActivate(() -> System.exit(0));
    
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
        text.setFill(b ? Color.BLUE : Color.AQUA);
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
