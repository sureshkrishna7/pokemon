package controller.States;

import java.awt.Point;

import controller.GameBackground;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public class Cave extends GameBackground{

	public  Cave(Point point, Image mapBackground) {
		super(point, mapBackground);
		this.setWidth(mapBackground.getWidth());
		this.setHeight(mapBackground.getHeight());
		
		// Create both images and draw both when the controller instructs
		// spritsheet contains 6 sub images
		character = new Image("file:src/images/Game_Boy_Advance - Pokemon_FireRed_LeafGreen - RivalBlueGreenGary.png", false);
		background = mapBackground;
		g2D = this.getGraphicsContext2D();
	
		// Create a TimeLine that call AnimateStarter.handle every 100ms
		// class AnimateStarter has two method stubs you have to complete.
		timeline = new Timeline(new KeyFrame(Duration.millis(75), new AnimateStarter()));
		timeline.setCycleCount(Animation.INDEFINITE);
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
		  	  
		  	  dx = ((playerLocation.x) * 16);	  // LEFT TO RIGHT, y = col
		  	  dy = ((playerLocation.y) * 16) - 8; // UP AND DOWN, x = row
		  	  dw = 17;
		  	  dh = 21;
		  	  
		      g2D.drawImage(background, 0, 0);
		      g2D.drawImage(character, sx, sy, sw, sh, dx,  dy, dw, dh);
		  }

		@Override
		public void handle(ActionEvent event) {
	        tic++;
	        
	        drawTrainer();
	        animateImageWithoutBoundary();
//	        System.out.println("DX = " + dx);
//	        System.out.println("DY = " + dy);

//	        g2D.drawImage(background, 0, 0);
//	        g2D.drawImage(character, sx, sy, sw, sh, dx,  dy, dw, dh);
	        // stop timeline from drawing after final sprite 
	        if (tic == 3) {
	        	timeline.stop();
	        }
	        
		}
	  }
	
}
