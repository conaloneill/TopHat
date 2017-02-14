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
			whitechapelRdTile = getImage("drawable/Whitechapel Road Tile.png");
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
				break;
			case 1:
				o.setImage(oldKentRoadTile);
				o.setInfoImage(oldKentRoad);
				break;
			case 2:
				o.setImage(comchest);
				break;
			case 3:
				o.setImage(whitechapelRdTile);
				o.setInfoImage(whitechapelRd);
				break;
			case 4:
				o.setImage(incometax);
				break;
			case 5:
				o.setImage(kingsCrossTile);
				o.setInfoImage(kingsCross);
				break;
			case 6:
				o.setImage(angelIslingtonTile);
				o.setInfoImage(angelIslington);
				break;
			case 7:
				o.setImage(chance);
				break;
			case 8:
				o.setImage(eustonRoadTile);
				o.setInfoImage(eustonRoad);
				break;
			case 9:
				o.setImage(pentonvilleRdTile);
				o.setInfoImage(pentonvilleRd);
				break;
			case 10:
				o.setImage(injail);
				break;
			case 11:
				o.setImage(pallMallTile);
				o.setInfoImage(pallMall);
				break;
			case 12:
				o.setImage(electricCoTile);
				o.setInfoImage(electricCo);
				break;
			case 13:
				o.setImage(whitehallTile);
				o.setInfoImage(whitehall);
				break;
			case 14:
				o.setImage(northumberlandAvTile);
				o.setInfoImage(northumberlandAv);
				break;
			case 15:
				o.setImage(maryleboneStationTile);
				o.setInfoImage(maryboyleStation);
				break;
			case 16:
				o.setImage(bowStreetTile);
				o.setInfoImage(bowStreet);
				break;
			case 17:
				o.setImage(comchestleft);
				break;
			case 18:
				o.setImage(marlboroughStTile);
				o.setInfoImage(marlboroughSt);
				break;
			case 19:
				o.setImage(vineStreetTile);
				o.setInfoImage(vineStreet);
				break;
			case 20:
				o.setImage(freepark);
				break;
			case 21:
				o.setImage(strandTile);
				o.setInfoImage(strand);
				break;
			case 22:
				o.setImage(chancetop);
				break;
			case 23:
				o.setImage(fleetStreetTile);
				o.setInfoImage(fleetStreet);
				break;
			case 24:
				o.setImage(trafalgarSqTile);
				o.setInfoImage(trafalgarSq);
				break;
			case 25:
				o.setImage(fenchurchStStationTile);
				o.setInfoImage(fenchurchStStation);
				break;
			case 26:
				o.setImage(leicesterSqTile);
				o.setInfoImage(leicesterSq);
				break;
			case 27:
				o.setImage(coventryStreetTile);
				o.setInfoImage(coventryStreet);
				break;
			case 28:
				o.setImage(waterWorksTile);
				o.setInfoImage(waterWorks);
				break;
			case 29:
				o.setImage(piccadillyTile);
				o.setInfoImage(piccadilly);
				break;
			case 30:
				o.setImage(gotojail);
				break;
			case 31:
				o.setImage(regentStreetTile);
				o.setInfoImage(regentStreet);
				break;
			case 32:
				o.setImage(oxfordStreetTile);
				o.setInfoImage(oxfordStreet);
				break;
			case 33:
				o.setImage(comchestright);
				break;
			case 34:
				o.setImage(bondStreetTile);
				o.setInfoImage(bondStreet);
				break;
			case 35: 
				o.setImage(liverpoolStStationTile);
				o.setInfoImage(liverpoolStStation);
				break;
			case 36:
				o.setImage(chanceright);
				break;
			case 37:
				o.setImage(parkLaneTile);
				o.setInfoImage(parkLane);
				break;
			case 38:
				o.setImage(luxurytax);
				break;
			case 39:
				o.setImage(mayfairTile);
				o.setInfoImage(mayfair);
				break;
			default:
				break;
			}
		}
	}


}
