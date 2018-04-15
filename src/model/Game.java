package model;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game {

  private boolean didGameEnd;
  private boolean playerWon;

  private MainMap pokeTown;
  private MainMap safariZone;
  private MainMap currCameraMap;
  private Trainer ash;

  private final int camWidth = 10;
  private final int camHeight = 10;
  private Items[][] cameraArray; 
  private Map<String, String> allPokemonList;
  private ArrayList<String> pokemonNameList;

  private char[][] camera;
  
  public Game() {

	 didGameEnd = false;
	 playerWon = false;

	 pokeTown = new MainMap();
	 safariZone = new MainMap();
	 currCameraMap = new MainMap();
	 initializePokeLists();
	 
	 try {
		pokeTown.geniusMethod("src/PokemonTown.txt");
		
		Scanner scanner = new Scanner(new File("src/PokemonNames.txt"));
		 
		for(int i = 0; i < 10; i++) {
			//pokemons[i] = new Pokemon("");
			String line = scanner.nextLine();
			String[] pokemonInitializer = new String[3];
			pokemonInitializer = line.split("\\s+");

			//Pokemon currentPokemon = new Pokemon(pokemonInitializer[0], pokemonInitializer[1], pokemonInitializer[2]);	
			//pokemons[i] = currentPokemon;
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

	 ash = new Trainer(new String("Ash"));
	 //ash.setLocation(27, 25);
	 //ash.setLocation(5,12);
	 ash.setLocation(5,20);


	 this.cameraArray = new Items[camHeight][camWidth];
	 this.camera = new char[camHeight][camWidth];
  }
  
  /*
   * initializePokeLists() -- initialize the Map object for the list of Pokemon as a HashMap,
   * adds all the Pokemon with their names as keys and their rareDegree and types as values, contained
   * in a two character String where the first character is the rareDegree and the second is the type.
   * Also initializes and fills an ArrayList of all the names of the Pokemon.
   */
  private void initializePokeLists() {
    this.allPokemonList = new HashMap<>();
    this.pokemonNameList = new ArrayList<String>(); 
    
    this.pokemonNameList.add("Charmeleon");
    this.pokemonNameList.add("Charmander");
    this.pokemonNameList.add("Vulpix");
    this.pokemonNameList.add("Sandslash");
    this.pokemonNameList.add("Glaceon");
    this.pokemonNameList.add("Seadra");
    this.pokemonNameList.add("Starmie");
    this.pokemonNameList.add("Seaking");
    this.pokemonNameList.add("Cubone");
    this.pokemonNameList.add("Rhydon");
    
    this.allPokemonList.put("Charmeleon", "MF");
    this.allPokemonList.put("Charmander", "RF");
    this.allPokemonList.put("Vulpix", "RF");
    this.allPokemonList.put("Sandslash", "CI");
    this.allPokemonList.put("Sandslash", "MI");
    this.allPokemonList.put("Seadra", "CW");
    this.allPokemonList.put("Starmie", "MW");
    this.allPokemonList.put("Seaking", "RW");
    this.allPokemonList.put("Cubone", "CE");
    this.allPokemonList.put("Rhydon", "ME");
  }// end initializePokeLists()


  public void setCurrCameraMap(MainMap map) {
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
  
  public void setSafariZone(MainMap newMap) {
	  safariZone = newMap;
  }
  public MainMap getSafariZone() {
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

public MainMap getPokeTown() {
	return pokeTown;
}

public Trainer getTrainer() {
	return ash;
}

public MainMap getCurrCameraMap() {
	// TODO Auto-generated method stub
	return currCameraMap;
}


public Map<String, String> getAllPokemonList() {
	return allPokemonList;
}

public ArrayList<String> getPokemonNameList(){
  return pokemonNameList;
}
  
}
