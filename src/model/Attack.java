package model;

import java.util.Random;

public class Attack {

  private String attack;
  private String effect;
  private String type;
  private int damage;
  
  private Random rand;
  
  public Attack(String nam, String effec, String typ) {
	 this.attack = nam;
	 this.effect = effec;
	 this.type = typ.toLowerCase();
	 damage = rand.nextInt(400) + 100;
  }
  
  int baseDamage() {
	 return this.damage;
  }
  
  String attack() {
	 return this.attack;
  }
  
  String effect() {
	 return this.effect;
  }
  
  String type() {
	 return this.type;
  }
}

