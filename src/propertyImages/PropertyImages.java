package propertyImages;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import monopoly.GameScreen;
import monopoly.Tile;


public class PropertyImages {


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
		GameScreen screen = GameScreen.screen;

		if(bondStreetTile == null){ //get images
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
			comchestleft = getImage("drawable/comchestleft.png");;
			comchestright = getImage("drawable/comchestright.png");;
			chancetop = getImage("drawable/chancetop.png");;
			chanceright = getImage("drawable/chanceright.png");;
		}


		for (Tile o : screen.Tiles) {
			switch (o.getTileNum()) {
			case 0:
				o.setImage(go);
				break;
			case 1:
				o.setImage(oldKentRoadTile);
				break;
			case 2:
				o.setImage(comchest);
				break;
			case 3:
				o.setImage(whitechapelRdTile);
				break;
			case 4:
				o.setImage(incometax);
				break;
			case 5:
				o.setImage(kingsCrossTile);
				break;
			case 6:
				o.setImage(angelIslingtonTile);
				break;
			case 7:
				o.setImage(chance);
				break;
			case 8:
				o.setImage(eustonRoadTile);
				break;
			case 9:
				o.setImage(pentonvilleRdTile);
				break;
			case 10:
				o.setImage(injail);
				break;
			case 11:
				o.setImage(pallMallTile);
				break;
			case 12:
				o.setImage(electricCoTile);
				break;
			case 13:
				o.setImage(whitehallTile);
				break;
			case 14:
				o.setImage(northumberlandAvTile);
				break;
			case 15:
				o.setImage(maryleboneStationTile);
				break;
			case 16:
				o.setImage(bowStreetTile);
				break;
			case 17:
				o.setImage(comchestleft);
				break;
			case 18:
				o.setImage(marlboroughStTile);
				break;
			case 19:
				o.setImage(vineStreetTile);
				break;
			case 20:
				o.setImage(freepark);
				break;
			case 21:
				o.setImage(strandTile);
				break;
			case 22:
				o.setImage(chancetop);
				break;
			case 23:
				o.setImage(fleetStreetTile);
				break;
			case 24:
				o.setImage(trafalgarSqTile);
				break;
			case 25:
				o.setImage(fenchurchStStationTile);
				break;
			case 26:
				o.setImage(leicesterSqTile);
				break;
			case 27:
				o.setImage(coventryStreetTile);
				break;
			case 28:
				o.setImage(waterWorksTile);
				break;
			case 29:
				o.setImage(piccadillyTile);
				break;
			case 30:
				o.setImage(gotojail);
				break;
			case 31:
				o.setImage(regentStreetTile);
				break;
			case 32:
				o.setImage(oxfordStreetTile);
				break;
			case 33:
				o.setImage(comchestright);
				break;
			case 34:
				o.setImage(bondStreetTile);
				break;
			case 35: 
				o.setImage(liverpoolStStationTile);
				break;
			case 36:
				o.setImage(chanceright);
				break;
			case 37:
				o.setImage(parkLaneTile);
				break;
			case 38:
				o.setImage(luxurytax);
				break;
			case 39:
				o.setImage(mayfairTile);
				break;

			default:
				break;
			}
		}
	}


}
