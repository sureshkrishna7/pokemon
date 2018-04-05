package model;

public class Pokeball extends BackpackItems {

	private String filepath;
	public Pokeball(String itemName, int number) {
		super(itemName, number);
		setImage(filepath);
	}

}
