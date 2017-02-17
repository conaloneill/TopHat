package monopoly;
/*
 *---Tophat---
 * Brian O'Leary - 134775468
 * Conal O'Neill - 13315756
 * Daniel Graham - 15319536
 * 
 * This class is used to create Player objects. Each player currently has its
 * own color, number and x,y position. These are used by our 'RenderPanel' class
 * to draw each player. 
 * In future Sprints, 'Player' objects will contain their own balances, owned properties etc.
 * 
 * */
import java.awt.Color;

public class Player {

	Player(int startingTile, int playerNum, int balance){
		currentTile = startingTile;
		playerNumber = playerNum;
		this.balance = balance;
		this.debt = 0;
	}
	
	public void setColour(Color color) {
		this.colour = color;
	}

	public void deposit(int value) {
		this.balance += value;
	}
	public void spend(int value) {
		this.balance -= value;
	}
	public int getBalance() {
		return this.balance;
	}
	public Color getColour() {
		return this.colour;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDebt(int amount) {
		this.debt = amount;
	}
	public int getDebt() {
		return this.debt;
	}
	public void setPlayerOwed(int playerNum) {
		this.playerNumberOwed = playerNum;
	}
	public int getPlayerOwed() {
		return this.playerNumberOwed;
	}
	
	private Color colour;
	private int  balance;
	private String name;
	private int debt;
	private int playerNumberOwed;
	
	public int playerNumber;
	public int currentTile;
	public int firstRoll;
	public int xPosition;  //Stores current x position
	public int yPosition;  //Stores current y position
}
