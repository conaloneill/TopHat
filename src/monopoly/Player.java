package monopoly;
/*
 *---Tophat---
 * Brian O'Leary - 13475468
 * Conal O'Neill - 13315756
 * Daniel Graham - 15319536
 * 
 * This class is used to create Player objects. Each player currently has its
 * own color, number and x,y position. These are used by our 'RenderPanel' class
 * to draw each player. 
 * 'Player' objects contain their own balances, owned properties, debt owed and to who, and their asset value
 * Players also keep track of information such as number of stations owned and number of get out of jail cards.
 * 
 * */
import java.awt.Color;
import java.util.ArrayList;

import monopoly.Tile;

public class Player {

	private Color colour;//Players color
	private int  balance;//Players balance
	private String name;//Name of player
	private int assetValue;//Players total assets
	private int inJailRollCount;//Number of rolls taken while in jail
	
	public int playerNumber;
	public int currentTile;//Tile player is currently on
	public int firstRoll;//Value of first dice roll
	public int xPosition;  //Stores current x position
	public int yPosition;  //Stores current y position
	public int stationsOwned;//Number of stations owned by player
	public int utilitiesOwned;//Number of utilities owned by player
	public int numberOfGOOJCards;//Number of 'GOOJ' cards owned by player
	
	public boolean inJail;//Has player been sent to jail
	

	Player(int playerNum, int balance, String pname){
		this.currentTile = 0;
		this.playerNumber = playerNum;
		this.balance = balance;
		this.utilitiesOwned = 0;
		this.stationsOwned = 0;
		this.numberOfGOOJCards = 0;
		this.name = pname;
		this.inJail = false;
		this.assetValue = 0;
		this.inJailRollCount = 0;
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

	public int getinJailRollCount() {
		return this.inJailRollCount;
	}
	public void incrementInJailRollCount() {
		this.inJailRollCount++;
	}
	public void resetInJailCount() {
		this.inJailRollCount = 0;
		
	}
	
	public int getAssetValue() {
		return assetValue;
	}
	
	//Used to calculate the total value of the Players assets, both cash and properties
	public void calculateAssetValue(ArrayList<Tile> Tiles, int playerNumber) {
		this.assetValue = getBalance();//Add players balance
		//Add players properties and buildings
		for (Tile o : Tiles) {
			if (o.getOwnerNumber() == playerNumber) {
				if (!o.checkMortgaged()) {
					this.assetValue += o.getPrice();
				}
				else {
					this.assetValue += o.getMortgageValue();
				}
				if (o.getBuildings() > 0) {
					this.assetValue += (o.getHousePrice()*o.getBuildings());
				}
			}
		}
	}


}
