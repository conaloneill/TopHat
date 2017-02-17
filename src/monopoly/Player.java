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
import java.util.ArrayList;

import monopoly.Tile;

public class Player {
	
	private Color colour;
	private int  balance;
	private String name;
	private int assetValue = 0;
	
	public int playerNumber;
	public int currentTile;
	public int firstRoll;
	public int xPosition;  //Stores current x position
	public int yPosition;  //Stores current y position
	

	
	Player(int startingTile, int playerNum, int balance){
		currentTile = startingTile;
		playerNumber = playerNum;
		this.balance = balance;
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

	public void calculateAssetValue(ArrayList<Tile> Tiles) {
		assetValue = balance;
		for (Tile o : Tiles) {
			if (o.getOwnerNumber() == playerNumber-1) {
				if (!o.checkMortaged()) {
					assetValue += o.getPrice();
				}
				else {
					assetValue += o.getPrice()/2;
				}
			}
		}
	}
}
