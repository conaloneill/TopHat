package monopoly;
/*
 *---Tophat---
 * Brian O'Leary - 134775468
 * Conal O'Neill - 13315756
 * Daniel Graham - 15319536
 * 
 * This class acts as our board for monopoly. It extends JPanel which we use to paint 
 * images on the board. It is redrawn using the ActionListener in our GameScreen Class.
 *
 * */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import monopoly.GameScreen;

import javax.swing.JPanel;


import propertyImages.PropertyImages;


@SuppressWarnings("serial")
public class RenderPanel extends JPanel {

	private boolean firstTime = true;
	private PropertyImages propertyImages = new PropertyImages();

	public Color insideGreen = new Color(165, 255, 137);

	private int hotelSize = 12, houseSize = 8, dotsize = 15, logoWidth = 500, logoHeight = 200, propertyImageWidth = GameScreen.BOARD_WIDTH/3 + 100, propertyImageHeight = GameScreen.BOARD_WIDTH/2 + 100;


	@Override
	protected void paintComponent(Graphics g) {
		//Enables use of info from screen
		GameScreen screen = GameScreen.screen; 
		super.paintComponent(g);

		//Draw green background
		g.setColor(Color.WHITE);
		g.fillRect(GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*11 ,GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*11, GameScreen.BOARD_WIDTH, GameScreen.BOARD_WIDTH);
		g.setColor(insideGreen);
		g.fillRect(GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*10 ,GameScreen.BOARD_WIDTH  - GameScreen.TILESIZE*10, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*2 , GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*2);

		if(firstTime){ //get images for the tile spaces only on the first time this method is called.
			propertyImages.assignTileImages();
			firstTime = false;
		}

		//Check if mouse is on a property Tile
		if(screen.mouseIsOnATile && screen.Tiles.get(screen.currentTile).getInfoImage() != null){
			g.drawImage(screen.Tiles.get(screen.currentTile).getInfoImage(), GameScreen.BOARD_WIDTH/2 - propertyImageWidth/2, GameScreen.BOARD_WIDTH/2 - propertyImageHeight/2, propertyImageWidth, propertyImageHeight, this);
		}else{
			g.drawImage(propertyImages.monopolyLogo, GameScreen.BOARD_WIDTH/2 - logoWidth/2, GameScreen.BOARD_WIDTH/2 - logoHeight/2,logoWidth,logoHeight, this);
		}

		//Loop to go through all tiles and draw the image and the black outline.
		for(Tile o : screen.Tiles){ 
			//Draw Tile's image
			g.drawImage(o.getImage(), o.x - GameScreen.TILESIZE/2, o.y - GameScreen.TILESIZE/2, GameScreen.TILESIZE, GameScreen.TILESIZE, this);
			//Draw black rectangles around tiles
			g.setColor(Color.BLACK);
			g.drawRect(o.x - GameScreen.TILESIZE/2, o.y - GameScreen.TILESIZE/2, GameScreen.TILESIZE, GameScreen.TILESIZE);
			
			//Houses and Hotels
			int x;
			int y;
			if(o.getType()==PropertyImages.TYPE_PROPERTY){
				//BOT ROW
				if(o.getTileNum() <= 9){
					if(o.getBuildings() > 0 && o.getBuildings() <= 4){
						x = o.x - GameScreen.TILESIZE/2;
						y = o.y - GameScreen.TILESIZE/2 - houseSize;
						drawHouses(g, o, x, y, 10,0);
					}else{
						x = o.x - GameScreen.TILESIZE/2;
						y = o.y - GameScreen.TILESIZE/2 - hotelSize;
						drawHotel(g, x, y);
					}
				}
				//LEFT COL
				if(o.getTileNum() >= 11 && o.getTileNum() <= 19){
					if(o.getBuildings() > 0 && o.getBuildings() <= 4){
						x = o.x + GameScreen.TILESIZE/2;
						y = o.y - GameScreen.TILESIZE/2;
						drawHouses(g, o, x, y, 0, 10);
					}else{
						x = o.x + GameScreen.TILESIZE/2;
						y = o.y - GameScreen.TILESIZE/2;
						drawHotel(g, x, y);
					}
				}
				//TOP ROW
				if(o.getTileNum() >= 21 && o.getTileNum() <= 29){
					if(o.getBuildings() > 0 && o.getBuildings() <= 4){
						x = o.x + GameScreen.TILESIZE/2 - houseSize;
						y = o.y + GameScreen.TILESIZE/2;
						drawHouses(g, o, x, y, -10, 0);
					}else{
						x = o.x + GameScreen.TILESIZE/2 - hotelSize;
						y = o.y + GameScreen.TILESIZE/2;
						drawHotel(g, x, y);
					}
				}
				//RIGHT COL
				if(o.getTileNum() >= 31 && o.getTileNum() <= 39){
					if(o.getBuildings() > 0 && o.getBuildings() <= 4){
						x = o.x - GameScreen.TILESIZE/2 - houseSize;
						y = o.y + GameScreen.TILESIZE/2 - houseSize;
						drawHouses(g, o, x, y, 0, -10);
					}else{
						x = o.x - GameScreen.TILESIZE/2 - hotelSize;
						y = o.y + GameScreen.TILESIZE/2 - hotelSize;
						drawHotel(g, x, y);
					}
				}
			}
			

		}

		//Draws the individual player tokens
		for(Player p : screen.Players){
			g.setColor(p.getColour());  //Sets Color to player color
			g.fillOval(p.xPosition, p.yPosition, dotsize, dotsize);  //Draws player token at current x,y coordinates
			g.setColor(Color.BLACK);
			g.drawOval(p.xPosition, p.yPosition, dotsize, dotsize);  //Draws black circle around token
			g.setFont(new Font("TimesRoman", Font.BOLD, 10));
			g.drawString("P" + p.playerNumber, p.xPosition+3, p.yPosition + 12);
		}
		//Mouse tracker red dot
		g.setColor(Color.red);
		g.fillOval(screen.mouseX - 8/2, screen.mouseY - 8/2, 8, 8);
	}


	private void drawHotel(Graphics g, int x, int y) {
		//Draw Hotel
		g.setColor(Color.red);
		g.fillRect(x, y, hotelSize, hotelSize);
		g.setColor(Color.black);
		g.drawRect(x, y, hotelSize, hotelSize);
	}


	private void drawHouses(Graphics g, Tile o, int x, int y, int xInc, int yInc) {
	
		for(int i = 0;i<o.getBuildings();i++){
			//Draw houses
			g.setColor(Color.white);
			g.fillRect(x, y, houseSize, houseSize);
			g.setColor(Color.black);
			g.drawRect(x, y, houseSize, houseSize);
			
			 x += xInc;
			 y += yInc;
		}
	}
}