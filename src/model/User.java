package model;

import java.util.Vector;

public class User extends Items{

	private String filepath;			//TODO: need to set proper filepath for the user
	private String playerName;
	private Vector<Pokemon> allPokemon;
	private Vector<BackpackItems> inventory;
	private int money;
	
  //Pokemon player, level, pokemon captured, items collected, boosters, money currently having
	public User(String name, Vector<Pokemon>pokemon, Vector<BackpackItems> backpack, int currentMoney) {
		setType("user");
		setImage("filpath");
		
		this.playerName = name;
		this.allPokemon = pokemon;
		this.inventory = backpack;
		this.money = currentMoney;
	}
	
	public String getName() {
		return playerName;
	}
	public void setName(String newName) {
		playerName = newName;
	}
	public Vector<Pokemon> getPokemon() {
		return allPokemon;
	}
	public void addPokemon(Pokemon newPokemon) {			//adds pokemon 
		allPokemon.add(newPokemon);
	}
	public void removePokemon(String oldPokemon) {		//removes pokemon
		for (Pokemon current : allPokemon) {
			if (current.getName().equals(oldPokemon))
				allPokemon.remove(current);
		}
	}
	public Vector<BackpackItems> getInventory() {
		return this.inventory;
	}
	public void addItems(BackpackItems newItem) {			//adds to inventory
		inventory.add(newItem);
	}
	public void removeWholeItem(String oldItem) {		//removes from inventory (** SEARCH BY 'TYPE')
		for (BackpackItems current : inventory) {
			if (current.getName().equals(oldItem))
				inventory.remove(current);
		}
	}
	public void useOneItem(String useItem) {		//removes from inventory (** SEARCH BY 'TYPE')
		for (BackpackItems current : inventory) {
			if (current.getName().equals(useItem) && current.getQuantity() != 0)
				current.useItem();
		}
	}
	public void addMoney(int increase) {
		this.money += increase;
	}
	public void loseMoney(int decrease) {
		this.money -= decrease;
	}
	public int getMoney() {
		return this.money;
	}
}
