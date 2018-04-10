package model.UsableItems;

import model.Items;
import model.Pokemon;

// needs a use method
public class Heal extends Items implements UsableItem {

  public Heal(char c) {
    super(c);
  }

  public Heal(char c, int x, int y) {
    super(c, x, y);
  }

  @Override
  public String use(Pokemon p) {

    if (p.getStatus() == "normal")
      return "No Effect...";

    p.setStatus("normal");
    return "Status restored.";

  }
}
