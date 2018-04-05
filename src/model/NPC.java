package model;

import java.util.Vector;

/* This is a general NPC class. We can create specific NPCs that extend this class.
 * The "Vector<String>dialogues" can contain Strings of dialogue that the NPC will output 
 * Hostility indicates that the NPC will fight or not	*/

public abstract class NPC extends Items {
	public String name;
	public Vector<String>dialogues;
	protected Vector<Pokemon> allPokemon;
	private Boolean hostile;
	
	public NPC(String npcName, Boolean hostility, Vector<Pokemon> NPCPokemon) {
		this.name = npcName;
		this.hostile = hostility;
		this.allPokemon = NPCPokemon;
	}
	
	public Boolean checkHostility() {
		return hostile;
	}
}
