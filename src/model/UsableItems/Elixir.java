package model.UsableItems;

import model.Items;
import model.Pokemon;
import model.Trainer;
import model.NPC.NPC;

// needs a use method
public class Elixir extends Items implements UsableItem {

  public Elixir(char c) {
    super(c);
  }

  public Elixir(char c, int x, int y) {
    super(c, x, y);
  }

  @Override
  public String use(Trainer trainer, Pokemon p) {
    if(p.isExhausted()) return "Cannot use!";
    trainer.getInventory().get("elixir").remove(0);
    
    int hPrestored = p.getMaxHP() - p.getCurHP();
    int mPrestored = p.getMaxMP() - p.getCurMP();
    
    p.setCurHP(p.getMaxHP());
    p.setCurMP(p.getMaxMP());
    
    return hPrestored + "HP ," + mPrestored + "MP restored!";
  }

  @Override
  public String use(NPC npc, Pokemon p) {
    // TODO Auto-generated method stub
    return null;
  }
}
