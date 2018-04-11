package model;

import java.util.Random;

public class PokemonStrategy {
	private double threshold;
	private double percent;
	private boolean pokemonFound;
	private String pokemonType;
	private Pokemon pokemon;
	private Random random;
	
	
	public PokemonStrategy() {
		threshold = 66.0;
		percent   = 0.0;
		
		pokemonFound = false;
		
		pokemonType  = "";
		pokemon = new Pokemon("");
		random  = new Random();
		
	}
	
	public boolean isPokemonInBush() {
		
		pokemonFound = false;
		
		
		
		int i = random.nextInt(100);
		
		if (i > threshold) {
			i = random.nextInt(34);
			
			if (i < 8) {
				pokemonType = "R";
			}
			else if (i < 18) {
				pokemonType = "M";
			}
			else {
				pokemonType = "C";
			}
			
			pokemonFound = true;
		}
		
		

		return pokemonFound;
	}
	
	public Pokemon getPokemonInBush(Pokemon[] allPokemon) {
		
		if (pokemonType.equals("")) {
			return null;
		}
		
		int i = 0;
		while(i < allPokemon.length) {
			int x = random.nextInt(allPokemon.length);
			
			if (allPokemon[x].getRarity().equals(pokemonType)) {
				return allPokemon[x];
			}
			i++;
		}
		
		
		return null;
	}
	
	
}
