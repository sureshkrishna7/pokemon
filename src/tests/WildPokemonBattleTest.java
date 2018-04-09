package tests;

import java.util.Scanner;

import org.junit.Test;

import model.Attack;
import model.Pokemon;
import model.Trainer;

public class WildPokemonBattleTest {

	boolean invalid;
	boolean lost;
	int choice;
	int beforeHP;
	int afterHP;

	@Test
	public void test1() {

		Scanner in = new Scanner(System.in);
		Trainer guy = new Trainer("Andrew", new Pokemon("Charmeleon", 'M', 'F', null));
		Pokemon enemy = new Pokemon("Sandslash", 'C', 'I', null);
		System.out.println("Encountered a " + enemy.getName() + "!" + " (" + enemy.getPokemonType() + " type)");

		while (true) {
			invalid = false;
			
			// if your Pokemon is out of HP
			if (guy.getCurPokemon().isExhausted()) {
				lost = true;
				break;
			}

			// check if have MP to make move, if not print message, enemy makes move
			if (guy.getCurPokemon().canMove() ) {
				// this loop takes input for move from list.
				// if move is invalid, it starts over
				do {
					if(invalid) System.out.println("Invalid move! try again!");
					guy.getCurPokemon().printMoves();
					System.out.println("Your move (type a number, 0-3): ");
					choice = in.nextInt();
					System.out.println("You chose " + guy.getCurPokemon().getAttacks().get(choice).getName());
				} while (invalid = guy.getCurPokemon().invalidMove(choice));
				
				//System.out.println("You chose " + guy.getCurPokemon().getAttacks().get(choice).getName());
				beforeHP = enemy.getCurHP();
				guy.getCurPokemon().attack(choice, enemy);
				afterHP = enemy.getCurHP();
				if (beforeHP == afterHP) {
					System.out.println("Attack missed!");
				}else {
					System.out.println("Damage inflicted: " + (beforeHP - afterHP));
				}
				
			}else {
				System.out.println("Insufficient MP! Turn forfeited.");
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
				if(beforeHP == afterHP) {
				  System.out.println("Attack missed!");	
				}else {
					System.out.println("Damage inflicted: " + (beforeHP - afterHP));
				}
				guy.getCurPokemon().printData();
			}else {
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
