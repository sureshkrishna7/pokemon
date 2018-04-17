package controller;

import java.awt.Point;

/*
 * Only one turnin needed to D2L Assignment '6-April) Animation'
 * 
 * Write one, two or three names here. You need not be on the same final project team
 * 
 *  1. _
 *  
 *  2. _
 *  
 *  3. _
 */
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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
public class CobvilleTown extends Canvas {

  private Image spritesheet, dirt;
  private GraphicsContext g2D;
  private Timeline timeline;
  private Point playerLocation;

  public CobvilleTown(Point point) {
    this.setWidth(800);
    this.setHeight(800);
    playerLocation = point;
    // TODO 1: Create both images and draw both when the controller instructs
    spritesheet = new Image("file:src/images/spriteSheet.png", false);
    // spritsheet contains 6 sub images
    dirt = new Image("file:src/images/SuperiorCity.png", false);
    g2D = this.getGraphicsContext2D();
    
    // Create a TimeLine that call AnimateStarter.handle every 100ms
    // class AnimateStarter has two method stubs you have to complete.
    timeline = new Timeline(new KeyFrame(Duration.millis(130), new AnimateStarter()));
    timeline.setCycleCount(Animation.INDEFINITE);
  }

  // Call this from the Application to begin the spritesheet animation
  public void animate() {
    timeline.play();
  }

  // TODO 2: Complete this class with its handle method to show 15 different 
  // views of the runner by cycling through the spritesheet three times. 
  // Begin at the upper left corner. Moving 10 pixels to the right each time.
  // The image two draw is 90 x 90 pixels.
  private class AnimateStarter implements EventHandler<ActionEvent> {
    private int tic = 0;
    double sx, sy, sw, sh, dx, dy, dw, dh;

    public AnimateStarter() {
      // TODO ICA: And the only one: Complete this animation to show 21 drawImages.
      // You need to add code the constructor to set the 8 instance variables
      // and draw the first image.  The handle messsage show shoe different
      // subImages moving left to right across the background.
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
      //sy = sx = dx = dy = 0;
      //sw = sh = dw = dh = 90;
    	
    	  sy = sx = 0;
    	  sw = sh = 90;
//    	  dx = playerLocation.x;
//    	  dy = playerLocation.y;
    	  
    	  
    	  //want to get to 9 , 19 (0 indexed)
    	  // LEFT TO RIGHT 
    	  dx = (playerLocation.y * 16);
    	  // UP AND DOWN
    	  dy = (playerLocation.x * 16);
    	  dw = dh = 20;
    	  
    	  System.out.println("SX = " + sx);
    	  System.out.println("SY = " + sy);
    	  System.out.println("DX = " + dx);
    	  System.out.println("DY = " + dy);
    	  
      g2D.drawImage(dirt, 0, 0);
      g2D.drawImage(spritesheet, sx, sy, sw, sh, dx,  dy, dw, dh);
    }
    

    @Override
    // This handle method gets called every so many milliseconds to
    // draw a varying subimage from a spritesheet over the desert dirt.
    public void handle(ActionEvent event) {
      tic++;
      dx += 10;
      sx = (sx < 400) ? sx += 90 : 0;
      dx = (dx < 200) ? dx += 10 : 0;
      g2D.drawImage(dirt, 0, 0);
      g2D.drawImage(spritesheet, sx, sy, sw, sh, dx,  dy, dw, dh);
      //if(tic > 21)timeline.stop();
    }
  }

	public void setPlayerLocation(Point trainerLocation) {
		playerLocation = trainerLocation;
		
	}
	
	public Point getPlayerLocation(Point trainerLocation) {
		return playerLocation;
		
	}
}