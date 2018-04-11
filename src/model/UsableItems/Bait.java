package model.UsableItems;

import model.Items;
import model.Pokemon;

public class Bait extends Items implements UsableItem {

  public Bait(char c) {
    super(c);
  }
  
  public Bait(char c, int x, int y) {
    super(c, x, y);
  }

  @Override
  public String use(Pokemon p) {
    p.setStatus("baited");
    return "Took bait!";
  }

}
