package propertyImages;
/*
 *---Tophat---
 * Brian O'Leary - 134775468
 * Conal O'Neill - 13315756
 * Daniel Graham - 15319536
 * 
 * This Class is used to handle all images drawn onto our monopoly board (RenderPanel).
 * It retrieves images from propertyImages.drawable and assigns them to their correct Tile 
 * in the ArrayList 'Tiles' making references to our instance of our main class 'GameScreen'
 * called 'screen'. These images are drawn in the RenderPanel class.
 * 
 * As this class is cycling through every tile and assigning info to those tiles, we 
 * have added the rent, type, price and name of each tile inside the same loop.
 * This was done to improve readability(everything is assigned together) and 
 * code performance(only 1 loop through all tiles to assign information). 
 * 
 * */
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;



import monopoly.GameScreen;
import monopoly.Tile;


public class PropertyImages {


	//Property Info card variables
	private Image bondStreet = null;
	private Image bowStreet;
	private Image coventryStreet;
	private Image electricCo;
	private Image eustonRoad;
	private Image fleetStreet;
	private Image fenchurchStStation;
	private Image kingsCross;
	private Image leicesterSq;
	private Image liverpoolStStation;
	private Image marlboroughSt;
	private Image maryboyleStation;
	private Image mayfair;
	private Image oldKentRoad;
	private Image northumberlandAv;
	private Image pallMall;
	private Image oxfordStreet;
	private Image parkLane;
	private Image pentonvilleRd;
	private Image piccadilly;
	private Image strand;
	private Image regentStreet;
	private Image trafalgarSq;
	private Image angelIslington;
	private Image vineStreet;
	private Image whitechapelRd;
	private Image waterWorks;
	private Image whitehall;

	//Tile Image variables
	private Image bondStreetTile = null;
	private Image bowStreetTile;
	private Image coventryStreetTile;
	private Image electricCoTile;
	private Image eustonRoadTile;
	private Image fenchurchStStationTile;
	private Image fleetStreetTile;
	private Image kingsCrossTile;
	private Image leicesterSqTile;
	private Image liverpoolStStationTile;
	private Image marlboroughStTile;
	private Image maryleboneStationTile;
	private Image mayfairTile;
	private Image northumberlandAvTile;
	private Image oldKentRoadTile;
	private Image oxfordStreetTile;
	private Image pallMallTile;
	private Image parkLaneTile;
	private Image pentonvilleRdTile;
	private Image piccadillyTile;
	private Image regentStreetTile;
	private Image strandTile;
	private Image angelIslingtonTile;
	private Image trafalgarSqTile;
	private Image vineStreetTile;
	private Image waterWorksTile;
	private Image whitechapelRdTile;
	private Image whitehallTile;

	// Non-property Image variables
	public Image monopolyLogo;
	private Image go;
	private Image freepark;
	private Image gotojail;
	private Image injail;
	private Image chance;
	private Image comchest;
	private Image incometax;
	private Image luxurytax;
	private Image comchestleft;
	private Image comchestright;
	private Image chancetop;
	private Image chanceright;
	
	
	//Property Types
	public static final int TYPE_GO = 0;
	public static final int TYPE_PROPERTY = 1; 
	public static final int TYPE_STATION = 2; 
	public static final int TYPE_UTILITY = 3; 
	public static final int TYPE_COMMUNITY = 4; 
	public static final int TYPE_CHANCE = 5; 
	public static final int TYPE_JAIL = 6; 
	public static final int TYPE_PARKING = 7; 
	public static final int TYPE_GOTO_JAIL = 8; 
	public static final int TYPE_TAX = 9;

	
	//Property rent constants
	private static final int[][] SITE_RENTS = { {2,10,30,90,160,250},
			{4,20,60,180,320,450},{25,50,100,200,200,200},{6,30,90,270,400,550},{6,30,90,270,400,550},
			{8,40,100,300,450,600},{10,50,150,450,625,750},{4,10,0,0,0,0},{10,50,150,450,625,750},
			{12,60,180,500,700,900},{25,50,100,200,200,200},{14,70,200,550,750,950},
			{14,70,200,550,750,950},{16,80,220,600,800,1000},{18,90,250,700,875,1050},
			{18,90,250,700,875,1050},{20,100,300,750,925,1100},{25,50,100,200,200,200},
			{22,110,330,800,975,1150},{22,110,330,800,975,1150},{4,10,0,0,0,0},
			{22,120,360,850,1025,1200},{26,130,390,900,1100,1275},
			{26,130,390,900,1100,1275},{28,150,450,1000,1200,1400},{25,50,100,200,200,200},
			{35,175,500,1100,1300,1500}, {50,200,600,1400,1700,2000}};
	
	
	//Property price constants
	private static final int[] SITE_PRICES = 	{60,60,200,100,100,120,140,
												150,140,160,200,180,180,200,
												220,220,240,200,260,260,150,
												280,300,300,320,200,350,400};
	
	
	//Property house prices(hotels same price)
	private static final int[] HOUSE_PRICES = 	{30, 30, 50, 50, 50,
												100,100,100,100,100,100,
												150,150,150,150,150,150,
												150,150,160,200,200};
	
