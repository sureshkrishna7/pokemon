package model;

import java.awt.Point;
import java.util.ArrayList;

public class Pokemon extends Items {

	private String filepath;
	private String pokemon;
	private Rare rarity;
	private PokemonType type;
	private ArrayList<Attack> moves;
	private Attack specialMove;

	public Pokemon(String name, char rareOfPokemon, char typeOfPokemon, Attack specialMove) {
		super('P');
		this.pokemon = name;
		this.rarity = new Rare(rareOfPokemon);
		this.type = new PokemonType(typeOfPokemon);
		this.specialMove = specialMove;
		this.moves = type.getPokemonAttacks();
		moves.add(specialMove);

	}

	public Pokemon(String name, String rareOfPokemon, String typeOfPokemon) {
		super('P');
		this.pokemon = name;
		this.rarity = new Rare(rareOfPokemon);
		this.type = new PokemonType(typeOfPokemon);
		this.moves = type.getPokemonAttacks();
	}

	// Create common pokemon of specific Earth type
	public Pokemon(String name) {
		super('P');
		this.pokemon = name;
		this.rarity = new Rare("common");
		this.type = new PokemonType("earth");
		this.moves = type.getPokemonAttacks();
	}

	// getDamage 1)
	public int getDamage() {
		return moves.get(0).baseDamage() + rarity.getDamageBoost();
	}

	// getDamage 2)
	// get attack 1,2,3,4, or 5. (indices 0-4)
	public int getDamage(int i) {
		if (i < 4) {
			return moves.get(i).baseDamage() + rarity.getDamageBoost();
		}
		return moves.get(0).baseDamage() + rarity.getDamageBoost();
	}

	public void attack(int i, Pokemon p) {
		Attack attack = moves.get(i);
		int boostFactor = 1;
		int burnBoost = 0;
		if (attack.getBuf() != null) {
			switch (attack.getBuf()) {
			case "double":
				boostFactor = 2;
				break;
			case "random":
				boostFactor = (int) (Math.random() * 2);
				break;
			case "burn":
				attack.setBurnCount(3);
				break;
			case "bubbles":
				break;
			case "dmgAll":
				break;
			case "slow":
				break;
			case "extra":
				break;
			}
		}
		if(isOpposingType(p)) boostFactor = (int)(boostFactor * 1.25);
		if(attack.getBurnCount() > 0) {
			// this adds 35 each time if did a burn attack, will add for 3 turns (can be layered)
			burnBoost += 35;
			attack.setBurnCount(attack.getBurnCount() - 1);
		}
		p.rarity.takeDamage(this.getDamage(i) * boostFactor + burnBoost);
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

	public ArrayList<Attack> getPokemonAttacks() {
		return this.moves;
	}

	public String getPokemon() {
		return this.pokemon;
	}

	public String getPokemonType() {
		return type.getPokemonType();
	}

	public int getHP() {
		return rarity.getHP();
	}

	public int getRunnable() {
		return rarity.getRunnable();
	}

}