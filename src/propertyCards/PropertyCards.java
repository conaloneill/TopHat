package propertyCards;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import monopoly.GameScreen;
import monopoly.RenderPanel;
import monopoly.Tile;


public class PropertyCards {

	private Image monopolyLogo = null;
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



	public Image getImage(String path){ 
		Image temp = null;
		try {
			URL imageURL = RenderPanel.class.getResource(path);
			temp = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return temp;
	}
	
	
	public void assignTileImages() {
		GameScreen screen = GameScreen.screen;

		if(monopolyLogo == null){ //get images
			bondStreet = getImage("drawable/Bond Street.png");
			bowStreet = getImage("drawable/Bow Street.png");
			coventryStreet = getImage("drawable/Coventry Street.png");
			electricCo = getImage("drawable/Electric Co.png");
			eustonRoad = getImage("drawable/Euston Road.png");
			fenchurchStStation = getImage("drawable/Fenchurch Street Station/png");
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
			strand = getImage("drawable/Strand");
			angelIslington = getImage("drawable/The Angel Islington.png");
			trafalgarSq = getImage("drawable/Trafalgar Square.png");
			vineStreet = getImage("drawable/Vine Street.png");
			waterWorks = getImage("drawable/Water Works.png");
			whitechapelRd = getImage("drawable/Whitechapel Road.png");
			whitehall = getImage("drawable/Whitehall");
		}
		
		
		for (Tile o : screen.Tiles) {
			switch (o.getTileNum()) {
			case 1:
				o.setInfoImage(oldKentRoad);
				break;
			case 3:
				o.setInfoImage(whitechapelRd);
				break;
			case 5:
				o.setInfoImage(kingsCross);
				break;
			case 6:
				o.setInfoImage(angelIslington);
				break;
			case 8:
				o.setInfoImage(eustonRoad);
				break;
			case 9:
				o.setInfoImage(pentonvilleRd);
				break;
			case 11:
				o.setInfoImage(pallMall);
				break;
			case 12:
				o.setInfoImage(electricCo);
				break;
			case 13:
				o.setInfoImage(whitehall);
				break;
			case 14:
				o.setInfoImage(northumberlandAv);
				break;
			case 15:
				o.setInfoImage(maryboyleStation);
				break;
			case 16:
				o.setInfoImage(bowStreet);
				break;
			case 18:
				o.setInfoImage(marlboroughSt);
				break;
			case 19:
				o.setInfoImage(vineStreet);
				break;
			case 21:
				o.setInfoImage(strand);
				break;
			case 23:
				o.setInfoImage(fleetStreet);
				break;
			case 24:
				o.setInfoImage(trafalgarSq);
				break;
			case 25:
				o.setInfoImage(fenchurchStStation);
				break;
			case 26:
				o.setInfoImage(leicesterSq);
				break;
			case 27:
				o.setInfoImage(coventryStreet);
				break;
			case 28:
				o.setInfoImage(waterWorks);
				break;
			case 29:
				o.setInfoImage(piccadilly);
				break;
			case 31:
				o.setInfoImage(regentStreet);
				break;
			case 32:
				o.setInfoImage(oxfordStreet);
				break;
			case 34:
				o.setInfoImage(bondStreet);
				break;
			case 35: 
				o.setInfoImage(liverpoolStStation);
				break;
			case 37:
				o.setInfoImage(parkLane);
				break;
			case 39:
				o.setInfoImage(mayfair);
				break;
				
			default:
				break;
			}
			
		}
	}
	
	
}
