package model.UsableItems;

import model.Items;
import model.Pokemon;
import model.Trainer;
import model.NPC.NPC;

// needs a use method
public class Revive extends Items implements UsableItem {

  public Revive(char c) {
    super(c);
  }

  public Revive(char c, int x, int y) {
    super(c, x, y);
  }

  @Override
  public String use(Trainer trainer, Pokemon p) {
    trainer.getInventory().get("revive").remove(0);
    if (!p.isExhausted())
      return "No Effect...";

    int hPrestored = p.getMaxHP();
    int mPrestored = p.getMaxMP() - p.getCurMP();

    p.setCurHP(p.getMaxHP());
    p.setCurMP(p.getMaxMP());
    return hPrestored + "HP ," + mPrestored + "MP restored!";

  }

  @Override
  public String use(NPC npc, Pokemon p) {
    npc.getInventory().get("revive").remove(0);

    int hPrestored = p.getMaxHP();
    int mPrestored = p.getMaxMP() - p.getCurMP();

    p.setCurHP(p.getMaxHP());
    p.setCurMP(p.getMaxMP());
    return hPrestored + "HP ," + mPrestored + "MP restored!";
  }
}
