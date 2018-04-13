package model;

import java.util.Random;

public class Statistics {

  private int maxMP;
  private int curMP;
  private int maxHP;
  private int curHP;
  private int level;
  private char rareDegree;
  private double runChance; // dependent of rareDegree, also small bump due to level
  private double catchChance;
  private double takeBaitChance;
  private double accuracy; // dependent on level
  private int damageBoost; // dependent on level of Pokemon
  private Random rand;

  /*
   * Statistics(int char) -- constructor takes two params, int for level and char
   * for rareDegree. Calls setStats() and setRunChance() to determine damage boost
   * by level and runChance by rareDegree.
   */
  public Statistics(int level, char rareDegree) {
    this.level = level;
    this.rareDegree = rareDegree;
    setChanceVars();
    setStats();
  }

  /*
   * setChanceVars() -- sets the runChance, catchChance, and takeBaitChance by the
   * rareDegree of the Pokemon. Uses double to represent in a range of 0.0 - 1.0,
   * which can be used as percentages.
   */
  private void setChanceVars() {
    if (rareDegree == 'C') {
      this.runChance = 0.35;
      this.catchChance = 0.75;
      this.takeBaitChance = 0.75;
    } else if (rareDegree == 'M') {
      this.runChance = 0.57;
      this.catchChance = 0.58;
      this.takeBaitChance = 0.50;
    } else if (rareDegree == 'R') {
      this.runChance = 0.73;
      this.catchChance = 0.45;
      this.takeBaitChance = 0.30;
    }
  }

  /*
   * setStats() -- sets the stats of this object which is a composite of Pokemon.
   * It does so in 4 branches based on the level of the Pokemon: lvls 0-5 lvls
   * 6-10 lvls 11-15 lvls 16 and above. (20 should be max level possible..) The HP
   * and damageBoost are varied by a random buffer, ranges provided in comments.
   * runChance is incremented slightly depending on the level, which is
   * initialized in setRunChance();
   */
  private void setStats() {
    rand = new Random();
    int base;
    int buffer = rand.nextInt(100);
    int damageBoost;

    if (level > 15) {
      // HP range: 2000 - 2599
      base = rand.nextInt(500) + buffer + 2000;
      this.maxHP = base;
      // Damage boost: 50-349
      damageBoost = rand.nextInt(300) + 50;
      this.damageBoost = damageBoost;
      // runChance: incremented by 0.6 (initially determined by rareDegree)
      // this.runChance += 0.6;
      // MP: hard coded
      this.maxMP = 37;
      // accuracy: hard coded
      this.accuracy = 0.95;
    } else if (level > 10) {
      // HP range: 1500 - 2099
      base = rand.nextInt(500) + buffer + 1500;
      this.maxHP = base;
      // Damage boost: 50-299
      damageBoost = rand.nextInt(250) + 50;
      this.damageBoost = damageBoost;
      // runChance: incremented by 0.4 (initially determined by rareDegree)
      // this.runChance += 0.4;
      // MP: hard coded
      this.maxMP = 35;
      // accuracy: hard coded
      this.accuracy = 0.85;
    } else if (level > 5) {
      // HP range: 1000 - 1599
      base = rand.nextInt(500) + buffer + 1000;
      this.maxHP = base;
      // Damage boost: 50-249
      damageBoost = rand.nextInt(200) + 50;
      this.damageBoost = damageBoost;
      // runChance: incremented by 0.3 (initially determined by rareDegree)
      // this.runChance += 0.3;
      // MP: hard coded
      this.maxMP = 33;
      // accuracy: hard coded
      this.accuracy = 0.80;
    } else {
      // HP range: 800 - 1399
      base = rand.nextInt(500) + buffer + 800;
      this.maxHP = base;
      // Damage boost: 50-224
      damageBoost = rand.nextInt(175) + 50;
      this.damageBoost = damageBoost;
      // runChance: incremented by 0.2 (initially determined by rareDegree)
      // this.runChance += 0.2;
      // MP: hard coded
      this.maxMP = 31;
      // accuracy: hard coded
      this.accuracy = 0.75;
    }
    this.curHP = maxHP;
    this.curMP = maxMP;
  }

  public int getMaxHP() {
    return this.maxHP;
  }

  public int getCurHP() {
    return this.curHP;
  }

  public void setCurHP(int i) {
    this.curHP = i;
  }

  public int getMaxMP() {
    return this.maxMP;
  }

  public int getCurMP() {
    return this.curMP;
  }

  public void setCurMP(int i) {
    this.curMP = i;
  }

  public void subtractMP(int i) {
    this.curMP -= i;
  }

  public void takeDamage(int i) {
    this.curHP -= i;
  }

  public double getRunChance() {
    return this.runChance;
  }

  public double getCatchChance() {
    return this.catchChance;
  }

  public double getTakeBaitChance() {
    return this.takeBaitChance;
  }

  public int getDamageBoost() {
    return this.damageBoost;
  }

  public double getAccuracy() {
    return this.accuracy;
  }

  public void setAccuracy(double d) {
    this.accuracy = d;
  }

}
