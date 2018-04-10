package model.UsableItems;
import model.Items;
import model.Pokemon;

// needs a use method
public class FullTonic extends Items implements UsableItem {

  public FullTonic(char c) {
    super(c);
  }
  
  public FullTonic(char item, int x, int y) {
    super(item, x, y);
  }

  @Override
  public String use(Pokemon p) {
    int restored = 0;
    
    if(p.isExhausted()) return "Cannot use!";
    
    if(p.getCurHP() + 500 > p.getMaxHP()) {
      restored = p.getMaxHP() - p.getCurHP();
      p.setCurHP(p.getMaxHP());
    }
    else {
      p.setCurHP(p.getCurHP() + 500);
      restored = 500;
    }
    return restored + "HP restored!";
  }

}