package model;

import java.util.ArrayList;

public class PokemonType {

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
  private final static int baseDmg = 25;
  
  public PokemonType(char typeChar) {
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

  public PokemonType(String typeChar) {
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
		moves.add(new Attack("Avalanche", "ice",     baseDmg, null,        null, 2));
		moves.add(new Attack( "Blizzard", "ice",     baseDmg, null,    "freeze", 3));
		moves.add(new Attack( "Icy Wind", "ice", baseDmg * 2, null, "speedDown", 4));
		moves.add(new Attack( "Ice Rain", "ice", baseDmg * 2, null,    "double", 5));
	 }
	 else if(attackType.equals("fire")) {
		moves.add(new Attack("Flame Thrower", "fire",     baseDmg, null,     null, 2));
		moves.add(new Attack(        "Flare", "fire",     baseDmg, null, "random", 3));
		moves.add(new Attack(   "Fire Punch", "fire", baseDmg * 2, null,     null, 5));
		moves.add(new Attack("Tucson Summer", "fire", baseDmg * 2, null,   "burn", 7));
	 }
	 else if(attackType.equals("water")) {
		moves.add(new Attack(     "Soak", "water",     baseDmg,     null,      null, 2));
		moves.add(new Attack(    "Brine", "water",           0, "double",      null, 3));
		moves.add(new Attack( "Aqualung", "water", baseDmg * 2,     null, "bubbles", 5));
		moves.add(new Attack("Maelstrom", "water", baseDmg * 2,     null,  "dmgAll", 6));
	 }
	 else{
		moves.add(new Attack(        "Rock", "earth",     baseDmg, null,    null, 1));
		moves.add(new Attack(       "Drill", "earth",     baseDmg, null,  "slow", 2));
		moves.add(new Attack("Earth Bender", "earth", baseDmg * 3, null,    null, 5));
		moves.add(new Attack(       "Quake", "earth", baseDmg * 2, null, "extra", 7));
	 }
  }

  public String getPokemonType() {
	 return this.type;
  }

  public ArrayList<Attack> getPokemonAttacks(){
	 return this.moves;
  }

}
