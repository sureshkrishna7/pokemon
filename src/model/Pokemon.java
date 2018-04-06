package model;

import java.awt.Point;
import java.util.ArrayList;

public class Pokemon extends Items{

  private String filepath;	
  private String pokemon;
  private Rare rarity;
  private PokemonType type;
  private ArrayList<Attack> moves;

  public Pokemon(String name, char rareOfPokemon, char typeOfPokemon) {
	 super('P');
	 this.pokemon = name;
	 this.rarity = new Rare(rareOfPokemon);
	 this.type = new PokemonType(typeOfPokemon);
	 this.moves = new ArrayList<Attack>(4);
	 this.moves = type.getPokemonAttacks();
  }

  public Pokemon(String name, String rareOfPokemon, String typeOfPokemon) {
	 super('P');
	 this.pokemon = name;
	 this.rarity = new Rare(rareOfPokemon);
	 this.type = new PokemonType(typeOfPokemon);
	 this.moves = new ArrayList<Attack>(4);
	 this.moves = type.getPokemonAttacks();
  }

  // Create common pokemon of specific Earth type
  public Pokemon(String name) {
	 super('P');
	 this.pokemon = name;
	 this.rarity = new Rare("common");
	 this.type = new PokemonType("earth");
	 this.moves = new ArrayList<Attack>(4);
	 this.moves = type.getPokemonAttacks();
  }

  // getDamage 1)
  public int getDamage() {
	 return moves.get(0).baseDamage() + rarity.getDamageBoost(); 
  }

  // getDamage 2)
  // get attack 1,2,3 or 4
  public int getDamage(int i) {
	 if(i < 4) {
		return moves.get(i).baseDamage() + rarity.getDamageBoost(); 
	 }
	 return moves.get(0).baseDamage() + rarity.getDamageBoost(); 
  }

  public ArrayList<Attack> getPokemonAttacks(){
	 return this.moves;
  }

  public String pokemon() {
	 return this.pokemon;
  }

  public String pokemonType() {
	 return type.getPokemonType();
  }

  public int pokemonHP() {
	 return rarity.getHP();
  }

  public int pokemonRunnable() {
	 return rarity.getRunable();
  }

}

