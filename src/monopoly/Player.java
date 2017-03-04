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
 * 'Player' objects contain their own balances, owned properties, debt owed and to who, and their asset value
 * 
 * */
import java.awt.Color;
import java.util.ArrayList;

import monopoly.Tile;

public class Player {

	private Color colour;
	private int  balance;
	private String name;
	private int assetValue = 0;
	private int debt;
	private int playerNumberOwed;

	public int playerNumber;
	public int currentTile;
	public int firstRoll;
	public int xPosition;  //Stores current x position
	public int yPosition;  //Stores current y position
	
	
	public int stationsOwned;
	public int utilitiesOwned;



	Player(int playerNum, int balance){
		this.currentTile = 0;
		this.playerNumber = playerNum;
		this.balance = balance;
		this.debt = 0;
		this.utilitiesOwned = 0;
		this.stationsOwned = 0;
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

	public int getAssetValue() {
		return assetValue;
	}
	
	//used to calculate the total value of the Players assets, both cash and properties
	public void calculateAssetValue(ArrayList<Tile> Tiles) {
		assetValue = balance;
		for (Tile o : Tiles) {
			if (o.getOwnerNumber() == playerNumber-1) {
				assetValue += o.getPrice();
			}
		}
	}
	
	//getter and setter for debt (used in paying rent)
	public void setDebt(int amount) {
		this.debt = amount;
	}
	public int getDebt() {
		return this.debt;
	}
	//getter and setter for who the player owes rent to
	public void setPlayerOwed(int playerNum) {
		this.playerNumberOwed = playerNum;
	}
	public int getPlayerOwed() {
		return this.playerNumberOwed;
	}

}
