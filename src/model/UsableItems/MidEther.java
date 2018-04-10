package model.UsableItems;

import model.Items;
import model.Pokemon;

// needs a use method
public class MidEther extends Items implements UsableItem {

  public MidEther(char c) {
    super(c);
  }

  public MidEther(char c, int x, int y) {
    super(c, x, y);
  }

  @Override
  public String use(Pokemon p) {
    int restored = 0;
    
    if(p.isExhausted()) return "Cannot use!";
    
    if (p.getCurMP() + 8 > p.getMaxMP()) {
      restored = p.getMaxMP() - p.getCurMP();
      p.setCurMP(p.getMaxMP());
    } else {
      p.setCurMP(p.getCurHP() + 8);
      restored = 8;
    }
    return restored + "MP restored!";
  }
}
