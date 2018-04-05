package model;

public class Potion extends BackpackItems {

	private String filepath;		//TODO: add filepath to string to link the proper image
	public Potion(String itemName, int number) {
		super(itemName, number);
		setImage(filepath);
	}

}
