package monopoly;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class RenderPanel extends JPanel {

	private Image monopolyLogo = null;
	private Image go = null;
	private Image freepark = null;
	private Image gotojail = null;
	private Image injail = null;
	private Image chance = null;
	private Image comchest = null;
	private Image train = null;
	private Image incometax = null;
	private Image luxurytax = null;

	public Image propertyTest = null;

	private Color backGreen = new Color(198, 255, 181);
	public Color insideGreen = new Color(165, 255, 137);

	private int dotsize = 15, logoWidth = 500, logoHeight = 200, fontSizeBig = 30, propertyImageWidth = GameScreen.BOARD_WIDTH/3 + 100,
			propertyImageHeight = GameScreen.BOARD_HEIGHT/2 + 100;

	//Function to get images
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
	//method to rotate canvas by 90*
	private void rotate90Degrees(Graphics2D g){ 
		g.translate(GameScreen.BOARD_WIDTH/2, GameScreen.BOARD_HEIGHT/2);
		g.rotate(Math.toRadians(90));
		g.translate( - GameScreen.BOARD_WIDTH/2,   - GameScreen.BOARD_HEIGHT/2);
	}

	@Override
	protected void paintComponent(Graphics g) {
		GameScreen screen = GameScreen.screen; //enables use of info from screen
		super.paintComponent(g);

		//Draw green background
		g.setColor(backGreen);
		g.fillRect(GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*11 ,GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE*11, GameScreen.BOARD_WIDTH, GameScreen.BOARD_HEIGHT);
		g.setColor(insideGreen);
		g.fillRect(GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*10 ,GameScreen.BOARD_HEIGHT  - GameScreen.TILESIZE*10, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*2 , GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE*2);

		if(monopolyLogo == null){ //get images
			monopolyLogo = getImage("drawable/monologo.png");
			go = getImage("drawable/go.jpg");
			freepark = getImage("drawable/freepark.png");
			gotojail = getImage("drawable/gotojail.png");
			injail = getImage("drawable/injail.png");
			chance = getImage("drawable/chance.png");
			comchest = getImage("drawable/comchest.png");
			train = getImage("drawable/train.png");
			incometax = getImage("drawable/incometax.png");
			luxurytax = getImage("drawable/luxurytax.png");
			propertyTest = getImage("drawable/Picadilly.png");
		}

		if(screen.mouseIsOnATile){
			g.setFont(new Font("TimesRoman", Font.PLAIN, fontSizeBig));
			g.setColor(Color.black);
			String s = "Tile no. " + screen.currentTile + " info here";
			g.drawString(s , GameScreen.BOARD_WIDTH/2 - s.length()/2-fontSizeBig*3, GameScreen.BOARD_HEIGHT/2);
		}else{
			g.drawImage(monopolyLogo, GameScreen.BOARD_WIDTH/2 - logoWidth/2, GameScreen.BOARD_HEIGHT/2 - logoHeight/2,logoWidth,logoHeight, this);
		}

		Graphics2D g2d = (Graphics2D)g; //Cast to 2dGraphics to enable rotating method
		AffineTransform old = g2d.getTransform();//Save old canvas to revert back to

		//Draw Images on board
		//bottom row
		g.drawImage(go, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE, GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE, GameScreen.TILESIZE, GameScreen.TILESIZE, this);
		g.drawImage(comchest, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*3, GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE, GameScreen.TILESIZE, GameScreen.TILESIZE, this);
		g.drawImage(incometax, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*5, GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE, GameScreen.TILESIZE, GameScreen.TILESIZE, this);
		g.drawImage(train, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*6, GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE, GameScreen.TILESIZE, GameScreen.TILESIZE, this);
		g.drawImage(chance, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*8, GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE, GameScreen.TILESIZE, GameScreen.TILESIZE, this);
		g.drawImage(injail, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*11, GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE, GameScreen.TILESIZE, GameScreen.TILESIZE, this);

		//Rotate 90*
		rotate90Degrees(g2d);

		//left column
		g.drawImage(train, GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE*6, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE, GameScreen.TILESIZE, GameScreen.TILESIZE, this);
		g.drawImage(comchest, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*8, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE, GameScreen.TILESIZE, GameScreen.TILESIZE, this);
		g.drawImage(freepark, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*11, GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE, GameScreen.TILESIZE, GameScreen.TILESIZE, this);

		//Rotate 90*
		rotate90Degrees(g2d);

		//Top row
		g.drawImage(chance, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*3, GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE, GameScreen.TILESIZE, GameScreen.TILESIZE, this);
		g.drawImage(train, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*6, GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE, GameScreen.TILESIZE, GameScreen.TILESIZE, this);
		g.drawImage(gotojail, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*11, GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE, GameScreen.TILESIZE, GameScreen.TILESIZE, this);

		//Rotate 90*
		rotate90Degrees(g2d);

		//Right column
		g.drawImage(comchest, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*4, GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE, GameScreen.TILESIZE, GameScreen.TILESIZE, this);
		g.drawImage(train, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*6, GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE, GameScreen.TILESIZE, GameScreen.TILESIZE, this);
		g.drawImage(chance, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*7, GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE, GameScreen.TILESIZE, GameScreen.TILESIZE, this);
		g.drawImage(luxurytax, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*9, GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE, GameScreen.TILESIZE, GameScreen.TILESIZE, this);

		//rotate back to original canvas
		g2d.setTransform(old); 

		for(Tile o : screen.Tiles){ 
			//Draw black rectangles around tiles
			g.setColor(Color.BLACK);
			g.drawRect(o.x - GameScreen.TILESIZE/2, o.y - GameScreen.TILESIZE/2, GameScreen.TILESIZE, GameScreen.TILESIZE);

			//If mouse is on tile o, then draw o's info image
			if(o.getTileNum() == screen.currentTile){
				g.drawImage(o.getinfoImage(), GameScreen.BOARD_WIDTH/2 - propertyImageWidth/2, GameScreen.BOARD_HEIGHT/2 - propertyImageHeight/2, propertyImageWidth, propertyImageHeight, this);
			}
		}

		//Draws the individual player tokens
		for(Player p : screen.Players){
			g.setColor(p.getColour());  //Sets Color to player color
			g.fillOval(p.xPosition, p.yPosition, dotsize, dotsize);  //Draws player token at current x,y coordinates
			g.setColor(Color.BLACK);
			g.drawOval(p.xPosition, p.yPosition, dotsize, dotsize);  //Draws black circle around token
		}
		//Mouse tracker red dot
		g.setColor(Color.red);
		g.fillOval(screen.mouseX - 10/2, screen.mouseY - 10/2, 10, 10);
	}
}
