package controller;

import java.awt.Point;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation.Status;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public class GameBackground extends Canvas{
	  protected Image character, background;
	  protected GraphicsContext g2D;
	  protected Timeline timeline;
	  protected Point playerLocation;
	  
	  protected int tic = 0;
	  protected double sx, sy, sw, sh, dx, dy, dw, dh;
	  protected String drawPlayerOverOrUnder;
	  protected KeyCode keyCode;
	  protected final double cameraViewSize = 20 * 16;
	  protected double lastValidPlayerDX;
	  protected double lastValidPlayerDY;
	  
	  
	  public GameBackground(Point point, Image mapBackground) {
		  playerLocation = point;
		  background = mapBackground;
		  g2D = this.getGraphicsContext2D();
		  System.out.println("Created  = " + playerLocation);
		  
		  // defaults, will be changed when movePlayer() is called
		  keyCode = KeyCode.UP;
		  drawPlayerOverOrUnder = "over";
		  
		  // Create a TimeLine that call AnimateStarter.handle every 100ms
		  // class AnimateStarter has two method stubs you have to complete.
		  timeline = new Timeline(new KeyFrame(Duration.millis(90), new AnimateStarter()));
		  timeline.setCycleCount(Animation.INDEFINITE);
	  }
	  
	  public boolean isTimelineAnimating() {
		  return timeline.getStatus() == Status.RUNNING;
	  }
	  
	  public void movePlayer(KeyCode code, String overOrUnder) {
		  keyCode = code;
		  drawPlayerOverOrUnder = overOrUnder;
		  tic = 0;
		  timeline.play();
	  }
	  public void setPlayerLocation(Point trainerLocation) {
		  playerLocation = trainerLocation;
	  }
		
	  public Point getPlayerLocation(Point trainerLocation) {
		  return playerLocation;
		
	  }
	
	  public void setDXandDY(Point playerLoc) {
		  System.out.println("SETTING DX and DY");
	  	  dx = ((playerLocation.y) * 16);	  // LEFT TO RIGHT, y = col
	  	  dy = ((playerLocation.x) * 16) - 8; 
	  }
	  public double getDx() {
		  return dx;
	  }
	  public void setDx(double newDx) {
		  dx = newDx;
	  }
	  
	  public double getDy() {
		  return dy;
	  }
	  public void setDy(double newDy) {
		  dy = newDy;
	  }
	  
	  public class AnimateStarter implements EventHandler<ActionEvent> {
		  
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
		    	
		      
		  }

		@Override
		public void handle(ActionEvent event) {}
	  }

}
