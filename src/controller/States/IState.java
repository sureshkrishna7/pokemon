package controller.States;

import javafx.scene.Scene;

public interface IState {
    public String getName();
    public void update(float elapsedTime);
    public Scene render();
    public void onEnter();
    public void onExit();
}
 
