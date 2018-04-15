package tests;

import java.util.Scanner;

import org.junit.Test;

import model.Pokemon;
import model.Trainer;

public class SafariEncounterTest {
  
  int choice;
  boolean invalid;
  String itemUseResult;
  
  @Test 
  public void mainTest() {
    Scanner in = new Scanner(System.in);
    Trainer guy = new Trainer("Moose");
    Pokemon wild = new Pokemon("Vulpix", 16, 'M', 'F', null);
    System.out.println("Encountered a " + wild.getName() + "!" + " (" + wild.getPokemonType() + " type)");
    
    while(true) {
      invalid = false;
      
      do {
        if(invalid)
          System.out.println("Invalid selection! Try again.");
        guy.printSafariInventory();
        choice = in.nextInt();
        //System.out.println("item count: " + guy.safariInventoryByIndex(choice).getValue().size());
        if(guy.safariInventoryByIndex(choice).getValue().size() == 0)
          invalid = true;
        else
          invalid = false;
      }while (invalid == true);
      
      String s = guy.safariInventoryByIndex(choice).getKey();
      System.out.println("Chose " + s);
      itemUseResult = guy.getSafariInventory().get(s).get(0).use(guy, wild);
      System.out.println(itemUseResult);
      if(itemUseResult == "Caught!!") {
        guy.printListOfPokemon();
        break;
      }
      if(wild.timeToRun()) {
        System.out.println("Ran away...");
        break;
      }
    }// end while(true)
  }
}
