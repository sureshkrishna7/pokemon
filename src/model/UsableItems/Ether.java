package model.UsableItems;

import model.Items;
import model.Pokemon;

// needs a use method
public class Ether extends Items implements UsableItem {

  public Ether(char c) {
    super(c);
  }

  public Ether(char c, int x, int y) {
    super(c, x, y);
  }

  @Override
  public String use(Pokemon p) {
    int restored = 0;
    
    if(p.isExhausted()) return "Cannot use!";
    
    if (p.getCurMP() + 4 > p.getMaxMP()) {
      restored = p.getMaxMP() - p.getCurMP();
      p.setCurMP(p.getMaxMP());
    } else {
      p.setCurMP(p.getCurHP() + 4);
      restored = 4;
    }
    return restored + "MP restored!";
  }
}
