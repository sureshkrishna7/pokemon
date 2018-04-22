package controller.StateMachine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javafx.scene.Scene;
import model.Game;
import model.Menus.MainMenu;

public class StateStack implements IState {
  private Map<String, IState> mStates;
  private List<IState> stack;
  private IState top;
  private Scene scene;
  
  public StateStack(Game theGame) {
    mStates = new HashMap<>();
    mStates.put("mainMenu", new MainMenu(theGame));
    stack = new Stack<>();
  }
  
  public void push(String name) {
    top = mStates.get(name);
    stack.add(top);
  }
  
  public IState pop() {
    return stack.remove(stack.size() - 1);
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

}
