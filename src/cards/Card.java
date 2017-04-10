package cards;

/*
 *---Tophat---
 * Brian O'Leary - 13475468
 * Conal O'Neill - 13315756
 * Daniel Graham - 15319536
 * 
 * This class is used to create 'Card' objects to represent chance and community chest cards. 
 * 
 * Cards were designed with the intention of making is easy to add more cards to the game by 
 * making card types as general as possible.
 * What information is held by a card depends heavily on what type of card it is.
 *
 * */

public class Card {
	
	//Types of card
	public static final int TYPE_FINE = 0;
	public static final int TYPE_REWARD = 1; 
	public static final int TYPE_GOOJ = 2; 
	public static final int TYPE_GOTO = 3;
	public static final int TYPE_BUILDINGFINE = 4;
	public static final int TYPE_MONEYFROMEACHPLAYER = 5;
	public static final int TYPE_FINEORCHANCE = 6;
	public static final int TYPE_MOVEXSPACES = 7;
	
	private String message;//Message to be printed when card is drawn.
	private int amount;//Used to hold fine or reward amount
	private int type;//Type of card as listed above
	private int destination;//When a player is told to move to a certain tile
	private int spacesToMove;//When a player is told to move a certain numner of spaces
	
	//Used for 'building fine' cards
	private int housecost;
	private int hotelcost;
	
	public boolean passGo;//Is player allowed to collect 200 when passing go
	public int cardNumber;
	
	
	public Card(int n){
		this.cardNumber = n;
		this.amount = 0;
	}
	public void setMessage(String s){
		this.message = s;
	}
	public String getMessage(){
		return this.message;
	}
	
	public void setAmount(int x){
		this.amount = x;
	}
	public int getAmount(){
		return this.amount;
	}
	
	public void setType(int x){
		this.type = x;
	}
	public int getType(){
		return this.type;
	}
	
	public void setDestination(int x){
		this.destination = x;
	}
	public int getDestination(){
		return this.destination;
	}
	
	public void setSpacesToMove(int x){
		this.spacesToMove = x;
	}
	public int getSpacesToMove(){
		return this.spacesToMove;
	}
	
	public void setBuildingCosts(int house, int hotel){
		this.housecost = house;
		this.hotelcost = hotel;
	}
	public int getHouseCost(){
		return this.housecost;
	}
	public int getHotelCost(){
		return this.hotelcost;
	}
}
