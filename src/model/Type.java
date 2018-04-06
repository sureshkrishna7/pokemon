package model;

import java.util.ArrayList;

public class Type {

  //Water, Ice, Fire, Earth

  /*
   * ***************************
   * Fire <-----------> Ice 
   * FIRE has maximum damage against ICE Pokemon and VICEVERSA
   * 
   * 
   * Water <----------> Earth
   * WATER has maximum damage against EARTH Pokemon and VICEVERSA
   * ***************************
   */

  private String type;
  private ArrayList<Attack> moves;

  public Type(char typeChar) {
	 //FIRE type
	 if(typeChar == 'F') {
		type = "fire";
	 }
	 //ICE type
	 else if(typeChar == 'I'){
		type = "ice";
	 }
	 //WATER type
	 else if(typeChar == 'W') {
		type = "water";
	 }
	 //EARTH type
	 else {
		type = "earth";
	 }
	 initializeAttacks(type);
  }

  public Type(String typeChar) {
	 //FIRE type
	 if(typeChar.toLowerCase().equals("fire")) {
		type = "fire";
	 }
	 //ICE type
	 else if(typeChar.toLowerCase().equals("ice")) {
		type = "ice";
	 }
	 //WATER type
	 else if(typeChar.toLowerCase().equals("water")) {
		type = "water";
	 }
	 //EARTH type
	 else {
		type = "earth";
	 }
	 initializeAttacks(type);
  }

  /*
   * *********************************
   * Needs improvement
   * 
   * *********************************
   */

  private void initializeAttacks(String attackType) {
	 moves = new ArrayList<Attack>(4);

	 if(attackType.equals("ice")) {
		moves.add(new Attack("Avalanche", "Double effect if took damage first", "ice"));
		moves.add(new Attack("Blizzard", "Side effect of freezing opponent", "ice"));
		moves.add(new Attack("Icy Wind", "Lowers speed of opponent", "ice"));
		moves.add(new Attack("Ice Rain", "Random double power", "ice"));
	 }
	 else if(attackType.equals("fire")) {
		moves.add(new Attack("Flame Thrower", "Standard Attack", "fire"));
		moves.add(new Attack("Flare", "Can do critical damage or low damage", "fire"));
		moves.add(new Attack("Fire Punch", "Higher Damage then Flame Thrower", "fire"));
		moves.add(new Attack("Tucson Summer", "Initial damage, slight hp drop during course of battle", "fire"));
	 }
	 else if(attackType.equals("water")) {
		moves.add(new Attack("Soak", "Standard Attack", "water"));
		moves.add(new Attack("Brine", "Doubles power if opponent HP < 50%", "water"));
		moves.add(new Attack("Aqualung", "Lowers opponent accuracy", "water"));
		moves.add(new Attack("Maelstrom", "Does damage to opponent battling, also does little damage to NPC's other Pokemon", "water"));
	 }
	 else{
		moves.add(new Attack("Rock", "Standard Attack", "earth"));
		moves.add(new Attack("Drill", "Slows opponent speed", "earth"));
		moves.add(new Attack("Earth Bender", "Strong Attack", "earth"));
		moves.add(new Attack("Quake", "Super strong against water", "earth"));
	 }
  }

  String getPokemonType() {
	 return this.type;
  }

  ArrayList<Attack> getPokemonAttacks(){
	 return this.moves;
  }

}
