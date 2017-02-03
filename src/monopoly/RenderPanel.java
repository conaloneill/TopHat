package monopoly;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RenderPanel extends JPanel {
	
	private Image backgroundImage = null;
	private Image monopolyLogo = null;
	private Image go = null;
	private Image freepark = null;
	private Image jail = null;
	private Image injail = null;
	private Image chance = null;
	private Image comchest = null;
	private Image train = null;
	private Image incometax = null;
	private Image luxurytax = null;
	
	private Color backGreen = new Color(198, 255, 181);
	public Color insideGreen = new Color(165, 255, 137);
	private int dotsize = 15, logoWidth = 500, logoHeight = 200, fontSize = 30;;
	
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
	
	@Override
	protected void paintComponent(Graphics g) {
		GameScreen screen = GameScreen.screen; //enables use of info from screen
		super.paintComponent(g);
		
		g.setColor(backGreen);//Draw gray background
		g.fillRect(GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*10 - GameScreen.TILESIZE/2 ,GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE*11, GameScreen.BOARD_WIDTH + GameScreen.TILESIZE/2, GameScreen.BOARD_HEIGHT);
		g.setColor(insideGreen);
		g.fillRect(GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*10 + GameScreen.TILESIZE/2 ,GameScreen.BOARD_HEIGHT  - GameScreen.TILESIZE*10, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE - GameScreen.TILESIZE/2 , GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE*2);

		if(backgroundImage == null){ //get images
			backgroundImage = getImage("drawable/monopoly.jpg");
			monopolyLogo = getImage("drawable/monologo.png");
			go = getImage("drawable/go.jpg");
			freepark = getImage("drawable/freepark.png");
			jail = getImage("drawable/jail.png");
			injail = getImage("drawable/injail.png");
			chance = getImage("drawable/chance.png");
			comchest = getImage("drawable/comchest.png");
			train = getImage("drawable/train.png");
			incometax = getImage("drawable/incometax.png");
			luxurytax = getImage("drawable/luxurytax.png");
		}
		
		//g.drawImage(backgroundImage,0,0,GameScreen.BOARD_WIDTH, GameScreen.BOARD_HEIGHT, this);
		
		if(screen.mouseIsOnATile){
			g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
			g.setColor(Color.black);
			String s = "Tile no. " + screen.currentTile + " info here";
			g.drawString(s , GameScreen.BOARD_WIDTH/2 - s.length()/2-fontSize*3, GameScreen.S_HEIGHT/2);
		}else{
			g.drawImage(monopolyLogo, GameScreen.BOARD_WIDTH/2 - logoWidth/2 + 23, GameScreen.S_HEIGHT/2 - logoHeight/2,logoWidth,logoHeight, this);
		}
		
		//Draw Images on board
		g.drawImage(go, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE/2, GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE, GameScreen.TILESIZE, GameScreen.TILESIZE, this);
		g.drawImage(freepark, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*10 - GameScreen.TILESIZE/2, GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE*11, GameScreen.TILESIZE, GameScreen.TILESIZE, this);
		g.drawImage(jail, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE/2, GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE*11, GameScreen.TILESIZE, GameScreen.TILESIZE, this);
		g.drawImage(injail, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*10 - GameScreen.TILESIZE/2, GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE, GameScreen.TILESIZE, GameScreen.TILESIZE, this);

		g.drawImage(comchest, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE/2 - GameScreen.TILESIZE*2, GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE, GameScreen.TILESIZE, GameScreen.TILESIZE, this);
		g.drawImage(comchest, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*10 - GameScreen.TILESIZE/2, GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE*8, GameScreen.TILESIZE, GameScreen.TILESIZE, this);
		g.drawImage(comchest, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE/2, GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE*8, GameScreen.TILESIZE, GameScreen.TILESIZE, this);

		g.drawImage(chance, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE/2 - GameScreen.TILESIZE*7, GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE, GameScreen.TILESIZE, GameScreen.TILESIZE, this);
		g.drawImage(chance, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE/2 - GameScreen.TILESIZE*8, GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE - GameScreen.TILESIZE*10, GameScreen.TILESIZE, GameScreen.TILESIZE, this);
		g.drawImage(chance, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE/2, GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE*5, GameScreen.TILESIZE, GameScreen.TILESIZE, this);

		g.drawImage(train, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE/2 - GameScreen.TILESIZE*5, GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE, GameScreen.TILESIZE, GameScreen.TILESIZE, this);
		g.drawImage(train, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE/2 - GameScreen.TILESIZE*10, GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE*6, GameScreen.TILESIZE, GameScreen.TILESIZE, this);
		g.drawImage(train, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE/2 - GameScreen.TILESIZE*5, GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE*11, GameScreen.TILESIZE, GameScreen.TILESIZE, this);
		g.drawImage(train, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE/2, GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE*6, GameScreen.TILESIZE, GameScreen.TILESIZE, this);

		g.drawImage(incometax, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE/2 - GameScreen.TILESIZE*4, GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE, GameScreen.TILESIZE, GameScreen.TILESIZE, this);
		g.drawImage(luxurytax, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE/2, GameScreen.BOARD_HEIGHT - GameScreen.TILESIZE*3, GameScreen.TILESIZE, GameScreen.TILESIZE, this);

		
		for(Tile o : screen.Tiles){ 
			
			g.setColor(Color.BLACK);//Draw black rectangles around tiles
			g.drawRect(o.x - GameScreen.TILESIZE/2, o.y - GameScreen.TILESIZE/2, GameScreen.TILESIZE, GameScreen.TILESIZE);
			
			g.setColor(Color.yellow);
			if(o.hasPlayer){
				g.fillOval(o.x - dotsize/2, o.y - dotsize/2, dotsize, dotsize);
				g.setColor(Color.BLACK);
				g.drawOval(o.x - dotsize/2, o.y - dotsize/2, dotsize, dotsize);
			}
		}
	}
}
