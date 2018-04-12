package model;

import java.util.Scanner;
import org.junit.Test;
import model.Pokemon;
import model.Trainer;

public class Battle {

  private static boolean invalid;
  private static boolean lost;
  private static boolean usingItem;
  private static int choice;
  private static int beforeHP;
  private static int afterHP;
  private static String itemUseResult;

  public static boolean battle(Trainer trainer, Pokemon enemy) {
    Scanner in = new Scanner(System.in);
    System.out.println("Encountered a " + enemy.getName() + "!" + " (" + enemy.getPokemonType() + " type)");

    while (true) {
      invalid = false;
      usingItem = false;

      // if your Pokemon is out of HP
      if (trainer.getCurPokemon().isExhausted()) {
        lost = true;
        break;
      }

      // check if have MP to make move, if not print message, enemy makes move
      if (trainer.getCurPokemon().canMove()) {
        // this loop takes input for move from list.
        // if move is invalid, it starts over

        do {
          if (invalid)
            System.out.println("Invalid move! Try again.");
          trainer.getCurPokemon().printMoves();
          System.out.println("Your move (type a number, 0-3, or 4 for item list):");
          choice = in.nextInt();
          if (choice == 4) {
            usingItem = true;
            break;
          }
          System.out.println("You chose " + trainer.getCurPokemon().getAttacks().get(choice).getName());
        } while (invalid = trainer.getCurPokemon().invalidMove(choice));

        if (usingItem) {
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
        } else {
          beforeHP = enemy.getCurHP();
          trainer.getCurPokemon().attack(choice, enemy);
          afterHP = enemy.getCurHP();
          if (beforeHP == afterHP) {
            System.out.println("Attack missed!");
          } else {
            System.out.println("Damage inflicted: " + (beforeHP - afterHP));
          }
        }

      } else {
        if (enemy.getStatus() == "freeze") {
          System.out.println("Frozen! Turn forfeited.");
        } else {
          System.out.println("Insufficient MP! Turn forfeited.");
        }

      }

      // if move killed enemy Pokemon, set boolean and break out of loop
      if (enemy.isExhausted()) {
        lost = false;
        break;
      }

      // print enemy status after move has been made
      enemy.printData();

      // check if enemy has MP to make move, if not print message, loop starts over
      if (enemy.canMove()) {
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
      } else {
        System.out.println("Enemy has insufficient MP! Turn forfeited.");
      }

    } // end while(true)

    if (lost) {
      System.out.println("You lose...");
      return true;
    } else {
      System.out.println("You win!!");
      return false;
    }
  }
}// end battle()
