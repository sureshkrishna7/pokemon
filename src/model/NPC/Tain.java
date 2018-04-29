package model.NPC;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.image.Image;
import model.Pokemon;
import model.Trainer;
import model.UsableItems.FullTonic;
import model.UsableItems.MidTonic;
import model.UsableItems.UsableItem;

public class Tain extends NPC {

  public Tain(String name, boolean hostile) {
    super('N', name, hostile);
    this.inventory = new HashMap<>();
    this.listOfPokemon = new ArrayList<Pokemon>();
    this.img = new Image("file:src/images/Tain.png");
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
      return "!!! Chk Chk Chk!";
    }
    if (i == 1) {
      return "Clear out!";
    }
    if (i == 2) {
      return "Come on, man!";
    } 
    if ( i == 3) {
      return "CLEAR OUT FOR REAL!";
    }
    else {
      return "No way! Didn't see that coming.";
      
    }
  }

  /*
   * switchPokemon() -- will switch the NPC's curPokemon in battle under varied conditions.
   * Will return true if a switch was made thus indicating a turn was consumed, else will return false.
   */
  @Override
  public String switchPokemon() {
    /*
     * switch to non-knocked out Pokemon if curPokemon is exhausted
     */
    if(this.currentPokemon.isExhausted()) {
      for (Pokemon p : this.listOfPokemon) {
        if (!p.isExhausted()) {
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
    if (this.currentPokemon.getCurHP() < (int) (this.currentPokemon.getMaxHP() * 0.10)) {
      if (this.inventory.containsKey("full tonic") && this.inventory.get("full tonic").size() > 0) {
        this.inventory.get("full tonic").get(0).use(this, this.currentPokemon);
        return "full tonic";
      }
    }
    else if (this.currentPokemon.getCurHP() < (int) (this.currentPokemon.getMaxHP() * 0.30)) {
      if (this.inventory.containsKey("mid tonic") && this.inventory.get("mid tonic").size() > 0) {
        this.inventory.get("mid tonic").get(0).use(this, this.currentPokemon);
        return "mid tonic";
      }
    }
    return null;
  }// end useItem()

  @Override
  public void initializePokeList() {
    this.listOfPokemon.add(new Pokemon("Charmander", 5, 'M', 'F', null));
    this.listOfPokemon.add(new Pokemon("Cubone", 9, 'C', 'E', null));
    this.listOfPokemon.add(new Pokemon("Seadra", 7, 'C', 'W', null));
    this.listOfPokemon.add(new Pokemon("Charmeleon", 12, 'M', 'F', null));
  }

  @Override
  public void initializeInventory() {
    ArrayList<UsableItem> l1 = new ArrayList<UsableItem>();
    ArrayList<UsableItem> l2 = new ArrayList<UsableItem>();
    ArrayList<UsableItem> l3 = new ArrayList<UsableItem>();
    l1.add(new FullTonic('U'));
    l1.add(new FullTonic('U'));
    l1.add(new FullTonic('U'));
    l1.add(new FullTonic('U'));
    l2.add(new MidTonic('U'));
    l2.add(new MidTonic('U'));
    l2.add(new MidTonic('U'));
    l2.add(new MidTonic('U'));
    this.inventory.put("full tonic", l1);
    this.inventory.put("mid tonic", l2);
  }

}
