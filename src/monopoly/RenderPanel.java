package monopoly;
/*
 *---Tophat---
 * Brian O'Leary
 * Conal O'Neill
 * Daniel Graham
 * 
 * This class acts as our board for monopoly. It extends JPanel which we use to paint 
 * images on the board. It is redrawn using the ActionListener in our GameScreen Class.
 *
 * */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;


import propertyImages.PropertyImages;


@SuppressWarnings("serial")
public class RenderPanel extends JPanel {

	public boolean firstTime = true;
	private PropertyImages propertyImages = new PropertyImages();

	private Color backGreen = Color.WHITE;//198, 255, 181);
	public Color insideGreen = new Color(165, 255, 137);

	private int dotsize = 15, logoWidth = 500, logoHeight = 200;

	@Override
	protected void paintComponent(Graphics g) {
		GameScreen screen = GameScreen.screen; //enables use of info from screen
		super.paintComponent(g);

		//Draw green background
		g.setColor(backGreen);
		g.fillRect(GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*11 ,GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*11, GameScreen.BOARD_WIDTH, GameScreen.BOARD_WIDTH);
		g.setColor(insideGreen);
		g.fillRect(GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*10 ,GameScreen.BOARD_WIDTH  - GameScreen.TILESIZE*10, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*2 , GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*2);

		if(firstTime){ //get images
			propertyImages.assignTileImages();
			firstTime = false;
		}

		//Draw Monopoly Logo on board
		g.drawImage(propertyImages.monopolyLogo, GameScreen.BOARD_WIDTH/2 - logoWidth/2, GameScreen.BOARD_WIDTH/2 - logoHeight/2,logoWidth,logoHeight, this);

		for(Tile o : screen.Tiles){ 
			//Draw Tile's image
			g.drawImage(o.getImage(), o.x - GameScreen.TILESIZE/2, o.y - GameScreen.TILESIZE/2, GameScreen.TILESIZE, GameScreen.TILESIZE, this);
			//Draw black rectangles around tiles
			g.setColor(Color.BLACK);
			g.drawRect(o.x - GameScreen.TILESIZE/2, o.y - GameScreen.TILESIZE/2, GameScreen.TILESIZE, GameScreen.TILESIZE);

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
	}
}