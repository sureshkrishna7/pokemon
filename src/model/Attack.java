package model;

public class Attack {
	private String name;
	private int totalPP;
	private int currentPP;
	private double damageMultiplier;
	
	public Attack(String moveName, int movePP, double multiplier) {
		this.name = moveName;
		this.currentPP = movePP;					// this tells you how many more times you can use the move before it is exhausted
		this.totalPP = movePP;
		this.damageMultiplier = multiplier; //if this is high, PP should be low
	}
	public int getCurrentPP() {
		return this.currentPP;
	}
	public int getTotalPP() {
		return this.totalPP;
	}
	public void resetPP() {		//replenishes PP to maximum (effect of a potion, pokemon being healed or level up)
		this.currentPP = this.totalPP;
	}
	public void decreasePP() {
		this.currentPP--;
	}
	public void increaseTotalPP() {
		this.totalPP++;
	}
	public String getAttackName() {
		return this.name;
	}
	public double getMultiplier() {
		return this.damageMultiplier;
	}
}
