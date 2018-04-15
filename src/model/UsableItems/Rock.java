package model.UsableItems;

import java.util.Random;

import model.Items;
import model.Pokemon;
import model.Trainer;
import model.NPC.NPC;

public class Rock extends Items implements UsableItem {

  public Rock(char c) {
    super(c);
  }
  
  public Rock(char c, int x, int y) {
    super(c, x, y);
  }

  /*
   * use(Trainer, Pokemon) -- Trainer throws rock at Pokemon. Might miss, dependent
   * on Pokemon level. If hit a success, Pokemon catchChance is increased and
   * runChance is lowered. It assumes there is at least one rock to be used.
   */
  @Override
  public String use(Trainer trainer, Pokemon p) {
    trainer.getSafariInventory().get("rock").remove(0);
    
    Random ran = new Random();
    double pivot = ran.nextDouble();

    double threshold = 0;
    threshold += (p.getCurHP() / p.getMaxHP()) * 0.5;
    threshold += (p.getLevel() / 20) * 0.5;

    if (pivot > threshold) {
      p.setCurHP((int)(p.getCurHP() - (p.getMaxHP() * 0.2)));
      p.setCatchChance(p.getCatchChance() + 0.5);
      p.setRunChance(p.getRunChance() - 0.5);
      return "Hit!";
    }
    return "Miss...";
  }
  
  @Override
  public String use(NPC npc, Pokemon p) {
    return null;
  }

}