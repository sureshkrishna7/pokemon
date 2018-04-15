package model.UsableItems;

import model.Pokemon;
import model.Trainer;
import model.NPC.NPC;

public interface UsableItem {
  
  public String use(Trainer trainer, Pokemon p);

  public String use(NPC npc, Pokemon p);
  
}
