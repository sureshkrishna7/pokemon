package model;

public abstract class Items {

  private String type;
  private String img;
  private boolean walkable;
  private int x;
  private int y;

  public Items(String type, String img, boolean walkable) {
	 this.type = type;
	 this.img = img;
	 this.walkable = walkable;
  }

  public int[] getPosition() {
	 int[] pos = new int[2];
	 pos[0] = x;
	 pos[1] = y;
	 
	 return pos;
  }

  public String getImage() {
	 return img;
  }
}
