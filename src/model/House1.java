package model;

import java.awt.Point;
import java.util.Vector;

//This is an extension of the PokeTown map. This is a house interior within the map

public class House1 extends Map{

	private Vector<NPC> allNPCs;				//This will contain all the NPCs for this map
	private Vector<Door> allDoors;
	private Point startLocation;
	private Point prevLocation;
	private User currentUser;
	
	public House1(User user, Point entryLocation) {				//takes user as the argument so that the same "User instance" is maintained
		int width = 20;		//this represents '20 ITEMS wide' x '20 ITEMS high'
		int height = 20;
		initializeBoard(width, height);		//Initializes board to height and weight 
		prevLocation = entryLocation;
		startLocation = new Point (0, 1);
		this.currentUser = user;
		currentUser.setLocation(startLocation);
		add(currentUser);
		//TODO 1: initialize all NPCs
		DocileNPC1 professor = new DocileNPC1("Professor", false);
		professor.setLocation(5,5);
		add(professor);
		allNPCs.addElement(professor);
		
		//TODO 2 : initialize all Doors (ADD LOCATIONS TO BOARD AS WELL)
		PokeTownMap mainMap = new PokeTownMap(currentUser, prevLocation);			//this is a new Map that the door leads into
		Door door1 = new Door(0, 0, mainMap);
		door1.setLocation(0, 0);
		add(door1);
		allDoors.addElement(door1);
		
		//TODO 3 : initialize all remaining elements ie: Bushes, house structures, pathways, etc. (ADD LOCATIONS TO BOARD AS WELL) -- For right now we can just set it to BLACK
	}

}
