package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Pokemon extends Items {

  private String filepath;
  private String name;
  private String status;
  private Rare rarity;
  private PokemonType type;
  private ArrayList<Attack> moves;
  private Attack specialMove;
  private boolean boostFactorSet;
  private int accuracy;
  public int runLikelihood = 5; // have to change to make this different for different pokemon later
  public int catchLikelihood = 5; // have to change to make this different for different pokemon later
  public int maxDuration = 3; // have to change to make this different for different pokemon later

  public Pokemon(String name, char rareOfPokemon, char typeOfPokemon, Attack specialMove) {
    super('P');
    this.name = name;
    this.status = "normal";
    this.rarity = new Rare(rareOfPokemon);
    this.type = new PokemonType(typeOfPokemon);
    this.specialMove = specialMove;
    this.moves = type.getPokemonAttacks();
    // moves.add(specialMove);
    boostFactorSet = false;
    accuracy = 10;
  }

  public Pokemon(String name, String rareOfPokemon, String typeOfPokemon) {
    super('P');
    this.name = name;
    this.status = "normal";
    this.rarity = new Rare(rareOfPokemon);
    this.type = new PokemonType(typeOfPokemon);
    this.moves = type.getPokemonAttacks();
    boostFactorSet = false;
    accuracy = 10;
  }

  // Create common pokemon of specific Earth type
  public Pokemon(String name) {
    super('P');
    this.name = name;
    this.status = "normal";
    this.rarity = new Rare("common");
    this.type = new PokemonType("earth");
    this.moves = type.getPokemonAttacks();
    boostFactorSet = false;
    accuracy = 10;
  }

  // getDamage 1)
  public int getDamage() {
    return moves.get(0).baseDamage() + rarity.getDamageBoost();
  }

  /*
   * getDamage(int) -- returns baseDamage for specific attack. If baseDamage != 0
   * add the random boost, else return simply zero(indicating the move is a buf
   * that deals no damage.)
   */
  public int getDamage(int i) {
    if (moves.get(i).baseDamage() != 0) {
      return moves.get(i).baseDamage() + rarity.getDamageBoost();
    }
    return moves.get(i).baseDamage();

  }

  public void attack(int i, Pokemon enemy) {
    Attack attack = moves.get(i);

    // reject move if not enough MP
    // if(attack.getCost() > this.getCurMP()) {
    // return false;
    // }

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
        enemy.accuracy /= 2;
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
      this.rarity.subtractMP(attack.getCost());

    // check if attack was success by applying accuracy factor
    if (success()) {
      // apply damage to enemy
      enemy.rarity.takeDamage(this.getDamage(i) * boostFactor + burnBoost);
      // if attack has debuf, set enemies status to be this particular debuf (effect)
      if(attack.getDebuf() != null) {
        enemy.setStatus(attack.getDebuf());
      }
    }
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
   * success() -- fills array with ones for indices 0 - accuracy, then chooses one
   * at random. If chooses a 1, return true, else return false(zero chosen). If
   * accuracy == 10, will always return success (array populated with ones).
   */
  public boolean success() {
    int[] arr = new int[10];
    for (int i = 0; i < accuracy; i++) {
      arr[i] = 1;
    }
    Random ran = new Random();
    int index = ran.nextInt(10);
    if (arr[index] == 1)
      return true;
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
    return rarity.getMaxHP();
  }

  public int getCurHP() {
    return rarity.getCurHP();
  }

  public void setCurHP(int i) {
    rarity.setCurHP(i);
  }

  public int getMaxMP() {
    return rarity.getMaxMP();
  }

  public int getCurMP() {
    return rarity.getCurMP();
  }

  public void setCurMP(int i) {
    rarity.setCurMP(i);
  }

  public boolean mPExhausted() {
    if (this.getCurMP() <= 0) {
      return true;
    }
    return false;
  }

  public int getRunnable() {
    return rarity.getRunnable();
  }

  public int randomMove() {
    Random ran = new Random();
    return ran.nextInt(4);
  }

  public void printMoves() {
    int i = 0;
    for (Attack a : this.getAttacks()) {
      System.out.printf("  %d: %13s  Power:%d  MP:%d  Effect:", i, a.getName(),
          (a.baseDamage() + this.rarity.getDamageBoost()), a.getCost());
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
    System.out.printf("\tname: %s\n\tstatus: %s\n\tHP: %d/%d\n\tMP: %d/%d\n", this.getName(), this.getStatus(), this.getCurHP(), this.getMaxHP(),
        this.getCurMP(), this.getMaxMP());
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
    if(this.status == "freeze") {
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

}