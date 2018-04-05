package model;

import javafx.scene.image.Image;

public class BackpackItems {
	private String name;
	private Image picture;
	private int quantity;
	
	public BackpackItems(String itemName, int number) {
		this.name = itemName;
		this.quantity = number;
	}
	
	public String getName() {
		return this.name;
	}
	public int getQuantity() {
		return this.quantity;
	}
	public void useItem() {
		if (this.quantity > 0) {
			this.quantity--;
		}
	}
	public void addItem(int number) {
		this.quantity += number;
	}
	
	public void setImage(String filepath) {			//Sets the image according to "filepath" (file:images/newImage.png)
  		this.picture = new Image(filepath, 20, 20, false, false);		//Sets image to 20X20 size (can change later)
  	}
	public Image getImage() {
  		return picture;
  	}
}
