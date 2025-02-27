package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import model.UsableItems.*;

public class Trainer extends Items implements Serializable{

  private String filepath; // TODO: need to set proper filepath for the user or Image of the player
  private String name;
  private ArrayList<Pokemon> listOfPokemon;
  private ArrayList<Pokemon> beforeSafariPokeList;
  private Map<String, ArrayList<UsableItem>> inventory;
  private Map<String, ArrayList<UsableItem>> itemsGainedInSafariZone;
  private Map<String, ArrayList<UsableItem>> safariInventory;
  private Point currentPos = new Point();
  private Pokemon currentPokemon; // current battle pokemon, switch dynamically while in battle
  private int allowedSafariSteps; // 500 steps in safari mode
  private int allowedCurrentBalls; // 30 balls only in safari mode
  Random ran;
  double pivot;

  /*
   * Initialize with one pokemon or can make Player pick it up Rick Mercer as
   * easter egg, professor that gives you a pokemon
   */
  public Trainer(String name) {
    super('O');
    this.name = name;
    this.listOfPokemon = new ArrayList<Pokemon>();
    this.beforeSafariPokeList = new ArrayList<Pokemon>();
    this.inventory = new HashMap<>();
    this.safariInventory = new HashMap<>();
    this.itemsGainedInSafariZone = new HashMap<>();

    //setUpDefault();

    // Set this to 500 when the trainer pays money to NPC or menu option
    this.allowedSafariSteps = 500;
    this.allowedCurrentBalls = 30;
  }

  public void setUpDefault() {
    listOfPokemon.add(new Pokemon("Squirtle", 20, 'C', 'W', null));
    setBattlePokemon(listOfPokemon.get(0));
    initializeInventory();
    initializeSafariInventory();
  }

  public void setThisAsInventory(Map<String, ArrayList<UsableItem>> invent) {
    this.inventory = invent;
  }

  public void setThisAsSafariInventory(Map<String, ArrayList<UsableItem>> safariInvent) {
    this.safariInventory = safariInvent;
  }
  
  /*
   * initializeSafariInventory() -- this method adds to the safari inventory 
   * 10 each rock and bait and 30 safari balls.
   */
  private void initializeSafariInventory() {
    ArrayList<UsableItem> l1 = new ArrayList<UsableItem>();
    ArrayList<UsableItem> l2 = new ArrayList<UsableItem>();
    ArrayList<UsableItem> l3 = new ArrayList<UsableItem>();
    for(int i = 1; i <= 10; i++) {
      l1.add(new Bait('U'));
      l2.add(new Rock('U'));
      l3.add(new SafariBall('U'));
    }
    for(int i = 1; i <= 20; i++) {
      l3.add(new SafariBall('U'));
    }
    this.safariInventory.put("bait", l1);
    this.safariInventory.put("rock", l2);
    this.safariInventory.put("safariball", l3);
  }

  private void initializeInventory() {
    ArrayList<UsableItem> l1 = new ArrayList<UsableItem>();
    ArrayList<UsableItem> l2 = new ArrayList<UsableItem>();
    ArrayList<UsableItem> l3 = new ArrayList<UsableItem>();
    l1.add(new FullTonic('U'));
    l1.add(new FullTonic('U'));
    l1.add(new FullTonic('U'));
    l2.add(new FullEther('U'));
    l2.add(new FullEther('U'));
    l2.add(new FullEther('U'));
    l2.add(new FullEther('U'));
    l3.add(new Heal('U'));
    this.inventory.put("full tonic", l1);
    this.inventory.put("full ether", l2);
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

  public Map<String, ArrayList<UsableItem>> getItemsGainedInSafariZone() {
    return this.itemsGainedInSafariZone;
  }

  public void setBattlePokemon(Pokemon p) {
    this.currentPokemon = p;
  }

  public String getName() {
    return name;
  }

  public Pokemon getCurPokemon() {
    return currentPokemon;
  }

  public void addPokemon(Pokemon s) {
    listOfPokemon.add(s);
  }

  public int getAllowedSafariSteps() {
    return allowedSafariSteps;
  }

  public int getAllowedCurrentBalls() {
    return allowedCurrentBalls;
  }

  public ArrayList<Pokemon> getPokeList() {
    return listOfPokemon;
  }

  public ArrayList<Pokemon> getBeforeSafariPokeList(){
    return beforeSafariPokeList;
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
   * throwSafariBall(Pokemon) -- calls use on safariball from safari inventory.
   * Assumes there is at least one bait to be used.
   */
  public String throwSafariBall(Pokemon wild) {
    return this.getSafariInventory().get("safariball").get(0).use(this, wild);
  }

  /*
   * throwBait(Pokemon) -- calls use on bait from safari inventory.
   * Assumes there is at least one bait to be used.
   */
  public String throwBait(Pokemon wild) {
    return this.getSafariInventory().get("bait").get(0).use(this, wild);
  }

  /*
   * throwRock(Pokemon) -- calls use on rock from safari inventory.
   * It assumes there is at least one rock to be used.
   */
  public String throwRock(Pokemon wild) {
    return this.getSafariInventory().get("rock").get(0).use(this, wild);
  }
}
