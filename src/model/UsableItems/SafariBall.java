package model.UsableItems;

import java.util.Random;

import model.Items;
import model.Pokemon;
import model.Trainer;
import model.NPC.NPC;

public class SafariBall extends Items implements UsableItem {

  public SafariBall(char c) {
    super(c);
  }

  public SafariBall(char c, int x, int y) {
    super(c, x, y);
  }

  /*
   * use(Trainer, Pokemon) -- Trainer throws a safari ball at the wild Pokemon in
   * an attempt to catch it. Safari ball count in safariInventory is decreased by
   * 1 and the Pokemon is or is not caught, depending on the catchChance variable
   * and a random pivot selector. This assumes the Trainer has at least one ball
   * to throw. Returns message depending on whether or not the Pokemon was caught.
   */
  @Override
  public String use(Trainer trainer, Pokemon p) {
    trainer.getSafariInventory().get("safariball").remove(0);

    Random ran = new Random();
    double pivot = ran.nextDouble();

    if (pivot > p.getCatchChance()) {
      trainer.getPokeList().add(p);
      return "Caught!!";
    }
    return "Missed!";
  }

  @Override
  public String use(NPC npc, Pokemon p) {
    // TODO Auto-generated method stub
    return null;
  }

}
