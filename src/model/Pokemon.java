package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Pokemon extends Items {

	private String filepath;
	private String name;
	private Rare rarity;
	private PokemonType type;
	private ArrayList<Attack> moves;
	private Attack specialMove;
	private boolean boostFactorSet;
	private int accuracy;

	public Pokemon(String name, char rareOfPokemon, char typeOfPokemon, Attack specialMove) {
		super('P');
		this.name = name;
		this.rarity = new Rare(rareOfPokemon);
		this.type = new PokemonType(typeOfPokemon);
		this.specialMove = specialMove;
		this.moves = type.getPokemonAttacks();
		moves.add(specialMove);
		boostFactorSet = false;
		accuracy = 10;
	}

	public Pokemon(String name, String rareOfPokemon, String typeOfPokemon) {
		super('P');
		this.name = name;
		this.rarity = new Rare(rareOfPokemon);
		this.type = new PokemonType(typeOfPokemon);
		this.moves = type.getPokemonAttacks();
		boostFactorSet = false;
		accuracy = 10;
	}

	// Create common pokemon of specific Earth type
	public Pokemon(String name) {
		super('P');
		this.name = name;
		this.rarity = new Rare("common");
		this.type = new PokemonType("earth");
		this.moves = type.getPokemonAttacks();
		boostFactorSet = false;
		accuracy = 10;
	}

	// getDamage 1)
	public int getDamage() {
		return moves.get(0).baseDamage() + rarity.getDamageBoost();
	}

	/*
	 * getDamage(int) -- returns baseDamage for specific attack. If baseDamage != 0 add the
	 * random boost, else return simply zero(indicating the move is a buf that deals no damage.)
	 */
	public int getDamage(int i) {
		if (moves.get(i).baseDamage() != 0) {
			return moves.get(i).baseDamage() + rarity.getDamageBoost();
		}
		return moves.get(i).baseDamage();
		
	}

	public void attack(int i, Pokemon p) {
		Attack attack = moves.get(i);
		boolean diffBoostFactor = false;
		int boostFactor = 1;
		if(boostFactorSet) boostFactor = 2;
		int burnBoost = 0;
		if (attack.getDebuf() != null) {
			switch (attack.getDebuf()) {
			case "double":
				boostFactor = 2;
				break;
			case "random":
				Random ran = new Random();
				boostFactor = ran.nextInt(3);
				break;
			case "burn":
				attack.setBurnCount(3);
				break;
			case "accDown":
				p.accuracy /= 2;
				break;
			case "dmgAll":
				// in this case we will need the object that contains the list of Pokemon
				break;
			case "slow":
				break;
			case "extra":
				diffBoostFactor = true;
				boostFactor = 3;
				break;
			}
		}
		else if (attack.getBuf() != null) {
			switch (attack.getBuf()) {
			case "double":
				if(p.getCurHP() <= p.getMaxHP() / 2) {
					boostFactorSet = true;
				}
				break;
			}
		}
		if(isOpposingType(p) && !diffBoostFactor) boostFactor = (int)(boostFactor * 1.25);
		if(attack.getBurnCount() > 0) {
			// this adds 35 each time if did a burn attack, will add for 3 turns (can be layered)
			burnBoost += 35;
			attack.setBurnCount(attack.getBurnCount() - 1);
		}
		// if move not going to miss, subtract MP
	    if(boostFactor != 0) this.rarity.subtractMP(attack.getCost());
	    // adjust HP level of Pokemon
		if(success()) {
			p.rarity.takeDamage(this.getDamage(i) * boostFactor + burnBoost);
		}
	}
	
	public boolean isOpposingType(Pokemon p) {
		if(this.getPokemonType() == "fire" && p.getPokemonType() == "ice"
				|| this.getPokemonType() == "ice" && p.getPokemonType() == "fire") {
			return true;
		}
		if(this.getPokemonType() == "earth" && p.getPokemonType() == "water"
				|| this.getPokemonType() == "water" && p.getPokemonType() == "earth") {
			return true;
		}
		return false;
	}

	/*
	 * success() -- fills array with ones for indices 0 - accuracy, then chooses one at random.
	 * If chooses a 1, return true, else return false(zero chosen).
	 * If accuracy == 10, will always return success (array populated with ones).
	 */
	public boolean success() {
		int[] arr = new int[10];
		for(int i = 0; i < accuracy; i++) {
			arr[i] = 1;
		}
		Random ran = new Random();
		int index = ran.nextInt(10);
		if(arr[index] == 1) return true;
		return false;
	}
	
	public ArrayList<Attack> getAttacks() {
		return this.moves;
	}

	public String getName() {
		return this.name;
	}

	public String getPokemonType() {
		return type.getPokemonType();
	}

	public int getMaxHP() {
		return rarity.getMaxHP();
	}
	
	public int getCurHP() {
		return rarity.getCurHP();
	}

	public int getMaxMP() {
		return rarity.getMaxMP();
	}
	
	public int getCurMP() {
		return rarity.getCurMP();
	}
	
	public int getRunnable() {
		return rarity.getRunnable();
	}
	
	public int randomMove() {
		Random ran = new Random();
		return ran.nextInt(4);
	}
	
	public void printData() {
		System.out.printf("\tname: %s\n\tHP: %d/%d\n\tMP: %d/%d\n", 
				this.getName(), this.getCurHP(), this.getMaxHP(), 
				this.getCurMP(), this.getMaxMP());
	}
	
	/*
	 * this is a temp method for testing purposes.
	 * The real check for battleOver will be in the Game Class..or PokemonGame Class...
	 */
	public boolean battleOver(Pokemon enemy) {
		if(this.getCurHP() <= 0 || this.getCurMP() <= 0 || enemy.getCurHP() <= 0 || enemy.getCurMP() <= 0) {
			return true;
		}
		return false;
	}

}