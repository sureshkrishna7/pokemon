package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.Pokemon;
import model.UsableItems.Heal;
import model.UsableItems.Revive;
import model.UsableItems.Tonic;

public class ItemUsageTest {
  @Test
  public void useTonicWhenHPLow() {
    Pokemon p = new Pokemon("A", 'R', 'I', null);
    p.setCurHP(p.getMaxHP() - 400);

    int before = p.getCurHP();
    Tonic t = new Tonic('U');
    t.use(p);
    int after = p.getCurHP();
    assertTrue(after == before + 150);
  }
  
  @Test
  public void useTonicWhenWithinRangeOfMax() {
    Pokemon p = new Pokemon("A", 'R', 'I', null);
    p.setCurHP(p.getMaxHP() - 50);

    Tonic t = new Tonic('U');
    t.use(p);
    int after = p.getCurHP();
    assertTrue(after == p.getMaxHP());
  }
  
  @Test
  public void useHealWhenStatusNotNormal() {
    Pokemon p = new Pokemon("A", 'R', 'I', null);
    p.setStatus("freeze");
    
    Heal h = new Heal('U');
    h.use(p);
    assertTrue(p.getStatus() == "normal");
  }
  
  @Test
  public void useReviveWhenPokemonNotExhausted() {
    Pokemon p = new Pokemon("A", 'R', 'I', null);
    
    int hPbefore = p.getCurHP();
    int mPbefore = p.getCurMP();
    Revive r = new Revive('U');
    r.use(p);
    int hPafter = p.getCurHP();
    int mPafter = p.getCurMP();
    
    assertTrue(hPbefore == hPafter);
    assertTrue(mPbefore == mPafter);
  }
  
  @Test
  public void useReviveWhenPokemonIsExhausted() {
    Pokemon p = new Pokemon("A", 'R', 'I', null);
    p.setCurHP(0);
    p.setCurMP(2);
    
    Revive r = new Revive('U');
    r.use(p);
    assertTrue(p.getCurHP() == p.getMaxHP());
    assertTrue(p.getCurMP() == p.getMaxMP());
  }
  
  @Test
  public void useTonicWhenPokemonIsExhausted() {
    Pokemon p = new Pokemon("A", 'R', 'I', null);
    p.setCurHP(0);
    
    Tonic t = new Tonic('U');
    t.use(p);
    assertTrue(p.getCurHP() == 0);
  }
}
