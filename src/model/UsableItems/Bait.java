package model.UsableItems;

import java.util.Random;

import model.Items;
import model.Pokemon;
import model.Trainer;
import model.NPC.NPC;

public class Bait extends Items implements UsableItem {

  public Bait(char c) {
    super(c);
  }

  public Bait(char c, int x, int y) {
    super(c, x, y);
  }

  /*
   * use(Trainer, Pokemon) -- Trainer throws bait at Pokemon. Trainer loses bait
   * item, Pokemon takes bait or doesn't, dependent on takeBaitChance field in
   * Statistics (which is in turn set by level of Pokemon). If bait is taken,
   * runChance is raised but catchChance is lowered (done in setStatus call. Bait
   * effect will last a time as determined by a LocalDate variable. It assumes
   * there is at least one bait to be used.
   */
  @Override
  public String use(Trainer trainer, Pokemon wild) {
    Random ran = new Random();
    double pivot = ran.nextDouble();

    trainer.getSafariInventory().get("bait").remove(0);
    // if selection below threshold of take bait
    if (pivot < wild.getTakeBaitChance()) {
      // use bait
      wild.setStatus("baited");
      return "Took bait!";
    }
    return "Bait refused.";
  }

  @Override
  public String use(NPC npc, Pokemon p) {
    // TODO Auto-generated method stub
    return null;
  }

}
