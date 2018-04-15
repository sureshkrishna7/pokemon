package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import model.UsableItems.*;

public class Trainer extends Items {

  private String filepath; // TODO: need to set proper filepath for the user or Image of the player
  private String trainer;
  private ArrayList<Pokemon> listOfPokemon;
  private Map<String, ArrayList<UsableItem>> inventory;
  private Map<String, ArrayList<UsableItem>> safariInventory;
  private int money;
  private Point currentPos = new Point();
  private Pokemon currentPokemon; // current battle pokemon, switch dynamically while in battle
  private int safariSteps; // 500 steps in safari mode
  private int currentBalls; // 30 balls only in safari mode
  private boolean safariMode;
  Random ran;
  double pivot;

  /*
   * Initialize with one pokemon or can make Player pick it up Rick Mercer as
   * easter egg, professor that gives you a pokemon
   */
  public Trainer(String name) {
    super('O');
    this.trainer = name;
    this.money = 5000;
    this.listOfPokemon = new ArrayList<Pokemon>();
    listOfPokemon.add(new Pokemon("Squirtle", 20, 'C', 'W', null));
    this.inventory = new HashMap<>();
    this.safariInventory = new HashMap<>();
    initializeInventory();
    initializeSafariInventory();
    setBattlePokemon(listOfPokemon.get(0));

    // Set this to 500 when the trainer pays money to NPC or menu option
    this.safariSteps = 0;
    this.currentBalls = 0;
    this.safariMode = false;
  }

  /*
   * useSafariItem(String, Pokemon) -- uses the item on the wild Pokemon, done by
   * switch statement with cases for the String that is passed in. A conditional
   * is executed with a method inside of it that attempts the move and returns a
   * boolean with the result (if the move succeeded or failed). This method
   * assumes that String s is a valid key in the HashMap safariInventory and that
   * the value for the key has at least one item in it. At the end of the block
   * the item is removed from its respective location and a String is returned
   * detailing the outcome of the item usage.
   */
  public String useSafariItem(String s, Pokemon wild) {
    String result = "Failed!";

    switch (s) {
    case "bait":
      if (this.throwBait(wild)) {
        result = this.getSafariInventory().get(s).get(0).use(wild);
        // here the stats of the wild Pokemon need to be adjusted, and it needs to be determined for how long
        // (probably a timer, but not sure how to implement quite yet...)
      }
      break;
    case "rock":
      if (this.throwRock(wild)) {
        result = this.getSafariInventory().get(s).get(0).use(wild);
      }
      break;
    case "pokeball":
      if (this.throwSafariBall(wild)) {
        result = this.getSafariInventory().get(s).get(0).use(wild);
      }
      break;
    }
    if(result != "Failed!") this.getSafariInventory().get(s).remove(0);
    return result;
  }

  private void initializeSafariInventory() {
    ArrayList<UsableItem> l1 = new ArrayList<UsableItem>();
    ArrayList<UsableItem> l2 = new ArrayList<UsableItem>();
    ArrayList<UsableItem> l3 = new ArrayList<UsableItem>();
    l1.add(new Bait('U'));
    l1.add(new Bait('U'));
    l2.add(new Rock('U'));
    l2.add(new Rock('U'));
    l3.add(new PokeBall('U'));
    l3.add(new PokeBall('U'));
    l3.add(new PokeBall('U'));
    this.safariInventory.put("bait", l1);
    this.safariInventory.put("rock", l2);
    this.safariInventory.put("pokeball", l3);
  }

  private void initializeInventory() {
    ArrayList<UsableItem> l1 = new ArrayList<UsableItem>();
    ArrayList<UsableItem> l2 = new ArrayList<UsableItem>();
    ArrayList<UsableItem> l3 = new ArrayList<UsableItem>();
    l1.add(new Tonic('U'));
    l1.add(new Tonic('U'));
    l1.add(new Tonic('U'));
    l2.add(new Ether('U'));
    l2.add(new Ether('U'));
    l3.add(new Heal('U'));
    this.inventory.put("tonic", l1);
    this.inventory.put("ether", l2);
    this.inventory.put("heal", l3);
  }

  public void printListOfPokemon() {
    for (Pokemon p : listOfPokemon) {
      System.out.printf("  Name: %s  Level: %d  Type: %s  HP: %d  MP: %d\n", p.getName(), p.getLevel(),
          p.getPokemonType(), p.getCurHP(), p.getCurMP());
    }
  }

  public void printInventory() {
    int i = 1;
    for (Map.Entry<String, ArrayList<UsableItem>> entry : inventory.entrySet()) {
      System.out.println(i++ + ": " + entry.getKey() + ", " + entry.getValue().size());
    }
  }

  public Map.Entry<String, ArrayList<UsableItem>> safariInventoryByIndex(int choice) {
    int i = 1;
    for (Map.Entry<String, ArrayList<UsableItem>> entry : safariInventory.entrySet()) {
      if (i == choice)
        return entry;
      i++;
    }
    return null;
  }

  public void printSafariInventory() {
    int i = 1;
    for (Map.Entry<String, ArrayList<UsableItem>> entry : safariInventory.entrySet()) {
      System.out.println(i++ + ": use " + entry.getKey() + ", " + entry.getValue().size());
    }
  }

