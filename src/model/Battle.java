package model;

import java.util.Scanner;
import model.Pokemon;
import model.Trainer;

public class Battle {

  private static boolean invalid;
  private static boolean lost;
  private static int choice;
  private static int beforeHP;
  private static int afterHP;
  private static String itemUseResult;

  public static boolean battle(Trainer trainer, NPC enemy) {
    return true;
  }

  public static boolean battle(Trainer trainer, Pokemon enemy) {
    Scanner in = new Scanner(System.in);
    System.out.println("Encountered a " + enemy.getName() + "!" + " (" + enemy.getPokemonType() + " type)");

    while (true) {
      invalid = false;

      // lose condition: all Pokemon are exhausted
      if (allPokemonExhausted(trainer)) {
        trainer.getCurPokemon().printData();
        lost = true;
        break;
      }

      // if your curPokemon is out of HP
      if (trainer.getCurPokemon().isExhausted()) {
        System.out.println("Current Pokemon exhausted! Switch now.");
        switchPokemon(trainer, in);
      }

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
          if (trainer.getCurPokemon().canMove()) {
            do {
              if (invalid) {
                System.out.println("Invalid move! Try again.");
                invalid = false;
              }
              trainer.getCurPokemon().printMoves();
              System.out.println("Your move (type a number 0-3):");
              choice = in.nextInt();
              System.out.println("You chose " + trainer.getCurPokemon().getAttacks().get(choice).getName());
            } while (invalid = trainer.getCurPokemon().invalidMove(choice));
            beforeHP = enemy.getCurHP();
            trainer.getCurPokemon().attack(choice, enemy);
            afterHP = enemy.getCurHP();
            if (beforeHP == afterHP) {
              System.out.println("Attack missed!");
            } else {
              System.out.println("Damage inflicted: " + (beforeHP - afterHP));
            }
          }
        }
        // chose to use item
        else if (choice == 2) {
          System.out.println("Select an item: ");
          trainer.printInventory();
          choice = in.nextInt();
          String s = trainer.inventoryByIndex(choice).getKey();
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
        }
        // chose to switch Pokemon
        else if (choice == 3) {
          switchPokemon(trainer, in);
        }
        // chose to run away
        else {
          lost = true;
          break;
        }
      } // end if curPokemon not disabled

      // print enemy status after move has been made
      enemy.printData();

      // if move killed enemy Pokemon, set boolean and break out of loop
      if (enemy.isExhausted()) {
        lost = false;
        break;
      }

      // the enemy's turn
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
        trainer.getCurPokemon().printData();
      }
    } // end while(true)

    in.close();

    if (lost) {
      System.out.println("You lose...");
      return false;
    } else {
      System.out.println("You win!!");
      return true;
    }
  }

  /*
   * switchPokemon(Trainer) -- this method allows the trainer to switch his
   * current Pokemon to another Pokemon in his list.
   */
  private static void switchPokemon(Trainer trainer, Scanner in) {
    int i = 1;
    int choice;
    boolean invalid = false;
    boolean cancel = false;
    //Scanner in = new Scanner(System.in);

    for (Pokemon p : trainer.getPokeList()) {
      System.out.print(i);
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
      if(choice == 0) {
        cancel = true;
        break;
      }
      if (choice < 1 || choice > trainer.getPokeList().size() - 1 || trainer.getPokeList().get(choice).isExhausted()) {
        invalid = true;
      }
    } while (invalid);
    if(!cancel) {
      trainer.setBattlePokemon(trainer.getPokeList().get(choice-1));
      System.out.println("Chose " + trainer.getCurPokemon().getName() + "!");
    }
  }// end switchPokemon()

  /*
   * allPokemonExhausted(Trainer) -- this method iterates through all of the
   * Pokemon in the Trainer's pokeList. It returns false as soon as it finds one
   * Pokemon that is not exhausted (pokemon.isExhausted() == false), otherwise
   * returns true if the list is traversed and this condition is not met.
   */
  private static boolean allPokemonExhausted(Trainer trainer) {
    for (Pokemon p : trainer.getPokeList()) {
      if (p.isExhausted() == false)
        return false;
    }
    return true;
  }

}// end battle()
