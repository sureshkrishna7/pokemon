package controller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;
import model.Attack;
import model.Door;
import model.Game;


//Simply Create the User and insert User into PokeTownMap, the rest of the maps will be embedded within PokeTownMap

public class PokemonGame {

  private static Scanner sc;
  private static Game theGame;
  private static Point playerStartLocation = new Point(11,25);
  private static Point playerOldLocation = new Point();
  
  public static void main(String[] args) {
	 theGame = new Game();
	 
	 char north = 'n';
	 char south = 's';
	 char west = 'w';
	 char east = 'e';
	 String direction = "n";
	 char gameLogic = 0;
	 theGame.setCurrCameraMap(theGame.getPokeTown());
	 /* if(direction.equals(""+north)) {
		System.out.print("It's True\n");
	 }
	  */
	 //scanner the next direction and act accordingly
	 while (true) {

		int i = 0;
		int j = 0;

		while(i < 10) {
		  j = 0;
		  while(j < 10) {
			 System.out.print(" "+theGame.getCamera()[i][j]);
			 j++;
		  }
		  System.out.println();
		  i++;
		}
		
		if (theGame.getCurrCameraMap() == theGame.getPokeTown().getSafariZoneMap())
			System.out.print("Move (n, e, s, w)? pt for PokemonTown!: ");
		else
			System.out.print("Move (n, e, s, w)? sz for Safari Zone!: ");

		sc = new Scanner(System.in);
		direction=sc.next().toLowerCase();

		if(direction.equals(""+north)) {
		  gameLogic = theGame.playerMove(north);
		}//hunter moved south
		else if(direction.equals(""+south)) {
		  gameLogic = theGame.playerMove(south);
		}//hunter moved west
		else if(direction.equals(""+west)) {
		  gameLogic = theGame.playerMove(west);
		}//hunter moved east
		else if(direction.equals(""+east)) {
		  gameLogic = theGame.playerMove(east);
		}
		else if (direction.equals("sz") && !(theGame.getCurrCameraMap() == theGame.getPokeTown().getSafariZoneMap())) {
			theGame.setTrainerLocation(playerStartLocation);
			theGame.setCurrCameraMap(theGame.getPokeTown().getSafariZoneMap());
		}
		else if (direction.equals("pt") && theGame.getCurrCameraMap() == theGame.getPokeTown().getSafariZoneMap()) {
			theGame.setTrainerLocation(playerStartLocation);
			theGame.setCurrCameraMap(theGame.getPokeTown());
		}

		
		if(gameLogic == 'D') {
			
		  System.out.print("Encountered a Door\n");
		  playerOldLocation.x = theGame.getTrainerLocation().x;
		  playerOldLocation.y = theGame.getTrainerLocation().y;
		  
		  Door door = (Door) theGame.getPokeTown().getItemsBoard()[theGame.getTrainerLocation().x][theGame.getTrainerLocation().y];
		  
		  theGame.setCurrCameraMap(door.getInsideMap());
		  theGame.setTrainerLocation(door.getInsideMap().getPlayerLocation());
		  
		  theGame.getCamera();
		}
		else if(gameLogic == 'P') {
		  System.out.print("Encountered a Pokemon\n");
		}
		else if(gameLogic == 'N') {
		  System.out.print("Encountered a NPC\n");
		}
		else if(gameLogic == ' ') {
			theGame.setTrainerLocation(playerOldLocation);
			theGame.setCurrCameraMap(theGame.getPokeTown());
		}	
		else if(gameLogic == 'S' || theGame.getTrainer().getSafariSteps() >= 500) { 
			theGame.setTrainerLocation(playerStartLocation);
			theGame.setCurrCameraMap(theGame.getPokeTown().getSafariZoneMap());
		}

	 }

	 // sc.close();
  }

}