  /*
   * inventoryByIndex(int) -- this method is used to return the selected item from
   * a numbered list in console output (a variation will be integrated for actual
   * game). Used like this for simplicity, instead of user typing in String (key
   * in HashMap).
   */
  public Map.Entry<String, ArrayList<UsableItem>> inventoryByIndex(int choice) {
    int i = 1;
    for (Map.Entry<String, ArrayList<UsableItem>> entry : inventory.entrySet()) {
      if (i == choice)
        return entry;
      i++;
    }
    return null;
  }

  public Map<String, ArrayList<UsableItem>> getInventory() {
    return this.inventory;
  }

  public Map<String, ArrayList<UsableItem>> getSafariInventory() {
    return this.safariInventory;
  }

  public void setBattlePokemon(Pokemon p) {
    this.currentPokemon = p;
  }

  public Pokemon getCurPokemon() {
    return currentPokemon;
  }

  /*
   * // Add 1) public void addPokemon(String str) { capturedPokemon.add(new
   * Pokemon(str)); }
   * 
   * // Add 2) public void addPokemon(String name, String rareOfPokemon, String
   * typeOfPokemon) { Pokemon s = new Pokemon(name, rareOfPokemon, typeOfPokemon);
   * capturedPokemon.add(s); }
   * 
   * // Add 3) public void addPokemon(String name, char rareOfPokemon, char
   * typeOfPokemon) { Pokemon s = new Pokemon(name, rareOfPokemon, typeOfPokemon,
   * null); capturedPokemon.add(s); }
   * 
   */
  // Add 4)
  public void addPokemon(Pokemon s) {
    listOfPokemon.add(s);
  }

  /*
   * ********** To go to Safari Zone **********
   */
  public boolean goToSafariZone() {
    if (money >= 500) {
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
   * ********** To get out of Safari Zone **********
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

  public ArrayList<Pokemon> getPokeList() {
    return listOfPokemon;
  }

  public void setPosition(int x, int y) {

    currentPos.x = x;
    currentPos.y = y;
  }

  public void setPosition(Point pos) {
    System.out.println("Point: " + pos);
    currentPos.x = pos.x;
    currentPos.y = pos.y;
  }
  
  /*
   * allPokemonExhausted(Trainer) -- this method iterates through all of the
   * Pokemon in the Trainer's pokeList. It returns false as soon as it finds one
   * Pokemon that is not exhausted (pokemon.isExhausted() == false), otherwise
   * returns true if the list is traversed and this condition is not met.
   */
  public boolean allPokemonExhausted() {
    for (Pokemon p : listOfPokemon) {
      if (p.isExhausted() == false)
        return false;
    }
    return true;
  }// end allPokemonExhausted()

  /*
   * throwSafariBall(Pokemon) -- Trainer throws a safari ball at the wild Pokemon
   * in an attempt to catch it. currentBalls is decreased by 1 and the Pokemon is
   * or is not caught, depending on the catchChance variable and a random pivot
   * selector. This assumes the Trainer has at least one ball to throw, will be
   * checked elsewhere. Returns true if the Pokemon is caught, else returns false.
   */
  public boolean throwSafariBall(Pokemon wild) {
    // this is a simple random generator, looks weird but works better than Random
    //double pivot = ThreadLocalRandom.current().nextDouble(0.1, 1.0);
    Random ran = new Random();
    double pivot = ran.nextDouble();

    //System.out.printf("pivot=%.3f, catch chance=%.3f\n", pivot, wild.getTakeBaitChance());
    // if successfully caught Pokemon..
    if (pivot > wild.getCatchChance()) {
      this.listOfPokemon.add(wild);
      return true;
    }
    this.safariInventory.get("pokeball").remove(0);
    return false;
  }

  /*
   * throwBait(Pokemon) -- Trainer throws bait at Pokemon. Trainer loses bait
   * item, Pokemon takes bait or doesn't, dependent on takeBaitChance field in
   * Statistics (which is in turn set by level of Pokemon). If bait is taken,
   * runChance is raised but catchChance is lowered. Bait effect will last a time
   * as determined by a LocalDate variable. It assumes there is at least one bait
   * to be used.
   */
  public boolean throwBait(Pokemon wild) {
    // this is a simple random generator, looks weird but works better than Random
  //double pivot = ThreadLocalRandom.current().nextDouble(0.1, 1.0);
    Random ran = new Random();
    double pivot = ran.nextDouble();

    //System.out.printf("pivot=%3f, bait chance=%3f\n", pivot, wild.getTakeBaitChance());
    // if selection below threshold of take bait
    if (pivot < wild.getTakeBaitChance()) {
      // use bait
      wild.setStatus("baited");
      return true;
    }
    this.safariInventory.get("bait").remove(0);
    return false;
  }

  /*
   * throwRock(Pokemon) -- Trainer throws rock at Pokemon. Might miss, dependent
   * on Pokemon level. If hit a success, Pokemon catchChance is increased and
   * runChance is lowered. It assumes there is at least one rock to be used.
   */
  public boolean throwRock(Pokemon wild) {
    // this is a simple random generator, looks weird but works better than Random
    //double pivot = ThreadLocalRandom.current().nextDouble(0.1, 1.0);
    Random ran = new Random();
    double pivot = ran.nextDouble();

    double threshold = 0;
    threshold += (wild.getCurHP() / wild.getMaxHP()) * 0.5;
    threshold += (wild.getLevel() / 20) * 0.5;

    //System.out.printf("pivot=%.3f, rock chance=%.3f\n", pivot, threshold);
    if (pivot > threshold) {
      return true;
    }
    this.safariInventory.get("rock").remove(0);
    return false;
  }
}
