package controller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import model.Attack;
import model.Door;
import model.Game;
import model.Pokemon;
import model.Trainer;


//Simply Create the User and insert User into PokeTownMap, the rest of the maps will be embedded within PokeTownMap

public class PokemonGame {

  private static Scanner sc;
  private static Game theGame;
  private static Point playerOldLocation = new Point();
  
  public static void SafariEncounter(Trainer currTrainer, Pokemon currPoke) {
	  System.out.println("You have encountered a " + currPoke.getPokemon());
	  String nextMove = "z";
	  int roundCounter = 0;
	  while (!nextMove.equals("f")) {
		  if (roundCounter == currPoke.maxDuration) {
			  System.out.println("Pokemon Ran Away");
			  return;
		  }
		  System.out.println("Choose next move (r, b, p, f: )");
		  sc = new Scanner(System.in);
		  nextMove=sc.next().toLowerCase();
		  if (nextMove.equals("r")) {		//Throws a Rock
			  roundCounter++;
			  currPoke.catchLikelihood--;
			  currPoke.runLikelihood--;
		  }
		  if (nextMove.equals("b")) {		//Throws Bait
			  roundCounter++;
			  currPoke.catchLikelihood++;
			  currPoke.runLikelihood++;
		  }
		  if (nextMove.equals("p")) {		//Throws Pokeball
			  roundCounter++;
			  Random random = new Random();
			  int x = random.nextInt(currPoke.catchLikelihood);
			  if (x == 0) {
				  System.out.println("Pokemon Caught");
				  currTrainer.addPokemon(currPoke.getPokemon());
				  return;
			  }
		  }
		  Random random = new Random();
		  int run = random.nextInt(currPoke.runLikelihood);
		  if (run == 0) {
			  System.out.println("Pokemon Ran Away");
			  return;
		  }
	  }
  }
  
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
//			 System.out.print(" "+theGame.getCamera(theGame.getPokeTown())[i][j]);
			 System.out.print(" "+theGame.getCamera()[i][j]);
			 j++;
		  }
		  System.out.println();
		  i++;
		}
		
		//System.out.println(theGame.getTrainerLocation().x  + "" + theGame.getTrainerLocation().y);
		//System.out.print(Arrays.toString(theGame.getCamera()));


		System.out.print("Move (n, e, s, w)?");

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
		else if (gameLogic == 'S') {				//S represents Safari Pokemon for now, until Safari Map is created
			System.out.println("Encountered a Safari Pokemon");
			Pokemon b = new Pokemon("Sandslash", 'C', 'I', null);
			SafariEncounter(theGame.getTrainer(), b);
		}
		else if(gameLogic == 'N') {
		  System.out.print("Encountered a NPC\n");
		}
		else if(gameLogic == ' ') {
			theGame.setTrainerLocation(playerOldLocation);
			theGame.setCurrCameraMap(theGame.getPokeTown());
		}

	 }

	 // sc.close();
  }

}
