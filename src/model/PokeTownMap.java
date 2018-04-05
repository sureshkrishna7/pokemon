package model;

import java.awt.Point;
import java.util.Random;
import java.util.Vector;

public class PokeTownMap extends Map {

	private Vector<Pokemon> allPokemon;		//this will contain all the "pokemon" for this map
	private Vector<Grass> allGrass;	//this will contain all of the "grass locations" for this map (Where pokemon can potentially exist)
	private Vector<NPC> allNPCs;				//This will contain all the NPCs for this map
	private Vector<Door> allDoors;
	private User currentUser;
	public PokeTownMap(User user, Point spawnLocation) {		
		int width = 20;		//this represents '20 ITEMS wide' x '20 ITEMS high'
		int height = 20;
		initializeBoard(width, height);		//Initializes board to height and weight 
		this.currentUser = user;
		this.currentUser.setLocation(spawnLocation);
		add(currentUser);
		//TODO 1 : initialize all the pokemon for this map & put into "allPokemon" vector (DONT SPECIFY LOCATION YET, THIS IS DONE IN initializePokemon() && DONT ADD TO BOARD bc this should be "invisible")
		Charizard charizard1 = new Charizard("Charizard", 5, 5, 5, 100, 100, "fire");
		Charizard charizard2 = new Charizard("Charizard", 5, 5, 5, 100, 100, "fire");
		Charizard charizard3 = new Charizard("Charizard", 5, 5, 5, 100, 100, "fire");
		Charizard charizard4 = new Charizard("Charizard", 5, 5, 5, 100, 100, "fire");
		Charizard charizard5 = new Charizard("Charizard", 5, 5, 5, 100, 100, "fire");
		allPokemon.addElement(charizard1);
		allPokemon.addElement(charizard2);
		allPokemon.addElement(charizard3);
		allPokemon.addElement(charizard4);
		allPokemon.addElement(charizard5);
		//TODO 2 : initialize all the grass locations & put into "grassLocations" vector (ADD TO BOARD AS WELL)
		Grass grass1 = new Grass(0,0);
		Grass grass2 = new Grass(0,1);
		Grass grass3 = new Grass(0,2);
		Grass grass4 = new Grass(0,3);
		Grass grass5 = new Grass(0,4);
		Grass grass6 = new Grass(1,0);
		Grass grass7 = new Grass(1,1);
		Grass grass8 = new Grass(1,2);
		Grass grass9 = new Grass(1,3);
		Grass grass10 = new Grass(1,4);
		allGrass.addElement(grass1);
		allGrass.addElement(grass2);
		allGrass.addElement(grass3);
		allGrass.addElement(grass4);
		allGrass.addElement(grass5);
		allGrass.addElement(grass6);
		allGrass.addElement(grass7);
		allGrass.addElement(grass8);
		allGrass.addElement(grass9);
		allGrass.addElement(grass10);
		add(grass1);			//add to the BOARD
		add(grass2);	
		add(grass3);	
		add(grass4);	
		add(grass5);	
		add(grass6);	
		add(grass7);	
		add(grass8);	
		add(grass9);	
		add(grass10);	
		//TODO 3 : initialize all NPCs (ADD LOCATIONS TO BOARD AS WELL)
		Charizard charizard6 = new Charizard("Charizard", 5, 5, 5, 100, 100, "fire");
		Pikachu pikachu1 = new Pikachu("Pikachu", 5, 5, 5, 100, 100, "Electric");
		Vector<Pokemon> pokemonNPC1 = new Vector<Pokemon>();
		pokemonNPC1.addElement(charizard6);
		pokemonNPC1.addElement(pikachu1);
		HostileNPC1 gary = new HostileNPC1("Gary", true, pokemonNPC1);		//initializes HostileNPC with Pokemon
		gary.setLocation(2,2);				//Sets location of the NPC				
		add(gary);								//Adds NPC to the board
		allNPCs.addElement(gary);			//adds NPC to "allNPCs" vector
		DocileNPC1 misty = new DocileNPC1("Misty", false);
		misty.setLocation(2,3);
		add(misty);
		allNPCs.addElement(misty);
		//TODO 4 : initialize all Doors (ADD LOCATIONS TO BOARD AS WELL)
		Point entryLocation = new Point(3,4);
		House1 house1 = new House1(currentUser, entryLocation);			//this is a new Map that the door leads into
		Door door1 = new Door(3, 3, house1);
		door1.setLocation(3, 3);
		add(door1);
		allDoors.addElement(door1);
		//TODO 5 : initialize all remaining elements ie: Bushes, house structures, pathways, etc. (ADD LOCATIONS TO BOARD AS WELL) -- For right now we can just set it to BLACK
		initializePokemon();		//this will randomly place pokemon in the grass locations
	}
	
	private void initializePokemon() {
		for (Grass current: allGrass)
			current.setVacant();
		for (Pokemon current: allPokemon) {
			int rnd = new Random().nextInt(allGrass.size());
			while (allGrass.elementAt(rnd).isOccupied()) {		//if the grassLocation is occupied, look for a new available grassLocation
				rnd = new Random().nextInt(allGrass.size());
			}
			current.setLocation(allGrass.elementAt(rnd).getLocation());	//updates location of the Pokemon
		}
	}

}
