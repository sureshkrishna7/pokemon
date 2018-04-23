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
import model.Game;
import sun.net.www.content.audio.x_aiff;

/**
 * This view contains an animation of a pokemon trainer through a town named Cobville.
 * It can be added to any Pane elsewhere and started with the instance method
 * 
 *   public void animate()
 * 
 */
public class CobvilleTown extends GameBackground {

  private double lastValidPlayerDX;
  private double lastValidPlayerDY;
  private double playerPixelsFromTopBoundary;
  private double playerPixelsFromLeftBoundary;
  private double playerPixelsFromBottomBoundary;
  private int closeToTopPictureBounderSteps;
  private int closeToLeftPictureBounderSteps;
  private int closeToBottomPictureBounderSteps;
  private boolean afterTopLeftCornerCondition = false;
  private boolean afterBottomLeftCornerCondition = false;
  private boolean afterBottomLeftCornerCondition2 = false;


  public CobvilleTown(Point point, Image mapBackground) {
	  super(point, mapBackground);
	  this.setWidth(800);
	  this.setHeight(800);
	  
	  closeToTopPictureBounderSteps = 0;
	  closeToLeftPictureBounderSteps = 0;
	  closeToBottomPictureBounderSteps = 0;
	  
	  // will only set when close to border 
	  playerPixelsFromTopBoundary = 0;
	  playerPixelsFromLeftBoundary = 0;
	  playerPixelsFromBottomBoundary = 0;
    
	  // Create both images and draw both when the controller instructs
	  // spritsheet contains 6 sub images
	  character = new Image("file:src/images/Game_Boy_Advance - Pokemon_FireRed_LeafGreen - RivalBlueGreenGary.png", false);
	  background = mapBackground;
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
	  g2D.clearRect(0, 0, 800, 800);
	  background = changeOfMap;
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
    	
      lastValidPlayerDX = ((playerLocation.x) * 16);
      lastValidPlayerDY = ((playerLocation.y) * 16) - 8;
  	  sy = 5;
  	  sx = 50;
  	  sw = 17;
  	  sh = 21;
  	  
  	  dx = ((playerLocation.y) * 16);	  // LEFT TO RIGHT, y = col
  	  dy = ((playerLocation.x) * 16) - 8; // UP AND DOWN, x = row
  	  dw = 17;
  	  dh = 21;
  	  
  	  
      g2D.drawImage(background, dx - (cameraViewSize / 2),  dy - (cameraViewSize / 2), cameraViewSize, cameraViewSize, 0,  0, cameraViewSize, cameraViewSize);
      g2D.drawImage(character, sx, sy, sw, sh, cameraViewSize / 2.0,  cameraViewSize / 2.0, dw, dh);
    }
    

