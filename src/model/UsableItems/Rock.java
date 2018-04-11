package model.UsableItems;

import model.Items;
import model.Pokemon;

public class Rock extends Items implements UsableItem {

  public Rock(char c) {
    super(c);
  }
  
  public Rock(char c, int x, int y) {
    super(c, x, y);
  }

  @Override
  public String use(Pokemon p) {
    p.setCurHP((int)(p.getCurHP() - (p.getMaxHP() * 0.2)));
    return "Hit!";
  }

}