package model.NPC;

import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

import model.Items;
import model.Pokemon;
import model.UsableItems.UsableItem;

/* This is a general NPC class. We can create specific NPCs that extend this class.
 * The "Vector<String>dialogues" can contain Strings of dialogue that the NPC will output 
 * Hostility indicates that the NPC will fight or not	*/

public abstract class NPC extends Items {
  public String name;
  public ArrayList<String> dialogues;
  private ArrayList<Pokemon> listOfPokemon;
  private Map<String, ArrayList<UsableItem>> inventory;
  private boolean hostile;

  /*
   * NPC(String, boolean, ArrayList<Pokemon>) -- this constructor creates an instance of
   * an NPC object. The name will be unique. The listOfPokemon will be unique as well,
   * and so will need to be instatiated (hard coded) and then passed in as a parameter when
   * instantiating NPC object.
   */
  public NPC(String name, boolean hostile, ArrayList<String> dialogues, ArrayList<Pokemon> listOfPokemon) {
    super('N');
    this.name = name;
    this.hostile = hostile;
    this.dialogues = dialogues;
    this.listOfPokemon = listOfPokemon;
  }

  public void addPokemon(Pokemon p) {
    listOfPokemon.add(p);
  }

  public abstract String getDialogue(int i);
  
  public boolean getHostility() {
    return hostile;
  }
}