    @Override
    // This handle method gets called every so many milliseconds to
    // draw a varying subimage from a spritesheet over the desert dirt.
    public void handle(ActionEvent event) {
    	System.out.println("Cobvile animating");
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
        	//if (!(Game.getCharAtIndex(playerLocation.x, playerLocation.x) == 'E')) {
        		dy += (16 / 3.0);
        	//}
      	  
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
        
        //System.out.println("Player at  = "+ playerLocation);
        g2D.clearRect(0, 0, 800, 800);
        
        int row = playerLocation.x;
        int col = playerLocation.y;
        
        /*
         * Conditions when player is close to top-left boundaries of screen
         * rows <= 10 and col <= 10
         */
        if (row <= 10 && col <= 10 && keyCode == KeyCode.UP) {
        	closeToTopPictureBounderSteps++;
        	animateImage("left, top", "last Valid DX and DY");
        }
        else if (row <= 10 && col <= 10 && keyCode == KeyCode.DOWN) {
        	closeToTopPictureBounderSteps--;
        	animateImage("left, top", "last Valid DX and DY");
        }
        else if (row <= 10 && col <= 10 && keyCode == KeyCode.RIGHT) {
        	closeToLeftPictureBounderSteps--;
        	afterTopLeftCornerCondition = true;
        	animateImage("left, top", "last Valid DX and DY");
        }
        else if (row <= 10 && col <= 10 && keyCode == KeyCode.LEFT) {
        	closeToLeftPictureBounderSteps++;
        	animateImage("left, top", "last Valid DX and DY");
        }
        
        /*
         * Conditions when player is close to bottom-left boundaries of screen
         * rows > 20 and col <= 10
         */
        else if(row > 20 && col <= 10 && keyCode == KeyCode.UP) {
        	closeToBottomPictureBounderSteps--;
        	afterBottomLeftCornerCondition = true;
        	animateImage("left, bottom", "last Valid DX and DY");
        }
        else if(row > 20 && col <= 10 && keyCode == KeyCode.DOWN) {
        	closeToBottomPictureBounderSteps++;
        	animateImage("left, bottom", "last Valid DX and DY");
        }
        else if(row > 20 && col <= 10 && keyCode == KeyCode.LEFT) {
        	/*
        	 * NOTE: player last at bottom-left boundaries but
        	 * moves right towards only bottom boundary (never
        	 * updated bottom variable last step), subtract 3
        	 */
        	if (afterBottomLeftCornerCondition2) {
        		closeToLeftPictureBounderSteps -= 3;
        		afterBottomLeftCornerCondition2 = false;
        	}
        	closeToLeftPictureBounderSteps++;
        	animateImage("left, bottom", "last Valid DX and DY");
        }
        else if(row > 20 && col <= 10 && keyCode == KeyCode.RIGHT) {
        	closeToLeftPictureBounderSteps--;
        	animateImage("left, bottom", "last Valid DX and DY");
        }
        
        /*
         * Conditions when player is close to top of screen, rows <= 10
         */
        else if (row <= 10 && keyCode == KeyCode.UP) {
        	closeToTopPictureBounderSteps++;
        	animateImage("cam/2, top", "last Valid DX and DY");
        }
        else if(row <= 10 && keyCode == KeyCode.DOWN){
        	closeToTopPictureBounderSteps--;
        	animateImage("cam/2, top", "last Valid DX and DY");
        }
        
        else if(row <= 10 && keyCode == KeyCode.LEFT) {
        	lastValidPlayerDX = dx;
        	animateImage("cam/2, top", "dx and last Valid DY");
        	
        }
        else if(row <= 10 && keyCode == KeyCode.RIGHT) {
        	/*
        	 * NOTE: if the player was previously in the top left corner of screen, 
        	 * then the closeToLeftPictureBounderSteps was being calculated before, 
        	 * and then the player entered the area only close to the top of screen
        	 * boundary and didnt adjust the variable one last time.
        	 * Since timer calls 3 tics, each subtracts one, it would have needed
        	 * to subtract three the last adjustment.  
        	 */
        	if (afterTopLeftCornerCondition) {
        		closeToLeftPictureBounderSteps -= 3;
        		afterTopLeftCornerCondition = false;
        	}
        	
        	lastValidPlayerDX = dx;
        	animateImage("cam/2, top", "dx and last Valid DY");
        }
        
        /*
         * Conditions when player is close to left of screen, cols <= 10
         */
        else if(col <= 10 && keyCode == KeyCode.LEFT) {
        	lastValidPlayerDY = dy;
        	closeToLeftPictureBounderSteps++;
        	animateImage("left, cam/2", "last Valid DX and dy");
        }
        else if(col <= 10 && keyCode == KeyCode.RIGHT) {
        	lastValidPlayerDY = dy;
        	closeToLeftPictureBounderSteps--;
        	animateImage("left, cam/2", "last Valid DX and dy");
        }
        else if(col <= 10 && keyCode == KeyCode.UP) {
        	lastValidPlayerDY = dy;
        	/*
        	 * NOTE: same as special note above but after a player was 
        	 * in bottom-left corner of screen and entered an area where
        	 * only the left boundary was being counted, then the last 
        	 * call to this handle didnt adjust bottom variable
        	 * one last time, 3 tics (3 calls each subtracts by one), 
        	 * hence, subtract by three. 
        	 */
        	if (afterBottomLeftCornerCondition) {
        		closeToBottomPictureBounderSteps -= 3;
        		afterBottomLeftCornerCondition = false;
        	}
        	animateImage("left, cam/2", "last Valid DX and dy");
        }
        else if(col <= 10 && keyCode == KeyCode.DOWN) {
        	lastValidPlayerDY = dy;
        	animateImage("left, cam/2", "last Valid DX and dy");
        }
        
        /*
         * Conditions when player is close to bottom of screen, rows > 20
         */
        else if(row > 20 && keyCode == KeyCode.DOWN) {
        	lastValidPlayerDX = dx;
        	closeToBottomPictureBounderSteps++;
        	animateImage("cam/2, bottom", "dx and last Valid DY");
        }
        else if(row > 20 && keyCode == KeyCode.UP) {
        	lastValidPlayerDX = dx;
        	closeToBottomPictureBounderSteps--;
        	animateImage("cam/2, bottom", "dx and last Valid DY");
        }
        else if(row > 20 && keyCode == KeyCode.RIGHT) {
        	afterBottomLeftCornerCondition2 = true;
        	lastValidPlayerDX = dx;
        	animateImage("cam/2, bottom", "dx and last Valid DY");
        }
       
        /*
         * Map layout doesnt let a user get close enough to boundary on south east / south west
         * so we only have to worry about the case where close to south boundary
         */
        else if(row > 20 && keyCode == KeyCode.LEFT) {
        	lastValidPlayerDX = dx;
        	animateImage("cam/2, bottom", "dx and last Valid DY");
        }
        
        /**
         * Normal condition when player not close to boundaries
         */
        else {
        	closeToTopPictureBounderSteps = 0;
        	closeToLeftPictureBounderSteps = 0;
        	closeToBottomPictureBounderSteps = 0;
            lastValidPlayerDX = dx;
            lastValidPlayerDY = dy;
            g2D.drawImage(background, dx - (cameraViewSize / 2.0),  dy - (cameraViewSize / 2.0), cameraViewSize, cameraViewSize, 0,  0, cameraViewSize, cameraViewSize);
            g2D.drawImage(character, sx, sy, sw, sh, cameraViewSize / 2.0,  cameraViewSize / 2.0, dw, dh);	
        }

        System.out.println("DX --> " + dx);
        System.out.println("DY --> " + dy);
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
	
	
	public double getCameraViewHeight() {
		return cameraViewSize;
	}
	public double getCameraViewWidth() {
		return cameraViewSize;
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
	


}