package controller;



import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GameGUI extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  private Button startAnimationButton, endAnimationButton;
  private CobvilleTown localView;
  private BorderPane pane;
  
  @Override
  public void start(Stage stage) throws Exception {
//    BorderPane pane = new BorderPane();
	pane = new BorderPane();
    startAnimationButton = new Button("Start animation");
    pane.setTop(startAnimationButton);
    localView = new CobvilleTown();
    pane.setCenter(localView);
    BorderPane.setAlignment(startAnimationButton, Pos.BOTTOM_CENTER);
    startAnimationButton.setOnAction(new StartTimerButtonListener());
    Scene scene = new Scene(pane, 600, 300);
    stage.setScene(scene);
    stage.show();
  }
  
  // Add a listener that will start the Timeline's animation 
  public class StartTimerButtonListener implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
      localView.animate();
      //pane.setCenter(endAnimationButton);
    }
  }
}
