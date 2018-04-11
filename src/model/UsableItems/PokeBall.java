package model.UsableItems;

import model.Items;
import model.Pokemon;

public class PokeBall extends Items implements UsableItem {

  public PokeBall(char c) {
    super(c);
  }
  
  public PokeBall(char c, int x, int y) {
    super(c, x, y);
  }

  @Override
  public String use(Pokemon p) {
    return "Caught!!";
  }

}