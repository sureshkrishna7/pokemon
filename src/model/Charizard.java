package model;

public class Charizard extends Pokemon {

	private String filepath;		//hardcode the filepath in here containing charizard image
	
	public Charizard(String pokemonName, int pokemonLevel, int resistance, int stayProbability, int health, int totXP, String type) {
		super(pokemonName, pokemonLevel, resistance, stayProbability, health, totXP, type);
		setImage(filepath);		//sets the image for easy drawing implementation
		
		Attack move1 = new Attack ("Ember", 10, 1);
		Attack move2 = new Attack ("Flamethrower", 5, 1.2);
		Attack move3 = new Attack ("Tackle", 20, 0.7);
		Attack move4 = new Attack ("Fire Blast", 5, 1.5);
		
		setMoves(move1, move2, move3, move4);
	}

}
