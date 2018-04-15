package model.UsableItems;

import model.Items;
import model.Pokemon;
import model.Trainer;
import model.NPC.NPC;

// needs a use method
public class Tonic extends Items implements UsableItem {

  public Tonic(char c) {
    super(c);
  }

  public Tonic(char c, int x, int y) {
    super(c, x, y);
  }

  @Override
  public String use(Trainer trainer, Pokemon p) {
    int restored = 0;

    if (p.isExhausted())
      return "Cannot use!";
    trainer.getInventory().get("tonic").remove(0);
    if (p.getCurHP() + 150 > p.getMaxHP()) {
      restored = p.getMaxHP() - p.getCurHP();
      p.setCurHP(p.getMaxHP());
    } else {
      p.setCurHP(p.getCurHP() + 150);
      restored = 150;
    }
    return restored + "HP restored!";
  }

  @Override
  public String use(NPC npc, Pokemon p) {
    int restored = 0;

    npc.getInventory().get("tonic").remove(0);
    if (p.getCurHP() + 150 > p.getMaxHP()) {
      restored = p.getMaxHP() - p.getCurHP();
      p.setCurHP(p.getMaxHP());
    } else {
      p.setCurHP(p.getCurHP() + 150);
      restored = 150;
    }
    return restored + "HP restored!";
  }
}
