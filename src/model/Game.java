package model;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Game {

  private boolean didGameEnd;
  private boolean playerWon;

  private Map pokeTown;
  private Map safariZone;
  private Map currCameraMap;
  private Trainer ash;

  private final int camWidth = 10;
  private final int camHeight = 10;
  private Items[][] cameraArray; 
  private Pokemon[] pokemons;

  private char[][] camera;
  
  public Game() {

	 didGameEnd = false;
	 playerWon = false;

	 pokeTown = new Map();
	 safariZone = new Map();
	 currCameraMap = new Map();
	 pokemons = new Pokemon[10];
	 
	 try {
		pokeTown.geniusMethod("src/PokemonTown.txt");
		
		Scanner scanner = new Scanner(new File("src/PokemonNames.txt"));
		 
		for(int i = 0; i < 10; i++) {
			pokemons[i] = new Pokemon("");
			String line = scanner.nextLine();
			String[] pokemonInitializer = new String[3];
			pokemonInitializer = line.split("\\s+");

			Pokemon currentPokemon = new Pokemon(pokemonInitializer[0], pokemonInitializer[1], pokemonInitializer[2]);	
			pokemons[i] = currentPokemon;
		}

		//Location of the pokemon is important
		//board[i][j] = (currentPokemon);
		//currentPokemon.setLocation(i, j);
		
		
//		 try{

//		 }
//		 catch (NoSuchElementException e) {
//			rp = new Scanner(new File("src/PokemonNames.txt"));
//		 }
		
		// in Poketown bc player must be able to go to 'S' and be transferred to safariZone
		// genuisMethod will read the txt file and convert it to a map, assigning the values 
		// for items[][] board and char[][] board for safariZone.
		safariZone = pokeTown.getSafariZoneMap();
		safariZone.geniusMethod("src/SafariZone.txt");
		
	 } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	 }

	 ash = new Trainer(new String("Ash"), null);
	 //ash.setLocation(27, 25);
	 //ash.setLocation(5,12);
	 ash.setLocation(5,20);


	 this.cameraArray = new Items[camHeight][camWidth];
	 this.camera = new char[camHeight][camWidth];
  }
  
  
  public void setCurrCameraMap(Map map) {
	  currCameraMap = map;
  }

  public void setTrainerLocation(Point point) {
	  ash.setLocation(point);
  }
  
  // direction could be n,s,e,w or N, S, E, W
  public char playerMove(char direction) {

	 Point player = ash.getLocation();
	 Point newPoint = new Point();
	 
	 if(direction == 'n' || direction == 'N') {
		newPoint.x = player.x - 1;
		newPoint.y = player.y;

		if(currCameraMap.isWalkable(newPoint)) {
		  ash.setLocation(newPoint);
		  return currCameraMap.getCharacterFromLocation(newPoint);
		}
	 }
	 else if(direction == 's' || direction == 'S') {
		newPoint.x = player.x + 1;
		newPoint.y = player.y;

		if(currCameraMap.isWalkable(newPoint)) {
		  ash.setLocation(newPoint);
		  return currCameraMap.getCharacterFromLocation(newPoint);
		}
	 }
	 else if(direction == 'e' || direction == 'E') {
		newPoint.x = player.x;
		newPoint.y = player.y + 1;

		if(currCameraMap.isWalkable(newPoint)) {
		  ash.setLocation(newPoint);
		  return currCameraMap.getCharacterFromLocation(newPoint);
		}
	 }
	 else if(direction == 'w' || direction == 'W') {
		newPoint.x = player.x;
		newPoint.y = player.y - 1;

		if(currCameraMap.isWalkable(newPoint)) {
		  ash.setLocation(newPoint);
		  return currCameraMap.getCharacterFromLocation(newPoint);
		}
	 }
	 return '0';
  }
  
  public Point getTrainerLocation() {
	  return ash.getLocation();
  }
  
  public void setSafariZone(Map newMap) {
	  safariZone = newMap;
  }
  public Map getSafariZone() {
	  return safariZone;
  }
  

  public char[][] getCamera(){
	 Point playerPos = ash.getLocation(); 
	 
	 
	 int g = playerPos.x - 4;
	 
	 int h = playerPos.y - 4;
	 
	 int i = 0;
	 int j = 0;
	 
	 int x;
	 int y;
	 
	 //System.out.println("CurrentMapHeight = " +  currCameraMap.getMapHeight());
	 //System.out.println("CurrentMapWidth  = " +  currCameraMap.getMapWidth());
	 
	 while(i < camHeight) {
		
		x = g  + i;
		j = 0;
		while(j < camWidth) {
		  
		  y = h  + j;
		  
		  if(x < 0 || x >= currCameraMap.getMapHeight() || y < 0 || y >= currCameraMap.getMapWidth()) {
			 camera[i][j] = '1';
		  }
		  else if(i == (camHeight-1)/2 && j == (camWidth-1)/2){ 
			 //Player Position is dynamic, he is NOT placed in a MAP TILE
			 //His position is stored in Trainer, he MOVES but is NEVER STORED in the MAP
			 camera[i][j] = 'O';
		  }
		  else {
			 camera[i][j] = currCameraMap.getCharacterFromLocation(x,y);
		  }
		  j++;
		}
		i++;
	 }
	 
	 return camera; 
  }

public Map getPokeTown() {
	return pokeTown;
}

public Trainer getTrainer() {
	return ash;
}

public Map getCurrCameraMap() {
	// TODO Auto-generated method stub
	return currCameraMap;
}


public Pokemon[] getPokemons() {
	// TODO Auto-generated method stub
	return pokemons;
}
  
}