	//Property mortgage constants
	private static final int[] SITE_MORTGAGE_VALUE = {50,50,100,50,50,60,70,
			                                          75,70,80,100,90,90,100,110,
			                                          110,120,100,150,150,75,150,
		                                           	  200,200,200,100,175,200};
	
	//Property full names
	private static final String[] SITE_NAMES = {
			"Old Kent Rd","Whitechapel Rd","King's Cross Station","The Angel Islington",
			"Euston Rd","Pentonville Rd","Pall Mall","Electric Company","Whitehall",
			"Northumberland Avenue","Marylebone Station","Bow St","Marlborough St",
			"Vine St","Strand","Fleet St","Trafalgar Sq","Fenchurch Station",
			"Leicester Sq","Coventry St","Water Works","Piccadilly","Regent St",
			"Oxford St","Bond St","Liverpool Station","Park Lane","Mayfair"};
	
	//Property short names
	private static final String[] SITE_SHORT_NAMES = {
			"kent","whitechapel","king","angel",
			"euston","pentonville","mall","electric","whitehall",
			"northumberland","marylebone","bow","marlborough",
			"vine","strand","fleet","trafalgar","fenchurch",
			"leicester","coventry","water","piccadilly","regent",
			"oxford","bond","liverpool","park","mayfair"};
	
	
	
	
	//Function to retrieve images from their path in the drawable folder
	public Image getImage(String path){ 
		Image temp = null;
		try {
			URL imageURL = PropertyImages.class.getResource(path);
			temp = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return temp;
	}
	
	

	public void assignTileImages() {
		//Allow reference to our game screen
		GameScreen screen = GameScreen.screen;

		//Get images 
		if(bondStreetTile == null){ 
			bondStreetTile = getImage("drawable/Bond Street Tile.png");
			bowStreetTile = getImage("drawable/Bow Street Tile.png");
			coventryStreetTile = getImage("drawable/Coventry Street Tile.png");
			electricCoTile = getImage("drawable/ElectricCompany Tile.png");
			eustonRoadTile = getImage("drawable/Euston Road Tile.png");
			fenchurchStStationTile = getImage("drawable/Fenchurch St Station Tile.png");
			fleetStreetTile = getImage("drawable/Fleet Street Tile.png");
			kingsCrossTile = getImage("drawable/Kings Cross Station Tile.png");
			leicesterSqTile = getImage("drawable/Leicester Square Tile.png");
			liverpoolStStationTile = getImage("drawable/Liverpool St Station Tile.png");
			marlboroughStTile = getImage("drawable/Marlborough Street Tile.png");
			maryleboneStationTile = getImage("drawable/Marylebone Station Tile.png");
			mayfairTile = getImage("drawable/Mayfair Tile.png");
			northumberlandAvTile = getImage("drawable/Northumberland Avenue Tile.png");
			oldKentRoadTile = getImage("drawable/Old Kent Road Tile.png");
			oxfordStreetTile = getImage("drawable/Oxford Street Tile.png");
			pallMallTile = getImage("drawable/Pall Mall Tile.png");
			parkLaneTile = getImage("drawable/Park Lane Tile.png");
			pentonvilleRdTile = getImage("drawable/Pentonville Road Tile.png");
			piccadillyTile = getImage("drawable/Piccadilly Tile.png");
			regentStreetTile = getImage("drawable/Regent Street Tile.png");
			strandTile = getImage("drawable/Strand Tile.png");
			angelIslingtonTile = getImage("drawable/The Angel Islington Tile.png");
			trafalgarSqTile = getImage("drawable/Trafalgar Square Tile.png");
			vineStreetTile = getImage("drawable/Vine Street Tile.png");
			waterWorksTile = getImage("drawable/WaterWorks Tile.png");
			whitechapelRdTile = getImage("drawable/WhiteChapel Road Tile.png");
			whitehallTile = getImage("drawable/Whitehall Tile.png");




			//Non- Property images
			monopolyLogo = getImage("drawable/monologo.png");
			go = getImage("drawable/go.jpg");
			freepark = getImage("drawable/freepark.png");
			gotojail = getImage("drawable/gotojail.png");
			injail = getImage("drawable/injail.png");
			chance = getImage("drawable/chance.png");
			comchest = getImage("drawable/comchest.png");
			incometax = getImage("drawable/incometax.png");
			luxurytax = getImage("drawable/luxurytax.png");
			comchestleft = getImage("drawable/comchestleft.png");
			comchestright = getImage("drawable/comchestright.png");
			chancetop = getImage("drawable/chancetop.png");
			chanceright = getImage("drawable/chanceright.png");





			//Info Card images
			bondStreet = getImage("drawable/Bond Street.png");
			bowStreet = getImage("drawable/Bow Street.png");
			coventryStreet = getImage("drawable/Coventry Street.png");
			electricCo = getImage("drawable/Electric Co.png");
			eustonRoad = getImage("drawable/Euston Road.png");
			fenchurchStStation = getImage("drawable/Fenchurch Street Station.png");
			fleetStreet = getImage("drawable/Fleet Street.png");
			kingsCross = getImage("drawable/King's Cross Station.png");
			leicesterSq = getImage("drawable/Leicester Square.png");
			liverpoolStStation = getImage("drawable/Liverpool Street Station.png");
			marlboroughSt = getImage("drawable/Marlborough Street.png");
			maryboyleStation = getImage("drawable/Maryboyle Station.png");
			mayfair = getImage("drawable/Mayfair.png");
			northumberlandAv = getImage("drawable/Northumberland Avenue.png");
			oldKentRoad = getImage("drawable/Old Kent Road.png");
			oxfordStreet = getImage("drawable/Oxford Street.png");
			pallMall = getImage("drawable/Pall Mall.png");
			parkLane = getImage("drawable/Park Lane.png");
			pentonvilleRd = getImage("drawable/Pentonville Road.png");
			piccadilly = getImage("drawable/Piccadilly.png");
			regentStreet = getImage("drawable/Regent Street.png");
			strand = getImage("drawable/Strand.png");
			angelIslington = getImage("drawable/The Angel Islington.png");
			trafalgarSq = getImage("drawable/Trafalgar Square.png");
			vineStreet = getImage("drawable/Vine Street.png");
			waterWorks = getImage("drawable/Water Works.png");
			whitechapelRd = getImage("drawable/Whitechapel Road.png");
			whitehall = getImage("drawable/Whitehall.png");
		}


		//Assign Images to their correct Tile
		for (Tile o : screen.Tiles) {
			switch (o.getTileNum()) {
			case 0:
				o.setImage(go);
				o.setType(TYPE_GO);
				o.setName("Go");
				break;
			case 1:
				o.setImage(oldKentRoadTile);
				o.setInfoImage(oldKentRoad);
				o.setName(SITE_NAMES[0]);
				o.setShortname(SITE_SHORT_NAMES[0]);
				o.setPrice(SITE_PRICES[0]);
				o.setMortgageValue(SITE_MORTGAGE_VALUE[0]);
				o.setHousePrice(HOUSE_PRICES[0]);
				o.setType(TYPE_PROPERTY);
				o.setPossibleRents(SITE_RENTS[0]);
				break;
			case 2:
				o.setImage(comchest);
				o.setType(TYPE_COMMUNITY);
				o.setName("Community Chest");
				o.setShortname("chest");
				break;
			case 3:
				o.setImage(whitechapelRdTile);
				o.setInfoImage(whitechapelRd);
				o.setName(SITE_NAMES[1]);
				o.setShortname(SITE_SHORT_NAMES[1]);
				o.setPrice(SITE_PRICES[1]);
				o.setMortgageValue(SITE_MORTGAGE_VALUE[1]);
				o.setHousePrice(HOUSE_PRICES[1]);
				o.setType(TYPE_PROPERTY);
				o.setPossibleRents(SITE_RENTS[1]);
				break;
			case 4:
				o.setImage(incometax);
				o.setType(TYPE_TAX);
				o.setPrice(SITE_PRICES[2]); // same price as stations(kings)
				o.setName("Income Tax");
				o.setShortname("income");
				break;
			case 5:
				o.setImage(kingsCrossTile);
				o.setInfoImage(kingsCross);
				o.setName(SITE_NAMES[2]);
				o.setShortname(SITE_SHORT_NAMES[2]);
				o.setPrice(SITE_PRICES[2]);
				o.setMortgageValue(SITE_MORTGAGE_VALUE[2]);
				o.setType(TYPE_STATION);
				o.setPossibleRents(SITE_RENTS[2]);
				break;
			case 6:
				o.setImage(angelIslingtonTile);
				o.setInfoImage(angelIslington);
				o.setName(SITE_NAMES[3]);
				o.setShortname(SITE_SHORT_NAMES[3]);
				o.setType(TYPE_PROPERTY);
				o.setPrice(SITE_PRICES[3]);
				o.setMortgageValue(SITE_MORTGAGE_VALUE[3]);
				o.setHousePrice(HOUSE_PRICES[2]);
				o.setPossibleRents(SITE_RENTS[3]);
				break;
			case 7:
				o.setImage(chance);
				o.setType(TYPE_CHANCE);
				o.setName("Chance");
				break;
			case 8:
				o.setImage(eustonRoadTile);
				o.setInfoImage(eustonRoad);
				o.setName(SITE_NAMES[4]);
				o.setShortname(SITE_SHORT_NAMES[4]);
				o.setType(TYPE_PROPERTY);
				o.setPrice(SITE_PRICES[4]);
				o.setMortgageValue(SITE_MORTGAGE_VALUE[4]);
				o.setHousePrice(HOUSE_PRICES[3]);
				o.setPossibleRents(SITE_RENTS[4]);
				break;
			case 9:
				o.setImage(pentonvilleRdTile);
				o.setInfoImage(pentonvilleRd);
				o.setName(SITE_NAMES[5]);
				o.setShortname(SITE_SHORT_NAMES[5]);
				o.setType(TYPE_PROPERTY);
				o.setPrice(SITE_PRICES[5]);
				o.setMortgageValue(SITE_MORTGAGE_VALUE[5]);
				o.setHousePrice(HOUSE_PRICES[4]);
				o.setPossibleRents(SITE_RENTS[5]);
				break;
			case 10:
				o.setImage(injail);
				o.setType(TYPE_JAIL);
				o.setName("Jail");
				break;
			case 11:
				o.setImage(pallMallTile);
				o.setInfoImage(pallMall);
				o.setName(SITE_NAMES[6]);
				o.setShortname(SITE_SHORT_NAMES[6]);
				o.setType(TYPE_PROPERTY);
				o.setPrice(SITE_PRICES[6]);
				o.setMortgageValue(SITE_MORTGAGE_VALUE[6]);
				o.setHousePrice(HOUSE_PRICES[5]);
				o.setPossibleRents(SITE_RENTS[6]);
				break;
			case 12:
				o.setImage(electricCoTile);
				o.setInfoImage(electricCo);
				o.setName(SITE_NAMES[7]);
				o.setShortname(SITE_SHORT_NAMES[7]);
				o.setType(TYPE_UTILITY);
				o.setPrice(SITE_PRICES[7]);
				o.setMortgageValue(SITE_MORTGAGE_VALUE[7]);
				o.setPossibleRents(SITE_RENTS[7]);
				break;
			case 13:
				o.setImage(whitehallTile);
				o.setInfoImage(whitehall);
				o.setName(SITE_NAMES[8]);
				o.setShortname(SITE_SHORT_NAMES[8]);
				o.setType(TYPE_PROPERTY);
				o.setPrice(SITE_PRICES[8]);
				o.setMortgageValue(SITE_MORTGAGE_VALUE[8]);
				o.setHousePrice(HOUSE_PRICES[6]);
				o.setPossibleRents(SITE_RENTS[8]);
				break;
			case 14:
				o.setImage(northumberlandAvTile);
				o.setInfoImage(northumberlandAv);
				o.setName(SITE_NAMES[9]);
				o.setShortname(SITE_SHORT_NAMES[9]);
				o.setType(TYPE_PROPERTY);
				o.setPrice(SITE_PRICES[9]);
				o.setMortgageValue(SITE_MORTGAGE_VALUE[9]);
				o.setHousePrice(HOUSE_PRICES[7]);
				o.setPossibleRents(SITE_RENTS[9]);
				break;
			case 15:
				o.setImage(maryleboneStationTile);
				o.setInfoImage(maryboyleStation);
				o.setName(SITE_NAMES[10]);
				o.setShortname(SITE_SHORT_NAMES[10]);
				o.setType(TYPE_STATION);
				o.setPrice(SITE_PRICES[10]);
				o.setMortgageValue(SITE_MORTGAGE_VALUE[10]);
				o.setPossibleRents(SITE_RENTS[10]);
				break;
			case 16:
				o.setImage(bowStreetTile);
				o.setInfoImage(bowStreet);
				o.setName(SITE_NAMES[11]);
				o.setShortname(SITE_SHORT_NAMES[11]);
				o.setType(TYPE_PROPERTY);
				o.setPrice(SITE_PRICES[11]);
				o.setMortgageValue(SITE_MORTGAGE_VALUE[11]);
				o.setHousePrice(HOUSE_PRICES[8]);
				o.setPossibleRents(SITE_RENTS[11]);
				break;
			case 17:
				o.setImage(comchestleft);
				o.setType(TYPE_COMMUNITY);
				o.setName("Community Chest");
				o.setShortname("chest");
				break;
			case 18:
				o.setImage(marlboroughStTile);
				o.setInfoImage(marlboroughSt);
				o.setName(SITE_NAMES[12]);
				o.setShortname(SITE_SHORT_NAMES[12]);
				o.setType(TYPE_PROPERTY);
				o.setPrice(SITE_PRICES[12]);
				o.setMortgageValue(SITE_MORTGAGE_VALUE[12]);
				o.setHousePrice(HOUSE_PRICES[9]);
				o.setPossibleRents(SITE_RENTS[12]);
				break;
			case 19:
				o.setImage(vineStreetTile);
				o.setInfoImage(vineStreet);
				o.setName(SITE_NAMES[13]);
				o.setShortname(SITE_SHORT_NAMES[13]);
				o.setType(TYPE_PROPERTY);
				o.setPrice(SITE_PRICES[13]);
				o.setMortgageValue(SITE_MORTGAGE_VALUE[13]);
				o.setHousePrice(HOUSE_PRICES[10]);
				o.setPossibleRents(SITE_RENTS[13]);
				break;
			case 20:
				o.setImage(freepark);
				o.setType(TYPE_PARKING);
				o.setName("Free Parking");
				break;
			case 21:
				o.setImage(strandTile);
				o.setInfoImage(strand);
				o.setName(SITE_NAMES[14]);
				o.setShortname(SITE_SHORT_NAMES[14]);
				o.setType(TYPE_PROPERTY);
				o.setPrice(SITE_PRICES[14]);
				o.setMortgageValue(SITE_MORTGAGE_VALUE[14]);
				o.setHousePrice(HOUSE_PRICES[11]);
				o.setPossibleRents(SITE_RENTS[14]);
				break;
			case 22:
				o.setImage(chancetop);
				o.setType(TYPE_CHANCE);
				o.setName("Chance");
				break;
			case 23:
				o.setImage(fleetStreetTile);
				o.setInfoImage(fleetStreet);
				o.setName(SITE_NAMES[15]);
				o.setShortname(SITE_SHORT_NAMES[15]);
				o.setType(TYPE_PROPERTY);
				o.setPrice(SITE_PRICES[15]);
				o.setMortgageValue(SITE_MORTGAGE_VALUE[15]);
				o.setHousePrice(HOUSE_PRICES[12]);
				o.setPossibleRents(SITE_RENTS[15]);
				break;
			case 24:
				o.setImage(trafalgarSqTile);
				o.setInfoImage(trafalgarSq);
				o.setName(SITE_NAMES[16]);
				o.setShortname(SITE_SHORT_NAMES[16]);
				o.setType(TYPE_PROPERTY);
				o.setPrice(SITE_PRICES[16]);
				o.setMortgageValue(SITE_MORTGAGE_VALUE[16]);
				o.setHousePrice(HOUSE_PRICES[13]);
				o.setPossibleRents(SITE_RENTS[16]);
				break;
			case 25:
				o.setImage(fenchurchStStationTile);
				o.setInfoImage(fenchurchStStation);
				o.setName(SITE_NAMES[17]);
				o.setShortname(SITE_SHORT_NAMES[17]);
				o.setType(TYPE_STATION);
				o.setPrice(SITE_PRICES[17]);
				o.setMortgageValue(SITE_MORTGAGE_VALUE[17]);
				o.setPossibleRents(SITE_RENTS[17]);
				break;
			case 26:
				o.setImage(leicesterSqTile);
				o.setInfoImage(leicesterSq);
				o.setName(SITE_NAMES[18]);
				o.setShortname(SITE_SHORT_NAMES[18]);
				o.setType(TYPE_PROPERTY);
				o.setPrice(SITE_PRICES[18]);
				o.setMortgageValue(SITE_MORTGAGE_VALUE[18]);
				o.setHousePrice(HOUSE_PRICES[14]);
				o.setPossibleRents(SITE_RENTS[18]);
				break;
			case 27:
				o.setImage(coventryStreetTile);
				o.setInfoImage(coventryStreet);
				o.setName(SITE_NAMES[19]);
				o.setShortname(SITE_SHORT_NAMES[19]);
				o.setType(TYPE_PROPERTY);
				o.setPrice(SITE_PRICES[19]);
				o.setMortgageValue(SITE_MORTGAGE_VALUE[19]);
				o.setHousePrice(HOUSE_PRICES[15]);
				o.setPossibleRents(SITE_RENTS[19]);
				break;
			case 28:
				o.setImage(waterWorksTile);
				o.setInfoImage(waterWorks);
				o.setName(SITE_NAMES[20]);
				o.setShortname(SITE_SHORT_NAMES[20]);
				o.setType(TYPE_UTILITY);
				o.setPrice(SITE_PRICES[20]);
				o.setMortgageValue(SITE_MORTGAGE_VALUE[20]);
				o.setPossibleRents(SITE_RENTS[20]);
				break;
			case 29:
				o.setImage(piccadillyTile);
				o.setInfoImage(piccadilly);
				o.setName(SITE_NAMES[21]);
				o.setShortname(SITE_SHORT_NAMES[21]);
				o.setType(TYPE_PROPERTY);
				o.setPrice(SITE_PRICES[21]);
				o.setMortgageValue(SITE_MORTGAGE_VALUE[21]);
				o.setHousePrice(HOUSE_PRICES[16]);
				o.setPossibleRents(SITE_RENTS[21]);
				break;
			case 30:
				o.setImage(gotojail);
				o.setType(TYPE_GOTO_JAIL);
				o.setName("Go to Jail!");
				break;
			case 31:
				o.setImage(regentStreetTile);
				o.setInfoImage(regentStreet);
				o.setName(SITE_NAMES[22]);
				o.setShortname(SITE_SHORT_NAMES[22]);
				o.setType(TYPE_PROPERTY);
				o.setPrice(SITE_PRICES[22]);
				o.setMortgageValue(SITE_MORTGAGE_VALUE[22]);
				o.setHousePrice(HOUSE_PRICES[17]);
				o.setPossibleRents(SITE_RENTS[22]);
				break;
			case 32:
				o.setImage(oxfordStreetTile);
				o.setInfoImage(oxfordStreet);
				o.setName(SITE_NAMES[23]);
				o.setShortname(SITE_SHORT_NAMES[23]);
				o.setType(TYPE_PROPERTY);
				o.setPrice(SITE_PRICES[23]);
				o.setMortgageValue(SITE_MORTGAGE_VALUE[23]);
				o.setHousePrice(HOUSE_PRICES[18]);
				o.setPossibleRents(SITE_RENTS[23]);
				break;
			case 33:
				o.setImage(comchestright);
				o.setType(TYPE_COMMUNITY);
				o.setName("Community Chest");
				o.setShortname("chest");
				break;
			case 34:
				o.setImage(bondStreetTile);
				o.setInfoImage(bondStreet);
				o.setName(SITE_NAMES[24]);
				o.setShortname(SITE_SHORT_NAMES[24]);
				o.setType(TYPE_PROPERTY);
				o.setPrice(SITE_PRICES[24]);
				o.setMortgageValue(SITE_MORTGAGE_VALUE[24]);
				o.setHousePrice(HOUSE_PRICES[19]);
				o.setPossibleRents(SITE_RENTS[24]);
				break;
			case 35: 
				o.setImage(liverpoolStStationTile);
				o.setInfoImage(liverpoolStStation);
				o.setName(SITE_NAMES[25]);
				o.setShortname(SITE_SHORT_NAMES[25]);
				o.setType(TYPE_STATION);
				o.setPrice(SITE_PRICES[25]);
				o.setMortgageValue(SITE_MORTGAGE_VALUE[25]);
				o.setPossibleRents(SITE_RENTS[25]);
				break;
			case 36:
				o.setImage(chanceright);
				o.setType(TYPE_CHANCE);
				o.setName("Chance");
				break;
			case 37:
				o.setImage(parkLaneTile);
				o.setInfoImage(parkLane);
				o.setName(SITE_NAMES[26]);
				o.setShortname(SITE_SHORT_NAMES[26]);
				o.setType(TYPE_PROPERTY);
				o.setPrice(SITE_PRICES[26]);
				o.setMortgageValue(SITE_MORTGAGE_VALUE[26]);
				o.setHousePrice(HOUSE_PRICES[20]);
				o.setPossibleRents(SITE_RENTS[26]);
				break;
			case 38:
				o.setImage(luxurytax);
				o.setType(TYPE_TAX);
				o.setPrice(SITE_PRICES[3]); //same price as angel Islington
				o.setName("Luxury Tax");
				break;
			case 39:
				o.setImage(mayfairTile);
				o.setInfoImage(mayfair);
				o.setName(SITE_NAMES[27]);
				o.setShortname(SITE_SHORT_NAMES[27]);
				o.setType(TYPE_PROPERTY);
				o.setPrice(SITE_PRICES[27]);
				o.setMortgageValue(SITE_MORTGAGE_VALUE[27]);
				o.setHousePrice(HOUSE_PRICES[21]);
				o.setPossibleRents(SITE_RENTS[27]);
				break;
			default:
				break;
			}
		}
	}
}