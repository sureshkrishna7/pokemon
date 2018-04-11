package model.UsableItems;

import model.Items;
import model.Pokemon;

// needs a use method
public class FullEther extends Items implements UsableItem {

  public FullEther(char c) {
    super(c);
  }

  public FullEther(char c, int x, int y) {
    super(c, x, y);
  }

  @Override
  public String use(Pokemon p) {
    int restored = 0;
    
    if(p.isExhausted()) return "Cannot use!";
    
    if (p.getCurMP() + 10 > p.getMaxMP()) {
      restored = p.getMaxMP() - p.getCurMP();
      p.setCurMP(p.getMaxMP());
    } else {
      p.setCurMP(p.getCurHP() + 10);
      restored = 10;
    }
    return restored + "MP restored!";
  }
}
