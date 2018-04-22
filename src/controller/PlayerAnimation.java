package controller;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;


import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;
import model.MainMap.MainMap;
import sun.net.www.content.audio.x_aiff;

/**
 * This view contains an animation of a running girl thought the desert.
 * It can be added to any Pane elsewhere and started with the instance method
 * 
 *   public void animate()
 * 
 * @author Rick Mercer
 */
public class PlayerAnimation extends Canvas {

  private Image character, background;
  private GraphicsContext g2D;
  private Timeline timeline;
  private Point playerLocation;
  private int tic = 0;
  double sx, sy, sw, sh, dx, dy, dw, dh;
  double olddx, olddy;
  private String drawPlayerOverOrUnder;
  private KeyCode keyCode;

  public PlayerAnimation(Point point, MainMap mainMap) {
    this.setWidth(mainMap.getMapImage().getWidth());
    this.setHeight(mainMap.getMapImage().getHeight());
    playerLocation = point;

    // defaults, will be changed when movePlayer() is called
    keyCode = KeyCode.UP;
    drawPlayerOverOrUnder = "over";

    // Create both images and draw both when the controller instructs
    // spritsheet contains 6 sub images
    character = new Image("file:src/images/Game_Boy_Advance - Pokemon_FireRed_LeafGreen - RivalBlueGreenGary.png", false);
    background = mainMap.getMapImage();
    g2D = this.getGraphicsContext2D();


    // Create a TimeLine that call AnimateStarter.handle every 100ms
    // class AnimateStarter has two method stubs you have to complete.
    timeline = new Timeline(new KeyFrame(Duration.millis(90), new AnimateStarter()));
    timeline.setCycleCount(Animation.INDEFINITE);
  }

  // Call this from the Application to begin the spritesheet animation
  public void animate() {

  }

  public void setBackGroundImage(Image changeOfMap) {
    background = changeOfMap;
    g2D.clearRect(0, 0, background.getWidth(), background.getHeight());
    g2D.drawImage(background, 0, 0);
    g2D.drawImage(character, sx, sy, sw, sh, dx, dy, dw, dh);
  }

  public void drawPlayerAtDoorSpot(Point pos) {
    olddx = dx;
    olddy = dy-32;
    dx = pos.x*8 - 8;
    dy = pos.y*36 - 8;
  }

  public void drawOutOfDoor() {
    dx = olddx;
    dy = olddy;
  }

  public void movePlayer(KeyCode code, String overOrUnder) {
    keyCode = code;
    drawPlayerOverOrUnder = overOrUnder;
    tic = 0;
    timeline.play();
  }

  public boolean isTimelineAnimating() {
    return timeline.getStatus() == Status.RUNNING;
  }


  private class AnimateStarter implements EventHandler<ActionEvent> {


    public AnimateStarter() {
      /*
        The images to draw are know as spritesheet (6 images) and dirt (the background)
        Use method drawImage with 9 arguments: 
        drawImage(theImage, sx, sy, sw, sh, dx, dy, dw, dh)
        sx the source rectangle's X coordinate position.
        sy the source rectangle's Y coordinate position.
        sw the source rectangle's width.
        sh the source rectangle's height.
        dx the destination rectangle's X coordinate position.
        dy the destination rectangle's Y coordinate position.
        dw the destination rectangle's width.
        dh the destination rectangle's height.
       */

      sy = 5;
      sx = 50;
      sw = 17;
      sh = 21;

      dx = ((playerLocation.y) * 16);	  // LEFT TO RIGHT, y = col
      dy = ((playerLocation.x) * 16) - 8; // UP AND DOWN, x = row
      dw = 17;
      dh = 21;

      g2D.clearRect(0, 0, 800, 800);
      g2D.drawImage(background, 0, 0);
      g2D.drawImage(character, sx, sy, sw, sh, dx,  dy, dw, dh);
    }


    @Override
    // This handle method gets called every so many milliseconds to
    // draw a varying subimage from a spritesheet over the desert dirt.
    public void handle(ActionEvent event) {
      tic++;

      if (KeyCode.UP == keyCode ) {
        dy -= (16 / 3.0);
        if (drawPlayerOverOrUnder.equals("over")) {
          // get picture that makes trainer look going north
          if (tic == 1) {
            sx = 128;
            sy = 5; 
          }
          else if (tic == 2) {
            sx = 147;
            sy = 5;
          }
          else if (tic == 3){
            sx = 109;
            sy = 5;
          }
        }else {
          sx = 0;
          sy = 0;
        }

      }
      else if(KeyCode.DOWN == keyCode) {
        dy += (16 / 3.0);
        if (drawPlayerOverOrUnder.equals("over")) {
          // get picture that makes trainer look going south
          if (tic == 1) {
            sx = 69;
            sy = 5;
          }
          else if (tic == 2) {
            sx = 89;
            sy = 5;
          }
          else if (tic == 3){
            sx = 50;
            sy = 5;
          }
        }
        else {
          sx = 0;
          sy = 0;
        }

      }
      else if(KeyCode.RIGHT == keyCode) {
        dx += (16 / 3.0);
        if (drawPlayerOverOrUnder.equals("over")) {
          // get picture that makes trainer look going east
          if (tic == 1) {
            sx = 68;
            sy = 29; 
          }
          else if (tic == 2) {
            sx = 89;
            sy = 29; 
          }
          else if (tic == 3){
            sx = 50;
            sy = 29; 
          }
        }
        else {
          sx = 0;
          sy = 0;
        }

      }
      else if(KeyCode.LEFT == keyCode) {
        dx -= (16 / 3.0);
        if (drawPlayerOverOrUnder.equals("over")) {
          // get picture that makes trainer look going west
          if (tic == 1) {
            sx = 127;
            sy = 29;
          }
          else if (tic == 2) {
            sx = 144;
            sy = 29;
          }
          else if (tic == 3){
            sx = 107;
            sy = 29;
          }
        }
        else {
          sx = 0;
          sy = 0;
        }

      }
      else {
        System.out.println("KeyCode   = "+ keyCode);
      }
      g2D.clearRect(0, 0, 800, 800);
      g2D.drawImage(background, 0, 0);
      g2D.drawImage(character, sx, sy, sw, sh, dx,  dy, dw, dh);

      // stop timeline from drawing after final sprite 
      if (tic == 3) {
        timeline.stop();
      }
    }
  }

  public void setPlayerLocation(Point trainerLocation) {
    playerLocation = trainerLocation;
  }

  public Point getPlayerLocation(Point trainerLocation) {
    return playerLocation;

  }


}