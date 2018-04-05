package model;

public class Pikachu extends Pokemon{

	private String filepath;		//hardcode the filepath in here containing charizard image
	
	public Pikachu(String pokemonName, int pokemonLevel, int resistance, int stayProbability, int health, int totXP, String type) {
		super(pokemonName, pokemonLevel, resistance, stayProbability, health, totXP, type);
		setImage(filepath);		//sets the image for easy drawing implementation
		
		Attack move1 = new Attack ("Thunder", 5, 1.2);
		Attack move2 = new Attack ("Thunderbolt", 10, 1);
		Attack move3 = new Attack ("Tackle", 20, 0.7);
		Attack move4 = new Attack ("Thunder Kick", 5, 1.2);
		
		setMoves(move1, move2, move3, move4);
	}
}
