package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.Pokemon;

public class PokemonAttackMethodTest {
  
  @Test
  public void battleTest_MattacksC() {
    Pokemon a = new Pokemon("Charmeleon", 'M', 'F', null);
    Pokemon b = new Pokemon("Sandslash", 'C', 'I', null);
    
    int before = b.getCurHP();
    // baseDmg of this attack is 100
    a.attack(0, b);
    // will have boost of 50-249, attack will be between 150 and 349
    int after = b.getCurHP();
    assertTrue(after < before - 150);
  }
  
  @Test
  public void battleTest_CattacksM() {
	  Pokemon a = new Pokemon("A", 'C', 'W', null);
	  Pokemon b = new Pokemon("B", 'M', 'W', null);
	  
	  int before = b.getCurHP();
	  // baseDmg of this attack is 100
	  a.attack(0, b);
	  // will have boost of 0-149, attack will be between 100 and 249
	  int after = b.getCurHP();
	  assertTrue(after < before - 100);
  }
  
  @Test
  public void battleTest_RattacksC() {
	  Pokemon a = new Pokemon("A", 'R', 'E', null);
	  Pokemon b = new Pokemon("B", 'C', 'W', null);
	  
	  //System.out.println("b.getHP()="+b.getHP());
	  int before = b.getCurHP();
	  // baseDmg of this attack is 100 * 3 = 300
	  a.attack(2, b);
	  // will have boost of 50-349, attack range: 300-649, or might be 0 (random)
	  //System.out.println("b.getHP()="+b.getHP());
	  int after = b.getCurHP();
	  //System.out.println("attack="+(before-after));
	  assertTrue(after == before || after < before - 300);
  }
  
  @Test
  public void battleTest_attackWithDeBufBurn() {
	  Pokemon a = new Pokemon("A", 'M', 'F', null);
	  Pokemon b = new Pokemon("B", 'M', 'W', null);
	  
	  //System.out.println("b.getHP()="+b.getHP());
	  int before = b.getCurHP();
	  // baseDmg of this attack is 200
	  a.attack(3, b);
	  // will have boost of 35 for this and next two turns
	  int after = b.getCurHP();
	  //System.out.println("attack="+(before-after));
	  assertTrue(after < before - 235);
  }
  
  @Test
  public void battleTest_attackWithDeBufRandom() {
	  Pokemon a = new Pokemon("A", 'M', 'F', null);
	  Pokemon b = new Pokemon("B", 'M', 'E', null);
	  
	  int before = b.getCurHP();
	  // baseDmg of this attack is 100. Might miss, random hit, or be critical
	  a.attack(1, b);
	  // will have boost *0, *1, or *2
	  int after = b.getCurHP();
	  assertTrue(after == before - 0 || after < before - 100 || after < before - 200);
  }
  
  @Test
  public void battleTest_attackWithDeBufExtra() {
	  Pokemon a = new Pokemon("A", 'M', 'E', null);
	  Pokemon b = new Pokemon("B", 'M', 'W', null);
	  
	  int before = b.getCurHP();
	  // baseDmg of this attack is 200. Will triple if opponent polar opposite
	  a.attack(3, b);
	  // will have boost *3
	  int after = b.getCurHP();
	  assertTrue(after < before - 600);
  }
  
  @Test
  public void battleTest_attackPolarOpposite() {
	  Pokemon a = new Pokemon("A", 'M', 'E', null);
	  Pokemon b = new Pokemon("B", 'M', 'W', null);
	  
	  int before = b.getCurHP();
	  a.attack(0, b);
	  int after = b.getCurHP();
	  // this test could be tightened
	  assertTrue(after < before);
  }
  
  @Test
  public void battleTest_checkMPWithdrawal() {
	  Pokemon a = new Pokemon("A", 'C', 'I', null);
	  Pokemon b = new Pokemon("B", 'C', 'W', null);
	  
	  a.attack(0, b);
	  int mp = a.getCurMP();
	  //System.out.println("a.getCurMP()="+a.getCurMP());
	  assertTrue(mp == 13);
  }
  
  @Test
  public void battleTest_checkBufDouble() {
	  Pokemon a = new Pokemon("A", 'C', 'W', null);
	  Pokemon b = new Pokemon("B", 'C', 'W', null);
	  
	  //System.out.println("b.getHP()="+b.getCurHP());
	  a.attack(1, b);
	  //System.out.println("b.getHP()="+b.getCurHP());
	  assertTrue(b.getCurHP() == b.getMaxHP());
  }
  // need tests for win/lose conditions
  
}
