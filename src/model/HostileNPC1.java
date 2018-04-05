package model;

import java.util.Random;
import java.util.Vector;

//Attacking NPC

public class HostileNPC1 extends NPC {

	private String filepath;				//TODO: Need to set correct filepath
	
	public HostileNPC1(String npcName, Boolean hostility, Vector<Pokemon> npcPokemon) {
		super(npcName, hostility, npcPokemon);
		setImage(filepath);
		setType("NPC");
		dialogues.addElement("Hello");
		dialogues.addElement("Next dialogue");
		dialogues.addElement("This is a test...");
		dialogues.addElement("Lets battle.");
	}

	public Boolean pokemonStillEligible() {			//checks if there are still eligible pokemon
		for (Pokemon current: allPokemon) {
			if (!current.isDead())
				return true;
		}
		return false;
	}
	public Pokemon chooseNextPokemon() {		//**BE SURE TO USE "pokemonStillEligible" prior to this fxn
		int rnd = new Random().nextInt(allPokemon.size());		//selects a random move
	  	while (allPokemon.elementAt(rnd).isDead()) {				//Checks attack elegibility (Enough PP)
			rnd = new Random().nextInt(allPokemon.size());
	  	}
	  	return allPokemon.elementAt(rnd);			//uses eligible attack
	}
	
}
