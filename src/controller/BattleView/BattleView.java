package controller.BattleView;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.settings.GameSettings;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Map;

public class BattleView extends GameApplication {

  private Entity currentPokemon;
  private Entity background;
  
  @Override
  protected void initUI() {
      Text textPixels = new Text();
      textPixels.textProperty().bind(getGameState().intProperty("pixelsMoved").asString());
      textPixels.setTranslateX(50); // x = 50
      textPixels.setTranslateY(100); // y = 100

      getGameScene().addUINode(textPixels); // add to the scene graph
  }
  
  @Override
  protected void initGameVars(Map<String, Object> vars) {
    vars.put("pixelsMoved", 0);
  }
  
  @Override
  protected void initInput() {
    Input input = getInput();
    input.addAction(new UserAction("Move Right") {
      @Override
      protected void onAction() {
        currentPokemon.translateX(5);
        getGameState().increment("pixelsMoved", +5);
      }
    }, KeyCode.D);
  }
  
  @Override
  protected void initGame() {
    GameWorld world = getGameWorld();
    
    ImageView backgroundImg = new ImageView("file:/src/Images/GameBackground.gif");
    ImageView curPokeImg = new ImageView("file:src/images/696.gif");
    background = Entities.builder().at(0,0)
        .viewFromNode(backgroundImg)
        .buildAndAttach(world);
    currentPokemon = Entities.builder().at(0,400)
        .viewFromNodeWithBBox(curPokeImg)
        .buildAndAttach(world);
    
    
  }
  
  @Override
  protected void initSettings(GameSettings settings) {
    settings.setWidth(600);
    settings.setHeight(600);
    settings.setTitle("Battle!!!");
  }

  public static void main(String[] args) {
    launch(args);
  }
}
