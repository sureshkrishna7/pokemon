package tests;

import java.util.Scanner;

import org.junit.Test;

import model.Attack;
import model.Pokemon;
import model.Trainer;

public class WildPokemonBattleTest {
  @Test
  public void test1() {
	Scanner in = new Scanner(System.in);
	Trainer guy = new Trainer("Andrew", new Pokemon("Charmeleon", 'M', 'F', null));
	Pokemon enemy = new Pokemon("Cubone", 'C', 'E', null);
	System.out.println("Encountered a " + enemy.getName() + "!");
	
	while(!guy.getCurPokemon().battleOver(enemy)) {
		System.out.println("Your move (type a number, 0-3): ");
		int choice = in.nextInt();
		System.out.println("You chose " + guy.getCurPokemon().getAttacks().get(choice).getName());
		guy.getCurPokemon().attack(choice, enemy);
		enemy.printData();
		int i = enemy.randomMove();
		System.out.println(enemy.getName() + " chose " + enemy.getAttacks().get(i).getName());
		enemy.attack(i, guy.getCurPokemon());
		guy.getCurPokemon().printData();
	}
	if(guy.getCurPokemon().getCurHP() <= 0 || guy.getCurPokemon().getCurMP() <= 0) {
		System.out.println("You lose!");
	}else {
		System.out.println("You win!!");
	}
  }
}
