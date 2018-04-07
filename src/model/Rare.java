package model;

import java.util.Random;

public class Rare {

  //private int catchable;
  /*
   * ******************************************************************************************
   * ****NOTE****   Catchable depends on the health of the pokemon
   * Lower health is easily catchable, rare have high health and hence they are harder to catch
   * ******************************************************************************************
   */

  private int HP; 				// differs from 500 to 3000, health points higher is a stronger pokemon, rare has high health
  private int runable; 		// 500 to 3000, inclined to run when it is lower, rare has low runable
  private int damageBoost;	// 50 to 300, common has less damage, rare has more damage boost 

  private Random rand;
  /*
   * Constructor 1)
   * Rare, Common, Medium as r, c, m 
   * Rarity such as common, rare, super
   * Defines the health, catch and run aspects of the pokemon
   */

  public Rare(char rare) {
	 rand = new Random();
	 int r;
	 int c = rand.nextInt(500);
	 int d;

	 /*
	  * ******************************************************************************************
	  * Damage boost, RARE has a higher CHANCE to have more damage boost than common
	  * 
	  * BUT COMMON can get lucky and have higher damage boost
	  * 
	  * This would happen when rare gets damage boost less than common, EX: RARE damage boost is 0 and COMMON damage boost in 45
	  * 
	  * Damage boost is ADDED on top of normal pokemon damage
	  * Rare would have more damage obviously 
	  * Common would have less damage
	  * ******************************************************************************************
	  */

	 //Medium rarity 
	 if(rare == 'm' || rare == 'M') {
		r = rand.nextInt(500) + c + 1000;  	//around 2000 HP, always less than
		this.HP = r;

		r = rand.nextInt(500) + c + 1000;
		this.runable = r;

		d = rand.nextInt(200)+50;
		this.damageBoost = d;  
	 }
	 //Rare rarity
	 else if(rare == 'r' || rare == 'R') {
		r = rand.nextInt(500) + c + 2000; 	//around 3000 HP, always less than
		this.HP = r;

		r = rand.nextInt(500) + c + 500;
		this.runable = r;

		d = rand.nextInt(300)+50; 				
		this.damageBoost = d;						
	 }
	 //common rarity
	 else { //rare == 'c' || rare == 'C'
		r = rand.nextInt(500) + c + 500; 		//around 1500 HP, always less than
		this.HP = r;

		r = rand.nextInt(500) + c + 2000;
		this.runable = r;

		d = rand.nextInt(100)+50;
		this.damageBoost = d;
	 }
  }

  /*
   * Constructor 2)
   * Rare, Common, Medium 
   * Rarity such as common, rare, super
   * Defines the health, catch and run aspects of the pokemon
   */
  
  public Rare(String rare) {
	 rand = new Random();
	 int r;
	 int c = rand.nextInt(500);
	 int d;

	 //Medium rarity 
	 if(rare.toLowerCase().equals("medium")) {
		r = rand.nextInt(500) + c + 1000;  	//around 2000 HP, always less than
		this.HP = r;

		r = rand.nextInt(500) + c + 1000;
		this.runable = r;

		d = rand.nextInt(200)+50;
		this.damageBoost = d;  
	 }
	 //Rare rarity
	 else if(rare.toLowerCase().equals("rare")) {
		r = rand.nextInt(500) + c + 2000; 	//around 3000 HP, always less than
		this.HP = r;

		r = rand.nextInt(500) + c + 500;
		this.runable = r;

		d = rand.nextInt(300)+50; 				
		this.damageBoost = d;						
	 }
	 //common rarity
	 else { //rare == 'c' || rare == 'C'
		r = rand.nextInt(500) + c + 500; 		//around 1500 HP, always less than
		this.HP = r;

		r = rand.nextInt(500) + c + 2000;
		this.runable = r;

		d = rand.nextInt(100)+50;
		this.damageBoost = d;
	 }
  } 

  /*
   * ******************************************************************************************
   * ****NO MODIFIER METHODS****
   * So that nobody can see these
   * This is to STREAMLINE the process of getting POKEMON HEALTH and RUN VALUE
   * 
   * ****TO USE*****
   * pokemon.getHP()
   * ***************
   * 
   * ****INSTEAD OF*********
   * pokemon.rarity.getHP();
   * ***********************
   * ******************************************************************************************
   */ 

  /*
   * Difference between package private, public, protected, and private
   * 
   *          			| Class | Package | Subclass | Subclass | World
   *        				|       |         |(same pkg)|(diff pkg)| 
   *		————————————+———————+—————————+——————————+——————————+————————
   *		public      |   +   |    +    |    +     |     +    |   +     
   *		————————————+———————+—————————+——————————+——————————+————————
   *		protected   |   +   |    +    |    +     |     +    |         
   *		————————————+———————+—————————+——————————+——————————+————————
   *		no modifier |   +   |    +    |    +     |          |    
   *		————————————+———————+—————————+——————————+——————————+————————
   *		private     |   +   |         |          |          |    
   *
   *		+ : accessible
   *		blank : not accessible
   */

  int getHP() {
	 return this.HP;
  }

  int getRunable() {
	 return this.runable;
  }

  int getDamageBoost() {
	 return this.damageBoost;
  }
}
