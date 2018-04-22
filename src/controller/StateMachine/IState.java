package controller.StateMachine;

import javafx.scene.Scene;

public interface IState {
    public void update(float elapsedTime);
    public Scene render();
    public void onEnter();
    public void onExit();
}
 
