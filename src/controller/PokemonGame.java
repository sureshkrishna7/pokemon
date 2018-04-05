package controller;

import java.awt.Point;
import java.util.Vector;

import model.BackpackItems;
import model.Charizard;
import model.Pikachu;
import model.PokeTownMap;
import model.Pokeball;
import model.Pokemon;
import model.Potion;
import model.User;

//Simply Create the User and insert User into PokeTownMap, the rest of the maps will be embedded within PokeTownMap

public class PokemonGame {
	public PokemonGame() {
		Vector<Pokemon>userPokemon = new Vector<Pokemon>();
		Charizard charizard1 = new Charizard("Charizard", 5, 5, 5, 100, 100, "fire");
		Pikachu pikachu1 = new Pikachu("Pikachu", 5, 5, 5, 100, 100, "Electric");
		userPokemon.add(charizard1);
		userPokemon.add(pikachu1);
		Vector<BackpackItems>userInventory = new Vector<BackpackItems>();
		Pokeball normalPokeball = new Pokeball("pokeball", 10);
		Pokeball ultraPokeball = new Pokeball("ultraball", 10);
		Potion normalPotion = new Potion("potion", 10);
		Potion superPotion = new Potion("superpotion", 10);
		userInventory.add(normalPokeball);
		userInventory.add(ultraPokeball);
		userInventory.add(normalPotion);
		userInventory.add(superPotion);
		
		User initialUser = new User("Ash", userPokemon, userInventory, 10000);
		Point start = new Point(10, 10);
		PokeTownMap mainMap = new PokeTownMap(initialUser, start);
	}
}
