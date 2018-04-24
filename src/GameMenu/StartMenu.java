package GameMenu;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StartMenu extends Application{

  private static final int width = 600;
  private static final int height = 400;
  public static void main(String args[]) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    BorderPane all = new BorderPane();
    GridPane buttonPane = new GridPane();
    GridPane imagesPane = new GridPane();
    GridPane pokePane = new GridPane();
    
    Button start = new Button("START");
    Button save = new Button("SAVE");
    Button inrt = new Button("INSTRUCTIONS");
    Button exit = new Button("EXIT");

    Image logo = new Image("file:src/images/GameLogo.png",400,200,true,true);
    Image displayPoke = new Image("file:src/images/223.gif",100,100,false,true);
    Image displayPoke1 = new Image("file:src/images/335.gif",100,100,false,true);
    Image displayPoke2 = new Image("file:src/images/474.gif",100,100,false,true);
    Image displayPoke3 = new Image("file:src/images/696.gif",100,100,false,true);
    Image displayPoke4 = new Image("file:src/images/111.gif",100,100,false,true);
    Image displayPoke5 = new Image("file:src/images/112.gif",105,100,false,true);
    Image displayPoke6 = new Image("file:src/images/113.gif",100,100,false,true);
    Image displayPoke7 = new Image("file:src/images/114.gif",100,100,false,true);
    
    imagesPane.getChildren().add(new ImageView(logo));
   
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

    buttonPane.add(start, 0,0);
    buttonPane.add(save, 0,1);
    buttonPane.add(inrt,0,2);
    buttonPane.add(exit,0,3);

    buttonPane.setHgap(10); // horizontal gap in pixels => that's what you are asking for
    buttonPane.setVgap(30);

    all.setCenter(buttonPane);
    all.setRight(imagesPane);

    BorderPane.setMargin(buttonPane, new Insets(60, 10, 10, 30));



    start.setOnMouseClicked(event -> {
      System.out.println(event.toString());
    });




    Scene scene = new Scene(all);
    scene.getStylesheets().add("file:src/GameMenu/style.css");
    primaryStage.setScene(scene);
    primaryStage.sizeToScene();
    primaryStage.show();
    primaryStage.setMinWidth(width);
    primaryStage.setMinHeight(height);
  }
}
