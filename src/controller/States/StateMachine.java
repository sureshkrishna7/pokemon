package controller.States;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import controller.States.CobvilleTown;
import controller.PokemonGame;
import controller.PokemonGame.STATE;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Game;
import model.Menus.MainMenu;
import model.Menus.StartMenu;

public class StateMachine implements IState {
  // hash map of states, keys as strings, IState objects as values
  private Map<STATE, IState> mStates;
  // the stack we will use to keep track of states
  private List<STATE> stack;
  private STATE top;
  private Scene scene;
  
  public StateMachine(Game theGame, Stage stage, PokemonGame pokemonController) {
    // constructor initializes HashMap and Stack
    mStates = new HashMap<>();
    mStates.put(STATE.STARTMENU, new StartMenu());
    mStates.put(STATE.COBVILLETOWN, new CobvilleTown(theGame.getTrainerLocation(), theGame.getCurrCameraMap().getMapImage()));
    mStates.put(STATE.MENU, new MainMenu(theGame));
    mStates.put(STATE.START, new StartScreen(stage));
    mStates.put(STATE.MART, new Mart(theGame.getTrainerLocation(), theGame.getCurrCameraMap().getMapImage()));
    stack = new Stack<>();
  }
  
  public void updateIState(STATE state, IState iState) {
    mStates.put(state, iState);
  }
  
  public boolean isEmpty() {
    return stack.size() == 0;
  }
  
  public boolean isStateInStack(STATE st) {
    if(stack.contains(st)){
      return true;
    }
    else {
      return false;
    }
  }
  
  public IState getIState(STATE st) {
    return mStates.get(st);
  }
  
  @Override
  public void update(float elapsedTime) {
    top = stack.get(stack.size() - 1);
    //top.update(elapsedTime);
  }
  
  @Override
  public Scene render() {
    top = stack.get(stack.size() - 1);
    scene = mStates.get(top).render();
    return scene;
  }
  
  @Override
  public void onEnter() {
    // TODO Auto-generated method stub
  }
  
  @Override
  public void onExit() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public String getName() {
    return null;
  }
}
