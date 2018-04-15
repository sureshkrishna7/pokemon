package model.UsableItems;

import model.Items;
import model.Pokemon;
import model.Trainer;
import model.NPC.NPC;

// needs a use method
public class Ether extends Items implements UsableItem {

  public Ether(char c) {
    super(c);
  }

  public Ether(char c, int x, int y) {
    super(c, x, y);
  }

  @Override
  public String use(Trainer trainer, Pokemon p) {
    int restored = 0;
    
    if(p.isExhausted()) return "Cannot use!";
    trainer.getInventory().get("ether").remove(0);
    if (p.getCurMP() + 4 > p.getMaxMP()) {
      restored = p.getMaxMP() - p.getCurMP();
      p.setCurMP(p.getMaxMP());
    } else {
      p.setCurMP(p.getCurMP() + 4);
      restored = 4;
    }
    return restored + "MP restored!";
  }

  @Override
  public String use(NPC npc, Pokemon p) {
    // TODO Auto-generated method stub
    return null;
  }
}
