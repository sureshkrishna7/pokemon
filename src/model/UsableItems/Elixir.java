package model.UsableItems;

import model.Items;
import model.Pokemon;

// needs a use method
public class Elixir extends Items implements UsableItem {

  public Elixir(char c) {
    super(c);
  }

  public Elixir(char c, int x, int y) {
    super(c, x, y);
  }

  @Override
  public String use(Pokemon p) {
    if(p.isExhausted()) return "Cannot use!";
    
    int hPrestored = p.getMaxHP() - p.getCurHP();
    int mPrestored = p.getMaxMP() - p.getCurMP();
    
    p.setCurHP(p.getMaxHP());
    p.setCurMP(p.getMaxMP());
    
    return hPrestored + "HP ," + mPrestored + "MP restored!";
  }
}
