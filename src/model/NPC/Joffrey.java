package model.NPC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import model.Pokemon;
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
  }

  /*
   * 0 - Pokemon took damage 
   * 1 - Pokemon afflicted damage 
   * 2 - won battle 
   * 3 - lost battle
   */
  @Override
  public String getDialogue(int i) {
    if (i == 0) {
      return "Wait til my mom hears about this!";
    }
    if (i == 1) {
      return "Bring me his head...";
    }
    if (i == 2) {
      return "Run away. Now.";
    } else {
      return "It's not over...";
    }
  }

  @Override
  public void switchPokemon() {
    if (this.currentPokemon.getCurHP() < (int) (this.currentPokemon.getMaxHP() * 0.25)) {
      for (Pokemon p : this.listOfPokemon) {
        if (p.getCurHP() < (int) (p.getMaxHP() * 0.25))
          this.currentPokemon = p;
      }
    }
  }

  @Override
  public void useItem() {
    if (this.currentPokemon.getCurHP() < (int) (this.currentPokemon.getMaxHP() * 0.20)) {
      if (this.inventory.containsKey("tonic")) {
        this.inventory.get("tonic").get(0).use(this.currentPokemon);
      }
    }
  }

  @Override
  public void initializePokeList() {
    this.listOfPokemon.add(new Pokemon("Rhydon", 3, 'M', 'E', null));
    this.listOfPokemon.add(new Pokemon("Sandslash", 4, 'C', 'I', null));
    this.listOfPokemon.add(new Pokemon("Cubone", 5, 'C', 'E', null));
    this.listOfPokemon.add(new Pokemon("Rhydon", 6, 'M', 'E', null));
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
