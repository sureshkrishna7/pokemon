package model.UsableItems;

import model.Items;
import model.Pokemon;
import model.Trainer;
import model.NPC.NPC;

// needs a use method
public class MidEther extends Items implements UsableItem {

  public MidEther(char c) {
    super(c);
  }

  public MidEther(char c, int x, int y) {
    super(c, x, y);
  }

  @Override
  public String use(Trainer trainer, Pokemon p) {
    int restored = 0;
    
    if(p.isExhausted()) return "Cannot use!";
    trainer.getInventory().get("mid ether").remove(0);
    if (p.getCurMP() + 8 > p.getMaxMP()) {
      restored = p.getMaxMP() - p.getCurMP();
      p.setCurMP(p.getMaxMP());
    } else {
      p.setCurMP(p.getCurMP() + 8);
      restored = 8;
    }
    return restored + "MP restored!";
  }

  @Override
  public String use(NPC npc, Pokemon p) {
    // TODO Auto-generated method stub
    return null;
  }
}
