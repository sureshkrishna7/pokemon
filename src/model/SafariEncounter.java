package model;
import java.util.Scanner;
import model.Pokemon;
import model.Trainer;

public class SafariEncounter {
  
  private static int choice;
  private static boolean invalid;
  private static String itemUseResult;
   
  public static void safariEncounter(Trainer trainer, Pokemon wild) {
    Scanner in = new Scanner(System.in);
    System.out.println("Encountered a " + wild.getName() + "!" + " (" + wild.getPokemonType() + " type)");
    
    while(true) {
      invalid = false;
      
      do {
        if(invalid)
          System.out.println("Invalid selection! Try again.");
        trainer.printSafariInventory();
        choice = in.nextInt();
        //System.out.println("item count: " + guy.safariInventoryByIndex(choice).getValue().size());
        if(trainer.safariInventoryByIndex(choice).getValue().size() == 0)
          invalid = true;
        else
          invalid = false;
      }while (invalid == true);
      
      String s = trainer.safariInventoryByIndex(choice).getKey();
      System.out.println("Chose " + s);
      itemUseResult = trainer.useSafariItem(s, wild);
      //System.out.println("item count: " + guy.safariInventoryByIndex(choice).getValue().size());
      System.out.println(itemUseResult);
      if(itemUseResult == "Caught!!") {
        trainer.printListOfPokemon();
        break;
      }
      if(wild.timeToRun()) {
        System.out.println("Ran away...");
        break;
      }
    }// end while(true)
    in.close();
  }
}
