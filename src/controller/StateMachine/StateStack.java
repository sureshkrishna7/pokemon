package controller.StateMachine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import controller.CobvilleTown;
import javafx.scene.Scene;
import model.Game;
import model.Menus.MainMenu;

public class StateStack implements IState {
  // hash map of states, keys as strings, IState objects as values
  private Map<String, IState> mStates;
  // the stack we will use to keep track of states
  private List<IState> stack;
  private IState top;
  private Scene scene;
  
  public StateStack(Game theGame) {
    // constructor initializes HashMap and Stack
    mStates = new HashMap<>();
    mStates.put("cobTown", new CobvilleTown(theGame.getTrainerLocation(), theGame.getCurrCameraMap().getMapImage()));
    mStates.put("mainMenu", new MainMenu(theGame));
    stack = new Stack<>();
  }
  
  public IState getState(String id) {
    return mStates.get(id);
  }
  
  public List<IState> getStack(){
    return this.stack;
  }
  
  public void push(String name) {
    top = mStates.get(name);
    stack.add(top);
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
