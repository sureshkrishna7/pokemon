package model;

import java.awt.Point;

public class UsableItems extends Items{

  private int hpBoost;
  private int mpBoost;
  private String status;
  private String name;
  
  public UsableItems(String name, int hpBoost, int mpBoost, String status) {
    super('U');
    this.name = name;
    this.hpBoost = hpBoost;
    this.mpBoost = mpBoost;
    this.status = status;
  }
  
  public UsableItems(String name, int hpBoost, int mpBoost, String status, int x, int y) {
    super('U', x, y);
    this.name = name;
    this.hpBoost = hpBoost;
    this.mpBoost = mpBoost;
    this.status = status;
  }
  
  public UsableItems(String name, int hpBoost, int mpBoost, String status, Point p) {
    super('U', p);
    this.name = name;
    this.hpBoost = hpBoost;
    this.mpBoost = mpBoost;
    this.status = status;
  }

  public String getPotionName() {
    
    return name;
  }

  
  
}
