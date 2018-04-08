package model;

import java.util.Random;

public class Attack {

  private String name;
  private String buf;
  private String debuf;
  private String type;
  private int damage;
  private int cost;
  private int burnCount;
  
  private Random rand;
  
  public Attack(String name, String type, int damage, String buf, String debuf, int cost) {
	 rand = new Random();
	 this.name = name;
	 this.type = type.toLowerCase();
	 this.damage = damage;
	 this.buf = buf;
	 this.debuf = debuf;
	 this.cost = cost;
	 this.burnCount = 0;
	 //damage = rand.nextInt(400) + 100;
  }
  
  public int baseDamage() {
	 return damage;
  }
    
  public String getBuf() {
	 return buf;
  }
  
  public String getDebuf() {
	  return debuf;
  }
  
  public String getType() {
	 return type;
  }
  
  public int getCost() {
	  return cost;
  }
  
  public void setBurnCount(int i) {
	  this.burnCount = i;
  }
  
  public int getBurnCount() {
	  return burnCount;
  }
}

