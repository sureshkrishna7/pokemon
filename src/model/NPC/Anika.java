package model.NPC;

import java.util.ArrayList;
import java.util.HashMap;
import model.Pokemon;
import model.Trainer;
import model.UsableItems.FullTonic;
import model.UsableItems.Heal;
import model.UsableItems.MidEther;
import model.UsableItems.UsableItem;

public class Anika extends NPC {

  public Anika(String name, boolean hostile) {
    super('A', name, hostile);
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
      return "How???";
    }
    if (i == 1) {
      return "Your time will come.";
    }
    if (i == 2) {
      return ".......";
    } 
    if ( i == 3) {
      return "Now get out of here.";
    }
    else {
      return "You cheated!";
      
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
     * switch if curPokemon curMP less than 1/3 of it's max HP
     */
    if (this.currentPokemon.getCurMP() < (int)(this.currentPokemon.getMaxMP() * 0.3)) {
      for (Pokemon p : this.listOfPokemon) {
        if (p.getCurMP() >= (int)(p.getMaxMP() * 0.3)) {
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
    if (this.currentPokemon.getCurMP() < (int) (this.currentPokemon.getMaxMP() * 0.20)) {
      if (this.inventory.containsKey("mid ether") && this.inventory.get("mid ether").size() > 0) {
        this.inventory.get("mid ether").get(0).use(this, this.currentPokemon);
        return "mid ether";
      }
    }
    else if (this.currentPokemon.getStatus() != "normal") {
      if(this.inventory.containsKey("heal") && this.inventory.get("heal").size() > 0) {
        this.inventory.get("heal").get(0).use(this, this.currentPokemon);
        return "heal";
      }
    }
    return null;
  }// end useItem()

  @Override
  public void initializePokeList() {
    this.listOfPokemon.add(new Pokemon("Vulpix", 2, 'R', 'F', null));
    this.listOfPokemon.add(new Pokemon("Glaceon", 4, 'M', 'I', null));
    this.listOfPokemon.add(new Pokemon("Starmie", 4, 'M', 'W', null));
    this.listOfPokemon.add(new Pokemon("Seaking", 9, 'R', 'W', null));
  }

  @Override
  public void initializeInventory() {
    ArrayList<UsableItem> l1 = new ArrayList<UsableItem>();
    ArrayList<UsableItem> l2 = new ArrayList<UsableItem>();
    ArrayList<UsableItem> l3 = new ArrayList<UsableItem>();
    l1.add(new FullTonic('U'));
    l1.add(new FullTonic('U'));
    l1.add(new FullTonic('U'));
    l2.add(new MidEther('U'));
    l2.add(new MidEther('U'));
    l2.add(new MidEther('U'));
    l3.add(new Heal('U'));
    l3.add(new Heal('U'));
    this.inventory.put("full tonic", l1);
    this.inventory.put("mid ether", l2);
    this.inventory.put("heal", l3);
  }

  @Override
  public void setCoords() {
    // TODO Auto-generated method stub
    
  }


}
