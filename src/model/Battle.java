package model;

import java.util.Scanner;
import model.Pokemon;
import model.Trainer;
import model.NPC.NPC;

public class Battle {

  private static boolean invalid;
  private static boolean lost;
  private static boolean ranAway;
  private static boolean changedMind;
  private static int choice;
  private static int beforeHP;
  private static int afterHP;
  private static String itemUseResult;

  public static boolean battle(Trainer trainer, NPC npc, Scanner in) {
    
    while (true) {
      invalid = false;
      changedMind = false;

      // lose condition: all Pokemon are exhausted
      if (trainer.allPokemonExhausted()) {
        trainer.getCurPokemon().printData();
        lost = true;
        break;
      }

      // if your curPokemon is out of HP
      if (trainer.getCurPokemon().isExhausted()) {
        System.out.println("Current Pokemon exhausted! Switch now.");
        switchPokemon(trainer, in);
      } else {
        trainerMove(trainer, npc.getCurPokemon(), in);
        // changedMind is set in any of the three sub-menus, which are entered in trainerMove
        if (changedMind) continue;
      }

      // print enemy stats after move has been made
      npc.getCurPokemon().printData();

      // if move killed enemy Pokemon, set boolean and break out of loop
      if (npc.allPokemonExhausted()) {
        lost = false;
        break;
      }

      //System.out.println("npc curPoke: " + npc.getCurPokemon().getName());
      // the enemy's turn
      npcMove(trainer, npc);
      //System.out.println("npc curPoke: " + npc.getCurPokemon().getName());
      
      // print trainer's curPokemon stats after move has been made
      trainer.getCurPokemon().printData();

    } // end while(true)

    if (lost) {
      System.out.println("You lose...");
      System.out.println("\"" + npc.getDialogue(3) + "\" - " + npc.getName());
      return false;
    } else {
      System.out.println("You win!!");
      System.out.println("\"" + npc.getDialogue(4) + "\" - " + npc.getName());
      // what happens when you beat a NPC? A reward perhaps?
      // items and a Pokemon
      
      return true;
    }
  }


  public static boolean battle(Trainer trainer, Pokemon wildPoke, Scanner in) {
    System.out.println("Encountered a " + wildPoke.getName() + "!" + " (" + wildPoke.getPokemonType() + " type)");

    while (true) {
      invalid = false;
      changedMind = false;
      ranAway = false;

      // lose condition: all Pokemon are exhausted
      if (trainer.allPokemonExhausted()) {
        trainer.getCurPokemon().printData();
        lost = true;
        break;
      }

      // if your curPokemon is out of HP
      if (trainer.getCurPokemon().isExhausted()) {
        System.out.println("Current Pokemon exhausted! Switch now.");
        switchPokemon(trainer, in);
      } else {
        trainerMove(trainer, wildPoke, in);
        // changedMind is set in any of the three sub-menus, which are entered in trainerMove
        if (changedMind) continue;
      }

      if(ranAway) {
        System.out.println("Running away");
        lost = true;
        break;
      }
      // print enemy stats after move has been made
      wildPoke.printData();

      // if move killed enemy Pokemon, set boolean and break out of loop
      if (wildPoke.isExhausted()) {
        lost = false;
        break;
      }

      // the enemy's turn
      wildPokeMove(trainer, wildPoke);
      
      // print trainer's curPokemon stats after move has been made
      trainer.getCurPokemon().printData();

    } // end while(true)

    if (lost) {
      System.out.println("You lose...");
      return false;
    } else {
      System.out.println("You win!!");
      return true;
    }
  }
  
  private static void npcMove(Trainer trainer, NPC npc) {
    String result = null;
    
    if(npc.getCurPokemon().isDisabled()) {
      System.out.println(npc.getCurPokemon().getName() + "is " + npc.getCurPokemon().getStatus());
    } else {
      /*
       *  the conditional returns a String of the Pokemon switched to if a switch was made, 
       *  meaning it's time to return. (turn consumed)
       */
      if ((result = npc.switchPokemon()) != null) {
        System.out.println(npc.getName() + " chose " + result + ".");
        //System.out.println("npc curPoke: " + npc.getCurPokemon().getName());
        return;
      }
      /*
       *  the conditional returns a String of the item name if an it was used, 
       *  meaning it's time to return. (turn consumed)
       */
      if ((result = npc.useItem()) != null) {
        System.out.println(npc.getName() + " used " + result + " on " + npc.getCurPokemon().getName() + ".");
        return;
      }
      choice = npc.getAttack(trainer);
      System.out.println(npc.getName() + " has " + npc.getCurPokemon().getName() + " use " + npc.getCurPokemon().getAttacks().get(choice).getName());
      beforeHP = trainer.getCurPokemon().getCurHP();
      npc.getCurPokemon().attack(choice, trainer.getCurPokemon());
      afterHP = trainer.getCurPokemon().getCurHP();
      if (beforeHP == afterHP) {
        System.out.println("Attack missed!");
        System.out.println("\"" + npc.getDialogue(2) + "\" - " + npc.getName());
      } else {
        System.out.println("Damage inflicted: " + (beforeHP - afterHP));
        System.out.println("\"" + npc.getDialogue(1) + "\" - " + npc.getName());
      }
    }
    
    
  }

