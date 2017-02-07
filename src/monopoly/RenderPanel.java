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

	private Image backgroundImage = null;
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
	
	private Image propertyTest = null;


	private Color backGreen = new Color(198, 255, 181);
	public Color insideGreen = new Color(165, 255, 137);
	private Color brown = new Color(175, 91, 7);
	private Color lightBlue = new Color(24, 224, 214);
	private Color pink = new Color(255, 53, 248);
	private Color orange = new Color(255, 161, 0);
	private Color red = new Color(234, 31, 9);
	private Color yellow = new Color(255, 238, 12);
	private Color green = new Color(33, 160, 1);
	private Color darkBlue = new Color(1, 25, 160);


	
	private int dotsize = 15, logoWidth = 500, logoHeight = 200, fontSizeBig = 30, fontSizeRent = 17, propertyImageWidth = GameScreen.BOARD_WIDTH/3 + 100,
			propertyImageHeight = GameScreen.BOARD_HEIGHT/2 + 100;
	
	public Image getImage(String path){ //Function to get images
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

		g.setColor(backGreen);//Draw green background
		g.fillRect(GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*11 ,GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE*11, GameScreen.BOARD_WIDTH, GameScreen.BOARD_HEIGHT);
		g.setColor(insideGreen);
		g.fillRect(GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*10 ,GameScreen.BOARD_HEIGHT  - GameScreen.TILESIZE*10, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*2 , GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE*2);

		 //get images
		if(backgroundImage == null){
			backgroundImage = getImage("drawable/monopoly.jpg");
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
			propertyTest = getImage("drawable/PropertyTest1.png");
		}

		//g.drawImage(backgroundImage,0,0,GameScreen.BOARD_WIDTH, GameScreen.BOARD_HEIGHT, this);

		if(screen.mouseIsOnATile){
			g.setFont(new Font("TimesRoman", Font.PLAIN, fontSizeBig));
			g.setColor(Color.black);
			String s = "Tile no. " + screen.currentTile + " info here";
			g.drawString(s , GameScreen.BOARD_WIDTH/2 - s.length()/2-fontSizeBig*3, GameScreen.BOARD_HEIGHT/2);
			/*if(screen.currentTile == 1){
				g.drawImage(propertyTest, GameScreen.BOARD_WIDTH/2 - propertyImageWidth/2, GameScreen.BOARD_HEIGHT/2 - propertyImageHeight/2, propertyImageWidth, propertyImageHeight, this);
			}*/
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

		g2d.setTransform(old); //rotate back to original canvas

		for(Tile o : screen.Tiles){ 

			g.setColor(Color.BLACK);//Draw black rectangles around tiles
			g.drawRect(o.x - GameScreen.TILESIZE/2, o.y - GameScreen.TILESIZE/2, GameScreen.TILESIZE, GameScreen.TILESIZE);

		
			/*if (o.getTileNum() == 7 || o.getTileNum() == 22 || o.getTileNum() == 36) {
				o.setImage(chance);
			}*/

			//should put this in init() so its not done everytime
			switch (o.getTileNum()) {
			
			case 0:
				o.setImage(go); break;
			case 1:
				o.setinfoImage(propertyTest);   //Testing tile images
			case 2:
				o.setImage(comchest); break;
			case 4:
				o.setImage(incometax); break;
			case 7:
				o.setImage(chance); break;
			case 10:
				o.setImage(injail); break;
			case 17:
				o.setImage(comchest); break;
			case 20:
				o.setImage(freepark); break;
			case 22:
				o.setImage(chance); break;
			case 30:
				o.setImage(gotojail); break;
			case 33:
				o.setImage(comchest); break;
			case 36:
				o.setImage(chance); break;
			case 38:
				o.setImage(luxurytax); break;
			default:
				break;
				
			}
			
			//Prob wont need this if were using images
			/*g.setFont(new Font("TimesRoman", Font.BOLD, fontSizeRent));
			g.setColor(Color.black);
			g.drawString(o.rentToString(), o.x - 15, o.y + 25);*/
			
			//Tile Area testing
			//g.setColor(Color.BLUE);
			//g.fillRect(o.x - GameScreen.TILESIZE/2, o.y - GameScreen.TILESIZE/2, GameScreen.TILESIZE, GameScreen.TILESIZE);
			 

			if(o.hasPlayer){
				g.setColor(Color.yellow);
				g.fillOval(o.x - dotsize/2, o.y - dotsize/2, dotsize, dotsize);
				g.setColor(Color.BLACK);
				g.drawOval(o.x - dotsize/2, o.y - dotsize/2, dotsize, dotsize);
			}
			
			//If mouse is on tile, draw tile info image
			if(o.getTileNum() == screen.currentTile){
				g.drawImage(o.getinfoImage(), GameScreen.BOARD_WIDTH/2 - propertyImageWidth/2, GameScreen.BOARD_HEIGHT/2 - propertyImageHeight/2, propertyImageWidth, propertyImageHeight, this);
			}
		}

		//Mouse tracker red dot
		g.setColor(Color.red);
		g.fillOval(screen.mouseX - 10/2, screen.mouseY - 10/2, 10, 10);
	}
}