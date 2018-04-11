package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.Pokemon;

public class MapTest 
{
	
	  @Test
	  public void startGame() {
	    Pokemon a = new Pokemon("Charmeleon", 'M', 'F', null);
	    Pokemon b = new Pokemon("Sandslash", 'C', 'I', null);
	    
	    int before = b.getCurHP();
	    // baseDmg of this attack is 100
	    a.attack(0, b);
	    // will have boost of 50-249, attack will be between 150 and 349
	    int after = b.getCurHP();
	    assertTrue(after < before - 150);
	  }
}
