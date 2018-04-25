package controller.States;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import controller.PokemonGame;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Game;
import model.Menus.MainMenu;

public class StateStack implements IState {
  // hash map of states, keys as strings, IState objects as values
  private Map<String, IState> mStates;
  // the stack we will use to keep track of states
  private List<IState> stack;
  private IState top;
  private Scene scene;
  
  public StateStack(Game theGame, Stage stage, PokemonGame pokemonController) {
    // constructor initializes HashMap and Stack
    mStates = new HashMap<>();
    mStates.put("cobTown", new CobvilleTown(theGame.getTrainerLocation(), theGame.getCurrCameraMap().getMapImage()));
    mStates.put("mainMenu", new MainMenu(theGame));
    mStates.put("start", new StartScreen(stage, pokemonController));
    stack = new Stack<>();
  }
  
  public boolean isEmpty() {
    return stack.size() == 0;
  }
  
  public boolean isStateInStack(String id) {
    if(stack.contains(mStates.get(id))){
      return true;
    }
    else {
      return false;
    }
  }
  public IState getState(String id) {
    return mStates.get(id);
  }
  
  public List<IState> getStack(){
    return this.stack;
  }
  
  public boolean push(String name) {
    top = mStates.get(name);
    stack.add(top);
    return true;
  }
  
  public IState pop() {
    return stack.remove(stack.size() - 1);
  }
  
  public String peek() {
    return stack.get(stack.size() - 1).getName();
  }
  
  @Override
  public void update(float elapsedTime) {
    top = stack.get(stack.size() - 1);
    top.update(elapsedTime);
  }
  
  @Override
  public Scene render() {
    top = stack.get(stack.size() - 1);
    scene = top.render();
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
