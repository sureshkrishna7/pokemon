package controller.States;

import javafx.scene.Scene;

public class EmptyState implements IState{

  @Override
  public void update(float elapsedTime) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Scene render() {
    return null;
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
    return "empty";
  }

}