  private static void wildPokeMove(Trainer trainer, Pokemon enemy) {
    if (enemy.isDisabled()) {
      System.out.println(enemy.getName() + "is " + enemy.getStatus());
    } else if (!enemy.canMove()) {
      System.out.println("Enemy has insufficient MP! Turn forfeited.");
    } else {
      do {
        choice = enemy.randomMove();
      } while (enemy.invalidMove(choice));
      System.out.println(enemy.getName() + " chose " + enemy.getAttacks().get(choice).getName());
      beforeHP = trainer.getCurPokemon().getCurHP();
      enemy.attack(choice, trainer.getCurPokemon());
      afterHP = trainer.getCurPokemon().getCurHP();
      if (beforeHP == afterHP) {
        System.out.println("Attack missed!");
      } else {
        System.out.println("Damage inflicted: " + (beforeHP - afterHP));
      }
    }
  }

  private static void trainerMove(Trainer trainer, Pokemon enemy, Scanner in) {

    if (trainer.getCurPokemon().isDisabled()) {
      System.out.println(trainer.getCurPokemon().getName() + "can't move!");
    } else {
      // this loop takes input for option to attack, change Pokemon, or use item.
      // if selection is invalid, it starts over.

      do {
        if (invalid) {
          System.out.println("Invalid selection!");
          invalid = false;
        }
        System.out.println("Select option:");
        System.out.printf("1: Attack\n2. Use Item\n3. Switch Pokemon\n4. Run away\n");
        choice = in.nextInt();
        if (choice < 1 || choice > 4) {
          invalid = true;
        }
      } while (invalid);

      // chose to attack
      if (choice == 1) {
        attack(trainer, enemy, in);
      }
      // chose to use item
      else if (choice == 2) {
        useItem(trainer, in);
      }
      // chose to switch Pokemon
      else if (choice == 3) {
        switchPokemon(trainer, in);
      }
      // chose to run away
      else {
        ranAway = true;
      }
    } // end if curPokemon not disabled

  }// end trainerMove()

  /*
   * useItem(Trainer, Scanner) -- this method is run when the user selects 2. Use
   * Item from the main menu. If 0 is selected, we return to the main menu.
   */
  private static void useItem(Trainer trainer, Scanner in) {
    System.out.println("Select an item: (or 0 to return)");
    trainer.printInventory();
    choice = in.nextInt();
    if (choice == 0) {
      changedMind = true;
      return;
    }
    
    
    String s = trainer.inventoryByIndex(choice).getKey();
    if(trainer.getInventory().get(s).size() != 0) {
      System.out.println("Chose " + s + "!");
      /*
       * Here we get the item from the inventory via a String, the ArrayList of
       * whatever item is chosen is returned. The use() method for that particular
       * item is called on the Trainer's current Pokemon. Then the item is removed
       * from the ArrayList.
       */
      itemUseResult = trainer.getInventory().get(s).get(0).use(trainer.getCurPokemon());
      trainer.getInventory().get(s).remove(0);
      System.out.println(itemUseResult);
    }else {
      useItem(trainer, in);
    }
    
  }// end useItem()

  /*
   * attack(Trainer, Pokemon, Scanner) -- this method is run when the user selects
   * 1. Attack from the menu of selections. Within the method, the user chooses an
   * attack, or 0 which indicates that the user wants to return to the main menu.
   * If an attack is chosen, the effects are afflicted on the opponent and
   * statements are printed demonstrating what happened and how stats have
   * changed.
   */
  private static void attack(Trainer trainer, Pokemon enemy, Scanner in) {
    if (trainer.getCurPokemon().canMove()) {
      do {
        if (invalid) {
          System.out.println("Invalid move! Try again.");
          invalid = false;
        }
        trainer.getCurPokemon().printMoves();
        System.out.println("Your move (type a number 1-4), 0 to return:");
        choice = in.nextInt();
        if (choice == 0) {
          changedMind = true;
          return;
        }
        System.out.println("You chose " + trainer.getCurPokemon().getAttacks().get(choice - 1).getName());
      } while (invalid = trainer.getCurPokemon().invalidMove(choice - 1));

      beforeHP = enemy.getCurHP();
      trainer.getCurPokemon().attack(choice - 1, enemy);
      afterHP = enemy.getCurHP();
      if (beforeHP == afterHP) {
        System.out.println("Attack missed!");
      } else {
        System.out.println("Damage inflicted: " + (beforeHP - afterHP));
      }
    }
  }// end attack()

  /*
   * switchPokemon(Trainer) -- this method allows the trainer to switch his
   * current Pokemon to another Pokemon in his list.
   */
  private static void switchPokemon(Trainer trainer, Scanner in) {
    int i = 1;
    boolean invalid = false;

    for (Pokemon p : trainer.getPokeList()) {
      System.out.print(i++);
      p.printData();
    }

    /*
     * this loop prompts the player to select a different Pokemon, has input
     * validation for input range as well as if the Pokemon the user is trying to
     * selected is exhausted.
     */
    do {
      if (invalid) {
        System.out.println("Invalid choice!");
        invalid = false;
      }
      System.out.println("Select a Pokemon by number (or 0 to return):");
      choice = in.nextInt();
      if (choice == 0) {
        changedMind = true;
        return;
      }
      if (choice < 1 || choice > trainer.getPokeList().size() || trainer.getPokeList().get(choice - 1).isExhausted()) {
        invalid = true;
      }
    } while (invalid);

    trainer.setBattlePokemon(trainer.getPokeList().get(choice - 1));
    System.out.println("Chose " + trainer.getCurPokemon().getName() + "!");

  }// end switchPokemon()

}// end battle()
