package controller.States;

import controller.PokemonGame;
import controller.PokemonGame.STATE;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;

public class StartInstruct implements IState{
  private static TextArea textBox;
  private Scene scene;
  private String textString;

  public StartInstruct() {
    textBox = new TextArea();
    textBox.setEditable(false);
    textBox.setMaxWidth(600);
    textBox.setMaxHeight(400);

    textString = new String();
    textString = "Welcome to Pokemon:\n" + 
        "\n" + 
        "There are 2 game modes in this type of game. Safari Zone & Battle:\n" + 
        "\n" + 
        "You can explore the City and find pokemon to battle.\n" + 
        "You have the ability to change pokemon, use Items, fight or run.\n" + 
        "\n" + 
        "You can also enter the safari zone to capture new Pokemon.\n" + 
        "Here, you cannot battle pokemon, but you can use items to \n" + 
        "assist you in capturing pokemon.\n" + 
        "Rocks will make it easier to catch a pokemon, but more likely\n" + 
        "to run.\n" + 
        "Bait will make the pokemon less likely to run.\n" + 
        "Use the safari ball to catch safari pokemon\n" + 
        "\n" + 
        "Explore the map, battle pokemon, and capture pokemon to add\n" + 
        "to your team.\n" + 
        "\n" + 
        "Have Fun!";
    textBox.setText(textString);

    scene = new Scene(textBox);
    scene.setOnKeyPressed(event -> {
      if(event.getCode() == KeyCode.ESCAPE) {
        PokemonGame.setState(STATE.STARTMENU);
      }
    });
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
    // TODO Auto-generated method stub

  }



}

