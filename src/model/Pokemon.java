package model;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Pokemon extends Items {

  private String name;
  private String status;
  private int level;
  private Statistics stats;
  private PokemonType type;
  private ArrayList<Attack> moves;
  //private Attack specialMove;
  private boolean boostFactorSet;
  
  public Pokemon(String name, int level, char rarity, char typeOfPokemon, Attack specialMove) {
    super('P');
    this.name = name;
    this.status = "normal";
    this.level = level;
    this.stats = new Statistics(level, rarity);
    this.type = new PokemonType(typeOfPokemon);
    //this.specialMove = specialMove;
    this.moves = type.getPokemonAttacks();
    // moves.add(specialMove);
    boostFactorSet = false;
  }

  public Pokemon(String name, int level, String rareOfPokemon, String typeOfPokemon) {
    super('P');
    this.name = name;
    this.status = "normal";
    this.level = level;
    this.type = new PokemonType(typeOfPokemon);
    this.moves = type.getPokemonAttacks();
    boostFactorSet = false;
  }

  // Create common pokemon of specific Earth type
  public Pokemon(String name, int level) {
    super('P');
    this.name = name;
    this.status = "normal";
    this.level = level;
    this.type = new PokemonType("earth");
    this.moves = type.getPokemonAttacks();
    boostFactorSet = false;
  }
  
  /*
   * getDamage(int) -- returns baseDamage for specific attack. If baseDamage != 0
   * add the random boost, else return simply zero(indicating the move is a buf
   * that deals no damage.)
   */
  public int getDamage(int i) {
    if (moves.get(i).baseDamage() != 0) {
      return moves.get(i).baseDamage() + stats.getDamageBoost();
    }
    return moves.get(i).baseDamage();
  }

  public boolean attack(int i, Pokemon enemy) {
    Attack attack = moves.get(i);

    boolean diffBoostFactor = false;
    int boostFactor = 1;
    if (boostFactorSet)
      boostFactor = 2;
    int burnBoost = 0;
    if (attack.getDebuf() != null) {
      switch (attack.getDebuf()) {
      case "double":
        boostFactor = 2;
        break;
      case "random":
        Random ran = new Random();
        boostFactor = ran.nextInt(3);
        break;
      case "burn":
        attack.setBurnCount(3);
        break;
      case "accDown":
        enemy.stats.setAccuracy(enemy.stats.getAccuracy() / 2);
        break;
      case "dmgAll":
        // in this case we will need the object that contains the list of Pokemon
        break;
      case "slow":
        break;
      case "extra":
        diffBoostFactor = true;
        boostFactor = 3;
        break;
      }
    } else if (attack.getBuf() != null) {
      switch (attack.getBuf()) {
      case "double":
        if (enemy.getCurHP() <= enemy.getMaxHP() / 2) {
          boostFactorSet = true;
        }
        break;
      }
    }
    if (isOpposingType(enemy) && !diffBoostFactor)
      boostFactor = (int) (boostFactor * 1.25);
    if (attack.getBurnCount() > 0) {
      // this adds 35 each time if did a burn attack, will add for 3 turns (can be
      // layered)
      burnBoost += 35;
      attack.setBurnCount(attack.getBurnCount() - 1);
    }
    // if move not going to miss, subtract MP
    if (boostFactor != 0)
      this.stats.subtractMP(attack.getCost());

    // check if attack was success by applying accuracy factor
    if (success()) {
      // apply damage to enemy
      enemy.stats.takeDamage(this.getDamage(i) * boostFactor + burnBoost);
      // if attack has debuf, set enemies status to be this particular debuf (effect)
      if(attack.getDebuf() != null) {
        enemy.setStatus(attack.getDebuf());
      }
      return true;
    }
    else
    		return false;
  }

  public boolean isOpposingType(Pokemon p) {
    if (this.getPokemonType() == "fire" && p.getPokemonType() == "ice"
        || this.getPokemonType() == "ice" && p.getPokemonType() == "fire") {
      return true;
    }
    if (this.getPokemonType() == "earth" && p.getPokemonType() == "water"
        || this.getPokemonType() == "water" && p.getPokemonType() == "earth") {
      return true;
    }
    return false;
  }

  /*
   * success() -- chooses a random double between 0.0 and 1.0. It compares this value
   * with the accuracy of the Pokemon, which is in the range 0.0 to 1.0. If the value 
   * chosen is less than the accuracy of the Pokemon, then true is returned, else false
   * is returned. Thus, the higher the accuracy of the Pokemon, the higher the likelihood
   * of true being returned.
   */
  public boolean success() {
    Random ran = new Random();
    double pivot = ran.nextDouble();
    if(pivot < this.stats.getAccuracy()) {
      return true;
    }
    return false;
  }

  public ArrayList<Attack> getAttacks() {
    return this.moves;
  }

  public String getName() {
    return this.name;
  }
  
  // If status == null, Pokemon is healthy (has no ailment)
  public String getStatus() {
    return this.status;
  }
  
  public void setStatus(String newStatus) {
    this.status = newStatus;
  }
  
  public String getPokemonType() {
    return type.getPokemonType();
  }

  public int getMaxHP() {
    return stats.getMaxHP();
  }

  public int getCurHP() {
    return stats.getCurHP();
  }

  public void setCurHP(int i) {
    stats.setCurHP(i);
  }

  public int getMaxMP() {
    return stats.getMaxMP();
  }

  public int getCurMP() {
    return stats.getCurMP();
  }

  public void setCurMP(int i) {
    stats.setCurMP(i);
  }
  
  public int getLevel() {
    return this.level;
  }

  public boolean mPExhausted() {
    if (this.getCurMP() <= 0) {
      return true;
    }
    return false;
  }

  public double getAccuracy() {
    return stats.getAccuracy();
  }
  
  public double getRunChance() {
    return stats.getRunChance();
  }
  
  public void setRunChance(double rc) {
    stats.setRunChance(rc);
  }
  
  public double getTakeBaitChance() {
    return stats.getTakeBaitChance();
  }

  public double getCatchChance() {
    return stats.getCatchChance();
  }
  
  public void setCatchChance(double cc) {
    stats.setCatchChance(cc);
  }
  
  public int randomMove() {
    Random ran = new Random();
    return ran.nextInt(4);
  }

  public void printMoves() {
    int i = 1;
    for (Attack a : this.getAttacks()) {
      System.out.printf("  %d: %13s  Power:%d  MP:%d  Effect:", i, a.getName(),
          (a.baseDamage() + this.stats.getDamageBoost()), a.getCost());
      if (a.getBuf() != null)
        System.out.printf("%s\n", a.getBuf());
      else if (a.getDebuf() != null)
        System.out.printf("%s\n", a.getDebuf());
      else
        System.out.printf("None\n");
      i++;
    }

  }

  public void printData() {
    System.out.printf("\tname: %s\n\tlevel: %d\n\tstatus: %s\n\tHP: %d/%d\n\tMP: %d/%d\n", this.getName(), this.getLevel(), this.getStatus(), this.getCurHP(), this.getMaxHP(),
        this.getCurMP(), this.getMaxMP());
  }
  
  public String getData() {
    return "\tname: " + this.getName() + "  level: " + this.getLevel() + "  status: " + this.getStatus() + "  HP: " + this.getCurHP() + "  MP: "
        + this.getCurMP() + "\n";
  }

  public boolean isExhausted() {
    if (this.getCurHP() <= 0) {
      this.setCurHP(0);
      return true;
    }
    return false;
  }
  
  public boolean canMove() {
    if (this.getCurMP() < this.getAttacks().get(0).getCost()) {
      return false;
    }
    return true;
  }

  public boolean invalidMove(int i) {
    if (this.getAttacks().get(i).getCost() > this.getCurMP()) {
      return true;
    }
    return false;
  }
  
  public boolean timeToRun() {
    // this is a simple random generator, looks weird but works better than Random
    // double pivot = ThreadLocalRandom.current().nextDouble(0.1, 1.0);
    Random ran = new Random();
    double pivot = ran.nextDouble();
    
    //System.out.printf("pivot= %.2f, run chance= %.2f\n", pivot, this.stats.getRunChance());
    if(pivot > this.stats.getRunChance()) {
      return true;
    }
    return false;
  }
  
  public boolean isDisabled() {
    if(this.getStatus() == "frozen") {
      return true;
    }
    return false;
  }

}