package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.Pokemon;

public class BattleLogicTest {
  
  @Test
  public void battleTest_MattacksC() {
    Pokemon a = new Pokemon("Charmeleon", 'M', 'F', null);
    Pokemon b = new Pokemon("Sandslash", 'C', 'I', null);
    
    int before = b.getHP();
    // baseDmg of this attack is 100
    a.attack(0, b);
    // will have boost of 50-249, attack will be between 150 and 349
    int after = b.getHP();
    assertTrue(after < before - 150);
  }
  
  @Test
  public void battleTest_CattacksM() {
	  Pokemon a = new Pokemon("A", 'C', 'W', null);
	  Pokemon b = new Pokemon("B", 'M', 'W', null);
	  
	  int before = b.getHP();
	  // baseDmg of this attack is 100
	  a.attack(0, b);
	  // will have boost of 0-149, attack will be between 100 and 249
	  int after = b.getHP();
	  assertTrue(after < before - 100);
  }
  
  @Test
  public void battleTest_RattacksC() {
	  Pokemon a = new Pokemon("A", 'R', 'E', null);
	  Pokemon b = new Pokemon("B", 'C', 'W', null);
	  
	  System.out.println("b.getHP()="+b.getHP());
	  int before = b.getHP();
	  // baseDmg of this attack is 100 * 3 = 300
	  a.attack(2, b);
	  // will have boost of 50-349, attack range: 300-649
	  //System.out.println("b.getHP()="+b.getHP());
	  int after = b.getHP();
	  //System.out.println("attack="+(before-after));
	  assertTrue(after < before - 300);
  }
  
  @Test
  public void battleTest_attackWithBurn() {
	  Pokemon a = new Pokemon("A", 'M', 'F', null);
	  Pokemon b = new Pokemon("B", 'M', 'W', null);
	  
	  //System.out.println("b.getHP()="+b.getHP());
	  int before = b.getHP();
	  // baseDmg of this attack is 200
	  a.attack(3, b);
	  // will have boost of 35 for this and next two turns
	  int after = b.getHP();
	  //System.out.println("attack="+(before-after));
	  assertTrue(after < before - 235);
  }
  
  @Test
  public void battleTest_attackWithRandomBuf() {
	  Pokemon a = new Pokemon("A", 'M', 'F', null);
	  Pokemon b = new Pokemon("B", 'M', 'E', null);
	  
	  System.out.println("before="+b.getHP());
	  int before = b.getHP();
	  // baseDmg of this attack is 100. Might miss, random hit, or be critical
	  a.attack(1, b);
	  // will have boost -100, 0, or 100
	  int after = b.getHP();
	  System.out.println("after="+b.getHP());
	  System.out.println("attack="+(before-after));
	  assertTrue(after == before - 0 || after == before - 100 || after == before - 200);
  }
}
