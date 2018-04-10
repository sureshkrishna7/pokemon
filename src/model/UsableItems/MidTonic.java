package model.UsableItems;
import model.Items;
import model.Pokemon;

// needs a use method
public class MidTonic extends Items implements UsableItem {

  public MidTonic(char c) {
    super(c);
  }
  public MidTonic(char item, int x, int y) {
    super(item, x, y);
  }

  @Override
  public String use(Pokemon p) {
    int restored = 0;
    
    if(p.isExhausted()) return "Cannot use!";
    
    if(p.getCurHP() + 300 > p.getMaxHP()) {
      restored = p.getMaxHP() - p.getCurHP();
      p.setCurHP(p.getMaxHP());
    }
    else {
      p.setCurHP(p.getCurHP() + 300);
      restored = 300;
    }
    return restored + "HP restored!";
  }

}