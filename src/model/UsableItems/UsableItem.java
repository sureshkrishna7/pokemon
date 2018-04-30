package model.UsableItems;

import java.io.Serializable;

import model.Pokemon;
import model.Trainer;
import model.NPC.NPC;

public interface UsableItem extends Serializable{
  
  public String use(Trainer trainer, Pokemon p);

  public String use(NPC npc, Pokemon p);
  
}
