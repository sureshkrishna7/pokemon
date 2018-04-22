package model.UsableItems;
import model.Items;
import model.Pokemon;
import model.Trainer;
import model.NPC.NPC;

// needs a use method
public class FullTonic extends Items implements UsableItem {

  public FullTonic(char c) {
    super(c);
  }
  
  public FullTonic(char item, int x, int y) {
    super(item, x, y);
  }

  @Override
  public String use(Trainer trainer, Pokemon p) {
    int restored = 0;
    
    if(p.isExhausted()) return "Cannot use!";
    trainer.getInventory().get("full tonic").remove(0);
    if(p.getCurHP() + 500 > p.getMaxHP()) {
      restored = p.getMaxHP() - p.getCurHP();
      p.setCurHP(p.getMaxHP());
    }
    else {
      p.setCurHP(p.getCurHP() + 500);
      restored = 500;
    }
    return restored + "HP restored!";
  }

  @Override
  public String use(NPC npc, Pokemon p) {
    int restored = 0;
    
    npc.getInventory().get("full tonic").remove(0);
    if(p.getCurHP() + 500 > p.getMaxHP()) {
      restored = p.getMaxHP() - p.getCurHP();
      p.setCurHP(p.getMaxHP());
    }
    else {
      p.setCurHP(p.getCurHP() + 500);
      restored = 500;
    }
    return restored + "HP restored!";
  }

}