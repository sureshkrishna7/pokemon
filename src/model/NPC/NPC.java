package model.NPC;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import javafx.scene.image.Image;
import model.Items;
import model.Pokemon;
import model.Trainer;
import model.UsableItems.UsableItem;

public abstract class NPC extends Items {
  private String name;
  private boolean hostile;
  
  protected Pokemon currentPokemon; // current battle pokemon, switch dynamically while in battle
  protected Map<String, ArrayList<UsableItem>> inventory;
  protected ArrayList<Pokemon> listOfPokemon;
  protected double xCoord;
  protected double yCoord;
  protected Image img;

  /*
   * NPC(String, boolean, ArrayList<Pokemon>) -- this constructor creates an
   * instance of an NPC object. The name will be unique. The listOfPokemon will be
   * unique as well, and so will need to be instatiated (hard coded) and then
   * passed in as a parameter when instantiating NPC object.
   */
  public NPC(char id, String name, boolean hostile) {
    super(id);
    this.name = name;
    this.hostile = hostile;
  }

  /*
   * setRandomCurPoke() -- this method is called in the constructor of each NPC.
   * It sets the NPC's current Pokemon to a random Pokemon in the listOfPokemon.
   */
  public void setRandomCurPoke() {
    Random ran = new Random();
    int index = ran.nextInt(this.listOfPokemon.size());
    this.currentPokemon = this.listOfPokemon.get(index);
  }

  public Map<String, ArrayList<UsableItem>> getInventory() {
    return this.inventory;
  }
  
  public double getYCoord() {
    return yCoord;
  }

  public double getXCoord() {
    return xCoord;
  }
  
  public Image getImage() {
    return img;
  }
  
  public String getName() {
    return name;
  }

  public Pokemon getCurPokemon() {
    return currentPokemon;
  }

  public boolean getHostility() {
    return hostile;
  }

  /*
   * allPokemonExhausted(NPC) -- this method iterates through all of the Pokemon
   * in the NPC's pokeList. It returns false as soon as it finds one Pokemon that
   * is not exhausted (pokemon.isExhausted() == false), otherwise returns true if
   * the list is traversed and this condition is not met.
   */
  public boolean allPokemonExhausted() {
    for (Pokemon p : this.listOfPokemon) {
      if (p.isExhausted() == false)
        return false;
    }
    return true;
  }// end allPokemonExhausted()

  /*
   * this method in the class of the specific NPC has hard coded Strings, it
   * returns one specific to the number passed in which will indicate a specific
   * situation. 0 - Pokemon took damage 1 - Pokemon afflicted damage 2 - won
   * battle 3 - lost battle
   */
  public abstract String getDialogue(int i);

  /*
   * this method in the class of the specific NPC has different strategies for
   * when the NPC will switch out their Pokemon. This will be called at every
   * move, and will switch or won't.
   */
  public abstract String switchPokemon();

  /*
   * this method will use an item, dependent on the strategy of the specific NPC
   * implementing this method.
   */
  public abstract String useItem();

  public abstract int getAttack(Trainer trainer);

  public abstract void initializePokeList();

  public abstract void initializeInventory();
  
  public abstract void setCoords();
}