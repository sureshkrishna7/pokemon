package model;

import java.awt.Point;
import java.util.ArrayList;

public class Trainer extends Items{

  private String filepath;			//TODO: need to set proper filepath for the user or Image of the player
  private String trainer;

  private ArrayList<Pokemon> capturedPokemons;
  private ArrayList<Items> inventory;
  private int money;

  private Point currentPos;

  private Pokemon currentPokemon; //current battle pokemon, switch dynamically while in battle

  private int safariSteps; //500 steps in safari mode
  private int currentBalls; //30 balls only in safari mode
  private boolean safariMode;

  /*
   * Initialize with one pokemon or can make Player pick it up
   * Rick Mercer as easter egg, professor that gives you a pokemon
   */
  public Trainer(String name) {
	 super('O');
	 this.trainer = name;
	 this.money = 5000;
	 this.capturedPokemons = new ArrayList<Pokemon>(1);
	 this.inventory = new ArrayList<Items>(1);
	 addPokemon(new String("Pikachu"));
	 setBattlePokemon(capturedPokemons.get(0));

	 // Set this to 500 when the trainer pays money to NPC or menu option
	 this.safariSteps = 0;
	 this.currentBalls = 0;
	 this.safariMode = false;
  }

  public void setBattlePokemon(Pokemon p) {
	 this.currentPokemon = p;
  }

  // Add 1)
  public void addPokemon(String str) {
	 capturedPokemons.add(new Pokemon(str));
  }
  //Add 2)
  public void addPokemon(String name, String rareOfPokemon, String typeOfPokemon) {
	 Pokemon s = new Pokemon(name, rareOfPokemon, typeOfPokemon);
	 capturedPokemons.add(s);
  }
  //Add 3)
  public void addPokemon(String name, char rareOfPokemon, char typeOfPokemon) {
	 Pokemon s = new Pokemon(name, rareOfPokemon, typeOfPokemon);
	 capturedPokemons.add(s);
  }
  //Add 4)
  public void addPokemon(Pokemon s) {
	 capturedPokemons.add(s);
  }

  /*
   * **********
   * To go to Safari Zone
   * **********
   */
  public boolean goToSafariZone() {
	 if(money >= 500) {
		money = money - 500;
		setSafariSteps();
		setCurrentBalls();
		safariMode = true;
		return true;
	 }
	 getOutOfSafariZone();
	 return false;
  }

  /*
   * **********
   * To get out of Safari Zone
   * **********
   */
  public void getOutOfSafariZone() {
	 safariSteps = 0;
	 currentBalls = 0;
	 safariMode = false;
  }


  private void setSafariSteps() {
	 this.safariSteps = 500;
  }

  private void setCurrentBalls() {
	 this.currentBalls = 30;
  }

  public int getSafariSteps() {
	 return this.safariSteps;
  }

  public int getCurrentBalls() {
	 return this.currentBalls;
  }

  public void setPosition(int x, int y) {

	 currentPos.x = x;
	 currentPos.y = y;
  }

  public void setPosition(Point pos) {

	 currentPos.x = pos.x;
	 currentPos.y = pos.y;
  }

  /*
   * Methods are not implemented because we need NPC pokemon or wild Pokemon
   */
  // Has Random value from 1000 to 3001, can catch any pokemon less than that value
  public boolean throwSafariBall() {

	 if(safariMode) {
		if(currentBalls != 0) {
		  currentBalls = currentBalls - 1;
		}
		else {
		  return false;
		}
	 }

	 return true;
  }

  // Throwing Bait makes a Pokémon less likely to run, but makes it harder to catch;
  public void throwBait() {

  }

  // Throwing a Rock does the reverse, making it easier to catch but more likely to run
  public void throwRock() {

  }
}
