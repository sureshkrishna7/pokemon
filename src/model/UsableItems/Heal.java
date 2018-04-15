package model.UsableItems;

import model.Items;
import model.Pokemon;
import model.Trainer;
import model.NPC.NPC;

// needs a use method
public class Heal extends Items implements UsableItem {

  public Heal(char c) {
    super(c);
  }

  public Heal(char c, int x, int y) {
    super(c, x, y);
  }

  @Override
  public String use(Trainer trainer, Pokemon p) {
    trainer.getInventory().get("heal").remove(0);
    
    if (p.getStatus() == "normal")
      return "No Effect...";
    
    p.setStatus("normal");
    return "Status restored.";

  }

  @Override
  public String use(NPC npc, Pokemon p) {
    // TODO Auto-generated method stub
    return null;
  }
}
