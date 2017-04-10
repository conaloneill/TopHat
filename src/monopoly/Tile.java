package monopoly;

/*
 *---Tophat---
 * Brian O'Leary - 13475468
 * Conal O'Neill - 13315756
 * Daniel Graham - 15319536
 * 
 * This class is used to create Tile objects which we use for spaces on the monopoly board.
 * These Tiles can be of any type(property, chance, community chest etc.)
 * 40 Tiles are used in our game. These are kept in the ArrayList 'Tiles' in GameScreen.
 * Each Tile has its own Image which is drawn relative to its own x and y position.
 * Each Tile also has all its possible rent values, its mortgage value, the price of houses,
 * what player owns it, if its mortgaged or not, and the type of tile it is.  
 *
 * */
import java.awt.Image;

public class Tile {
	
	private String propertyName;
	private String propertyShortname;
	private String colour;
	
	private Image image;//Image drawn in tile space around board
	private Image infoImage;//Image drawn when player mouses over tile
	
	private int type;//Type of tile as defined in 'PropertyInfo'
	private int[] rents;//Array of all possible rents
	private int ownedByPlayer;
	private int number;//Tile number
	private int price;
	private int mortgageValue;
	private int housePrice;
	private int tax;//If tax tile, holds tax amount
	private int buildings;//Number of buildings on tile
	
	private boolean mortgaged;
	private boolean allColourOwned;//Is this tiles color group all owned by the same player
	
	public int x;	//stores tile x position
	public int y;	//stores tile y position 

	public Tile(int tileNumber,int posx, int posy){
		this.number = tileNumber;
		this.price = 0;
		this.x=posx;
		this.y=posy;
		this.ownedByPlayer = -1; // -1 denotes no owner
		this.buildings = 0;
		this.mortgaged = false;
	}

	public void setImage(Image img) {
		this.image = img;
	}
	public Image getImage() {
		return this.image;
	}
	public void setInfoImage(Image img) {
		this.infoImage = img;
	}
	public Image getInfoImage() {
		return this.infoImage;
	}
	
	public int getTileNum() {
		return this.number;
	}
	
	public String rentToString(){
		String s = "" + rents[buildings];
		return s;
	}
	
	public int getType() {
		return this.type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public void setOwnerNumber(int number) {
		this.ownedByPlayer = number;
	}
	public int getOwnerNumber() {
		return this.ownedByPlayer;
	}
	
	public String getName() {
		return this.propertyName;
	}
	public void setName(String name) {
		this.propertyName = name;
	}
	public String getShortName() {
		return this.propertyShortname;
	}
	public void setShortname(String name) {
		this.propertyShortname = name;
	}
	
	public void setPrice(int value) {
		this.price = value;
	}
	public int getPrice() {
		return this.price;
	}
	public void setMortgageValue(int value) {
		this.mortgageValue = value;
	}
	public int getMortgageValue() {
		return this.mortgageValue;
	}
	
	public int getRent() {
		//If color group is all owned by same player, chack number of buildings
		if (buildings == 0 && isAllColourOwned()) {
			return (this.rents[buildings] * 2);
		}
		return this.rents[buildings];
	}
	
	public int getStationRent(int num) {
		return this.rents[num - 1];
	}
	
	public int getUtilityRentMultiplier(int num) {
		return this.rents[num - 1];
	}
	
	public void setPossibleRents(int[] a) {
		this.rents = a;
	}

	public boolean checkMortgaged() {
		return mortgaged;
	}

	public void setMortgaged(boolean mortgaged) {
		this.mortgaged = mortgaged;
	}
	
	//Get number of building
	public int getBuildings() {
		return buildings;
	}	// add x number of buildings
	public void addBuildings(int num) {
		this.buildings += num;
	}	//remove x number of buildings
	public void removeBuildings(int num) {
		this.buildings -= num;
	}	
	//remove all buildings (for bankruptcy/quick sale of all without knowing how many) 
	public int removeAllBuildings() {
		int gain = ((buildings * housePrice) / 2);
		this.buildings = 0;
		
		return gain;
	}

	//Set house and hotel price
	public int getHousePrice() {
		return housePrice;
	}
	public void setHousePrice(int housePrice) {
		this.housePrice = housePrice;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public boolean isAllColourOwned() {
		return allColourOwned;
	}

	public void setAllColourOwned(boolean allColourOwned) {
		this.allColourOwned = allColourOwned;
	}

	public void setTaxAmount(int tax) {
		this.tax = tax;
	}
	public int getTaxAmount() {
		return this.tax;
	}
}
