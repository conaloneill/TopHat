package monopoly;
/*
 *---Tophat---
 * Brian O'Leary - 134775468
 * Conal O'Neill - 13315756
 * Daniel Graham - 15319536
 * 
 * This class is used to create Tile objects which we use for spaces on the monopoly board.
 * These Tiles can be of any type(property, chance, community chest etc.)
 * 40 Tiles are used in our game. These are kept in the ArrayList 'Tiles' in GameScreen.
 * Each Tile has its own Image which is drawn relative to its own x and y position.
 * Each Tile also has all its possible rent values (fixed at first for Sprint 2), 
 * what player owns it, and the type of tile it is.  
 *
 * */
import java.awt.Image;

public class Tile {
	
	private String propertyName;
	private String propertyShortname;
	
	private int type;
	private int[] rents;
	private int ownedByPlayer;
	private Image image;
	private Image infoImage;
	private int number;
	private boolean mortaged = false;
	private int price;
	private int housePrice;
	
	private int buildings = 0;
	
	
	public int x;	//stores tile x position
	public int y;	//stores tile y position 

	
	public Tile(int numberl,int posx, int posy){
		number = numberl;
		this.price = 0;
		x=posx;
		y=posy;
		ownedByPlayer = -1; // -1 denotes no owner
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
	public void setRent() {
		//this.rent =;
	}
	public int getRent() {
		return this.rents[buildings];
	}
	
	public void setPossibleRents(int[] a) {
		this.rents = a;
	}

	public boolean checkMortaged() {
		return mortaged;
	}

	public void setMortaged(boolean mortaged) {
		this.mortaged = mortaged;
	}

	
	//get num of building
	public int getBuildings() {
		return buildings;
	}	// add x num of buildings
	public void addBuildings(int num) {
		this.buildings += num;
	}	//remove x num of buildings
	public void removeBuildings(int num) {
		this.buildings -= num;
	}	//remove all buildings (for bankruptcy/quick sale of all without knowing how many) 
	public void removeAllBuilding() {
		this.buildings = 0;
	}

	//set house and hotel price
	public int getHousePrice() {
		return housePrice;
	}
	public void setHousePrice(int housePrice) {
		this.housePrice = housePrice;
	}
}
