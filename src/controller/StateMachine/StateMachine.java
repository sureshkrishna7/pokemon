package controller.StateMachine;

import java.util.HashMap;
import java.util.Map;

public class StateMachine {
  Map<String, IState> mStates;
  IState curState;
  
  public StateMachine() {
    mStates = new HashMap<>();
    curState = new EmptyState();
  }
  
  public void render() {
    curState.render();
  }
  
  public void change(String name) {
    curState.onExit();
    curState = mStates.get(name);
    curState.onEnter();
  }
  
  public void add(String name, IState state) {
    mStates.put(name, state);
  }
}
