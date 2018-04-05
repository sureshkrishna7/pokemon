package model;

import java.awt.Point;
import java.util.Random;
import java.util.Vector;

public abstract class Pokemon extends Items{

  private String name;
  private int resistanceToCatch;		//scale of 1-10 (Higher number means harder to catch)
  private int likelyToStay;		 //scale of 1-10 (Higher means LESS likely to run -- Will have a rand # generator, so if this value is high, it is less likely to guess the run number)
  private int totalHP;
  private int currentHP;
  private int totalXP;
  private int currentXP;
  private int level;
  private String pokemonType;
  private Vector<Attack> allMoves;
  //private Type pokemonType;			//idk if we need a whole nother class for the pokemon type, should "String" be sufficient?
  //private Rare pokemonRarity;		//idk if this is necessary bc we will hardcode all the pokemon per map so we code all the aspects of rarity into the program
  
  public Pokemon(String pokemonName, int pokemonLevel, int resistance, int stayProbability, int health, int totXP, String type) {
	  this.name = pokemonName;
	  this.level = pokemonLevel;
	  this.resistanceToCatch = resistance;
	  this.likelyToStay = stayProbability;
	  this.currentHP = health;
	  this.totalHP = health;
	  this.totalXP = totXP;
	  this.currentXP = 0;
	  this.pokemonType = type;
	  
	  setWalkable();				//Sets to walkable bc it is in grass
	  setType("pokemon");			
  }
  
  public int getResistance() {			//Resistance Getters, Setters, Increment & decrement
	  return this.resistanceToCatch;
  }
  public void setResistance(int newResistance) {
	  this.resistanceToCatch = newResistance;
  }
  public void lowerResistance() {
	  this.resistanceToCatch--;
  }
  public void raiseResistance() {
	  this.resistanceToCatch++;
  }
  
  public int getLikelyToStay() {		//LikelyToStay Getters, Setters, Increment & decrement
	  return this.likelyToStay;
  }
  public void setLikelyToStay(int newNum) {
	  this.likelyToStay = newNum;
  }
  public void increaseStay() {
	  this.likelyToStay++;
  }
  public void decreaseStay() {
	  this.likelyToStay--;
  }
  
  public int getCurrentHP() {			//HP Getters, Setters, Increment & decrement
	  return this.currentHP;
  }
  public void setCurrentHP(int newHP) {
	  this.currentHP = newHP;
	  if (this.currentHP > this.totalHP)
		  this.currentHP = this.totalHP;
  }
  
  public void resetHP() {
	  this.currentHP = this.totalHP;
  }
  
  public int getTotalHP() {		
	  return this.totalHP;
  }
  public void setTotalHP(int newHP) {
	  this.totalHP = newHP;
  }
  public void decreaseHP(int decrease) {
	  this.currentHP -= decrease;
	  if (this.currentHP < 0)
		  this.currentHP = 0;
  }
  public void increaseHP(int increase) {
	  this.currentHP += increase;
	  if (this.currentHP > this.totalHP) 
		  this.currentHP = this.totalHP;
  }
  
  public String getType() {
	  return this.pokemonType;
  }
  public String getName() {		//Name getters and setters
	  return this.name;
  }
  public void setName(String newName) {
	  this.name = newName;
  }
  
  public int getLevel() {		//level Getters, Setters, Increment & decrement
	  return this.level;
  }
  public void setLevel(int newLevel) {
	  this.level = newLevel;
  }
  public void increaseLevel() {
	  this.level++;
  }
  public void decreaseLevel() {
	  this.level--;
  }
  
  public int getCurrentXP() {		//XP Getters, Setters, Increment & decrement
	  return this.currentXP;
  }
  public void setCurrentXP(int newXP) {
	  this.currentXP = newXP;
  }
  public int getTotalXP() {		
	  return this.totalXP;
  }
  public void setTotalXP(int newXP) {
	  this.totalXP = newXP;
  }
  public void increaseXP(int increase) {
	  this.currentXP += increase;
  }
  public void decreaseXP(int decrease) {
	  this.currentXP -= decrease;
  }
  
  public void setMoves(Attack move1, Attack move2, Attack move3, Attack move4) {
	  this.allMoves.addElement(move1);
	  this.allMoves.addElement(move2);
	  this.allMoves.addElement(move3);
	  this.allMoves.addElement(move4);
  }
  
  public void levelup() {		//ROUGH estimation of a LEVELUP -- need to alter calculations
	  this.level++;
	  this.totalHP += 50;		//in actuality, this should DECAY as the level increases
	  this.currentHP = this.totalHP;
	  this.totalXP += 50;		//in actuality, this should INCREASE as the level increases
	  this.currentXP = 0;
  }
  
  public int useAttack(Attack nextAttack) {		//The return type is the amt Damage Inflicted
	  if (allMoves.contains(nextAttack)) {
		  int index = allMoves.indexOf(nextAttack);
		  if (allMoves.get(index).getCurrentPP() > 0)
		  	allMoves.get(index).decreasePP(); 
		  	return (int) (allMoves.get(index).getMultiplier() * this.level);		//Tentative damage calculation
	  }
	  else
		  return 0;
  }
  
  public int useAttack(String nextAttack) {		//Overloading method for ease of implementation
	  int index = -1;
	  for (int i = 0; i < allMoves.size(); i++)	{
		  if (allMoves.get(i).getAttackName().equals(nextAttack)) {		//Not sure if this syntax is correct
			  index = i;
		  }
	  }
	  if (index != -1 && allMoves.get(index).getCurrentPP() > 0) {
		  allMoves.get(index).decreasePP(); 
		  return (int) (allMoves.get(index).getMultiplier() * this.level);		//Tentative damage calculation
	  }
	  else
		  return 0;
  }
  
  public Boolean attackEligible(Attack potentialMove) {			//checks attack eligibility
	  if (potentialMove.getCurrentPP() > 0)
		  return true;
	  else
		  return false;
  }
  
  public int useRandomAttack() {
	  if (allMoves.get(0).getCurrentPP() == 0 && allMoves.get(1).getCurrentPP() == 0 && allMoves.get(2).getCurrentPP() == 0 && allMoves.get(3).getCurrentPP() == 0)
		  return 0;		//THis is the case that NO moves are remaining
	  int rnd = new Random().nextInt(allMoves.size());		//selects a random move
	  	while (!attackEligible(allMoves.elementAt(rnd))) {				//Checks attack elegibility (Enough PP)
			rnd = new Random().nextInt(allMoves.size());
	  	}
	  return useAttack(allMoves.elementAt(rnd));			//uses eligible attack
  }
  public Boolean isDead() {
	  if (this.currentHP == 0)
		  return true;
	  else return false;
  }
}
