package model.NPC;

import java.util.ArrayList;
import java.util.HashMap;
import model.Pokemon;
import model.Trainer;
import model.UsableItems.Ether;
import model.UsableItems.Heal;
import model.UsableItems.Tonic;
import model.UsableItems.UsableItem;

public class Joffrey extends NPC {

  public Joffrey(String name, boolean hostile) {
    super('J', name, hostile);
    this.inventory = new HashMap<>();
    this.listOfPokemon = new ArrayList<Pokemon>();
    initializeInventory();
    initializePokeList();
    setRandomCurPoke();
  }
  
  @Override
  public int getAttack(Trainer trainer) {
    int choice;
    do {
      choice = this.currentPokemon.randomMove();
    }while(this.currentPokemon.invalidMove(choice));
    return choice;  
  }
  
  /*
   * (from the NPC's perspective)
   * 0 - Pokemon took damage 
   * 1 - Pokemon afflicted damage
   * 2 - Pokemon attack missed 
   * 3 - won battle 
   * 4 - lost battle
   */
  @Override
  public String getDialogue(int i) {
    if (i == 0) {
      return "Wait til my mom hears about this!";
    }
    if (i == 1) {
      return "Run away. Now.";
    }
    if (i == 2) {
      return "Stupid half Pokemon.";
    } 
    if ( i == 3) {
      return "Bring me his head...";
    }
    else {
      return "It's not over...";
      
    }
  }

  /*
   * switchPokemon() -- will switch the NPC's curPokemon in battle under varied conditions.
   * Will return true if a switch was made thus indicating a turn was consumed, else will return false.
   */
  @Override
  public String switchPokemon() {
    /*
     * switch if curPokemon curHP less than 1/4 of it's max HP
     */
    if (this.currentPokemon.getCurHP() < (int)(this.currentPokemon.getMaxHP() * 0.25)) {
      for (Pokemon p : this.listOfPokemon) {
        if (p.getCurHP() >= (int)(p.getMaxHP() * 0.25)) {
          this.currentPokemon = p;
          return p.getName();
        }
      }
      
    }
    
    /*
     * if cur Pokemon has insufficient MP to make any move...
     */
    if(!this.currentPokemon.canMove()) {
      for (Pokemon p : this.listOfPokemon) {
        if (p.canMove())
          this.currentPokemon = p;
          return p.getName();
      }
    }
    
    return null;
  }

  /*
   * useItem() -- will use an item on the NPC's curPokemon in battle under varied conditions.
   * Will return true if an item was used thus indicating a turn was consumed, else will return false.
   */
  @Override
  public String useItem() {
    if (this.currentPokemon.getCurHP() < (int) (this.currentPokemon.getMaxHP() * 0.20)) {
      if (this.inventory.containsKey("tonic")) {
        this.inventory.get("tonic").get(0).use(this.currentPokemon);
        return "tonic";
      }
    }
    return null;
  }

  @Override
  public void initializePokeList() {
    this.listOfPokemon.add(new Pokemon("Rhydon", 3, 'M', 'E', null));
    this.listOfPokemon.add(new Pokemon("Sandslash", 4, 'C', 'I', null));
    this.listOfPokemon.add(new Pokemon("Cubone", 5, 'C', 'E', null));
    this.listOfPokemon.add(new Pokemon("Glaceon", 6, 'M', 'I', null));
  }

  @Override
  public void initializeInventory() {
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


}
