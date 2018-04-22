package model.NPC;

import java.util.ArrayList;
import java.util.HashMap;
import model.Pokemon;
import model.Trainer;
import model.UsableItems.FullTonic;
import model.UsableItems.Heal;
import model.UsableItems.MidEther;
import model.UsableItems.UsableItem;

public class Elroy extends NPC {

  public Elroy(String name, boolean hostile) {
    super('E', name, hostile);
    this.inventory = new HashMap<>();
    this.listOfPokemon = new ArrayList<Pokemon>();
    initializeInventory();
  }

  @Override
  public int getAttack(Trainer trainer) {
    return 0;
  }

  /*
   * (from the NPC's perspective) 0 - Welcome greeting 1 - You made a purchase 2 -
   * Out of that item 3 - Insufficient funds 4 - departing statement
   */
  @Override
  public String getDialogue(int i) {
    if (i == 0) {
      return "Welcome Welcome Welcome!";
    }
    if (i == 1) {
      return "Thank you for your patronage!";
    }
    if (i == 2) {
      return "I'm all out, so sorry...";
    }
    if (i == 3) {
      return "Not enough dough.";
    } else {
      return "Until next time!";

    }
  }

  @Override
  public String switchPokemon() {
    return null;
  }

  /*
   * useItem() -- will use an item, meaning the item will be taken out of the
   * inventory of the merchant and added to the inventory of the trainer.
   */
  @Override
  public String useItem() {

    return null;
  }// end useItem()

  @Override
  public void initializePokeList() {
  }

  @Override
  public void initializeInventory() {
    ArrayList<UsableItem> l1 = new ArrayList<UsableItem>();
    ArrayList<UsableItem> l2 = new ArrayList<UsableItem>();
    ArrayList<UsableItem> l3 = new ArrayList<UsableItem>();

    for (int i = 0; i < 11; i++) {

    }
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

}
