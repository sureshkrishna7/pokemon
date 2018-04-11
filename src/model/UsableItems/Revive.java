package model.UsableItems;

import model.Items;
import model.Pokemon;

// needs a use method
public class Revive extends Items implements UsableItem {

  public Revive(char c) {
    super(c);
  }

  public Revive(char c, int x, int y) {
    super(c, x, y);
  }

  @Override
  public String use(Pokemon p) {
    if (!p.isExhausted())
      return "No Effect...";

    int hPrestored = p.getMaxHP();
    int mPrestored = p.getMaxMP() - p.getCurMP();

    p.setCurHP(p.getMaxHP());
    p.setCurMP(p.getMaxMP());
    return hPrestored + "HP ," + mPrestored + "MP restored!";

  }
}
