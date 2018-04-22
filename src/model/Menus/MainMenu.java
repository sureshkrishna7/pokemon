package model.Menus;

import java.util.ArrayList;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Game;
import model.Pokemon;
import model.UsableItems.UsableItem;

public class MainMenu {
  private static final Font FONT1 = Font.font("Serif",FontWeight.NORMAL, 18);
  private static final Font FONT2 = Font.font("Serif", FontWeight.BOLD, 26);
  //private static final Font FONT = new Font("Courier", 18.0);
  private Scene scene;
  private HBox menuBox;
  private int currentItem = 0;
  
  public MainMenu(Game theGame) {  
    scene = new Scene(getGameMenu(theGame));
    scene.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.LEFT) {
        if (currentItem > 0) {
          getMenuItem(currentItem).setActive(false);
          getMenuItem(--currentItem).setActive(true);
        }
      }
      
      if (event.getCode() == KeyCode.RIGHT) {
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
  
  @SuppressWarnings("static-access")
  private Parent getGameMenu(Game theGame) {
    StackPane root = new StackPane();
    root.setPrefSize(600, 600);
    root.setPadding(new Insets(15,15,15,15));
    
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
    BackgroundFill fill = new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY,
            Insets.EMPTY);
    Background background = new Background(fill);
    root.setBackground(background);
    System.out.println(text.getFont().toString());
    MenuItem exit = new MenuItem("EXIT");
    exit.setOnActivate(() -> System.exit(0));
    menuBox = new HBox(10, new MenuItem("SAVE"), new MenuItem("QUIT"), exit);
    menuBox.setPadding(new Insets(0, 0, 0, 80.0));
    root.getChildren().addAll(text, menuBox);
    root.setAlignment(text, Pos.TOP_CENTER);
    root.setAlignment(menuBox, Pos.BOTTOM_CENTER);
    return root;
  }
  
  private static class MenuItem extends HBox {
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
  
  
}
