
/**
 * 
 * @author Daniel Lopez
 */

package controller;

import java.awt.Point;

import controller.States.Cave;
import controller.States.CobvilleTown;
import controller.States.IState;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation.Status;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.util.Duration;

public class GameBackground extends Canvas implements IState{
	  protected Image character, background;
	  protected GraphicsContext g2D;
	  protected Timeline timeline;
	  protected Point playerLocation;
	  protected double lastValidPlayerDX;
	  protected double lastValidPlayerDY;
	  protected double playerPixelsFromTopBoundary;
	  protected double playerPixelsFromLeftBoundary;
	  protected double playerPixelsFromBottomBoundary;
	  protected int closeToTopPictureBounderSteps;
	  protected int closeToLeftPictureBounderSteps;
	  protected int closeToBottomPictureBounderSteps;

	  protected boolean afterTopLeftCornerCondition = false;
	  protected boolean afterBottomLeftCornerCondition = false;
	  protected boolean afterBottomLeftCornerCondition2 = false;
	  
	  protected int tic = 0;
	  protected double sx, sy, sw, sh, dx, dy, dw, dh;
	  protected String drawPlayerOverOrUnder;
	  protected KeyCode keyCode;
	  protected double cameraViewSize = 15 * 16;
	  
	  
	  public GameBackground(Point point, Image mapBackground) {
		  playerLocation = point;
		  background = mapBackground;
		  g2D = this.getGraphicsContext2D();
		  
		  // defaults, will be changed when movePlayer() is called
		  keyCode = KeyCode.UP;
		  drawPlayerOverOrUnder = "over";
		  
		  // Create a TimeLine that call AnimateStarter.handle every 100ms
		  // class AnimateStarter has two method stubs you have to complete.
//		  timeline = new Timeline(new KeyFrame(Duration.millis(75), new AnimateStarter()));
//		  timeline.setCycleCount(Animation.INDEFINITE);
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

	  public void drawTrainer() {
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
	}
	
	public void animateImageWithoutBoundary() {
		System.out.println("Background size = height:" + background.getHeight()  + ", width: " + background.getWidth());
		System.out.println("dx   = "+ (dx - (cameraViewSize / 2.0)));
		System.out.println("dy   = "+ (dy - (cameraViewSize / 2.0)));
		
		 g2D.clearRect(0, 0, 800, 800);
		double backgroundSX = dx - (cameraViewSize / 2.0);
		double backgroundSY = dy - (cameraViewSize / 2.0);
		
//		if (backgroundDX < 0 && backgroundDY < 0) {
//			System.out.println("Test 2");
//			g2D.drawImage(background, 0, 0, cameraViewSize, cameraViewSize, dx - (cameraViewSize / 2.0),  dy - (cameraViewSize / 2.0), cameraViewSize, cameraViewSize);
//		}
//		else if(backgroundDX < 0) {
//			System.out.println("Test 2");
//			g2D.drawImage(background, 0,  backgroundDY, cameraViewSize, cameraViewSize, dx - (cameraViewSize / 2.0),  0, cameraViewSize, cameraViewSize);
//		}
//		else if(backgroundDY < 0) {
//			System.out.println("Test 2");
//			g2D.drawImage(background, backgroundDX, 0, cameraViewSize, cameraViewSize, 0,  dy - (cameraViewSize / 2.0), cameraViewSize, cameraViewSize);
//		}
//		else {
//			g2D.drawImage(background, backgroundDX,  backgroundDY, cameraViewSize, cameraViewSize, 0,  0, cameraViewSize, cameraViewSize);
//		}
		g2D.drawImage(background, backgroundSX,  backgroundSY, cameraViewSize, cameraViewSize, 0,  0, cameraViewSize , cameraViewSize);
        g2D.drawImage(character, sx, sy, sw, sh, cameraViewSize / 2.0, cameraViewSize / 2.0, dw , dh);	
	}

	public void animateImage(String playerDrawStrategy, String dXOrDyDrawStrategy) {
		
		playerPixelsFromTopBoundary  = (cameraViewSize / 2.0) - ((closeToTopPictureBounderSteps * 16) / 3.0);
		playerPixelsFromLeftBoundary = (cameraViewSize / 2.0) - ((closeToLeftPictureBounderSteps * 16) / 3.0);
		playerPixelsFromBottomBoundary = (cameraViewSize / 2.0) + ((closeToBottomPictureBounderSteps * 16) / 3.0);
		
		if (dXOrDyDrawStrategy.equals("last Valid DX and DY")) {
			if (playerDrawStrategy.equals("cam/2, top")) {
				g2D.drawImage(background, lastValidPlayerDX - (cameraViewSize / 2.0),  lastValidPlayerDY - (cameraViewSize / 2.0), cameraViewSize, cameraViewSize, 0,  0, cameraViewSize, cameraViewSize);
				g2D.drawImage(character, sx, sy, sw, sh, cameraViewSize / 2.0, playerPixelsFromTopBoundary, dw, dh);
			}
			else if (playerDrawStrategy.equals("cam/2, bottom")) {
				g2D.drawImage(background, lastValidPlayerDX - (cameraViewSize / 2.0),  lastValidPlayerDY - (cameraViewSize / 2.0), cameraViewSize, cameraViewSize, 0,  0, cameraViewSize, cameraViewSize);
				g2D.drawImage(character, sx, sy, sw, sh, cameraViewSize / 2.0, playerPixelsFromBottomBoundary, dw, dh);
			}
			else if (playerDrawStrategy.equals("cam/2, cam/2")) {
				g2D.drawImage(background, lastValidPlayerDX - (cameraViewSize / 2.0),  lastValidPlayerDY - (cameraViewSize / 2.0), cameraViewSize, cameraViewSize, 0,  0, cameraViewSize, cameraViewSize);
				g2D.drawImage(character, sx, sy, sw, sh, cameraViewSize / 2.0, cameraViewSize / 2.0, dw, dh);
			}
			else if (playerDrawStrategy.equals("left, cam/2")) {
				g2D.drawImage(background, lastValidPlayerDX - (cameraViewSize / 2.0),  lastValidPlayerDY - (cameraViewSize / 2.0), cameraViewSize, cameraViewSize, 0,  0, cameraViewSize, cameraViewSize);
				g2D.drawImage(character, sx, sy, sw, sh, playerPixelsFromLeftBoundary, cameraViewSize / 2.0, dw, dh);
			}
			else if (playerDrawStrategy.equals("left, top")) {
				g2D.drawImage(background, lastValidPlayerDX - (cameraViewSize / 2.0),  lastValidPlayerDY - (cameraViewSize / 2.0), cameraViewSize, cameraViewSize, 0,  0, cameraViewSize, cameraViewSize);
				g2D.drawImage(character, sx, sy, sw, sh, playerPixelsFromLeftBoundary, playerPixelsFromTopBoundary, dw, dh);
			}
			else if (playerDrawStrategy.equals("left, bottom")) {
				g2D.drawImage(background, lastValidPlayerDX - (cameraViewSize / 2.0),  lastValidPlayerDY - (cameraViewSize / 2.0), cameraViewSize, cameraViewSize, 0,  0, cameraViewSize, cameraViewSize);
				g2D.drawImage(character, sx, sy, sw, sh, playerPixelsFromLeftBoundary, playerPixelsFromBottomBoundary, dw, dh);
			}
		}
		else if (dXOrDyDrawStrategy.equals("last Valid DX and dy")) {
			if (playerDrawStrategy.equals("left, cam/2")) {
				g2D.drawImage(background, lastValidPlayerDX - (cameraViewSize / 2.0),  dy - (cameraViewSize / 2.0), cameraViewSize, cameraViewSize, 0,  0, cameraViewSize, cameraViewSize);
				g2D.drawImage(character, sx, sy, sw, sh, playerPixelsFromLeftBoundary, cameraViewSize / 2.0, dw, dh);
			}
		}
		else if (dXOrDyDrawStrategy.equals("dx and last Valid DY")) {
			if (playerDrawStrategy.equals("cam/2, top")) {
				g2D.drawImage(background, dx - (cameraViewSize / 2.0), lastValidPlayerDY - (cameraViewSize / 2.0), cameraViewSize, cameraViewSize, 0,  0, cameraViewSize, cameraViewSize);
				g2D.drawImage(character, sx, sy, sw, sh, cameraViewSize / 2.0, playerPixelsFromTopBoundary, dw, dh);
			}
			if (playerDrawStrategy.equals("cam/2, bottom")) {
				g2D.drawImage(background, dx - (cameraViewSize / 2.0), lastValidPlayerDY - (cameraViewSize / 2.0), cameraViewSize, cameraViewSize, 0,  0, cameraViewSize, cameraViewSize);
				g2D.drawImage(character, sx, sy, sw, sh, cameraViewSize / 2.0, playerPixelsFromBottomBoundary, dw, dh);
			}
		}


	}
	
	
	public double getCameraViewHeight() {
		return cameraViewSize;
	}
	

  

	public double getCameraViewWidth() {
		return cameraViewSize;
  	}

	  @Override
	  public void update(float elapsedTime) {
	    // TODO Auto-generated method stub

	  }

	  @Override
	  public Scene render() {
	    BorderPane bp = new BorderPane();
	    bp.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0), new Insets(0,0,0,0) )));
	    bp.setCenter(this);
	    Scene scene = null;
	    Scale scale = null;
	    if (this instanceof CobvilleTown || this instanceof Cave) {
	    	System.out.println("instance of Cobville Town");
	    	cameraViewSize = 15 * 16;
		    scene = new Scene(bp, this.getCameraViewWidth() * 1.3, this.getCameraViewHeight() * 1.3);
		    scale = new Scale(1.3, 1.3);
	    }else {
	    	System.out.println("NOT instance of Cobville Town");
	    	cameraViewSize = 10 * 16;
		    scene = new Scene(bp, this.getCameraViewWidth() * 1.5, this.getCameraViewHeight() * 1.5);
		    scale = new Scale(1.5, 1.5);
	    }

	    scale.setPivotX(0);
	    scale.setPivotY(0);
	    scene.getRoot().getTransforms().setAll(scale);
	    return scene;
	  }

	  @Override
	  public void onEnter() {
	    // TODO Auto-generated method stub

	  }

	  @Override
	  public void onExit() {
	    System.out.println("Changing state, exiting CobvilleTown");

	  }



	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
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
		public void handle(ActionEvent event) {
			// currently implemented differently by each sublcass 
			
		}
		
		
		
		
	  }

}
