package tests;

import java.util.Scanner;
import org.junit.Test;

import model.Battle;
import model.Pokemon;
import model.Trainer;
import model.NPC.Joffrey;

public class WildPokemonBattleTest {
  
  boolean invalid;
  boolean lost;
  boolean usingItem;
  int choice;
  int beforeHP;
  int afterHP;
  String itemUseResult;
  
  //@Test
  public void battleWithWildPoke() {
    Trainer guy = new Trainer("Andrew");
    guy.getCurPokemon().setCurHP(100);
    Pokemon enemy = new Pokemon("Sandslash", 1, 'C', 'I', null);
    Battle.battle(guy, enemy);
  }
  
  @Test
  public void battleWithNPC() {
    Trainer guy = new Trainer("Andrew");
    Battle.battle(guy, new Joffrey("Joffrey", true)); 
  }
  
  //@Test
  public void standAloneSystemTest() {

    Scanner in = new Scanner(System.in);
    Trainer guy = new Trainer("Andrew");
    Pokemon enemy = new Pokemon("Sandslash", 3, 'C', 'I', null);
    System.out.println("Encountered a " + enemy.getName() + "!" + " (" + enemy.getPokemonType() + " type)");

    while (true) {
      invalid = false;
      usingItem = false;

      // if your Pokemon is out of HP
      if (guy.getCurPokemon().isExhausted()) {
        lost = true;
        break;
      }

      // check if have MP to make move, if not print message, enemy makes move
      if (guy.getCurPokemon().canMove()) {
        // this loop takes input for move from list.
        // if move is invalid, it starts over

        do {
          if (invalid)
            System.out.println("Invalid move! Try again.");
          guy.getCurPokemon().printMoves();
          System.out.println("Your move (type a number, 0-3, or 4 for item list):");
          choice = in.nextInt();
          if (choice == 4) {
            usingItem = true;
            break;
          }
          System.out.println("You chose " + guy.getCurPokemon().getAttacks().get(choice).getName());
        } while (invalid = guy.getCurPokemon().invalidMove(choice));

        if (usingItem) {
          System.out.println("Select an item: ");
          guy.printInventory();
          choice = in.nextInt();
          String s = guy.inventoryByIndex(choice).getKey();
          System.out.println("Chose " + s + "!");
          /*
           * Here we get the item from the inventory via a String, the ArrayList of
           * whatever item is chosen is returned. The use() method for that particular
           * item is called on the Trainer's current Pokemon. Then the item is removed
           * from the ArrayList.
           */
          itemUseResult = guy.getInventory().get(s).get(0).use(guy.getCurPokemon());
          guy.getInventory().get(s).remove(0);
          System.out.println(itemUseResult);
        } else {
          beforeHP = enemy.getCurHP();
          guy.getCurPokemon().attack(choice, enemy);
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
        beforeHP = guy.getCurPokemon().getCurHP();
        enemy.attack(choice, guy.getCurPokemon());
        afterHP = guy.getCurPokemon().getCurHP();
        if (beforeHP == afterHP) {
          System.out.println("Attack missed!");
        } else {
          System.out.println("Damage inflicted: " + (beforeHP - afterHP));
        }
        guy.getCurPokemon().printData();
      } else {
        System.out.println("Enemy has insufficient MP! Turn forfeited.");
      }

    } // end while(true)

    if (lost) {
      System.out.println("You lose...");
    } else {
      System.out.println("You win!!");
    }
  }
  
}
