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

public class House6 extends GameBackground{

	public  House6(Point point, Image mapBackground) {
		super(point, mapBackground);
		  this.setWidth(mapBackground.getWidth());
		  this.setHeight(mapBackground.getHeight());
		// Create both images and draw both when the controller instructs
		// spritsheet contains 6 sub images
		character = new Image("file:src/images/Game_Boy_Advance - Pokemon_FireRed_LeafGreen - RivalBlueGreenGary.png", false);
		background = mapBackground;
		g2D = this.getGraphicsContext2D();
		
		//playerLocation = new Point(9,19);
		
		// Create a TimeLine that call AnimateStarter.handle every 100ms
		// class AnimateStarter has two method stubs you have to complete.
		timeline = new Timeline(new KeyFrame(Duration.millis(90), new AnimateStarter()));
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
	        System.out.println("Mart  animating");
	        System.out.println("(Mart 69) TEST playerLocation: " + playerLocation);
	        if (KeyCode.UP == keyCode ) {
	      	  dy -= (16 / 3.0);
	      	  if (drawPlayerOverOrUnder.equals("over")) {
	      		System.out.println("TEST handle if");
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
	      		System.out.println("TEST handle else");
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
	        
	        System.out.println("DX --> " + dx);
	        System.out.println("DY --> " + dy);
	        
	        g2D.drawImage(background, 0, 0);
	        g2D.drawImage(character, sx, sy, sw, sh, dx,  dy, dw, dh);
	        // stop timeline from drawing after final sprite 
	        if (tic == 3) {
	        	timeline.stop();
	        }
	        
		}
	  }
	
}
