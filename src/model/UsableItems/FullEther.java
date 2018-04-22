package model.UsableItems;

import model.Items;
import model.Pokemon;
import model.Trainer;
import model.NPC.NPC;

// needs a use method
public class FullEther extends Items implements UsableItem {

  public FullEther(char c) {
    super(c);
  }

  public FullEther(char c, int x, int y) {
    super(c, x, y);
  }

  @Override
  public String use(Trainer trainer, Pokemon p) {
    int restored = 0;
    
    if(p.isExhausted()) return "Cannot use!";
    trainer.getInventory().get("full ether").remove(0);
    if (p.getCurMP() + 10 > p.getMaxMP()) {
      restored = p.getMaxMP() - p.getCurMP();
      p.setCurMP(p.getMaxMP());
    } else {
      p.setCurMP(p.getCurMP() + 10);
      restored = 10;
    }
    return restored + "MP restored!";
  }

  @Override
  public String use(NPC npc, Pokemon p) {
    int restored = 0;
    
    npc.getInventory().get("full ether").remove(0);
    if (p.getCurMP() + 10 > p.getMaxMP()) {
      restored = p.getMaxMP() - p.getCurMP();
      p.setCurMP(p.getMaxMP());
    } else {
      p.setCurMP(p.getCurMP() + 10);
      restored = 10;
    }
    return restored + "MP restored!";
  }
}
