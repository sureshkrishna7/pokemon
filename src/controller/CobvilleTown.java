package controller;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import sun.net.www.content.audio.x_aiff;

/**
 * This view contains an animation of a running girl thought the desert.
 * It can be added to any Pane elsewhere and started with the instance method
 * 
 *   public void animate()
 * 
 * @author Rick Mercer
 */
public class CobvilleTown extends Canvas implements Observer {

  private Image character, background;
  private GraphicsContext g2D;
  private Timeline timeline;
  private Point playerLocation;
  private int tic = 0;
  double sx, sy, sw, sh, dx, dy, dw, dh;

  public CobvilleTown(Point point, Image mapBackground) {
	  this.setWidth(800);
	  this.setHeight(800);
	  playerLocation = point;
    
	  // Create both images and draw both when the controller instructs
	  character = new Image("file:src/images/Game_Boy_Advance - Pokemon_FireRed_LeafGreen - RivalBlueGreenGary.png", false);
	  // spritsheet contains 6 sub images
	  background = mapBackground;
	  g2D = this.getGraphicsContext2D();
	  setOnKeyReleased(new AnimateStarter());
	  System.out.println("set on release");
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
  	
	  sy = 0;
	  sx = 50;
	  sw = 15;
	  sh = 25;
	  
	  // LEFT TO RIGHT, y = col
	  dx = ((playerLocation.y) * 16);
	  // UP AND DOWN, x = row
	  dy = ((playerLocation.x) * 16) - 8;
	  dw = 15;
	  dh = 25;
	  
	  g2D.drawImage(background, 0, 0);
	  g2D.drawImage(character, sx, sy, sw, sh, dx,  dy, dw, dh);
  }
  
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("Must update CobvilleTown view");
	}

	  // Call this from the Application to begin the spritesheet animation
	public void animate() {
	
	}

	public void setBackGroundImage(Image changeOfMap) {
	  background = changeOfMap;
	}
	
  public void movePlayer(KeyCode code, String drawPlayerOverOrUnder) {
      
      if (KeyCode.UP == code ) {
    	  dy -= 16;
    	  if (drawPlayerOverOrUnder.equals("over")) {
        	  // get picture that makes trainer look going east
        	  sx = 110;
        	  sy = 0;
    	  }else {
        	  sx = 0;
        	  sy = 0;
    	  }

      }
      else if(KeyCode.DOWN == code) {
    	  dy += 16;
    	  if (drawPlayerOverOrUnder.equals("over")) {
        	  // get picture that makes trainer look going east
        	  sx = 50;
        	  sy = 0;
    	  }
    	  else {
        	  sx = 0;
        	  sy = 0;
    	  }

      }
      else if(KeyCode.RIGHT == code) {
    	  dx += 16;
    	  if (drawPlayerOverOrUnder.equals("over")) {
        	  // get picture that makes trainer look going east
        	  sx = 50;
        	  sy = 30; 
    	  }
    	  else {
        	  sx = 0;
        	  sy = 0;
    	  }

      }
      else if(KeyCode.LEFT == code) {
    	  dx -= 16;
    	  if (drawPlayerOverOrUnder.equals("over")) {
        	  // get picture that makes trainer look going west
        	  sx = 110;
        	  sy = 30;
    	  }
    	  else {
        	  sx = 0;
        	  sy = 0;
    	  }

      }
      else {
    	  //System.out.println("Direction = "+ direction);
    	  System.out.println("KeyCode   = "+ code);
      }
      
      g2D.drawImage(background, 0, 0);
      g2D.drawImage(character, sx, sy, sw, sh, dx,  dy, dw, dh);
  }
  private class AnimateStarter implements EventHandler<KeyEvent> {


    public AnimateStarter() {}
    

    @Override
    // This handle method gets called every so many milliseconds to
    // draw a varying subimage from a spritesheet over the desert dirt.
    public void handle(KeyEvent event) {
    }
  }

	public void setPlayerLocation(Point trainerLocation) {
		playerLocation = trainerLocation;
	}
	
	public Point getPlayerLocation(Point trainerLocation) {
		return playerLocation;
		
	}


}