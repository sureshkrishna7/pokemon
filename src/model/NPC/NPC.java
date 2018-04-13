package model.NPC;

import java.util.ArrayList;
import java.util.Map;

import model.Items;
import model.Pokemon;
import model.UsableItems.UsableItem;

public abstract class NPC extends Items {
  private String name;
  private boolean hostile;
  protected Pokemon currentPokemon; // current battle pokemon, switch dynamically while in battle
  protected Map<String, ArrayList<UsableItem>> inventory;
  protected ArrayList<Pokemon> listOfPokemon;
  /*
   * NPC(String, boolean, ArrayList<Pokemon>) -- this constructor creates an instance of
   * an NPC object. The name will be unique. The listOfPokemon will be unique as well,
   * and so will need to be instatiated (hard coded) and then passed in as a parameter when
   * instantiating NPC object.
   */
  public NPC(char id, String name, boolean hostile) {
    super(id);
    this.name = name;
    this.hostile = hostile;
  }
  
  public boolean getHostility() {
    return hostile;
  }

  /*
   *  this method in the class of the specific NPC has hard coded Strings, it returns one specific
   *  to the number passed in which will indicate a specific situation.
   *    0 - Pokemon took damage
   *    1 - Pokemon afflicted damage
   *    2 - won battle
   *    3 - lost battle
   */
  public abstract String getDialogue(int i);
  
  /*
   *  this method in the class of the specific NPC has different strategies for when the NPC will
   *  switch out their Pokemon. This will be called at every move, and will switch or won't. 
   */
  public abstract void switchPokemon();
  
  /*
   *  this method will use an item, dependent on the strategy of the specific NPC implementing this 
   *  method.
   */
  public abstract void useItem();
  
  public abstract void initializePokeList();
  
  public abstract void initializeInventory();
}