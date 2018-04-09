package model;

import java.util.ArrayList;
import java.util.Vector;

/* This is a general NPC class. We can create specific NPCs that extend this class.
 * The "Vector<String>dialogues" can contain Strings of dialogue that the NPC will output 
 * Hostility indicates that the NPC will fight or not	*/

public abstract class NPC extends Items {
	public String name;
	public ArrayList<String>dialogues;
	protected ArrayList<Pokemon> allPokemon;
	private boolean hostile;
	
	public NPC(String npcName, boolean hostility, Vector<Pokemon> NPCPokemon) {
	  super('N');
		this.name = npcName;
		this.hostile = hostility;
	}
	
	public void addPokemon(Pokemon p) {
		allPokemon.add(p);
	}
	
	public boolean checkHostility() {
		return hostile;
	}
}
